package com.github.pshirshov.izumi.distage.roles.launcher.exceptions

import com.github.pshirshov.izumi.distage.model.exceptions.DIException
import com.github.pshirshov.izumi.distage.roles.roles.ResourceCheck

class IntegrationCheckException(message: String, val failures: Seq[ResourceCheck.Failure]) extends DIException(message, null)
