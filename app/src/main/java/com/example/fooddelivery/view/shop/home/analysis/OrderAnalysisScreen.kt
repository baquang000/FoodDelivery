package com.example.fooddelivery.view.shop.home.analysis

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.example.fooddelivery.R
import com.example.fooddelivery.data.viewmodel.shop.charts.ChartsOrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderAnalysisScreen(
    navController: NavController,
    chartsOrderViewModel: ChartsOrderViewModel = viewModel()
) {
    val loading by chartsOrderViewModel.loadingChartsOrder.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.analysis_order),
                        color = Color.White,
                        modifier = Modifier.padding(start = 45.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Gray
                ),
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = stringResource(
                            id = R.string.arrow
                        ),
                        tint = Color.White,
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(24.dp)
                            .clickable {
                                navController.navigateUp()
                            }
                    )
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn {
                    item {
                        LineChartScreen(chartsOrderViewModel = chartsOrderViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun LineChartScreen(chartsOrderViewModel: ChartsOrderViewModel) {
    val chartOrder by chartsOrderViewModel.chartOrder.collectAsStateWithLifecycle()
    chartOrder?.let { chart ->
        // Bước 1: Chuyển đổi `series` thành danh sách các điểm `Point`
        val pointsData: List<Point> = chart.series.mapIndexed { index, value ->
            Point(
                x = chart.labels[index].toFloat(),
                y = value.toFloat()
            ) // `x` là index, `y` là giá trị trong series
        }


        val maxY = chart.series.maxOrNull() ?: 0
        val minY = chart.series.minOrNull() ?: 0
        val steps = if (maxY - minY < 5) 5 else (maxY - minY)

        val xAxisData = AxisData.Builder()
            .axisStepSize(100.dp)
            .backgroundColor(Color.Transparent)
            .steps(pointsData.size - 1)
            .labelData { i -> (chart.labels.getOrNull(i) ?: "").toString() }
            .labelAndAxisLinePadding(20.dp)
            .axisLineColor(MaterialTheme.colorScheme.tertiary)
            .axisLabelColor(MaterialTheme.colorScheme.tertiary)
            .build()

        val yAxisData = AxisData.Builder()
            .steps(steps)
            .backgroundColor(Color.Transparent)
            .labelAndAxisLinePadding(20.dp)
            .labelData { i ->
                val yScale = (maxY - minY) / steps.toFloat() // Tính thang giá trị `y`
                "%.1f".format(minY + i * yScale)
            }
            .axisLineColor(MaterialTheme.colorScheme.tertiary)
            .axisLabelColor(MaterialTheme.colorScheme.tertiary)
            .build()

        val lineChartData = LineChartData(
            linePlotData = LinePlotData(
                lines = listOf(
                    Line(
                        dataPoints = pointsData,
                        LineStyle(
                            color = MaterialTheme.colorScheme.tertiary,
                            lineType = LineType.SmoothCurve(isDotted = false)
                        ),
                        IntersectionPoint(
                            color = MaterialTheme.colorScheme.tertiary,
                        ),
                        SelectionHighlightPoint(color = MaterialTheme.colorScheme.primary),
                        ShadowUnderLine(
                            alpha = 0.5f,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.inversePrimary,
                                    Color.Transparent
                                )
                            )
                        ),
                        SelectionHighlightPopUp()
                    )
                ),
            ),
            xAxisData = xAxisData,
            yAxisData = yAxisData,
            gridLines = GridLines(
                color = MaterialTheme.colorScheme.outlineVariant
            ),
            backgroundColor = MaterialTheme.colorScheme.surface
        )

        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            lineChartData = lineChartData
        )
    }
}
