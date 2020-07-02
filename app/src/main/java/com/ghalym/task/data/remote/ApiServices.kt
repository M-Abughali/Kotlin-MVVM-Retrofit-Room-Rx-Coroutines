package com.ghalym.task.data.remote

import com.ghalym.task.data.model.CatImage
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiServices {
    @GET("images/search")
    fun getAllPublicImage(
        @Query("page") page: String?
    ): Single<ArrayList<CatImage>>


}