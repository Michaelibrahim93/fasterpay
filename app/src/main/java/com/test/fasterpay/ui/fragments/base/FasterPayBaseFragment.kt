package com.test.fasterpay.ui.fragments.base

import androidx.lifecycle.ViewModelProvider
import com.test.basemodule.base.view.fragment.BaseFragment

abstract class FasterPayBaseFragment<ViewModel : FasterPayBaseViewModel> :
    BaseFragment<ViewModel>() {
    override fun provideViewModel(viewModelClass: Class<ViewModel>): ViewModel {
        return ViewModelProvider(this).get(viewModelClass)
    }
}