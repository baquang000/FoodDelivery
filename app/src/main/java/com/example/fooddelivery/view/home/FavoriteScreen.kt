package com.example.fooddelivery.view.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.viewmodel.FavoriteViewModel

@Composable
fun FavoriteScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = viewModel()
) {
    val favoriteFood by favoriteViewModel::favoriteFood
    val isLoading by favoriteViewModel::isLoading
    val loadError by favoriteViewModel::loadError
    LaunchedEffect(Unit) {
        favoriteViewModel.getFavoriteFood()
    }
    if (isLoading){
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    }else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()){
                items(favoriteFood){food->
                    FavoriteItem(food)
                }
            }
            if (loadError != null) {
                Text(text = loadError ?: "", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun FavoriteItem(
    food:Food
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        AsyncImage(
            model = food.ImagePath,
            contentDescription = food.Title,
            contentScale = ContentScale.Inside,
            modifier = Modifier.size(100.dp,100.dp)
        )
        Column()
        {
            Text(text = food.Title.toString(), modifier = Modifier.padding(vertical = 8.dp))
            Text(text = "${food.Price}đ")
        }

    }

}
