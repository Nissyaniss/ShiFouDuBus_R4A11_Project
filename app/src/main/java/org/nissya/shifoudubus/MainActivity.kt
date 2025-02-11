package org.nissya.shifoudubus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.nissya.shifoudubus.ui.theme.ShiFouDuBusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShiFouDuBusTheme {
                title()
            }
        }
    }
}

@Composable
fun title() {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.gameTitle)
        )
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick = {
//            val intent = Intent(context, Game::class.java)
//            context.startActivity(intent)
        }
        ) { Text(text = stringResource(R.string.play)) }
    }

}