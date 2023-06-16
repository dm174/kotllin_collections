fun main() {
    val note = Note<String>()

    val noteItem = NoteItem(title = "Тестовая Заметка", content = "Это тестовая заметка")
    val noteId = note.add(noteItem)
    println("Добавлена заметка с ID: $noteId")

    val comment = Comment(content = "Это комментарий")
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

    fun add(noteItem: NoteItem<T>): Long {
        noteItem.id = ++idCounter
        notes.add(noteItem)
        return noteItem.id
    }

    fun createComment(noteId: Long, comment: Comment<T>) {
        val note = getById(noteId)
        note?.comments?.add(comment)
    }

    fun delete(noteId: Long) {
        val note = getById(noteId)
        notes.remove(note)
    }

    fun deleteComment(noteId: Long, commentId: Long) {
        val note = getById(noteId)
        note?.comments?.removeIf { it.id == commentId }
    }

    fun edit(noteId: Long, updatedNote: NoteItem<T>) {
        val note = getById(noteId)
        note?.let {
            it.title = updatedNote.title
            it.content = updatedNote.content
        }
    }

    fun editComment(noteId: Long, commentId: Long, updatedComment: Comment<T>) {
        val note = getById(noteId)
        note?.comments?.forEach { comment ->
            if (comment.id == commentId) {
                comment.content = updatedComment.content
                return@forEach
            }
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
}

data class NoteItem<T>(
    var id: Long = 0,
    var title: String,
    var content: T,
    var comments: MutableList<Comment<T>> = mutableListOf()
)

data class Comment<T>(
    var id: Long = 0,
    var content: T,
    var isDeleted: Boolean = false
)