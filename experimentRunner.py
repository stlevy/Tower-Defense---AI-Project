import os
redos_number = 4
width_list = [1,2,5,10,20,30,40,50,100,200,300,500,1000]
time_list = [10,20,30,40,50,100,200,300,500,1000,2000,3000,4000,5000,7500,10000,20000,50000,100000]
alpha__beta_gamma_list = []
for alpha in range(0,11):
	for beta in range(0,11):
		if alpha+beta <= 10:
			alpha__beta_gamma_list.append((alpha/10.0,beta/10.0,(10-alpha-beta)/10.0))
		
for alpha,beta,gamma in alpha__beta_gamma_list:
	print alpha,beta,gamma
print len(alpha__beta_gamma_list)

def alpha_experiment(t,width):
	print '***************ALPHA EXPERIMENTS***************'
	for i in range(redos_number):
		for raw_alpha in alpha_list:
			alpha = float(raw_alpha) / 100.0
			print '\n\nrunning for alpha = %.2f\n\n' %(alpha,)
			os.system('java -jar game.jar ai %d %d %.2f' %(t, width, alpha) )

			
def width_experiment(alpha,t):
	print '***************WIDTH EXPERIMENTS***************'
	for i in range(redos_number):
		
		for width in width_list:
			print '\n\nrunning for width = %.2f\n\n' %(width,)
			os.system('java -jar game.jar ai %d %d %.2f' %(t, width, alpha) )

def time_experiment(alpha,width):
	print '***************TIME EXPERIMENTS***************'
	for i in range(redos_number):
		for t in time_list:
			print '\n\nrunning for time = %.2f\n\n' %(t,)
			os.system('java -jar game.jar ai %d %d %.2f' %(t, width, alpha) )
			

def seperator(str):
	f = open('Results/results.csv','at')
	f.write('%s\n' %(str,))
	f.close()	
	
def experiment1():
	alpha_experiment(1000,100)
	
def experiment2():
	for alpha in [0,0.5,1,2]: 
		seperator('time')
		time_experiment(alpha,100)	
		
def experiment3():
	for alpha in [0,0.5,1,2]: 
		seperator('width')
		width_experiment(alpha,1000)	
		
def experiment4():
	for alpha in [0,0.5,1,2]: 
		seperator('width with long time')
		width_experiment(alpha,10000)	

def experiment5(): 
	seperator('width with very long time')
	width_experiment(2,1000000)	

if __name__ == '__main__':
	pass
	# experiment1()
	# experiment2()
	# experiment3()
	# experiment4()
	# experiment5()