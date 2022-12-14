package ru.frozenpriest.howsday.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.frozenpriest.howsday.data.db.dao.ClassificationResultDao
import ru.frozenpriest.howsday.data.db.entity.ClassificationResultEntity

@Database(
    entities = [
        ClassificationResultEntity::class,
    ],
    version = 1,
    exportSchema = true
)
abstract class LocalRoomDatabase : RoomDatabase() {
    abstract fun classificationResultDao(): ClassificationResultDao
}
