package com.aman.notes.data

import kotlinx.coroutines.flow.Flow


class NoteRepository(private val dao: NoteDao) {

    val allNotes: Flow<List<Note>> = dao.getAllNotes()

    suspend fun insert(note: Note) = dao.insert(note)
    suspend fun update(note: Note) = dao.update(note)
    suspend fun delete(note: Note) = dao.delete(note)
    suspend fun getNoteById(id: Int): Note? = dao.getNoteById(id)

    fun searchNotes(query: String): Flow<List<Note>> = dao.searchNotes(query)
}
