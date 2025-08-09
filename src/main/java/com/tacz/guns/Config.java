package com.tacz.guns;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

import java.io.File;

public class Config {
    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_CLIENT = "client";
    private static final String CATEGORY_SERVER = "server";

    // General settings
    public static boolean enableDebugLogging = false;
    
    // Client settings
    public static boolean fancyGraphics = true;
    
    // Server settings
    public static int maxAmmoPerGun = 1000;

    public static void loadConfig(File configFile) {
        Configuration cfg = new Configuration(configFile);
        
        try {
            cfg.load();
            
            // General settings
            enableDebugLogging = cfg.getBoolean("enableDebugLogging", CATEGORY_GENERAL, enableDebugLogging, 
                "Enable debug logging");
                
            // Client settings
            fancyGraphics = cfg.getBoolean("fancyGraphics", CATEGORY_CLIENT, fancyGraphics, 
                "Enable fancy graphics");
                
            // Server settings
            maxAmmoPerGun = cfg.getInt("maxAmmoPerGun", CATEGORY_SERVER, maxAmmoPerGun, 1, 10000, 
                "Maximum ammo a gun can hold");
                
        } catch (Exception e) {
            GunMod1710.LOGGER.log(Level.ERROR, "Error loading config file!", e);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }
}
