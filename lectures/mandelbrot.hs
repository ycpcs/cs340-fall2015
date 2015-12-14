-- Mandelbrot in Haskell

module Main where
   type Complex = (Double, Double)

   cmake :: Double -> Double -> Complex
   cmake r  i = (r, i)

   creal :: Complex -> Double
   creal (r, i) = r

   cimag :: Complex -> Double
   cimag (r, i) = i

   cadd :: Complex -> Complex -> Complex
   cadd (a, b) (c, d) = (a+c, b+d)

   cmul :: Complex -> Complex -> Complex
   cmul (a, b) (c, d) = (a*c - b*d, b*c + a*d)

   cmag :: Complex -> Double
   cmag (r, i) = sqrt (r*r + i*i)

   citer :: Complex -> Int
   citer c = citer_work c (0, 0) 0

   citer_work :: Complex -> Complex -> Int -> Int
   citer_work c z count =
       if ((count >= 1000) || (cmag z) >= 2.0)
           then count
           else citer_work c (cadd c (cmul z z)) (count + 1)

   -- rownum, y, xmin, dx, numcols
   type Row = (Int, Double, Double, Double, Int)

   row_rownum :: Row -> Int
   row_rownum row =
       let (rownum, _, _, _, _) = row
         in rownum

   row_y :: Row -> Double
   row_y row =
       let (_, y, _, _, _) = row
         in y

   row_xmin :: Row -> Double
   row_xmin row =
       let (_, _, xmin, _, _) = row
         in xmin

   row_dx :: Row -> Double
   row_dx row =
       let (_, _, _, dx, _) = row
         in dx

   row_numcols :: Row -> Int
   row_numcols row =
       let (_, _, _, _, numcols) = row
         in numcols

   -- rownum, list of iteration counts
   type RowResult = (Int, [Int])

   compute_row :: Row -> RowResult
   compute_row row = compute_row_work row ((row_numcols row) - 1) []

   compute_row_work :: Row -> Int -> [Int] -> RowResult
   compute_row_work row i accum =
       if (i < 0)
           then
              let rownum = (row_rownum row)
                  in (rownum, accum)
           else
               let c = cmake ((row_xmin row) + ((fromIntegral i) * (row_dx row))) (row_y row)
               in compute_row_work row (i - 1) ((citer c) : accum)

   compute :: Double -> Double -> Double -> Double -> Int -> Int -> [RowResult]
   compute x1 y1 x2 y2 numcols numrows =
       let dx = (x2 - x1) / (fromIntegral numcols)
           dy = (y2 - y1) / (fromIntegral numrows)
       in compute_work x1 y1 dx dy numcols (numrows - 1) []

   -- example call:
   -- compute (-2) (-2) 2 2 10 10
   compute_work :: Double -> Double -> Double -> Double -> Int -> Int -> [RowResult] -> [RowResult]
   compute_work xmin ymin dx dy numcols j accum =
       if (j < 0)
         then accum
         else
            let rr = compute_row (j, (ymin + (fromIntegral j)*dy), xmin, dx, numcols)
            in compute_work xmin ymin dx dy numcols (j - 1) (rr : accum)
