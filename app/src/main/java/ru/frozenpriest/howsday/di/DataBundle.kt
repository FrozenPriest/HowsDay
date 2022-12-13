package ru.frozenpriest.howsday.di

import org.koin.dsl.module
import ru.frozenpriest.howsday.commons.di.IKoinModuleBundle
import ru.frozenpriest.howsday.machine.learning.FaceDetectorProcessor

object DataBundle : IKoinModuleBundle {
    override val bootScopeModule = module {
        single { FaceDetectorProcessor() }
    }
}