package com.ks.aplikasidicodingevent.ui.setting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.ks.aplikasidicodingevent.databinding.FragmentSettingBinding
import com.ks.aplikasidicodingevent.ui.MyWorker
import java.util.concurrent.TimeUnit


class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private lateinit var workManager : WorkManager
    private lateinit var settingViewModel: SettingViewModel
    private lateinit var notificationPreference: NotificationPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        workManager = WorkManager.getInstance(requireContext())
        notificationPreference = NotificationPreference(requireContext())
        val dataStore = requireContext().dataStore
        val pref = SettingPreference.getInstance(dataStore)

        val factory = SettingViewModelFactory(pref, notificationPreference)
        settingViewModel = ViewModelProvider(this, factory)[SettingViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeThemeSetting()
        observeNotificationSetting()
//
//        val dataStore = requireContext().dataStore
//        val pref = SettingPreference.getInstance(dataStore)
//        settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref, notificationPreference))[SettingViewModel::class.java]

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            Log.d("SettingFragment", "Switch toggled: $isChecked")
            settingViewModel.saveThemeSetting(isChecked)
        }

        binding.switchNotification.setOnCheckedChangeListener { _, isChecked ->
            settingViewModel.getNotificationSetting(isChecked)
            if (isChecked) {
                startPeriodicTask()
            } else {
                cancelPeriodicTask()
            }
        }
    }

    private fun startPeriodicTask() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val periodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()
        workManager.enqueue(periodicWorkRequest)
        Toast.makeText(context, "Notification task scheduled", Toast.LENGTH_SHORT).show()
    }

    private fun cancelPeriodicTask() {
        workManager.cancelAllWork()
        Toast.makeText(context, "Notification task canceled", Toast.LENGTH_SHORT).show()
    }

    private fun observeThemeSetting() {
        settingViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            binding.switchTheme.isChecked = isDarkModeActive
        }
    }

    private fun observeNotificationSetting() {
        binding.switchNotification.isChecked = notificationPreference.isNotificationEnabled()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
