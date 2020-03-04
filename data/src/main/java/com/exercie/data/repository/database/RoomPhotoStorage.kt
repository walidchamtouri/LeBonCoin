package com.exercie.data.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exercie.data.model.*

@Dao
interface PhotoDao : DataStorage {

    @Query("SELECT * FROM $ALBUM_TABLE")
    override fun albums(): LiveData<List<AlbumTable>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    override fun insert(albumTable: AlbumTable)

    @Query("SELECT * FROM $PHOTO_TABLE WHERE $PHOTO_ALBUM_ID = :albumId")
    override fun photos(albumId: Long): LiveData<List<PhotoTable>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    override fun insert(photoTable: PhotoTable)
}
