package com.alishacarrington.coursework

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alishacarrington.coursework.R.drawable.ad
import com.alishacarrington.coursework.R.drawable.ar
import com.alishacarrington.coursework.R.drawable.at
import com.alishacarrington.coursework.R.drawable.au
import com.alishacarrington.coursework.R.drawable.be
import com.alishacarrington.coursework.R.drawable.bh
import com.alishacarrington.coursework.R.drawable.bq
import com.alishacarrington.coursework.R.drawable.br
import com.alishacarrington.coursework.R.drawable.ca
import com.alishacarrington.coursework.R.drawable.ch
import com.alishacarrington.coursework.R.drawable.cn
import com.alishacarrington.coursework.R.drawable.cy
import com.alishacarrington.coursework.R.drawable.cz
import com.alishacarrington.coursework.R.drawable.de
import com.alishacarrington.coursework.R.drawable.es
import com.alishacarrington.coursework.R.drawable.fr
import com.alishacarrington.coursework.R.drawable.gb
import com.alishacarrington.coursework.R.drawable.gr
import com.alishacarrington.coursework.R.drawable.hk
import com.alishacarrington.coursework.R.drawable.`in`
import com.alishacarrington.coursework.R.drawable.it
import com.alishacarrington.coursework.R.drawable.jm
import com.alishacarrington.coursework.R.drawable.jp
import com.alishacarrington.coursework.R.drawable.ke
import com.alishacarrington.coursework.R.drawable.mo
import com.alishacarrington.coursework.R.drawable.mx
import com.alishacarrington.coursework.R.drawable.pl
import com.alishacarrington.coursework.R.drawable.se
import com.alishacarrington.coursework.R.drawable.ua
import com.alishacarrington.coursework.R.drawable.us
import kotlin.random.Random

// https://stackoverflow.com/questions/68910034/how-to-bind-drawable-files-to-variables-kotlin
val images = listOf(ad, ar, at, au, be, bh, br, ca, cn, cy, cz, fr, de, gr, hk, `in`, it, jm,
    jp, ke, mo, mx, bq, pl, es, se, ch, ua, gb, us)

val countries = listOf("Andorra", "Argentina", "Austria", "Australia", "Belgium",
    "Bahrain", "Brazil", "Canada", "China", "Cyprus", "Czech Republic", "France",
    "Germany", "Greece", "Hong Kong", "India", "Italy", "Jamaica", "Japan", "Kenya",
    "Macau", "Mexico", "Netherlands", "Poland", "Spain", "Sweden", "Switzerland",
    "Ukraine", "United Kingdom", "United States")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val backgroundColour = Modifier.background(colorResource(id = R.color.cream))

    Column(
        modifier = backgroundColour
            .fillMaxSize()
            .padding(top = 130.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        ElevatedButton(
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.orange)),
            onClick = {
            val i = Intent(context, GuessTheCountry::class.java)
            context.startActivity(i)
        }) {
            Text(text = "Guess the Country",
                fontSize = 20.sp)
        }

        ElevatedButton(
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.orange)),
            onClick = {
            val i = Intent(context, GuessHints::class.java)
            context.startActivity(i)
        }) {
            Text(text = "Guess-Hints",
                fontSize = 20.sp)
        }

        ElevatedButton(
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.orange)),
            onClick = {
            val i = Intent(context, GuessTheFlag::class.java)
            context.startActivity(i)
        }) {
            Text(text = "Guess the Flag",
                fontSize = 20.sp)
        }

        ElevatedButton(
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.orange)),
            onClick = {
            val i = Intent(context, AdvancedLevel::class.java)
            context.startActivity(i)
        }) {
            Text(text = "Advanced Level",
                fontSize = 20.sp)
        }
    }
}

// for GuessHints game
fun ChooseNewImage(): Int {
    // list of numbers already used so only new images appear
    var usedIndex = mutableListOf<Int>()

    var index = Random.nextInt(images.size)    // get random num 0 - 29

    // if number is in list already then keep getting a new random number
    while (index in usedIndex)
        index = Random.nextInt(images.size)

    // add new number to the list
    usedIndex.add(index)

    return index
}

// for GuessTheFlag and AdvancedLevel games
fun ThreeNewImages(): List<Int> {
    var drawablesList = mutableListOf<Int>()

    var index1 = Random.nextInt(images.size)
    var country1 = images[index1]

    var index2 = Random.nextInt(images.size)
    var country2 = images[index2]
    while (country1 == country2) {
        index2 = Random.nextInt(images.size)
        country2 = images[index2]
    }

    var index3 = Random.nextInt(images.size)
    var country3 = images[index3]
    while (country1 == country3 || country2 == country3) {
        index3 = Random.nextInt(images.size)
        country3 = images[index3]
    }

    // add the 3 drawables to the list
    drawablesList.add(images[index1])
    drawablesList.add(images[index2])
    drawablesList.add(images[index3])

    return drawablesList
}