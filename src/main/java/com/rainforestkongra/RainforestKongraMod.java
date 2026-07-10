package com.rainforestkongra;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import com.rainforestkongra.entity.KongraEntity;
import com.rainforestkongra.entity.JungleToucanEntity;
import com.rainforestkongra.entity.RainforestJaguarEntity;
import com.rainforestkongra.item.KongraArmorMaterial;
import com.rainforestkongra.event.RainDamageHandler;

public class RainforestKongraMod implements ModInitializer {
    public static final String MOD_ID = "rainforestkongra";

    // Items
    public static final Item RAINFOREST_GEM = new Item(new FabricItemSettings());
    public static final Item KONGRA_SCALE = new Item(new FabricItemSettings());

    public static final KongraArmorMaterial KONGRA_MATERIAL = new KongraArmorMaterial();

    public static final Item KONGRA_HELMET = new ArmorItem(KONGRA_MATERIAL, ArmorItem.Type.HELMET, new FabricItemSettings());
    public static final Item KONGRA_CHESTPLATE = new ArmorItem(KONGRA_MATERIAL, ArmorItem.Type.CHESTPLATE, new FabricItemSettings());
    public static final Item KONGRA_LEGGINGS = new ArmorItem(KONGRA_MATERIAL, ArmorItem.Type.LEGGINGS, new FabricItemSettings());
    public static final Item KONGRA_BOOTS = new ArmorItem(KONGRA_MATERIAL, ArmorItem.Type.BOOTS, new FabricItemSettings());

    // Entities
    public static final EntityType<KongraEntity> KONGRA = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(MOD_ID, "kongra"),
        FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, KongraEntity::new)
            .dimensions(EntityDimensions.fixed(1.4f, 2.9f))
            .build()
    );

    public static final EntityType<JungleToucanEntity> JUNGLE_TOUCAN = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(MOD_ID, "jungle_toucan"),
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, JungleToucanEntity::new)
            .dimensions(EntityDimensions.fixed(0.5f, 0.7f))
            .build()
    );

    public static final EntityType<RainforestJaguarEntity> RAINFOREST_JAGUAR = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(MOD_ID, "rainforest_jaguar"),
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RainforestJaguarEntity::new)
            .dimensions(EntityDimensions.fixed(0.9f, 0.9f))
            .build()
    );

    // Spawn eggs
    public static final Item KONGRA_SPAWN_EGG = new SpawnEggItem(KONGRA, 0x3a2a1a, 0x2fa832, new FabricItemSettings());
    public static final Item JUNGLE_TOUCAN_SPAWN_EGG = new SpawnEggItem(JUNGLE_TOUCAN, 0x111111, 0xffb300, new FabricItemSettings());
    public static final Item RAINFOREST_JAGUAR_SPAWN_EGG = new SpawnEggItem(RAINFOREST_JAGUAR, 0xd9a441, 0x2b2b2b, new FabricItemSettings());

    public static final RegistryKey<ItemGroup> ITEM_GROUP_KEY =
        RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(MOD_ID, "main"));

    @Override
    public void onInitialize() {
        // Register items
        registerItem("rainforest_gem", RAINFOREST_GEM);
        registerItem("kongra_scale", KONGRA_SCALE);
        registerItem("kongra_helmet", KONGRA_HELMET);
        registerItem("kongra_chestplate", KONGRA_CHESTPLATE);
        registerItem("kongra_leggings", KONGRA_LEGGINGS);
        registerItem("kongra_boots", KONGRA_BOOTS);
        registerItem("kongra_spawn_egg", KONGRA_SPAWN_EGG);
        registerItem("jungle_toucan_spawn_egg", JUNGLE_TOUCAN_SPAWN_EGG);
        registerItem("rainforest_jaguar_spawn_egg", RAINFOREST_JAGUAR_SPAWN_EGG);

        // Register entity attributes
        FabricDefaultAttributeRegistry.register(KONGRA, KongraEntity.createKongraAttributes());
        FabricDefaultAttributeRegistry.register(JUNGLE_TOUCAN, JungleToucanEntity.createToucanAttributes());
        FabricDefaultAttributeRegistry.register(RAINFOREST_JAGUAR, RainforestJaguarEntity.createJaguarAttributes());

        // Item group
        ItemGroup group = FabricItemGroup.builder()
            .icon(() -> new ItemStack(KONGRA_CHESTPLATE))
            .displayName(Text.translatable("itemGroup.rainforestkongra.main"))
            .entries((displayContext, entries) -> {
                entries.add(RAINFOREST_GEM);
                entries.add(KONGRA_SCALE);
                entries.add(KONGRA_HELMET);
                entries.add(KONGRA_CHESTPLATE);
                entries.add(KONGRA_LEGGINGS);
                entries.add(KONGRA_BOOTS);
                entries.add(KONGRA_SPAWN_EGG);
                entries.add(JUNGLE_TOUCAN_SPAWN_EGG);
                entries.add(RAINFOREST_JAGUAR_SPAWN_EGG);
            })
            .build();
        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP_KEY, group);

        // Rain damage tick handler
        RainDamageHandler handler = new RainDamageHandler();
        ServerTickEvents.END_SERVER_TICK.register(handler::onServerTick);
    }

    private static void registerItem(String name, Item item) {
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), item);
    }
}