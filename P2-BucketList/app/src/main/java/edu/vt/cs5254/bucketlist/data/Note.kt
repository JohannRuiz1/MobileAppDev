package edu.vt.cs5254.bucketlist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "note")
data class Note(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val text: String = "",
    val type: NoteType,
    val goalId: UUID
)

enum class NoteType {
    PROGRESS,
    PAUSED,
    COMPLETED,
}