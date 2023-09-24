package com.example.chatappbycompose.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.chatappbycompose.R
import com.example.chatappbycompose.login.LoginActivity
import com.example.chatappbycompose.register.RegisterActivity
import com.example.chatappbycompose.ui.theme.ChatAppByComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppByComposeTheme {
                // A surface container using the 'background' color from the theme
                Handler(Looper.getMainLooper()).postDelayed({
                               val intent =Intent(this@MainActivity,LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                },2500)
                SplashContent()

            }
        }
    }
}

@Composable
fun SplashContent() {
    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        val (logo,signature) =createRefs()
        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription ="logo" ,
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxSize(0.35f)
        )
        Image(painter = painterResource(id = R.drawable.signature),
            contentDescription = "App Signature",
            modifier = Modifier
                .constrainAs(signature) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.2f)
            )

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ChatAppByComposeTheme {
        SplashContent()

    }
}