package org.nissya.shifoudubus

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.ui.graphics.painter.Painter
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
import androidx.compose.foundation.layout.Box
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

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
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        Text(
            text = stringResource(R.string.best_score) + viewModel.currentBestScore,
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
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                    modifier = Modifier.padding(bottom = 50.dp)
                )
                Text(text = stringResource(R.string.current_score) + viewModel.currentScore)
                Text(text = stringResource(R.string.best_score) + viewModel.currentBestScore)
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
                    onClick = {
                        navController.navigate("home")
                        viewModel.isContinuing = false
                        viewModel.lastPlay = R.drawable.squidgame
                        viewModel.currentScore = 0
                    },
                    modifier = Modifier.padding(top = 50.dp)
                ) {
                    Text(text = stringResource(R.string.home))
                }
                if (viewModel.isWin || (!viewModel.isWin && !viewModel.isLose)) {
                    Button(
                        onClick = {
                            navController.navigate("selecter")
                            viewModel.isContinuing = true
                            viewModel.lastPlay = viewModel.image
                        },
                        modifier = Modifier.padding(top = 50.dp)
                    ) {
                        Text(text = stringResource(R.string.Continue))
                    }
                } else if (viewModel.isLose) {
                    Button(
                        onClick = {
                            navController.navigate("selecter")
                            viewModel.currentScore = 0
                            viewModel.isContinuing = false
                            viewModel.lastPlay = R.drawable.squidgame
                        },
                        modifier = Modifier.padding(top = 50.dp)
                    ) {
                        Text(text = stringResource(R.string.replay))
                    }
                }

            }
        }

        if (viewModel.isWin) {
            KonfettiView(
                parties = listOf(
                    Party(
                        speed = 0f,
                        maxSpeed = 30f,
                        damping = 0.9f,
                        spread = 360,
                        colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                        emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
                        position = Position.Relative(0.5, 0.5)
                    )
                ),
                modifier = Modifier.fillMaxSize()
            )
        }
        if (viewModel.bestScore) {
            KonfettiView(
                parties = listOf(
                    Party(
                        speed = 0f,
                        maxSpeed = 30f,
                        damping = 0.9f,
                        spread = 720,
                        colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                        emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(300),
                        position = Position.Relative(0.5, 0.5)
                    )
                ),
                modifier = Modifier.fillMaxSize()
            )
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
    Selecter(stringResource(R.string.choose_move)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {



            ChooseButton(
                {
                    viewModel.image = R.drawable.cacaillou
                    navController.navigate("gameAgainstBot")
                },
                painter = painterResource(id = R.drawable.cacaillou),
                stringResource(R.string.stone)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ChooseButton(
                {
                    viewModel.image = R.drawable.arbre
                    navController.navigate("gameAgainstBot")
                },
                painter = painterResource(id = R.drawable.arbre),
                stringResource(R.string.leaf)
            )

        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ChooseButton(
                {
                    viewModel.image = R.drawable.ciseaux
                    navController.navigate("gameAgainstBot")
                },
                painter = painterResource(id = R.drawable.ciseaux),

                stringResource(R.string.scissors)


            )
        }

    }

}

@Composable
fun Selecter(
    title: String,
    content: @Composable RowScope.() -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(top = 100.dp),
            style = LocalTextStyle.current.copy(lineHeight = 50.sp),
            textAlign = TextAlign.Center,
            text = title,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun DifficultySelecter(navController: NavHostController, viewModel: ShakeViewModel) {
    Selecter(stringResource(R.string.choose_difficulty)) {
        ChooseButton(
            {
                viewModel.difficulty = Difficulty.RANDOM
                navController.navigate("selecter")
            },
            painterResource(id = R.drawable.gamble),
            stringResource(R.string.random)
        )
        Spacer(modifier = Modifier.width(16.dp))
        ChooseButton(
            {
                viewModel.difficulty = Difficulty.NORMAL
                navController.navigate("selecter")
            },
            painterResource(R.drawable.intelligent),
            stringResource(R.string.normal)
        )
    }
}

@Composable
fun ChooseButton(
    onClick: () -> Unit,
    painter: Painter,
    text: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(100.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = "Icon Button",
            )
        }
        Text(text)
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




