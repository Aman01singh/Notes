1. App Description
The Notes App is a modern Android application designed for efficient note-taking. Built using Kotlin and the latest Android Architecture Components (MVVM, Room Persistence Library, Kotlin Flow, and Coroutines), the app provides a smooth, reactive user experience.

Core Features:
CRUD Operations: Create, Read, Update, and Delete notes.

Real-time List: The main screen displays a list of notes that updates instantly when notes are added, edited, or deleted, thanks to Kotlin Flow.

Search Functionality: Users can filter the note list in real-time by typing keywords that match the note's title or description.

Persistent Data: Notes are saved locally on the device using the highly efficient Room database.

Night Mode Toggle: Includes a physical switch to toggle between Light and Dark themes, with the preference being saved across app sessions.

2. Architecture Explanation
The application adheres strictly to the MVVM (Model-View-ViewModel) architectural pattern. This separation of concerns makes the application highly maintainable, testable, and robust.

A. View Layer (Activities)
NotesActivity & AddEditNoteActivity: These are the Activities responsible for displaying the UI, handling user input (clicks, search text), and observing data changes.

Function: They hold no business logic. They simply observe StateFlow objects exposed by the NotesViewModel and update the UI (like submitting a list to the NotesAdapter).

B. ViewModel Layer
NotesViewModel.kt: This component retrieves and prepares data for the UI.

Lifecycle: It is aware of the Android lifecycle but survives configuration changes.

Data Handling: It uses Kotlin Flow and the flatMapLatest operator to combine the current searchQueryFlow state with the data retrieval functions (repository.allNotes or repository.searchNotes). This ensures the list displayed to the user is always accurate and responsive to the search input.

Business Logic: It exposes simple insert, update, delete, and getNoteById functions, delegating the actual work to the Repository.

C. Model/Data Layer
This layer is responsible for data persistence and abstraction.

NoteRepository.kt: Acts as a clean API for the ViewModel. It coordinates between the DAO (Room database) and the ViewModel, exposing asynchronous methods (suspend and Flow).

NoteDao.kt: The interface used by Room to define SQL queries. Critically, its list retrieval methods (getAllNotes, searchNotes) return Flow<List<Note>> to provide real-time updates to the ViewModel.

NoteDatabase (Assumed): The actual Room databa![Screenshot_2025-12-16-14-04-44-119_com aman notes](https://github.com/user-attachments/assets/ad449536-fb61-4e4a-a327-fd423820539f)
![Screenshot_2025-12-16-14-04-37-575_com aman notes](https://github.com/user-attachments/assets/890a7465-a6ab-4b70-a44d-256c2b3db70d)
![Screenshot_2025-12-16-14-04-09-895_com aman notes](https://github.com/user-attachments/assets/bcef96fe-e5c5-4a70-bd71-36c140092719)
![Screenshot_2025-12-16-14-04-04-664_com aman notes](https://github.com/user-attachments/assets/87890dc3-4307-4a25-9ebf-2f91a2208b15)
![Screenshot_2025-12-16-14-04-02-490_com aman notes](https://github.com/user-attachments/assets/ba0e52db-f540-4ad8-aae0-dc2eada3c7d9)
![Screenshot_2025-12-16-14-03-55-456_com aman notes](https://github.com/user-attachments/assets/9953513a-e12b-4665-b49f-a9cc488dbbd5)
![Screenshot_2025-12-16-14-03-53-314_com aman notes](https://github.com/user-attachments/assets/8637b01b-c797-4aba-856a-4714eb596463)
![Screenshot_2025-12-16-14-03-35-087_com aman notes](https://github.com/user-attachments/assets/027358a9-6f66-4610-82d3-da8166f00be0)
![Screenshot_2025-12-16-14-03-31-705_com aman notes](https://github.com/user-attachments/assets/0b4c728a-9c55-4f6c-aa3f-47641c643eb2)
![Screenshot_2025-12-16-14-03-28-132_com aman notes](https://github.com/user-attachments/assets/54849362-d447-41b4-84dc-c083b664a795)
se singleton instance.

Note.kt (Assumed): The Room @Entity data class representing the structure of a single note (including the id, title, description, and createdAt).

3. Steps to Build/Run
A. Prerequisites
Android Studio: Latest version recommended.

Kotlin: The project is written entirely in Kotlin.

Basic Android Project: Ensure you have a standard Android project initialized.

