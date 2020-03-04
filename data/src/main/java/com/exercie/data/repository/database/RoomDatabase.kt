package com.exercie.data.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.exercie.data.model.AlbumTable
import com.exercie.data.model.PhotoTable

const val DATABASE_VERSION = 1
const val DATABASE_NAME = "album_database"

@Database(entities = [AlbumTable::class, PhotoTable::class], version = DATABASE_VERSION, exportSchema = false)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun musicDao(): PhotoDao
}


