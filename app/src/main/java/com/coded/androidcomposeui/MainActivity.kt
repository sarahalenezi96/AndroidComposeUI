package com.coded.androidcomposeui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import com.coded.androidcomposeui.ui.theme.AndroidComposeUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidComposeUITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuizGame(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun QuizGame(modifier: Modifier = Modifier) {
    var currentQuestion by remember { mutableStateOf(0) }
    var showResult by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }

    val questions = listOf(
        "Android is an operating system." to true,
        "Kotlin is officially supported for Android development." to true
    )

    val (text, answer) = questions[currentQuestion]

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 32.dp),
            textAlign = TextAlign.Center
        )

        if (showResult) {
            AnswerResultCircle(isCorrect = isCorrect)

            Button(onClick = {
                if (currentQuestion < questions.size - 1) {
                    currentQuestion++
                    showResult = false }
            })
            {
                Text("Next Question")
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        isCorrect = true == answer
                        showResult = true
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("True")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        isCorrect = false == answer
                        showResult = true
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("False")
                }
            }
        }
    }
}

@Composable
fun AnswerResultCircle(isCorrect: Boolean) {
    val backgroundColor = if (isCorrect) Color.Green else Color.Red
    val resultText = if (isCorrect) "Correct Answer" else "Wrong Answer"

    Box(
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = resultText,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuizGamePreview() {
    AndroidComposeUITheme {
        QuizGame()
    }
}
