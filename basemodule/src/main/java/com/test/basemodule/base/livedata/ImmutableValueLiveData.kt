package com.test.basemodule.base.livedata

import androidx.lifecycle.MutableLiveData
import com.test.basemodule.base.model.ImmutableWrapper

class ImmutableValueLiveData<T> : MutableLiveData<ImmutableWrapper<T>>() {
    fun setImmutableValue(t: T) {
        if (value != null)
            value?.value = t
        else
            value = ImmutableWrapper(t)
    }

    fun getImmutableValue(): T? {
        return value?.value
    }
}