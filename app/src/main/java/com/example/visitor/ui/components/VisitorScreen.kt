package com.example.visitor.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.visitor.model.Visitor
import com.example.visitor.viewmodel.VisitorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisitorScreen(viewModel: VisitorViewModel) {
    val visitors     by viewModel.visitors.collectAsState()
    val stats        by viewModel.stats.collectAsState()
    val isLoading    by viewModel.isLoading.collectAsState()
    val notification by viewModel.notification.collectAsState()

    var showAddDialog   by remember { mutableStateOf(false) }
    var visitorToEdit   by remember { mutableStateOf<Visitor?>(null) }
    var visitorToDelete by remember { mutableStateOf<Visitor?>(null) }

    // — Dialogs —
    if (showAddDialog) {
        VisitorDialog(
            visitor   = null,
            onDismiss = { showAddDialog = false },
            onConfirm = { newVisitor ->
                viewModel.addVisitor(newVisitor) { showAddDialog = false }
            }
        )
    }

    visitorToEdit?.let { v ->
        VisitorDialog(
            visitor   = v,
            onDismiss = { visitorToEdit = null },
            onConfirm = { updated ->
                viewModel.updateVisitor(updated) { visitorToEdit = null }
            }
        )
    }

    visitorToDelete?.let { v ->
        DeleteConfirmDialog(
            itemName  = v.name,
            onConfirm = {
                v.visitorId?.let { viewModel.deleteVisitor(it) }
                visitorToDelete = null
            },
            onDismiss = { visitorToDelete = null }
        )
    }

    // — Scaffold —
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Gestion des Visiteurs",
                            fontWeight = FontWeight.Bold,
                            fontSize   = 20.sp
                        )
                        Text(
                            "${visitors.size} visiteur(s) enregistré(s)",
                            fontSize = 12.sp,
                            color    = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor    = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier    = Modifier
                                .size(32.dp)
                                .padding(end = 16.dp),
                            color       = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick        = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter", tint = Color.White)
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NotificationBanner(
                notification = notification,
                onDismiss    = viewModel::clearNotification
            )

            VisitorListContent(
                visitors  = visitors,
                stats     = stats,
                isLoading = isLoading,
                onEdit    = { visitorToEdit = it },
                onDelete  = { visitorToDelete = it }
            )
        }
    }
}
