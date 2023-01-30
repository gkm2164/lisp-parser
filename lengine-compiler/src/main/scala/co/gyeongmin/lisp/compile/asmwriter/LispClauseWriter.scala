package co.gyeongmin.lisp.compile.asmwriter

import co.gyeongmin.lisp.compile.LengineEnv
import co.gyeongmin.lisp.compile.asmwriter.LengineType.lambdaClass
import co.gyeongmin.lisp.lexer.values.symbol.{EagerSymbol, ObjectReferSymbol}
import co.gyeongmin.lisp.lexer.values.{LispClause, LispValue}
import lengine.runtime.LengineMap
import org.objectweb.asm.Opcodes._
import org.objectweb.asm.{MethodVisitor, Opcodes}

import scala.collection.mutable

class LispClauseWriter(clause: LispClause)(implicit runtimeEnvironment: LengineRuntimeEnvironment) {

  import AsmHelper._

  val mv: MethodVisitor = runtimeEnvironment.methodVisitor

  private def declareObjectRefer(key: String, operands: List[LispValue]): Unit = {
    val map :: _ = operands
    mv.visitLdcInsn(key)
    val keyIdx = runtimeEnvironment.allocateNextVar
    mv.visitIntInsn(Opcodes.ASTORE, keyIdx)
    new LispValueAsmWriter(map).visitForValue(needReturn = true)
    mv.visitCheckCast(classOf[LengineMap])
    mv.visitIntInsn(Opcodes.ALOAD, keyIdx)
    mv.visitMethodCall(
      classOf[LengineMap],
      "get",
      classOf[Object],
      List(classOf[Object])
    )
  }

  def visitForValue(needReturn: Boolean = false): Unit = {
    val operation :: operands = clause.body
    operation match {
      case ObjectReferSymbol(key) => declareObjectRefer(key, operands)
      case s if RuntimeMethodVisitor.supportOperation(s) => RuntimeMethodVisitor.handle(clause.body, needReturn)
      case s: EagerSymbol if runtimeEnvironment.hasFn(s) =>
        runtimeEnvironment.getFn(s).foreach(realFn => {
          LengineEnv.getFn(realFn.name).foreach(fn => {
            val popThis = mutable.ListBuffer[Int]()
            (0 until fn.args).zip(operands).foreach { case (_, value) =>
              new LispValueAsmWriter(value).visitForValue(needReturn = true)
              val loc = runtimeEnvironment.allocateNextVar
              mv.visitIntInsn(Opcodes.ASTORE, loc)
              popThis += loc
            }

            fn.fnEnv.captureVariables.foreach(_.getRequestedCaptures.foreach(symbol => {
              val location = runtimeEnvironment.getVar(symbol)
              if (location.isEmpty) {
                throw new RuntimeException(s"Unable to capture variable: $symbol")
              }

              val Some(locationAddress) = location
              popThis += locationAddress
            }))

            val argCount = fn.args + fn.fnEnv.captureVariables.map(_.getRequestedCaptures.size).getOrElse(0)

            popThis.foreach(mv.visitIntInsn(ALOAD, _))
            mv.visitMethodInsn(
              INVOKESTATIC,
              runtimeEnvironment.className,
              realFn.name.name,
              getFnDescriptor(classOf[Object], argCount),
              false
            )
          })
        })
      case s: EagerSymbol if !runtimeEnvironment.hasVar(s) =>
        throw new RuntimeException(s"unable to find the symbol definition: $s")

      case value@(EagerSymbol(_) | LispClause(_)) =>
        val suspectFn = runtimeEnvironment.allocateNextVar
        new LispValueAsmWriter(value).visitForValue(needReturn = needReturn)
        mv.visitAStore(suspectFn)

        val lClass = lambdaClass(operands.size)
        mv.visitIntInsn(Opcodes.ALOAD, suspectFn)
        mv.visitCheckCast(lClass)
        operands.foreach(v => new LispValueAsmWriter(v).visitForValue(needReturn = true))
        mv.visitInterfaceMethodCall(
          lClass,
          "invoke",
          classOf[Object],
          operands.map(_ => classOf[Object])
        )
    }
  }
}
