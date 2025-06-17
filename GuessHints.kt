package com.alishacarrington.coursework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class GuessHints : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuessHintsGame()
        }
    }
}

var dashList = mutableListOf<Char>()
var guesses = 0
var newRound = true
var correctAnswer = ""

@Preview(showBackground = true)
@Composable
fun GuessHintsGame(modifier: Modifier = Modifier) {
    var image by rememberSaveable{ mutableIntStateOf(0) }
    var imageIndex by rememberSaveable{ mutableIntStateOf(0) }
    var userInput by rememberSaveable{ mutableStateOf("") }
    var country by rememberSaveable{ mutableStateOf("") }    // correct country name
    var dashes by rememberSaveable{ mutableStateOf("") }    // dashes that match country
    var buttonLabel by rememberSaveable{ mutableStateOf("Submit") }    // "Submit" or "Next"

    val backgroundColour = Modifier.background(colorResource(id = R.color.cream))

    // set the initial image and blank spaces
    if (newRound) {
        imageIndex = ChooseNewImage()    // Int
        image = images[imageIndex]    // select an image to show
        country = countries[imageIndex].uppercase()    // select matching country

        for (i in country) {
            dashes += "-"    // initial empty dashes
            dashList.add('-')    // list of Chars
        }

        newRound = false    // make true for next round
    }

    Column(
        modifier = backgroundColour
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Guess-Hints",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 20.dp)
        )

        Image(
            painterResource(id = image),
            contentDescription = "Image of flag",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
        )

        Text(text = dashes, fontSize = 40.sp)

        OutlinedTextField(
            value = userInput,
            onValueChange = {
                // can only enter one letter at a time
                if (it.length <= 1)
                    userInput = it
            },
            label = { Text("Enter a letter") },
            // ref: https://developer.android.com/courses/android-basics-compose/course
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
        )

        // correct/incorrect/answer colours
        val green = colorResource(id = R.color.green)
        val red = colorResource(id = R.color.red)
        val blue = colorResource(id = R.color.blue)

        var result by rememberSaveable{ mutableStateOf("") }    // correct/incorrect
        var resultColour by remember{ mutableStateOf(blue) }    // changes to green/red

        // submit button
        ElevatedButton(
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.orange)),
            onClick = {
                if (buttonLabel == "Submit") {
                    dashes = checkLetter(country, dashList, userInput)    // check if letter is in country

                    if (dashes == country && guesses != 3) {    // win round
                        result = "CORRECT"
                        resultColour = green
                        buttonLabel = "Next"
                    } else if (guesses == 3) {    // lose round
                        result = "INCORRECT"
                        resultColour = red
                        buttonLabel = "Next"
                        correctAnswer = country
                    }
                } else if (buttonLabel == "Next") {
                    buttonLabel = "Submit"
                    // reset everything for new round
                    dashes = ""
                    dashList = mutableListOf()
                    correctAnswer = ""
                    result = ""
                    guesses = 0
                    // start new round
                    newRound = true
                }

                userInput = ""    // remove letter from TextField for next guess
            }
        ) {
            Text(text = buttonLabel)    // "Submit" as default - changes to "Next"
        }

        // correct/incorrect
        Text(text = result,
            fontSize = 20.sp,
            color = resultColour)

        // show correct answer if incorrect
        Text(text = correctAnswer,
            fontSize = 20.sp,
            color = blue)
    }
}

fun checkLetter(country: String, dashList: MutableList<Char>, userInput: String): String {
    val inputChar = userInput.single()
    var returnedAnswer = ""

    // ref: https://www.techiedelight.com/find-all-occurrences-of-character-in-a-kotlin-string/
    var index = country.indexOf(inputChar)    // first occurrence of the letter (Int)
    while (index != -1) {
        dashList[index] = inputChar
        index = country.indexOf(inputChar, index + 1)
    }

    for (i in dashList) {
        returnedAnswer += i
    }

    if (inputChar !in country && guesses < 3) {
        guesses++
    }

    return returnedAnswer
}