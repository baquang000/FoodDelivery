package com.example.fooddelivery.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BottomNavigationBar(
    items: List<BottomNavigationItem>,
    currentRoute: String?,
    onclick: (BottomNavigationItem) -> Unit
) {
    NavigationBar {
        items.forEachIndexed { index, navigationItem ->
            NavigationBarItem(
                selected = currentRoute == navigationItem.route,
                onClick = { onclick(navigationItem) },
                icon = {
                    Icon(
                        imageVector = if (currentRoute == navigationItem.route) navigationItem.selectedIcon else navigationItem.unselectedIcon,
                        contentDescription = navigationItem.title
                    )
                },
                label = {
                    Text(text = navigationItem.title)
                },
                alwaysShowLabel = false
            )
        }
    }
}