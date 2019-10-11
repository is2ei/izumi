package izumi.fundamentals.reflection

import scala.collection.mutable
import scala.language.experimental.macros
import scala.reflect.macros.blackbox

object CirceTool {
  def make[T](): Unit = macro CirceToolMacro.make[T]
}

class CirceToolMacro(val c: blackbox.Context) {

  import c.universe._

  @inline def make[T: c.WeakTypeTag](): c.Expr[Unit] = {
    val all = new mutable.HashSet[Type]()
    processType(weakTypeOf[T], all)
    println("===")
    val x = all
      .toSeq
      .filterNot(t => t.toString.startsWith("scala") || t.toString.startsWith("java"))
      .filter(t => t.typeSymbol.isClass)
      .map {
        t =>
          s"implicit def `codec:${t}`: Codec.AsObject[$t] = deriveCodec"
      }
      .distinct

    println(x.sorted.mkString("\n"))

    reify(())
  }

  def processType(t0: Type, all: mutable.HashSet[Type]): Unit = {
    val t = t0.dealias
    if (t.typeArgs.isEmpty) {
      handleNonGeneric(t, all)
    } else {
      t.typeArgs.foreach(a => processType(a, all))
    }



    //m.map(_.asMethod).foreach(m => println((m.name, )))

  }

  private def handleNonGeneric(t0: c.universe.Type, all: mutable.HashSet[c.universe.Type]): Unit = {
    val t = t0.dealias
    if (!all.contains(t)) {
      all.add(t)

      // this triggers full typing so  knownDirectSubclasses works
      if (t.toString == "" || t.typeSymbol.toString == "") {
        ???
      }

      if (t.typeSymbol.isClass) {
        if (t.typeSymbol.asClass.knownDirectSubclasses.isEmpty) {
          val methods = t.members.filter(m => m.isMethod && m.asMethod.isGetter).map(_.asMethod)
          methods.foreach(m => processType(m.returnType, all))

          //        println(("?", Modifiers(t.finalResultType.typeSymbol.asInstanceOf[{def flags: FlagSet}].flags).hasFlag(Flag.TRAIT) ))
        } else {
          t.typeSymbol.asClass.knownDirectSubclasses.foreach {
            s =>
              if (s.isType) {
                processType(s.asType.toType, all)
              } else {
                println(("Not a type?..", s))
              }
          }
        }
      } else {
        println(("Not a class nor trait?..", t))

      }

    }
  }
}
