package com.example.weatherreport.ui.detailInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherreport.databinding.FragmentDetailInfoBinding
import com.example.weatherreport.ui.detailInfo.adapters.ForecastTimestampsDaysAdapter
import com.example.weatherreport.ui.home.HomeFragment

class DetailInfoFragment : Fragment() {

    private lateinit var forecastTimestampsAdapter: ForecastTimestampsDaysAdapter
    private var _binding: FragmentDetailInfoBinding? = null
    private val viewModel: DetailInfoViewModel by viewModels()


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel.currentDate = requireArguments().getInt(HomeFragment.LOCALE_DATE_KEY, -1)

        _binding = FragmentDetailInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        viewModel.weatherSummaryEntity.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            forecastTimestampsAdapter.timestampsInfo =
                viewModel.getForecastTimestampList(it, viewModel.currentDate)
        }
    }

    private fun initAdapter() {
        forecastTimestampsAdapter = ForecastTimestampsDaysAdapter(resources)
        binding.rvForecastByTimestamps.adapter = forecastTimestampsAdapter

    }

}