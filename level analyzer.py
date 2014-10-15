# this script analyzes the levels of the game and create a results file
import pdb 
import itertools

def analyze_level(level):
	def get_grid():
		grid = []
		empty = True
		for line in level:
			row = []			
			for ch in line:
				if 0 <= ord(ch) - ord('0') <= 9:
					row.append(ord(ch) - ord('0'))
					if ord(ch) != ord('0'):
						empty = False
			grid.append(row)		
		# for l in grid:
			# for n in l:
				# if n == 0 : 
					# print ' ',
				# else: 
					# print n,
			# print ''
		if empty:
			return None
		return grid
		
	def calculate_length(grid):
		length = 1 # the final step 
		for line in grid:
			for n in line:
				if n == 1:
					length = length + 1
		return length	
	
	def calculate_effective_area(grid):
		max_x = max_y = min_x = min_y = None

		for y , line in enumerate(grid):
			for x, ch in enumerate(line):
				if grid[y][x] == 0:
					continue
				if min_x is None or  x < min_x:
					min_x = x
				if max_x is None or x > max_x: 
					max_x = x
					
				if min_y is None or y < min_y:
					min_y = y
				if max_y is None or y > max_y: 
					max_y = y
					
		# print "%s -> x : (%d,%d) y : (%d,%d) " %(level.name,min_x,max_x,min_y,max_y)
		return (max_x - min_x + 1 ) * (max_y - min_y + 1 )
		
	def calculate_attack_angles(grid):
		def get_attack_angles(x,y):
			angles = 0
			
			for deltaX,deltaY in itertools.product(range(-1,2),range(-1,2)):
				if deltaX == deltaY == 0:
					continue
				if not 0 <= x+deltaX < len(grid[0]) :
					continue
				if not 0 <= y+deltaY < len(grid) :
					continue
				if grid[y+deltaY][x+deltaX] == 1 or  grid[y+deltaY][x+deltaX] == 2:
					angles += 1
			return angles
			
		attack_angles = 0
		for y,x in itertools.product(range(len(grid)),range(len(grid[0]))):
			if grid[y][x] == 0:
				attack_angles += get_attack_angles(x,y)
			# if i % len(grid[0]) == 0 and i!= 0:
				# print ''
			# print grid[y][x],
			
			# i = i+1
		# print ''
		return attack_angles
		
	gridd = get_grid()
	if not gridd:
		return -1,-1,-1
	path_length = calculate_length(gridd)
	area = calculate_effective_area(gridd)
	angles = calculate_attack_angles(gridd)
	return path_length , area, angles
	

seperator = ','
out = open('levels/analasis.csv','wt')
out.write("level"+seperator+"path length"+seperator+"effective area"+seperator+"attack angles"+seperator+"average angles\n")

for n in range(1,21):
	level = open('levels/level%d.txt'  %(n,) ,'rt')
	out.write('['+level.name+']'+seperator)
	length,area,angles = analyze_level(level)		
	out.write(("%d"+seperator) %(length,))
	out.write(("%d"+seperator) %(area,))
	out.write(("%d"+seperator) %(angles,))
	out.write(("%.2f\n") %(float(angles)/length,)) 
	level.close()

