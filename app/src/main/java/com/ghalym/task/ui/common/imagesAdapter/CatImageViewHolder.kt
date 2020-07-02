package com.ghalym.task.ui.common.imagesAdapter

import androidx.recyclerview.widget.RecyclerView
import com.ghalym.task.R
import com.ghalym.task.core.MyApp
import com.ghalym.task.databinding.RowCatItemBinding
import com.ghalym.task.data.model.CatImage
import com.ghalym.task.ui.base.listeners.RecyclerItemListener
import com.ghalym.task.util.MyPicassoCache

class CatImageViewHolder(
    private val rowItemBinding: RowCatItemBinding,
    private val recyclerItemListener: RecyclerItemListener?
) :
RecyclerView.ViewHolder(rowItemBinding.root) {
fun bind(catImage: CatImage) {

        MyPicassoCache.getInstance(MyApp.getInstance()).load(catImage.url)
            .error(R.drawable.image_not_found)
            .placeholder(R.drawable.image_loading).into(rowItemBinding.catImageView)
        rowItemBinding.catImageItem = catImage
        rowItemBinding.root.setOnClickListener {
            recyclerItemListener?.onItemSelected(catImage)
        }
    }

}