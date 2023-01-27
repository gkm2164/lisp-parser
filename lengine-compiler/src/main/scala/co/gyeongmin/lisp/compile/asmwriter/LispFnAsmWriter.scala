package co.gyeongmin.lisp.compile.asmwriter

import co.gyeongmin.lisp.compile.LengineEnv
import co.gyeongmin.lisp.lexer.statements.LispFuncDef
import co.gyeongmin.lisp.lexer.values.LispClause
import co.gyeongmin.lisp.lexer.values.LispUnit.traverse
import co.gyeongmin.lisp.lexer.values.symbol.{EagerSymbol, LispSymbol}
import lengine.runtime.LengineUnit
import org.objectweb.asm.{Label, Opcodes, Type}

class LispFnAsmWriter(f: LispFuncDef) {
  def writeValue(): Unit = {
    val fnLabel = new Label
    LengineEnv.defineFn(f.symbol.name, fnLabel, f.fn.placeHolders.size, (mv, args, varNumTracer) => {
      mv.visitCode()
      mv.visitLabel(fnLabel)
      val argmap = traverse(f.fn.placeHolders
        .map(holder => holder.as[LispSymbol]))
        .map(_.map(_.name).zip(args)) match {
        case Left(err) => throw new RuntimeException(s"unexpected error: $err")
        case Right(value) => value.toMap
      }
      new LispValueAsmWriter(mv, f.fn.body)(argmap, varNumTracer).writeValue(None)
      f.fn.body match {
        case LispClause(EagerSymbol("println") :: _) =>
          mv.visitTypeInsn(Opcodes.NEW, Type.getType(classOf[LengineUnit]).getInternalName)
          mv.visitInsn(Opcodes.DUP)
          mv.visitMethodInsn(
            Opcodes.INVOKESPECIAL,
            Type.getType(classOf[LengineUnit]).getInternalName,
            "<init>",
            Type.getMethodDescriptor(
              Type.getType(java.lang.Void.TYPE)
            ),
            false
          )
        case _ =>

      }
    })
  }
}
