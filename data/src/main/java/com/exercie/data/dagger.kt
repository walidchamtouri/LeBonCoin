package com.exercie.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.exercie.data.repository.PHOTOS_PREFERENCES
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Component
import dagger.Module
import dagger.Provides
import com.exercie.data.repository.PhotosDataRepository
import com.exercie.data.repository.database.DATABASE_NAME
import com.exercie.data.repository.database.PhotoDatabase
import com.exercie.data.repository.database.DataStorage
import com.exercie.data.source.PhotosDataSource
import com.exercie.data.source.photosdataprovider.PhotosProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

lateinit var dagger: DataDaggerComponent

fun initDataDagger(application: Application) {
    dagger = DaggerDataDaggerComponent.builder()
            .appModule(AppModule(application))
            .photosStorageModule(PhotosStorageModule())
            .photosProviderModule(PhotosProviderModule(BuildConfig.PHOTO_SERVICE_URL))
            .build()
}

@Singleton
@Component(modules = [AppModule::class, PhotosStorageModule::class, PhotosProviderModule::class])
interface DataDaggerComponent {
    fun inject(photosDataRepository: PhotosDataRepository)
    fun inject(photosDataSource: PhotosDataSource)
}

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun getContext(): Context = application
}

@Module
class PhotosStorageModule {

    @Provides
    @Singleton
    fun getPhotosDatabase(context: Context): PhotoDatabase =
            Room.databaseBuilder(context, PhotoDatabase::class.java, DATABASE_NAME)
                    .build()

    @Provides
    @Singleton
    fun getPhotosStorage(roomDatabase: PhotoDatabase): DataStorage =
            roomDatabase.musicDao()

    @Provides
    @Singleton
    fun getPhotosSharedPreference(context: Context): SharedPreferences =
            context.getSharedPreferences(PHOTOS_PREFERENCES, Context.MODE_PRIVATE)
}

@Module
class PhotosProviderModule(private val serviceUrl: String) {

    @Provides
    @Singleton
    fun getPhotosSource(): PhotosProvider = Retrofit.Builder()
            .baseUrl(serviceUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(PhotosProvider::class.java)
}