package co.gyeongmin.lisp.compile

import org.scalatest.{FlatSpec, Matchers}
class MainTest extends FlatSpec with Matchers {
  "builtin function with compile" should "run and return" in {
    println("hello.lisp")
    Main.main(Array(
      "./compile-example/hello.lisp",
    ))
  }

  "builtin function with compile boolean.lisp" should "run and return" in {
    println("boolean.lisp")
    Main.main(Array(
      "./compile-example/boolean.lisp",
    ))
  }

  "map type object with compile map.lisp" should "run and return" in {
    println("map.lisp")
    Main.main(Array(
      "./compile-example/map.lisp",
    ))
  }

  "map type object with compile lambda.lisp" should "run and return" in {
    println("lambda.lisp")
    Main.main(Array(
      "./compile-example/lambda.lisp",
    ))
  }

  "seq type with compile seq-test.lisp" should "run and return" in {
    println("seq-test.lisp")
    Main.main(Array(
      "./compile-example/seq-test.lisp",
    ))
  }

  "read-line test compile readline.lisp" should "run and return" in {
    println("readline.lisp")
    Main.main(Array(
      "./compile-example/readline.lisp",
    ))
  }

  "module test compile module.lisp" should "run and return" in {
    println("module.lisp")
    Main.main(Array(
      "./compile-example/module.lisp",
    ))
  }

  "math test compile math.lisp" should "run and return" in {
    println("math.lisp")
    Main.main(Array(
      "./compile-example/math.lisp",
    ))
  }

  "import-module test compile import-module.lisp" should "run and return" in {
    println("import-module.lisp")
    Main.main(Array(
      "./compile-example/import-module.lisp",
    ))
  }

  "string test compile string.lisp" should "run and return" in {
    println("string.lisp")
    Main.main(Array(
      "./compile-example/string.lisp",
    ))
  }

  "infinity loop compile inifinity-loop.list" should "run and return" in {
    println("infinity-loop.lisp")
    Main.main(Array(
      "./compile-example/infinity-loop.lisp"
    ))
  }

  "string test compile process-membrane.lisp" should "run and return" in {
    println("process-membrane.lisp")
    Main.main(Array(
      "./compile-example/process-membrane.lisp",
    ))
  }

  "string test compile process-membrane-2.lisp" should "run and return" in {
    println("process-membrane-2.lisp")
    Main.main(Array(
      "./compile-example/process-membrane-2.lisp",
    ))
  }

  "string test compile json.lisp" should "run and return" in {
    println("json.lisp")
    Main.main(Array(
      "./compile-example/json.lisp",
    ))
  }
}
