package com.ks.aplikasidicodingevent.ui.finished

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ks.aplikasidicodingevent.data.remote.response.ListEventsItem
import com.ks.aplikasidicodingevent.databinding.FragmentFinishedBinding
import com.ks.aplikasidicodingevent.ui.EventAdapter

class FinishedFragment : Fragment(), EventAdapter.OnItemClickListener {
    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!
    private val finishedViewModel by viewModels<FinishedViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvEvent.layoutManager = LinearLayoutManager(requireContext())
        val adapter = EventAdapter(this)
        binding.rvEvent.adapter = adapter

        finishedViewModel.listEvent.observe(viewLifecycleOwner) { listEvents ->
            setEventData(listEvents)

        }

        finishedViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setEventData(events: List<ListEventsItem?>?) {
        if (!events.isNullOrEmpty()) {
            val adapter = binding.rvEvent.adapter as EventAdapter // Gunakan adapter yang sudah ada
            adapter.submitList(events)
        } else {
            Log.e("UpcomingFragment", "Event list is empty or null")
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(event: ListEventsItem) {
        val action = FinishedFragmentDirections
            .actionNavigationFinishedToNavigationDetail(event.id)
        findNavController().navigate(action)
    }
}