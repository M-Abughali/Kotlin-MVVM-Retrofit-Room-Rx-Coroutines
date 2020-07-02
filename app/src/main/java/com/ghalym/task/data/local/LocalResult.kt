package com.ghalym.task.data.local


sealed class LocalResult<out T : Any> {


    class Success<out T : Any>(val data: T?, val msg: String) : LocalResult<T>()
    class GeneralError(val msg: String) : LocalResult<Nothing>()
    object InProgress : LocalResult<Nothing>()
    class ActionAddToStorage(val status: Boolean, val msg: String) : LocalResult<Nothing>()
    class ActionRemoveFromStorage(val status: Boolean, val msg: String) : LocalResult<Nothing>()
    class IsImageInFavorite(val status: Boolean, val msg: String) : LocalResult<Nothing>()

}