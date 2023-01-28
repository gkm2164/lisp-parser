package co.gyeongmin.lisp.compile.asmwriter

import co.gyeongmin.lisp.compile.LengineEnv
import co.gyeongmin.lisp.lexer.values.{LispClause, LispValue}
import co.gyeongmin.lisp.lexer.values.symbol.EagerSymbol
import org.objectweb.asm.{MethodVisitor, Opcodes}
import org.objectweb.asm.Opcodes._

import scala.collection.mutable

class LispClauseWriter(clause: LispClause)(implicit runtimeEnvironment: LengineRuntimeEnvironment) {

  import AsmHelper._

  val mv: MethodVisitor = runtimeEnvironment.methodVisitor

  def writeValue(): Unit = {
    val operation = clause.body.head
    val operands = clause.body.tail

    operation match {
      case s if RuntimeMethodVisitor.supportOperation(s) => RuntimeMethodVisitor.handle(clause.body)
      case s: EagerSymbol if LengineEnv.hasFn(s) =>
        LengineEnv.getFn(s).foreach(fn => {
          val popThis = mutable.ListBuffer[Int]()
          (0 until fn.args).zip(operands).foreach { case (_, value) =>
            new LispValueAsmWriter(value).writeValue()
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
            s.name,
            getFnDescriptor(classOf[Object], argCount),
            false
          )
        })

      case _ =>
    }
  }
}
