package co.gyeongmin.lisp.lexer.statements

import co.gyeongmin.lisp.lexer.values.LispValue

case class LispWhenStmt(value: LispValue, thenValue: LispValue)
