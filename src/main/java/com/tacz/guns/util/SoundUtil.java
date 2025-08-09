package com.tacz.guns.util;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SoundUtil {
    
    /**
     * Plays a sound at the specified entity's position
     * @param world The world to play the sound in
     * @param entity The entity to play the sound at
     * @param soundName The name of the sound to play (e.g., "tacz:gun.shoot")
     * @param volume The volume of the sound (0.0 - 1.0)
     * @param pitch The pitch of the sound (0.5 - 2.0)
     */
    public static void playSoundAtEntity(World world, Entity entity, String soundName, float volume, float pitch) {
        if (entity == null || world == null) return;
        
        // Only play the sound on the client side
        if (world.isRemote) {
            world.playSound(
                entity.posX, 
                entity.posY, 
                entity.posZ, 
                soundName, 
                volume, 
                pitch, 
                false
            );
        }
    }
    
    /**
     * Plays a sound at the specified position
     * @param world The world to play the sound in
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @param soundName The name of the sound to play (e.g., "tacz:gun.shoot")
     * @param volume The volume of the sound (0.0 - 1.0)
     * @param pitch The pitch of the sound (0.5 - 2.0)
     */
    public static void playSoundAtPosition(World world, double x, double y, double z, String soundName, float volume, float pitch) {
        if (world == null) return;
        
        // Only play the sound on the client side
        if (world.isRemote) {
            world.playSound(x, y, z, soundName, volume, pitch, false);
        }
    }
    
    /**
     * Plays a sound for the local player only
     * @param soundName The name of the sound to play (e.g., "tacz:gun.shoot")
     * @param volume The volume of the sound (0.0 - 1.0)
     * @param pitch The pitch of the sound (0.5 - 2.0)
     */
    public static void playSoundForPlayer(String soundName, float volume, float pitch) {
        // Only play on the client side
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            Minecraft.getMinecraft().getSoundHandler().playSound(
                PositionedSoundRecord.func_147673_a(new ResourceLocation(soundName), volume, pitch)
            );
        }
    }
    
    /**
     * Plays a sound for the local player only at a specific position
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @param soundName The name of the sound to play (e.g., "tacz:gun.shoot")
     * @param volume The volume of the sound (0.0 - 1.0)
     * @param pitch The pitch of the sound (0.5 - 2.0)
     */
    public static void playPositionedSoundForPlayer(double x, double y, double z, String soundName, float volume, float pitch) {
        // Only play on the client side
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            Minecraft.getMinecraft().getSoundHandler().playSound(
                PositionedSoundRecord.func_147674_a(new ResourceLocation(soundName), volume, pitch, (float)x, (float)y, (float)z)
            );
        }
    }
}
