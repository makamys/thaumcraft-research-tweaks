package elan.tweaks.thaumcraft.research.integration.client.gui

import elan.tweaks.common.gui.component.UIComponent
import elan.tweaks.common.gui.geometry.Rectangle
import elan.tweaks.common.gui.geometry.grid.Grid
import elan.tweaks.common.gui.geometry.grid.GridDynamicListAdapter
import elan.tweaks.thaumcraft.research.integration.client.container.ResearchTableContainerFactory
import elan.tweaks.thaumcraft.research.integration.client.gui.textures.ResearchTableInventoryTexture
import elan.tweaks.thaumcraft.research.integration.client.gui.textures.ResearchTableInventoryTexture.AspectPools
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.world.World
import thaumcraft.api.aspects.Aspect
import thaumcraft.common.Thaumcraft
import thaumcraft.common.lib.network.PacketHandler
import thaumcraft.common.lib.network.playerdata.PacketAspectCombinationToServer
import thaumcraft.common.tiles.TileResearchTable

object ResearchTableGuiFactory {

    fun createFor(
        player: EntityPlayer,
        world: World, x: Int, y: Int, z: Int
    ): ResearchTableGui {
        val tableEntity = world.getTableTileEntity(x, y, z)

        return ResearchTableGui(
            createContainer(player, world, x, y, z),
            components = aspectPoolsOf(player, tableEntity)
        )
    }

    private fun aspectPoolsOf(
        player: EntityPlayer,
        tableEntity: TileResearchTable
    ): List<UIComponent> {
        val leftAspectPool = aspectPoolFor(
            player,
            tableEntity,
            AspectPools.leftRectangle
        ) { index, _ -> index % 2 == 0 }

        val rightAspectPool = aspectPoolFor(
            player,
            tableEntity,
            AspectPools.rightRectangle
        ) { index, _ -> index % 2 != 0 }
        
        return listOf(leftAspectPool, rightAspectPool)
    }

    private fun aspectPoolFor(
        player: EntityPlayer,
        tableEntity: TileResearchTable,
        bounds: Rectangle,
        aspectSelector: (Int, Aspect) -> Boolean
    ): AspectPool {
        val aspectPoolGrid: Grid<Aspect> = GridDynamicListAdapter(
            bounds = bounds,
            cellSize = AspectPools.ASPECT_CELL_SIZE_PIXEL
        ) {
            discoveredAspectsBy(player)
                .aspectsSorted
                .filterIndexed(aspectSelector)
        }
        
        return AspectPool(
            aspectPoolGrid,
            aspectAmountProvider(player),
            bonusAspectAmountProvider(tableEntity),
            aspectCombinationRequestSender(player, tableEntity)
        )
    }

    private fun aspectCombinationRequestSender(
        player: EntityPlayer,
        tableEntity: TileResearchTable
    ) = { aspect: Aspect ->
        PacketHandler.INSTANCE.sendToServer(
            PacketAspectCombinationToServer(
                player,
                tableEntity.xCoord, tableEntity.yCoord, tableEntity.zCoord, aspect.components[0],
                aspect.components[1],
                tableEntity.bonusAspects.getAmount(aspect.components[0]) > 0,
                tableEntity.bonusAspects.getAmount(aspect.components[1]) > 0,
                true
            )
        )
    }

    private fun bonusAspectAmountProvider(tableEntity: TileResearchTable) = { aspect: Aspect ->
        tableEntity.bonusAspects.getAmount(aspect)
    }

    private fun aspectAmountProvider(player: EntityPlayer) = { aspect: Aspect ->
        discoveredAspectsBy(player).getAmount(aspect).toFloat()
    }

    private fun discoveredAspectsBy(player: EntityPlayer) =
        Thaumcraft.proxy.getPlayerKnowledge().getAspectsDiscovered(player.commandSenderName)


    fun createContainer(
        player: EntityPlayer,
        world: World, x: Int, y: Int, z: Int
    ) =
        createContainer(
            player.inventory,
            world.getTableTileEntity(x, y, z),
        )

    private fun World.getTableTileEntity(x: Int, y: Int, z: Int) =
        getTileEntity(x, y, z) as TileResearchTable


    private fun createContainer(
        playerInventory: InventoryPlayer,
        researchTableTileEntity: TileResearchTable
    ) =
        ResearchTableContainerFactory.create(
            playerInventory,
            researchTableTileEntity,
            scribeToolsSlotOrigin = ResearchTableInventoryTexture.Slots.scribeToolsOrigin,
            notesSlotOrigin = ResearchTableInventoryTexture.Slots.notesOrigin,
            inventorySlotOrigin = ResearchTableInventoryTexture.inventoryOrigin,
        )

}