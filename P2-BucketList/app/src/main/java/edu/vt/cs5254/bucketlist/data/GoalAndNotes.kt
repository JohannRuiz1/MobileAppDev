package edu.vt.cs5254.bucketlist.data

import androidx.room.Embedded
import androidx.room.Relation

data class GoalAndNotes(
    @Embedded
    val goal: Goal,
    @Relation(
        parentColumn = "id",
        entityColumn = "goalId",

    )
    val notes: List<Note>,
) {
    val progressCount get() = notes.count { it.type == NoteType.PROGRESS }
    val isCompleted get() = notes.any { it.type == NoteType.COMPLETED }
    val isPaused get() = notes.any { it.type == NoteType.PAUSED }
    val photoFileName get() = "img_${goal.id}.jpg"
}