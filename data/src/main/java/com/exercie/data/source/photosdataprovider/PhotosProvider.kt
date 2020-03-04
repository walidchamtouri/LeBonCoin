package com.exercie.data.source.photosdataprovider

import com.exercie.data.model.JsonData
import io.reactivex.Observable
import retrofit2.http.GET

interface PhotosProvider {

    @GET("technical-test.json")
    fun getPhotos(): Observable<List<JsonData>>
}