package com.example.rickandmorty.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.rickandmorty.data.remote.ApiService
import com.example.rickandmorty.paging.RickAndMortyPagingSource
import javax.inject.Inject

class RickAndMortyRepository @Inject constructor(val apiService: ApiService) {

    fun getSearchResults(query : String, status : String) = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {RickAndMortyPagingSource(apiService,query,status)}
    ).liveData
}