package com.example.detailsdata

import com.example.network.response.CommonPic
import com.example.util.Queue
import com.example.util.StateMessage

data class DetailsDataState(
    val isLoading: Boolean = false,
    val commonPic: CommonPic? = null,
    val queue: Queue<StateMessage> = Queue(mutableListOf())
)