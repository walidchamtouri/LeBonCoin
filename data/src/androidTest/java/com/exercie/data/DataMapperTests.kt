package com.exercie.data


import com.exercie.data.model.AlbumTable
import com.exercie.data.model.JsonData
import com.exercie.data.model.mapper.transformTable
import com.exercie.data.model.mapper.transformToAlbumTable
import com.exercie.data.model.mapper.transformToPhotoTable
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

private const val ALBUM_ID: Long = 1
private const val ALBUM_TITLE = "title"
private const val ALBUM_COVER_URL = "cover_url"

private const val PHOTO_ID: Long = 1
private const val TITLE = "title"
private const val URL = "http://test_url"
private const val THUMBNAIL_URL = "http://thumbnail_Photo"

class DataMapperTests {

    private lateinit var jsonPhoto: JsonData

    @Before
    fun init() {
        jsonPhoto = JsonData(ALBUM_ID, PHOTO_ID, TITLE, URL, THUMBNAIL_URL)
    }


    @Test
    fun transform_album_entity_to_album() {
        val albumTable = AlbumTable(ALBUM_ID, ALBUM_TITLE, ALBUM_COVER_URL)
        val album = transformTable(albumTable)

        assertThat(album.id, `is`(equalTo(albumTable.id)))
        assertThat(album.title, `is`(equalTo(albumTable.title)))
        assertThat(album.coverUrl, `is`(equalTo(albumTable.coverUrl)))
    }


    @Test
    fun transform_json_to_photo_entity() {
        val photoTable = transformToPhotoTable(jsonPhoto)

        assertThat(photoTable.id, `is`(equalTo(jsonPhoto.id)))
        assertThat(photoTable.url, `is`(equalTo(jsonPhoto.url)))
        assertThat(photoTable.albumId, `is`(equalTo(jsonPhoto.albumId)))
    }

    @Test
    fun transform_json_to_album_entity() {
        val albumTable = transformToAlbumTable(jsonPhoto)

        assertThat(albumTable.id, `is`(equalTo(jsonPhoto.albumId)))
        assertThat(albumTable.title, `is`(equalTo(jsonPhoto.title)))
        assertThat(albumTable.coverUrl, `is`(equalTo(jsonPhoto.url)))
    }
}