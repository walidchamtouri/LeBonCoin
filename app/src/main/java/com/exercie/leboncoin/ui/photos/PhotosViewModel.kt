package com.exercie.leboncoin.ui.photos

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.exercie.domain.Album
import com.exercie.domain.repository.DataRepository
import com.exercie.leboncoin.dagger
import javax.inject.Inject

class PhotosViewModelProvider(private val album: Album) : ViewModelProvider.NewInstanceFactory() {

    @Inject
    lateinit var dataRepository: DataRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        dagger.inject(this)
        return PhotosViewModel(dataRepository, album) as T
    }
}

class PhotosViewModel(dataRepository: DataRepository, album: Album) : ViewModel() {

    val photos = dataRepository.photos(album.id)

    init {
        Log.i("TAG", "New instance, album: $album")
    }
}