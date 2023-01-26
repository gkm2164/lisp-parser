package co.gyeongmin.lisp.compile.asmwriter

import co.gyeongmin.lisp.compile.LengineEnv
import co.gyeongmin.lisp.compile.LengineEnv.allocateVariable
import co.gyeongmin.lisp.lexer.values.{LispClause, LispValue}
import co.gyeongmin.lisp.lexer.values.symbol.EagerSymbol
import co.gyeongmin.lisp.types.{LengineString, LengineType}
import org.objectweb.asm.Opcodes._
import org.objectweb.asm.{MethodVisitor, Type}

class LispClauseWriter(mv: MethodVisitor, clause: LispClause)(implicit args: Map[String, Int]){

  import LengineTypeSystem._

  implicit val mv$: MethodVisitor = mv
  
  def writeValue(finalCast: Option[LengineType] = None): Unit = {
    val operation = clause.body.head
    val operands = clause.body.tail

    operation match {
      case EagerSymbol(op) if "+-*/".contains(op) =>
        if (clause.resolveType.isLeft) {
          throw new RuntimeException(s"Unable to decide the type of clause: ${clause.resolveType}")
        }
        val finalResolvedType = clause.resolveType.right.get
        if (op == "+" && finalResolvedType == LengineString) {
          defineStringBuild(operands)
        } else {
          defineNumberCalc(op, finalResolvedType, operands)
          finalCast.foreach(finalResolvedType.cast)
        }
      case EagerSymbol("println") => definePrintln(operands)
      case EagerSymbol(operation) if LengineEnv.hasFn(operation) =>
        LengineEnv.getFn(operation).foreach(fn => {
          fn.args.zip(operands).foreach { case (argLoc, value) =>
            new LispValueAsmWriter(mv, value).writeValue()
            mv.visitIntInsn(ASTORE, argLoc)
          }
          mv.visitJumpInsn(JSR, fn.atLabel)
        })
    }
  }

  private def defineStringBuild(operands: List[LispValue]): Unit = {
    mv.visitTypeInsn(NEW, "java/lang/StringBuilder")
    mv.visitInsn(DUP)
    mv.visitMethodInsn(
      INVOKESPECIAL,
      "java/lang/StringBuilder",
      "<init>",
      Type.getMethodDescriptor(Type.getType(java.lang.Void.TYPE)),
      false
    )
    val sbIdx = allocateVariable
    mv.visitIntInsn(ASTORE, sbIdx)
    operands.foreach(value => {
      val thisVar = allocateVariable

      new LispValueAsmWriter(mv, value).writeValue()
      val (store, load) = value.resolveType.map(_.getCommands) match {
        case Left(err) => throw new RuntimeException(s"unable to process: $err")
        case Right(value) => value
      }

      mv.visitIntInsn(store, thisVar)
      mv.visitIntInsn(ALOAD, sbIdx)
      mv.visitIntInsn(load, thisVar)
      mv.visitMethodInsn(
        INVOKEVIRTUAL,
        "java/lang/StringBuilder",
        "append",
        Type.getMethodDescriptor(
          Type.getType(classOf[java.lang.StringBuilder]),
          Type.getType(value.resolveType.right.get.getJvmNativeType)
        ),
        false
      )
    })
    mv.visitIntInsn(ALOAD, sbIdx)
    mv.visitMethodInsn(
      INVOKEVIRTUAL,
      "java/lang/StringBuilder",
      "toString",
      Type.getMethodDescriptor(
        Type.getType(classOf[java.lang.String])
      ),
      false
    )
  }

  private def defineNumberCalc(op: String, finalResolvedType: LengineType, operands: List[LispValue]): Unit = {
    operands.foreach(v => {
      new LispValueAsmWriter(mv, v).writeValue(None)
      v.resolveType match {
        case Left(err) => throw new RuntimeException(s"Unable to cast type!: $err")
        case Right(resolvedType) if finalResolvedType != resolvedType => resolvedType.cast(finalResolvedType)
        case Right(resolvedType) if finalResolvedType == resolvedType =>
      }
    })

    mv.visitInsn(op match {
      case "+" => finalResolvedType.ADD
      case "-" => finalResolvedType.SUB
      case "*" => finalResolvedType.MUL
      case "/" => finalResolvedType.DIV
    })
  }

  private def definePrintln(operands: List[LispValue]): Unit = {
    operands.foreach(v => new LispValueAsmWriter(mv, v).writeValue(Some(LengineString)))
    val temporalVarIdx = allocateVariable
    mv.visitIntInsn(ASTORE, temporalVarIdx)
    mv.visitFieldInsn(GETSTATIC,
      "java/lang/System",
      "out",
      "Ljava/io/PrintStream;")
    mv.visitIntInsn(ALOAD, temporalVarIdx)
    mv.visitMethodInsn(INVOKEVIRTUAL,
      "java/io/PrintStream",
      "println",
      Type.getMethodDescriptor(
        Type.getType(Void.TYPE),
        Type.getType(classOf[String])
      ),
      false)
  }
}
