package com.exercie.data.model.mapper

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.exercie.data.model.AlbumTable
import com.exercie.data.model.JsonData
import com.exercie.data.model.PhotoTable
import com.exercie.domain.Album
import com.exercie.domain.Photo

fun transformToAlbum(albumEntities: LiveData<List<AlbumTable>>): LiveData<List<Album>> =
        Transformations.map(albumEntities
        ) { it.map { AlbumTable -> transformTable(AlbumTable) } }

fun transformTable(albumTable: AlbumTable): Album =
        Album(   albumTable.id,
                albumTable.title,
                albumTable.coverUrl)

fun transformToPhoto(photoEntities: LiveData<List<PhotoTable>>): LiveData<List<Photo>> =
        Transformations.map(photoEntities) { it.map { photoTable -> transform(photoTable) } }


fun transform(photoTable: PhotoTable): Photo = Photo(photoTable.id, photoTable.url)


fun transformToAlbumTable(jsonData: JsonData): AlbumTable = AlbumTable(
        jsonData.albumId,
        jsonData.title,
        jsonData.url)

fun transformToPhotoTable(jsonData: JsonData): PhotoTable = PhotoTable(
        jsonData.id,
        jsonData.url,
        jsonData.albumId)

