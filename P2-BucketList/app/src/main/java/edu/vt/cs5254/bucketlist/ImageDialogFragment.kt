package edu.vt.cs5254.bucketlist

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.doOnLayout
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import edu.vt.cs5254.bucketlist.databinding.FragmentImageDialogBinding
import java.io.File

class ImageDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val binding = FragmentImageDialogBinding.inflate(layoutInflater)

        val args: ImageDialogFragmentArgs by navArgs()

        val photoFile = File(
                requireContext().applicationContext.filesDir,
                args.goalImageFilename
            )

        binding.root.doOnLayout { measuredView ->
            val scaledBM = getScaledBitmap(
                photoFile.path,
                measuredView.width,
                measuredView.height
            )
            binding.imageDetail.setImageBitmap(scaledBM)
        }

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .show()

    }
}