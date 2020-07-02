package com.ghalym.task.di.module

import com.ghalym.task.data.Repository
import com.ghalym.task.data.local.CatImageDao
import com.ghalym.task.data.remote.ApiServices
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun getRepository(apiServices: ApiServices,catImageDao: CatImageDao): Repository {
        return Repository(apiServices,catImageDao)
    }


}