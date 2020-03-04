package com.exercie.data.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.exercie.data.dagger
import com.exercie.data.model.mapper.transformToAlbum
import com.exercie.data.model.mapper.transformToPhoto
import com.exercie.data.repository.database.DataStorage
import com.exercie.data.source.PhotosDataSource
import com.exercie.domain.Album
import com.exercie.domain.Photo
import com.exercie.domain.repository.DataRepository
import javax.inject.Inject

const val PHOTOS_PREFERENCES = "PHOTOS_PREFERENCES"
const val DOWNLOAD_STATUS = "DOWNLOAD_STATUS"

class PhotosDataRepository : DataRepository {

    @Inject
    lateinit var dataStorage: DataStorage

    @Inject
    lateinit var photosPreferences: SharedPreferences

    private val photosDataSource = PhotosDataSource()

    init {
        dagger.inject(this)

        isPhotoStored()
    }

    private fun isPhotoStored() {
        if (!photosPreferences.getBoolean(DOWNLOAD_STATUS, false)) {
            photosDataSource.startDownload()
        }
    }

    override fun albums(): LiveData<List<Album>> = transformToAlbum(dataStorage.albums())

    override fun photos(albumId: Long): LiveData<List<Photo>> = transformToPhoto(dataStorage.photos(albumId))
}