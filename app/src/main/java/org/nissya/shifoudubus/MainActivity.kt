package org.nissya.shifoudubus

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import org.nissya.shifoudubus.ui.theme.ShiFouDuBusTheme

class MainActivity : ComponentActivity() {
    private lateinit var sensorManager: SensorManager
    private var sensorEventListener: SensorEventListener? = null
    private var lastShakeTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mediaPlayer = MediaPlayer.create(this, R.raw.welcome)
        mediaPlayer?.start()
        val mediaPlayerPierre = MediaPlayer.create(this, R.raw.pierre)
        val mediaPlayerFeuille = MediaPlayer.create(this, R.raw.feuille)
        val mediaPlayerCiseaux = MediaPlayer.create(this, R.raw.ciseaux)
        val mediaPlayerMusique = MediaPlayer.create(this, R.raw.musique)
        mediaPlayerMusique.isLooping = true
        mediaPlayerMusique.setVolume(0.1f, 0.1f)

        enableEdgeToEdge()

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        setContent {
            ShiFouDuBusTheme {
                val viewModel: ShakeViewModel = viewModel()
                GameController(viewModel, sensorManager, mediaPlayerPierre, mediaPlayerFeuille, mediaPlayerCiseaux, mediaPlayerMusique)
            }
        }
    }
}
