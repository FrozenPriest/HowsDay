package ru.frozenpriest.howsday.data

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.frozenpriest.howsday.commons.di.IKoinModuleBundle
import ru.frozenpriest.howsday.data.db.LocalRoomDatabase
import ru.frozenpriest.howsday.data.db.dao.ClassificationResultDao

object DataBundle : IKoinModuleBundle {
    override val bootScopeModule = module {
        single<LocalRoomDatabase> {
            Room.databaseBuilder(androidContext(), LocalRoomDatabase::class.java, "local_room")
                .build()
        }
        single<ClassificationResultDao> { get<LocalRoomDatabase>().classificationResultDao() }
        single { LocalRepository(get()) }
    }
}
