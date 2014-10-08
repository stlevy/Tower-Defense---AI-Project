import os
algo_running_time = 1000 
beam_width = 100
const_alpha = 0.5
gui = False
	
def alpha_experiment():
	print '***************ALPHA EXPERIMENTS***************'
	for raw_alpha in range(0,101):
		alpha = float(raw_alpha) / 100.0
		print '\n\nrunning for alpha = %.2f\n\n' %(alpha,)
		if gui:
			os.system('java -jar game.jar gui %d %d %.2f' %(algo_running_time, beam_width, alpha) )
		else:
			os.system('java -jar game.jar ai %d %d %.2f' %(algo_running_time, beam_width, alpha) )

			
def width_experiment():
	print '***************WIDTH EXPERIMENTS***************'
	width_list = [1,2,5,10,20,30,40,50,100,200,300,500,1000]
	for width in width_list:
		print '\n\nrunning for width = %.2f\n\n' %(width,)
		if gui:
			os.system('java -jar game.jar gui %d %d %.2f' %(algo_running_time, width, const_alpha) )
		else:
			os.system('java -jar game.jar ai %d %d %.2f' %(algo_running_time, width, const_alpha) )

def time_experiment():
	print '***************TIME EXPERIMENTS***************'
	time_list = [10,20,30,40,50,100,200,300,500,1000,2000,3000,4000,5000,7500,10000]
	for t in time_list:
		print '\n\nrunning for time = %.2f\n\n' %(t,)
		if gui:
			os.system('java -jar game.jar gui %d %d %.2f' %(t, beam_width, const_alpha) )
		else:
			os.system('java -jar game.jar ai %d %d %.2f' %(t, beam_width, const_alpha) )

def seperator(str):
	f = open('Results/results.csv','at')
	f.write('%s\n' %(str,))
	f.close()
	
if __name__ == '__main__':
	seperator('alpha')
	alpha_experiment()
	seperator('width')
	width_experiment()
	seperator('time')
	time_experiment()