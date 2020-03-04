package com.exercie.leboncoin.entity.mapper
import com.exercie.domain.Album
import com.exercie.leboncoin.entity.ParcelableAlbum


fun transform(album: Album): ParcelableAlbum = ParcelableAlbum(album.id, album.title, album.coverUrl)

fun transform(parcelableAlbum: ParcelableAlbum): Album =
        Album(parcelableAlbum.id, parcelableAlbum.title, parcelableAlbum.coverUrl)