package com.ks.aplikasidicodingevent.ui.upcoming

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
import com.ks.aplikasidicodingevent.databinding.FragmentUpcomingBinding
import com.ks.aplikasidicodingevent.ui.EventAdapter

class UpcomingFragment : Fragment(), EventAdapter.OnItemClickListener {
    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private val upcomingViewModel by viewModels<UpcomingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvEvent.layoutManager = LinearLayoutManager(requireContext())
        val adapter = EventAdapter(this)

        binding.rvEvent.adapter = adapter

        upcomingViewModel.listEvent.observe(viewLifecycleOwner) { listEvents ->
            setEventData(listEvents)
        }

        upcomingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
//        val searchView = view.findViewById<SearchView>(R.id.searchView)
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                query?.let {
//                    searchViewModel.findEvent(1, it)
//                }
//                return true
//            }
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return true
//            }
//        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setEventData(events: List<ListEventsItem?>?) {
        if (!events.isNullOrEmpty()) {
            val adapter = binding.rvEvent.adapter as EventAdapter
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
        val action = UpcomingFragmentDirections
            .actionNavigationUpcomingToNavigationDetail(event.id)
        findNavController().navigate(action)
    }
}