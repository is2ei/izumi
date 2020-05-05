package izumi.distage.model.exceptions

import izumi.distage.model.reflection.DIKey
import izumi.fundamentals.graphs.ConflictResolutionError

//class ConflictingDIKeyBindingsException(message: String, val conflicts: Map[DIKey, DIKeyConflictResolution.Failed]) extends DIException(message)
class ConflictResolutionException(message: String, val conflicts: List[ConflictResolutionError[DIKey]]) extends DIException(message)
