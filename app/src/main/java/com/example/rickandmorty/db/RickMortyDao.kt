package com.example.rickandmorty.db

import androidx.paging.PagingData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.data.models.Details


@Dao
interface RickMortyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToRoomDb(rickMortyResponse: Details)

    @Query("SELECT * FROM rickandmorty_details")
     fun getFromDatabase() : List<Details>
}