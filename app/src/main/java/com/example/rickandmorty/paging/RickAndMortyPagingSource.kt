package com.example.rickandmorty.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.data.models.Details
import com.example.rickandmorty.data.remote.ApiService

private const val STARTING_PAGE_INDEX = 1


class RickAndMortyPagingSource(
    private val apiService: ApiService,
    private val query: String,
    private val status: String
) : PagingSource<Int, Details>() {

    override fun getRefreshKey(state: PagingState<Int, Details>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Details> {
        return try {
            val responseData = mutableListOf<Details>()
            val currentPage = params.key ?: STARTING_PAGE_INDEX
            val response = apiService.searchCharacters(query,currentPage,status)
            val data = response.body()?.details ?: emptyList()
            responseData.addAll(data)
            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = if (data.isEmpty()) null else currentPage.plus(1)
            )
        }catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}