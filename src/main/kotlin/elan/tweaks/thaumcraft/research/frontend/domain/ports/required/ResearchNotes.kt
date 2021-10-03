package elan.tweaks.thaumcraft.research.frontend.domain.ports.required

import thaumcraft.api.aspects.Aspect

interface ResearchNotes {
    val present: Boolean

    val complete: Boolean
    
    fun erase(hexKey: String): Result<Unit>
    fun write(hexKey: String, aspect: Aspect): Result<Unit>
    
    fun findUsedAspectAmounts(): Map<Aspect, Int>
}