package ru.frozenpriest.howsday.commons.di

interface IKoinModuleLoader {

// MARK: - Methods

    fun loadBootScopeModules()

    fun unloadBootScopeModules()
}
