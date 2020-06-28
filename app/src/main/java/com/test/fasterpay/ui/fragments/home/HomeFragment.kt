package com.test.fasterpay.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.test.fasterpay.R
import com.test.fasterpay.ui.fragments.base.FasterPayBaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : FasterPayBaseFragment<HomeViewModel>() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomNavigationView()
    }

    private fun initBottomNavigationView() {
        fHBottomNav.post {
            activity?.let {
                fHBottomNav.setupWithNavController(
                    Navigation.findNavController(it, R.id.fHHostFragment)
                )
            }
        }
    }
}