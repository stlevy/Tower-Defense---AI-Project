base_conf = open('conf.ini')
configurations = base_conf.read()
base_conf.close()

for n in range(1,21):
	level = open('level%d.ini'  %(n,) ,'wt')
	level.write(configurations)
	level.close()

