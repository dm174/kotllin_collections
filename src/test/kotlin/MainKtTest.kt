import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class NoteTest {
    @Test
    fun testNoteOperations() {
        val note = Note<String>()

        val noteItem = NoteItem(title = "Тестовая Заметка", content = "Это тестовая заметка")
        val noteId = note.add(noteItem)
        assertEquals(1, noteId)
        println("Добавлена заметка с ID: $noteId")

        val comment = Comment(content = "Это комментарий")
        note.createComment(noteId, comment)
        println("Добавлен комментарий к заметке с ID: $noteId")

        val updatedNote = NoteItem(title = "Обновленная Заметка", content = "Это обновленная заметка")
        note.edit(noteId, updatedNote)
        println("Отредактирована заметка с ID: $noteId")

        val notes = note.get()
        assertEquals(1, notes.size)
        assertEquals(updatedNote, notes[0])
        println("Все заметки: $notes")

        val retrievedNote = note.getById(noteId)
        assertEquals(updatedNote, retrievedNote)
        println("Заметка по ID ($noteId): $retrievedNote")

        val comments = note.getComments(noteId)
        assertEquals(1, comments.size)
        assertEquals(comment, comments[0])
        println("Комментарии к заметке с ID ($noteId): $comments")

        note.deleteComment(noteId, comments[0].id)
        val updatedComments = note.getComments(noteId)
        assertEquals(0, updatedComments.size)
        println("Комментарии к заметке с ID ($noteId) после удаления комментария: $updatedComments")

        note.delete(noteId)
        assertNull(note.getById(noteId))
        val emptyNotes = note.get()
        assertEquals(0, emptyNotes.size)
        println("Заметки после удаления: $emptyNotes")
    }
    @Test
    fun testDeleteComment() {
        val note = Note<String>()

        val noteItem = NoteItem(title = "Тестовая Заметка", content = "Это тестовая заметка")
        val noteId = note.add(noteItem)
        assertEquals(1, noteId)
        println("Добавлена заметка с ID: $noteId")

        val comment1 = Comment(content = "Это комментарий 1")
        val comment2 = Comment(content = "Это комментарий 2")
        note.createComment(noteId, comment1)
        note.createComment(noteId, comment2)
        println("Добавлены комментарии к заметке с ID: $noteId")

        val commentsBeforeDeletion = note.getComments(noteId)
        assertEquals(2, commentsBeforeDeletion.size)
        println("Комментарии к заметке с ID ($noteId) перед удалением: $commentsBeforeDeletion")

        val commentIdToDelete = commentsBeforeDeletion[0].id
        note.deleteComment(noteId, commentIdToDelete)
        val commentsAfterDeletion = note.getComments(noteId)
        assertEquals(1, commentsAfterDeletion.size)
        assertNull(commentsAfterDeletion.find { it.id == commentIdToDelete })
        println("Комментарии к заметке с ID ($noteId) после удаления комментария: $commentsAfterDeletion")
    }
    @Test
    fun testMain() {
        // Создаем экземпляр класса Note
        val note = Note<String>()

        // Добавляем заметку
        val noteItem = NoteItem(title = "Тестовая Заметка", content = "Это тестовая заметка")
        val noteId = note.add(noteItem)
        println("Добавлена заметка с ID: $noteId")

        // Создаем комментарий
        val comment = Comment(content = "Это комментарий")
        note.createComment(noteId, comment)
        println("Добавлен комментарий к заметке с ID: $noteId")

        // Удаляем заметку
        note.delete(noteId)
        assertNull(note.getById(noteId))
        println("Удалена заметка с ID: $noteId")

        // Редактируем заметку
        val updatedNote = NoteItem(title = "Обновленная Заметка", content = "Это обновленная заметка")
        note.edit(noteId, updatedNote)
        assertNull(note.getById(noteId))
        println("Отредактирована заметка с ID: $noteId")

        // Получаем все заметки
        val notes = note.get()
        assertTrue(notes.isEmpty())
        println("Все заметки: $notes")

        // Получаем заметку по ID
        val retrievedNote = note.getById(noteId)
        assertNull(retrievedNote)
        println("Заметка по ID ($noteId): $retrievedNote")

        // Получаем комментарии к заметке
        val comments = note.getComments(noteId)
        assertTrue(comments.isEmpty())
        println("Комментарии к заметке с ID ($noteId): $comments")
    }

    @Test
    fun testDelete() {
        // Создаем экземпляр класса Note
        val note = Note<String>()

        // Добавляем заметку
        val noteItem = NoteItem(title = "Тестовая Заметка", content = "Это тестовая заметка")
        val noteId = note.add(noteItem)
        println("Добавлена заметка с ID: $noteId")

        // Удаляем заметку
        note.delete(noteId)

        // Проверяем, что заметка успешно удалена
        assertNull(note.getById(noteId))
        println("Удалена заметка с ID: $noteId")
    }

    @Test
    fun testEditComment() {
        // Создаем экземпляр класса Note
        val note = Note<String>()

        // Добавляем заметку
        val noteItem = NoteItem(title = "Тестовая Заметка", content = "Это тестовая заметка")
        val noteId = note.add(noteItem)
        println("Добавлена заметка с ID: $noteId")

        // Создаем комментарий
        val comment = Comment(content = "Это комментарий")
        note.createComment(noteId, comment)
        println("Добавлен комментарий к заметке с ID: $noteId")

        // Редактируем комментарий
        val updatedComment = Comment(content = "Это обновленный комментарий")
        val commentsBeforeEdit = note.getComments(noteId)
        val commentId = commentsBeforeEdit.firstOrNull()?.id ?: 0
        note.editComment(noteId, commentId, updatedComment)
        val commentsAfterEdit = note.getComments(noteId)

        // Проверяем, что комментарий успешно отредактирован
        assertEquals(updatedComment.content, commentsAfterEdit.firstOrNull()?.content)
        println("Отредактирован комментарий с ID ($commentId): ${commentsAfterEdit.firstOrNull()}")
    }
    @Test
    fun testMain1() {

       var main1= main()

    }
}
