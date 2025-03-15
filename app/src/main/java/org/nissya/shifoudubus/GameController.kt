package org.nissya.shifoudubus

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController
import kotlin.math.sqrt

class ShakeViewModel : ViewModel() {
    var shakeCount by mutableIntStateOf(0)
    var x by mutableFloatStateOf(0f)
    var y by mutableFloatStateOf(0f)
    var z by mutableFloatStateOf(0f)
    var image by mutableIntStateOf(R.drawable.squidgame)
    var isGameBot by mutableStateOf(false)
    var imageBot by mutableIntStateOf(R.drawable.squidgame)
    var isWin by mutableStateOf(false)
    var isLose by mutableStateOf(false)
}

@Composable
fun GameController(
    viewModel: ShakeViewModel,
    sensorManager: SensorManager,
    mediaPlayerPierre: MediaPlayer,
    mediaPlayerFeuille: MediaPlayer,
    mediaPlayerCiseaux: MediaPlayer,
    mediaPlayerMusique: MediaPlayer
) {
    val navController = rememberNavController()// Ce sera bien un NavHostController

    mediaPlayerMusique.start()
    DisposableEffect(sensorManager) {
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        val sensorEventListener = object : SensorEventListener {
            private var lastShakeTime: Long = 0

            override fun onSensorChanged(event: SensorEvent) {
                viewModel.x = event.values[0]
                viewModel.y = event.values[1]
                viewModel.z = event.values[2]
                val acceleration =
                    sqrt((viewModel.x * viewModel.x + viewModel.y * viewModel.y + viewModel.z * viewModel.z).toDouble())

                val currentTime = System.currentTimeMillis()
                if (acceleration > 50 && currentTime - lastShakeTime > 300) {
                    lastShakeTime = currentTime
                    viewModel.shakeCount += 1

                    when (viewModel.shakeCount) {
                        1 -> mediaPlayerPierre.start()
                        2 -> mediaPlayerFeuille.start()
                        3 -> {
                            val randomNumber = (1..3).random()

                            mediaPlayerCiseaux.start()
                            when (randomNumber) {
                                1 -> viewModel.image = R.drawable.cacaillou
                                2 -> viewModel.image = R.drawable.arbre
                                3 -> viewModel.image = R.drawable.ciseaux
                            }

                            if (viewModel.isGameBot) {
                                val randomNumberBot = (1..3).random()
                                when (randomNumberBot) {
                                    1 -> viewModel.imageBot = R.drawable.cacaillou
                                    2 -> viewModel.imageBot = R.drawable.arbre
                                    3 -> viewModel.imageBot = R.drawable.ciseaux
                                }


                                if (randomNumber == randomNumberBot) {
                                    viewModel.isLose = false
                                    viewModel.isWin = false
                                } else if (randomNumber == 2 && randomNumberBot == 1) {
                                    viewModel.isLose = false
                                    viewModel.isWin = true
                                } else if (randomNumber == 3 && randomNumberBot == 1) {
                                    viewModel.isLose = true
                                    viewModel.isWin = false
                                } else if (randomNumber == 1 && randomNumberBot == 2) {
                                    viewModel.isLose = true
                                    viewModel.isWin = false
                                } else if (randomNumber == 3 && randomNumberBot == 2) {
                                    viewModel.isLose = false
                                    viewModel.isWin = true
                                } else if (randomNumber == 1 && randomNumberBot == 3) {
                                    viewModel.isLose = false
                                    viewModel.isWin = true
                                } else if (randomNumber == 2 && randomNumberBot == 3) {
                                    viewModel.isLose = true
                                    viewModel.isWin = false
                                }
                                navController.navigate("result")
                            }
                            viewModel.shakeCount = 0
                        }
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

        onDispose {
            sensorManager.unregisterListener(sensorEventListener)
        }
    }

    AppNavigation(viewModel, navController)

}

