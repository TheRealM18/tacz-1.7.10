package com.tacz.guns.client.renderer.entity;

import com.tacz.guns.entity.EntityBullet1710;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class RenderBullet1710 extends Render {
    
    private static final ResourceLocation bulletTextures = new ResourceLocation("tacz", "textures/entity/bullet.png");
    
    public RenderBullet1710() {
        this.shadowSize = 0.1F;
    }
    
    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        EntityBullet1710 bullet = (EntityBullet1710) entity;
        
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        
        // Rotate to face the direction of motion
        GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
        
        // Scale down the bullet
        float scale = 0.1F;
        GL11.glScalef(scale, scale, scale);
        
        // Bind the texture
        this.bindEntityTexture(entity);
        
        // Enable blending for transparency
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        // Draw the bullet
        Tessellator tessellator = Tessellator.instance;
        
        // Simple bullet shape - a small rectangle
        GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
        
        // Draw a small rectangle
        tessellator.startDrawingQuads();
        
        // Front face
        tessellator.addVertexWithUV(-0.5, -0.5, 0.0, 0, 0);
        tessellator.addVertexWithUV(0.5, -0.5, 0.0, 1, 0);
        tessellator.addVertexWithUV(0.5, 0.5, 0.0, 1, 1);
        tessellator.addVertexWithUV(-0.5, 0.5, 0.0, 0, 1);
        
        // Back face
        tessellator.addVertexWithUV(-0.5, 0.5, -1.0, 0, 1);
        tessellator.addVertexWithUV(0.5, 0.5, -1.0, 1, 1);
        tessellator.addVertexWithUV(0.5, -0.5, -1.0, 1, 0);
        tessellator.addVertexWithUV(-0.5, -0.5, -1.0, 0, 0);
        
        // Top face
        tessellator.addVertexWithUV(-0.5, 0.5, 0.0, 0, 0);
        tessellator.addVertexWithUV(0.5, 0.5, 0.0, 1, 0);
        tessellator.addVertexWithUV(0.5, 0.5, -1.0, 1, 1);
        tessellator.addVertexWithUV(-0.5, 0.5, -1.0, 0, 1);
        
        // Bottom face
        tessellator.addVertexWithUV(-0.5, -0.5, -1.0, 0, 1);
        tessellator.addVertexWithUV(0.5, -0.5, -1.0, 1, 1);
        tessellator.addVertexWithUV(0.5, -0.5, 0.0, 1, 0);
        tessellator.addVertexWithUV(-0.5, -0.5, 0.0, 0, 0);
        
        // Right face
        tessellator.addVertexWithUV(0.5, -0.5, 0.0, 0, 0);
        tessellator.addVertexWithUV(0.5, -0.5, -1.0, 1, 0);
        tessellator.addVertexWithUV(0.5, 0.5, -1.0, 1, 1);
        tessellator.addVertexWithUV(0.5, 0.5, 0.0, 0, 1);
        
        // Left face
        tessellator.addVertexWithUV(-0.5, 0.5, 0.0, 0, 1);
        tessellator.addVertexWithUV(-0.5, 0.5, -1.0, 1, 1);
        tessellator.addVertexWithUV(-0.5, -0.5, -1.0, 1, 0);
        tessellator.addVertexWithUV(-0.5, -0.5, 0.0, 0, 0);
        
        tessellator.draw();
        
        // Add a glowing effect for certain bullet types
        if (bullet.getBulletType().equals("incendiary") || bullet.getBulletType().equals("explosive")) {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            
            float r = bullet.getBulletType().equals("incendiary") ? 1.0F : 1.0F;
            float g = bullet.getBulletType().equals("incendiary") ? 0.5F : 0.8F;
            float b = bullet.getBulletType().equals("incendiary") ? 0.0F : 0.0F;
            
            GL11.glColor4f(r, g, b, 0.5F);
            
            // Draw a slightly larger, semi-transparent box around the bullet
            GL11.glPushMatrix();
            GL11.glScalef(1.2F, 1.2F, 1.2F);
            
            tessellator.startDrawingQuads();
            // ... (similar drawing code as above, but with the new color)
            tessellator.draw();
            
            GL11.glPopMatrix();
            
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }
        
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return bulletTextures;
    }
}
