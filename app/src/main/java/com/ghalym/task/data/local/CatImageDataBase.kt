package com.ghalym.task.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ghalym.task.data.model.CatImage


@Database(
    entities = [CatImage::class],
    version = 1
)
abstract class CatImageDataBase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "DATABASE_NAME"
    }

    abstract fun noteDao(): CatImageDao
}


