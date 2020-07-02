package com.ghalym.task.ui.component.singleImageDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ghalym.task.data.model.CatImage
import com.ghalym.task.data.Repository
import com.ghalym.task.data.local.LocalResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageDetailsViewModel @Inject constructor(
    private val repository: Repository) : ViewModel() {


    private var isImageFavoritLiveDataPrivate = MutableLiveData<LocalResult<Boolean>>()
    private val isImageFavoritLiveData: LiveData<LocalResult<Boolean>> get() = isImageFavoritLiveDataPrivate



    private var changeImageStatuesLiveDataPrivate = MutableLiveData<LocalResult<Boolean>>()
    private val imageStatuesLiveData: LiveData<LocalResult<Boolean>> get() = changeImageStatuesLiveDataPrivate

    fun getChangeImageStatuesLiveData(): LiveData<LocalResult<Boolean>> {
        return imageStatuesLiveData
    }





    fun isImageInFav(catImage: CatImage) {
        CoroutineScope(Dispatchers.IO).launch {
            val result: Boolean = repository.isImageInFav(catImage)
            withContext(Dispatchers.Main) {
                if(result){
                    isImageFavoritLiveDataPrivate.postValue(LocalResult.IsImageInFavorite(true,"IMAGE IS IN FAV"))
                }else{
                    isImageFavoritLiveDataPrivate.postValue(LocalResult.IsImageInFavorite(false,"IMAGE IS Not In FAV"))

                }

            }
        }

    }




     fun getIsImageFavoritLiveData(): LiveData<LocalResult<Boolean>> {
        return isImageFavoritLiveData
    }



    fun changeFavoriteImageStatues(catImage: CatImage) {
        CoroutineScope(Dispatchers.IO).launch {
            val localResult: LocalResult<Boolean> = repository.changeImageFavStatus(catImage)
            withContext(Dispatchers.Main) {
                changeImageStatuesLiveDataPrivate.postValue(localResult)
            }
        }

    }





}