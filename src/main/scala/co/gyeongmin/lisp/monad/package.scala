package co.gyeongmin.lisp

import cats.Monad
import co.gyeongmin.lisp.errors._
import co.gyeongmin.lisp.lexer._

package object monad {
  type LispTokenState[A] = Stream[LispToken] => Either[ParseError, (A, Stream[LispToken])]

  implicit val lispTokenStateMonad: Monad[LispTokenState] = new Monad[LispTokenState] {
    override def flatMap[A, B](fa: LispTokenState[A])(f: A => LispTokenState[B]): LispTokenState[B] = tokens => fa(tokens) match {
      case Right((value, nextTokens)) => f(value)(nextTokens)
      case Left(e) => Left(e)
    }

    override def tailRecM[A, B](a: A)(f: A => LispTokenState[Either[A, B]]): LispTokenState[B] = tokens => f(a)(tokens) match {
      case Left(e) => Left(e)
      case Right(v) => v match {
        case (Left(a), tail) => tailRecM(a)(f)(tail)
        case (Right(b), tail) => Right((b, tail))
      }
    }

    override def pure[A](x: A): LispTokenState[A] = tokens => Right((x, tokens))
  }

  object LispTokenState {
    def pure[A](x: A): LispTokenState[A] = lispTokenStateMonad.pure(x)
    def error(err: ParseError): LispTokenState[Nothing] = _ => Left(err)
  }
}