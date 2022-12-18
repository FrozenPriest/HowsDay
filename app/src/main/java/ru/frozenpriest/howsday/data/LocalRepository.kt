package ru.frozenpriest.howsday.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.frozenpriest.howsday.data.db.dao.ClassificationResultDao
import ru.frozenpriest.howsday.data.db.entity.ClassificationResultEntity
import ru.frozenpriest.howsday.data.model.ClassificationResult

class LocalRepository(
    private val dao: ClassificationResultDao
) {

    fun getResults(): Flow<List<ClassificationResult>> {
        return dao.getAll().map { results ->
            results.map {
                ClassificationResult(it.timestamp, it.face, it.result)
            }
        }
    }

    suspend fun getResultsSync(): List<ClassificationResult> = withContext(Dispatchers.IO) {
        dao.getAllSync().map {
            ClassificationResult(it.timestamp, it.face, it.result)
        }
    }

    suspend fun insertResult(result: ClassificationResult) {
        dao.insert(ClassificationResultEntity(result.timestamp, result.face, result.result))
    }

    suspend fun updateResult(result: ClassificationResult) {
        dao.update(ClassificationResultEntity(result.timestamp, result.face, result.result))
    }
}
