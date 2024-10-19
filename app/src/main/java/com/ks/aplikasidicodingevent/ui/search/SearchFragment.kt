package com.ks.aplikasidicodingevent.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ks.aplikasidicodingevent.data.remote.response.ListEventsItem
import com.ks.aplikasidicodingevent.databinding.FragmentSearchBinding
import com.ks.aplikasidicodingevent.ui.EventAdapter

class SearchFragment : Fragment(), EventAdapter.OnItemClickListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var eventAdapter: EventAdapter
    private val viewModel: SearchViewModel by viewModels()
    private var status: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvEvent.layoutManager = LinearLayoutManager(requireContext())
        eventAdapter = EventAdapter(this)
        binding.rvEvent.adapter = eventAdapter

        // Observe LiveData dari ViewModel
        viewModel.listEvent.observe(viewLifecycleOwner, { events ->
            setEventData(events)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        status = arguments?.getInt("status") ?: 1

        binding.searchView.setupWithSearchBar(binding.searchBar)
        binding.searchView.editText.setOnEditorActionListener { _, _, _ ->
            val query = binding.searchView.text.toString().trim()
            if (query.isNotBlank()) {
                viewModel.findEvent(status!!, query)
            } else {
                Toast.makeText(requireContext(), "Masukkan pencarian..", Toast.LENGTH_SHORT).show()
            }
            binding.searchView.hide()
            false
        }
    }

    private fun setEventData(events: List<ListEventsItem?>?) {
        if (!events.isNullOrEmpty()) {
            val adapter = binding.rvEvent.adapter as EventAdapter
            adapter.submitList(events)
        } else {
            Log.e("UpcomingFragment", "Event list is empty or null")
        }
    }

    override fun onItemClick(event: ListEventsItem) {
        val action = SearchFragmentDirections.actionNavigationSearchToNavigationDetail(event.id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
