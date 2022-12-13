package ru.frozenpriest.howsday.machine.learning

import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import com.google.android.gms.tasks.TaskExecutors
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FaceDetectorProcessor {

    private val detector: FaceDetector

    private val executor = TaskExecutors.MAIN_THREAD

    init {
        val faceDetectorOptions = FaceDetectorOptions.Builder()
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_NONE)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_NONE)
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .setMinFaceSize(0.4f)
            .build()

        detector = FaceDetection.getClient(faceDetectorOptions)
    }

    fun stop() {
        detector.close()
    }

    @OptIn(ExperimentalGetImage::class)
    suspend fun processImageProxy(image: ImageProxy) = suspendCoroutine { continuation ->
        detector.process(InputImage.fromMediaImage(image.image!!, image.imageInfo.rotationDegrees))
            .addOnSuccessListener(executor) { results: List<Face> ->
                continuation.resume(results)
            }
            .addOnFailureListener(executor) { e: Exception ->
                Log.e("Camera", "Error detecting face", e)
                continuation.resumeWithException(e)
            }
    }
}
