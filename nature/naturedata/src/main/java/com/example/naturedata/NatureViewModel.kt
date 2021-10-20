package com.example.naturedata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispatchers.IoDispatcher
import com.example.paging.data.PagingDataProvider
import com.example.repository.Datasource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class NatureViewModel @Inject constructor(
    private val datasource: Datasource.DatasourceFactory,
    private val dataProvider: PagingDataProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel(){

    private val dataDatasource
        get() = datasource.create("nature")

    val data = dataProvider.providePageData(viewModelScope, ioDispatcher){
        dataDatasource
    }
}