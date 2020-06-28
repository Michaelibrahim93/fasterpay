package com.test.fasterpay.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.test.fasterpay.R
import com.test.fasterpay.ui.fragments.base.FasterPayBaseFragment
import com.test.fasterpay.ui.fragments.scan.ScanFragment
import com.test.fasterpay.ui.fragments.transactiondetails.TransactionDetailsFragment
import com.test.fasterpay.vo.MoneyTransaction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : FasterPayBaseFragment<HomeViewModel>(), ScanFragment.FragmentContainer {
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

    override fun onTransactionDetected(transaction: MoneyTransaction) {
        view?.findNavController()?.navigate(R.id.action_homeFragment_to_transactionDetailsFragment
            , bundleOf(TransactionDetailsFragment.KEY_TRANSACTION to transaction))
    }
}