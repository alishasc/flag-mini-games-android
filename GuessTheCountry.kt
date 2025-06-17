package com.alishacarrington.coursework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class GuessTheCountry : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuessCountryGame()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GuessCountryGame(modifier: Modifier = Modifier) {
    var imageShown by rememberSaveable{ mutableIntStateOf(0) }    // id of image shown
    var imageIndex by rememberSaveable { mutableIntStateOf(0) }    // Int returned from ChooseNewImage()
    var selected by rememberSaveable{ mutableStateOf("") }    // country name user selects from list
    var newRound by rememberSaveable { mutableStateOf(true) }    // true when user presses 'Next'
    var buttonLabel by rememberSaveable{ mutableStateOf("Submit") }    // alternate button text between 'Submit' and 'Next'
    var correctAnswer by rememberSaveable { mutableStateOf("") }    // country that matches flag shown

    val backgroundColour = Modifier.background(colorResource(id = R.color.cream))

    // sets the first image and then changes image when press "Next"
    if (newRound) {
        imageIndex = ChooseNewImage()    // Int value
        imageShown = images[imageIndex]    // images[Int]

        // make this true to get new image
        newRound = false
    }

    Column(
        modifier = backgroundColour
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
            // ref: https://developer.android.com/jetpack/compose/touch-input/pointer-input/scroll
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // title
        Text(
            text = "Guess the Country",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 20.dp)
        )

        Image(
            painterResource(id = imageShown),
            contentDescription = "Flag image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
        )

        LazyColumn(
            modifier = Modifier
                .size(height = 350.dp, width = 300.dp)
                .border(width = 0.5.dp, color = Color.LightGray)
                .background(color = Color.White)
        ) {
            for (i in countries) {
                item {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = { selected = i })
                    ) {
                        Text(text = i)   // country names
                    }
                }
            }
        }

        Text(text = "You selected: $selected",
            fontSize = 15.sp)

        // correct/incorrect/answer colours
        val green = colorResource(id = R.color.green)
        val red = colorResource(id = R.color.red)
        val blue = colorResource(id = R.color.blue)

        var result by rememberSaveable{ mutableStateOf("") }    // correct/incorrect
        var resultColour by remember{ mutableStateOf(blue) }

        ElevatedButton(
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.orange)),
            onClick = {
            if (selected == countries[imageIndex]) {
                result = "CORRECT"
                resultColour = green
            } else {
                result = "INCORRECT"
                correctAnswer = countries[imageIndex]    // to change the correct name of the country shown
                resultColour = red
            }

            if (buttonLabel == "Submit") {
                buttonLabel = "Next"
            } else if (buttonLabel == "Next") {
                buttonLabel = "Submit"
                // reset all info for next round
                result = ""
                correctAnswer = ""
                selected = ""
                newRound = true    // get new image
            }
        }) {
            Text(buttonLabel)    // "Submit" by default
        }

        // correct/incorrect
        Text(
            text = result,
            fontSize = 15.sp,
            color = resultColour
        )

        // answer if guessed wrong
        Text(
            text = correctAnswer,
            fontSize = 15.sp,
            color = blue
        )
    }
}