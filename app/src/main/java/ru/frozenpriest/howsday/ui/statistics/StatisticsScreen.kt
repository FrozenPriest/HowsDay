package ru.frozenpriest.howsday.ui.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.line.LineChart
import com.himanshoe.charty.line.model.LineData
import org.koin.androidx.compose.koinViewModel
import ru.frozenpriest.howsday.data.model.ClassificationResult
import ru.frozenpriest.howsday.data.model.name
import ru.frozenpriest.howsday.data.model.toGraphHeight
import java.util.Calendar
import java.util.Date

@Composable
fun StatisticsScreen() {
    val viewModel: StatisticsViewModel = koinViewModel()

    val screenState by viewModel.state.collectAsState()

    val data by viewModel.data.collectAsState(initial = emptyList())

    val lineData by remember {
        derivedStateOf {
            data.map { it.timestamp to it.result }
                .map {
                    val (time, result) = it

                    val dayMonth = time.toDayMonth()

                    LineData(
                        xValue = dayMonth,
                        yValue = result.toGraphHeight().toFloat()
                    )
                }
                .distinctBy { it.xValue }
                .sortedBy { it.xValue.toString() }
        }
    }

    if (data.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .padding(32.dp),
                color = Color.Red,
                lineData = lineData
            )
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(data) { item: ClassificationResult ->
                    Row(Modifier.fillMaxWidth()) {
                        Text(modifier = Modifier.weight(1f), text = item.timestamp.toDayMonth())
                        Text(modifier = Modifier.weight(1f), text = item.result.name())
                    }
                }
            }
        }
    }
}

private fun Long.toDayMonth(): String {
    val calendar = Calendar.getInstance()
    calendar.time = Date(this)

    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.get(Calendar.MONTH)

    return "$day/$month"
}
