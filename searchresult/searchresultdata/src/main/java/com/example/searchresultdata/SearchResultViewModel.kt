package com.example.searchresultdata

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispatchers.IoDispatcher
import com.example.navigator.Navigator
import com.example.paging.data.PagingDataProvider
import com.example.repository.Datasource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val datasource: Datasource.DatasourceFactory,
    private val dataProvider: PagingDataProvider,
    private val navigator: Navigator,
    private val savedStateHandle: SavedStateHandle,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel(), Navigator by navigator {


     val query
        get() = savedStateHandle.get<String>("search_query")!!

    private val dataDatasource
        get() = datasource.create(query)

    val data = dataProvider.providePageData(viewModelScope, ioDispatcher){
        dataDatasource
    }
}