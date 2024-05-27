package com.example.ott.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.ott.model.Content
import com.example.ott.repository.ContentListRepository
import com.example.ott.repository.ListDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ContentListViewModel @Inject constructor(private val repository: ContentListRepository) :
    ViewModel() {

    //Pager for making pagination call on "Content List Screen"
    val listPager = Pager(
        PagingConfig(pageSize = 12)
    ) {
        ListDataSource(repository)

    }.flow.cachedIn(viewModelScope)

    //Hold the list for the searched keyword
    private val _filterListFlow = MutableStateFlow<List<Content>>(emptyList())
    val filterListFlow: StateFlow<List<Content>> = _filterListFlow

    // Fetching all the pages data at once because searched keyword related list has to filter from all the available content list
    // Generally we make the network call to server for fetching the searched keyword data list.
    // Here contentListFlow will act as a server.
    private val _contentListFlow = MutableStateFlow<List<Content>>(emptyList())

    suspend fun getContentList() {

        viewModelScope.launch {

            val searchContentList: MutableList<Content> = mutableListOf()

            val listOne = repository.getContentList(1)
            listOne.page?.contentItems?.content?.let { searchContentList.addAll(it) }

            val listTwo = repository.getContentList(2)
            listTwo.page?.contentItems?.content?.let { searchContentList.addAll(it) }

            val listThree = repository.getContentList(3)
            listThree.page?.contentItems?.content?.let { searchContentList.addAll(it) }

            _contentListFlow.value = searchContentList
        }

    }

    fun filterSearcherData(searchText: String) {
        viewModelScope.launch {
            //Reset list when searchText is ""
            if(searchText.isEmpty()){
                _filterListFlow.value = emptyList()
            }else {
                _filterListFlow.value = _contentListFlow.value.filter { content ->
                    content.name.lowercase(Locale.ROOT)
                        .startsWith(searchText.lowercase(Locale.ROOT))
                }
            }
        }
    }


}

