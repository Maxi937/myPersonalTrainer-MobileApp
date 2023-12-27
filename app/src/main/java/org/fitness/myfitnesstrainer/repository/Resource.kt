package org.fitness.myfitnesstrainer.repository

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.fitness.myfitnesstrainer.api.MyFitnessClient
import org.fitness.myfitnesstrainer.data.remote.models.NetworkResult
import org.fitness.myfitnesstrainer.data.remote.models.apiProfile

class Resource<T : Any>(nothing: Nothing?) : MutableLiveData<ResourceStatus<T>>() {

    fun isLoading() {
        postValue(ResourceStatus.IsLoading())
    }

    fun isError(code: Int, errorMsg: String?) {
        postValue(ResourceStatus.IsError(code=code, errorMsg=errorMsg))
    }

    fun isSuccess(data: T) {
        postValue(ResourceStatus.IsSuccess(data))
    }

    fun update(data: T) {
        postValue(ResourceStatus.IsSuccess(data))
    }

    fun clear() {
        postValue(null)
    }

    fun isException(e: Throwable) {
        postValue(ResourceStatus.IsException(e))
    }

    override fun postValue(value: ResourceStatus<T>?) {
        super.postValue(value)
    }
}
