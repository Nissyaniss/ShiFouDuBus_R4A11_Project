package org.nissya.shifoudubus

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Home(navController: NavController, viewModel: ShakeViewModel) {
    viewModel.isGameBot = false
    viewModel.imageBot = R.drawable.squidgame
    viewModel.image = R.drawable.squidgame
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
            onClick = { navController.navigate("selecter") },
            modifier = Modifier.padding(top = 50.dp)
        ) {
            Text(text = stringResource(R.string.play))
        }
        Button(
            onClick = { navController.navigate("selecter") },
            modifier = Modifier.padding(top = 50.dp)
        ) {
            Text(text = stringResource(R.string.play_against_bot))
        }
    }
}

@Composable
fun Result(navController: NavController, viewModel: ShakeViewModel) {
    viewModel.isGameBot = false
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            text = if (viewModel.isWin) {
                stringResource(R.string.you_win)
            } else if (viewModel.isLose) {
                stringResource(R.string.you_lost)
            } else {
                stringResource(R.string.draw)
            },
            modifier = Modifier.padding(bottom = 100.dp)
        )
        Text(
            text = stringResource(R.string.you),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = viewModel.image),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.bot),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = viewModel.imageBot),
            contentDescription = null
        )
        Button(
            onClick = { navController.navigate("home") },
            modifier = Modifier.padding(top = 50.dp)
        ) {
            Text(text = stringResource(R.string.home))
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun GameAgainstBot(viewModel: ShakeViewModel) {
    viewModel.isGameBot = true
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
        Text(
            text = stringResource(R.string.shake_instruction),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.shake) + viewModel.shakeCount,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.x) + String.format("%.1f", viewModel.x) + " " +
                    stringResource(R.string.y) + String.format("%.1f", viewModel.y) + " " +
                    stringResource(R.string.z) + String.format("%.1f", viewModel.z),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = viewModel.image),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Bot",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = viewModel.imageBot),
            contentDescription = null
        )
    }
}

@Composable
fun MoveSelecter(navController: NavHostController, viewModel: ShakeViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    viewModel.image = R.drawable.arbre; navController.navigate("gameAgainstBot")
                },
                modifier = Modifier.size(100.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arbre),
                    contentDescription = "Icon Button",
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(
                onClick = {
                    viewModel.image = R.drawable.cacaillou; navController.navigate("gameAgainstBot")
                },
                modifier = Modifier.size(100.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cacaillou),
                    contentDescription = "Icon Button",
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(
                onClick = {
                    viewModel.image = R.drawable.ciseaux; navController.navigate("gameAgainstBot")
                },
                modifier = Modifier.size(100.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ciseaux),
                    contentDescription = "Icon Button",
                )
            }
        }
    }
}


@SuppressLint("DefaultLocale")
@Composable
fun Game(viewModel: ShakeViewModel) {
    viewModel.isGameBot = false
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
            text = stringResource(R.string.x) + String.format("%.1f", viewModel.x) + " " +
                    stringResource(R.string.y) + String.format("%.1f", viewModel.y) + " " +
                    stringResource(R.string.z) + String.format("%.1f", viewModel.z),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = viewModel.image),
            contentDescription = null
        )
    }
}

@Composable
fun AppNavigation(viewModel: ShakeViewModel, navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Home(navController, viewModel)
        }
        composable("game") {
            Game(viewModel)
        }
        composable("gameAgainstBot") {
            GameAgainstBot(viewModel)
        }
        composable("result") {
            Result(navController, viewModel)
        }
        composable("selecter") {
            MoveSelecter(navController, viewModel)
        }
    }
}


