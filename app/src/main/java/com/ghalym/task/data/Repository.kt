package com.ghalym.task.data

import com.ghalym.task.data.local.CatImageDao
import com.ghalym.task.data.local.LocalResult
import com.ghalym.task.data.model.CatImage
import com.ghalym.task.data.remote.ApiServices
import com.ghalym.task.util.Constants.INSTANCE.INSERT_FAIL_MESSAGE
import com.ghalym.task.util.Constants.INSTANCE.INSERT_SUCCESS_MESSAGE
import com.ghalym.task.util.Constants.INSTANCE.REMOVE_FAIL_MESSAGE
import com.ghalym.task.util.Constants.INSTANCE.REMOVE_SUCCESS_MESSAGE
import io.reactivex.Single
import javax.inject.Inject


class Repository @Inject constructor(
    private val apiServices: ApiServices,
    private val catImageDao: CatImageDao
) {


    fun getAllPublicImages(page: String): Single<ArrayList<CatImage>> {
        return apiServices.getAllPublicImage("" + page)
    }





    fun getFavoriteImage() = catImageDao.getAllCatImages()

    fun isImageInFav(catImage: CatImage): Boolean {
        return catImageDao.isImageInFav(catImage.id)

    }

    fun changeImageFavStatus(catImage: CatImage): LocalResult<Boolean> {
        val isImageInFav: Boolean = isImageInFav(catImage)
        return if (isImageInFav) {
            removeImageFromFavorite(catImage)
        } else {
            addImageToFavorite(catImage)
        }


    }

    private fun addImageToFavorite(catImage: CatImage): LocalResult<Nothing> {
        val insertResult: Long = catImageDao.addCatImage(catImage)
        return if (insertResult > 0) {
            LocalResult.ActionAddToStorage(true,INSERT_SUCCESS_MESSAGE)
        } else {
            LocalResult.ActionAddToStorage(false,INSERT_FAIL_MESSAGE)
        }

    }

    private fun removeImageFromFavorite(catImage: CatImage): LocalResult<Nothing> {
        val deleteResult: Int = catImageDao.deleteCatImage(catImage)
        return if (deleteResult > 0) {
            LocalResult.ActionRemoveFromStorage(true,REMOVE_SUCCESS_MESSAGE)
        } else {
            LocalResult.ActionRemoveFromStorage(false,REMOVE_FAIL_MESSAGE)
        }


    }


}