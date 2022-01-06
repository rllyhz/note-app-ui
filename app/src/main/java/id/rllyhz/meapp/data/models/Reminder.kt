package id.rllyhz.meapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reminder(
    val id: Long,
    val title: String,
    val description: String,
    val isDailyReminder: Boolean,
    val notifyingAt: Long,
    val levelOfImportance: Int,
    val createdAt: Long,
    val updatedAt: Long
) : Parcelable
