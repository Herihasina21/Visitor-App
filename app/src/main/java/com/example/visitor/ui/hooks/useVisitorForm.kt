package com.example.visitor.ui.hooks

import androidx.compose.runtime.*
import com.example.visitor.model.Visitor

data class VisitorFormState(
    val name: String = "",
    val numberOfDays: String = "",
    val dailyRate: String = "",
    val nameError: Boolean = false,
    val daysError: Boolean = false,
    val rateError: Boolean = false
) {
    val estimatedTotal: Double?
        get() {
            val days = numberOfDays.toIntOrNull() ?: 0
            val rate = dailyRate.toDoubleOrNull() ?: 0.0
            return if (days > 0 && rate > 0) days * rate else null
        }

    fun toVisitor(existingId: Long?): Visitor = Visitor(
        visitorId   = existingId,
        name        = name.trim(),
        numberOfDays = numberOfDays.toInt(),
        dailyRate   = dailyRate.toDouble()
    )
}

data class VisitorFormActions(
    val onNameChange: (String) -> Unit,
    val onDaysChange: (String) -> Unit,
    val onRateChange: (String) -> Unit,
    val validate: () -> Boolean
)

@Composable
fun useVisitorForm(initial: Visitor?): Pair<VisitorFormState, VisitorFormActions> {
    var state by remember {
        mutableStateOf(
            VisitorFormState(
                name         = initial?.name ?: "",
                numberOfDays = initial?.numberOfDays?.toString() ?: "",
                dailyRate    = initial?.dailyRate?.toString() ?: ""
            )
        )
    }

    val actions = VisitorFormActions(
        onNameChange = { state = state.copy(name = it, nameError = false) },
        onDaysChange = { state = state.copy(numberOfDays = it, daysError = false) },
        onRateChange = { state = state.copy(dailyRate = it, rateError = false) },
        validate = {
            val nameError = state.name.isBlank()
            val daysError = state.numberOfDays.toIntOrNull()?.let { it <= 0 } ?: true
            val rateError = state.dailyRate.toDoubleOrNull()?.let { it <= 0 } ?: true
            state = state.copy(nameError = nameError, daysError = daysError, rateError = rateError)
            !nameError && !daysError && !rateError
        }
    )

    return state to actions
}