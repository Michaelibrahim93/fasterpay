package com.test.fasterpay.ui.fragments.scan

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.hilt.lifecycle.ViewModelInject
import com.google.zxing.Result
import com.test.fasterpay.R
import com.test.fasterpay.ui.fragments.base.FasterPayBaseViewModel
import com.test.fasterpay.dataaccess.operator.TransactionDecoder

class ScanViewModel @ViewModelInject constructor(
    application: Application,
    private val transactionDecoder: TransactionDecoder
) : FasterPayBaseViewModel(application) {

    @WorkerThread
    fun onQrCodeDetected(it: Result) = launchDataLoad(false) {
        try {
            val transaction = transactionDecoder.decodeTransaction(it.text)
            addAction(ScanFragment.ACTION_TRANSACTION_FOUND, transaction, false)
        } catch (throwable: Throwable) {
            sendError(getContext().getString(R.string.invalid_qr_code))
        }
    }
}