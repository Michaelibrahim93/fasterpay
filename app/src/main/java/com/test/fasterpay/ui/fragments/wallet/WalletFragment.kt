package com.test.fasterpay.ui.fragments.wallet

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.test.basemodule.utils.initLinear
import com.test.fasterpay.R
import com.test.fasterpay.databinding.FragmentWalletBinding
import com.test.fasterpay.ui.fragments.base.BindingBaseFragment
import com.test.fasterpay.ui.fragments.wallet.adapter.TransactionAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFragment : BindingBaseFragment<WalletViewModel, FragmentWalletBinding>(), WalletCallbacks {
    private val adapter: TransactionAdapter = TransactionAdapter()

    override val layoutRes: Int
        get() = R.layout.fragment_wallet

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initBinding()
        initObservers()
    }

    private fun initObservers() {
        viewModel.ldTransactions.observe(viewLifecycleOwner, Observer { adapter.setData(it) })
    }

    private fun initBinding() {
        binding.callbacks = this
        binding.wallet = viewModel.ldWallet
        binding.transactions = viewModel.ldTransactions
    }

    private fun initRecycler() {
        context?.let { binding.fWRecyclerTransactions.initLinear(it, adapter) }
    }

    override fun clickedTopUp() {
        comingSoon()
    }

    override fun clickedVault() {
        comingSoon()
    }
}