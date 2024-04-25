package com.example.fooddelivery.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fooddelivery.R
import com.example.fooddelivery.ui.theme.category_btn_0

@Composable
fun NormalTextComponents(
    modifier: Modifier = Modifier,
    value: String,
    nomalColor: Color = Color.White,
    nomalFontsize: TextUnit = 14.sp,
    nomalFontWeight: FontWeight = FontWeight.Normal,
    nomalTextAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = value,
        style = TextStyle(
            color = nomalColor,
            fontSize = nomalFontsize,
            fontWeight = nomalFontWeight
        ),
        modifier = modifier,
        textAlign = nomalTextAlign
    )
}

@Composable
fun MyTextFieldComponents(
    lableText: String,
    errorStatus: Boolean = false,
    onTextSelected: (String) -> Unit
) {
    var textValue by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = textValue, onValueChange = {
            textValue = it
            onTextSelected(it)
        },
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier.padding(top = 32.dp, start = 32.dp, end = 32.dp),
        label = {
            Text(text = lableText)
        },
        colors = TextFieldDefaults.colors(
            disabledContainerColor = Color.LightGray,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            unfocusedIndicatorColor = Color.Blue,
            focusedIndicatorColor = Color.Blue,
            disabledIndicatorColor = Color.Red,
            disabledLeadingIconColor = Color.Red,
            focusedLeadingIconColor = Color.Black,
            unfocusedLeadingIconColor = Color.Black,
            disabledLabelColor = Color.Red,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black
        ),
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Email, contentDescription = "Phone_login")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        isError = !errorStatus
    )
}

@Composable
fun MyPasswordTextFieldComponents(
    lableText: String,
    errorStatus: Boolean = false,
    onTextSelected: (String) -> Unit
) {
    var textPassWord by remember {
        mutableStateOf("")
    }
    var passWordVisibility by remember {
        mutableStateOf(false)
    }
    val iconVisibility = if (passWordVisibility) {
        painterResource(id = R.drawable.visibility)
    } else {
        painterResource(id = R.drawable.unvisible)
    }
    val iconDescription = if (passWordVisibility) {
        stringResource(id = R.string.Show_password)
    } else {
        stringResource(id = R.string.Hide_pass_word)
    }
    val localFocusManager = LocalFocusManager.current
    OutlinedTextField(
        value = textPassWord,
        onValueChange = {
            textPassWord = it
            onTextSelected(it)
        },
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier.padding(top = 32.dp, start = 32.dp, end = 32.dp),
        label = {
            Text(
                text = lableText
            )
        },
        colors = TextFieldDefaults.colors(
            disabledContainerColor = Color.LightGray,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            unfocusedIndicatorColor = Color.Blue,
            focusedIndicatorColor = Color.Blue,
            disabledIndicatorColor = Color.Red,
            disabledTrailingIconColor = Color.Red,
            focusedTrailingIconColor = Color.Black,
            unfocusedTrailingIconColor = Color.Black,
            disabledLabelColor = Color.Red,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black
        ),
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = {
                passWordVisibility = !passWordVisibility
            }) {
                Icon(
                    painter = iconVisibility, contentDescription = iconDescription,
                    modifier = Modifier.width(30.dp)
                )
            }
        },
        visualTransformation = if (passWordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                localFocusManager.clearFocus()
            }
        ),
        isError = !errorStatus
    )
}

@Composable
fun ButtonComponents(
    value: String, modifier: Modifier = Modifier,
    isEnable: Boolean = false,
    onButtonClicked: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 32.dp, start = 55.dp, end = 55.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red
        ),
        enabled = isEnable,
        onClick = {
            onButtonClicked.invoke()
        }) {
        Text(
            text = value,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun DrawLineAndTextComponents() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 12.dp, start = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            thickness = 1.dp,
            color = Color.Red
        )
        NormalTextComponents(
            value = stringResource(id = R.string.or),
            nomalFontsize = 18.sp,
            nomalFontWeight = FontWeight.Normal,
            nomalColor = Color.Black
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            thickness = 1.dp,
            color = Color.Red
        )
    }
}

@Composable
fun IconButtonWithText(
    backgroundColor: Color = category_btn_0,
    @DrawableRes iconId: Int = R.drawable.btn_1,
    @StringRes textId: Int = R.string.Pizza,
    eventOnclick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = backgroundColor)
                .padding(12.dp)
        ) {
            IconButton(onClick = { eventOnclick() }) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = stringResource(
                        id = R.string.Pizza
                    )
                )
            }
        }
        NormalTextComponents(
            value = stringResource(id = textId),
            nomalFontsize = 18.sp,
            nomalColor = Color.Black,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun ComponentPreview() {
    IconButtonWithText {}
}