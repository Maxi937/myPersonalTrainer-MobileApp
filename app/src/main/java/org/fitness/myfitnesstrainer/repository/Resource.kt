package org.fitness.myfitnesstrainer.repository

import androidx.lifecycle.MutableLiveData

class Resource<T : Any>(nothing: Nothing?) : MutableLiveData<ResourceStatus<T>>() {
    fun isLoading() {
        postValue(ResourceStatus.IsLoading())
    }

    fun isError(errorMsg: String? = null) {
        postValue(ResourceStatus.IsError(errorMsg = errorMsg))
    }

    override fun postValue(value: ResourceStatus<T>?) {
        super.postValue(value)
    }

    fun isSuccess(data: T) {
        postValue(ResourceStatus.IsSuccess(data))
    }

    fun clear() {
        postValue(null)
    }

    fun isException(e: Throwable) {
        postValue(ResourceStatus.IsException(e))
    }
}