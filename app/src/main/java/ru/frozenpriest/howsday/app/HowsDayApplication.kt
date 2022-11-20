package ru.frozenpriest.howsday.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import ru.frozenpriest.howsday.di.ApplicationKoinContainer

class HowsDayApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@HowsDayApplication)
        }
        ApplicationKoinContainer.loadBootScopeModules()
    }
}
