import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.tiqzy_mobile_frontend.data.model.Event

open class FavoritesViewModel : ViewModel() {
    private val _favorites = mutableStateListOf<Event>()
    open val favorites: List<Event> get() = _favorites

    open fun toggleFavorite(event: Event) {
        if (_favorites.contains(event)) {
            _favorites.remove(event)
        } else {
            _favorites.add(event)
        }
    }
}
