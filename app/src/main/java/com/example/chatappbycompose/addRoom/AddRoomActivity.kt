package com.example.chatappbycompose.addRoom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatappbycompose.addRoom.ui.theme.ChatAppByComposeTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatappbycompose.R
import com.example.chatappbycompose.login.ChatAuthTextField
import androidx.lifecycle.viewmodel.compose.viewModel

class AddRoomActivity : ComponentActivity(),Navigator{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppByComposeTheme {
                AddRoomContent(navigator = this)
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
fun AddRoomContent(viewModel: AddRoomViewModel = viewModel(),navigator: Navigator) {
    viewModel.navigator=navigator
    Scaffold(
        topBar = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
                verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.navigatorUp() }) {
                    Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription ="icon_back", tint = colorResource(
                        id = R.color.white))
                    
                }
                Text(
                    text = stringResource(R.string.chat_app),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 21.sp,
                    style = TextStyle(color = colorResource(id = R.color.white))
                )
            }
        }
    ){
        Column(modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
            .padding(top = it.calculateTopPadding()))
        {
            Spacer(modifier = Modifier.fillMaxHeight(0.10f))
            AddRoomCard(modifier = Modifier
                .fillMaxWidth(0.85f)
                .align(Alignment.CenterHorizontally))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoomCard(modifier: Modifier = Modifier,viewModel: AddRoomViewModel= viewModel()) {
    Card(
        colors = CardDefaults.cardColors(colorResource(id = R.color.white)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier, shape = RoundedCornerShape(12.dp)
    ){
        Text(text = stringResource(R.string.create_new_room), style = TextStyle(fontSize = 18.sp,
            color = colorResource(id = R.color.black2), fontWeight = FontWeight.Bold),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.add_room_image),
            contentDescription ="logo",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(160.dp)
                .padding(vertical = 16.dp),
            contentScale = ContentScale.FillWidth
        )
        ChatAuthTextField(state =viewModel.titleState , label = stringResource(id = R.string.room_title) , errorState =viewModel.titleErrorState)
        ExposedDropdownMenuBox(expanded = viewModel.isExpanded.value, onExpandedChange = {
            viewModel.isExpanded.value = !viewModel.isExpanded.value
        }) {
            
        }
        ChatAuthTextField(state =viewModel.descriptionState , label = stringResource(id = R.string.room_desc) , errorState =viewModel.descriptionErrorState )
        Button(
            onClick = {

            },
            modifier = Modifier
                .fillMaxWidth(0.77f)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource (id = R.color.blue), contentColor = colorResource(
                id = R.color.white
            )),
        )
        {
            Text(text = "Create")

        }

    }
    
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview4() {
    ChatAppByComposeTheme {
        AddRoomContent(navigator = object :Navigator{
            override fun navigatorUp() {

            }

        })

    }
}