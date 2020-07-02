package com.ghalym.task.ui.component.favouriteCatList

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ghalym.task.R
import com.ghalym.task.core.MyApp
import com.ghalym.task.data.local.LocalResult
import com.ghalym.task.data.model.CatImage
import com.ghalym.task.databinding.ActivityFavCatsImagesListBinding
import com.ghalym.task.ui.base.BaseActivity
import com.ghalym.task.ui.common.imagesAdapter.CatsImagesAdapter
import com.ghalym.task.ui.base.ViewModelFactory
import com.ghalym.task.ui.base.listeners.RecyclerItemListener
import com.ghalym.task.ui.component.singleImageDetails.ImageDetailsActivity
import com.ghalym.task.util.*
import kotlinx.android.synthetic.main.activity_cats_images_list.*
import javax.inject.Inject

class FavCatsImagesListActivity : BaseActivity(), RecyclerItemListener {
    private var isLoading: Boolean = false
    private lateinit var catsImagesAdapter: CatsImagesAdapter

    private lateinit var binding: ActivityFavCatsImagesListBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var favCatsImagesViewModel: FavCatsImagesListViewModel

    private lateinit var gridLayoutManager: GridLayoutManager

    override fun initViewBinding() {
        binding = ActivityFavCatsImagesListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initToolBar() {
        setTitle("Favorite List")
        binding.toolbarLayout.icToolbarSetting.visibility = View.GONE
        binding.toolbarLayout.icToolbarRefresh.visibility = View.VISIBLE
        binding.toolbarLayout.icToolbarRefresh.setImageResource(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbarLayout.icToolbarRefresh.setOnClickListener {
            onBackPressed()
        }
    }

    override fun injectActivity(baseActivity: BaseActivity) {
        MyApp.getInstance().getAppComponent().inject(this)
    }

    override fun initializeViewModel() {
        favCatsImagesViewModel =
            ViewModelProvider(this, viewModelFactory).get(FavCatsImagesListViewModel::class.java)

    }

    private fun handleImageList(remoteResult: LocalResult<List<CatImage>>) {
        isLoading = false
        when (remoteResult) {
            is LocalResult.Success -> {
                showLoadingProgress(false)
                bindListData(remoteResult.data!!)

            }
            is LocalResult.GeneralError -> {
                showLoadingProgress(false)
                Toast.makeText(this, remoteResult.msg, Toast.LENGTH_LONG).show()
            }
            is LocalResult.InProgress -> {
                isLoading = true
                showLoadingProgress(true)
            }

        }

    }

    override fun observeViewModel() {
        observe(favCatsImagesViewModel.getLiveData(), ::handleImageList)

    }

    override fun onResume() {
        super.onResume()
        favCatsImagesViewModel.getFavouritImagesFromStorage()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()

    }

    private fun initRecyclerView() {
        gridLayoutManager = GridLayoutManager(this, 2)
        catsImagesAdapter = CatsImagesAdapter(this)
        rvImages.layoutManager = gridLayoutManager
        rvImages.adapter = catsImagesAdapter

    }


    private fun bindListData(list: List<CatImage>) {
        if (!(list.isNullOrEmpty())) {
            catsImagesAdapter.clearItems()
            catsImagesAdapter.setItems(list)
            catsImagesAdapter.notifyDataSetChanged()
            showDataView(true)
        } else {
            showDataView(false)
        }
    }


    private fun showDataView(isVisible: Boolean) {
        rvImages.visibility = if (isVisible) View.VISIBLE else View.GONE
    }


    private fun showLoadingProgress(isVisible: Boolean) {
        progress.visibility = when (isVisible) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    override fun onItemSelected(catImage: CatImage) {
        val intent = Intent(this, ImageDetailsActivity::class.java)
        intent.putExtra(Constants.IMAGE_ITEM_KEY, catImage)
        startActivity(intent)
    }


}