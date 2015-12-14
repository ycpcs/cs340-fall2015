module Main where
    data Complex = Complex { real :: Double,
                             imag :: Double } deriving Show

    cadd :: Complex -> Complex -> Complex
    cadd l r = Complex { real=(real l) + (real r), imag=(imag l) + (imag r) }
