import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tiqzy_mobile_frontend.R
import com.example.tiqzy_mobile_frontend.ui.navigation.Screen

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        Screen.Home,
        Screen.Tickets,
        Screen.Favorites,
        Screen.Profile
    )

    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = when (screen) {
                                Screen.Home -> R.drawable.home_icon
                                Screen.Tickets -> R.drawable.ticket
                                Screen.Favorites -> R.drawable.heart
                                Screen.Profile -> R.drawable.profile
                                else -> R.drawable.help
                            }
                        ),
                        contentDescription = screen.title,
                        modifier = Modifier.size(26.dp)
                    )
                },
                label = { Text(text = screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        println("Navigating to ${screen.title}")
                        navController.navigate(screen.route) {
                            println("navigating.... ${screen.route}")
                            popUpTo("home") {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
