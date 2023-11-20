package com.example.weatherreport.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weatherreport.R
import com.example.weatherreport.databinding.FragmentHomeBinding
import com.example.weatherreport.domain.entities.ForecastDayEntity
import com.example.weatherreport.ui.home.adapters.ForecastDaysAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()

    //    NOTE: Несколько важных моментов, в идеале можно было сделать возможность обновить информацию свайпом / или delay (например если нет интернета/ местоположение не включено), но в требованиях этого нет, поэтому решил не усложнять функционал
    //    NOTE: данный способ получение местоположения рекомендует гугл, поэтому я использовал его здесь, однако заметил что на некоторых устройствах он может не работать
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var forecastDaysAdapter: ForecastDaysAdapter
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLocation()

    }


    private fun initLocation() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    false
                ) && permissions.getOrDefault(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    false
                ) -> {
                    fusedLocationClient =
                        LocationServices.getFusedLocationProviderClient(requireActivity())

                    requestLocation(onSuccess = { lat, lon ->
                        initAdapter()
                        initObservers()
                        viewModel.getWeatherSummary(lat, lon)
                    }, onError = {
                        showError(getString(R.string.location_not_found))
                    })
                }

                else -> {
                    showError(getString(R.string.not_permissions))
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    // NOTE: Важно - не на всех телефонах работает данный метод поиска GPS, но этот способ рекомендует гугл + другой способ на моем телефоне тоже не работает, поэтому я не стал заморачиваться раз это приложение-пример а мой телефон исключение
    private fun showError(errorText: String) {
        binding.tvError.isVisible = errorText != ""
        binding.tvError.text = errorText
    }

    private fun initAdapter() {
        forecastDaysAdapter = ForecastDaysAdapter(resources)
        binding.rvForecast.adapter = forecastDaysAdapter
        forecastDaysAdapter.onClickListener =
            object : ForecastDaysAdapter.OnWeatherDayClickListener {
                override fun OnItemClick(weatherDayInfo: ForecastDayEntity) {
                    navigateToDetailInfoFragment(weatherDayInfo)
                }
            }
    }

    private fun navigateToDetailInfoFragment(weatherDayInfo: ForecastDayEntity) {
        val day = viewModel.getCurrentDay(weatherDayInfo.localDate)
        findNavController().navigate(
            R.id.action_navigation_home_to_detailInfoFragment,
            bundleOf(LOCALE_DATE_KEY to day)
        )
    }

    private fun initObservers() {
        viewModel.weatherSummaryEntity.observe(viewLifecycleOwner) {
            if (it == null) return@observe

            binding.tvLocation.text = getString(R.string.location_value, it.city.name)
            binding.tvLocation.isVisible = true

            forecastDaysAdapter.daysInfoList = viewModel.getForecastDaysList(it).forecastDayEntity
        }
        viewModel.errorMLD.observe(viewLifecycleOwner) {
            showError(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestLocation(
        onSuccess: (lat: Double, lon: Double) -> Unit,
        onError: () -> Unit
    ) {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val lat = location.latitude
                    val lon = location.longitude
                    onSuccess.invoke(lat, lon)
                } else {
                    onError.invoke()
                }
            }
    }

    companion object {
        const val LOCALE_DATE_KEY = "LocaleDateKey"
    }
}