package com.ghalym.task.data.remote

sealed class RemoteResult<out T : Any> {


    class Success<out T : Any>(val data: T?, val msg: String) : RemoteResult<T>()
    class NetworkGeneralError(val msg: String) : RemoteResult<Nothing>()
    object NetworkNoInternetError : RemoteResult<Nothing>()
    object InProgress : RemoteResult<Nothing>()
}