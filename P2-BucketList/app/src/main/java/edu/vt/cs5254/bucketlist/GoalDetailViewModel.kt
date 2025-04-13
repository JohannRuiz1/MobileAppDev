package edu.vt.cs5254.bucketlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.vt.cs5254.bucketlist.data.Goal
import edu.vt.cs5254.bucketlist.data.GoalAndNotes
import edu.vt.cs5254.bucketlist.data.Note
import edu.vt.cs5254.bucketlist.data.NoteType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class GoalDetailViewModel(goalId: UUID): ViewModel() {
    private val goalRepository = GoalRepository.get()

    private val _goalAndNotes: MutableStateFlow<GoalAndNotes?> = MutableStateFlow(null)
    val goalAndNotes: StateFlow<GoalAndNotes?> = _goalAndNotes.asStateFlow()

    init {
        viewModelScope.launch {
            _goalAndNotes.value = goalRepository.getGoalAndNotes(goalId)
            goalAndNotes.drop(1).collect { goalAndNotes ->
                goalAndNotes?.let {
                    goalRepository.updateGoalAndNotes(it)
                }
            }
        }
    }

    fun updateGoalAndNotes(onUpdate: (GoalAndNotes) -> GoalAndNotes) {
        _goalAndNotes.update { oldValue ->
            val newValue = oldValue?.let { onUpdate(it) } ?: return
            if (newValue == oldValue) { return }
            newValue.copy(goal = newValue.goal.copy(lastUpdated = Date()))
        }

    }
}

class GoalDetailViewModelFactory(
    private val goalId: UUID
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GoalDetailViewModel(goalId) as T
    }
}
