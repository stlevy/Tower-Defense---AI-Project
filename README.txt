this Tower Defense game is a normal TD game,
where each tower is granted a damage bonus for each tower next to him (at all 8 directions)
for example , if a tower has 5 neighbor towers, he gets 5 bonuses.   


To run the game , first download:
	- Results directory
	- Levels diretory
	- resources directory ( if you want gui)
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
	the AI algorithm running time, in milliseconds 
w : 
	the width of the beam in the "beam search" algorithm
alpha : where 0<=alpha<=1
	the algorithms has 2 heuristics 
		- "Path heuristics" - maximizes the amount of path seen by the towers
		- "Tower heuristics" - maximizes the amount of "effective bonuses" of the tower
			where an "effective bonus" is a bonus for a tower that sees some path.
		the heuristics the game runs is (alpha*PathHeuristics) + ((1-alpha) * TowerHeuristics)   