package ru.frozenpriest.howsday.di

import ru.frozenpriest.howsday.commons.di.AbstractKoinContainer
import ru.frozenpriest.howsday.commons.di.IKoinModuleBundle
import ru.frozenpriest.howsday.data.DataBundle
import ru.frozenpriest.howsday.machine.learning.DomainBundle

object ApplicationKoinContainer : AbstractKoinContainer() {
    override val koinModuleBundles: List<IKoinModuleBundle> = listOf(
        DataBundle,
        DomainBundle,
        ViewModelBundle
    )
}
