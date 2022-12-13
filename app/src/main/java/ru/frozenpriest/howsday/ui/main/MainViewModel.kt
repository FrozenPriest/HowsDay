package ru.frozenpriest.howsday.ui.main

import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.frozenpriest.howsday.machine.learning.FaceClassifier
import ru.frozenpriest.howsday.machine.learning.FaceDetectorProcessor

class MainViewModel(
    private val faceDetectorProcessor: FaceDetectorProcessor,
    private val faceClassifier: FaceClassifier,
) : ViewModel() {

    fun imageTaken(image: ImageProxy) {

        viewModelScope.launch {
            val result = faceDetectorProcessor.processImageProxy(image)
            val result2 = faceClassifier.classifyImage(image)
            println("$result, $result2")

            image.close()
        }
    }
}
