package com.ghalym.task.di.module

import android.app.Application
import androidx.room.Room
import com.ghalym.task.data.local.CatImageDataBase
import com.ghalym.task.data.local.CatImageDataBase.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class CatImageDataBaseModule {

    @Singleton
    @Provides
    fun getCatImagesDataBase(application: Application): CatImageDataBase {
        return Room.databaseBuilder(application, CatImageDataBase::class.java, DATABASE_NAME).build()
    }

}