class Complex(val real : Double, val imag : Double) {
  def +(other : Complex) = new Complex(
    real + other.real,
    imag + other.imag)

  def *(other : Complex) = new Complex(
    real*other.real - imag*other.imag,
    imag*other.real + real*other.imag)
  
  def magnitude() : Double = Math.sqrt(real*real + imag*imag)
}

class Row(val rowNum : Int,
          val y : Double,
          val xStart : Double,
          val dx : Double,
          val numPoints : Int) {
  def compute() : List[Int] = {
    // TODO
    throw new RuntimeException("TODO - implement this")
  }
}

object Mandelbrot {
  val x1 = -2.0
  val y1 = -2.0
  val x2 = 2.0
  val y2 = 2.0
  val rows = 10
  val cols = 10

  def main(args: Array[String]) {
    val dx = (x2 - x1) / cols
    val dy = (y2 - y1) / rows

    for (j <- 0 until rows) {
      val row = new Row(j, y1 + (j*dy), x1, dx, cols)
      val rowResults = row.compute()
      println(j + ": " + rowResults)
    }
  }
}

Mandelbrot.main(args)
