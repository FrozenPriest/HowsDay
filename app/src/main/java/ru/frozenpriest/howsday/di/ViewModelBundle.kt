package ru.frozenpriest.howsday.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.frozenpriest.howsday.commons.di.IKoinModuleBundle
import ru.frozenpriest.howsday.ui.main.MainViewModel

object ViewModelBundle : IKoinModuleBundle {
    override val bootScopeModule = module {
        viewModel { MainViewModel(get(), get(), get()) }
    }
}
