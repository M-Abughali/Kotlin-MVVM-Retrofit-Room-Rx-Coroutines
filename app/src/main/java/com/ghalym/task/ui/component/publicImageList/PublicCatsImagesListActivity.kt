package com.ghalym.task.ui.component.publicImageList

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ghalym.task.R
import com.ghalym.task.core.MyApp
import com.ghalym.task.data.remote.RemoteResult
import com.ghalym.task.data.model.CatImage
import com.ghalym.task.databinding.ActivityCatsImagesListBinding
import com.ghalym.task.ui.base.BaseActivity
import com.ghalym.task.ui.base.PaginationScrollListener
import com.ghalym.task.ui.common.imagesAdapter.CatsImagesAdapter
import com.ghalym.task.ui.component.publicImageList.viewModel.PublicCatsImagesListViewModel
import com.ghalym.task.ui.base.ViewModelFactory
import com.ghalym.task.ui.base.listeners.RecyclerItemListener
import com.ghalym.task.ui.component.favouriteCatList.FavCatsImagesListActivity
import com.ghalym.task.ui.component.singleImageDetails.ImageDetailsActivity
import com.ghalym.task.util.*
import kotlinx.android.synthetic.main.activity_cats_images_list.*
import javax.inject.Inject

class PublicCatsImagesListActivity : BaseActivity(), RecyclerItemListener {
    var isLoading: Boolean = false
    private lateinit var catsImagesAdapter: CatsImagesAdapter

    private lateinit var binding: ActivityCatsImagesListBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var publicCatsImagesViewModel: PublicCatsImagesListViewModel

    lateinit var gridLayoutManager: GridLayoutManager

    override fun initViewBinding() {
        binding = ActivityCatsImagesListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initToolBar() {
        binding.toolbarLayout.icToolbarRefresh.visibility = View.VISIBLE
        binding.toolbarLayout.icToolbarSetting.visibility = View.VISIBLE

        binding.toolbarLayout.icToolbarSetting.setImageResource(R.drawable.ic_baseline_favorite_24)
        binding.toolbarLayout.icToolbarSetting.setOnClickListener{
            val intent = Intent(this, FavCatsImagesListActivity::class.java)
            startActivity(intent)
        }


        binding.toolbarLayout.icToolbarRefresh.setOnClickListener {
            clearListData()
            publicCatsImagesViewModel.reloadImagesFromServer()
        }

    }

    override fun injectActivity(baseActivity: BaseActivity) {
        MyApp.getInstance().getAppComponent().inject(this)
    }

    override fun initializeViewModel() {
        publicCatsImagesViewModel =
            ViewModelProvider(this, viewModelFactory).get(PublicCatsImagesListViewModel::class.java)

    }

    private fun handleImageList(remoteResult: RemoteResult<List<CatImage>>) {
        isLoading = false
        when (remoteResult) {
            is RemoteResult.Success -> {
                showLoadingProgress(false)
                bindListData(remoteResult.data!!)
                setTitle("Loaded Image = " + rvImages.adapter?.itemCount)
            }
            is RemoteResult.NetworkGeneralError -> {
                showLoadingProgress(false)
                Toast.makeText(this, remoteResult.msg, Toast.LENGTH_LONG).show()
            }
            is RemoteResult.InProgress -> {
                isLoading = true
                showLoadingProgress(true)
            }
            is RemoteResult.NetworkNoInternetError -> {
                showLoadingProgress(false)
                Toast.makeText(this, getString(R.string.lbl_no_error_connection), Toast.LENGTH_LONG)
                    .show()
            }


        }

    }

    override fun observeViewModel() {
        observe(publicCatsImagesViewModel.getLiveData(), ::handleImageList)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
        setRecyclerViewScrollListener()
        initSwipToRefresh()


    }

    private fun initRecyclerView() {
        gridLayoutManager = GridLayoutManager(this, 2)
        catsImagesAdapter = CatsImagesAdapter(this)
        rvImages.layoutManager = gridLayoutManager
        rvImages.adapter = catsImagesAdapter

    }

    private fun setRecyclerViewScrollListener() {
        rvImages?.addOnScrollListener(object : PaginationScrollListener(gridLayoutManager) {
            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                publicCatsImagesViewModel.getAllPublicImages()
            }
        })


    }


    private fun bindListData(list: List<CatImage>) {
        if (!(list.isNullOrEmpty())) {
            catsImagesAdapter.setItems(list)
            catsImagesAdapter.notifyDataSetChanged()
            showDataView(true)
        } else {
            showDataView(false)
        }
    }

    private fun clearListData() {
        catsImagesAdapter.clearItems()
        catsImagesAdapter.notifyDataSetChanged()

    }

    private fun showDataView(isVisible: Boolean) {
        // tvNoData.visibility = if (isVisible) View.GONE else View.VISIBLE
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


    private fun initSwipToRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            clearListData()
            publicCatsImagesViewModel.reloadImagesFromServer()

        }

    }


}