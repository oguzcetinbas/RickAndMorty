package com.example.rickandmorty.repository


import com.example.rickandmorty.data.models.Details
import com.example.rickandmorty.db.RickMortyDao
import javax.inject.Inject

class RickMortyLocalDataSource @Inject constructor(private val rickMortyDao: RickMortyDao) {

    suspend fun insertProperties(properties: Details) {
        rickMortyDao.insertToRoomDb(properties)
    }
     fun getAllPropertiesFromRoomDb() = rickMortyDao.getFromDatabase()
}