package ru.frozenpriest.howsday.commons.di

import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import java.util.concurrent.atomic.AtomicBoolean

abstract class AbstractKoinContainer : IKoinModuleLoader {

// MARK: - Properties

    protected abstract val koinModuleBundles: List<IKoinModuleBundle>

    protected open val koinModuleLoaders: List<IKoinModuleLoader> = emptyList()

// MARK: - Methods

    override fun loadBootScopeModules() {
        if (_bootScopeLoaded.compareAndSet(false, true)) {

            this.koinModuleLoaders.forEach { loader ->
                loader.loadBootScopeModules()
            }

            this.koinModuleBundles.forEach { bundle ->
                bundle.bootScopeModule?.let { module ->
                    loadKoinModules(module)
                }
            }
        }
    }

    override fun unloadBootScopeModules() {
        if (_bootScopeLoaded.get()) {

            this.koinModuleBundles.reversed().forEach { bundle ->
                bundle.bootScopeModule?.let { module ->
                    unloadKoinModules(module)
                }
            }

            this.koinModuleLoaders.reversed().forEach { loader ->
                loader.unloadBootScopeModules()
            }

            _bootScopeLoaded.set(false)
        }
    }

// MARK: - Variables

    private val _bootScopeLoaded = AtomicBoolean(false)
}
