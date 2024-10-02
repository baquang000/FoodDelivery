package com.example.fooddelivery.view.shop.home.profile

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.fooddelivery.data.viewmodel.shop.ProfileAdminViewModel
import com.example.fooddelivery.R
import com.example.fooddelivery.components.shop.NormalTextComponents
import com.example.fooddelivery.view.shop.home.food.uploadImageToFirebase
import kotlinx.coroutines.launch

@Composable
fun ProfileAdminScreen(
    navController: NavController,
    profileAdminViewModel: ProfileAdminViewModel = viewModel()
) {
    var isFixDetails by remember {
        mutableStateOf(false)
    }
    var readonlyTextField by remember {
        mutableStateOf(true)
    }
    //Get shop in Profile viewmodel
    val shopInfor by profileAdminViewModel.shopInfor.collectAsStateWithLifecycle()
    val isLoading by profileAdminViewModel.isLoadShopInfor.collectAsStateWithLifecycle()

    ///define var
    var titleShop by remember {
        mutableStateOf("")
    }
    var nameText by remember {
        mutableStateOf("")
    }
    var addressText by remember {
        mutableStateOf("")
    }
    var emailText by remember {
        mutableStateOf("")
    }
    var phonenumber by remember {
        mutableStateOf("")
    }
    var imageUrl by remember {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = shopInfor) {
        shopInfor?.let {
            titleShop = it.titleShop
            nameText = it.name
            addressText = it.address
            emailText = it.email
            phonenumber = it.phoneNumber
            imageUrl = it.imageUrl
        }
    }
    //image clickable
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val coroutineScope = rememberCoroutineScope()
    val singlePhotoPickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                selectedImageUri = uri
            })
    val painter: Painter = if (selectedImageUri != null) {
        rememberAsyncImagePainter(selectedImageUri)
    } else {
        if (imageUrl == "")
            painterResource(id = R.drawable.image_false)
        else rememberAsyncImagePainter(imageUrl)
    }
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box {
                Image(
                    painter = painter,
                    contentDescription = "Image_backgroud",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable(isFixDetails) {
                            singlePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                )
                IconButton(
                    onClick = {
                        navController.navigateUp()
                    },
                    modifier = Modifier
                        .padding(top = 8.dp, start = 8.dp)
                        .background(color = Color.LightGray, shape = CircleShape)
                        .size(30.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = stringResource(
                            id = R.string.arrow_icon
                        ),
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, end = 22.dp, start = 15.dp, bottom = 32.dp),
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
                        coroutineScope.launch {
                            isFixDetails = !isFixDetails
                            if (selectedImageUri != null) {
                                val downloadUri = uploadImageToFirebase(selectedImageUri)
                                if (downloadUri != null) {
                                    profileAdminViewModel.saveUserData(
                                        titleShop = titleShop,
                                        nameShop = nameText,
                                        address = addressText,
                                        email = emailText,
                                        phoneNumber = phonenumber,
                                        imageUrl = downloadUri
                                    )
                                }
                            } else {
                                profileAdminViewModel.saveUserData(
                                    titleShop = titleShop,
                                    nameShop = nameText,
                                    address = addressText,
                                    email = emailText,
                                    phoneNumber = phonenumber,
                                    imageUrl = imageUrl
                                )
                            }
                        }
                    })
            }

            FieldItemProfile(
                textvalue = titleShop,
                nameField = R.string.title_shop,
                isreadonly = readonlyTextField,
                onchangeText = {
                    titleShop = it
                },
                startPadding = 12.dp,
            )
            FieldItemProfile(
                textvalue = nameText,
                isreadonly = readonlyTextField,
                onchangeText = {
                    nameText = it
                })
            FieldItemProfile(
                textvalue = addressText,
                nameField = R.string.address,
                startPadding = 12.dp,
                isreadonly = readonlyTextField,
                onchangeText = {
                    addressText = it
                })
            FieldItemProfile(
                textvalue = emailText,
                nameField = R.string.email,
                startPadding = 22.dp,
                isreadonly = readonlyTextField,
                onchangeText = {
                    emailText = it
                })
            FieldItemProfile(
                textvalue = phonenumber,
                nameField = R.string.phonenumber,
                startPadding = 32.dp,
                isreadonly = readonlyTextField,
                onchangeText = {
                    phonenumber = it
                })
        }
    }
}

@Composable
fun FieldItemProfile(
    textvalue: String,
    @StringRes nameField: Int = R.string.title,
    startPadding: Dp = 40.dp,
    isreadonly: Boolean = true,
    onchangeText: (String) -> Unit
) {
    Log.d("Test3", textvalue)
    val localManager = LocalFocusManager.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 13.dp, end = 22.dp, bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        NormalTextComponents(
            value = stringResource(id = nameField),
            nomalColor = Color.Black,
            nomalFontsize = 20.sp,
        )
        OutlinedTextField(
            value = textvalue,
            onValueChange = onchangeText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = startPadding, top = 16.dp, bottom = 16.dp, end = 4.dp),
            textStyle = TextStyle(
                fontSize = 14.sp,
            ),
            readOnly = isreadonly,
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
}
