package com.appmilla.vslsocial.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.viewbinding.ViewBinding
import com.amity.socialcloud.uikit.chat.home.fragment.AmityChatHomePageFragment
import com.amity.socialcloud.uikit.community.explore.fragments.AmityCommunityExplorerFragment
import com.amity.socialcloud.uikit.community.home.fragments.AmityCommunityHomePageFragment
import com.appmilla.vslsocial.Greeting
import com.appmilla.vslsocial.android.databinding.ChatPageContainerBinding
import com.appmilla.vslsocial.android.databinding.ClubsPageContainerBinding
import com.appmilla.vslsocial.android.databinding.HomePageContainerBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

// You can use LocalCompositionLocal to provide the FragmentManager to your composable functions
// To provide LocalFragmentManager down the tree you can create a Local provider like this:
val LocalFragmentManager = compositionLocalOf<FragmentManager> {
    error("FragmentManager not provided")
}

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Observe the login status using repeatOnLifecycle
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.loginStatus.collectLatest { isLoggedIn ->
                    when (isLoggedIn) {
                        true -> setupTabbedContent()
                        false -> showLoginError()
                        null -> showLoading()
                    }
                }
            }
        }

        mainViewModel.initiateLogin()
    }

    private fun setupTabbedContent() {
        setContent {
            MyApplicationTheme {
                CompositionLocalProvider(LocalFragmentManager provides supportFragmentManager) {
                    // Your composable functions that require the FragmentManager go here...
                    // e.g., Scaffold, BottomBar, NavHost...
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = { BottomBar(navController = navController) },
                    ) { innerPadding ->
                        NavHost(
                            navController,
                            startDestination = "home",
                            Modifier.padding(innerPadding),
                        ) {
                            composable("home") {
                                val frameLayoutId = remember { 1 }
                                FragmentHolderScreen(fragmentTag = "HomeFragmentTag", frameId = frameLayoutId, factory = { inflater, parent, attachToParent ->
                                    HomePageContainerBinding.inflate(inflater, parent, attachToParent).apply {
                                        // Ensure the container has a unique ID
                                        fragmentContainerView.id = frameLayoutId
                                    }
                                }) { AmityCommunityHomePageFragment.newInstance().build() }
                            }
                            composable("chat") {
                                // var frameLayoutId = remember { View.generateViewId() }
                                val frameLayoutId = remember { 2 }
                                FragmentHolderScreen(fragmentTag = "ChatFragmentTag", frameId = frameLayoutId, factory = { inflater, parent, attachToParent ->
                                    ChatPageContainerBinding.inflate(inflater, parent, attachToParent).apply {
                                        // Ensure the container has a unique ID
                                        fragmentContainerView.id = frameLayoutId
                                    }
                                }) { AmityChatHomePageFragment.newInstance().build(this@MainActivity) }
                            }
                            composable("store") {
                                GreetingView(Greeting().greet())
                            }
                            composable("clubs") {
                                val frameLayoutId = remember { 3 }
                                FragmentHolderScreen(fragmentTag = "ClubsFragmentTag", frameId = frameLayoutId, factory = { inflater, parent, attachToParent ->
                                    ClubsPageContainerBinding.inflate(inflater, parent, attachToParent).apply {
                                        // Ensure the container has a unique ID
                                        fragmentContainerView.id = frameLayoutId
                                    }
                                }) { AmityCommunityExplorerFragment.newInstance().build() }
                            }

                            composable("more") { Text("More") }
                        }
                    }
                }
            }
        }
    }

    private fun showLoading() {
        setContent {
            MyApplicationTheme {
                CircularProgressIndicator() // Show loading indicator
            }
        }
    }

    private fun showLoginError() {
        setContent {
            MyApplicationTheme {
                Text("Login error. Please try again.") // Show error message
            }
        }
    }

    @Composable
    fun GreetingView(text: String) {
        Text(text = text)
    }

    @Composable
    fun <T : ViewBinding> FragmentHolderScreen(fragmentTag: String, frameId: Int, factory: (inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean) -> T, fragmentProvider: () -> Fragment) {
        val fragmentManager = LocalFragmentManager.current
        // var frameLayoutId = remember { 0 }
        // var frameLayoutId = remember { View.generateViewId() }
        var frameLayoutId = remember { frameId }

        // AndroidViewBinding(FragmentContainerLayoutBinding::inflate)
        AndroidViewBinding(factory = factory)

        DisposableEffect(frameLayoutId, fragmentTag) {
            var fragment = fragmentManager.findFragmentByTag(fragmentTag)

            if (frameLayoutId != 0) {
                if (fragment == null) {
                    fragment = fragmentProvider()
                    fragmentManager.beginTransaction()
                        .replace(frameLayoutId, fragment, fragmentTag)
                        .commitNowAllowingStateLoss()
                } else {
                    // Reattach the fragment if it's already added but not visible
                    fragmentManager.beginTransaction()
                        .attach(fragment)
                        .commitNowAllowingStateLoss()
                }
            }

            onDispose {
                // Detach the fragment when the composable leaves the composition
                fragment?.let {
                    if (it.isAdded) {
                        fragmentManager.beginTransaction()
                            .detach(it)
                            .commitNowAllowingStateLoss()
                    }
                }
            }
        }
    }

    /*
// see https://touchlab.co/jetpack-compose-a-use-case-for-view-interop-migration-strategy
@Composable
fun <T : ViewBinding> FragmentHolderScreen(
    androidViewBindingFactory: (inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean) -> T,
    androidViewBindingUpdate: T.() -> Unit = {},
) {
    Scaffold(
        content = { paddingValues ->
            AndroidViewBinding(
                factory = androidViewBindingFactory,
                modifier = Modifier.padding(paddingValues),
                update = androidViewBindingUpdate,
            )
        },
    )
}
 */

    @Composable
    fun BottomBar(navController: NavController) {
        BottomNavigation {
            val items = listOf("home", "store", "clubs", "chat", "more")
            val icons = listOf(
                Icons.Filled.Home,
                Icons.Filled.ShoppingCart,
                Icons.Filled.AccountCircle,
                Icons.Filled.MailOutline,
                Icons.Filled.Menu,
            )
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            items.forEachIndexed { index, item ->
                BottomNavigationItem(
                    icon = { Icon(icons[index], contentDescription = item) },
                    label = { Text(item.capitalize(Locale.getDefault())) },
                    selected = currentRoute == item,
                    onClick = {
                        navController.navigate(item) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        }
    }
}

/*
@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
 */
