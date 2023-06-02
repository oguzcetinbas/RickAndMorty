package com.example.rickandmorty.data.remote

import com.example.rickandmorty.data.models.ListResponse
import com.example.rickandmorty.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Constants.END_POINT)
    suspend fun searchCharacters(
        @Query("name")
        searchQuery : String,
        @Query("page")
        page : Int,
        @Query("status")
        status : String
    ) : Response<ListResponse>
}