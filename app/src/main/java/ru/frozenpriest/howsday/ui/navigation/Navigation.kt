package ru.frozenpriest.howsday.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDestination(val destination: String) {
    object Main : NavDestination("main")
    object Statistics : NavDestination("statistics")
}

sealed class BottomBarScreen(
    val destination: String,
    @StringRes val label: Int,
    val icon: ImageVector
) {

    companion object {
        val items = listOf<BottomBarScreen>()
    }
}
