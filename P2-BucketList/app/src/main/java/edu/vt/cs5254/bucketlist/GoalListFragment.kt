package edu.vt.cs5254.bucketlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.vt.cs5254.bucketlist.data.Goal
import edu.vt.cs5254.bucketlist.data.GoalAndNotes
import edu.vt.cs5254.bucketlist.databinding.FragmentGoalListBinding
import kotlinx.coroutines.launch

class GoalListFragment: Fragment() {

    private var _binding: FragmentGoalListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }
    private val vm: GoalListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGoalListBinding.inflate(inflater, container, false)

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_goal_list, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.new_goal -> {
                        showNewGoal()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)

        binding.goalRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getItemTouchHelper(): ItemTouchHelper {

        return ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val goalHolder = viewHolder as GoalHolder
                vm.deleteGoalAndNotes(goalHolder.boundGoalAndNotes)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.goalsAndNotes.collect { goalsAndNotes ->
                    if(goalsAndNotes.isEmpty()){
                        binding.noGoalButton.visibility = View.VISIBLE
                        binding.noGoalText.visibility = View.VISIBLE
                    }
                    else{
                        binding.noGoalButton.visibility = View.GONE
                        binding.noGoalText.visibility = View.GONE
                    }

                    binding.goalRecyclerView.adapter = GoalListAdapter(goalsAndNotes) { goalId ->
                        findNavController().navigate(
                            GoalListFragmentDirections.showGoalDetail(goalId)
                        )
                    }
                    getItemTouchHelper().attachToRecyclerView(binding.goalRecyclerView)
                }
            }
        }

        binding.noGoalButton.setOnClickListener {
            showNewGoal()
        }


    }

    private fun showNewGoal() {
        viewLifecycleOwner.lifecycleScope.launch {
            GoalAndNotes(
                goal = Goal(),
                notes = emptyList(),
            ).let {
                vm.addGoalAndNotes(it)
                findNavController().navigate(
                    GoalListFragmentDirections.showGoalDetail(it.goal.id)
                )
            }
        }
    }
}