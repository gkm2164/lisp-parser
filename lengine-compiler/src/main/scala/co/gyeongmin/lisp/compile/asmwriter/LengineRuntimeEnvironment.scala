package co.gyeongmin.lisp.compile.asmwriter

import co.gyeongmin.lisp.compile.asmwriter.AsmHelper.MethodVisitorWrapper
import co.gyeongmin.lisp.lexer.ast.LispMacroDef
import co.gyeongmin.lisp.lexer.values.symbol.LispSymbol
import org.objectweb.asm.{ClassWriter, Label}

import java.util.concurrent.atomic.AtomicInteger
import scala.collection.mutable

class LengineRuntimeEnvironment(
  val classWriter: ClassWriter,
  val methodVisitor: MethodVisitorWrapper,
  val args: mutable.Map[LispSymbol, LengineRuntimeVariable],
  val className: String,
  val fileName: String,
  numberOfArgs: Int
) {

  private val macros = mutable.Map[LispSymbol, LispMacroDef]()
  def registerMacroDef(symbol: LispSymbol, macroDef: LispMacroDef): Unit =
    macros += (symbol -> macroDef)
  def getMacroVar(ref: LispSymbol): LispMacroDef = macros(ref)
  def hasMacroVar(ref: LispSymbol): Boolean = macros.contains(ref)

  val writeLaterAllScopeList: mutable.ListBuffer[(LispSymbol, Class[_], Int)] = mutable.ListBuffer()
  def writeLaterAllScope(symbol: LispSymbol, typeToBe: Class[_], location: Int): Unit = {
    val tuple = (symbol, typeToBe, location)
    this.writeLaterAllScopeList += tuple
  }

  val writeLaterList: mutable.ListBuffer[(LispSymbol, Class[_], Label, Label, Int)] =
    mutable.ListBuffer()
  def writeLater(
    name: LispSymbol,
    typeToBe: Class[_],
    startLabel: Label,
    endLabel: Label,
    location: Int
  ): Unit = {
    val tuple = (name, typeToBe, startLabel, endLabel, location)
    writeLaterList += tuple
  }

  var captureVariables: Option[LengineVarCapture] = None

  private val varIdx = new AtomicInteger(numberOfArgs)

  def setRequestedCapture(captureVariables: LengineVarCapture): Unit =
    this.captureVariables = Some(captureVariables)

  def registerVariable(value: LispSymbol, varIdx: Int, knownType: Class[_]): Unit =
    args += (value -> LengineRuntimeVariable(Some(varIdx), knownType))

  def deregisterVariable(value: LispSymbol): Unit =
    args -= value

  def getVar(varName: LispSymbol): Option[(Int, Class[_])] = args.get(varName).map {
    case LengineRuntimeVariable(Some(value), declaredType) => (value, declaredType)
  }

  def hasVar(varName: LispSymbol): Boolean = args.contains(varName)
  def allocateNextVar: Int = varIdx.getAndAdd(1)

  def getLastVarIdx: Int = varIdx.get()
}
