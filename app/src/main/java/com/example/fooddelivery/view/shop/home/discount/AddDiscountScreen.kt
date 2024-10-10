package com.example.fooddelivery.view.shop.home.discount

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.ButtonComponents
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.data.model.CreateDiscount
import com.example.fooddelivery.data.model.TypeDiscount
import com.example.fooddelivery.data.viewmodel.ID
import com.example.fooddelivery.data.viewmodel.shop.DiscountViewModel
import com.google.android.gms.common.config.GservicesValue.value
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("UnrememberedMutableInteractionSource")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDiscountScreen(
    navController: NavController,
    discountViewModel: DiscountViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val list = listOf(TypeDiscount.PERCENTAGE, TypeDiscount.FIXED)
    var selectedOption by remember { mutableStateOf(list[0]) }
    var name by remember {
        mutableStateOf("")
    }
    var percentage by remember {
        mutableStateOf("")
    }
    var maxDiscountAmount by remember {
        mutableStateOf("")
    }
    var minOrderAmount by remember {
        mutableStateOf("")
    }
    var maxUser by remember {
        mutableStateOf("")
    }
    var numberUse by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var dateStart by remember {
        mutableStateOf("")
    }
    var dateEnd by remember {
        mutableStateOf("")
    }
    val isValid = validateAllFields(
        name,
        percentage,
        maxDiscountAmount,
        minOrderAmount,
        maxUser,
        numberUse,
        description,
        dateStart,
        dateEnd
    )
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var isSuccess by discountViewModel::isSuccess
    val isFailer by discountViewModel::isFailer
    val errormessage by discountViewModel::errormessage
    val isAdd by discountViewModel.isLoadAdd.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Tạo khuyến mãi mới",
                        color = Color.Black,
                        modifier = Modifier.padding(start = 45.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = stringResource(
                            id = R.string.arrow
                        ),
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(24.dp)
                            .clickable {
                                navController.navigateUp()
                            }
                    )
                }
            )
        }
    ) { paddingValues ->
        Box {
            if (isAdd) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .clickable(
                        indication = null, // Remove the grey ripple effect
                        interactionSource = MutableInteractionSource() // Required when setting indication to null
                    ) {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    },
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray.copy(alpha = 0.5f))
                            .padding(vertical = 15.dp, horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        NormalTextComponents(
                            value = "Thông tin khuyến mãi",
                            nomalColor = Color.Black,
                            nomalFontsize = 18.sp,
                            nomalFontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 5.dp)
                        )
                    }
                }
                item {
                    Column {
                        NormalTextComponents(
                            value = "Tên khuyến mãi",
                            nomalColor = Color.Gray,
                            nomalFontsize = 16.sp,
                            nomalFontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                        )
                        OutlineTextFieldAdd(
                            text = name,
                            lable = "Nhập tên mã khuyến mãi",
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 50.dp)
                        ) {
                            name = it
                        }
                    }
                }

                item {
                    NormalTextComponents(
                        value = "Loại khuyến mãi",
                        nomalColor = Color.Gray,
                        nomalFontsize = 16.sp,
                        nomalFontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                    )
                }
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        list.forEach { option ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                RadioButton(
                                    selected = selectedOption == option,
                                    onClick = { selectedOption = option }
                                )
                                Text(text = "$option")
                            }
                        }
                    }
                }
                item {
                    Column {
                        NormalTextComponents(
                            value = "Giá trị khuyến mãi",
                            nomalColor = Color.Gray,
                            nomalFontsize = 16.sp,
                            nomalFontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                        )
                        OutlineTextFieldAdd(
                            text = percentage,
                            lable = (if (selectedOption == TypeDiscount.PERCENTAGE) "Nhập phần trăm giảm giá" else "Nhập số tiền khuyến mãi "),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 50.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            )
                        ) {
                            percentage = it
                        }
                    }
                }

                item {
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        NormalTextComponents(
                            value = "Giảm tối đa",
                            nomalColor = Color.Gray,
                            nomalFontsize = 16.sp,
                            nomalFontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        OutlineTextFieldAdd(
                            text = maxDiscountAmount,
                            lable = "Nhập số tiền tối đa được giảm",
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 50.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            )
                        ) {
                            maxDiscountAmount = it
                        }
                    }
                }
                item {
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        NormalTextComponents(
                            value = "Áp dụng cho đơn hàng tối thiểu từ",
                            nomalColor = Color.Gray,
                            nomalFontsize = 16.sp,
                            nomalFontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        OutlineTextFieldAdd(
                            text = minOrderAmount,
                            lable = "Đơn hàng tối thiểu được áp dụng",
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 50.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            )
                        ) {
                            minOrderAmount = it
                        }
                    }
                }
                item {
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        NormalTextComponents(
                            value = "Số khách hàng tối đa được áp dụng",
                            nomalColor = Color.Gray,
                            nomalFontsize = 16.sp,
                            nomalFontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        OutlineTextFieldAdd(
                            text = maxUser,
                            lable = "Số mã giảm giá được sử dụng",
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 50.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            )
                        ) {
                            maxUser = it
                        }
                    }
                }
                item {
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        NormalTextComponents(
                            value = "Số lần áp dụng mỗi khách hàng ",
                            nomalColor = Color.Gray,
                            nomalFontsize = 16.sp,
                            nomalFontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        OutlineTextFieldAdd(
                            text = numberUse,
                            lable = "Mỗi khách hàng dùng được bao nhiêu lần",
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 50.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            )
                        ) {
                            numberUse = it
                        }
                    }
                }
                if (dateStart > dateEnd){
                    item {
                        NormalTextComponents(
                            value = "Ngày bắt đầu phải bé hơn ngày kết thúc",
                            nomalColor = Color.Red,
                            nomalFontsize = 16.sp,
                            nomalFontWeight = FontWeight.Bold,
                        )
                    }
                }
                item {
                    TimePickInDiscount { start, end ->
                        dateStart = start
                        dateEnd = end
                    }
                }
                item {
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        NormalTextComponents(
                            value = "Chi tiết mã giảm giá ",
                            nomalColor = Color.Gray,
                            nomalFontsize = 16.sp,
                            nomalFontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        OutlineTextFieldAdd(
                            text = description,
                            lable = "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 50.dp)
                        ) {
                            description = it
                        }
                    }
                }
                if (isFailer) {
                    item {
                        NormalTextComponents(
                            value = errormessage.toString(),
                            nomalColor = Color.Red,
                            nomalFontsize = 16.sp,
                            nomalFontWeight = FontWeight.Bold,
                        )
                    }
                }

                item {
                    ButtonComponents(value = "Tạo mới", isEnable = isValid) {
                        val discount = CreateDiscount(
                            description = description,
                            endDate = dateEnd,
                            idShop = ID,
                            isActive = true,
                            maxDiscountAmount = maxDiscountAmount,
                            maxUser = maxUser.toInt(),
                            minOrderAmount = minOrderAmount,
                            name = name,
                            numberUse = numberUse.toInt(),
                            percentage = percentage,
                            startDate = dateStart,
                            typeDiscount = selectedOption.toString()
                        )
                        coroutineScope.launch(Dispatchers.IO) {
                            discountViewModel.createDiscount(discount)
                        }
                    }
                }
            }
            if (isSuccess) {
                AlertDialog(onDismissRequest = { isSuccess = false },
                    title = {
                        NormalTextComponents(
                            value = stringResource(R.string.success),
                            nomalColor = Color.Black
                        )
                    },
                    text = {
                        Text(text = "Thêm mới mã giảm giá thành công")
                    },
                    confirmButton = {
                        Button(onClick = {
                            isSuccess = false
                            navController.navigateUp()
                        }) {
                            Text(text = "Ok")
                        }
                    })
            }
        }
    }
}


