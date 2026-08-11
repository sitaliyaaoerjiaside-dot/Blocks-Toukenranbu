package com.Equatorial.toukenranbu.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class JikkoKillCounter extends SavedData {
    private static final String DATA_NAME = "toukenranbu_jikko_kills";
    public static final int COOLDOWN_TICKS = 36000; // 30分钟一刀切

    private int killCount = 0;
    private int kebiishiTier = 0;
    private boolean spawningEnabled = true;
    private long spawnCooldown = COOLDOWN_TICKS;
    private long warningCooldown = 0; // 聚集提示后的延迟生成倒计时
    private boolean pendingSpawn = false; // 是否有待执行的生成

    public int getKillCount() { return killCount; }
    public int getKebiishiTier() { return kebiishiTier; }
    public boolean isSpawningEnabled() { return spawningEnabled; }
    public long getSpawnCooldown() { return spawnCooldown; }
    public long getWarningCooldown() { return warningCooldown; }
    public boolean isPendingSpawn() { return pendingSpawn; }

    public void setSpawningEnabled(boolean enabled) {
        this.spawningEnabled = enabled;
        setDirty();
    }

    public void setSpawnCooldown(long ticks) {
        this.spawnCooldown = Math.max(0, ticks);
        setDirty();
    }

    public void setWarningCooldown(long ticks) {
        this.warningCooldown = Math.max(0, ticks);
        setDirty();
    }

    public void setPendingSpawn(boolean pending) {
        this.pendingSpawn = pending;
        setDirty();
    }

    public void addKill() {
        killCount++;
        int newTier = Math.min(5, killCount / 100); // 每100杀升一级
        if (newTier > kebiishiTier) {
            kebiishiTier = newTier;
            // 升级时重置为30分钟
            resetCooldown();
        }
        setDirty();
    }

    /** 生成完成后或找不到位置时调用，重置为30分钟 */
    public void resetCooldown() {
        this.spawnCooldown = COOLDOWN_TICKS;
        this.warningCooldown = 0;
        this.pendingSpawn = false;
        setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putInt("KillCount", killCount);
        tag.putInt("KebiishiTier", kebiishiTier);
        tag.putBoolean("SpawningEnabled", spawningEnabled);
        tag.putLong("SpawnCooldown", spawnCooldown);
        tag.putLong("WarningCooldown", warningCooldown);
        tag.putBoolean("PendingSpawn", pendingSpawn);
        return tag;
    }

    public static JikkoKillCounter load(CompoundTag tag) {
        JikkoKillCounter counter = new JikkoKillCounter();
        counter.killCount = tag.getInt("KillCount");
        counter.kebiishiTier = tag.getInt("KebiishiTier");

        // 旧存档兼容：没有字段时保持默认值
        counter.spawningEnabled = tag.contains("SpawningEnabled")
                ? tag.getBoolean("SpawningEnabled")
                : true;

        counter.spawnCooldown = tag.contains("SpawnCooldown")
                ? tag.getLong("SpawnCooldown")
                : COOLDOWN_TICKS;

        counter.warningCooldown = tag.contains("WarningCooldown")
                ? tag.getLong("WarningCooldown")
                : 0;

        counter.pendingSpawn = tag.contains("PendingSpawn")
                ? tag.getBoolean("PendingSpawn")
                : false;

        return counter;
    }

    public static JikkoKillCounter get(ServerLevel level) {
        DimensionDataStorage storage = level.getDataStorage();
        return storage.computeIfAbsent(
                JikkoKillCounter::load,
                JikkoKillCounter::new,
                DATA_NAME);
    }
}