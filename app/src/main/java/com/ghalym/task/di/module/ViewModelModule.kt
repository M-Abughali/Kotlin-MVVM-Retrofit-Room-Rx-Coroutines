package com.ghalym.task.di.module
import androidx.lifecycle.ViewModel
import com.ghalym.task.ui.component.favouriteCatList.FavCatsImagesListViewModel
import com.ghalym.task.ui.component.publicImageList.viewModel.PublicCatsImagesListViewModel
import com.ghalym.task.ui.component.singleImageDetails.ImageDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(PublicCatsImagesListViewModel::class)
    internal abstract fun bindImageListViewModel(viewModel: PublicCatsImagesListViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(FavCatsImagesListViewModel::class)
    internal abstract fun bindFavoriteImageListViewModel(viewModel: FavCatsImagesListViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(ImageDetailsViewModel::class)
    internal abstract fun bindImageDetailsViewModel(viewModel: ImageDetailsViewModel): ViewModel

}
