package edu.vt.cs5254.bucketlist

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import edu.vt.cs5254.bucketlist.databinding.FragmentProgressDialogBinding

class ProgressDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val binding = FragmentProgressDialogBinding.inflate(layoutInflater)

        val positiveListener = DialogInterface.OnClickListener { _, _ ->
            val resultText = binding.progressText.text.toString()
            setFragmentResult(
                REQUEST_KEY,
                bundleOf(BUNDLE_KEY to resultText)
            )
        }

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setTitle(R.string.progress_dialog_title) // "Add Progress"
            .setPositiveButton(R.string.progress_dialog_positive, positiveListener) // "Add"
            .setNegativeButton(R.string.progress_dialog_negative, null) // "Cancel"
            .show()
    }

    companion object {
        const val REQUEST_KEY = "REQUEST_KEY"
        const val BUNDLE_KEY = "BUNDLE_KEY"
    }

}