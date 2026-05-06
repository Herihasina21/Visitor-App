package com.example.visitor.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.visitor.viewmodel.AppNotification
import com.example.visitor.viewmodel.NotificationType
import kotlinx.coroutines.delay

/**
 * A composable that displays a notification banner at the top of the screen.
 * The notification is displayed for 3 seconds and then dismissed.
 */
@Composable
fun NotificationBanner(
    notification: AppNotification?,
    onDismiss: () -> Unit
) {
    LaunchedEffect(notification) {
        if (notification != null) {
            delay(3000)
            onDismiss()
        }
    }

    AnimatedVisibility(
        visible = notification != null,
        enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
        exit  = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
    ) {
        notification?.let { notif ->
            val (bgColor, icon, iconTint) = when (notif.type) {
                NotificationType.SUCCESS -> Triple(
                    Color(0xFF1B5E20).copy(alpha = 0.92f),
                    Icons.Default.CheckCircle,
                    Color(0xFF81C784)
                )
                NotificationType.ERROR -> Triple(
                    Color(0xFFB71C1C).copy(alpha = 0.92f),
                    Icons.Default.Warning,
                    Color(0xFFEF9A9A)
                )
                NotificationType.INFO -> Triple(
                    Color(0xFF0D47A1).copy(alpha = 0.92f),
                    Icons.Default.Info,
                    Color(0xFF90CAF9)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(bgColor)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = notif.message,
                    color = Color.White,
                    fontSize = 13.sp,
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = onDismiss) {
                    Text("×", color = Color.White, fontSize = 18.sp)
                }
            }
        }
    }
}