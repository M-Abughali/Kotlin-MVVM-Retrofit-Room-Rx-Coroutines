package com.ghalym.task.core

import android.app.Application
import com.ghalym.task.di.DaggerAppComponent
import com.ghalym.task.di.AppComponent

class MyApp : Application() {
    private lateinit var appComponent: AppComponent

    fun getAppComponent(): AppComponent {
        return appComponent
    }

    companion object {
        private lateinit var Instance: MyApp

        fun getInstance(): MyApp {
            return Instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        Instance = this
        appComponent = DaggerAppComponent.builder().injectApplication(this).build()

    }


}