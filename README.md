# TACZ Guns - 1.7.10 Edition

This is a backported version of the TACZ Guns mod for Minecraft 1.7.10. This version is designed to be compatible with Java 8 and the older Forge Mod Loader (FML) architecture.

## Features

- Custom guns with unique firing mechanics
- Ammunition system with different bullet types
- Gun attachments and modifications
- Crafting workbenches and gun smithing tables
- Bullet physics and hit detection

## Installation

1. Install Minecraft 1.7.10
2. Install Forge for 1.7.10 (recommended: 10.13.4.1614)
3. Place the mod JAR file in your `mods` folder
4. Launch Minecraft with the Forge profile

## Development Setup

### Requirements
- Java 8 JDK
- Gradle 2.14.1
- ForgeGradle 1.2

### Building

1. Clone the repository
2. Run the following Gradle commands:
   ```
   gradlew setupDecompWorkspace
   gradlew build
   ```
3. Find the built JAR in `build/libs/`

### IDE Setup

#### IntelliJ IDEA
1. Import the project as a Gradle project
2. Set the project SDK to Java 8
3. Run `gradlew genIntellijRuns` to set up run configurations

#### Eclipse
1. Run `gradlew eclipse` to generate Eclipse project files
2. Import the project into Eclipse
3. Set the JRE to Java 8

## Code Structure

- `com.tacz.guns` - Main mod class and core functionality
- `com.tacz.guns.block` - Custom blocks (workbenches, etc.)
- `com.tacz.guns.entity` - Custom entities (bullets, etc.)
- `com.tacz.guns.item` - Custom items (guns, ammo, etc.)
- `com.tacz.guns.proxy` - Client/Server proxy system
- `com.tacz.guns.util` - Utility classes and helpers

## API Usage

### Registering Items/Blocks
```java
// In your mod's initialization:
public static Item myItem = new MyItem();
public static Block myBlock = new MyBlock();

@EventHandler
public void preInit(FMLPreInitializationEvent event) {
    GameRegistry.registerItem(myItem, "my_item");
    GameRegistry.registerBlock(myBlock, "my_block");
}
```

### Event Handling
```java
@SubscribeEvent
public void onLivingHurt(LivingHurtEvent event) {
    // Handle damage events
}
```

## Version History

### 1.0.0
- Initial 1.7.10 backport
- Basic gun and ammo system
- Workbench and gun smithing table blocks
- Bullet physics and hit detection

## License

This mod is licensed under the [MIT License](LICENSE).

## Credits

- Original mod by The Realm 18 Studios
- 1.7.10 backport by [Your Name]
