package com.ghalym.task.ui.base

import androidx.lifecycle.ViewModel
import com.ghalym.task.data.model.CatImage
import io.reactivex.disposables.Disposable


abstract class BaseViewModel : ViewModel() {


    var disposable: Disposable? = null

    protected abstract fun onLoading(disposable: Disposable?)

    protected abstract fun onSuccess(it: ArrayList<CatImage>?)

    protected abstract fun onError(throwable: Throwable?)


    override fun onCleared() {
        super.onCleared()
        if (disposable != null) {
            disposable?.dispose()
            disposable = null
        }
    }
}
