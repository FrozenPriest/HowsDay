package ru.frozenpriest.howsday.di

import ru.frozenpriest.howsday.commons.di.AbstractKoinContainer
import ru.frozenpriest.howsday.commons.di.IKoinModuleBundle

object ApplicationKoinContainer : AbstractKoinContainer() {
    override val koinModuleBundles: List<IKoinModuleBundle> = listOf(
        // Do nothing
    )
}
