package com.example.paging

import androidx.lifecycle.ViewModel
import com.example.paging.ui.PagingUIProvider
import com.example.paging.ui.PagingUIProviderContract
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PagingUIProviderViewModel @Inject constructor(
    private val pagingUIProvider: PagingUIProvider
) : PagingUIProviderContract by pagingUIProvider, ViewModel()