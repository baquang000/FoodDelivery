package com.example.fooddelivery.view.home.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.data.model.tabItemOrder
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrderFoodScreen(
    navController: NavController,
) {
    val pagerState = rememberPagerState { tabItemOrder.size }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp, horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = {
                    navController.navigateUp()
                },
                modifier = Modifier.padding(end = 80.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = stringResource(
                        id = R.string.arrow
                    ),
                    modifier = Modifier.size(width = 24.dp, height = 24.dp)
                )
            }
            NormalTextComponents(
                value = stringResource(id = R.string.order_screen),
                nomalFontsize = 24.sp,
                nomalColor = colorResource(id = R.color.black),
                nomalFontWeight = FontWeight.Bold
            )
        }
        TabRow(selectedTabIndex = pagerState.currentPage) {
            tabItemOrder.forEachIndexed { index, tabItem ->
                Tab(
                    selected = true,
                    onClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(index) }
                    },
                    text = { Text(text = tabItem.title) },
                )
            }
        }
        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState
        ) { index ->
            //Some screen
            NormalTextComponents(
                value = tabItemOrder[index].title,
                nomalFontsize = 16.sp,
                nomalColor = colorResource(id = R.color.black)
            )
        }
    }
}

@Composable
fun ItemFood(){
    
}

@Preview(
    showBackground = true,
)
@Composable
fun OrderFoodScreenPreview() {

}