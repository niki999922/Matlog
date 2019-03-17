module Grammar where

import Data.List (intercalate)

data Binop = Or | And | Impl 

data Expr = Binary Binop Expr Expr | Not Expr | Var String

instance Show Binop where
  show Impl = "->"
  show Or   = "|"
  show And  = "&"

instance Show Expr where
  show (Var a)         = a
  show (Not a)         = "(!" ++ show a ++ ")"
  show (Binary op a b) = "(" ++ intercalate "," [show op, show a, show b] ++ ")"
