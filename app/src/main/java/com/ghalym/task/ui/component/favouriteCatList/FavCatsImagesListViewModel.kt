package com.ghalym.task.ui.component.favouriteCatList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ghalym.task.data.model.CatImage
import com.ghalym.task.data.Repository
import com.ghalym.task.data.local.LocalResult
import com.ghalym.task.network.RxSingleSchedulers
import com.ghalym.task.ui.base.BaseViewModel
import com.ghalym.task.util.Constants.INSTANCE.GENERAL_ERROR_MSG
import com.ghalym.task.util.Constants.INSTANCE.SUCCESS_MSG
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class FavCatsImagesListViewModel @Inject constructor(
    private val repository: Repository, private val rxSingleSchedulers: RxSingleSchedulers
) : BaseViewModel() {


    private var imagesResultLiveDataPrivate = MutableLiveData<LocalResult<List<CatImage>>>()
    private val imageLocalResultLiveData: LiveData<LocalResult<List<CatImage>>> get() = imagesResultLiveDataPrivate


    fun getFavouritImagesFromStorage() {
        disposable = repository.getFavoriteImage()
            .compose(rxSingleSchedulers.applySchedulers())
            .doOnSubscribe { o -> onLoading(o) }
            .subscribe(
                { t -> onSuccess(t as ArrayList<CatImage>?) },
                { e -> onError(e) })
    }


    override fun onLoading(disposable: Disposable?) {
        imagesResultLiveDataPrivate.postValue(LocalResult.InProgress)
    }

    override fun onSuccess(it: ArrayList<CatImage>?) {
        if (it != null) {
            imagesResultLiveDataPrivate.postValue(
                LocalResult.Success(
                    it,
                    SUCCESS_MSG
                )
            )

        }
    }

    override fun onError(throwable: Throwable?) {
        imagesResultLiveDataPrivate.postValue(
            LocalResult.GeneralError(
                GENERAL_ERROR_MSG
            )
        )

    }


     fun getLiveData(): LiveData<LocalResult<List<CatImage>>> {
        return imageLocalResultLiveData
    }
}