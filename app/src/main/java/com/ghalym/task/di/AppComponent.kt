package com.ghalym.task.di

import android.app.Application
import com.ghalym.task.di.module.*
import com.ghalym.task.ui.component.singleImageDetails.ImageDetailsActivity
import com.ghalym.task.ui.component.favouriteCatList.FavCatsImagesListActivity
import com.ghalym.task.ui.component.publicImageList.PublicCatsImagesListActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [RetrofitServiceModule::class, RepositoryModule::class, ViewModelModule::class, RxModule::class, CatImageDaoModule::class, CatImageDataBaseModule::class
    ]
)
interface AppComponent {
    fun inject(publicCatsImagesListActivity: PublicCatsImagesListActivity)
    fun inject(imageDetailsActivity: ImageDetailsActivity)
    fun inject(favCatsImagesListActivity: FavCatsImagesListActivity)


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun injectApplication(application: Application): Builder

        fun build(): AppComponent
    }
}