package ru.frozenpriest.howsday.machine.learning

import com.google.mlkit.vision.face.Face
import ru.frozenpriest.howsday.data.LocalRepository
import ru.frozenpriest.howsday.data.model.HumanState

class FaceClassifier(
    private val localRepository: LocalRepository
) {
    suspend fun classifyImage(face: Face): HumanState {
        val smilingProbability = face.smilingProbability

        val localData = localRepository.getResultsSync()

        return localData.find {
            it.face.smilingProbability == face.smilingProbability
        }?.result
            ?: when {
                smilingProbability == null || smilingProbability < -0.01 -> HumanState.UNKNOWN
                smilingProbability <= 0.01 -> HumanState.SAD
                smilingProbability < 0.5 -> HumanState.NORMAL
                smilingProbability >= 0.7 -> HumanState.HAPPY
                else -> HumanState.ANGRY
            }
    }
}
