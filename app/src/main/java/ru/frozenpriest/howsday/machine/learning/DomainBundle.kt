package ru.frozenpriest.howsday.machine.learning

import org.koin.dsl.module
import ru.frozenpriest.howsday.commons.di.IKoinModuleBundle

object DomainBundle : IKoinModuleBundle {
    override val bootScopeModule = module {
        single { FaceDetectorProcessor() }
        single { FaceClassifier(get()) }
    }
}
