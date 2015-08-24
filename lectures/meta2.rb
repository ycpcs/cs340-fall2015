#! /usr/bin/ruby

class Object
	def self.gen_getters_and_setters(*names)
		names.each do |name|
			#puts "#{name}"

			fieldname_sym = "@#{name}".to_sym

			setter = Proc.new do |value|
				#puts "self.class is #{self.class}"
				self.instance_variable_set(fieldname_sym, value)
			end
			self.send(:define_method, "set_#{name}", setter)

			getter = Proc.new do
				#puts "self.class is #{self.class}"
				return self.instance_variable_get(fieldname_sym)
			end
			self.send(:define_method, "get_#{name}", getter)
		end
	end
end


class Person
	gen_getters_and_setters :name, :age

	def initialize(name, age)
		@name = name
		@age = age
	end
end

p = Person::new("Dave", 41)
puts "Original age is: #{p.get_age}"

p.set_age(42)
puts "Happy birthday, your age is now #{p.get_age}"
