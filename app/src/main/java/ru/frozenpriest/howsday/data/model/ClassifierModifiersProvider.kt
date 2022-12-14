package ru.frozenpriest.howsday.data.model

class ClassifierModifiersProvider {

    val modifiers: ClassifierModifiers
        get() = provideModifiers()

    fun provideModifiers(): ClassifierModifiers {
        return ClassifierModifiers(0f, 0f, 0f)
    }
}
