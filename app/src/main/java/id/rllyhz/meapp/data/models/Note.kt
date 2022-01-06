package id.rllyhz.meapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val id: Long,
    val title: String,
    val content: String,
    val levelOfImportance: Int,
    val createdAt: Long,
    val updatedAt: Long
) : Parcelable