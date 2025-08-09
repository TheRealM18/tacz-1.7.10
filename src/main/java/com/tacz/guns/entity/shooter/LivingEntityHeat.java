package com.tacz.guns.entity.shooter;

import com.tacz.guns.api.item.gun.AbstractGunItem;
import net.minecraft.world.entity.LivingEntity;

public class LivingEntityHeat {

    private final LivingEntity shooter;
    private final ShooterDataHolder data;

    public LivingEntityHeat(LivingEntity shooter, ShooterDataHolder dataHolder) {
        this.shooter = shooter;
        this.data = dataHolder;
    }

    public void tickHeat() {
        net.minecraft.world.item.ItemStack gunStack = shooter.getMainHandItem();
        if (gunStack.getItem() instanceof AbstractGunItem gunItem) {
            gunItem.tickHeat(data, gunStack, shooter);
        }
    }
}
