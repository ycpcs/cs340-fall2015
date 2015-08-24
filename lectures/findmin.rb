#! /usr/bin/ruby

def findmin(arr)
	return arr.inject(arr[0]) {|accum, val| val.<(accum) ? val : accum }
end


#min = findmin([42, 44, 4, 31, 11, 3, 55, 121])
min = findmin(["Hello", "World", "Ruby", "Is", "Cool"])
puts "#{min}"
