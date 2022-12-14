package ru.frozenpriest.howsday.machine.learning

import org.koin.dsl.module
import ru.frozenpriest.howsday.commons.di.IKoinModuleBundle
import ru.frozenpriest.howsday.data.model.ClassifierModifiersProvider

object DomainBundle : IKoinModuleBundle {
    override val bootScopeModule = module {
        single { FaceDetectorProcessor() }
        single { FaceClassifier(ClassifierModifiersProvider()) }
    }
}
