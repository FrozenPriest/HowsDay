package ru.frozenpriest.howsday.machine.learning

import com.google.mlkit.vision.face.Face
import ru.frozenpriest.howsday.data.model.ClassifierModifiers
import ru.frozenpriest.howsday.data.model.ClassifierModifiersProvider
import ru.frozenpriest.howsday.data.model.HumanState

class FaceClassifier(
    private val modifiersProvider: ClassifierModifiersProvider
) {
    suspend fun classifyImage(face: Face): HumanState {

        val smilingProbability = face.smilingProbability

        return when {
            smilingProbability == null || smilingProbability < -0.01 -> HumanState.UNKNOWN
            smilingProbability <= 0.01 + modifiers.sad -> HumanState.SAD
            smilingProbability < 0.5 + modifiers.normal -> HumanState.NORMAL
            smilingProbability >= 0.7 - modifiers.happy -> HumanState.HAPPY
            else -> HumanState.ANGRY
        }
    }

    private inline val modifiers: ClassifierModifiers
        get() = modifiersProvider.modifiers
}
