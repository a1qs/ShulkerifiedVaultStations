package io.github.a1qs.shulkerifiedvaultstations.mixin;

import io.github.a1qs.shulkerifiedvaultstations.ShulkerifiedVaultStations;
import iskallia.vault.block.InscriptionTableBlock;
import iskallia.vault.block.base.FacedBlock;
import iskallia.vault.block.entity.InscriptionTableTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Collections;
import java.util.List;

@Mixin(value = InscriptionTableBlock.class, remap = false)
public abstract class MixinInscriptionTableBlock extends FacedBlock {
    public MixinInscriptionTableBlock(Properties properties) {
        super(properties);
    }

    /**
     * @author a1qs
     * @reason make block retain its contents on break
     */
    @Overwrite
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity tile = level.getBlockEntity(pos);
            if (tile instanceof InscriptionTableTileEntity inscriptionTableTile) {
                ItemStack blockStack = new ItemStack(this);
                ShulkerifiedVaultStations.addCustomNbtData(blockStack, inscriptionTableTile);
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), blockStack);

                inscriptionTableTile.getInventory().clearContent();
                inscriptionTableTile.getResultContainer().clearContent();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootContext.Builder pBuilder) {
        return Collections.singletonList(new ItemStack(Items.AIR));
    }

}
