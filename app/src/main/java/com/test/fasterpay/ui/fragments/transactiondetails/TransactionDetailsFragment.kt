package com.test.fasterpay.ui.fragments.transactiondetails

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.test.basemodule.base.model.VMNotification
import com.test.fasterpay.R
import com.test.fasterpay.databinding.FragmentTransactionDetailsBinding
import com.test.fasterpay.ui.fragments.base.BindingBaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionDetailsFragment :
    BindingBaseFragment<TransactionDetailsViewModel, FragmentTransactionDetailsBinding>(),
    TransactionDetailsCallbacks{

    override val layoutRes: Int
        get() = R.layout.fragment_transaction_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.extractData(arguments)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding(view)
        binding.fTDToolbar.setNavigationOnClickListener { activity?.onBackPressed() }
    }

    private fun initBinding(rootView: View) {
        binding.rootView = rootView
        binding.transaction = viewModel.ldUiTransactionDetails
        binding.callbacks = this
    }

    override fun doAction(vmNotification: VMNotification) {
        super.doAction(vmNotification)
        if (ACTION_SHOW_CONGRATS == vmNotification.getAction())
            showCongratsDialog()
    }

    private fun showCongratsDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.congrats_transaction_completed)
            .setPositiveButton(R.string.ok, null)
            .setOnDismissListener { activity?.onBackPressed() }
            .show()
    }

    override fun clickedPay() {
        viewModel.pay()
    }

    companion object {
        const val ACTION_SHOW_CONGRATS: String = "ACTION_SHOW_CONGRATS"
        const val KEY_TRANSACTION = "KEY_TRANSACTION"
    }
}