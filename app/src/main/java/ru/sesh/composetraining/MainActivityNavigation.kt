package ru.sesh.composetraining

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.sesh.composetraining.lore.HomeViewModel

/**
 * Метод navigate не стоит вызывать в Composable коде, чтобы избежать выполнения навигации при перезапусках Composable функций.
 * Поэтому для вызовов используйте лямды-колбэки.
 *
 * NavBackStackEntry - это сущность навигации.
 * Когда мы с помощью NavController открываем новый экран, для этого экрана создается NavBackStackEntry и помещается в BackStack.
 * Для каждого экрана эта сущность своя. И она же для него является ViewModelStoreOwner.
 * Поэтому каждый экран получает свои экземпляры моделей из своего хранилища.
 * А когда экран закрывается, то NavBackStackEntry убирается из BackStack, а все ее вьюмодели уничтожаются.
 *
 * Т.е. при использовании навигации, каждому экрану предоставляется свое хранилище вьюмоделей, которое привязано к времени жизни этого экрана.
 */
class MainActivityNavigation : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(modifier = Modifier.fillMaxSize()) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.weight(1f)
                ) {
                    composable("home") {
                        HomeScreen { navController.navigate("orders") }
                    }
                    composable("orders") { OrdersScreen() }
                    composable("users") { UsersScreen() }
                    composable(
                        route = "user/{id}",
                        arguments = listOf(navArgument("id") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("id").orEmpty()
                        // используется viewModelStore от users, таким образом экземпляр такой же как и в users
                        val userListEntry = remember(backStackEntry) {
                            navController.getBackStackEntry("users")
                        }
                        UserScreen(userId, viewModel(userListEntry))
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Home",
                        modifier = Modifier.clickable { navController.navigate("home") })
                    Text(
                        text = "Orders",
                        modifier = Modifier.clickable { navController.navigate("orders") })
                    Text(
                        text = "Users",
                        modifier = Modifier.clickable { navController.navigate("users") })
                }
            }
        }
    }

    @Composable
    fun HomeScreen(
        onNavigateToOrders: () -> Unit
    ) {
    }

    @Composable
    fun OrdersScreen() {
    }

    @Composable
    fun UsersScreen(userViewModel: UserViewModel = viewModel()) {
    }

    @Composable
    fun UserScreen(userId: String, userViewModel: UserViewModel) {
    }
}

class UserViewModel : ViewModel()