package edu.vt.cs5254.bucketlist.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import edu.vt.cs5254.bucketlist.data.Goal
import edu.vt.cs5254.bucketlist.data.GoalAndNotes
import edu.vt.cs5254.bucketlist.data.Note
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface GoalDao {

    @Transaction
    @Query("SELECT * FROM goal ORDER BY lastUpdated DESC")
    fun getGoalAndNotesList(): Flow<List<GoalAndNotes>>

    @Transaction
    @Query("SELECT * FROM goal WHERE id=(:id)")
    suspend fun getGoalAndNotes(id: UUID): GoalAndNotes

    @Query("DELETE FROM note WHERE goalId = :goalId")
    suspend fun deleteNotesForGoalId(goalId: UUID)

    @Update
    suspend fun updateGoal(goal: Goal)

    @Insert
    suspend fun insertNoteList(notes: List<Note>)

    @Insert
    suspend fun insertGoal(goal: Goal)

    @Delete
    suspend fun deleteGoal(goal: Goal)

    @Transaction
    suspend fun deleteGoalAndNotes(goalAndNotes: GoalAndNotes) {
        deleteNotesForGoalId(goalAndNotes.goal.id)
        deleteGoal(goalAndNotes.goal)
    }

    @Transaction
    suspend fun insertGoalAndNotes(goalAndNotes: GoalAndNotes) {
        insertGoal(goalAndNotes.goal)
        insertNoteList(goalAndNotes.notes)
    }

    @Transaction
    suspend fun updateGoalAndNotes(goalAndNotes: GoalAndNotes) {
        deleteNotesForGoalId(goalAndNotes.goal.id)
        updateGoal(goalAndNotes.goal)
        insertNoteList(goalAndNotes.notes)
    }

}