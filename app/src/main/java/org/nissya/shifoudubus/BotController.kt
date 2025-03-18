package org.nissya.shifoudubus

import androidx.navigation.NavHostController

class BotController {
    private var randomNumber: Int
    private var viewModel: ShakeViewModel
    private var navController: NavHostController

    fun play() {
        val randomNumberBot = (1..this.randomNumber).random()
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

    constructor(randomNumberMax: Int, viewModel: ShakeViewModel, navController: NavHostController) {
        this.randomNumber = randomNumberMax
        this.viewModel = viewModel
        this.navController = navController
    }
}