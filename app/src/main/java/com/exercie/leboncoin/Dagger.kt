package com.exercie.leboncoin

import android.app.Application
import android.content.Context
import com.exercie.data.repository.PhotosDataRepository
import com.exercie.domain.repository.DataRepository
import dagger.Component
import dagger.Module
import dagger.Provides
import com.exercie.leboncoin.ui.albums.AlbumsAdapter
import com.exercie.leboncoin.ui.albums.AlbumsViewModelProvider
import com.exercie.leboncoin.ui.photos.PhotosAdapter
import com.exercie.leboncoin.ui.photos.PhotosViewModelProvider
import javax.inject.Singleton

lateinit var dagger: DaggerComponent

fun initAppDagger(application: Application) {
    dagger = DaggerDaggerComponent.builder()
            .appModule(AppModule(application))
            .build()
}

@Singleton
@Component(modules = [
    AppModule::class,
    PhotoRepositoryModule::class])
interface DaggerComponent {
    fun inject(albumsViewModelProvider: AlbumsViewModelProvider)
    fun inject(albumsAdapter: AlbumsAdapter)
    fun inject(photosViewModelProvider: PhotosViewModelProvider)
    fun inject(photosAdapter: PhotosAdapter)
}

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun getContext(): Context = application
}

@Module
class PhotoRepositoryModule {

    @Provides
    @Singleton
    fun getPhotoRepository(): DataRepository = PhotosDataRepository()
}
