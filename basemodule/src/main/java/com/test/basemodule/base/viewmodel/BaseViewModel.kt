package com.test.basemodule.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.test.basemodule.base.livedata.QuickLiveData
import com.test.basemodule.base.model.LoadingState
import com.test.basemodule.base.model.UiError
import com.test.basemodule.base.model.VMNotification
import com.test.basemodule.utils.LogUtils
import retrofit2.HttpException
import java.util.*

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val ldLoadingState: QuickLiveData<LoadingState> = QuickLiveData()
    val ldActions: QuickLiveData<MutableSet<VMNotification>> = QuickLiveData()
    val ldUiError: MutableLiveData<UiError> = MutableLiveData<UiError>()

    init {
        ldActions.value = HashSet()
        ldLoadingState.value = LoadingState()
    }

    protected fun addLoadingObject(loadingMode: Int, tag: Any) {
        addLoadingObject(loadingMode, tag, true)
    }

    protected fun addLoadingObject(
        loadingMode: Int,
        tag: Any,
        threadSafe: Boolean
    ) {
        ldLoadingState.value!!.addLoadingObject(loadingMode, tag)
        if (threadSafe) ldLoadingState.postValue() else ldLoadingState.setValue()
    }

    protected fun removeLoadingObject(tag: Any?) {
        ldLoadingState.value!!.removeLoadingObject(tag)
        ldLoadingState.postValue()
    }

    protected fun addAction(action: String?) {
        ldActions.value!!.add(VMNotification(action!!))
        ldActions.postValue()
    }

    protected fun addAction(action: String?, tag: Any?) {
        ldActions.value!!.add(VMNotification(action!!, tag))
        ldActions.postValue()
    }

    fun clearActions() {
        ldActions.value!!.clear()
    }

    fun handleNetworkError(
        throwable: Throwable,
        mustRetry: Boolean,
        runnable: Runnable?
    ) {
        LogUtils.e("handleNetworkError", throwable)
        ldUiError.postValue(createUiErrorModel(throwable, mustRetry, runnable))
    }

    protected fun createUiErrorModel(throwable: Throwable, mustRetry: Boolean,runnable: Runnable?): UiError? {
        return UiError(
            throwable, throwable.toString(), mustRetry
            , throwable is HttpException
            , runnable
        )
    }

    fun handleNetworkError(throwable: Throwable) {
        handleNetworkError(throwable, false, null)
    }

    fun clearUiError() {
        ldUiError.postValue(null)
    }

}