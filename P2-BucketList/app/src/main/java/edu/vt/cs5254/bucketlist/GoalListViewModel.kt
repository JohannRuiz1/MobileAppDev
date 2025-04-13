package edu.vt.cs5254.bucketlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.vt.cs5254.bucketlist.data.Goal
import edu.vt.cs5254.bucketlist.data.GoalAndNotes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GoalListViewModel : ViewModel() {
    private val goalRepository = GoalRepository.get()

    private val _goalsAndNotes: MutableStateFlow<List<GoalAndNotes>> = MutableStateFlow(emptyList())
    val goalsAndNotes: StateFlow<List<GoalAndNotes>>
        get() = _goalsAndNotes.asStateFlow()

    init {
        viewModelScope.launch {
            goalRepository.getGoalAndNotesList().collect() {
                _goalsAndNotes.value = it
            }
        }
    }

    suspend fun addGoalAndNotes(goalAndNotes: GoalAndNotes){
        goalRepository.addGoalAndNotes(goalAndNotes)
    }

    fun deleteGoalAndNotes(goalAndNotes: GoalAndNotes) {
        // This is a suspend function
        viewModelScope.launch {
            goalRepository.deleteGoalAndNotes(goalAndNotes)
        }
    }

}