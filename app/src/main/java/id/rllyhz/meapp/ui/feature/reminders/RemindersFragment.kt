package id.rllyhz.meapp.ui.feature.reminders

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import id.rllyhz.meapp.adapters.RemindersAdapter
import id.rllyhz.meapp.adapters.RemindersHorizontalAdapter
import id.rllyhz.meapp.data.models.Reminder
import id.rllyhz.meapp.databinding.FragmentRemindersBinding
import id.rllyhz.meapp.ui.adding_item.AddItemActivity

class RemindersFragment : Fragment(), RemindersAdapter.ReminderItemClickCallback,
    RemindersHorizontalAdapter.ReminderItemClickCallback {
    private var _binding: FragmentRemindersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RemindersViewModel by viewModels()
    private var pinnedRemindersAdapter: RemindersHorizontalAdapter? = null
    private var upcomingRemindersAdapter: RemindersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pinnedRemindersAdapter = RemindersHorizontalAdapter()
        pinnedRemindersAdapter?.setOnItemClickListener(this)
        upcomingRemindersAdapter = RemindersAdapter()
        upcomingRemindersAdapter?.setOnItemClickListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRemindersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        pinnedRemindersAdapter = null
        upcomingRemindersAdapter = null
    }

    private fun setupUI() {
        binding.apply {

            btnAddReminder.setOnClickListener {
                Intent(requireActivity(), AddItemActivity::class.java).also {
                    it.putExtra(
                        AddItemActivity.DESTINATION_PAGE,
                        AddItemActivity.ADDING_REMINDERS_PAGE
                    )
                    startActivity(it)
                }
            }

            rvRemindersPinned.apply {
                setHasFixedSize(true)
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = pinnedRemindersAdapter
            }

            rvRemindersUpcoming.apply {
                setHasFixedSize(true)
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                adapter = upcomingRemindersAdapter
            }

            viewModel.getAllPinnedReminders().observe(requireActivity()) {
                pinnedRemindersAdapter?.submitList(it)
            }
            viewModel.getAllUpcomingReminders().observe(requireActivity()) {
                upcomingRemindersAdapter?.submitList(it)
            }
        }
    }

    override fun onReminderClick(reminder: Reminder) {
        //
    }
}