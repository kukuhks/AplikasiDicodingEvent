package com.ks.aplikasidicodingevent.ui.detail

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ks.aplikasidicodingevent.R
import com.ks.aplikasidicodingevent.data.local.entity.FavoriteEvent
import com.ks.aplikasidicodingevent.databinding.FragmentDetailEventBinding
import com.ks.aplikasidicodingevent.ui.ViewModelFactory
import java.util.Locale

class DetailEventFragment : Fragment() {
    private var _binding: FragmentDetailEventBinding? = null
    private val binding get() = _binding!!
    private val detailEventViewModel: DetailEventViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())
        return try {
            val date = inputFormat.parse(inputDate) // Parsing string ke Date
            outputFormat.format(date) // Format ulang ke bentuk yang mudah dibaca
        } catch (e: Exception) {
            inputDate // Kembalikan input jika parsing gagal
        }
    }

    private fun openLink(link: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        } catch (e: Exception) {
            Log.e("DetailEventFragment", "Error opening link: $link", e)
        }
    }

    private val args: DetailEventFragmentArgs by navArgs(   )
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventId = args.eventId
        Log.d("DetailEventFragment", "Received Event ID: $eventId")

        detailEventViewModel.getDetailEvent(eventId)
        detailEventViewModel.detailEvent.observe(viewLifecycleOwner) { eventDetail ->
            if (eventDetail != null) {
                Log.d("DetailEventFragment", "Detail Event: $eventDetail") // Log detail event
                binding.apply {
                    Glide.with(requireContext())
                        .load(eventDetail.mediaCover)
                        .into(ivPictureDesc)
                    tvTitleDesc.text = eventDetail.name
                    ownerNameValue.text = eventDetail.ownerName
                    val sisa = eventDetail.quota - eventDetail.registrants
                    quotaLeftValue.text = "$sisa"
                    dateValue.text = formatDate(eventDetail.beginTime)
                    tvInfoValue.text = HtmlCompat.fromHtml(
                        eventDetail.description,
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                    btnRegister.setOnClickListener{
                        openLink(eventDetail.link)
                    }
                }
            }
        }

        detailEventViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        detailEventViewModel.getFavoriteStatus(eventId.toString()).observe(viewLifecycleOwner) { favoriteEvent ->
            val isFavorite = favoriteEvent != null
            toggleFavorite(isFavorite)

            binding.ivFavorite.setOnClickListener {
                val event = detailEventViewModel.detailEvent.value
                event?.let {
                    detailEventViewModel.toggleFavorite(
                        FavoriteEvent(it.id.toString(), it.name, it.imageLogo),
                        isFavorite = isFavorite
                    )
                }
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun toggleFavorite(isFavorite: Boolean) {
        val icon = if (isFavorite) {
            R.drawable.baseline_favorite_white_24
        } else {
            R.drawable.baseline_favorite_border_white_24
        }
        binding.ivFavorite.setImageResource(icon)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}