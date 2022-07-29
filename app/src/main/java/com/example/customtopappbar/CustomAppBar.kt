package com.example.customtopappbar

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomAppBar() {
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
                AppBarActions()
            }
        )
    }
}

@Composable
fun AppBarActions() {
    SearchAction()
    ShareAction()
    MoreAction()
}

@Composable
fun SearchAction() {
    val context = LocalContext.current
    IconButton(
        onClick = {
            Toast.makeText(context, "Search Clicked!", Toast.LENGTH_SHORT).show()
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
    val context = LocalContext.current
    IconButton(
        onClick = {
            Toast.makeText(context, "More Clicked!", Toast.LENGTH_SHORT).show()
        }
    ) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = "search_icon",
            tint = Color.White
        )
    }
}

@Preview
@Composable
fun CustomAppBarPreview() {
    CustomAppBar()
}














