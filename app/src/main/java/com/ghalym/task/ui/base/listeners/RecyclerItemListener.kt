package com.ghalym.task.ui.base.listeners

import com.ghalym.task.data.model.CatImage


interface RecyclerItemListener {
    fun onItemSelected(catImage: CatImage)
}
