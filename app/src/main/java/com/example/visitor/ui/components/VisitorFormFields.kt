package com.example.visitor.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.visitor.ui.hooks.VisitorFormActions
import com.example.visitor.ui.hooks.VisitorFormState
import com.example.visitor.ui.utilis.toAriary

@Composable
fun VisitorFormFields(
    state: VisitorFormState,
    actions: VisitorFormActions,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        OutlinedTextField(
            value       = state.name,
            onValueChange = actions.onNameChange,
            label       = { Text("Nom du visiteur") },
            isError     = state.nameError,
            supportingText = { if (state.nameError) Text("Le nom est requis") },
            modifier    = Modifier.fillMaxWidth(),
            singleLine  = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value       = state.numberOfDays,
            onValueChange = actions.onDaysChange,
            label       = { Text("Nombre de jours") },
            isError     = state.daysError,
            supportingText = { if (state.daysError) Text("Entrez un nombre valide") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier    = Modifier.fillMaxWidth(),
            singleLine  = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value       = state.dailyRate,
            onValueChange = actions.onRateChange,
            label       = { Text("Tarif journalier (Ar)") },
            isError     = state.rateError,
            supportingText = { if (state.rateError) Text("Entrez un tarif valide") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier    = Modifier.fillMaxWidth(),
            singleLine  = true
        )

        state.estimatedTotal?.let { total ->
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                colors   = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text     = "Total estimé : ${total.toAriary()}",
                    style    = MaterialTheme.typography.bodyMedium,
                    color    = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}
