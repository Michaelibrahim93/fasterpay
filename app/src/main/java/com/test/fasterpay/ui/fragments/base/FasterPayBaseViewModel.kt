package com.test.fasterpay.ui.fragments.base

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.test.basemodule.base.model.UiError
import com.test.basemodule.base.viewmodel.BaseViewModel
import com.test.fasterpay.dataaccess.fakenetwork.models.FakeServiceError
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

open class FasterPayBaseViewModel(application: Application) : BaseViewModel(application) {
    protected inline fun launchDataLoad(defaultThrowableHandle: Boolean = true, crossinline block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                block()
            } catch (error: Throwable) {
                if (defaultThrowableHandle)
                    handleNetworkError(error)
            }
        }
    }

    override fun onCleared() {
        viewModelScope.cancel(CancellationException("ViewModel has been cleared"))
        super.onCleared()
    }

    override fun createUiErrorModel(throwable: Throwable, mustRetry: Boolean, runnable: Runnable?): UiError {
        return if (throwable is FakeServiceError)
            UiError(throwable, throwable.userMessage,
                mustRetry = false,
                isServerError = true,
                runnable = null
            )
        else
            super.createUiErrorModel(throwable, mustRetry, runnable)
    }

    fun sendError(message: String) {
        ldUiError.postValue(UiError(null, message,
            mustRetry = false,
            isServerError = true,
            runnable = null
        ))
    }
}