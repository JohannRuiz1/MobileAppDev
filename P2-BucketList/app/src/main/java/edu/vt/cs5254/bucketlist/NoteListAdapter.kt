package edu.vt.cs5254.bucketlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.vt.cs5254.bucketlist.data.GoalAndNotes
import edu.vt.cs5254.bucketlist.databinding.ListItemNoteBinding
import java.util.UUID


class NoteHolder(
    private val binding: ListItemNoteBinding
) : RecyclerView.ViewHolder(binding.root) {
    lateinit var boundNote: Note
        private set

    fun bind(note: Note) {
        boundNote = note
        binding.apply {
            when (note.type) {
                NoteType.PROGRESS -> {
                    note_button.setBackgroundWithContrastingText("navy")
                    note_button.text = note.text
                }
                NoteType.PAUSED -> {
                    note_button.setBackgroundWithContrastingText("silver")
                    note_button.text = NoteType.PAUSED.toString()
                }
                NoteType.COMPLETED -> {
                    note_button.setBackgroundWithContrastingText("maroon")
                    note_button.text = NoteType.COMPLETED.toString()
                }
            }
        }
    }
}

class GoalListAdapter (
   private val notes: List<Note>
): RecyclerView.Adapter<NoteHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemNoteBinding.inflate(inflater, parent, false)
        return NoteHolder(binding)
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

}