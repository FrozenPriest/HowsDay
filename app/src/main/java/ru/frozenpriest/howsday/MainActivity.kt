package ru.frozenpriest.howsday

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.frozenpriest.howsday.theme.HowsDayTheme
import ru.frozenpriest.howsday.ui.main.MainScreen
import ru.frozenpriest.howsday.ui.navigation.NavDestination

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HowsDayTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopBar() }
                ) { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = NavDestination.Main.destination,
                        modifier = Modifier.padding(padding)
                    ) {
                        composable(NavDestination.Main.destination) {
                            MainScreen()
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun TopBar() {
        TopAppBar(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {

                IconButton(
                    onClick = {
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.CalendarMonth,
                        contentDescription = null
                    )
                }
            }
        }
    }
}
