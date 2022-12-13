package ru.frozenpriest.howsday.ui.main

import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.frozenpriest.howsday.machine.learning.FaceDetectorProcessor

class MainViewModel(
    private val faceDetectorProcessor: FaceDetectorProcessor,
) : ViewModel() {

    fun imageTaken(image: ImageProxy) {

        viewModelScope.launch {
            val result = faceDetectorProcessor.processImageProxy(image)
            println(result)
        }
    }
}
