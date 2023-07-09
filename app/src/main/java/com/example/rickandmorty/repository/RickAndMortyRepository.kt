package com.example.rickandmorty.repository


import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.map
import com.example.rickandmorty.data.models.Details
import javax.inject.Inject

class RickAndMortyRepository @Inject constructor(
    private val localDataSource: RickMortyLocalDataSource,
    private val remoteDataSource: RickMortyRemoteDataSource
    ) {

    suspend fun getSearchResults(query: String, status: String): LiveData<PagingData<Details>> {
        val response = remoteDataSource.getSearchResults(query,status)
        response.value?.let {
            it.map {
                localDataSource.insertProperties(it)
            }
        }
        return response
    }
}