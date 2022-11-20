package ru.frozenpriest.howsday.commons.di

import org.koin.core.module.Module

interface IKoinModuleBundle {

// MARK: - Properties

    val bootScopeModule: Module?
        get() = null
}
