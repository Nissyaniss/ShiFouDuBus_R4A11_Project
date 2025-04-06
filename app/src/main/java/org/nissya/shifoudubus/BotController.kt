package org.nissya.shifoudubus

import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.navigation.NavHostController

class BotController(
    private var viewModel: ShakeViewModel,
    private var navController: NavHostController,
    private var mediaPlayerYippee: MediaPlayer,
    private var sharedPref: SharedPreferences
) {

    fun play(isRandom: Boolean) {
        if (isRandom) {
            val randomNumber = (1..3).random()
            when (randomNumber) {
                1 -> viewModel.imageBot = R.drawable.cacaillou
                2 -> viewModel.imageBot = R.drawable.arbre
                3 -> viewModel.imageBot = R.drawable.ciseaux
            }
        } else {
            if (!viewModel.isWin && !viewModel.isLose) {
                this.play(true)
                return
            } else {
                if (viewModel.isWin) {
                    when (viewModel.lastPlay) {
                        R.drawable.arbre -> viewModel.imageBot = R.drawable.ciseaux
                        R.drawable.cacaillou -> viewModel.imageBot = R.drawable.arbre
                        R.drawable.ciseaux -> viewModel.imageBot = R.drawable.cacaillou
                    }
                }
            }
        }
        if (viewModel.image == viewModel.imageBot) {
            viewModel.isLose = false
            viewModel.isWin = false
        } else if (viewModel.image == R.drawable.cacaillou && viewModel.imageBot == R.drawable.ciseaux) {
            viewModel.isLose = false
            viewModel.isWin = true
        } else if (viewModel.image == R.drawable.ciseaux && viewModel.imageBot == R.drawable.cacaillou) {
            viewModel.isLose = true
            viewModel.isWin = false
        } else if (viewModel.image == R.drawable.cacaillou && viewModel.imageBot == R.drawable.arbre) {
            viewModel.isLose = true
            viewModel.isWin = false
        } else if (viewModel.image == R.drawable.arbre && viewModel.imageBot == R.drawable.cacaillou) {
            viewModel.isLose = false
            viewModel.isWin = true
        } else if (viewModel.image == R.drawable.ciseaux && viewModel.imageBot == R.drawable.arbre) {
            viewModel.isLose = false
            viewModel.isWin = true
        } else if (viewModel.image == R.drawable.arbre && viewModel.imageBot == R.drawable.ciseaux) {
            viewModel.isLose = true
            viewModel.isWin = false
        }

        if (viewModel.isWin) {
            viewModel.currentScore += 1
            if (viewModel.currentScore > viewModel.currentBestScore) {
                viewModel.currentBestScore = viewModel.currentScore
                mediaPlayerYippee.start()
                viewModel.bestScore = true
                with(sharedPref.edit()) {
                    putInt("bestScore", viewModel.currentBestScore)
                    commit()
                }
            }
        }
        navController.navigate("result")
    }

}