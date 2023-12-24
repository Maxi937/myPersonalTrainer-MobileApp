package org.fitness.myfitnesstrainer.repository

sealed class ResourceStatus<T : Any> {
    class IsLoading<T : Any> : ResourceStatus<T>()
    class IsSuccess<T : Any>(val data: T) : ResourceStatus<T>()
    class IsError<T : Any>(val errorMsg: String?) : ResourceStatus<T>()
    class IsException<T : Any>(val e: Throwable?) : ResourceStatus<T>()

}

