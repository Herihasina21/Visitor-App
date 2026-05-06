package com.example.visitor.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.visitor.model.Visitor
import com.example.visitor.ui.utilis.daysLabel
import com.example.visitor.ui.utilis.toAriary
import com.example.visitor.ui.utilis.toAriaryShort

@Composable
fun VisitorRow(
    visitor: Visitor,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape     = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors    = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            VisitorAvatar(name = visitor.name)

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text     = visitor.name,
                modifier = Modifier.weight(2f),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                maxLines = 1
            )
            Text(
                text     = visitor.numberOfDays.daysLabel(),
                modifier = Modifier.weight(1f),
                fontSize = 13.sp,
                color    = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text     = visitor.dailyRate.toAriaryShort(),
                modifier = Modifier.weight(1.2f),
                fontSize = 13.sp,
                color    = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text       = (visitor.totalFee ?: (visitor.numberOfDays * visitor.dailyRate)).toAriary(),
                modifier   = Modifier.weight(1.3f),
                fontWeight = FontWeight.SemiBold,
                fontSize   = 13.sp,
                color      = MaterialTheme.colorScheme.primary
            )

            Row(
                modifier              = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onEdit, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector    = Icons.Default.Edit,
                        contentDescription = "Modifier",
                        tint           = MaterialTheme.colorScheme.secondary,
                        modifier       = Modifier.size(18.dp)
                    )
                }
                IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector    = Icons.Default.Delete,
                        contentDescription = "Supprimer",
                        tint           = MaterialTheme.colorScheme.error,
                        modifier       = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun VisitorAvatar(name: String) {
    Box(
        modifier        = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text       = name.firstOrNull()?.uppercase() ?: "?",
            color      = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize   = 16.sp
        )
    }
}
