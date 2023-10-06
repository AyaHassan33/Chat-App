package com.example.chatappbycompose.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.example.chatappbycompose.chat.ChatActivity
import com.example.chatappbycompose.model.Category
import com.example.chatappbycompose.model.Constants
import com.example.chatappbycompose.model.Room

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

    override fun navigateToChatScreen(room: Room) {
        val intent =Intent(this@HomeActivity,ChatActivity::class.java)
        intent.putExtra(Constants.EXTRA_ROOM,room)
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
            )
            .padding(top = it.calculateTopPadding())){
            ChatRoomLazyGrid(navigator = navigator)

        }

    }
}

@Composable
fun ChatRoomLazyGrid(
    viewModel: HomeViewModel= viewModel(),
    navigator: Navigator
) {
    viewModel.navigator=navigator
    viewModel.getRoomsFromFirestore()
    LazyVerticalGrid(columns = GridCells.Fixed(2) ){
        items(viewModel.roomsListState.value.size){position->
            ChatRoomCard(room = viewModel.roomsListState.value.get(position), navigator = navigator)
        }
    }

    
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomCard(room:Room,viewModel: HomeViewModel = viewModel(),navigator: Navigator) {
    Card( colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
        shape = RoundedCornerShape(12.dp),
        onClick = {
                 viewModel.navigateToChatScreen(room= room)
        }
        ,modifier = Modifier.padding(vertical = 7.dp, horizontal = 20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        )
    {
        Image(painter = painterResource(id = Category.fromId(room.categoryID?:Category.MUSIC).imageId?:R.drawable.music),
            contentDescription ="Room category Image",
            modifier = Modifier
                .height(100.dp)
                .padding(13.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.FillHeight
        )
        Text(text = room.name ?:"",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp),
            style = TextStyle(textAlign = TextAlign.Center, fontSize = 14.sp)
        )
    }
    
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview3() {
    ChatAppByComposeTheme {
        HomeContent(navigator = object :Navigator{
            override fun navigateToAddRoomScreen() {

            }

            override fun navigateToChatScreen(room: Room) {

            }

        })
    }
}