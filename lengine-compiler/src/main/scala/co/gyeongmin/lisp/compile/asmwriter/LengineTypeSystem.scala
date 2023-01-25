package co.gyeongmin.lisp.compile.asmwriter

import co.gyeongmin.lisp.compile.LengineEnv
import co.gyeongmin.lisp.lexer.values.LispUnit.ResolveHelper
import co.gyeongmin.lisp.types.{LengineChar, LengineDouble, LengineInteger, LengineList, LengineString, LengineType}
import org.objectweb.asm.{MethodVisitor, Opcodes, Type}

object LengineTypeSystem {
  implicit class TypeCastor(lengineType: LengineType) {

    def cast(toType: LengineType)(implicit mv: MethodVisitor): Unit = {
      val originType  = Type.getType(lengineType.getJvmNativeType)
      val castingType = Type.getType(toType.getJvmNativeType)

      if (lengineType == toType) {
        return
      }

      toType match {
        case LengineString =>
          mv.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            castingType.getInternalName,
            "valueOf",
            Type.getMethodDescriptor(
              castingType,
              if (lengineType != LengineList) {
                originType
              } else {
                Type.getType(classOf[java.lang.Object])
              }
            ),
            false
          )
        case LengineDouble => lengineType match {
          case LengineChar => mv.visitInsn(Opcodes.I2D)
          case LengineInteger => mv.visitInsn(Opcodes.L2D)
          case _ => throw new IllegalArgumentException("Unsupported casting")
        }
      }
    }
  }

  implicit class VarCommand(types: LengineType) {
    def getCommands: (Int, Int) = types match {
        case LengineChar => (Opcodes.ISTORE, Opcodes.ILOAD)
        case LengineInteger => (Opcodes.LSTORE, Opcodes.LLOAD)
        case LengineDouble => (Opcodes.DSTORE, Opcodes.DLOAD)
        case LengineString => (Opcodes.ASTORE, Opcodes.ALOAD)
        case _ => (Opcodes.ASTORE, Opcodes.ALOAD)
    }

    def ADD = types match {
      case LengineChar => Opcodes.IADD
      case LengineInteger => Opcodes.LADD
      case LengineDouble => Opcodes.DADD
    }

    def SUB = types match {
      case LengineChar => Opcodes.ISUB
      case LengineInteger => Opcodes.LSUB
      case LengineDouble => Opcodes.DSUB
    }

    def MUL = types match {
      case LengineChar => Opcodes.IMUL
      case LengineInteger => Opcodes.LMUL
      case LengineDouble => Opcodes.DMUL
    }

    def DIV = types match {
      case LengineChar => Opcodes.IDIV
      case LengineInteger => Opcodes.LDIV
      case LengineDouble => Opcodes.DDIV
    }
  }

  implicit val resolveHelper: ResolveHelper = name => LengineEnv.getVarInfo(name).map(_.storedType)
}
