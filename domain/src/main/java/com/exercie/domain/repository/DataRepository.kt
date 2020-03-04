package com.exercie.domain.repository

import androidx.lifecycle.LiveData
import com.exercie.domain.Album
import com.exercie.domain.Photo

interface DataRepository {
    fun albums(): LiveData<List<Album>>
    fun photos(albumId: Long): LiveData<List<Photo>>
}