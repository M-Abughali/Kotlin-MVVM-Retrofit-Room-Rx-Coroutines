package com.ghalym.task.ui.common.imagesAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ghalym.task.R
import com.ghalym.task.databinding.RowCatItemBinding
import com.ghalym.task.data.model.CatImage
import com.ghalym.task.ui.base.listeners.RecyclerItemListener

class CatsImagesAdapter(private val recyclerItemListener: RecyclerItemListener) :
    RecyclerView.Adapter<CatImageViewHolder>() {
    private var list = ArrayList<CatImage>()

    fun clearItems() {
        list.clear()
    }

    fun setItems(list: List<CatImage>) {
        this.list.addAll(list)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatImageViewHolder {

        val rowItemBinding = DataBindingUtil.inflate<RowCatItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.row_cat_item,
            parent,
            false
        )
        return CatImageViewHolder(rowItemBinding, recyclerItemListener)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CatImageViewHolder, position: Int) {

        holder.bind(list[position])


    }
}