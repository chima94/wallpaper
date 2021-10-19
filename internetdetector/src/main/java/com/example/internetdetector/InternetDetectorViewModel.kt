package com.example.internetdetector

import androidx.lifecycle.ViewModel
import com.crazylegend.internetdetector.InternetDetector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class InternetDetectorViewModel @Inject constructor(
    internetDetector: InternetDetector
): ViewModel(), Flow<Boolean> by internetDetector.state