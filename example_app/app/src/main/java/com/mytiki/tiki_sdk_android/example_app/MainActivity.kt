package com.mytiki.tiki_sdk_android.example_app

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.example_app.databinding.MainActivityBinding
import com.mytiki.tiki_sdk_android.example_app.try_it_out.TryItOutViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import androidx.fragment.app.activityViewModels

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: MainActivityBinding

    private val viewModel by viewModels<TryItOutViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}