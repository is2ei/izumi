package com.github.pshirshov.izumi.distage.model.definition

final case class TrivialModuleDef(bindings: Set[Binding]) extends StandardModuleDef

object TrivialModuleDef extends BindingDSL(Set.empty)
