package com.solodilov.wallpaperapp.presentation.screen.splash

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.solodilov.wallpaperapp.R
import kotlinx.coroutines.delay

class SplashFragment: Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            delay(1000)
            startCategoryListFragment()
        }
    }

    private fun startCategoryListFragment() {
        findNavController().navigate(R.id.action_splashFragment_to_categoryListFragment)
    }
}