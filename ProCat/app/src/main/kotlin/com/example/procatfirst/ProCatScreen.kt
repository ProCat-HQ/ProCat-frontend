package com.example.procatfirst

import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.procatfirst.ui.auth.AuthScreen

import com.example.procatfirst.ui.auth.AuthViewModel
import com.example.procatfirst.ui.cart.Cart
import com.example.procatfirst.ui.item.ToolScreen
import com.example.procatfirst.ui.item.ToolViewModel
import com.example.procatfirst.ui.personal.PersonalScreen
import com.example.procatfirst.ui.personal.chats.ChatScreen
import com.example.procatfirst.ui.personal.chats.ListOfChatsScreen
import com.example.procatfirst.ui.personal.notifications.NotificationsScreen
import com.example.procatfirst.ui.personal.orders.OrdersScreen
import com.example.procatfirst.ui.personal.profile.ProfileScreen
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
    Chat(title = R.string.chat)
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
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("start", Icons.Default.Home, "Home")
    object Tools : BottomNavItem("tools", Icons.Default.Search, "Search")
    object Cart : BottomNavItem("cart", Icons.Default.ShoppingCart, "Cart")
    object Personal : BottomNavItem("personal", Icons.Default.Person, "Profile")
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
        backgroundColor = colorResource(id = R.color.white),
        contentColor = Color.White
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(text = item.label) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
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
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun ProCatApp (
    authViewModel: AuthViewModel = viewModel(),
    toolViewModel: ToolViewModel = viewModel(),
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
        //val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = ProCatScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = ProCatScreen.Start.name) {
                StartScreen(
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
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            composable(route = ProCatScreen.Tool.name) {
                ToolScreen(
                    onNextButtonClicked = {
//                        navController.navigate(ProCatScreen.Misha.name)
                        navController.navigate(ProCatScreen.Cart.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            composable(route = ProCatScreen.Misha.name) {
                TestButtons(
                    onNextButtonClicked = {
                        navController.navigate(ProCatScreen.Cart.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            composable(route = ProCatScreen.Cart.name) {
                Cart(
                    onNextButtonClicked = {
                        navController.navigate(ProCatScreen.Personal.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            composable(route = ProCatScreen.Personal.name) {
                PersonalScreen(
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
                    /*
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        */
                )
            }
            composable(route = ProCatScreen.Profile.name) {
                ProfileScreen(
                    /*
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        */
                )
            }
            composable(route = ProCatScreen.Orders.name) {
                OrdersScreen(
                    /*
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        */
                )
            }
            composable(route = ProCatScreen.Notifications.name) {
                NotificationsScreen(
                    /*
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        */
                )
            }
            composable(route = ProCatScreen.ListOfChatsScreen.name) {
                ListOfChatsScreen(
                    onToChatClicked = {
                        navController.navigate(ProCatScreen.Chat.name)
                    },
                    /*
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        */
                )
            }
            composable(route = ProCatScreen.Chat.name) {
                ChatScreen(

                    /*
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        */
                )
            }
        }

    }
}