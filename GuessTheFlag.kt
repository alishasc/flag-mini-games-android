package com.alishacarrington.coursework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class GuessTheFlag : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuessTheFlagGame()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GuessTheFlagGame(modifier: Modifier = Modifier) {
    var imageOne by rememberSaveable{ mutableIntStateOf(0) }
    var imageTwo by rememberSaveable{ mutableIntStateOf(0) }
    var imageThree by rememberSaveable{ mutableIntStateOf(0) }
    var countryChoice by rememberSaveable{ mutableIntStateOf(0) }    // country of the flag chosen

    var newRound by rememberSaveable{ mutableStateOf(true) }    // start next round
    var clickEnabled by rememberSaveable{ mutableStateOf(true) }    // controls if user can select a flag

    var result by rememberSaveable{ mutableStateOf("") }    // correct/incorrect
    var resultColour by remember{ mutableStateOf(Color.Black) }

    val backgroundColour = Modifier.background(colorResource(id = R.color.cream))

    if (newRound) {
        // to display new images AFTER pressing button
        val newImages = ThreeNewImages()
        imageOne = newImages[0]
        imageTwo = newImages[1]
        imageThree = newImages[2]

        // choose one of the 3 images as the country name to identify
        countryChoice = Random.nextInt(3)    // number 0 - 2

        newRound = false
    }

    // to match the country name to correct flag
    var country = ""
    var index = 0
    if (countryChoice == 0)
        index = images.indexOf(imageOne)
    else if (countryChoice == 1)
        index = images.indexOf(imageTwo)
    else
        index = images.indexOf(imageThree)

    country = countries[index]

    // layout
    Column(
        modifier = backgroundColour
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Guess the Flag",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 20.dp)
        )

        // name of country
        Text(text = country,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp)

        // images
        Column {
            // https://developer.android.com/jetpack/compose/graphics/images/customize
            val imageModifier = Modifier
                .size(250.dp, 175.dp)    // w x h
                .padding(10.dp)

            Image(
                painterResource(id = imageOne),
                contentDescription = "First flag",
                contentScale = ContentScale.FillWidth,
                modifier = imageModifier
                    .clickable(
                        enabled = clickEnabled,    // true so image can be clicked
                        onClick = {
                            clickEnabled = false    // can't click image again, also added to other Images so can't change answer
                            if (countryChoice == 0)
                                result = "CORRECT"
                            else
                                result = "INCORRECT"
                        }
                    )
            )

            Image(
                painterResource(id = imageTwo),
                contentDescription = "Second flag",
                contentScale = ContentScale.FillWidth,
                modifier = imageModifier
                    .clickable(
                        enabled = clickEnabled,
                        onClick = {
                            clickEnabled = false
                            if (countryChoice == 1)
                                result = "CORRECT"
                            else
                                result = "INCORRECT"
                        }
                    )
            )

            Image(
                painterResource(id = imageThree),
                contentDescription = "Third flag",
                contentScale = ContentScale.FillWidth,
                modifier = imageModifier
                    .clickable(
                        enabled = clickEnabled,
                        onClick = {
                            clickEnabled = false
                            if (countryChoice == 2)
                                result = "CORRECT"
                            else
                                result = "INCORRECT"
                        }
                    )
            )

            if (result == "CORRECT")
                resultColour = colorResource(id = R.color.green)
            else
                resultColour = colorResource(id = R.color.red)
        }

        ElevatedButton(
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.orange)),
            onClick = {
                result = ""    // clear result
                newRound = true    // displays new images
                clickEnabled = true    // makes it so you can click the images again
        }) {
            Text(text = "Next")
        }

        // "correct"/"incorrect" - answer variable!
        Text(text = result,
            fontSize = 20.sp,
            color = resultColour)
    }
}
