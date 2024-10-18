package io.github.a1qs.shulkerifiedvaultstations;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(ShulkerifiedVaultStations.MOD_ID)
public class ShulkerifiedVaultStations {
    public static final String MOD_ID = "shulkerifiedvaultstations";

    public ShulkerifiedVaultStations() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ItemStack addCustomNbtData(ItemStack pStack, BlockEntity pTe) {
        CompoundTag compoundtag = pTe.saveWithFullMetadata();
        BlockItem.setBlockEntityData(pStack, pTe.getType(), compoundtag);

        return pStack;
    }
}
