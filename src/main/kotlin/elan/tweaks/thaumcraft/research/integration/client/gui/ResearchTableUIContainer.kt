package elan.tweaks.thaumcraft.research.integration.client.gui

import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.EntityPlayer
import thaumcraft.common.container.ContainerResearchTable
import thaumcraft.common.tiles.TileResearchTable

class ResearchTableUIContainer(
    rootComponent: UIComponent,
    player: EntityPlayer,
    entity: TileResearchTable
) : GuiContainer(ContainerResearchTable(player.inventory, entity)) {
    override fun drawGuiContainerBackgroundLayer(p_146976_1_: Float, p_146976_2_: Int, p_146976_3_: Int) {
        println("p_146976_1_: $p_146976_1_, p_146976_2_: $p_146976_2_, p_146976_3_: $p_146976_3_")
        
        // draw root component
        TODO("Not yet implemented")
    }
}

interface UIComponent {
    val minimalSize: Scale
    val children: Set<UIComponent>
    
    fun draw(origin: Vector, mousePosition: Vector)
    fun onClick(point: Vector) 
    fun onLeftMouseButtonDown(point: Vector)
    fun onLeftMouseButtonUp(point: Vector)
    fun onHover(point: Vector)
}

interface UINode {
    val children: Set<UIComponent>
    
}
interface UILeaf
interface Drawable
interface MouseListener
interface UIContainerl
