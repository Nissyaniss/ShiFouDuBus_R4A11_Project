package org.nissya.shifoudubus

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.nissya.shifoudubus.ui.theme.ShiFouDuBusTheme


class ShakeViewModel : ViewModel() {
    var shakeCount by mutableStateOf(0)
    var x by mutableStateOf(0f)
    var y by mutableStateOf(0f)
    var z by mutableStateOf(0f)
    var image by mutableStateOf(R.drawable.squidgame)
}

class MainActivity : ComponentActivity() {
    private lateinit var sensorManager: SensorManager
    private var sensorEventListener: SensorEventListener? = null
    private var lastShakeTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mediaPlayer = MediaPlayer.create(this,R.raw.welcome)
        mediaPlayer?.start()
        val mediaPlayerPierre = MediaPlayer.create(this,R.raw.pierre)
        val mediaPlayerFeuille = MediaPlayer.create(this,R.raw.feuille)
        val mediaPlayerCiseaux = MediaPlayer.create(this,R.raw.ciseaux)
        val mediaPlayerMusique = MediaPlayer.create(this,R.raw.musique)
        mediaPlayerMusique.isLooping=true
        mediaPlayerMusique.setVolume(0.1f, 0.1f)

        enableEdgeToEdge()

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        setContent {
            ShiFouDuBusTheme {
                val viewModel: ShakeViewModel = viewModel()

                // Gestion du capteur avec LaunchedEffect
                LaunchedEffect(Unit) {
                    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
                    mediaPlayerMusique?.start()
                    if (accelerometer != null) {
                        sensorEventListener = object : SensorEventListener {
                            override fun onSensorChanged(event: SensorEvent) {
                                viewModel.x = event.values[0]
                                viewModel.y = event.values[1]
                                viewModel.z = event.values[2]
                                val acceleration = Math.sqrt((viewModel.x * viewModel.x + viewModel.y * viewModel.y + viewModel.z * viewModel.z).toDouble())

                                val currentTime = System.currentTimeMillis()
                                if (acceleration > 50 && currentTime - lastShakeTime > 300) {
                                    lastShakeTime = currentTime
                                    viewModel.shakeCount += 1
                                    if (viewModel.shakeCount==1) {

                                        mediaPlayerPierre?.start()
                                    }else if (viewModel.shakeCount==2){
                                        mediaPlayerFeuille?.start()

                                    }

                                    else if (viewModel.shakeCount==3){
                                        val randomNumber =(1..3).random()
                                        mediaPlayerCiseaux?.start()
                                        if (randomNumber==1){
                                            viewModel.image = R.drawable.cacaillou
                                        }
                                        else if (randomNumber==2){
                                            viewModel.image = R.drawable.arbre
                                        }
                                        else if (randomNumber==3){
                                           viewModel.image = R.drawable.ciseaux
                                        }
                                        viewModel.shakeCount=0

                                    }
                                }
                            }

                            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
                        }

                        sensorManager.registerListener(
                            sensorEventListener,
                            accelerometer,
                            SensorManager.SENSOR_DELAY_UI
                        )
                    }
                }

                DisposableEffect(Unit) {
                    onDispose {
                        sensorManager.unregisterListener(sensorEventListener)
                    }
                }

                AppNavigation(viewModel)
            }
        }
    }
}

@Composable
fun Home(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.gameTitle),
            modifier = Modifier.padding(bottom = 100.dp)
        )
        Button(
            onClick = {
                navController.navigate("game")
            },
            modifier = Modifier.padding(top = 50.dp)
        ) { Text(text = stringResource(R.string.play)) }

    }
}

@SuppressLint("DefaultLocale")
@Composable
fun Game(navController: NavController, viewModel: ShakeViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.welcome),
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.shake_instruction),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.shake) + viewModel.shakeCount,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.x) + String.format("%.1f", viewModel.x) + " "+
                    stringResource(R.string.y) + String.format("%.1f", viewModel.y) + " "+
                    stringResource(R.string.z) + String.format("%.1f", viewModel.z),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id=viewModel.image),
            contentDescription=null
        )
    }
}

@Composable
fun AppNavigation(viewModel: ShakeViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {

        composable("home") { Home(navController) }
        composable("game") { Game(navController = navController, viewModel = viewModel) }
    }
}
