package edu.vt.cs5254.bucketlist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "goal")
data class Goal(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val title: String = "",
    val lastUpdated: Date = Date(),
)
