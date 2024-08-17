package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.example.myapplication.data.User
import com.example.myapplication.ui.theme.MyApplicationTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                LoginView { user ->
                    val intent = Intent(this, QuestionActivity::class.java).apply {
                        putExtra("name", user.name)
                        putExtra("number", user.number)
                        putExtra("email", user.email)
                    }
                    startActivity(intent)
                }
            }
        }
    }
}

fun checkNameValidity(name: String): Boolean{
    return name.isNotEmpty()
}

fun checkNumberValidity(number: String): Boolean{
    return number.trim().length == 10 && number.any{it.isDigit()} && !number.contains("-") && !number.contains(".")
}

fun checkEmailValidity(email: String) : Boolean{
    return Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
}


@Composable
fun UserInputField(text : String = "", valueOnChange : (String) -> Unit = {}, label: String = "", errorMsg: String = "", bool: Boolean){
    Column {
        OutlinedTextField(
            value = text,
            onValueChange = valueOnChange,
            label = {
                Text(text = label)
            }
        )
        if(!bool){
            Text(
                text = errorMsg,
                color = colorResource(id = R.color.red),
                modifier = Modifier.padding(top = 3.dp)
            )
        }
    }
}

@Composable
fun LoginView(submitClick: (User) -> Unit = {}){
    var name by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Image(painter = painterResource(
            id = R.drawable.account),
            contentDescription = stringResource(id = R.string.image),
            contentScale = ContentScale.Fit,
        )
        Text(
            text = stringResource(id = R.string.login),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(25.dp))
        UserInputField(
            text = name,
            valueOnChange = { name = it },
            label = stringResource(R.string.username),
            bool = checkNameValidity(name),
            errorMsg = stringResource(R.string.username_empty)
        )
        Spacer(modifier = Modifier.height(12.dp))
        UserInputField(
            text = number,
            valueOnChange = { number = it },
            label = stringResource(R.string.phone_number),
            bool = checkNumberValidity(number),
            errorMsg = stringResource(R.string.number_invalid)
        )
        Spacer(modifier = Modifier.height(12.dp))
        UserInputField(
            text = email,
            valueOnChange = { email = it },
            label = stringResource(R.string.email),
            bool = checkEmailValidity(email),
            errorMsg = stringResource(R.string.email_invalid)
        )
        Spacer(modifier = Modifier.height(25.dp))
        Button(
            onClick = {
                if(checkNameValidity(name) && checkNumberValidity(number) && checkEmailValidity(email)){
                    submitClick(User(name, number, email))
                }
            },
            colors = ButtonColors(
                contentColor = Color.White,
                disabledContentColor = Color.White,
                containerColor = colorResource(id = R.color.red),
                disabledContainerColor = colorResource(id = R.color.red),
            ),
        ) {
            Text(
                text = stringResource(R.string.submit),
                fontSize = 20.sp,
                modifier = Modifier.padding(90.dp, 6.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    MyApplicationTheme {
        LoginView{}
    }
}