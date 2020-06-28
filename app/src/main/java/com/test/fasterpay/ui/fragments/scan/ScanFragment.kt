package com.test.fasterpay.ui.fragments.scan

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.budiyev.android.codescanner.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.test.basemodule.base.model.UiError
import com.test.basemodule.base.model.VMNotification
import com.test.fasterpay.R
import com.test.fasterpay.ui.fragments.base.FasterPayBaseFragment
import com.test.fasterpay.vo.MoneyTransaction
import kotlinx.android.synthetic.main.fragment_scan.*


class ScanFragment : FasterPayBaseFragment<ScanViewModel>() {
    private var codeScanner: CodeScanner? = null
    var fragmentContainer: FragmentContainer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (parentFragment is FragmentContainer)
            fragmentContainer = parentFragment as FragmentContainer
        else if (activity is FragmentContainer)
            fragmentContainer = activity as FragmentContainer
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Dexter.withContext(context)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    initCodeScanner()
                    startPreview()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                }
            }).check()
    }

    override fun onResume() {
        super.onResume()
        startPreview()
    }

    override fun onPause() {
        super.onPause()
        stopPreview()
    }

    private fun stopPreview() {
        codeScanner?.stopPreview()
    }

    private fun startPreview() {
        if (codeScanner?.isPreviewActive != false)
            codeScanner?.startPreview()
    }

    private fun initCodeScanner() {
        codeScanner = CodeScanner(requireContext(), fSScannerView)
        codeScanner?.autoFocusMode = AutoFocusMode.SAFE
        codeScanner?.scanMode = ScanMode.SINGLE
        codeScanner?.decodeCallback = DecodeCallback { viewModel.onQrCodeDetected(it) }
        codeScanner?.errorCallback = ErrorCallback { showError(it) }
    }

    private fun showError(error: Exception) { // or ErrorCallback.SUPPRESS
        activity?.runOnUiThread {
            Toast.makeText(activity, getString(R.string.camera_error, error.message),
                Toast.LENGTH_LONG).show()
        }
    }

    override fun doAction(vmNotification: VMNotification) {
        super.doAction(vmNotification)
        if (ACTION_TRANSACTION_FOUND == vmNotification.getAction())
            fragmentContainer?.onTransactionDetected(vmNotification.getTag(MoneyTransaction::class.java)!!)
    }

    override fun showUiError(uiError: UiError?) {
        uiError?.let{
            Toast.makeText(activity, uiError.errorText, Toast.LENGTH_LONG).show()
        }
    }

    interface FragmentContainer{
        fun onTransactionDetected(transaction: MoneyTransaction)
    }

    companion object {
        internal const val ACTION_TRANSACTION_FOUND = "ACTION_TRANSACTION_FOUND"
    }
}