package com.example.visitor.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.visitor.model.Stats
import com.example.visitor.model.Visitor

@Composable
fun VisitorListContent(
    visitors: List<Visitor>,
    stats: Stats?,
    isLoading: Boolean,
    onEdit: (Visitor) -> Unit,
    onDelete: (Visitor) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier       = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item { ListHeader() }

        if (visitors.isEmpty() && !isLoading) {
            item { EmptyState() }
        }

        items(visitors, key = { it.visitorId ?: 0L }) { visitor ->
            VisitorRow(
                visitor  = visitor,
                onEdit   = { onEdit(visitor) },
                onDelete = { onDelete(visitor) }
            )
        }

        stats?.let { s ->
            item {
                Spacer(modifier = Modifier.height(8.dp))
                StatsSection(stats = s)
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                ChartSection(stats = s)
            }
        }
    }
}

@Composable
private fun ListHeader() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TableHeader("Nom",     Modifier.weight(2f))
            TableHeader("Jours",   Modifier.weight(1f))
            TableHeader("Tarif/J", Modifier.weight(1.2f))
            TableHeader("Total",   Modifier.weight(1.3f))
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun TableHeader(text: String, modifier: Modifier) {
    Text(
        text       = text,
        modifier   = modifier,
        fontWeight = FontWeight.Bold,
        color      = MaterialTheme.colorScheme.onPrimaryContainer,
        fontSize   = 13.sp
    )
}

@Composable
private fun EmptyState() {
    Column(
        modifier            = Modifier
            .fillMaxWidth()
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector    = Icons.Default.Person,
            contentDescription = null,
            modifier       = Modifier.size(64.dp),
            tint           = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Aucun visiteur enregistré",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Appuyez sur + pour ajouter un visiteur",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
    }
}
