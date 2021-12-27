package id.rllyhz.meapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reminder(
    val id: Long,
    val title: String,
    val description: String,
    val notifyingAt: Long,
    val createdAt: Long,
    val updatedAt: Long
) : Parcelable
