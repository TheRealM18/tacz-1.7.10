package com.tacz.guns.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        // Server-side pre-initialization
    }
    
    public void init(FMLInitializationEvent event) {
        // Server-side initialization
    }
    
    public void postInit(FMLPostInitializationEvent event) {
        // Server-side post-initialization
    }
    
    public void registerClientOnlyEvents() {
        // Overridden in client proxy
    }
    
    public void registerKeyBindings() {
        // Overridden in client proxy
    }
    
    public void registerParticleEffects() {
        // Overridden in client proxy
    }
    
    public void registerRenderers() {
        // Overridden in client proxy
    }
    
    public void registerRenderersLate() {
        // Overridden in client proxy
    }
}
