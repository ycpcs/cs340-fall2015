#! /usr/bin/ruby

class Object
	def self.sayhello
		puts "Hello!"
		puts "self.class is #{self.class}"
		puts "This class is #{self.name}"
	end
end

class Person
	sayhello
end

class Vehicle
	sayhello
end
