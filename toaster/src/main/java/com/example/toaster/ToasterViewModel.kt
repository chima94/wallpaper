package com.example.toaster

import androidx.lifecycle.ViewModel
import com.crazylegend.toaster.Toaster
import com.crazylegend.toaster.ToasterContract
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ToasterViewModel @Inject constructor(
    private val toaster: Toaster,
) : ViewModel(), ToasterContract by toaster{
}