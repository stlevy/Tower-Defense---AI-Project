this Tower Defense game is a normal TD game,
where each tower is leveled up for each tower next to him (at all 8 directions)
for example , if a tower has 5 neighbor towers,he is at level 5.
at each level up, the tower gets stronger.   


To run the game , first download:
	- Levels diretory
	- resources directory ( required for gui and human mode)
	- game.jar 

open a cmd and run:
java -jar game.jar <Mode> [<t> <w> <alpha>]

example :
java -jar game.jar gui 1000 100 0.25

will the ai player in gui mode, with 1 second to run the algorithm, beam width 100 and alpha=0.25

Mode :
	- "human" - you want to play the game for yourself
			in this mode, the other parameters are not required
	- "gui" - you want the ai player to play, but to see the game in the gui mode
			in this mode, the results are written to Results/results.csv , make sure it's closed
	- "ai" - you want the ai player to play, and do not need to see the gui itself
			in this mode, the results are written to Results/results.csv , make sure it's closed
			
t :
	the AI algorithm running time, in milliseconds.
	the algorithm will stop after t milliseconds and return the best state it found (anytime algorithm)
w : 
	the width of the beam in the "beam search" algorithm

the algorithms has 2 heuristics 
		- "Path heuristics" - maximizes the amount of path seen by the towers
		- "Tower heuristics" - maximizes the "effective levels" of the towers,
			where "effective level" is the level of the tower that SEES some path.
alpha : if 0<=alpha<=1:
		the heuristics the game runs is (alpha*PathHeuristics) + ((1-alpha) * TowerHeuristics)
	
		if alpha == 2: 
		the heuristics the game runs is PathHeuristics* TowerHeuristics