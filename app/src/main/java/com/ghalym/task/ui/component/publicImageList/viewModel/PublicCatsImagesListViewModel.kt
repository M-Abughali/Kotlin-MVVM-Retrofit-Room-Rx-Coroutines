package com.ghalym.task.ui.component.publicImageList.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ghalym.task.data.remote.RemoteResult
import com.ghalym.task.data.model.CatImage
import com.ghalym.task.data.Repository
import com.ghalym.task.network.NoInternetException
import com.ghalym.task.network.RxSingleSchedulers
import com.ghalym.task.ui.base.BaseViewModel
import com.ghalym.task.util.Constants.INSTANCE.GENERAL_ERROR_MSG
import com.ghalym.task.util.Constants.INSTANCE.SUCCESS_MSG
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class PublicCatsImagesListViewModel @Inject constructor(
    private val repository: Repository, private val rxSingleSchedulers: RxSingleSchedulers
) : BaseViewModel() {


    private var totalImagesData = MutableLiveData<ArrayList<CatImage>>()

    private var imagesResultLiveDataPrivate = MutableLiveData<RemoteResult<List<CatImage>>>()
    private val catImageRemoteResultLiveData: LiveData<RemoteResult<List<CatImage>>> get() = imagesResultLiveDataPrivate


    private var imagesPageLiveDataPrivate = MutableLiveData<Int>()
    private val imagesPageLiveData: LiveData<Int> get() = imagesPageLiveDataPrivate


    init {
        totalImagesData.value = ArrayList()
        imagesPageLiveDataPrivate.value = 1
    }


    @Inject
    fun getAllPublicImages() {
        disposable = repository.getAllPublicImages(imagesPageLiveDataPrivate.value.toString())
            .compose(rxSingleSchedulers.applySchedulers())
            .doOnSubscribe { o -> onLoading(o) }
            .subscribe(
                { t -> onSuccess(t) },
                { e -> onError(e) })
    }

    fun reloadImagesFromServer() {
        totalImagesData.value?.clear()
        imagesPageLiveDataPrivate.postValue(1)

        getAllPublicImages()
    }

    fun changeImagesPage() {
        val currentPage = imagesPageLiveDataPrivate.value!!
        imagesPageLiveDataPrivate.postValue(currentPage + 1)
    }


    override fun onLoading(disposable: Disposable?) {
        imagesResultLiveDataPrivate.postValue(RemoteResult.InProgress)
    }

    override fun onSuccess(it: ArrayList<CatImage>?) {
        if (it != null) {
            totalImagesData.value?.addAll(it)

            imagesResultLiveDataPrivate.postValue(
                RemoteResult.Success(
                    it,
                    SUCCESS_MSG
                )
            )

            changeImagesPage()
        }
    }

    override fun onError(throwable: Throwable?) {
        when (throwable) {
            is NoInternetException -> imagesResultLiveDataPrivate.postValue(RemoteResult.NetworkNoInternetError)
            else -> imagesResultLiveDataPrivate.postValue(
                RemoteResult.NetworkGeneralError(
                    GENERAL_ERROR_MSG
                )
            )
        }
    }

    private fun postAllDataIfStateChanged() {
        imagesResultLiveDataPrivate.postValue(
            RemoteResult.Success(
                totalImagesData.value,
                SUCCESS_MSG
            )
        )
    }

      fun getLiveData(): LiveData<RemoteResult<List<CatImage>>> {
        postAllDataIfStateChanged()
        return catImageRemoteResultLiveData
    }

    fun getPageLiveData(): LiveData<Int> {
        return imagesPageLiveData
    }

}