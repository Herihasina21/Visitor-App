package com.example.visitor.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.visitor.model.Visitor
import com.example.visitor.ui.hooks.useVisitorForm

@Composable
fun VisitorDialog(
    visitor: Visitor? = null,
    onDismiss: () -> Unit,
    onConfirm: (Visitor) -> Unit
) {
    val (state, actions) = useVisitorForm(initial = visitor)

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape    = MaterialTheme.shapes.large,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors   = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {

                Text(
                    text     = if (visitor == null) "Ajouter un visiteur" else "Modifier le visiteur",
                    style    = MaterialTheme.typography.titleLarge,
                    color    = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                VisitorFormFields(
                    state   = state,
                    actions = actions
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick  = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Annuler")
                    }

                    Button(
                        onClick = {
                            if (actions.validate()) {
                                onConfirm(state.toVisitor(existingId = visitor?.visitorId))
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (visitor == null) "Ajouter" else "Modifier")
                    }
                }
            }
        }
    }
}
