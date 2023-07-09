package com.example.rickandmorty.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickandmorty.data.models.Details
import com.example.rickandmorty.utils.StringListConverter

@Database(entities = [Details::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class RickMortyPropertyDatabase : RoomDatabase() {

    abstract fun rickmortyDao(): RickMortyDao

}