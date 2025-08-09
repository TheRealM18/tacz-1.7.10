package com.tacz.guns.entity;

import com.tacz.guns.util.SoundUtil;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBullet1710 extends EntityArrow implements IEntityAdditionalSpawnData {
    
    private float damage = 5.0F;
    private int ticksInAir = 0;
    private boolean canBePickedUp = false;
    private String bulletType = "default";
    
    // Required constructor for FML
    public EntityBullet1710(World world) {
        super(world);
        this.setSize(0.1F, 0.1F);
    }
    
    public EntityBullet1710(World world, EntityLivingBase shooter, float damage, String bulletType) {
        super(world, shooter, 1.0F);
        this.damage = damage;
        this.bulletType = bulletType;
        this.canBePickedUp = false;
        this.setSize(0.1F, 0.1F);
        
        // Play shooting sound
        if (!world.isRemote) {
            String soundName = getShootSound();
            float volume = 0.5F;
            float pitch = 0.8F + (world.rand.nextFloat() * 0.4F);
            SoundUtil.playSoundAtEntity(world, shooter, soundName, volume, pitch);
        }
    }
    
    /**
     * Gets the appropriate shoot sound based on bullet type
     */
    private String getShootSound() {
        // You can customize this based on bullet type if needed
        return "tacz:gun.shoot";
    }
    
    /**
     * Gets the appropriate impact sound based on what was hit
     */
    private String getImpactSound(MovingObjectPosition mop) {
        // Default impact sound
        return "tacz:target_block_hit";
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        
        // Increment air time
        if (!this.onGround && !this.isInWater()) {
            ticksInAir++;
        }
        
        // Despawn after 60 seconds in air
        if (ticksInAir > 1200) {
            this.setDead();
        }
        
        // Visual effects
        if (this.worldObj.isRemote) {
            spawnParticles();
        }
    }
    
    @Override
    protected void onHit(MovingObjectPosition mop) {
        onImpact(mop);
    }
    
    @Override
    protected void onImpact(MovingObjectPosition mop) {
        if (mop.entityHit != null) {
            // Handle entity hit
            float damageDealt = this.damage;
            
            // Play impact sound
            String soundName = this.getImpactSound(mop);
            float volume = 0.5F;
            float pitch = 0.8F + (this.worldObj.rand.nextFloat() * 0.4F);
            SoundUtil.playSoundAtPosition(this.worldObj, this.posX, this.posY, this.posZ, soundName, volume, pitch);
            
            // Apply damage to the entity
            if (mop.entityHit.attackEntityFrom(DamageSource.causeArrowDamage(this, this.shootingEntity), damageDealt)) {
                // On successful hit
                
                // Apply knockback
                if (mop.entityHit instanceof EntityLivingBase) {
                    EntityLivingBase entityHit = (EntityLivingBase) mop.entityHit;
                    float knockbackStrength = 0.5F; // Default knockback
                    
                    // Apply knockback
                    double knockbackX = -Math.sin((this.rotationYaw * Math.PI) / 180.0F) * knockbackStrength * 0.5;
                    double knockbackZ = Math.cos((this.rotationYaw * Math.PI) / 180.0F) * knockbackStrength * 0.5;
                    entityHit.addVelocity(knockbackX, 0.1D, knockbackZ);
                    
                    // Apply fire damage for incendiary bullets
                    if ("incendiary".equals(this.bulletType)) {
                        entityHit.setFire(5);
                    }
                }
            }
        } else {
            // Handle block hit
            // Create impact effect
            this.worldObj.spawnParticle("explode", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            
            // Create impact sound
            String soundName = this.getImpactSound(mop);
            float volume = 0.5F;
            float pitch = 0.8F + (this.worldObj.rand.nextFloat() * 0.4F);
            SoundUtil.playSoundAtPosition(this.worldObj, this.posX, this.posY, this.posZ, soundName, volume, pitch);
        }
        
        // Set dead after impact
        this.setDead();
    }
    
    protected void spawnParticles() {
        if (this.worldObj.isRemote) {
            for (int i = 0; i < 2; i++) {
                this.worldObj.spawnParticle("smoke", 
                    this.posX + this.rand.nextGaussian() * 0.02D,
                    this.posY + this.rand.nextGaussian() * 0.02D,
                    this.posZ + this.rand.nextGaussian() * 0.02D,
                    0.0D, 0.0D, 0.0D);
            }
        }
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        // Initialize any data watcher objects here if needed
    }
    
    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.damage = nbt.getFloat("damage");
        this.ticksInAir = nbt.getInteger("ticksInAir");
        this.bulletType = nbt.getString("bulletType");
    }
    
    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setFloat("damage", this.damage);
        nbt.setInteger("ticksInAir", this.ticksInAir);
        nbt.setString("bulletType", this.bulletType);
    }
    
    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeFloat(this.damage);
        buffer.writeInt(this.ticksInAir);
        buffer.writeBoolean(this.canBePickedUp);
        
        // Write string length and then the string bytes
        byte[] bytes = this.bulletType.getBytes();
        buffer.writeInt(bytes.length);
        buffer.writeBytes(bytes);
    }
    
    @Override
    public void readSpawnData(ByteBuf buffer) {
        this.damage = buffer.readFloat();
        this.ticksInAir = buffer.readInt();
        this.canBePickedUp = buffer.readBoolean();
        
        // Read string length and then the string bytes
        int length = buffer.readInt();
        byte[] bytes = new byte[length];
        buffer.readBytes(bytes);
        this.bulletType = new String(bytes);
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return false;
    }
    
    @Override
    public boolean canBePushed() {
        return false;
    }
    
    @Override
    protected ItemStack func_145778_a() {
        return null; // Don't drop anything when picked up
    }
    
    public float getDamage() {
        return this.damage;
    }
    
    public void setDamage(float damage) {
        this.damage = damage;
    }
    
    public String getBulletType() {
        return this.bulletType;
    }
}
