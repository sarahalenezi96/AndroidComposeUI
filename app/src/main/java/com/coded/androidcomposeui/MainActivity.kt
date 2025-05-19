package com.coded.androidcomposeui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
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
import androidx.compose.ui.text.font.FontWeight
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
    var isCorrect by remember { mutableStateOf<Boolean?>(null) }
    var score by remember { mutableStateOf(0) }

    val questions = stringArrayResource(id = R.array.questions)
    val questionAnswers = listOf(true, false, true, false)

    val currentText = questions.getOrNull(currentQuestion)
    val correctAnswer = questionAnswers.getOrNull(currentQuestion)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (currentText != null && correctAnswer != null) {
            Text(
                text = currentText,
                fontSize = 25.sp,
                lineHeight = 30.sp,
                modifier = Modifier.padding(top = 32.dp),
                textAlign = TextAlign.Center
            )

            if (showResult) {
                AnswerResultCircle(isCorrect = isCorrect ?: false)

                val isLastQuestion = currentQuestion == questions.lastIndex

                Button(onClick = {
                    if (isLastQuestion) {
                        currentQuestion++
                    } else {
                        currentQuestion++
                    }
                    showResult = false
                    isCorrect = null
                }) {
                    Text(
                        if (isLastQuestion)
                            stringResource(id = R.string.submit_button)
                        else
                            stringResource(id = R.string.next_question),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            isCorrect = true == correctAnswer
                            if (isCorrect == true) score++
                            showResult = true
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            stringResource(id = R.string.true_option),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(15.dp))

                    Button(
                        onClick = {
                            isCorrect = false == correctAnswer
                            if (isCorrect == true) score++
                            showResult = true
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            stringResource(id = R.string.false_option),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        } else {
            Text(
                text = stringResource(id = R.string.your_score, score, questions.size),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 16.dp)
            )

            Button(onClick = {
                currentQuestion = 0
                score = 0
                showResult = false
                isCorrect = null
            }) {
                Text(
                    stringResource(id = R.string.restart_game),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
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
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
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
