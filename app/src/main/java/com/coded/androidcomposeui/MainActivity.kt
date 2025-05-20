package com.coded.androidcomposeui

import android.media.MediaPlayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
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
var score =0

@Composable
fun QuizGame(modifier: Modifier = Modifier) {
    var currentQuestion by remember { mutableStateOf(0) }
    var showResult by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf<Boolean?>(null) }

    val context = LocalContext.current

    fun playSound(resId: Int) {
        val mediaPlayer = MediaPlayer.create(context, resId)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            it.release()
        }
    }

    val questions = stringArrayResource(id = R.array.questions)
    val questionAnswers = listOf(true, false, true, false)

    val currentText = questions.getOrNull(currentQuestion)
    val correctAnswer = questionAnswers.getOrNull(currentQuestion)

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
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
                    color = Color(0xFF1A237E),
                    modifier = Modifier.padding(top = 32.dp),
                    textAlign = TextAlign.Center
                )

                if (showResult) {
                    AnswerResultCircle(isCorrect = isCorrect ?: false)

                    val isLastQuestion = currentQuestion == questions.lastIndex

                    Button(
                        onClick = {
                            currentQuestion++
                            showResult = false
                            isCorrect = null
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = if (isLastQuestion)
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
                                if (isCorrect == true) {
                                    playSound(R.raw.correct)
                                    score++
                                } else {
                                    playSound(R.raw.wrong)
                                }
                                showResult = true
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
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
                                if (isCorrect == true) {
                                    playSound(R.raw.correct)
                                    score++
                                } else {
                                    playSound(R.raw.wrong)
                                }
                                showResult = true
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
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
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.your_score, score, questions.size),
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = Color(0xFF1A237E),
                        textAlign = TextAlign.Center
                    )
                }

                Button(
                    onClick = {
                        currentQuestion = 0
                        showResult = false
                        isCorrect = null
                        score = 0
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        stringResource(id = R.string.restart_game),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}


@Composable
fun AnswerResultCircle(isCorrect: Boolean) {
    val backgroundColor = MaterialTheme.colorScheme.primary
    val resultText = if (isCorrect) "Correct Answer" else "Wrong Answer"
    val imageRes = if (isCorrect) R.drawable.correct_answer else R.drawable.wrong_answer

    Box(
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = resultText,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun QuizGamePreview() {
    AndroidComposeUITheme {
        QuizGame()
    }
}
