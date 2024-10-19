package com.ks.aplikasidicodingevent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ks.aplikasidicodingevent.data.remote.response.ListEventsItem
import com.ks.aplikasidicodingevent.databinding.FragmentHomeBinding
import com.ks.aplikasidicodingevent.ui.HorizontalAdapter
import com.ks.aplikasidicodingevent.ui.VerticalAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by viewModels<HomeViewModel>()

    private var adapterHorizontal = HorizontalAdapter(object : HorizontalAdapter.OnItemClickListener {
        override fun onItemClick(event: ListEventsItem) {
            val action = HomeFragmentDirections
                .actionNavigationHomeToNavigationDetail(event.id)
            findNavController().navigate(action)
        }
    })
    private var adapterVertical = VerticalAdapter(object : VerticalAdapter.OnItemClickListener {
        override fun onItemClick(event: ListEventsItem) {
            val action = HomeFragmentDirections
                .actionNavigationHomeToNavigationDetail(event.id)
            findNavController().navigate(action)
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvEventHorizontal.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterHorizontal
        }
        binding.rvEventHorizontal.adapter = adapterHorizontal


        binding.rvEventVertical.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.rvEventVertical.adapter = adapterVertical


        homeViewModel.upcomingListEvent.observe(viewLifecycleOwner) { listEvents ->
            upcomingEventData(listEvents)
        }

        homeViewModel.finishedListEvent.observe(viewLifecycleOwner) { listEvents ->
            finishedEventData(listEvents)
        }

        homeViewModel.isLoadingUpcoming.observe(viewLifecycleOwner) {
            showLoadingUpcoming(it)
        }

        homeViewModel.isLoadingFinished.observe(viewLifecycleOwner) {
            showLoadingFinish(it)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun upcomingEventData(events: List<ListEventsItem?>?) {
        if (!events.isNullOrEmpty()) {
            val adapterHorizontal = binding.rvEventHorizontal.adapter as HorizontalAdapter
            adapterHorizontal.submitList(events)
        }
    }

    private fun finishedEventData(events: List<ListEventsItem?>?) {
        if (!events.isNullOrEmpty()) {
            val adapterVertical = binding.rvEventVertical.adapter as VerticalAdapter
            adapterVertical.submitList(events)
        }
    }

    private fun showLoadingUpcoming(isLoading: Boolean) {
        binding.progressBarH.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showLoadingFinish(isLoading: Boolean) {
        binding.progressBarV.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}