package ru.muztache.feature.notch

import android.Manifest
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.muztache.feature.notch.event.NotchActivityEvent

class NotchActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[NotchActivityViewModel::class.java]
    }

    private val permissionRequestLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.onPermissionGranted()
        } else {
            viewModel.onPermissionNotGranted()
        }
    }

    private fun observeEvents(event: NotchActivityEvent) {
        when (event) {
            is NotchActivityEvent.RequestPermissionForOverlaying -> {
                permissionRequestLauncher.launch(Manifest.permission.SYSTEM_ALERT_WINDOW)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notch)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        enableEdgeToEdge()
    }
}