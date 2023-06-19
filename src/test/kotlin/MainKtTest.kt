import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class NoteTest {
    @Test
    fun testNoteOperations() {
        val note = Note<String>()

        val noteItem = NoteItem(title = "Тестовая Заметка", content = "Это тестовая заметка")
        val noteId = note.add(noteItem)
        assertEquals(1, noteId) // Ожидаемый ID - 1
        println("Добавлена заметка с ID: $noteId")

        val updatedNote = NoteItem(title = "Обновленная Заметка", content = "Это обновленная заметка")
        note.edit(noteId, updatedNote)
        println("Отредактирована заметка с ID: $noteId")

        val comment = Comment(id = 1, content = "Это комментарий")
        note.createComment(noteId, comment)
        println("Добавлен комментарий к заметке с ID: $noteId")

        val notes = note.get()
        assertEquals(1, notes.size)
        // assertEquals(updatedNote, notes[0])
        println("Все заметки: $notes")

        val retrievedNote = note.getById(noteId)
        // assertEquals(updatedNote, retrievedNote)
        println("Заметка по ID ($noteId): $retrievedNote")

        val comments = note.getComments(noteId)
        assertEquals(1, comments.size)
        // assertEquals(comment, comments[0])
        println("Комментарии к заметке с ID ($noteId): $comments")

        note.deleteComment(noteId, comments[0].id,DeleteAction.MARK_DELETED)
        val updatedComments = note.getComments(noteId)
        assertEquals(0, updatedComments.size)
        println("Комментарии к заметке с ID ($noteId) после удаления комментария: $updatedComments")

        note.delete(noteId)
        assertNull(note.getById(noteId))
        val emptyNotes = note.get()
        assertEquals(0, emptyNotes.size)
        println("Заметки после удаления: $emptyNotes")

        if (updatedComments.isNotEmpty()) {
            note.restoreComment(noteId, updatedComments[0].id)
            val restoredComments = note.getComments(noteId)
            assertEquals(1, restoredComments.size)
            assertFalse(restoredComments[0].deleted)
            println("Восстановленный комментарий к заметке с ID ($noteId): ${restoredComments[0]}")
        } else {
            println("Нет комментариев для восстановления.")
        }
    }

    @Test
    fun testGetId() {
        val note = Note<String>()

        val noteItem1 = NoteItem(title = "Заметка 1", content = "Содержимое заметки 1")
        note.add(noteItem1)
        val id1 = note.getId()
        assertEquals(1, id1)
        println("ID первой заметки: $id1")

        val noteItem2 = NoteItem(title = "Заметка 2", content = "Содержимое заметки 2")
        note.add(noteItem2)
        val id2 = note.getId()
        assertEquals(2, id2)
        println("ID второй заметки: $id2")

        val noteItem3 = NoteItem(title = "Заметка 3", content = "Содержимое заметки 3")
        note.add(noteItem3)
        val id3 = note.getId()
        assertEquals(3, id3)
        println("ID третьей заметки: $id3")
    }

    @Test
    fun testDeleteComment() {
        val note = Note<String>()

        val noteItem = NoteItem(title = "Тестовая Заметка", content = "Это тестовая заметка")
        val noteId = note.add(noteItem)
        println("Добавлена заметка с ID: $noteId")

        val comment = Comment(id = 1, content = "Это комментарий")
        note.createComment(noteId, comment)
        println("Добавлен комментарий к заметке с ID: $noteId")

        val comments = note.getComments(noteId)
        assertEquals(1, comments.size)
        assertEquals(comment.content, comments[0].content)

        println("Комментарии к заметке с ID ($noteId): $comments")

        val commentId = comments.firstOrNull()?.id ?: throw IllegalStateException("Нет доступных комментариев.")
        note.deleteComment(noteId, commentId, DeleteAction.DELETE_PERMANENTLY)

        val updatedComments = note.getComments(noteId)
        assertEquals(0, updatedComments.size)
        println("Комментарии к заметке с ID ($noteId) после удаления комментария: $updatedComments")
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
        val comment = Comment(id = 1, content = "Это комментарий")
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
        val commentsAfterDelete = note.getComments(noteId)
        assertTrue(commentsAfterDelete.isEmpty())
        println("Комментарии после удаления заметки: $commentsAfterDelete")
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
        //проверкa, что список заметок после удаления пуст
        val emptyNotes = note.get()
        assertTrue(emptyNotes.isEmpty())
        println("Заметки после удаления: $emptyNotes")
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
        val comment = Comment(id = 1, content = "Это комментарий")
        note.createComment(noteId, comment)
        println("Добавлен комментарий к заметке с ID: $noteId")

        // Редактируем комментарий
        val updatedComment = Comment(id = 1, content = "Это обновленный комментарий")
        val commentsBeforeEdit = note.getComments(noteId)
        val commentId = commentsBeforeEdit.firstOrNull()?.id ?: 0L
        note.editComment(noteId, commentId, updatedComment)
        val commentsAfterEdit = note.getComments(noteId)

        // Проверяем, что комментарий успешно отредактирован
        assertEquals(updatedComment.content, commentsAfterEdit.firstOrNull()?.content)
        println("Отредактирован комментарий с ID ($commentId): ${commentsAfterEdit.firstOrNull()}")
    }

    @Test
    fun testMain1() {
        main()
    }
}
