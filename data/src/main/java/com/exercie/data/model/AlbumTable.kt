package com.exercie.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val ALBUM_TABLE = "albums"
const val ALBUM_COLUMN_ID = "id"
const val ALBUM_COLUMN_TITLE = "title"
const val ALBUM_COLUMN_COVER_URL = "cover_url"

@Entity(tableName = ALBUM_TABLE)
data class AlbumTable(
        @PrimaryKey
        @ColumnInfo(name = ALBUM_COLUMN_ID)
        val id: Long,
        @ColumnInfo(name = ALBUM_COLUMN_TITLE)
        val title: String,
        @ColumnInfo(name = ALBUM_COLUMN_COVER_URL)
        val coverUrl: String)
