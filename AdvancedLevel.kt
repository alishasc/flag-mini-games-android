package com.alishacarrington.coursework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class AdvancedLevel : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdvancedLevelGame()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdvancedLevelGame(modifier: Modifier = Modifier) {
    var newRound by rememberSaveable{ mutableStateOf(true) }    // start next round when press 'Next'
    var imageOne by rememberSaveable{ mutableIntStateOf(0) }
    var imageTwo by rememberSaveable{ mutableIntStateOf(0) }
    var imageThree by rememberSaveable{ mutableIntStateOf(0) }
    var tFieldOne by rememberSaveable{ mutableStateOf("") }
    var tFieldTwo by rememberSaveable{ mutableStateOf("") }
    var tFieldThree by rememberSaveable{ mutableStateOf("") }
    // correctAnswers shown when user loses round
    var correctAnswerOne by rememberSaveable{ mutableStateOf("") }
    var correctAnswerTwo by rememberSaveable{ mutableStateOf("") }
    var correctAnswerThree by rememberSaveable{ mutableStateOf("") }
    var buttonLabel by rememberSaveable{ mutableStateOf("Submit") }    // Submit/Next

    // control whether user can edit TextField inputs
    var editableOne by rememberSaveable{ mutableStateOf(true) }
    var editableTwo by rememberSaveable{ mutableStateOf(true) }
    var editableThree by rememberSaveable{ mutableStateOf(true) }

    // changes colours of user inputs to green/red depending on if guessed correctly
    var colourOne by remember{ mutableStateOf(Color.Black) }
    var colourTwo by remember{ mutableStateOf(Color.Black) }
    var colourThree by remember{ mutableStateOf(Color.Black) }

    // correct/incorrect/answer colours
    val green = colorResource(id = R.color.green)
    val red = colorResource(id = R.color.red)
    val blue = colorResource(id = R.color.blue)

    var result by rememberSaveable{ mutableStateOf("") }    // correct/incorrect
    var resultColour by remember{ mutableStateOf(blue) }

    if (newRound) {
        // to display new images AFTER pressing button
        val newImages = ThreeNewImages()
        imageOne = newImages[0]
        imageTwo = newImages[1]
        imageThree = newImages[2]

        newRound = false
    }

    var score by rememberSaveable{ mutableIntStateOf(0) }
    val backgroundColour = Modifier.background(colorResource(id = R.color.cream))

    Column(
        modifier = backgroundColour
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val imageModifier = Modifier
            .size(height = 75.dp, width = 120.dp)
            .padding(5.dp)

        Text(text = "Score: $score",
            textAlign = TextAlign.End,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, end = 10.dp))

        Text(
            text = "Advanced Level",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 20.dp)
        )
        // one
        Row {
            Column {
                Image(
                    painterResource(id = imageOne),
                    contentDescription = null,
                    modifier = imageModifier,
                    contentScale = ContentScale.FillHeight
                )
                EditTextField(
                    value = tFieldOne,
                    onValueChanged = { tFieldOne = it },
                    editable = editableOne,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Words
                    ),
                    color = colourOne,
                    modifier = imageModifier
                )
                Text(text = correctAnswerOne,
                    color = blue,
                    modifier = Modifier.padding(start = 10.dp))
            }

            // two
            Column {
                Image(
                    painterResource(id = imageTwo),
                    contentDescription = null,
                    modifier = imageModifier,
                    contentScale = ContentScale.FillHeight
                )
                EditTextField(
                    value = tFieldTwo,
                    onValueChanged = { tFieldTwo = it },
                    editable = editableTwo,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Words
                    ),
                    color = colourTwo,
                    modifier = imageModifier
                )
                Text(text = correctAnswerTwo,
                    color = blue,
                    modifier = Modifier.padding(start = 10.dp))
            }

            // three
            Column {
                Image(
                    painterResource(id = imageThree),
                    contentDescription = null,
                    modifier = imageModifier,
                    contentScale = ContentScale.FillHeight
                )
                EditTextField(
                    value = tFieldThree,
                    onValueChanged = { tFieldThree = it },
                    editable = editableThree,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        capitalization = KeyboardCapitalization.Words
                    ),
                    color = colourThree,
                    modifier = imageModifier
                )
                Text(text = correctAnswerThree,
                    color = blue,
                    modifier = Modifier.padding(start = 10.dp))
            }
        }

        val answerOne = countries[images.indexOf(imageOne)]
        val answerTwo = countries[images.indexOf(imageTwo)]
        val answerThree = countries[images.indexOf(imageThree)]

        var correctAnswerCount by rememberSaveable{ mutableIntStateOf(0) }
        var guesses by rememberSaveable{ mutableIntStateOf(0) }

        ElevatedButton(
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.orange)),
            onClick = {
                if (buttonLabel == "Submit") {
                    guesses++
                    correctAnswerCount = 0
                    
                    if (tFieldOne == answerOne) {
                        // change colour to green
                        colourOne = green
                        correctAnswerCount++
                        editableOne = false
                    } else {
                        colourOne = red
                    }

                    if (tFieldTwo == answerTwo) {
                        colourTwo = green
                        correctAnswerCount++
                        editableTwo = false
                    } else {
                        colourTwo = red
                    }

                    if (tFieldThree == answerThree) {
                        colourThree = green
                        correctAnswerCount++
                        editableThree = false
                    } else {
                        colourThree = red
                    }

                    if (guesses == 3) {
                        // make correct answers visible in blue
                        if (tFieldOne != answerOne)
                            correctAnswerOne = answerOne
                        if (tFieldTwo != answerTwo)
                            correctAnswerTwo = answerTwo
                        if (tFieldThree != answerThree)
                            correctAnswerThree = answerThree

                        result = "INCORRECT"
                        resultColour = red
                        buttonLabel = "Next"
                    } else if (correctAnswerCount == 3) {
                        result = "CORRECT"
                        resultColour = green
                        buttonLabel = "Next"
                    }
                }
                else if (buttonLabel == "Next") {
                    // reset everything for next round
                    buttonLabel = "Submit"
                    tFieldOne = ""
                    tFieldTwo = ""
                    tFieldThree = ""
                    correctAnswerOne = ""
                    correctAnswerTwo = ""
                    correctAnswerThree = ""
                    editableOne = true
                    editableTwo = true
                    editableThree = true
                    colourOne = Color.Black
                    colourTwo = Color.Black
                    colourThree = Color.Black
                    guesses = 0
                    result = ""
                    newRound = true    // start next round
                }
            }
        ) {
            Text(text = buttonLabel)
        }

        Text(text = result,
            fontSize = 20.sp,
            color = resultColour)
    }
}

// ref: https://developer.android.com/courses/android-basics-compose/course
@Composable
fun EditTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    editable: Boolean,
    color: Color,
    modifier: Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        singleLine = true,
        enabled = editable,
        keyboardOptions = keyboardOptions,
        textStyle = TextStyle(color = color),
        modifier = modifier
    )
}
