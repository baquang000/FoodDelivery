package com.example.fooddelivery.view.shop.home.food

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.fooddelivery.R
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.components.shop.MyDropdownMenuWithBestFood
import com.example.fooddelivery.data.viewmodel.shop.AddFoodViewModel
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun AddFoodScreen(
    navController: NavController = rememberNavController(),
    addFoodViewModel: AddFoodViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val isLoading by addFoodViewModel.isLoadingAddFood.collectAsStateWithLifecycle()
    val isTitleEmpty by addFoodViewModel::isTitleEmpty
    val isDescriptionEmpty by addFoodViewModel::isDescriptionEmpty
    val isPriceInvalid by addFoodViewModel::isPriceInvalid
    val isImageUrlEmpty by addFoodViewModel::isImageUrlEmpty
    val isTimeInvalid by addFoodViewModel::isTimeInvalid
    val listLoc = listOf("Bắc Từ Liêm", "Nam Từ Liêm")
    val bestfood = listOf(true, false)
    val listCategory =
        listOf("Pizza", "Burger", "Gà", "Shushi", "Thịt", "Xúc xích", "Đồ uống", "Khác")
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var titleText by remember {
        mutableStateOf("")
    }
    var priceText by remember {
        mutableStateOf("")
    }
    var descriptionText by remember {
        mutableStateOf("")
    }
    var isBestFood by remember {
        mutableStateOf(true)
    }
    var categoryIndex by remember {
        mutableIntStateOf(0)
    }
    var locIndex by remember {
        mutableIntStateOf(0)
    }
    var timeText by remember {
        mutableIntStateOf(0)
    }
    val singlePhotoPickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                selectedImageUri = uri
            })
    val painter: Painter = if (selectedImageUri != null) {
        rememberAsyncImagePainter(selectedImageUri)
    } else {
        painterResource(id = R.drawable.image_false)
    }
    val localFocusManager = LocalFocusManager.current
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow),
                            contentDescription = stringResource(
                                id = R.string.arrow_icon
                            ),
                            modifier = Modifier.size(width = 24.dp, height = 24.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        NormalTextComponents(
                            value = stringResource(id = R.string.add_new_food),
                            nomalColor = colorResource(id = R.color.black),
                            nomalFontsize = 28.sp,
                            modifier = Modifier.padding(end = 24.dp)
                        )
                    }
                }
            }
            if (isTitleEmpty) {
                item {
                    NormalTextComponents(
                        value = stringResource(R.string.is_title_empty),
                        nomalFontsize = 12.sp,
                        nomalColor = colorResource(id = R.color.red),
                        modifier = Modifier.padding(top = 12.dp, bottom = 2.dp)
                    )
                }
            } else {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            item {
                OutlineTextAddFood(onchangeText = {
                    titleText = it
                })
            }
            if (isPriceInvalid) {
                item {
                    NormalTextComponents(
                        value = stringResource(R.string.is_price_valid),
                        nomalFontsize = 12.sp,
                        nomalColor = colorResource(id = R.color.red),
                        modifier = Modifier.padding(top = 12.dp, bottom = 2.dp)
                    )
                }
            } else {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            item {
                OutlinedTextField(
                    value = priceText,
                    onValueChange = { newText ->
                        val number = newText.toDoubleOrNull()
                        if (number != null) {
                            priceText = newText
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    label = {
                        NormalTextComponents(
                            value = stringResource(id = R.string.price),
                            nomalFontsize = 14.sp,
                            nomalColor = colorResource(
                                id = R.color.black
                            )
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Decimal
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            localFocusManager.clearFocus()
                        }
                    )
                )
            }
            if (isImageUrlEmpty) {
                item {
                    NormalTextComponents(
                        value = stringResource(R.string.is_image_empty),
                        nomalFontsize = 12.sp,
                        nomalColor = colorResource(id = R.color.red),
                        modifier = Modifier.padding(top = 12.dp, bottom = 2.dp)
                    )
                }
            } else {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            item {
                OutlinedTextField(value = "",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 6.dp),
                    placeholder = {
                        NormalTextComponents(
                            value = stringResource(R.string.image_food),
                            nomalFontsize = 14.sp,
                            nomalColor = colorResource(
                                id = R.color.black
                            )
                        )
                    },
                    readOnly = true,
                    trailingIcon = {
                        Image(painter = painterResource(id = R.drawable.add),
                            contentDescription = stringResource(
                                id = R.string.add_icon
                            ),
                            modifier = Modifier
                                .size(width = 32.dp, height = 32.dp)
                                .clickable {
                                    singlePhotoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                })
                    })
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier.size(width = 175.dp, height = 120.dp)
                    ) {
                        Image(
                            painter = painter, contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(width = 175.dp, height = 120.dp)
                        )
                    }
                }
            }
            if (isDescriptionEmpty) {
                item {
                    NormalTextComponents(
                        value = stringResource(R.string.is_desception_empty),
                        nomalFontsize = 12.sp,
                        nomalColor = colorResource(id = R.color.red),
                        modifier = Modifier.padding(top = 12.dp, bottom = 2.dp)
                    )
                }
            } else {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            item {
                OutlineTextAddFood(
                    textLabel = stringResource(R.string.description_food),
                    onchangeText = {
                        descriptionText = it
                    })
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 6.dp)
                ) {
                    OutlinedTextField(
                        value = "", onValueChange = {},
                        placeholder = {
                            NormalTextComponents(
                                value = stringResource(id = R.string.best_food),
                                nomalFontsize = 14.sp,
                                nomalColor = colorResource(
                                    id = R.color.black
                                )
                            )
                        }, readOnly = true,
                        modifier = Modifier.weight(0.75f)
                    )
                    MyDropdownMenuWithBestFood(
                        list = bestfood,
                        onSelectedIndex = {},
                        onSelelectedValue = {
                            isBestFood = it
                        },
                        modifier = Modifier.weight(0.5f)
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 6.dp)
                ) {
                    OutlinedTextField(
                        value = "", onValueChange = {},
                        placeholder = {
                            NormalTextComponents(
                                value = stringResource(R.string.category),
                                nomalFontsize = 14.sp,
                                nomalColor = colorResource(
                                    id = R.color.black
                                )
                            )
                        }, readOnly = true,
                        modifier = Modifier.weight(0.75f)
                    )
                    MyDropdownMenuWithBestFood(list = listCategory, onSelectedIndex = {
                        categoryIndex = it
                    }, onSelelectedValue = {}, modifier = Modifier.weight(0.5f))
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 6.dp)
                ) {
                    OutlinedTextField(
                        value = "", onValueChange = {},
                        placeholder = {
                            NormalTextComponents(
                                value = stringResource(R.string.location),
                                nomalFontsize = 14.sp,
                                nomalColor = colorResource(
                                    id = R.color.black
                                )
                            )
                        }, readOnly = true,
                        modifier = Modifier.weight(0.75f)
                    )
                    MyDropdownMenuWithBestFood(list = listLoc, onSelectedIndex = {
                        locIndex = it
                    }, onSelelectedValue = {}, modifier = Modifier.weight(0.5f))
                }
            }
            if (isTimeInvalid) {
                item {
                    NormalTextComponents(
                        value = stringResource(R.string.time_is_not_invalid),
                        nomalFontsize = 12.sp,
                        nomalColor = colorResource(id = R.color.red),
                        modifier = Modifier.padding(top = 12.dp, bottom = 2.dp)
                    )
                }
            } else {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            item {
                OutlineTextAddFood(
                    textLabel = stringResource(R.string.time),
                    onchangeText = { newText ->
                        val number = newText.toIntOrNull()
                        if (number != null) {
                            timeText = number
                        }
                    },
                    keyboardType = KeyboardType.Number
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        coroutineScope.launch {
                            if (selectedImageUri != null) {
                                val downloadUri: String? =
                                    uploadImageToFirebase(selectedImageUri)
                                if (downloadUri != null) {
                                    addFoodViewModel.addNewFood(
                                        title = titleText,
                                        price = priceText.toDouble(),
                                        description = descriptionText,
                                        imageUrl = downloadUri,
                                        bestFood = isBestFood,
                                        category = categoryIndex,
                                        loc = locIndex,
                                        time = timeText
                                    )
                                    titleText = ""
                                    priceText = ""
                                    descriptionText = ""
                                    titleText = ""
                                    selectedImageUri = null
                                }
                            }
                        }
                    }) {
                        NormalTextComponents(
                            value = stringResource(id = R.string.add_btn),
                            modifier = Modifier.width(100.dp)
                        )
                    }
                }

            }
        }
    }
}


