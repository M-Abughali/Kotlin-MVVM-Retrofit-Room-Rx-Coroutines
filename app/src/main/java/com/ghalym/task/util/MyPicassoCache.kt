package com.ghalym.task.util

import android.app.Application
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import javax.inject.Singleton


@Singleton
 class MyPicassoCache  {

    companion object : SingletonHolder<Picasso, Application>({
        val downloader = OkHttp3Downloader(it, Int.MAX_VALUE.toLong())
        val builder = Picasso.Builder(it)
        builder.downloader(downloader)
         builder.build()
    })




}
