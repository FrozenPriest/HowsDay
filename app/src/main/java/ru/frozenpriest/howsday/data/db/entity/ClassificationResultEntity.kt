package ru.frozenpriest.howsday.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.frozenpriest.howsday.data.model.FaceDetails
import ru.frozenpriest.howsday.data.model.HumanState

@Entity(tableName = "classification_results")
data class ClassificationResultEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "timestamp")
    val timestamp: Long,

    @ColumnInfo(name = "face")
    val face: FaceDetails,

    @ColumnInfo(name = "result")
    val result: HumanState
)
