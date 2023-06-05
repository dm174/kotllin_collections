import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MainKtTest {
    private lateinit var wall: Wall

    @BeforeEach
    fun setup() {
        wall = Wall()
    }

    @Test
    fun testAddPost() {
        val post = Post(0, "Test post", "John")
        val addedPost = wall.add(post)
        assertTrue(addedPost.id != 0, "Post ID should be non-zero")
    }

    @Test
    fun testUpdateExistingPost() {
        val post = Post(1, "Original post", "Alice")
        wall.add(post)

        val updatedPost = Post(1, "Updated post", "Alice")
        val isUpdated = wall.update(updatedPost)
        assertTrue(isUpdated, "Post should be updated successfully")

        val retrievedPost = wall.getPostById(1)
        assertEquals(updatedPost, retrievedPost, "Post should be updated with new content")
    }

    @Test
    fun testUpdateNonExistingPost() {
        val post = Post(1, "Original post", "Bob")
        wall.add(post)

        val updatedPost = Post(2, "Updated post", "Bob")
        val isUpdated = wall.update(updatedPost)
        assertFalse(isUpdated, "Post with non-existing ID should not be updated")
    }
}
