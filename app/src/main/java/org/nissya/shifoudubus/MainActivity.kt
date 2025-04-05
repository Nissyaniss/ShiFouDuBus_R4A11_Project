package org.nissya.shifoudubus

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
    private lateinit var music: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val welcomeSound = MediaPlayer.create(this, R.raw.welcome)
        welcomeSound?.start()
        val mediaPlayerPierre = MediaPlayer.create(this, R.raw.pierre)
        val mediaPlayerFeuille = MediaPlayer.create(this, R.raw.feuille)
        val mediaPlayerCiseaux = MediaPlayer.create(this, R.raw.ciseaux)
        this.music = MediaPlayer.create(this, R.raw.musique)
        music.isLooping = true
        music.setVolume(0.1f, 0.1f)
        music.start()

        enableEdgeToEdge()

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        setContent {
            ShiFouDuBusTheme {
                val viewModel: ShakeViewModel = viewModel()
                GameController(
                    viewModel,
                    sensorManager,
                    mediaPlayerPierre,
                    mediaPlayerFeuille,
                    mediaPlayerCiseaux,
                )
            }
        }
    }

    override fun onStop() {
        super.onStop()
        music.pause()
    }

    override fun onRestart() {
        super.onRestart()
        music.start()
    }

}
