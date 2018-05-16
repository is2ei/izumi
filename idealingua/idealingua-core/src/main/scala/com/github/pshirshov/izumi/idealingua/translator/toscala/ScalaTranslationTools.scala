package com.github.pshirshov.izumi.idealingua.translator.toscala

import com.github.pshirshov.izumi.idealingua.model.common.{StructureId, TypeId}
import com.github.pshirshov.izumi.idealingua.translator.toscala.types.CompositeStructure

import scala.meta._


class ScalaTranslationTools(ctx: STContext) {
  import ctx.conv._

  def mkStructure(id: StructureId): CompositeStructure = {
    val fields = ctx.typespace.structure.structure(id).toScala
    new CompositeStructure(ctx, fields)
  }


  def idToParaName(id: TypeId): Term.Name = Term.Name(ctx.typespace.tools.idToParaName(id))
}
