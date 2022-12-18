package ru.frozenpriest.howsday.data.model

import ru.frozenpriest.howsday.data.model.HumanState.ANGRY
import ru.frozenpriest.howsday.data.model.HumanState.HAPPY
import ru.frozenpriest.howsday.data.model.HumanState.NORMAL
import ru.frozenpriest.howsday.data.model.HumanState.SAD
import ru.frozenpriest.howsday.data.model.HumanState.UNKNOWN

enum class HumanState {
    HAPPY,
    NORMAL,
    SAD,
    ANGRY,
    UNKNOWN
}

fun HumanState.toGraphHeight(): Int {
    return when (this) {
        HAPPY -> 4
        NORMAL -> 3
        SAD -> 2
        ANGRY -> 1
        UNKNOWN -> 0
    }
}

fun HumanState.name(): String {
    return when (this) {
        HAPPY -> "Happy"
        NORMAL -> "Normal"
        SAD -> "Sad"
        ANGRY -> "Angry"
        UNKNOWN -> "Unknown"
    }
}
