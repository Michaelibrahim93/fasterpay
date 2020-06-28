package com.test.fasterpay.ui.fragments.base

import android.app.Application
import com.test.basemodule.base.model.UiError
import com.test.basemodule.base.viewmodel.BaseViewModel
import com.test.basemodule.utils.LogUtils
import com.test.fasterpay.dataaccess.fakenetwork.models.FakeServiceError
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

open class FasterPayBaseViewModel(application: Application) : BaseViewModel(application) {
    protected val compositeDisposable = CompositeDisposable()

    protected fun runObservable(observable: Observable<*>) {
        compositeDisposable.add(observable.subscribe(
            { LogUtils.d(javaClass.simpleName, "request succeed $it") },
            { LogUtils.e(javaClass.simpleName, "request failed $it") }
        ))
    }

    override fun onCleared() {
        compositeDisposable.dispose()
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