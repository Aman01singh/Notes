package com.aman.notes.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aman.notes.data.Note
import com.aman.notes.databinding.ActivityAddEditNoteBinding
import com.aman.notes.viewmodel.NotesViewModel
import kotlinx.coroutines.launch

class AddEditNoteActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOTE_ID = "extra_note_id"
        private const val DEFAULT_NOTE_ID = 0
    }

    private lateinit var binding: ActivityAddEditNoteBinding
    private val viewModel: NotesViewModel by viewModels()
    private var noteId: Int = DEFAULT_NOTE_ID
    private var currentNote: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteId = intent.getIntExtra(EXTRA_NOTE_ID, DEFAULT_NOTE_ID)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = if (noteId == DEFAULT_NOTE_ID) "Create New Note" else "Edit Note"
        binding.toolbar.setNavigationOnClickListener { finish() }

        if (noteId != DEFAULT_NOTE_ID) {
            lifecycleScope.launch {
                currentNote = viewModel.getNoteById(noteId)
                currentNote?.let {
                    binding.etTitle.setText(it.title)
                    binding.etDesc.setText(it.description)
                }
            }
        }

        binding.btnSave.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val title = binding.etTitle.text.toString().trim()
        val desc = binding.etDesc.text.toString().trim()

        if (title.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "Title and description cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        if (noteId == DEFAULT_NOTE_ID) {
            val newNote = Note(title = title, description = desc)
            viewModel.insert(newNote)
            Toast.makeText(this, "Note created!", Toast.LENGTH_SHORT).show()
        } else {
            currentNote?.let { existingNote ->
                val updatedNote = existingNote.copy(
                    id = noteId,
                    title = title,
                    description = desc
                )
                viewModel.update(updatedNote)
                Toast.makeText(this, "Note updated!", Toast.LENGTH_SHORT).show()
            }
        }
        finish()
    }
}