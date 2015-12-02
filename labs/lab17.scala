object Lab09 {
  def allEven(list : List[Int]) : Boolean = {
    throw new RuntimeException("TODO")
  }
  
  def reverse(list : List[Int]) : List[Int] = {
    def reverseWork(list : List[Int], accum : List[Int]) : List[Int] = {
      throw new RuntimeException("TODO")
    }
    
    reverseWork(list, List())
  }
  
  def makeIntList(n : Int) : List[Int] = {
    def makeIntListWork(n : Int, accum : List[Int]) : List[Int] = {
      throw new RuntimeException("TODO")
    }
    
    makeIntListWork(n, List())
  }
  
  def replaceMultiplesOf(list : List[Int], n : Int) : List[Int] = {
    def replaceMultiplesOfWork(list : List[Int], n : Int, count : Int, accum : List[Int]) : List[Int] = {
      throw new RuntimeException("TODO")
    }
    
    replaceMultiplesOfWork(list, n, 1, List())
  }
  
  def allPrimesLessThan(n : Int) : List[Int] = {
    throw new RuntimeException("TODO")
  }
  
  def main(args: Array[String]) {
    val l1 = List(1, 6, 3, 9, 2, 3, 7)
    val l2 = List(4, 6, 2)
    
    // Tests for allEven function
    /*
    println(allEven(l1)) // should print false
    println(allEven(l2)) // should print true
    */
    
    // Tests for reverse function
    /*
    println(reverse(l1)) // should print List(7, 3, 2, 9, 3, 6, 1)
    println(reverse(l2)) // should print List(2, 6, 4)
    */
    
    // Tests for makeIntList function
    /*
    println(makeIntList(5))  // should print List(1, 2, 3, 4, 5)
    println(makeIntList(10)) // should print List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    */
    
    // Tests for replaceMultiplesOf
    /*
    println(replaceMultiplesOf(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 2))
      // should print List(1, 2, 3, 0, 5, 0, 7, 0, 9, 0)
    */
    
    // Tests for allPrimesLessThan
    /*
    println(allPrimesLessThan(10)) // should print List(2, 3, 5, 7)
    println(allPrimesLessThan(100))
    */
  }
}

Lab09.main(args)
