package com.example.visitor.ui.components

import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.visitor.model.Stats
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

@Composable
fun StatsBarChart(stats: Stats, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            BarChart(context).apply {
                description.isEnabled = false
                legend.isEnabled = false
                setDrawGridBackground(false)
                setDrawBarShadow(false)
                setFitBars(true)
                animateY(800)

                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                    granularity = 1f
                    valueFormatter = IndexAxisValueFormatter(arrayOf("Total", "Min", "Max"))
                    textColor = Color.DKGRAY
                    textSize = 12f
                }
                axisLeft.apply {
                    setDrawGridLines(true)
                    textColor = Color.DKGRAY
                    axisMinimum = 0f
                }
                axisRight.isEnabled = false
            }
        },
        update = { chart ->
            val entries = listOf(
                BarEntry(0f, stats.totalPaid.toFloat()),
                BarEntry(1f, stats.minPaid.toFloat()),
                BarEntry(2f, stats.maxPaid.toFloat())
            )
            val colors = listOf(
                Color.rgb(63, 114, 226),
                Color.rgb(46, 196, 134),
                Color.rgb(255, 99, 88)
            )
            val dataSet = BarDataSet(entries, "Stats").apply {
                this.colors = colors
                valueTextColor = Color.DKGRAY
                valueTextSize = 13f
            }
            chart.data = BarData(dataSet).apply { barWidth = 0.5f }
            chart.invalidate()
        }
    )
}