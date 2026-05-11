package com.example.visitor.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.visitor.model.Stats
import com.example.visitor.ui.utilis.toAriary

@Composable
fun StatsSection(stats: Stats) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text     = "Statistiques",
            style    = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatCard(
                label    = "Tarif Total payé",
                value    = stats.totalPaid.toAriary(),
                color    = Color(0xFF3F72E2),
                modifier = Modifier.weight(1f)
            )
            StatCard(
                label    = "Tarif Minimum",
                value    = stats.minPaid.toAriary(),
                color    = Color(0xFF2EC486),
                modifier = Modifier.weight(1f)
            )
            StatCard(
                label    = "Tarif Maximum",
                value    = stats.maxPaid.toAriary(),
                color    = Color(0xFFFF6358),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun StatCard(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape    = RoundedCornerShape(12.dp),
        colors   = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.12f)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier            = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(label, fontSize = 11.sp, color = color, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = color)
        }
    }
}

@Composable
fun ChartSection(stats: Stats) {
    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape     = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text     = "Visualisation des tarifs",
                style    = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            StatsBarChart(
                stats    = stats,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )
        }
    }
}
