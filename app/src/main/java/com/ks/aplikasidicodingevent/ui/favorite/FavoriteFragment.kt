package com.ks.aplikasidicodingevent.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ks.aplikasidicodingevent.databinding.FragmentFavoriteBinding
import com.ks.aplikasidicodingevent.ui.ViewModelFactory

class FavoriteFragment : Fragment() {

    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private lateinit var favoriteEventAdapter: FavoriteEventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteEventAdapter = FavoriteEventAdapter { favoriteEvent ->
            val action = FavoriteFragmentDirections.actionNavigationFavoriteToNavigationDetail(favoriteEvent.id.toInt())
            findNavController().navigate(action)
        }

        binding?.rvEvent?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteEventAdapter
        }

        favoriteViewModel.getFavoriteStatus().observe(viewLifecycleOwner) { favoriteEvent ->
            favoriteEventAdapter.setFavoriteEvents(favoriteEvent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}