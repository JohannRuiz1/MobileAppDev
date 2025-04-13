package edu.vt.cs5254.bucketlist

import android.content.Context
import androidx.room.Room
import edu.vt.cs5254.bucketlist.data.Goal
import edu.vt.cs5254.bucketlist.data.GoalAndNotes
import edu.vt.cs5254.bucketlist.database.GoalDatabase
import kotlinx.coroutines.flow.Flow
import java.util.UUID

private const val DATABASE_NAME = "goal-database"

class GoalRepository private constructor(context: Context) {

    private val database: GoalDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            GoalDatabase::class.java,
            DATABASE_NAME
        )
        .createFromAsset(DATABASE_NAME)
        .build()

    fun getGoalAndNotesList(): Flow<List<GoalAndNotes>> = database.goalDao().getGoalAndNotesList()

    suspend fun getGoalAndNotes(id: UUID): GoalAndNotes = database.goalDao().getGoalAndNotes(id)

    suspend fun updateGoalAndNotes(goalAndNotes: GoalAndNotes) {
        database.goalDao().updateGoalAndNotes(goalAndNotes)
    }

    suspend fun addGoalAndNotes(goalAndNotes: GoalAndNotes){
        database.goalDao().insertGoalAndNotes(goalAndNotes)
    }

    suspend fun deleteGoalAndNotes(goalAndNotes: GoalAndNotes){
        database.goalDao().deleteGoalAndNotes(goalAndNotes)
    }

    companion object {
        private var INSTANCE: GoalRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = GoalRepository(context)
            }
        }

        fun get(): GoalRepository {
            return INSTANCE ?:
            throw IllegalArgumentException("GoalRepository must be initialized")
        }
    }

}