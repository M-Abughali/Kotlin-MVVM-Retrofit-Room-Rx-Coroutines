package com.ghalym.task.di.module

import com.ghalym.task.data.remote.ApiServices
import com.ghalym.task.network.ServiceGenerator
import dagger.Module
import dagger.Provides

@Module
class RetrofitServiceModule {
    @Provides
    fun getRetrofitService(retrofitGenerator: ServiceGenerator): ApiServices {
        return retrofitGenerator.createService(ApiServices::class.java)
    }

}