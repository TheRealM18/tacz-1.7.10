package com.tacz.guns.event;

import com.tacz.guns.api.item.IGun;
import com.tacz.guns.config.common.GunConfig;
import com.tacz.guns.item.ModernKineticGunScriptAPI;
import com.tacz.guns.resource.pojo.data.gun.FeedType;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerRespawnEvent {
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        // 重生自动换弹
        if (!GunConfig.AUTO_RELOAD_WHEN_RESPAWN.get()) return;

        net.minecraft.world.entity.player.Player player = event.getEntity();
        player.getInventory().items.forEach(itemStack -> {
            if (!(itemStack.getItem() instanceof IGun)) return;

            ModernKineticGunScriptAPI api = new ModernKineticGunScriptAPI();
            api.setItemStack(itemStack);
            api.setShooter(player);


            // 针对背包直读特殊处理
            boolean useInventoryAmmo = api.getGunIndex().getGunData().getReloadData().getType() == FeedType.INVENTORY;
            // 如果为背包直读则不进行换弹
            if (useInventoryAmmo) {
                return;
            }

            // 针对燃料类型特殊处理
            boolean isFuel = api.getGunIndex().getGunData().getReloadData().getType() == FeedType.FUEL;
            int needAmmoCount = api.getNeededAmmoAmount();

            if (player.isCreative()) {
                api.putAmmoInMagazine(needAmmoCount);
            } else {
                int consumedAmount = api.consumeAmmoFromPlayer(isFuel ? 1 : needAmmoCount);
                api.putAmmoInMagazine(isFuel ? (needAmmoCount * consumedAmount) : consumedAmount);
            }
        });
    }
}
