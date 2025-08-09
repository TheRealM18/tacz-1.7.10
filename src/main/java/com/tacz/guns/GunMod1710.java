package com.tacz.guns;

import com.tacz.guns.entity.EntityBullet1710;
import com.tacz.guns.init.ModBlocks1710;
import com.tacz.guns.init.ModItems1710;
import com.tacz.guns.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
    modid = GunMod1710.MODID,
    name = GunMod1710.NAME,
    version = GunMod1710.VERSION,
    dependencies = "required-after:Forge@[10.13.4.1614,)",
    acceptableRemoteVersions = "*"
)
public class GunMod1710 {
    public static final String MODID = "tacz";
    public static final String NAME = "TACZ Guns";
    public static final String VERSION = "1.0.0";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    
    @SidedProxy(
        clientSide = "com.tacz.guns.proxy.ClientProxy",
        serverSide = "com.tacz.guns.proxy.CommonProxy"
    )
    public static CommonProxy proxy;
    
    public static CreativeTabs tabTACZGuns = new CreativeTabs("tacz_guns") {
        @Override
        public Item getTabIconItem() {
            return Items.iron_sword; // Temporary icon
        }
    };
    
    public static DamageSource bulletDamage = new DamageSource("bullet").setProjectile();
    
    @Mod.Instance(MODID)
    public static GunMod1710 instance;
    
    // Network channel for mod packets
    public static SimpleNetworkWrapper network;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;
        
        // Load configuration
        Config.loadConfig(event.getSuggestedConfigurationFile());
        
        // Initialize mod blocks and items
        ModBlocks1710.init();
        ModItems1710.init();
        
        // Register entities
        registerEntities();
        
        // Initialize network
        network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
        registerPackets();
        
        // Register event handlers
        MinecraftForge.EVENT_BUS.register(this);
        
        // Client-side initialization
        proxy.preInit(event);
        
        LOGGER.info("{} v{} pre-initialized successfully", NAME, VERSION);
    }
    
    private void registerEntities() {
        // Register bullet entity with a unique ID (start from 1, 0 is reserved)
        int entityId = 1;
        EntityRegistry.registerModEntity(EntityBullet1710.class, "bullet", entityId++, this, 64, 1, true);
    }
    
    private void registerPackets() {
        // Register network packets here
        // Example: network.registerMessage(MessageHandler.class, MessageClass.class, 0, Side.CLIENT);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        // Register recipes
        // TODO: Register recipes here
        
        // Register tile entities
        // TODO: Register tile entities here
        
        // Server-side initialization
        proxy.init(event);
        
        // Register client-only events and renderers
        proxy.registerClientOnlyEvents();
        proxy.registerKeyBindings();
        proxy.registerParticleEffects();
        
        LOGGER.info("{} v{} initialized successfully", NAME, VERSION);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        // Post-initialization tasks
        proxy.postInit(event);
        
        // Register renderers that need to be registered after other mods
        proxy.registerRenderersLate();
        
        LOGGER.info("{} v{} post-initialized successfully", NAME, VERSION);
    }
    
    /**
     * Helper method to get the mod instance
     */
    public static GunMod1710 getInstance() {
        return instance;
    }
}
