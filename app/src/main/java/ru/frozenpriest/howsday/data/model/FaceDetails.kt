package ru.frozenpriest.howsday.data.model

import com.google.mlkit.vision.face.Face
import kotlinx.serialization.Serializable

@Serializable
data class FaceDetails(
    val smilingProbability: Float,
    val leftEyeOpenProbability: Float,
    val rightEyeOpenProbability: Float,
    val headEulerAngleX: Float,
    val headEulerAngleY: Float,
    val headEulerAngleZ: Float
)

fun Face.toFaceDetails(): FaceDetails {

    val smilingProbability = requireNotNull(this.smilingProbability) {
        "smilingProbability is null"
    }

    val leftEyeOpenProbability = requireNotNull(this.leftEyeOpenProbability) {
        "leftEyeOpenProbability is null"
    }
    val rightEyeOpenProbability = requireNotNull(this.rightEyeOpenProbability) {
        "rightEyeOpenProbability is null"
    }

    return FaceDetails(
        smilingProbability,
        leftEyeOpenProbability,
        rightEyeOpenProbability,
        this.headEulerAngleX,
        this.headEulerAngleY,
        this.headEulerAngleZ,
    )
}
