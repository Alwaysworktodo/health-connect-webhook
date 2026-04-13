package com.hcwebhook.app

import kotlinx.serialization.Serializable
import java.util.Calendar
import java.util.UUID

@Serializable
data class ScheduledSync(
    val id: String = UUID.randomUUID().toString(),
    val hour: Int,
    val minute: Int,
    val label: String = "",
    val enabled: Boolean = true,
    val weekdays: List<Int> = allWeekdays()
) {
    fun getDisplayTime(): String {
        return String.format("%02d:%02d", hour, minute)
    }
    
    fun getDisplayLabel(): String {
        return if (label.isNotBlank()) label else getDisplayTime()
    }

    fun normalizedWeekdays(): List<Int> {
        val selected = if (weekdays.isEmpty()) allWeekdays() else weekdays.distinct()
        return allWeekdays().filter { it in selected }
    }

    fun getDisplayWeekdays(): String {
        val selected = normalizedWeekdays()
        return if (selected.size == allWeekdays().size) {
            "Every day"
        } else {
            selected.joinToString(", ") { weekdayShortName(it) }
        }
    }
    
    companion object {
        fun create(
            hour: Int,
            minute: Int,
            label: String = "",
            enabled: Boolean = true,
            weekdays: List<Int> = allWeekdays()
        ): ScheduledSync {
            return ScheduledSync(
                id = UUID.randomUUID().toString(),
                hour = hour,
                minute = minute,
                label = label,
                enabled = enabled,
                weekdays = weekdays
            )
        }

        fun allWeekdays(): List<Int> = listOf(
            Calendar.MONDAY,
            Calendar.TUESDAY,
            Calendar.WEDNESDAY,
            Calendar.THURSDAY,
            Calendar.FRIDAY,
            Calendar.SATURDAY,
            Calendar.SUNDAY,
        )

        fun weekdayShortName(dayOfWeek: Int): String = when (dayOfWeek) {
            Calendar.MONDAY -> "Mon"
            Calendar.TUESDAY -> "Tue"
            Calendar.WEDNESDAY -> "Wed"
            Calendar.THURSDAY -> "Thu"
            Calendar.FRIDAY -> "Fri"
            Calendar.SATURDAY -> "Sat"
            Calendar.SUNDAY -> "Sun"
            else -> "?"
        }
    }
}
