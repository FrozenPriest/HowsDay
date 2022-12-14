package ru.frozenpriest.howsday.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.frozenpriest.howsday.data.db.entity.ClassificationResultEntity

@Dao
interface ClassificationResultDao {

    @Query("SELECT * FROM classification_results ORDER BY timestamp DESC")
    fun getAll(): Flow<List<ClassificationResultEntity>>

    @Insert
    suspend fun insert(entity: ClassificationResultEntity)

    @Update
    suspend fun update(entity: ClassificationResultEntity)
}
