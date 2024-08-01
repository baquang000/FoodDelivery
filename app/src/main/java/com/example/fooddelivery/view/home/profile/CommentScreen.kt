package com.example.fooddelivery.view.home.profile

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.fooddelivery.R
import com.example.fooddelivery.components.RatingBar
import com.example.fooddelivery.data.model.Comment
import com.example.fooddelivery.data.model.OrderFood
import com.example.fooddelivery.data.viewmodel.profileviewmodel.OrderFoodViewModel
import com.google.firebase.storage.FirebaseStorage

@Composable
fun CommentScreen(
    navController: NavController,
    orderViewModel: OrderFoodViewModel,
) {
    val orderList by orderViewModel.orderFoodStateFlow.collectAsState()
    val orderId by orderViewModel::orderIdComment
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            CommentHeading(navController = navController)
        }
        items(orderList) { order ->
            if (orderId == order.idOrder) {
                CommentList(
                    order = order,
                    orderViewModel = orderViewModel,
                    navController = navController,
                )
            }
        }
    }
}

@Composable
fun CommentList(
    order: OrderFood,
    orderViewModel: OrderFoodViewModel,
    navController: NavController,
) {
    val commentSuccess by orderViewModel::commentSuccess
    val localManager = LocalFocusManager.current
    val context = LocalContext.current
    var rating by remember {
        mutableFloatStateOf(0f)
    }
    var commentTextField by remember {
        mutableStateOf("")
    }
    val countComment = commentTextField.length
    //image
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val singlePhotoPickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                selectedImageUri = uri
            })
    var downloadUri by remember {
        mutableStateOf("")
    }
    var isRating by remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        order.listFood.forEach { food ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    AsyncImage(
                        model = food.imagePath,
                        contentDescription = food.title,
                        modifier = Modifier.size(width = 80.dp, height = 80.dp)
                    )
                    Text(
                        text = food.title.toString(),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!isRating) {
                        Text(
                            text = "Bạn chưa chọn số sao",
                            style = MaterialTheme.typography.titleSmall.copy(
                                color = MaterialTheme.colorScheme.error
                            )
                        )
                    }
                    RatingBar(
                        modifier = Modifier.size(50.dp),
                        rating = rating,
                    ) {
                        rating = it
                    }
                    Text(
                        text = "Đánh giá sản phẩm này",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 20.sp
                        ),
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(1.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.write_comment),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "$countComment/300",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.LightGray
                        )
                    )
                }
                OutlinedTextField(
                    value = commentTextField, onValueChange = {
                        if (countComment < 300) {
                            commentTextField = it
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.LightGray,
                        focusedBorderColor = Color.LightGray
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            localManager.clearFocus()
                        }
                    )
                )
                Text(
                    text = "Thêm ảnh hoặc video", style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
                )
                if (selectedImageUri == null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(width = 1.dp, color = Color.LightGray),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(onClick = {
                            singlePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.add_photo),
                                contentDescription = "add_photo",
                                modifier = Modifier
                                    .size(width = 24.dp, height = 24.dp)
                                    .padding(top = 4.dp, bottom = 2.dp)
                            )
                        }
                        Text(
                            text = "Thêm ảnh",
                            modifier = Modifier.padding(top = 2.dp, bottom = 4.dp)
                        )
                    }
                } else {
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
                                painter = rememberAsyncImagePainter(model = selectedImageUri!!),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(width = 175.dp, height = 120.dp)
                            )
                        }
                    }
                }
            }
        }
        Button(
            onClick = {
                if (rating != 0f) {
                    if (selectedImageUri != null) {
                        uploadImageToFirebase(selectedImageUri, onUri = {
                            downloadUri = it
                            val comment = Comment(
                                idFood = order.listFood.first().id,
                                comment = commentTextField,
                                rating = rating,
                                imageUrl = downloadUri,
                                nameUser = order.name
                            )
                            orderViewModel.commentFood(comment = comment)
                        })
                    } else {
                        val comment = Comment(
                            idFood = order.listFood.first().id,
                            comment = commentTextField,
                            rating = rating,
                            imageUrl = downloadUri,
                            nameUser = order.name
                        )
                        orderViewModel.commentFood(comment = comment)
                        if (commentSuccess == false) {
                            Toast.makeText(context, "Gửi comment thành công", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Gửi comment thành công", Toast.LENGTH_SHORT).show()
                        }
                        orderViewModel.isCommented(orderFood = order)
                        navController.navigateUp()
                    }
                } else {
                    isRating = false
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Gửi",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CommentHeading(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = stringResource(
                        id = R.string.arrow
                    ),
                    modifier = Modifier.size(width = 24.dp, height = 24.dp)
                )
            }
            Text(
                text = stringResource(R.string.write_comment),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start = 68.dp)
            )
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}

fun uploadImageToFirebase(uri: Uri?, onUri: (String) -> Unit) {
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference
    val imageRef = storageRef.child("food/${uri?.lastPathSegment}")
    val uploadTask = uri.let { imageRef.putFile(uri!!) }
    uploadTask.addOnSuccessListener {
        imageRef.downloadUrl.addOnSuccessListener {
            val downloadUrl = it.toString()
            Log.d("Firebase", "Image loading uri successfully: $downloadUrl")
            onUri(downloadUrl)
        }.addOnFailureListener {
            Log.e("Firebase", "Failed to get download URL: ${it.message}")
        }
        Log.d("Firebase", "Image uploaded successfully")
    }.addOnFailureListener {
        Log.e("Firebase", "Image upload failed: ${it.message}")
    }
}