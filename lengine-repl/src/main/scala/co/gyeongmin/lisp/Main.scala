package co.gyeongmin.lisp

import co.gyeongmin.lisp.builtin.Builtin
import co.gyeongmin.lisp.execution.{compileFile, replLoop, runFile}

import scala.annotation.tailrec

object Main {
  def main(args: Array[String]): Unit = {
    val LengineOptions(compile, verbose, openFilename) = parseArgs(args)
    val env = Builtin.symbols(verbose)

    if (!compile) {
      if (openFilename.nonEmpty) {
        runFile(openFilename.get, env)
      } else {
        replLoop(env)
      }
    } else {
      compileFile(openFilename.get, env)
    }
  }

  def parseArgs(args: Array[String]): LengineOptions = {
    @tailrec
    def loop(
      remains: List[String],
      lengineOptions: LengineOptions
    ): LengineOptions = remains match {
      case Nil => lengineOptions
      case ("-v" | "--verbose") :: tail =>
        loop(tail, lengineOptions.copy(verbose = true))
      case ("-c" | "--compile") :: tail =>
        loop(tail, lengineOptions.copy(compile = true))
      case filename :: tail =>
        loop(tail, lengineOptions.copy(openFilename = Some(filename)))
      case _ =>
        throw new RuntimeException(s"Invalid parameters: ${args.mkString(" ")}")
    }

    loop(args.toList, LengineOptions())
  }
}
