import os
redos_number = 3
width_list = [1,2,5,10,20,30,40,50,100,200,300,500,1000]
time_list = [10,20,30,40,50,100,200,300,500,1000,2000,3000,4000,5000,7500,10000,20000,50000,100000,200000,500000,1000000]
alpha_list = [x/100.0 for x in range(0,101,2)] # 0,0.02,0.04 ...
alpha__beta_gamma_list = []
for alpha in range(0,11):
	for beta in range(0,11):
		if alpha+beta <= 10:
			alpha__beta_gamma_list.append((alpha/10.0,beta/10.0,(10-alpha-beta)/10.0))
			
def alpha_experiment(t,width):
	print '***************ALPHA With old path feature***************'	
	for i in range(redos_number):
		for alpha in alpha_list:
			print '\n\nrunning for alpha = %.2f\n\n' %(alpha,)
			os.system('java -jar game.jar ai %d %d %.2f 1' %(t, width, alpha) )
	print '***************ALPHA With new path feature***************'	
	for i in range(redos_number):
		for alpha in alpha_list:
			print '\n\nrunning for alpha = %.2f\n\n' %(alpha,)
			os.system('java -jar game.jar ai %d %d %.2f 2' %(t, width, alpha) )

def width_experiment(alpha,t,experiment):
	print '***************WIDTH EXPERIMENTS***************'
	for i in range(redos_number):
		
		for width in width_list:
			print '\n\nrunning for width = %.2f\n\n' %(width,)
			os.system('java -jar game.jar ai %d %d %.2f %d' %(t, width, alpha,experiment) )

def time_experiment(alpha,width):
	print '***************ALPHA With old path feature***************'	
	for i in range(redos_number):
		for t in time_list:
			print '\n\nrunning for time = %.2f\n\n' %(t,)
			os.system('java -jar game.jar ai %d %d %.2f 1' %(t, width, alpha) )
	print '***************ALPHA With new path feature***************'
	for i in range(redos_number):
		for t in time_list:
			print '\n\nrunning for time = %.2f\n\n' %(t,)
			os.system('java -jar game.jar ai %d %d %.2f 2' %(t, width, alpha) )

def seperator(str):
	f = open('Results/results.csv','at')
	f.write('%s\n' %(str,))
	f.close()	
	
def experiment1():
	alpha_experiment(3500,200)
	
def experiment2():
		time_experiment(0.5,100)	
		
def experiment3(): 
	alpha =0.5
	width=100
	print '***************New Product Heuristics ***************'
	for i in range(redos_number):
		for t in time_list:
			print '\n\nrunning for time = %.2f\n\n' %(t,)
			os.system('java -jar game.jar ai %d %d %.2f 3' %(t, width, alpha) )

def experiment4():
	for experiment in [1,2,3]:
		width_experiment(0.5,1000,experiment)

# def experiment5():
	# t = 100000
	# w = 15
	# alpha = 0.16
	# for experiment in [1,2,3]:
		# for i in range(redos_number):
			# print '\n\nrunning experiment %d number %d\n\n' %(experiment,i)
			# os.system('java -jar game.jar ai %d %d %.2f %d' %(t, w, alpha,experiment) )

if __name__ == '__main__':
	pass
	experiment1()
	experiment2()
	experiment3()
	experiment4()
	# experiment5()