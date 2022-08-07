package com.example.customtopappbar

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.customtopappbar.util.SearchAppBarState
import com.example.customtopappbar.util.SharedViewModel
import com.example.customtopappbar.util.TrailingIconState

@Composable
fun CustomTopAppBar(
    sharedViewModel: SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String
) {
    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultTopAppBar(
                onSearchClicked = {
                    sharedViewModel.searchAppBarState.value =
                        SearchAppBarState.OPENED
                }
            )
        }
        else -> {
            SearchTopAppBar(
                text = searchTextState,
                onTextChange = { text ->
                    sharedViewModel.searchTextState.value = text
                },
                onCloseClicked = {
                    sharedViewModel.searchAppBarState.value =
                        SearchAppBarState.CLOSED
                    sharedViewModel.searchTextState.value = ""
                },
                onSearchClicked = {}
            )
        }
    }
}

@Composable
fun DefaultTopAppBar(
    onSearchClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.ic_top_app_bar_bg),
            contentDescription = "background_image",
            contentScale = ContentScale.FillBounds
        )
        TopAppBar(
            modifier = Modifier.padding(top = 24.dp),
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            title = {
                Text(
                    text = "AppBar",
                    color = Color.White
                )
            },
            actions = {
                AppBarActions(
                    onSearchClicked = onSearchClicked
                )
            }
        )
    }
}

@Composable
fun AppBarActions(
    onSearchClicked: () -> Unit
) {
    SearchAction(onSearchClicked = onSearchClicked)
    ShareAction()
    MoreAction()
}

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit
) {
    val context = LocalContext.current
    IconButton(
        onClick = {
            onSearchClicked()
        }
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "search_icon",
            tint = Color.White
        )
    }
}

@Composable
fun ShareAction() {
    val context = LocalContext.current
    IconButton(
        onClick = {
            Toast.makeText(context, "Share Clicked!", Toast.LENGTH_SHORT).show()
        }
    ) {
        Icon(
            imageVector = Icons.Filled.Share,
            contentDescription = "search_icon",
            tint = Color.White
        )
    }
}

@Composable
fun MoreAction() {
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(
        onClick = {
            expanded = true
        }
    ) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = "search_icon",
            tint = Color.White
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = { expanded = false }
            ) {
                Text(text = "Profile")
            }
            DropdownMenuItem(
                onClick = { expanded = false }
            ) {
                Text(text = "Setting")
            }
            DropdownMenuItem(
                onClick = { expanded = false }
            ) {
                Text(text = "Help & Feedback")
            }
        }
    }
}

@Composable
fun SearchTopAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {

    var trailingIconState by remember {
        mutableStateOf(TrailingIconState.DELETE)
    }

    Box(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.ic_top_app_bar_bg),
            contentDescription = "background_image",
            contentScale = ContentScale.FillBounds
        )

        Surface(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxSize(),
            color = Color.Transparent,
            elevation = 0.dp
        ) {
            TextField(
                modifier = Modifier.fillMaxSize(),
                value = text,
                onValueChange = {
                    onTextChange(it)
                },
                placeholder = {
                    Text(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium),
                        text = stringResource(id = R.string.search),
                        color = Color.White
                    )
                },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp
                ),
                singleLine = true,
                leadingIcon = {
                    IconButton(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium),
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(
                                id = R.string.search_icon
                            ),
                            tint = Color.White
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = {
                        when (trailingIconState) {
                            TrailingIconState.DELETE -> {
                                onTextChange("")
                                trailingIconState = TrailingIconState.CLOSE
                            }
                            TrailingIconState.CLOSE -> {
                                if (text.isNotEmpty()) {
                                    onTextChange("")
                                } else {
                                    onCloseClicked()
                                    trailingIconState = TrailingIconState.DELETE
                                }
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(id = R.string.close_icon),
                            tint = Color.White
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchClicked(text)
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = Color.Transparent
                )
            )
        }

    }
}

@Preview
@Composable
fun CustomAppBarPreview() {
    DefaultTopAppBar(
        onSearchClicked = {}
    )
}

@Preview
@Composable
fun SearchAppBarPreview() {
    SearchTopAppBar(
        text = "",
        onTextChange = {},
        onCloseClicked = { },
        onSearchClicked = {}
    )
}














