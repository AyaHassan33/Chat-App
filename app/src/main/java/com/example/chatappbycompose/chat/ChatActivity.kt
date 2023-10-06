package com.example.chatappbycompose.chat

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatappbycompose.R
import com.example.chatappbycompose.chat.ui.theme.ChatAppByComposeTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatappbycompose.model.Constants
import com.example.chatappbycompose.model.DataUtils
import com.example.chatappbycompose.model.Message
import com.example.chatappbycompose.model.Room
import java.text.SimpleDateFormat
import java.util.Date

class ChatActivity : ComponentActivity(),Navigator {
    lateinit var room:Room
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        room = intent.getParcelableExtra(Constants.EXTRA_ROOM,Room::class.java)!!
        setContent {
            ChatAppByComposeTheme {
                // A surface container using the 'background' color from the theme
                ChatScreenContent(room = room, navigator = this)
            }
        }
    }

    override fun navigatorUp() {
        finish()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreenContent(room:Room, viewModel: ChatViewModel= viewModel(),navigator: Navigator) {
    viewModel.navigator=navigator
    viewModel.room =room
    viewModel.getMessagesFromFirestore()
    Scaffold(
        topBar =
        {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, start = 9.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween)
            {
                IconButton(onClick = { viewModel.navigatorUp() }) {
                    Icon(painter = painterResource(id = R.drawable.ic_back),
                        contentDescription ="icon Back",

                    )
                    
                }
                Text(text = room.name?:"not found name", fontSize = 21.sp)
                IconButton(onClick = { }) {
                   Icon(painter = painterResource(id = R.drawable.ic_settings),
                       contentDescription = "icon settings",
                       modifier = Modifier
                           .width(50.dp)
                           .height(50.dp))

                    
                }
            }
        }, contentColor = colorResource(id = R.color.white),
        bottomBar = {
            ChatSendMessageBar()
        }
    )

    {
        Column(modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
            .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()))
        {
            ChatCrazyColumn()
        }
    }
    
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatSendMessageBar(viewModel: ChatViewModel=viewModel()) {
    Row(modifier = Modifier.padding(bottom = 15.dp, start = 9.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        OutlinedTextField(value = viewModel.messageFieldState.value , onValueChange ={
            viewModel.messageFieldState.value = it },
            shape = RoundedCornerShape(topEnd = 10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.Transparent),
            label = {
                Text(text = "Type A Message")
            }
        )
        Button(onClick = {viewModel.addMessageToFireStore()}, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue), contentColor = colorResource(
                id = R.color.white
            ))
            , shape = RoundedCornerShape(5.dp)
            ) {
            Text(text = stringResource(R.string.send) )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(painter = painterResource(id = R.drawable.ic_send), contentDescription = "Icon Send")
        }
    }
    
}

@Composable
fun ChatCrazyColumn(viewModel: ChatViewModel= viewModel()) {
    LazyColumn(modifier = Modifier.fillMaxSize(), reverseLayout = true){
        items(viewModel.messagesListState.value.size){
            val item = viewModel.messagesListState.value.get(it)
            if (item.senderId ==DataUtils.appUser?.id){
                SendMessageRow(message = item)

            }else{
                ReceivedMessageRow(message = item)
                
            }
        }
    }
    
}

@Composable
fun SendMessageRow(message : Message) {
    val date = Date(message.dateTime ?: 0)
    val simpleTimeFormate = SimpleDateFormat("hh:mm a")
    val dateString  = simpleTimeFormate.format(date)
    Row(horizontalArrangement = Arrangement.End, modifier = Modifier
        .fillMaxWidth()
        .padding(7.dp)) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = dateString,
            style = TextStyle(color = colorResource(id = R.color.black)),
            modifier = Modifier
                .align(Alignment.Bottom)
                .padding(end = 4.dp)
        )
        Text(text = message.content?:"", modifier = Modifier
            .background(
                colorResource(id = R.color.blue),
                shape = RoundedCornerShape
                    (
                    topStart = 24.dp,
                    topEnd = 24.dp,
                    bottomStart = 24.dp,
                    bottomEnd = 0.dp
                )
            )
            .padding(vertical = 8.dp, horizontal = 16.dp),
            style = TextStyle(color = colorResource(id = R.color.white), fontSize = 18.sp)

        )
    }
}
@Composable
fun ReceivedMessageRow(message : Message) {
    val date = Date(message.dateTime ?: 0)
    val simpleTimeFormate = SimpleDateFormat("hh:mm a")
    val dateString  = simpleTimeFormate.format(date)
    Spacer(modifier = Modifier.height(20.dp))
    Column {

        Text(
            text = message.senderName ?: "",
            style = TextStyle(color = colorResource(id = R.color.dark_gray)),
            modifier = Modifier.padding(start= 14.dp, bottom = 4.dp)
        )
        Row(horizontalArrangement = Arrangement.Start, modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp)) {
            Text(text = message.content?:"", modifier = Modifier
                .background(
                    colorResource(id = R.color.receive_color),
                    shape = RoundedCornerShape
                        (
                        topStart = 24.dp,
                        topEnd = 24.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 24.dp
                    )
                )
                .padding(vertical = 8.dp, horizontal = 16.dp),
                style = TextStyle(color = colorResource(id = R.color.dark_gray), fontSize = 18.sp)

            )
            Text(
                text = dateString,
                style = TextStyle(color = colorResource(id = R.color.black)),
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(start = 4.dp)
            )
        }
    }

}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview5() {
    ChatAppByComposeTheme {
        ChatScreenContent(Room(name = "Hello world"), navigator = object :Navigator{
            override fun navigatorUp() {

            }

        })
    }
}