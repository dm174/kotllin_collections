fun main() {
    val note = Note<String>()

    val noteItem = NoteItem(title = "Тестовая Заметка", content = "Это тестовая заметка")
    val noteId = note.add(noteItem)
    println("Добавлена заметка с ID: $noteId")

    val comment = Comment(id = 1, content = "Это комментарий")

    note.createComment(noteId, comment)
    println("Добавлен комментарий к заметке с ID: $noteId")

    note.delete(noteId)
    println("Удалена заметка с ID: $noteId")

    val updatedNote = NoteItem(title = "Обновленная Заметка", content = "Это обновленная заметка")
    note.edit(noteId, updatedNote)
    println("Отредактирована заметка с ID: $noteId")

    val notes = note.get()
    println("Все заметки: $notes")

    val retrievedNote = note.getById(noteId)
    println("Заметка по ID ($noteId): $retrievedNote")

    val comments = note.getComments(noteId)
    println("Комментарии к заметке с ID ($noteId): $comments")
}

class Note<T> {

    private var idCounter: Long = 0
    private val notes: MutableList<NoteItem<T>> = mutableListOf()
    fun addComment(noteId: Long, comment: Comment<T>) {
        val note = getById(noteId)
        if (note != null) {
            note.comments.add(comment)
        } else {
            throw IllegalArgumentException("Заметка с ID $noteId не существует.")
        }
    }
    fun add(noteItem: NoteItem<T>): Long {
        noteItem.id = ++idCounter
        notes.add(noteItem)
        return noteItem.id
    }

    fun createComment(noteId: Long, comment: Comment<T>): Long {
        val note = getById(noteId)
        if (note != null) {
            val newComment = Comment(id = generateCommentId(), content = comment.content)
            note.comments.add(newComment)
            return noteId // Return the updated note ID
        } else {
            throw IllegalArgumentException("Заметка с ID $noteId не существует.")
        }
    }
    private fun generateCommentId(): Long {
        // Generate a unique ID for the comment (e.g., using a counter or a random number generator)
        // Replace this implementation with your own logic
        return System.currentTimeMillis()
    }
    fun delete(noteId: Long): Boolean {
        val note = getById(noteId)
        return if (note != null) {
            notes.remove(note)
            true
        } else {
            false
        }
    }

    fun deleteComment(noteId: Long, commentId: Long): Boolean {
        val note = getById(noteId)
        return if (note != null) {
            val comment = note.comments.find { it.id == commentId }
            if (comment != null) {
                comment.deleted = true
                true
            } else {
                false
            }
        } else {
            false
        }
    }

    fun edit(noteId: Long, updatedNote: NoteItem<T>): Boolean {
        val note = getById(noteId)
        return if (note != null) {
            note.title = updatedNote.title
            note.content = updatedNote.content
            true
        } else {
            false
        }
    }

    fun editComment(noteId: Long, commentId: Long, updatedComment: Comment<T>): Boolean {
        val note = getById(noteId)
        return if (note != null) {
            val comment = note.comments.find { it.id == commentId }
            if (comment != null) {
                comment.content = updatedComment.content
                true
            } else {
                false
            }
        } else {
            false
        }
    }

    fun get(): List<NoteItem<T>> {
        return notes
    }

    fun getById(noteId: Long): NoteItem<T>? {
        return notes.find { it.id == noteId }
    }

    fun getComments(noteId: Long): List<Comment<T>> {
        val note = getById(noteId)
        return note?.comments.orEmpty()
    }

    fun restoreComment(noteId: Long, commentId: Long): Boolean {
        val note = getById(noteId)
        return if (note != null) {
            val comment = note.comments.find { it.id == commentId }
            if (comment != null) {
                comment.deleted = false
                true
            } else {
                false
            }
        } else {
            false
        }
    }
    fun getId(): Long {
        return idCounter
    }

}

data class NoteItem<T>(
    var id: Long = 0,
    var title: String,
    var content: T,
    val comments: MutableList<Comment<T>> = mutableListOf()
)


data class Comment<T>(
    var id: Long ,
    var content: T,
    var deleted: Boolean = false
)
