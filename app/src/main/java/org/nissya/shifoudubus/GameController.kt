package org.nissya.shifoudubus

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController

class ShakeViewModel : ViewModel() {
    var shakeCount by mutableStateOf(0)
    var x by mutableStateOf(0f)
    var y by mutableStateOf(0f)
    var z by mutableStateOf(0f)
    var image by mutableStateOf(R.drawable.squidgame)
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
                val acceleration = Math.sqrt((viewModel.x * viewModel.x + viewModel.y * viewModel.y + viewModel.z * viewModel.z).toDouble())

                val currentTime = System.currentTimeMillis()
                if (acceleration > 50 && currentTime - lastShakeTime > 300) {
                    lastShakeTime = currentTime
                    viewModel.shakeCount += 1

                    if (viewModel.shakeCount==1) {

                        mediaPlayerPierre.start()
                    }else if (viewModel.shakeCount==2){
                        mediaPlayerFeuille.start()

                    }

                    else if (viewModel.shakeCount==3){
                        val randomNumber =(1..3).random()
                        mediaPlayerCiseaux.start()
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

        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_UI)

        onDispose {
            sensorManager.unregisterListener(sensorEventListener)
        }
    }

    AppNavigation(viewModel, navController)
}

