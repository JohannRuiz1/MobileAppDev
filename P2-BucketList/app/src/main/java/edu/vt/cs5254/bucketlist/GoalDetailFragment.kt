package edu.vt.cs5254.bucketlist

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import androidx.core.view.MenuProvider
import androidx.core.view.doOnLayout
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.test.espresso.util.joinToString
import edu.vt.cs5254.bucketlist.data.Goal
import edu.vt.cs5254.bucketlist.data.GoalAndNotes
import edu.vt.cs5254.bucketlist.data.Note
import edu.vt.cs5254.bucketlist.data.NoteType
import edu.vt.cs5254.bucketlist.databinding.FragmentGoalDetailBinding
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import java.io.File
import java.util.Date
import kotlin.math.min

private const val TAG = "GoalDetailFragment"

class GoalDetailFragment: Fragment() {

    private var _binding: FragmentGoalDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val args: GoalDetailFragmentArgs by navArgs()

    private val vm: GoalDetailViewModel by viewModels {
        GoalDetailViewModelFactory(args.goalId)
    }

    private lateinit var buttons: List<Button>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGoalDetailBinding.inflate(inflater, container, false)
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_goal_detail, menu)
                val captureImageIntent = takePhoto.contract.createIntent(
                    requireContext(),
                    Uri.EMPTY // NOTE: The "null" used in BNRG is obsolete now
                )
                menu.findItem(R.id.take_photo).isVisible = canResolveIntent(captureImageIntent)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.share_goal -> {
                        vm.goalAndNotes.value?.let { shareGoalAndNotes(it) }
                        true
                    }
                    R.id.take_photo -> {
                        vm.goalAndNotes.value?.let {
                            val photoFile = File(
                                requireContext().applicationContext.filesDir,
                                it.photoFileName
                            )
                            val photoUri = FileProvider.getUriForFile(
                                requireContext(),
                                "edu.vt.cs5254.bucketlist.fileprovider",
                                photoFile
                            )
                            takePhoto.launch(photoUri)
                        }
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            buttons =  listOf(
                note0Button,
                note1Button,
                note2Button,
                note3Button,
                note4Button
            )

            titleText.doOnTextChanged { text, _, _, _ ->
                vm.updateGoalAndNotes { oldGoalAndNotes ->
                    oldGoalAndNotes.copy(goal = oldGoalAndNotes.goal.copy(title = text.toString()))
                }
            }

            pausedCheckbox.setOnClickListener {
                vm.updateGoalAndNotes { oldGoalAndNotes ->
                    if(pausedCheckbox.isChecked){
                        oldGoalAndNotes.copy(notes = oldGoalAndNotes.notes + Note( type=NoteType.PAUSED, goalId = oldGoalAndNotes.goal.id ))
                    }
                    else {
                        oldGoalAndNotes.copy(notes= oldGoalAndNotes.notes.filter { it.type != NoteType.PAUSED })
                    }
                }
            }

            completedCheckbox.setOnClickListener {
                vm.updateGoalAndNotes { oldGoalAndNotes ->
                    if(completedCheckbox.isChecked){
                        oldGoalAndNotes.copy(notes = oldGoalAndNotes.notes + Note( type=NoteType.COMPLETED, goalId = oldGoalAndNotes.goal.id ))
                    }
                    else {
                        oldGoalAndNotes.copy(notes = oldGoalAndNotes.notes.filter { it.type != NoteType.COMPLETED })
                    }
                }
            }

            addProgressButton.setOnClickListener {
                findNavController().navigate(
                    GoalDetailFragmentDirections.addProgress()
                )
            }

            goalPhoto.setOnClickListener{
                vm.goalAndNotes.value?.let{
                    val photoFile =
                        File(
                            requireContext().applicationContext.filesDir,
                            it.photoFileName
                        )
                    if (photoFile.exists()) {
                        findNavController().navigate(
                            GoalDetailFragmentDirections.showImageDetail(it.photoFileName)
                        )
                    }
                }

            }

        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.goalAndNotes.collect { goalAndNotes ->
                    goalAndNotes?.let { updateView(it) }
                }


            }
        }

        setFragmentResultListener(
            ProgressDialogFragment.REQUEST_KEY
        ) { _, bundle ->
            bundle.getString(ProgressDialogFragment.BUNDLE_KEY)?.let { newNote ->
                vm.updateGoalAndNotes { oldGoalAndNotes ->
                    oldGoalAndNotes.copy(notes = oldGoalAndNotes.notes + Note( text=newNote, type=NoteType.PROGRESS, goalId = oldGoalAndNotes.goal.id ))
                }
            }

        }


    }

    private fun updateView(goalAndNotes: GoalAndNotes) {
        binding.apply {
            pausedCheckbox.isChecked = goalAndNotes.isPaused
            completedCheckbox.isChecked = goalAndNotes.isCompleted
            completedCheckbox.isEnabled = !pausedCheckbox.isChecked
            pausedCheckbox.isEnabled = !completedCheckbox.isChecked

            if(goalAndNotes.isCompleted) {
                addProgressButton.hide()
            }
            else{
                addProgressButton.show()
            }

            lastUpdatedText.text = getFormattedDate(goalAndNotes.goal.lastUpdated)

            if (titleText.text.toString() != goalAndNotes.goal.title) {
                titleText.setText(goalAndNotes.goal.title)
            }

            // goalAndNotes.notes.let { notes ->
            //     buttons.forEachIndexed { index, button ->
            //         if (index < notes.size) {
            //             val note = notes[index]
            //             when (note.type) {
            //                 NoteType.PROGRESS -> {
            //                     button.setBackgroundWithContrastingText("navy")
            //                     button.text = note.text
            //                 }
            //                 NoteType.PAUSED -> {
            //                     button.setBackgroundWithContrastingText("silver")
            //                     button.text = NoteType.PAUSED.toString()
            //                 }
            //                 NoteType.COMPLETED -> {
            //                     button.setBackgroundWithContrastingText("maroon")
            //                     button.text = NoteType.COMPLETED.toString()
            //                 }
            //             }
            //             button.visibility = View.VISIBLE
            //         } else {
            //             button.visibility = View.GONE
            //         }
            //     }
            // }
        }
        updatePhoto(goalAndNotes)
    }

    private fun updatePhoto(goalAndNotes: GoalAndNotes) {
        with(binding.goalPhoto) {
            if (tag != goalAndNotes.photoFileName) {
                val photoFile =
                    File(
                        requireContext().applicationContext.filesDir,
                        goalAndNotes.photoFileName
                    )
                if (photoFile.exists()) {
                    doOnLayout { measuredView ->
                        val scaledBM = getScaledBitmap(
                            photoFile.path,
                            measuredView.width,
                            measuredView.height
                        )
                        setImageBitmap(scaledBM)
                        tag = goalAndNotes.photoFileName
                    }
                } else {
                    setImageBitmap(null)
                    tag = null
                }
            }
        }
    }

    private val takePhoto = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { didTakePhoto: Boolean ->
        if (didTakePhoto) {
            binding.goalPhoto.tag = null
            vm.goalAndNotes.value?.let { updatePhoto(it) }
            binding.goalPhoto.isEnabled = true
        }
        else{
            binding.goalPhoto.isEnabled = false
        }
    }

    private fun shareGoalAndNotes(goalAndNotes: GoalAndNotes) {
        val reportIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, getGoalReport(goalAndNotes))
            putExtra(
                Intent.EXTRA_SUBJECT,
                goalAndNotes.goal.title
            )
        }

        val chooserIntent = Intent.createChooser(
            reportIntent,
            getString(R.string.share_goal)
        )

        startActivity(chooserIntent)
    }

    private fun getGoalReport(goalAndNotes: GoalAndNotes): String{
        val dateString = getFormattedDate(goalAndNotes.goal.lastUpdated)
        var progressString = ""
        var statusString = ""
        if (goalAndNotes.notes.isNotEmpty()){
            if (goalAndNotes.isCompleted || goalAndNotes.isPaused ){
                val status =  if (goalAndNotes.isCompleted) R.string.completed_checkbox else R.string.paused_checkbox
                statusString = getString(R.string.report_status, getString(status))
            }
            val notes = goalAndNotes.notes
                .filter { it.text.isNotBlank() }
                .joinToString(separator = "\n") {
                " * ${it.text}"
            }
            if (notes.isNotEmpty()){
                progressString = getString(R.string.report_progress, notes)
            }
        }


        return getString(
            R.string.goal_report,
            goalAndNotes.goal.title, dateString, progressString, statusString
        )
    }

    private fun getFormattedDate(date: Date): CharSequence? {
        return DateFormat.format("'Last updated' yyyy-MM-dd 'at' hh:mm:ss A", date)
    }

    private fun canResolveIntent(intent: Intent): Boolean {
        val packageManager: PackageManager = requireActivity().packageManager
        val resolvedActivity: ResolveInfo? =
            packageManager.resolveActivity(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY
            )
        return resolvedActivity != null
    }

}