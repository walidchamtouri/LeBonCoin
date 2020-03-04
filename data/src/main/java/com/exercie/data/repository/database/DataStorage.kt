package com.exercie.data.repository.database

import androidx.lifecycle.LiveData
import com.exercie.data.model.AlbumTable
import com.exercie.data.model.PhotoTable

interface DataStorage {
    fun albums(): LiveData<List<AlbumTable>>
    fun insert(albumTable: AlbumTable)
    fun photos(albumId: Long): LiveData<List<PhotoTable>>
    fun insert(photoTable: PhotoTable)
}