suspend fun uploadImageToFirebase(uri: Uri?): String? {
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference
    val imageRef = storageRef.child("food/${uri?.lastPathSegment}")

    return suspendCoroutine { continuation ->
        uri?.let {
            val uploadTask = imageRef.putFile(uri)
            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {
                    val downloadUrl = it.toString()
                    Log.d("Firebase", "Image loading uri successfully: $downloadUrl")
                    continuation.resume(downloadUrl)
                }.addOnFailureListener {
                    Log.e("Firebase", "Failed to get download URL: ${it.message}")
                    continuation.resume(null)
                }
                Log.d("Firebase", "Image uploaded successfully")
            }.addOnFailureListener {
                Log.e("Firebase", "Image upload failed: ${it.message}")
                continuation.resume(null)
            }
        } ?: continuation.resume(null)
    }
}

@Composable
fun OutlineTextAddFood(
    textLabel: String = stringResource(R.string.title),
    keyboardType: KeyboardType = KeyboardType.Text,
    onchangeText: (String) -> Unit
) {
    var textValue by remember {
        mutableStateOf("")
    }
    val localFocusManager = LocalFocusManager.current
    OutlinedTextField(value = textValue,
        onValueChange = { newtextValue ->
            textValue = newtextValue
            onchangeText(newtextValue)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp),
        label = {
            NormalTextComponents(
                value = textLabel, nomalFontsize = 14.sp, nomalColor = colorResource(
                    id = R.color.black
                )
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                localFocusManager.clearFocus()
            }
        )
    )
}


@Preview(
    showBackground = true, showSystemUi = true
)
@Composable
fun AddFoodScreenPreview() {
    AddFoodScreen()
}