@Composable
fun OutlineTextFieldAdd(
    text: String,
    lable: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black,
    outlineColor: Color = Color.Gray,
    enabled: Boolean = true,
    fontWeight: FontWeight = FontWeight.Normal,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    ),
    onChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = text, onValueChange = onChange,
        label = {
            Text(text = lable, color = Color.Gray)
        },
        textStyle = TextStyle(
            color = textColor,
            fontWeight = fontWeight,
            fontSize = 16.sp
        ),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            unfocusedIndicatorColor = outlineColor,
            disabledContainerColor = Color.White
        ),
        enabled = enabled,
        modifier = modifier.padding(vertical = 12.dp, horizontal = 12.dp),
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        )
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimePickInDiscount(
    onDatesSelected: (String, String) -> Unit
) {
    var dateStart by remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedDateStart by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("yyyy-MM-dd")
                .format(dateStart)
        }
    }
    var dateEnd by remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedDateEnd by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("yyyy-MM-dd")
                .format(dateEnd)
        }
    }
    value(formattedDateStart, formattedDateEnd)
    val dateStartDialog = rememberMaterialDialogState()
    val dateEndDialog = rememberMaterialDialogState()
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        Row {
            NormalTextComponents(
                value = "Ngày bắt đầu",
                nomalColor = Color.Gray,
                nomalFontsize = 16.sp,
                nomalFontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f)
            )
            NormalTextComponents(
                value = "Ngày kết thúc",
                nomalColor = Color.Gray,
                nomalFontsize = 16.sp,
                nomalFontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = formattedDateStart, onValueChange = {},
                suffix = {
                    Icon(
                        imageVector = Icons.Filled.CalendarMonth,
                        contentDescription = "Calender",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                dateStartDialog.show()
                            }
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 5.dp)
            )
            OutlinedTextField(
                value = formattedDateEnd, onValueChange = {},
                suffix = {
                    Icon(
                        imageVector = Icons.Filled.CalendarMonth,
                        contentDescription = "Calender",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                dateEndDialog.show()
                            }
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 5.dp)
            )
        }
    }
    MaterialDialog(
        dialogState = dateStartDialog,
        buttons = {
            positiveButton(text = "Ok") {
                onDatesSelected(formattedDateStart, formattedDateEnd)
            }
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a date",
            allowedDateValidator = {
                it.dayOfMonth % 2 == 1
            }
        ) {
            dateStart = it
        }
    }
    MaterialDialog(
        dialogState = dateEndDialog,
        buttons = {
            positiveButton(text = "Ok") {
                onDatesSelected(formattedDateStart, formattedDateEnd)
            }
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a date",
            allowedDateValidator = {
                it.dayOfMonth % 2 == 1
            }
        ) {
            dateEnd = it
        }
    }
}

@Composable
fun validateAllFields(
    name: String,
    percentage: String,
    maxDiscountAmount: String,
    minOrderAmount: String,
    maxUser: String,
    numberUse: String,
    description: String,
    dateStart: String,
    dateEnd: String
): Boolean {
    return listOf(
        name,
        percentage,
        maxDiscountAmount,
        minOrderAmount,
        maxUser,
        numberUse,
        description,
        dateStart,
        dateEnd
    ).all { it.isNotBlank() }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(
//    showSystemUi = true
//)
//@Composable
//fun AddDiscountScreenPreview() {
//    AddDiscountScreen()
//}