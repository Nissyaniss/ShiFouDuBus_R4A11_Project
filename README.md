Notre Shifumi propose 2 modes de difficulté pour jouer contre le robot: aléatoire et normal.
Le premier mode comme son nom l'indique joue totalement aléatoirement. Le second quant à lui utilise une stratégie qui provient de cette vidéo . En résumé, lorsque le joueur gagne ou perd, le robot remonte d'un dans le schéma pierre feuille ciseaux (exemple: si le robot perd en ayant jouer feuille, au prochain tour il jouera pierre). En cas d'égalité, le robot remontera d'un dans le schéma. 
D'après les études, cette stratégie permet une augmentation des chances de victoires par rapport à un jeu aléatoire, cependant si l'adversaire comprend la stratégie, alors il devient simple de battre le robot.

Le déclenchement du record s'effectue dans le fichier botController lorsque le joueur effectue une série de victoires ou d'égalité sans défaite supérieure à la précédente. Pour marquer ce record, nous avons décidé de déclencher des confettis dans le GameView (ceux-ci sont déjà présents lors d'une simple mais victoire mais en nombre inférieur) ainsi qu'un effet sonore.

L'application possède également un fond sonore.