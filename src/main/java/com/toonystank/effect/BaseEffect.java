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

/**
 * Abstract base class for creating custom effects in the RequisiteRTP plugin.
 * Effects are visual or gameplay modifications applied to players during random teleportation (RTP).
 * Developers should extend this class to implement custom effects, such as particle animations or sound effects.
 *
 * @see EffectManager
 */
@Getter
@SuppressWarnings("unused")
public abstract class BaseEffect {

    /**
     * Thread-safe map tracking active effects for each player.
     * Stores the running state (cancelled or finished) of an effect for a player.
     */
    @Getter
    private static final Map<Player, RunningResult> activeEffects = new ConcurrentHashMap<>();

    /**
     * The configuration for this主播 configuration associated with this effect instance.
     */
    private final Effect effect;

    /**
     * Constructs a new BaseEffect with the specified configuration.
     *
     * @param effect The effect configuration defining metadata like name and permissions.
     */
    public BaseEffect(Effect effect) {
        this.effect = effect;
    }

    /**
     * Applies the effect to a player at the specified tick count.
     * Implement this method to define the effect's behavior, such as spawning particles or playing sounds.
     *
     * @param player    The player to apply the effect to.
     * @param tickCount The current tick count, used for animating or timing the effect.
     */
    public abstract void applyEffect(Player player, int tickCount);

    /**
     * Determines whether the effect should stop for a player.
     * Implement this method to define conditions for stopping the effect, such as player movement or lack of permissions.
     *
     * @param player    The player to check.
     * @param firstCall True if this is the first call in the effect's lifecycle, false otherwise.
     * @return True if the effect should stop, false otherwise.
     */
    public abstract boolean hasToStop(Player player, boolean firstCall);

    /**
     * Configuration class for an effect, defining its properties.
     */
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class Effect {
        /**
         * The unique name of the effect (e.g., "SpiralEffect").
         */
        private String name;

        /**
         * Whether the effect is enabled and can be applied.
         */
        private boolean enabled;

        /**
         * A list of strings describing the effect, used for display purposes.
         */
        private List<String> description;

        /**
         * Optional list of commands to execute when the effect is applied.
         */
        @Nullable
        private List<String> commandsToRun;

        /**
         * The permission node required to use the effect (e.g., "requisitertp.effect.spiral").
         */
        private String permissionNode;

        /**
         * The class implementing the effect's logic, extending BaseEffect.
         */
        @NotNull
        private Class<? extends BaseEffect> effectClass;
    }

    /**
     * Represents the running state of an effect for a player.
     */
    @AllArgsConstructor
    @Getter
    @Setter
    public static class RunningResult {
        /**
         * Whether the effect has been cancelled (e.g., due to player action).
         */
        private boolean isCancelled;

        /**
         * Whether the effect has completed its execution.
         */
        private boolean isFinished;
    }

    /**
     * Checks if an effect is cancelled or finished for a player.
     *
     * @param player The player to check.
     * @return True if the effect is cancelled or finished, false otherwise.
     */
    public static boolean isCancelled(Player player) {
        RunningResult runningResult = updateRunningResult(player, false, false);
        return runningResult.isCancelled() || runningResult.isFinished();
    }

    /**
     * Creates a new running result for a player and stores it in the active effects map.
     *
     * @param player      The player associated with the effect.
     * @param isCancelled Whether the effect is cancelled.
     * @param isFinished  Whether the effect is finished.
     * @return The created RunningResult.
     */
    public static RunningResult createRunningResult(Player player, boolean isCancelled, boolean isFinished) {
        RunningResult result = new RunningResult(isCancelled, isFinished);
        activeEffects.put(player, result);
        return result;
    }

    /**
     * Retrieves or creates a running result for a player, updating its state.
     *
     * @param player      The player associated with the effect.
     * @param isCancelled Whether the effect is cancelled.
     * @param isFinished  Whether the effect is finished.
     * @return The updated or new RunningResult.
     */
    public static RunningResult updateRunningResult(Player player, boolean isCancelled, boolean isFinished) {
        return activeEffects.computeIfAbsent(player, p -> new RunningResult(isCancelled, isFinished));
    }
}