package com.example.rickandmorty.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.models.Details
import com.example.rickandmorty.repository.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListViewModel @Inject constructor(private val repository: RickAndMortyRepository): ViewModel()  {

    private val taskEventChannel = Channel<TaskEvent>()
    val taskEvent = taskEventChannel.receiveAsFlow()

    private val currentQueary = MutableLiveData("")
    private val statusSelected = MutableLiveData("")

    private val charactersFlow = combine(currentQueary.asFlow(), statusSelected.asFlow()) {
        query_, status_ ->
        Pair(query_, status_)
    }.flatMapLatest { (query_, status_) ->
        repository.getSearchResults(query_, status_).asFlow()
    }

    val characters = charactersFlow.asLiveData()

    fun searchCharacters(query : String) {
        currentQueary.value = query
    }

    fun statusChoose(status : String) {
        statusSelected.value = status
    }

    fun openCharactersDetail(details: Details) = viewModelScope.launch {
        taskEventChannel.send(TaskEvent.NavigateToDetailScreen(details))
    }
    sealed class TaskEvent {
        data class NavigateToDetailScreen(val details: Details) : TaskEvent()
    }
}