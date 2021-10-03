package elan.tweaks.thaumcraft.research.frontend.integration

import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.network.NetworkRegistry
import elan.tweaks.thaumcraft.research.frontend.integration.ThaumcraftResearchTweaks.DEPENDENCIES
import elan.tweaks.thaumcraft.research.frontend.integration.ThaumcraftResearchTweaks.MOD_ID
import elan.tweaks.thaumcraft.research.frontend.integration.ThaumcraftResearchTweaks.MOD_LANGUAGE_ADAPTER
import elan.tweaks.thaumcraft.research.frontend.integration.ThaumcraftResearchTweaks.NAME
import elan.tweaks.thaumcraft.research.frontend.integration.ThaumcraftResearchTweaks.VERSION

@Mod(
    modid = MOD_ID,
    name = NAME,
    version = VERSION,
    dependencies = DEPENDENCIES,
    modLanguageAdapter = MOD_LANGUAGE_ADAPTER
)
object ThaumcraftResearchTweaks {

    const val MOD_ID = "ThaumcraftResearchTweaks"
    const val VERSION = "1.0.0"
    const val NAME = "Thaumcraft Research Tweaks"
    const val MOD_LANGUAGE_ADAPTER = "net.shadowfacts.forgelin.KotlinAdapter"
    const val DEPENDENCIES = "required-after:forgelin;" +
            "required-after:spongemixins;" +
            "required-after:Thaumcraft;"

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        NetworkRegistry.INSTANCE.registerGuiHandler(ThaumcraftResearchTweaks, GuiHandler())
    }
}