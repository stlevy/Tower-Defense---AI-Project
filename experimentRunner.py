import os
algo_running_time = 1000 
beam_width = 100
gui = False
	
def alpha_experiment():
	for raw_alpha in range(0,11):
		alpha = float(raw_alpha) / 10.0
		print '\n\n\n\n\nrunning for alpha = %.2f\n\n\n\n\n' %(alpha,)
		if gui:
			os.system('java -jar game.jar gui %d %d %.2f' %(algo_running_time, beam_width, alpha) )
		else:
			os.system('java -jar game.jar ai %d %d %.2f' %(algo_running_time, beam_width, alpha) )

if __name__ == '__main__':
	alpha_experiment()