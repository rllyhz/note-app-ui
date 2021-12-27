package id.rllyhz.meapp.ui.feature.notes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import id.rllyhz.meapp.R
import id.rllyhz.meapp.adapters.NotesAdapter
import id.rllyhz.meapp.data.models.Note
import id.rllyhz.meapp.databinding.FragmentNotesBinding
import id.rllyhz.meapp.ui.adding_item.AddItemActivity
import id.rllyhz.meapp.utils.show

class NotesFragment : Fragment(), NotesAdapter.NoteItemClickCallback {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by viewModels()
    private var notesAdapter: NotesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notesAdapter = NotesAdapter()
        notesAdapter?.setOnItemClickListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        notesAdapter = null
    }

    private fun setupUI() {
        binding.apply {

            fabNotesAdd.setOnClickListener {
                Intent(requireActivity(), AddItemActivity::class.java).also {
                    it.putExtra(AddItemActivity.DESTINATION_PAGE, AddItemActivity.ADDING_NOTES_PAGE)
                    startActivity(it)
                }
            }

            swipeRefreshNotes.let {
                it.setColorSchemeColors(
                    ContextCompat.getColor(requireContext(), R.color.blue),
                )
                it.setOnRefreshListener {
                    viewModel.reloadAllNotes()
                }
            }

            rvNotes.apply {
                setHasFixedSize(true)
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                adapter = notesAdapter
            }

            viewModel.getAllNotes().observe(requireActivity()) {
                notesAdapter?.submitList(it)
                swipeRefreshNotes.show(false)
            }
        }
    }

    override fun onNoteClick(note: Note) {
        //
    }
}