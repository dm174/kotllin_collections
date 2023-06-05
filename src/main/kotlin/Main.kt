class Wall {
    private var lastId = 0
    private val posts = mutableListOf<Post>()

    fun generateUniqueId(): Int {
        return ++lastId
    }

    fun add(post: Post): Post {
        val newPost = post.copy(id = generateUniqueId())
        posts.add(newPost)
        return newPost
    }

    fun update(updatedPost: Post): Boolean {
        val index = posts.indexOfFirst { it.id == updatedPost.id }
        if (index != -1) {
            posts[index] = updatedPost
            return true
        }
        return false
    }

    fun getPostById(id: Int): Post? {
        return posts.find { it.id == id }
    }
}


data class Post(val id: Int, val text: String, val author: String)

// Пример использования
fun main() {
    val wall = Wall()

    val post1 = Post(0, "Test post 1", "John")
    val addedPost1 = wall.add(post1)
    println("Added post 1: $addedPost1")

    val post2 = Post(0, "Test post 2", "Alice")
    val addedPost2 = wall.add(post2)
    println("Added post 2: $addedPost2")

    val updatedPost = Post(addedPost1.id, "Updated post 1", "John")
    val isUpdated = wall.update(updatedPost)
    println("Post 1 updated: $isUpdated")

    val retrievedPost = wall.getPostById(addedPost1.id)
    println("Retrieved post: $retrievedPost")
}
