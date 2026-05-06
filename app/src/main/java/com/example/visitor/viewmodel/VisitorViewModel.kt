package com.example.visitor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.visitor.model.Stats
import com.example.visitor.model.Visitor
import com.example.visitor.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Type de notification affichée à l'utilisateur
enum class NotificationType { SUCCESS, ERROR, INFO }

data class AppNotification(
    val message: String,
    val type: NotificationType
)

class VisitorViewModel : ViewModel() {
    private val _visitors = MutableStateFlow<List<Visitor>>(emptyList())
    val visitors: StateFlow<List<Visitor>> = _visitors
    private val _stats = MutableStateFlow<Stats?>(null)
    val stats: StateFlow<Stats?> = _stats
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _notification = MutableStateFlow<AppNotification?>(null)
    val notification: StateFlow<AppNotification?> = _notification
    fun clearNotification() { _notification.value = null }

    private fun notify(message: String, type: NotificationType) {
        _notification.value = AppNotification(message, type)
    }

    private fun notifyFromApi(success: Boolean, message: String) {
        notify(message, if (success) NotificationType.SUCCESS else NotificationType.ERROR)
    }


    fun loadVisitors() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val visitorsResponse = RetrofitInstance.api.getVisitors()
                if (visitorsResponse.success) {
                    _visitors.value = visitorsResponse.data ?: emptyList()
                    // Info message only if the list is empty
                    if (_visitors.value.isEmpty()) {
                        notify(visitorsResponse.message, NotificationType.INFO)
                    }
                } else {
                    notify(visitorsResponse.message, NotificationType.ERROR)
                }

                val statsResponse = RetrofitInstance.api.getStats()
                if (statsResponse.success) {
                    _stats.value = statsResponse.data
                }
            } catch (e: Exception) {
                notify("Impossible de joindre le serveur : ${e.message}", NotificationType.ERROR)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Add
    fun addVisitor(visitor: Visitor, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.addVisitor(visitor)
                notifyFromApi(response.success, response.message)
                if (response.success) {
                    loadVisitors()
                    onSuccess()
                }
            } catch (e: Exception) {
                notify("Erreur réseau : ${e.message}", NotificationType.ERROR)
            }
        }
    }

    // Update
    fun updateVisitor(visitor: Visitor, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val id = visitor.visitorId ?: return@launch
                val response = RetrofitInstance.api.updateVisitor(id, visitor)
                notifyFromApi(response.success, response.message)
                if (response.success) {
                    loadVisitors()
                    onSuccess()
                }
            } catch (e: Exception) {
                notify("Erreur réseau : ${e.message}", NotificationType.ERROR)
            }
        }
    }

    // Delete
    fun deleteVisitor(id: Long) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.deleteVisitor(id)
                notifyFromApi(response.success, response.message)
                if (response.success) loadVisitors()
            } catch (e: Exception) {
                notify("Erreur réseau : ${e.message}", NotificationType.ERROR)
            }
        }
    }
}