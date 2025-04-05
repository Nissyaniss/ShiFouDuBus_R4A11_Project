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
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Home(navController: NavController, viewModel: ShakeViewModel) {
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
            onClick = { navController.navigate("difficultySelecter") },
            modifier = Modifier.padding(top = 50.dp)
        ) {
            Text(text = stringResource(R.string.play_against_bot))
        }
    }
}

@Composable
fun Result(navController: NavController, viewModel: ShakeViewModel) {
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
            if (viewModel.isWin)
                viewModel.currentScore += 1
            viewModel.isContinuing = false
            Text(text = stringResource(R.string.home))
        }
        Button(
            onClick = { navController.navigate("selecter") },
            modifier = Modifier.padding(top = 50.dp)
        ) {
            if (viewModel.isWin)
                viewModel.currentScore += 1
            viewModel.isContinuing = true
            viewModel.lastPlay = viewModel.image
            Text(text = stringResource(R.string.continuer))
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun GameAgainstBot(viewModel: ShakeViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        viewModel.imageBot = R.drawable.squidgame
        Text(
            modifier = Modifier.padding(top = 100.dp),
            style = LocalTextStyle.current.copy(lineHeight = 50.sp),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.choose_move),
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {
                        viewModel.image =
                            R.drawable.cacaillou; navController.navigate("gameAgainstBot")
                    },
                    modifier = Modifier.size(100.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cacaillou),
                        contentDescription = "Icon Button",
                    )
                }
                Text(text = stringResource(R.string.pierre))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
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
                Text(text = stringResource(R.string.feuille))

            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {
                        viewModel.image =
                            R.drawable.ciseaux; navController.navigate("gameAgainstBot")
                    },
                    modifier = Modifier.size(100.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ciseaux),
                        contentDescription = "Icon Button",
                    )
                }
                Text(text = stringResource(R.string.ciseaux))
            }

        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun DifficultySelecter(navController: NavHostController, viewModel: ShakeViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 100.dp),
            style = LocalTextStyle.current.copy(lineHeight = 50.sp),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.choose_difficulty),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {
                        viewModel.difficulty = Difficulty.RANDOM
                        navController.navigate("selecter")
                    },
                    modifier = Modifier.size(100.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.gamble),
                        contentDescription = "Icon Button",
                    )
                }
                Text(text = stringResource(R.string.random))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {
                        viewModel.difficulty = Difficulty.NORMAL
                        navController.navigate("selecter")
                    },
                    modifier = Modifier.size(100.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.intelligent),
                        contentDescription = "Icon Button",
                    )
                }
                Text(text = stringResource(R.string.normal))
            }
        }
    }
}

@Composable
fun AppNavigation(viewModel: ShakeViewModel, navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Home(navController, viewModel)
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
        composable("difficultySelecter") {
            DifficultySelecter(navController, viewModel)
        }
    }
}


