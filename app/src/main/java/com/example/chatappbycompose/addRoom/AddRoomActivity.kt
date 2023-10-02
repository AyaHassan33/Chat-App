package com.example.chatappbycompose.addRoom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.chatappbycompose.addRoom.ui.theme.ChatAppByComposeTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatappbycompose.R
import com.example.chatappbycompose.login.ChatAuthTextField



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
                .align(Alignment.CenterHorizontally),navigator=navigator)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoomCard(modifier: Modifier = Modifier,viewModel: AddRoomViewModel= viewModel(),navigator: Navigator) {
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
        ExposedDropdownMenuBox(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            expanded = viewModel.isExpanded.value, onExpandedChange = {
            viewModel.isExpanded.value = !viewModel.isExpanded.value
        }) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                readOnly = true,
                value = viewModel.selectedItem.value.name ?:"" ,
                onValueChange ={} ,
                label = { Text("Room Category") },
                leadingIcon = {
                              Image(painter = painterResource(id = viewModel.selectedItem.value.imageId!!), contentDescription = "",
                                  modifier= Modifier
                                      .width(50.dp)
                                      .height(50.dp)
                                      .padding(end = 8.dp))
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.isExpanded.value) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(containerColor = Color.Transparent),

                )
            ExposedDropdownMenu(
                expanded = viewModel.isExpanded.value,
                onDismissRequest = { viewModel.isExpanded.value = false }) {
                viewModel.categoriesList.forEach {category ->
                    DropdownMenuItem(text = {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background(
                            Color.Transparent
                        )){
                            Image(painter = painterResource(id = category.imageId!!),
                                modifier= Modifier
                                    .width(50.dp)
                                    .height(50.dp)
                                    .padding(end = 8.dp),
                                contentDescription ="Room Categeory Image" )
                            //Spacer(modifier = Modifier.width(16.dp))
                            Text(text = category.name ?: "")

                    }}, onClick = {
                        viewModel.selectedItem.value = category
                        viewModel.isExpanded.value=false
                    })

                }
            }
            
        }
        ChatAuthTextField(state =viewModel.descriptionState , label = stringResource(id = R.string.room_desc) , errorState =viewModel.descriptionErrorState )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                      viewModel.addRoomToFireStore()
            },
            modifier = Modifier
                .fillMaxWidth(0.77f)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource (id = R.color.blue), contentColor = colorResource(
                id = R.color.white
            )), shape = RoundedCornerShape(25.dp)
        )
        {
            Text(text = "Create", style = TextStyle(fontSize = 18.sp),modifier=Modifier.padding(7.dp))

        }
        Spacer(modifier = Modifier.fillMaxHeight(0.44f))

    }
    LoadingDialog()
    ChatAlertDialog(navigator = navigator)

}
@Composable
fun LoadingDialog(viewModel: AddRoomViewModel = viewModel()) {
    if(viewModel.showLoading.value)
        Dialog(onDismissRequest = {  }) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        color = colorResource(id = R.color.white),
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp),
                    color = colorResource(id = R.color.blue))

            }
        }
}
@Composable
fun ChatAlertDialog(viewModel: AddRoomViewModel = viewModel(),navigator: Navigator) {
    viewModel.navigator=navigator
    if(viewModel.message.value .isNotEmpty())
        AlertDialog(onDismissRequest = {
            viewModel.message.value = ""
        }, confirmButton = {
            TextButton(onClick = {
                viewModel.message.value = ""
                viewModel.navigatorUp()
            })
            {
                Text(text = "OK")
            }

        }, text = {
            Text(text = viewModel.message.value)
        }


        )
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