package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Greeting(
                    goToLogin = {
                      val intent = Intent(this, LoginActivity::class.java)
                      startActivity(intent)
                   }
               )
            }
        }
    }
}

@Composable
fun Greeting(goToLogin : () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
    ){
       Text(
           text = stringResource(R.string.welcome_to),
           fontSize = 45.sp,
           fontWeight = FontWeight.Bold,
           modifier = Modifier.padding(start = 30.dp)
       )
        Text(
            text = stringResource(id = R.string.quiz_app),
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.red),
            modifier = Modifier.padding(start = 30.dp)
        )
        Text(
            text = stringResource(id = R.string.get_to_know_yourself_through_our_application),
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 30.dp, end = 40.dp, start = 30.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.questions_pana_1_),
            contentDescription = stringResource(id = R.string.image),
            contentScale = ContentScale.Fit
        )
        Button(
             onClick = goToLogin,
             colors = ButtonColors(
                 contentColor = Color.White,
                 disabledContentColor = Color.Black,
                 containerColor = colorResource(id = R.color.red),
                 disabledContainerColor = Color.White,
             ),
             elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
             modifier = Modifier.padding(start = 180.dp, top = 50.dp)
        ){
             Text(
                 text = stringResource(R.string.play_quiz),
                 fontWeight = FontWeight.Bold,
                 fontSize = 22.sp,
                 modifier = Modifier.padding(20.dp, 5.dp)
             )
             Icon(Icons.AutoMirrored.Filled.ArrowForward, stringResource(R.string.button))
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting{}
    }
}