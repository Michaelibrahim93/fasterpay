package com.test.fasterpay.ui.fragments.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.test.basemodule.base.model.UiError
import com.test.basemodule.base.view.fragment.BaseFragment
import com.test.fasterpay.R

abstract class FasterPayBaseFragment<ViewModel : FasterPayBaseViewModel> :
    BaseFragment<ViewModel>() {
    override fun provideViewModel(viewModelClass: Class<ViewModel>): ViewModel {
        return ViewModelProvider(this).get(viewModelClass)
    }

    fun<T : ViewDataBinding> inflateBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        @LayoutRes res: Int
    ): T {
        val bindingObject = DataBindingUtil.inflate<T>(layoutInflater, res, container, false)
        bindingObject.lifecycleOwner = viewLifecycleOwner
        return bindingObject
    }

    override fun showUiError(uiError: UiError?) {
        if (uiError == null) return
        context?.let {
            AlertDialog.Builder(it)
                .setMessage(uiError.errorText)
                .setPositiveButton(R.string.ok, null)
                .show()
        }
    }
}