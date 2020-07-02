package com.ghalym.task.data.local

import androidx.room.*
import com.ghalym.task.data.model.CatImage
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
@Dao
interface CatImageDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addCatImage(image: CatImage): Long

    @Query("select * from CatImage")
    fun getAllCatImages(): Single<List<CatImage>>

    @Query("select * from CatImage where id= :id")
    fun isImageInFav(id: String): Boolean

    @Delete
    fun deleteCatImage(image: CatImage): Int

    @Update
    fun updateCatImage(image: CatImage): Int
}