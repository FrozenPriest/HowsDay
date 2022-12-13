package ru.frozenpriest.howsday.machine.learning

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeler
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FaceClassifier {

    private val imageLabeler: ImageLabeler

    init {

        val imageLabelingOptions = ImageLabelerOptions.Builder()
            .setConfidenceThreshold(0.7f)
            .build()

        imageLabeler = ImageLabeling.getClient(imageLabelingOptions)
    }

    @OptIn(ExperimentalGetImage::class)
    suspend fun classifyImage(imageProxy: ImageProxy) = suspendCoroutine { continuation ->
        val inputImage = InputImage.fromMediaImage(imageProxy.image!!, 0)

        imageLabeler.process(inputImage)
            .addOnCanceledListener {
                continuation.resume(null)
            }
            .addOnSuccessListener {
                continuation.resume(it)
            }
            .addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }
}
