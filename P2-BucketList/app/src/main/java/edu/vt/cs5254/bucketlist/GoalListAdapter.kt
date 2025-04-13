package edu.vt.cs5254.bucketlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.vt.cs5254.bucketlist.data.GoalAndNotes
import edu.vt.cs5254.bucketlist.databinding.ListItemGoalBinding
import java.util.UUID


class GoalHolder(
    private val binding: ListItemGoalBinding
) : RecyclerView.ViewHolder(binding.root) {
    lateinit var boundGoalAndNotes: GoalAndNotes
        private set

    fun bind(goalAndNotes: GoalAndNotes, onGoalClicked: (goalId: UUID) -> Unit) {
        boundGoalAndNotes = goalAndNotes
        binding.apply {
            listItemTitle.text = goalAndNotes.goal.title
            listItemProgressCount.text = root.context.getString(R.string.progress_format, goalAndNotes.progressCount)
            listItemImage.visibility = View.GONE
            if(goalAndNotes.isPaused){
                listItemImage.visibility = View.VISIBLE
                listItemImage.setImageResource(R.drawable.ic_goal_paused)
            }
            if(goalAndNotes.isCompleted){
                listItemImage.visibility = View.VISIBLE
                listItemImage.setImageResource(R.drawable.ic_goal_completed)
            }

            root.setOnClickListener {
                onGoalClicked(goalAndNotes.goal.id)
            }
        }
    }
}

class GoalListAdapter (
   private val goalsAndNotes: List<GoalAndNotes>,
    private val onGoalClicked: (goalId: UUID) -> Unit
): RecyclerView.Adapter<GoalHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemGoalBinding.inflate(inflater, parent, false)
        return GoalHolder(binding)
    }

    override fun getItemCount() = goalsAndNotes.size

    override fun onBindViewHolder(holder: GoalHolder, position: Int) {
        val goalAndNotes = goalsAndNotes[position]
        holder.bind(goalAndNotes, onGoalClicked)
    }

}