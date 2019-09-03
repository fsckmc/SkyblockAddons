package codes.biscuit.skyblockaddons.mixins;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.utils.Feature;
import codes.biscuit.skyblockaddons.utils.Message;
import codes.biscuit.skyblockaddons.utils.ItemRarity;
import main.java.codes.biscuit.skyblockaddons.utils.Rarity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {

    private Item lastItem = null;
    private long lastDrop = System.currentTimeMillis();


    @Inject(method = "dropOneItem", at = @At(value = "HEAD"), cancellable = true)
    private void dropOneItemConfirmation(boolean dropAll, CallbackInfoReturnable<EntityItem> cir) {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getConfigValues().isEnabled(Feature.DROP_CONFIRMATION) && (main.getUtils().isOnSkyblock() ||
                main.getConfigValues().isDisabled(Feature.DISABLE_DOUBLE_DROP_AUTOMATICALLY))) {
            ItemStack heldItemStack = Minecraft.getMinecraft().thePlayer.getHeldItem();
            if (heldItemStack != null) {
                Item heldItem = heldItemStack.getItem();
                Rarity itemRarity = new ItemRarity(heldItemStack).get();
                // TODO: Figure out how to represent this multi option field in Biscuits UI.
                if(!(itemRarity == Rarity.COMMON)){
                    // UGLY but I'm copying the block because I'd like to include a custom message, about dropping item of $s rarity.
                    SkyblockAddons.getInstance().getUtils().sendMessage(main.getConfigValues().getColor(Feature.DROP_CONFIRMATION).getChatFormatting() +
                            Message.MESSAGE_DROP_CONFIRMATION.getMessage());
                    lastItem = heldItem;
                    lastDrop = System.currentTimeMillis();
                    cir.setReturnValue(null);
                }
                if (lastItem != null && lastItem == heldItem && System.currentTimeMillis() - lastDrop < 3000) {
                    lastDrop = System.currentTimeMillis();
                } else {
                    SkyblockAddons.getInstance().getUtils().sendMessage(main.getConfigValues().getColor(Feature.DROP_CONFIRMATION).getChatFormatting() +
                            Message.MESSAGE_DROP_CONFIRMATION.getMessage());
                    lastItem = heldItem;
                    lastDrop = System.currentTimeMillis();
                    cir.setReturnValue(null);
                }
            }
        }
    }
}
