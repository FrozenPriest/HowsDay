package ru.frozenpriest.howsday.ui.statistics

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.frozenpriest.howsday.data.LocalRepository

class StatisticsViewModel(
    localRepository: LocalRepository
) : ViewModel() {

    val data = localRepository.getResults()

    private val _state = MutableStateFlow<StatState>(StatState.Circular)
    val state: StateFlow<StatState> get() = _state
}

sealed class StatState {
    object Circular : StatState()
    object Graph : StatState()
}
