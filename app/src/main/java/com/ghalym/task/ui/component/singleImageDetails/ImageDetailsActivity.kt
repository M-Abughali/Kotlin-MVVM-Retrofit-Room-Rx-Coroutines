package com.ghalym.task.ui.component.singleImageDetails

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.ghalym.task.R
import com.ghalym.task.core.MyApp
import com.ghalym.task.data.local.LocalResult
import com.ghalym.task.data.model.CatImage
import com.ghalym.task.databinding.ActivityCatImageDetailsBinding
import com.ghalym.task.ui.base.BaseActivity
import com.ghalym.task.ui.base.ViewModelFactory
import com.ghalym.task.util.Constants
import com.ghalym.task.util.MyPicassoCache
import com.ghalym.task.util.observe
import kotlinx.android.synthetic.main.activity_cat_image_details.*
import javax.inject.Inject

class ImageDetailsActivity : BaseActivity() {
    private lateinit var binding: ActivityCatImageDetailsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var imageDetailsViewModel: ImageDetailsViewModel
    private lateinit var image: CatImage

    override fun injectActivity(baseActivity: BaseActivity) {
        MyApp.getInstance().getAppComponent().inject(this)
    }

    override fun initializeViewModel() {
        imageDetailsViewModel =
            ViewModelProvider(this, viewModelFactory).get(ImageDetailsViewModel::class.java)

    }

    override fun observeViewModel() {
        observe(imageDetailsViewModel.getIsImageFavoritLiveData(), ::handleImageFavoriteStatus)
        observe(
            imageDetailsViewModel.getChangeImageStatuesLiveData(),
            ::handleImageFavoriteChangeStatus
        )
    }


    override fun initViewBinding() {
        binding = ActivityCatImageDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initToolBar() {
        setTitle("Display Full Image activity")
        binding.toolbarLayout.icToolbarSetting.visibility = View.GONE
        binding.toolbarLayout.icToolbarRefresh.visibility = View.VISIBLE
        binding.toolbarLayout.icToolbarRefresh.setImageResource(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbarLayout.icToolbarRefresh.setOnClickListener {
           onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        image = intent.extras?.getSerializable(Constants.IMAGE_ITEM_KEY) as CatImage
        MyPicassoCache.getInstance(MyApp.getInstance()).load(image.url)
            .error(R.drawable.image_not_found)
            .placeholder(R.drawable.image_loading).into(binding.catImageView)

        imageDetailsViewModel.isImageInFav(image)

        initFavoriteBtn()
    }

    private fun initFavoriteBtn() {
        btnFavorite.setOnClickListener {
            imageDetailsViewModel.changeFavoriteImageStatues(image)
        }
    }


    private fun handleImageFavoriteChangeStatus(localResult: LocalResult<Boolean>) {
        when (localResult) {


            is LocalResult.ActionAddToStorage -> {
                if (localResult.status) {
                    btnFavorite.setColorFilter(
                        ContextCompat.getColor(this, R.color.red),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )

                } else {
                    Toast.makeText(this, localResult.msg, Toast.LENGTH_LONG)
                        .show()
                }
            }
            is LocalResult.ActionRemoveFromStorage -> {
                if (localResult.status) {
                    btnFavorite.setColorFilter(
                        ContextCompat.getColor(this, R.color.darkGray),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )

                } else {
                    Toast.makeText(this, localResult.msg, Toast.LENGTH_LONG)
                        .show()
                }

            }


        }

    }

    private fun handleImageFavoriteStatus(localResult: LocalResult<Boolean>) {

        when (localResult) {


            is LocalResult.IsImageInFavorite -> {
                if (localResult.status) {
                    btnFavorite.setColorFilter(
                        ContextCompat.getColor(this, R.color.red),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                } else {
                    btnFavorite.setColorFilter(
                        ContextCompat.getColor(this, R.color.darkGray),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                }
            }


        }


    }
}