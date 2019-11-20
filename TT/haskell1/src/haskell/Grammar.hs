module Grammar where

import Data.List (intercalate)

data Expr = Triple Application Ident Expr | TripleL Ident Expr | ExprApl Application | JustAtom Atom

data Application = Binary Application Atom | BinaryIdent Ident Ident | Atoms Atom | Nothings

data Atom = LRBrack Expr | Markers Ident

data Ident = Var String

instance Show Expr where
  show (Triple a b c)          = "(" ++ show a ++ " (\\" ++ show b ++ "." ++ show c ++ "))"
  show (TripleL b c)           = "(\\" ++ show b ++ "." ++ show c ++ ")"
  show (JustAtom a)            = show a
  show (ExprApl a)             = show a

instance Show Application where
  show (BinaryIdent a b)       = "(" ++ show a ++ " " ++ show b ++ ")"
  show (Binary a b)            = "(" ++ show a ++ " " ++ show b ++ ")"
  show (Atoms a)               = show a
  show (Nothings)              = ""

instance Show Atom where
  show (LRBrack a)             = "" ++ show a ++ ""
  show (Markers a)             = show a

instance Show Ident where
  show (Var a)                 = a
