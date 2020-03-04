package com.exercie.leboncoin.ui.albums

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.exercie.domain.repository.DataRepository
import com.exercie.leboncoin.dagger
import javax.inject.Inject

class AlbumsViewModelProvider : ViewModelProvider.NewInstanceFactory() {

    @Inject
    lateinit var dataRepository: DataRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        dagger.inject(this)
        return AlbumsViewModel(dataRepository) as T
    }
}

class AlbumsViewModel(dataRepository: DataRepository) : ViewModel() {

    val messageStatus = MutableLiveData<Int>()
    val albumsStatus = MutableLiveData<Int>()
    val albums = dataRepository.albums()

    init {
        Log.i("TAG", "New instance")

        messageStatus.value = View.VISIBLE
        albumsStatus.value = View.INVISIBLE

        albums.observeForever {
            if (albums.value!!.isNotEmpty()) {
                messageStatus.value = View.INVISIBLE
                albumsStatus.value = View.VISIBLE }
            else {
                messageStatus.value = View.VISIBLE
                albumsStatus.value = View.INVISIBLE
            }
        }
    }
}

