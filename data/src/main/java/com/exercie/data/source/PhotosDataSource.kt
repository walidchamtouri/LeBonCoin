package com.exercie.data.source

import android.annotation.SuppressLint
import android.content.*
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.util.Log
import com.exercie.data.dagger
import com.exercie.data.model.mapper.transformToAlbumTable
import com.exercie.data.model.mapper.transformToPhotoTable
import com.exercie.data.repository.DOWNLOAD_STATUS
import com.exercie.data.repository.database.DataStorage
import com.exercie.data.source.photosdataprovider.PhotosProvider
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PhotosDataSource {

    @Inject
    lateinit var context: Context
    private val connectionChangeReceiver by lazy { onConnectionChangeReceiver() }
    private val connectionChangeFilter by lazy { IntentFilter(CONNECTIVITY_ACTION) }
    private val connectivityManager by lazy { context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager }

    @Inject
    lateinit var photosProvider: PhotosProvider
    @Inject
    lateinit var dataStorage: DataStorage
    @Inject
    lateinit var photosPreferences: SharedPreferences

    init {
        dagger.inject(this)
    }

    private fun onConnectionChangeReceiver(): BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                CONNECTIVITY_ACTION -> onConnectionStateChange()
            }
        }
    }

    fun startDownload() {
        context.registerReceiver(connectionChangeReceiver, connectionChangeFilter)
    }

    private fun onConnectionStateChange() {
        val connected =  connectivityManager.activeNetworkInfo?.isConnected ?: false

        if (connected) {
            downloadData()
        }
    }

    @SuppressLint("CheckResult")
    private fun downloadData() {

        photosProvider.getPhotos()
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { jsonPhotos ->
                            for (jsonPhoto in jsonPhotos) {
                                dataStorage.insert(transformToAlbumTable(jsonPhoto))
                                dataStorage.insert(transformToPhotoTable(jsonPhoto))
                            }
                            context.unregisterReceiver(connectionChangeReceiver)
                            photosPreferences.edit()
                                .putBoolean(DOWNLOAD_STATUS, true)
                                .apply()
                        },
                    { Log.i("TAG", "Unable to save all the photos")}
                )
    }
}