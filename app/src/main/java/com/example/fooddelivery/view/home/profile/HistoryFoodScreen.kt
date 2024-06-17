package com.example.fooddelivery.view.home.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fooddelivery.R
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.data.viewmodel.profileviewmodel.HistoryFoodViewModel

@Composable
fun HistoryFoodScreen(
    navController: NavController,
    historyFoodViewModel: HistoryFoodViewModel = viewModel()
) {
    val historyFoodList by historyFoodViewModel.historyFoodStateFlow.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = stringResource(
                    id = R.string.arrow
                ),
                modifier = Modifier.scale(2f)
            )
        }
        LazyColumn {
            items(historyFoodList) { foodlist ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    foodlist.forEach { foodDetails ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            AsyncImage(
                                model = foodDetails.imagePath,
                                contentDescription = foodDetails.title,
                                modifier = Modifier.size(height = 100.dp, width = 130.dp),
                                contentScale = ContentScale.FillHeight
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(height = 100.dp)
                                    .padding(start = 8.dp, top = 6.dp, bottom = 6.dp),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                NormalTextComponents(
                                    value = foodDetails.title.toString(),
                                    nomalFontsize = 18.sp,
                                    nomalColor = Color.Black,
                                    nomalFontWeight = FontWeight.Bold
                                )
                                NormalTextComponents(
                                    value = "${foodDetails.price}đ",
                                    nomalFontsize = 16.sp,
                                    nomalColor = Color.Black,
                                    nomalFontWeight = FontWeight.Normal
                                )
                            }

                        }
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}