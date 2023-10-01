package com.example.chatappbycompose.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatappbycompose.R
import com.example.chatappbycompose.home.ui.theme.ChatAppByComposeTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatappbycompose.addRoom.AddRoomActivity

class HomeActivity : ComponentActivity(),Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppByComposeTheme {
                HomeContent(navigator = this)
            }
        }
    }

    override fun navigateToAddRoomScreen() {
        val intent = Intent(this@HomeActivity,AddRoomActivity::class.java)
        startActivity(intent)
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(viewModel: HomeViewModel = viewModel(),navigator: Navigator) {
    viewModel.navigator=navigator
    Scaffold(
        topBar = {
            Text(text = stringResource(R.string.home), modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
                style = TextStyle(color = colorResource(id = R.color.white),
                    textAlign = TextAlign.Center , fontSize = 21.sp)

            )
        },
       floatingActionButton = {
           FloatingActionButton(
               onClick = {
                   viewModel.navigateToAddRoomScreen()
           }, contentColor = Color.White, containerColor = colorResource(
               id = R.color.blue), shape = CircleShape.apply { RoundedCornerShape(0.5f) },
               modifier = Modifier.padding(15.dp)
           ) {
               Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription ="",
                   modifier = Modifier.padding(18.dp) )
           }
       }
    ){
        Column(modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )){

        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview3() {
    ChatAppByComposeTheme {
        HomeContent(navigator = object :Navigator{
            override fun navigateToAddRoomScreen() {

            }

        })
    }
}