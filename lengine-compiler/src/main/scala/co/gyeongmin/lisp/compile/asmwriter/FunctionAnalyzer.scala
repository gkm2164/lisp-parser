package co.gyeongmin.lisp.compile.asmwriter

import co.gyeongmin.lisp.lexer.statements.{LispDoStmt, LispForStmt, LispLetDef, LispLoopStmt, LispValueDef}
import co.gyeongmin.lisp.lexer.values.functions.GeneralLispFunc
import co.gyeongmin.lisp.lexer.values.numbers.{FloatNumber, IntegerNumber}
import co.gyeongmin.lisp.lexer.values.seq.{LispList, LispString}
import co.gyeongmin.lisp.lexer.values.symbol.{EagerSymbol, LispSymbol}
import co.gyeongmin.lisp.lexer.values.{LispChar, LispClause, LispValue}

object FunctionAnalyzer {

  /**
   * This method is to visit AST and identify which variable is not able to find.
   *
   * `captureVariables` contains what is identified, and what is unidentified for the methods.
   * If it is about to define a function, then it should create child capture, as the scope is going to be changed.
   * This is done in recursive way, as the tree is constructed in recursively.
   *
   * @param captureVariables store currently known variable & unknown variable
   * @param body             could be clause
   */
  def captureUnknownVariables(captureVariables: LengineVarCapture, body: LispValue): Unit = {
    def traverseLoopTree(capture: LengineVarCapture, loop: LispLoopStmt): Unit = loop match {
      case LispLoopStmt(Nil, body) =>
        captureUnknownVariables(capture, body)
      case LispLoopStmt(LispForStmt(symbol, seq) :: tail, body) =>
        captureUnknownVariables(capture, seq)
        val childCapture = new LengineVarCapture(capture)
        childCapture.ignoreCapture(symbol)
        traverseLoopTree(childCapture, LispLoopStmt(tail, body))
        capture.mergeChild(childCapture)
    }

    body match {
      case LispChar(_) | IntegerNumber(_) | FloatNumber(_) | LispString(_) =>
      case LispList(body) =>
        body.foreach(v => captureUnknownVariables(captureVariables, v))
      case ref: LispSymbol => captureVariables.requestCapture(ref)
      case LispClause(op :: value) if RuntimeMethodVisitor.supportOperation(op) =>
        value.foreach(v => captureUnknownVariables(captureVariables, v))
      case LispClause(body) =>
        body.foreach(v => captureUnknownVariables(captureVariables, v))
      case LispValueDef(symbol, body) =>
        captureUnknownVariables(captureVariables, body)
        captureVariables.requestCapture(symbol)
      case GeneralLispFunc(placeholders, body) =>
        val childCapture = new LengineVarCapture(captureVariables)
        placeholders.foreach {
          case symbol: LispSymbol => childCapture.ignoreCapture(symbol)
        }
        captureUnknownVariables(childCapture, body)
        captureVariables.mergeChild(childCapture)
      case LispForStmt(symbol, seq) =>
        val childCapture = new LengineVarCapture(captureVariables)
        childCapture.ignoreCapture(symbol)
        captureUnknownVariables(childCapture, seq)
        captureVariables.mergeChild(childCapture)
      case loop: LispLoopStmt =>
        traverseLoopTree(captureVariables, loop)
      case LispLetDef(name, value, body) =>
        val childCapture = new LengineVarCapture(captureVariables)
        captureUnknownVariables(childCapture, value)
        childCapture.ignoreCapture(name)
        captureUnknownVariables(childCapture, body)
        captureVariables.mergeChild(childCapture)
      case LispDoStmt(body) =>
        body.foreach(captureUnknownVariables(captureVariables, _))
    }
  }

  def isTailRecursion(itself: Option[LispSymbol], body: LispValue): Boolean = body match {
    case LispClause((symbol: LispSymbol) :: _) if symbol == EagerSymbol("$") || itself.contains(symbol) =>
      true
    case LispClause(EagerSymbol("if") :: _ :: thenValue :: elseValue :: Nil) =>
      isTailRecursion(itself, thenValue) || isTailRecursion(itself, elseValue)
    case LispDoStmt(last :: Nil) =>
      isTailRecursion(itself, last)
    case _ @LispDoStmt(_ :: tail) =>
      isTailRecursion(itself, LispDoStmt(tail))
    case LispLetDef(symbol, _, body) if !itself.contains(symbol) && symbol != EagerSymbol("$") =>
      isTailRecursion(itself, body)
    case LispLoopStmt(Nil, body) =>
      isTailRecursion(itself, body)
    case LispLoopStmt(head :: tail, body) if !itself.contains(head.symbol) && head.symbol != EagerSymbol("$") =>
      isTailRecursion(itself, LispLoopStmt(tail, body))
    case _ => false
  }
}
