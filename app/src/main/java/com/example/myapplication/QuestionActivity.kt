package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.User
import com.example.myapplication.data.answer
import com.example.myapplication.data.questionList
import com.example.myapplication.ui.theme.MyApplicationTheme

class QuestionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val user = User(intent.getStringExtra("name")?: "", intent.getStringExtra("number")?: "", intent.getStringExtra("email")?: "")
                QuizView(
                    backToHome = {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    },
                    mutableListOf(),
                    user
                )
            }
        }
    }
}

fun calculateSum(scoreList: MutableList<Int>, finalScore: MutableMap<String, Int>): List<Pair<String, Int>>{
    finalScore["Bienfaiteur"] = scoreList.slice(0..7).sum()
    finalScore["Insoumis"] = scoreList.slice(8..15).sum()
    finalScore["Visionnaire"] = scoreList.slice(16..23).sum()
    finalScore["Célébrité"] = scoreList.slice(24..31).sum()
    finalScore["Médiateur"] = scoreList.slice(32..39).sum()
    finalScore["Conservateur"] = scoreList.slice(40..47).sum()
    finalScore["Épicurien"] = scoreList.slice(48..55).sum()
    finalScore["Leader"] = scoreList.slice(56..63).sum()

    return finalScore.toList().sortedBy { (_, value) -> value }.reversed()
}

@Composable
fun QuizView(backToHome: () -> Unit, scoreList: MutableList<Int>, user: User){
    var openAlertDialog by remember { mutableStateOf(false) }
    var i by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .background(color = colorResource(id = R.color.red))
                .fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(horizontal = 25.dp, vertical =  50.dp)
            ) {
                Text(
                    text = user.name.replaceFirstChar { it.uppercaseChar() },
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                Text(
                    text = user.number,
                    color = Color.White
                )
                Text(
                    text = user.email,
                    color = Color.White
                )
            }
            Image(
                painter = painterResource(id = R.drawable.profile_interface_rafiki_1_),
                contentDescription = stringResource(id = R.string.image),
                modifier = Modifier.size(150.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp)
        ) {
            Text(
                text = stringResource(R.string.questions_out_of_64, i),
                fontSize = 18.sp,
                modifier = Modifier.padding(top =  50.dp, bottom = 10.dp)
            )
            Text(
                text =  questionList[i],
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(20.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            answer.forEach{ option ->
                ElevatedCard(
                    onClick = {
                        selectedOption = option
                        scoreList.add(answer.indexOf(selectedOption) + 1)
                        if(i < 63 )
                            i++
                        else if(i == 63)
                            openAlertDialog = true
                    },
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                )
                {
                    Text (
                        text = option,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                    )
                }
            }
        }
        if (openAlertDialog){
            AlertDialog(
                onDismissRequest = {
                    openAlertDialog = true
                },
                confirmButton = {
                    openAlertDialog = true
                },
                title = {
                   Column {
                       Image(
                           painter = painterResource(id = R.drawable.think_different_rafiki),
                           contentDescription = stringResource(id = R.string.image)
                       )
                       Text(
                           text = stringResource(R.string.vos_meilleures_profils),
                           textAlign = TextAlign.Center,
                           modifier = Modifier.fillMaxWidth()
                       )
                   }
                },

                text = {
                    val res = calculateSum(scoreList, mutableMapOf())
                    Column {
                        Text(
                            text = user.name.replaceFirstChar { it.uppercaseChar() },
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 20.dp)
                        )
                        Text(
                            text = stringResource(R.string.top_3),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.red)
                        )
                        res.slice(0..2).forEach{
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                            ) {
                                Text(
                                    text = it.first,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = stringResource(R.string.pts, it.second),
                                    fontSize = 16.sp
                                )
                            }
                        }
                        Text(
                            text = stringResource(R.string.dernier_profil),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.red),
                            modifier =  Modifier.padding(top = 16.dp)

                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        ) {
                            Text(
                                text = res.last().first,
                                fontSize = 16.sp
                            )
                            Text(
                                text = stringResource(R.string.pts, res.last().second),
                                fontSize = 16.sp
                            )
                        }
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            openAlertDialog = false
                            backToHome()
                        },
                        colors = ButtonColors(
                            contentColor = Color.White,
                            disabledContentColor = Color.Black,
                            containerColor = colorResource(id = R.color.red),
                            disabledContainerColor = Color.White,
                        ),
                    ){
                        Text(text = stringResource(R.string.revenir_l_accueil))
                    }
                },
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp).background(Color.White)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    MyApplicationTheme {
        QuizView({}, mutableListOf(), User("","",""))
    }
}