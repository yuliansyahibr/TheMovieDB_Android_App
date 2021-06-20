package com.dicoding.tmdbapp.util

import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

object FragmentExt {

    fun Fragment.setUpLoader(progressBar: View, loadingState: LiveData<Boolean>) {
        loadingState.observe(viewLifecycleOwner, { loading ->
            progressBar.isVisible = loading
        })
    }

    fun Fragment.toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}