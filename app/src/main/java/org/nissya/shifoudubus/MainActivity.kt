package org.nissya.shifoudubus

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.nissya.shifoudubus.ui.theme.ShiFouDuBusTheme
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    private lateinit var sensorManager: SensorManager
    private var sensorEventListener: SensorEventListener? = null
    private var lastShakeTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        setContent {
            ShiFouDuBusTheme {
                var shakeCount by remember { mutableStateOf(0) }


                sensorEventListener = object : SensorEventListener {
                    override fun onSensorChanged(event: SensorEvent) {
                        val x = event.values[0]
                        val y = event.values[1]
                        val z = event.values[2]
                        val acceleration = Math.sqrt((x * x + y * y + z * z).toDouble())

                        val currentTime = System.currentTimeMillis()
                        if (acceleration > 50 && currentTime - lastShakeTime > 300) {
                            lastShakeTime = currentTime
                            shakeCount += 1


                        }
                    }

                    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
                }

                val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
                if (accelerometer != null) {
                    sensorManager.registerListener(
                        sensorEventListener,
                        accelerometer,
                        SensorManager.SENSOR_DELAY_UI
                    )
                }

                AppNavigation(shakeCount)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(sensorEventListener)
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

@Composable
fun Game(navController: NavController, shakeDetected: Int) {
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
            text = stringResource(R.string.shake)  + shakeDetected,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

    }
}

@Composable
fun AppNavigation(shakeDetected: Int) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { Home(navController) }
        composable("game") { Game(navController = navController, shakeDetected = shakeDetected) }
    }
}
