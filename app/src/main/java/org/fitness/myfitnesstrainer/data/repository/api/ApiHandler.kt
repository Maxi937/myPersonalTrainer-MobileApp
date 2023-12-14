package org.fitness.myfitnesstrainer.data.repository.api

import retrofit2.HttpException

interface ApiHandler {
    suspend fun <T : Any> handleApi(
        execute: suspend () -> retrofit2.Response<T>
    ): NetworkResult<T> {
        return try {
            val response = execute()
            val body = response.body()

            if (response.isSuccessful && body != null) {
                NetworkResult.Success(response.code(), body)
            } else {
                NetworkResult.Error(
                    code = response.code(),
                    errorMsg = response.errorBody().toString()
                )
            }
        }catch (e: HttpException){
            NetworkResult.Error(e.code(), e.message())
        }catch (e:Throwable){
            NetworkResult.Exception(e)
        }

    }
}