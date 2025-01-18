import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthApiService(
    private val firebaseAuth: FirebaseAuth
) {

    // Sign up with email and password
    suspend fun signUp(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Sign in with email and password
    suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Logout the current user
    fun logout() {
        firebaseAuth.signOut()
    }

    // Check if a user is logged in
    fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}
