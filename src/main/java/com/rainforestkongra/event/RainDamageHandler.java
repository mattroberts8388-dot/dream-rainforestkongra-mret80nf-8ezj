package com.rainforestkongra.event;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;

import com.rainforestkongra.RainforestKongraMod;
import com.rainforestkongra.item.KongraArmorMaterial;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Tracks how long each player has been standing in the rain.
 * After a grace period, players start taking damage unless they wear
 * the full KONGRA armor set, which protects them from the acidic rainforest rain.
 */
public class RainDamageHandler {
    // Ticks a player has been continuously in the rain
    private final Map<UUID, Integer> rainExposure = new HashMap<>();

    // Grace period (in ticks) before rain begins to hurt. 20 ticks = 1 second.
    private static final int GRACE_PERIOD = 200; // 10 seconds
    // How often damage is applied after grace period (ticks)
    private static final int DAMAGE_INTERVAL = 40; // every 2 seconds
    private static final float DAMAGE_AMOUNT = 2.0f; // 1 heart

    public void onServerTick(MinecraftServer server) {
        for (ServerWorld world : server.getWorlds()) {
            for (PlayerEntity player : world.getPlayers()) {
                if (player.isCreative() || player.isSpectator()) {
                    rainExposure.remove(player.getUuid());
                    continue;
                }

                if (isPlayerInRain(world, player)) {
                    int exposure = rainExposure.getOrDefault(player.getUuid(), 0) + 1;

                    if (hasFullKongraArmor(player)) {
                        // Fully protected — reset exposure so armor keeps them safe
                        rainExposure.put(player.getUuid(), 0);
                        continue;
                    }

                    rainExposure.put(player.getUuid(), exposure);

                    if (exposure > GRACE_PERIOD && (exposure - GRACE_PERIOD) % DAMAGE_INTERVAL == 0) {
                        DamageSource source = world.getDamageSources().magic();
                        player.damage(source, DAMAGE_AMOUNT);
                    }
                } else {
                    // Not in rain — decay exposure quickly
                    int exposure = rainExposure.getOrDefault(player.getUuid(), 0);
                    if (exposure > 0) {
                        rainExposure.put(player.getUuid(), Math.max(0, exposure - 4));
                    }
                }
            }
        }
    }

    private boolean isPlayerInRain(ServerWorld world, PlayerEntity player) {
        if (!world.isRaining()) {
            return false;
        }
        BlockPos pos = player.getBlockPos();
        // Player must be exposed to sky and in a biome where it rains (not snowing)
        if (!world.isSkyVisible(pos)) {
            return false;
        }
        int topY = world.getTopY(Heightmap.Type.MOTION_BLOCKING, pos.getX(), pos.getZ());
        if (topY > pos.getY()) {
            return false;
        }
        return world.getBiome(pos).value().getPrecipitation(pos) == net.minecraft.world.biome.Biome.Precipitation.RAIN;
    }

    private boolean hasFullKongraArmor(PlayerEntity player) {
        ItemStack head = player.getEquippedStack(net.minecraft.entity.EquipmentSlot.HEAD);
        ItemStack chest = player.getEquippedStack(net.minecraft.entity.EquipmentSlot.CHEST);
        ItemStack legs = player.getEquippedStack(net.minecraft.entity.EquipmentSlot.LEGS);
        ItemStack feet = player.getEquippedStack(net.minecraft.entity.EquipmentSlot.FEET);

        return isKongra(head) && isKongra(chest) && isKongra(legs) && isKongra(feet);
    }

    private boolean isKongra(ItemStack stack) {
        if (stack.getItem() instanceof ArmorItem armorItem) {
            return armorItem.getMaterial() instanceof KongraArmorMaterial;
        }
        return false;
    }
}