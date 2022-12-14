package ru.frozenpriest.howsday.ui.main

import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.frozenpriest.howsday.data.LocalRepository
import ru.frozenpriest.howsday.data.model.ClassificationResult
import ru.frozenpriest.howsday.data.model.toFaceDetails
import ru.frozenpriest.howsday.machine.learning.FaceClassifier
import ru.frozenpriest.howsday.machine.learning.FaceDetectorProcessor

class MainViewModel(
    private val faceDetectorProcessor: FaceDetectorProcessor,
    private val faceClassifier: FaceClassifier,
    private val localRepository: LocalRepository
) : ViewModel() {

    val stateFlow = MutableStateFlow<MainState>(MainState.Normal)

    fun imageTaken(image: ImageProxy) {

        viewModelScope.launch(Dispatchers.IO) {
            stateFlow.update {
                MainState.Loading
            }
            val faces = faceDetectorProcessor.processImageProxy(image)

            try {
                val state = faceClassifier.classifyImage(faces.first())

                image.close()

                val classificationResult = ClassificationResult(
                    System.currentTimeMillis(),
                    faces.first().toFaceDetails(),
                    state
                )

                localRepository.insertResult(classificationResult)

                stateFlow.update {
                    MainState.Result(classificationResult)
                }
            } catch (ex: Exception) {
                stateFlow.update {
                    MainState.Normal
                }
            }
        }
    }

    fun permissionGranted(granted: Boolean) {

        when (granted) {
            true -> stateFlow.compareAndSet(MainState.NoPermission, MainState.Normal)
            else -> stateFlow.update { MainState.NoPermission }
        }
    }
}

sealed class MainState {
    object NoPermission : MainState()
    object Normal : MainState()
    object Loading : MainState()
    data class Result(val classificationResult: ClassificationResult) : MainState()
}
