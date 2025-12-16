package com.aman.notes.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aman.notes.adapter.NotesAdapter
import com.aman.notes.databinding.ActivityNotesBinding
import com.aman.notes.util.Constants
import com.aman.notes.viewmodel.NotesViewModel
import kotlinx.coroutines.launch

class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesBinding
    private val viewModel: NotesViewModel by viewModels()
    private lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecycler()

        observeViewModel()

        btnClicks()

    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.notes.collect { notesList ->
                adapter.submitList(notesList)
            }
        }
    }

    private fun btnClicks() {

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddEditNoteActivity::class.java))
        }

        binding.etSearch.doAfterTextChanged { editable ->
            viewModel.setSearchQuery(editable.toString().orEmpty())
        }

        binding.switchNightMode.isChecked =
            AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

        binding.switchNightMode.setOnCheckedChangeListener { _, isChecked ->
            val mode = if (isChecked) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            saveThemePreference(isChecked)
            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }

    private fun setUpRecycler() {
        adapter = NotesAdapter(
            onClick = { note ->
                val intent = Intent(applicationContext, AddEditNoteActivity::class.java).apply {
                    putExtra(AddEditNoteActivity.EXTRA_NOTE_ID, note.id)
                }
                startActivity(intent)
            },
            onLongClick = { note ->
                viewModel.delete(note)
                Toast.makeText(applicationContext,"Deleted successfylly", Toast.LENGTH_SHORT).show()
            }
        )

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }


    private fun saveThemePreference(isNightMode: Boolean) {
        getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(Constants.NIGHT_MODE_KEY, isNightMode)
            .apply()
    }
}