package com.example.procatfirst

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.procatfirst.repository.cache.CatalogCache
import com.example.procatfirst.ui.auth.AuthScreen
import com.example.procatfirst.ui.cart.Cart
import com.example.procatfirst.ui.courier.orders.CourierOrdersScreen
import com.example.procatfirst.ui.item.ToolScreen
import com.example.procatfirst.ui.managment.OrdersManagerScreen
import com.example.procatfirst.ui.managment.delivery.AdminDelivery
import com.example.procatfirst.ui.managment.deliverymen.Deliverymen
import com.example.procatfirst.ui.ordering.OrderConfirmation
import com.example.procatfirst.ui.ordering.OrderingScreen
import com.example.procatfirst.ui.personal.PersonalScreen
import com.example.procatfirst.ui.personal.chats.ChatScreen
import com.example.procatfirst.ui.personal.chats.ListOfChatsScreen
import com.example.procatfirst.ui.personal.notifications.NotificationsScreen
import com.example.procatfirst.ui.personal.orders.OrdersScreen
import com.example.procatfirst.ui.personal.profile.ProfileScreen
import com.example.procatfirst.ui.registration.RegistrationScreen
import com.example.procatfirst.ui.start.StartScreen
import com.example.procatfirst.ui.tools.ToolsScreen


enum class ProCatScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Auth(title = R.string.auth),
    Tool(title = R.string.tool),
    Tools(title = R.string.tools),
    Misha(title = R.string.test),
    Cart(title = R.string.cart),
    Personal(title = R.string.personal),
    Profile(title = R.string.profile),
    Orders(title = R.string.list_of_orders),
    Notifications(title = R.string.notifications),
    ListOfChatsScreen(title = R.string.list_of_chats),
    Chat(title = R.string.chat),
    Ordering(title = R.string.ordering),
    Delivery(title = R.string.delivery_page), // damn
    Registration(title = R.string.registration),
    Manager(title = R.string.manager), // damn
    OrderConfirmation(title = R.string.order_confirmation),
    AdminDelivery(title = R.string.delivery),
    AllDeliverymen(title = R.string.deliverymen)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProCatAppBar(
    currentScreen: ProCatScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("start", Icons.Default.Home, "Главная")
    object Tools : BottomNavItem("tools", Icons.Default.Search, "Поиск")
    object Cart : BottomNavItem("cart", Icons.Default.ShoppingCart, "Корзина")
    object Personal : BottomNavItem("personal", Icons.Default.Person, "Меню")
}



@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Tools,
        BottomNavItem.Cart,
        BottomNavItem.Personal
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.secondaryContainer
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    ) },
                selectedContentColor = MaterialTheme.colorScheme.inversePrimary,
                unselectedContentColor = MaterialTheme.colorScheme.inversePrimary,
                alwaysShowLabel = true,
                selected = false,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = false//true
                    }
                }
            )
        }
    }
}

private const val USER_ROLE_NAME = "guest"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_ROLE_NAME
)


@Composable
fun ProCatApp (
    controller: Context,
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = ProCatScreen.valueOf(
        backStackEntry?.destination?.route ?: ProCatScreen.Start.name
    )

    Scaffold(
        topBar = {
            ProCatAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController
            )
        }

    ) { innerPadding ->


        NavHost(
            navController = navController,
            startDestination = ProCatScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = ProCatScreen.Start.name) {
                StartScreen(
                    controller = controller,
                    onNextButtonClicked = {
                        navController.navigate(ProCatScreen.Auth.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            composable(route = ProCatScreen.Auth.name) {
                AuthScreen(
                    onNextButtonClicked = {
                        navController.navigate(ProCatScreen.Tools.name)
                    },
                    context = controller,
                    onToRegistrationClick = {
                        navController.navigate(ProCatScreen.Registration.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            composable(route = ProCatScreen.Registration.name) {
                RegistrationScreen(
                    onNextButtonClicked = {
                        navController.navigate(ProCatScreen.Tools.name)
                    },
                    onToAuthClick = {
                        navController.navigate(ProCatScreen.Auth.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            composable(route = ProCatScreen.Tools.name) {
                ToolsScreen(
                    onNextButtonClicked = {
                        navController.navigate(ProCatScreen.Tool.name)
                    },
                    onNextButtonClicked1 = {
                        CatalogCache.shared.setCurrent(it)
                        navController.navigate(ProCatScreen.Tool.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            composable(route = ProCatScreen.Tool.name) {
                ToolScreen(
                    onNextButtonClicked = {
                        navController.navigate(ProCatScreen.Cart.name)
                    },
                    tool = CatalogCache.shared.getCurrent(),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            composable(route = ProCatScreen.Cart.name) {
                Cart(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    onToOrderingClicked = {
                        navController.navigate(ProCatScreen.Ordering.name)
                    },
                    onGoToProfile = {
                        navController.navigate(ProCatScreen.Profile.name)
                    }
                )
            }
            composable(route = ProCatScreen.Personal.name) {
                PersonalScreen(
                    context = controller,
                    onToProfileClicked = {
                        navController.navigate(ProCatScreen.Profile.name)
                    },
                    onToOrdersClicked = {
                        navController.navigate(ProCatScreen.Orders.name)
                    },
                    onToNotificationsClicked = {
                        navController.navigate(ProCatScreen.Notifications.name)
                    },
                    onToChatsClicked = {
                        navController.navigate(ProCatScreen.ListOfChatsScreen.name)
                    },
                    onToDeliveryClicked = {
                        navController.navigate(ProCatScreen.Delivery.name)
                    },
                    onToManagerClicked = {
                        navController.navigate(ProCatScreen.Manager.name)
                    },
                    onToAdminDeliveryClicked = {
                        navController.navigate(ProCatScreen.AdminDelivery.name)
                    },
                    onToAllDeliverymenClicked = {
                        navController.navigate(ProCatScreen.AllDeliverymen.name)
                    }

                )
            }
            composable(route = ProCatScreen.Profile.name) {
                ProfileScreen(
                )
            }
            composable(route = ProCatScreen.Orders.name) {
                OrdersScreen(

                )
            }
            composable(route = ProCatScreen.Delivery.name) {

                CourierOrdersScreen(controller)
            }
            composable(route = ProCatScreen.Manager.name) {
                OrdersManagerScreen(
                    controller
                )
            }
            composable(route = ProCatScreen.Notifications.name) {
                NotificationsScreen(
                    context = LocalContext.current,
                    )
            }
            composable(route = ProCatScreen.ListOfChatsScreen.name) {
                ListOfChatsScreen(
                    onToChatClicked = {
                        navController.navigate(ProCatScreen.Chat.name)
                    },
                )
            }
            composable(route = ProCatScreen.Chat.name) {
                ChatScreen(

                )
            }
            composable(route = ProCatScreen.Ordering.name) {
                OrderingScreen(
                    onToConfirmationClicked = {
                        navController.navigate(ProCatScreen.OrderConfirmation.name)
                    }
                )
            }
            composable(route = ProCatScreen.OrderConfirmation.name) {
                OrderConfirmation(
                    orderingViewModel = viewModel()
                )
            }
            composable(route = ProCatScreen.AdminDelivery.name) {
                AdminDelivery(

                )
            }
            composable(route = ProCatScreen.AllDeliverymen.name) {
                Deliverymen(

                )
            }
        }

    }
}