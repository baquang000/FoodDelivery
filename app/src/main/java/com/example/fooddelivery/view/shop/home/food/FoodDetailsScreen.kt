package com.example.fooddelivery.view.shop.home.food

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fooddelivery.data.viewmodel.shop.ViewAllViewModel
import com.example.fooddelivery.R
import com.example.fooddelivery.components.shop.NormalTextComponents
import com.example.fooddelivery.components.shop.RatingBar
import com.example.fooddelivery.navigation.DESCRIPTION_ARGUMENT_KEY
import com.example.fooddelivery.navigation.ID_ARGUMENT_KEY
import com.example.fooddelivery.navigation.IMAGEPATH_ARGUMENT_KEY
import com.example.fooddelivery.navigation.PRICE_ARGUMENT_KEY
import com.example.fooddelivery.navigation.STAR_ARGUMENT_KEY
import com.example.fooddelivery.navigation.TIMEVALUE_ARGUMENT_KEY
import com.example.fooddelivery.navigation.TITLE_ARGUMENT_KEY
import kotlinx.coroutines.launch

@Composable
fun FoodDetailsScreen(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    viewAllViewModel: ViewAllViewModel = viewModel()
) {
    val isTitleEmpty by viewAllViewModel::isTitleEmpty
    val isDescriptionEmpty by viewAllViewModel::isDescriptionEmpty
    val isPriceInvalid by viewAllViewModel::isPriceInvalid
    val title = navBackStackEntry.arguments?.getString(TITLE_ARGUMENT_KEY)
    val price = navBackStackEntry.arguments?.getFloat(PRICE_ARGUMENT_KEY) ?: 0.0
    val star = navBackStackEntry.arguments?.getFloat(STAR_ARGUMENT_KEY) ?: 0.0
    val timevalue = navBackStackEntry.arguments?.getInt(TIMEVALUE_ARGUMENT_KEY)
    val description = navBackStackEntry.arguments?.getString(DESCRIPTION_ARGUMENT_KEY)
    val imagepath = navBackStackEntry.arguments?.getString(IMAGEPATH_ARGUMENT_KEY)
    val id = navBackStackEntry.arguments?.getInt(ID_ARGUMENT_KEY) ?: -1
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val singlePhotoPickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                selectedImageUri = uri
            })
    val imageFood = if (selectedImageUri != null) {
        selectedImageUri
    } else {
        imagepath.toString()
    }
    val rating by remember {
        mutableFloatStateOf(star.toFloat())
    }
    var isFixDetails by remember {
        mutableStateOf(false)
    }
    var titleFood by remember {
        mutableStateOf(title)
    }
    var priceFood by remember {
        mutableStateOf(price.toString())
    }
    var descriptionFood by remember {
        mutableStateOf(description)
    }
    var readonlyTextField by remember {
        mutableStateOf(true)
    }
    val coroutineScope = rememberCoroutineScope()
    val localManager = LocalFocusManager.current
    val commentValue by viewAllViewModel.commentStateFlow.collectAsStateWithLifecycle() // comment
    val isLoadComment by viewAllViewModel.isLoadComment.collectAsStateWithLifecycle()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = stringResource(
                            id = R.string.arrow
                        ),
                        modifier = Modifier
                            .size(24.dp),
                        tint = Color.Black
                    )
                }
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NormalTextComponents(value = if (!isFixDetails) stringResource(R.string.fix_details)
                else stringResource(id = R.string.cancel),
                    nomalColor = Color.Black, nomalFontsize = 18.sp,
                    modifier = Modifier.clickable {
                        isFixDetails = !isFixDetails
                        readonlyTextField = !readonlyTextField
                    })

                NormalTextComponents(value = stringResource(R.string.save_details),
                    nomalColor = Color.Black,
                    nomalFontsize = 18.sp,
                    modifier = Modifier.clickable(isFixDetails) {
                        isFixDetails = !isFixDetails
                        coroutineScope.launch {
                            if (selectedImageUri != null) {
                                val downloadUri: String? = uploadImageToFirebase(selectedImageUri)
                                if (downloadUri != null) {
                                    viewAllViewModel.updateImagpath(downloadUri, id)
                                }
                            }
                            if (titleFood != title) {
                                viewAllViewModel.updateTitle(titleFood.toString(), id)
                            }
                            if (priceFood.toDouble() != price.toDouble()) {
                                viewAllViewModel.updatePrice(priceFood.toDouble(), id)
                            }
                            if (description != descriptionFood) {
                                viewAllViewModel.updateDescription(descriptionFood.toString(), id)
                            }
                        }
                    }
                )
            }
        }
        item {
            AsyncImage(
                model = imageFood,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp)
                    .clickable(isFixDetails) {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
            )
            if (isTitleEmpty) {
                NormalTextComponents(
                    value = stringResource(R.string.is_title_empty),
                    nomalFontsize = 12.sp,
                    nomalColor = colorResource(id = R.color.red),
                    modifier = Modifier.padding(top = 12.dp, bottom = 2.dp)
                )
            } else {
                Spacer(modifier = Modifier.padding(top = 16.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NormalTextComponents(
                    value = stringResource(id = R.string.title),
                    nomalFontsize = 18.sp,
                    nomalColor = Color.Black,
                    modifier = Modifier.padding(end = 10.dp)
                )
                OutlinedTextField(
                    value = titleFood.toString(), onValueChange = {
                        titleFood = it
                    },
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    readOnly = readonlyTextField,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            localManager.clearFocus()
                        }
                    )
                )
            }
            if (isPriceInvalid) {
                NormalTextComponents(
                    value = stringResource(R.string.is_price_valid),
                    nomalFontsize = 12.sp,
                    nomalColor = colorResource(id = R.color.red),
                    modifier = Modifier.padding(top = 12.dp, bottom = 2.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NormalTextComponents(
                    value = stringResource(id = R.string.price),
                    nomalFontsize = 18.sp,
                    nomalColor = Color.Black,
                    modifier = Modifier.padding(end = 10.dp)
                )
                OutlinedTextField(
                    value = priceFood, onValueChange = {
                        priceFood = it
                    },
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    readOnly = readonlyTextField,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            localManager.clearFocus()
                        }
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    NormalTextComponents(
                        value = "${rating}/5",
                        nomalColor = Color.Black,
                        nomalFontsize = 16.sp
                    )
                    RatingBar(
                        modifier = Modifier.size(36.dp),
                        rating = rating
                    ) {}
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    NormalTextComponents(
                        value = "${timevalue.toString()}p",
                        nomalColor = Color.Black,
                        nomalFontsize = 16.sp,
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.time),
                        contentDescription = stringResource(
                            id = R.string.time
                        ),
                        modifier = Modifier.size(16.dp),
                        tint = Color.Red
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NormalTextComponents(
                    value = stringResource(R.string.details),
                    nomalFontsize = 22.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Bold,
                )
                if (isDescriptionEmpty) {
                    NormalTextComponents(
                        value = stringResource(R.string.is_desception_empty),
                        nomalFontsize = 12.sp,
                        nomalColor = colorResource(id = R.color.red),
                    )

                } else {
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
            OutlinedTextField(
                value = descriptionFood.toString(),
                onValueChange = {
                    descriptionFood = it
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start
                ),
                readOnly = readonlyTextField,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        localManager.clearFocus()
                    }
                )
            )

        }
        item {
            Text(
                text = "Bình luận", style = TextStyle(
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start = 8.dp, top = 16.dp)
            )
        }
        items(commentValue) { comment ->
            if (comment.idFood == id) {
                Box {
                    if (isLoadComment) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, start = 24.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = comment.user.name.toString(),
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 16.sp
                                ),
                            )
                            RatingBar(
                                modifier = Modifier.size(26.dp),
                                rating = comment.rating.toFloat()
                            ) {}
                            Text(
                                text = comment.content, style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                            )
                            AsyncImage(
                                model = comment.imagePath,
                                contentDescription = null,
                                modifier = Modifier.size(width = 100.dp, height = 100.dp),
                                contentScale = ContentScale.FillWidth
                            )
                            Text(
                                text = comment.time, style = TextStyle(
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}