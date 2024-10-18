package io.github.a1qs.shulkerifiedvaultstations.mixin;

import io.github.a1qs.shulkerifiedvaultstations.ShulkerifiedVaultStations;
import iskallia.vault.block.VaultJewelCuttingStationBlock;
import iskallia.vault.block.base.FacedBlock;
import iskallia.vault.block.entity.VaultJewelCuttingStationTileEntity;
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

@Mixin(value = VaultJewelCuttingStationBlock.class, remap = false)
public abstract class MixinJewelCuttingStationBlock extends FacedBlock {
    public MixinJewelCuttingStationBlock(Properties properties) {
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
            if (tile instanceof VaultJewelCuttingStationTileEntity jewelCuttingStationTileEntity) {
                ItemStack blockStack = new ItemStack(this);
                ShulkerifiedVaultStations.addCustomNbtData(blockStack, jewelCuttingStationTileEntity);
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), blockStack);

                jewelCuttingStationTileEntity.getInventory().clearContent();
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
