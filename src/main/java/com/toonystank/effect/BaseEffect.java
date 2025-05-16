package com.toonystank.effect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@SuppressWarnings("unused")
public abstract class BaseEffect {

    @Getter
    private static final Map<Player, RunningResult> activeEffects = new ConcurrentHashMap<>();

    private final Effect effect;

    public BaseEffect(Effect effect) {
        this.effect = effect;
    }

    public abstract void applyEffect(Player player, int tickCount);

    public abstract boolean hasToStop(Player player, boolean firstCall);

    /**
     * Called when the teleportation is complete, allowing effects to perform post-teleport actions.
     *
     * @param player The player who was teleported.
     */
    public abstract void onTeleportComplete(Player player);

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class Effect {
        private String name;
        private boolean enabled;
        private List<String> description;
        @Nullable
        private List<String> commandsToRun;
        private String permissionNode;
        @NotNull
        private Class<? extends BaseEffect> effectClass;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class RunningResult {
        private boolean isCancelled;
        private boolean isFinished;
    }

    public static boolean isCancelled(Player player) {
        RunningResult runningResult = updateRunningResult(player, false, false);
        return runningResult.isCancelled() || runningResult.isFinished();
    }

    public static RunningResult createRunningResult(Player player, boolean isCancelled, boolean isFinished) {
        RunningResult result = new RunningResult(isCancelled, isFinished);
        activeEffects.put(player, result);
        return result;
    }

    public static RunningResult updateRunningResult(Player player, boolean isCancelled, boolean isFinished) {
        return activeEffects.computeIfAbsent(player, p -> new RunningResult(isCancelled, isFinished));
    }
}