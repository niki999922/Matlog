module Main where

import Grammar (Expr (..))
import Lexer (alexScanTokens)
import Parser (parser)

main :: IO ()
main = do
  input <- getLine
  case parser(alexScanTokens input) of
    Right expr -> putStrLn $ show expr
