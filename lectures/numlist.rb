#! /usr/bin/ruby

class NumList
	def initialize
		@nums = []
	end

	def add(n)
		@nums.push n
	end

	def count
		return @nums.length
	end

	def get(i)
		return @nums[i]
	end

	def each
		(0..(self.count-1)).each do |i|
			n = @nums[i]
			yield n
		end
	end
end

numlist = NumList::new
numlist.add(1)
numlist.add(2)
numlist.add(3)

#(0..(numlist.count-1)).each do |i|
#	puts "#{numlist.get(i)}"
#end

numlist.each do |n|
	puts "Yay: #{n}"
end
