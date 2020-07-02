package com.ghalym.task.di.module

import com.ghalym.task.data.local.CatImageDao
import com.ghalym.task.data.local.CatImageDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class CatImageDaoModule {

    @Singleton
    @Provides
    fun getCatImageDao(catImageDataBase: CatImageDataBase): CatImageDao {
        return catImageDataBase.noteDao()
    }

}