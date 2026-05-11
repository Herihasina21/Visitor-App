package com.example.visitor.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.visitor.model.Stats
import com.example.visitor.model.Visitor

private const val PAGE_SIZE = 5

@Composable
fun VisitorListContent(
    visitors: List<Visitor>,
    stats: Stats?,
    isLoading: Boolean,
    onEdit: (Visitor) -> Unit,
    onDelete: (Visitor) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentPage by remember { mutableStateOf(0) }

    // Reset page quand la liste change
    LaunchedEffect(visitors.size) {
        if (currentPage > 0 && currentPage * PAGE_SIZE >= visitors.size) {
            currentPage = maxOf(0, (visitors.size - 1) / PAGE_SIZE)
        }
    }

    val totalPages = if (visitors.isEmpty()) 1 else (visitors.size + PAGE_SIZE - 1) / PAGE_SIZE
    val pagedVisitors = visitors.drop(currentPage * PAGE_SIZE).take(PAGE_SIZE)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item { ListHeader() }

        if (visitors.isEmpty() && !isLoading) {
            item { EmptyState() }
        }

        items(pagedVisitors, key = { it.visitorId ?: 0L }) { visitor ->
            VisitorRow(
                visitor  = visitor,
                onEdit   = { onEdit(visitor) },
                onDelete = { onDelete(visitor) }
            )
        }

        // Pagination
        if (visitors.isNotEmpty()) {
            item {
                PaginationBar(
                    currentPage = currentPage,
                    totalPages  = totalPages,
                    totalItems  = visitors.size,
                    onPrevious  = { if (currentPage > 0) currentPage-- },
                    onNext      = { if (currentPage < totalPages - 1) currentPage++ }
                )
            }
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
private fun PaginationBar(
    currentPage: Int,
    totalPages: Int,
    totalItems: Int,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    val start = currentPage * PAGE_SIZE + 1
    val end   = minOf(start + PAGE_SIZE - 1, totalItems)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape  = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Bouton Previous
            IconButton(
                onClick  = onPrevious,
                enabled  = currentPage > 0,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector        = Icons.Default.ArrowBack,
                    contentDescription = "Page précédente",
                    tint = if (currentPage > 0)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Info centre
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text       = "Page ${currentPage + 1} / $totalPages",
                    fontSize   = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text     = "$start–$end sur $totalItems visiteur(s)",
                    fontSize = 11.sp,
                    color    = MaterialTheme.colorScheme.outline
                )
            }

            // Bouton Next
            IconButton(
                onClick  = onNext,
                enabled  = currentPage < totalPages - 1,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector        = Icons.Default.ArrowForward,
                    contentDescription = "Page suivante",
                    tint = if (currentPage < totalPages - 1)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(20.dp)
                )
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
            TableHeader("Nom",         Modifier.weight(2f))
            TableHeader("Jours",       Modifier.weight(1f))
            TableHeader("Tarif/J",     Modifier.weight(1.2f))
            TableHeader("Tarif Total", Modifier.weight(1.3f))
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector        = Icons.Default.Person,
            contentDescription = null,
            modifier           = Modifier.size(64.dp),
            tint               = MaterialTheme.colorScheme.outline
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