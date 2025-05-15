package com.toonystank.effect;

import lombok.Getter;
import org.bukkit.entity.Player;

/**
 * Abstract base class for time-based effects in the RequisiteRTP plugin.
 * Extends {@link BaseEffect} to add support for effects that trigger at specific time intervals
 * (e.g., every tick, second, or minute). Developers should extend this class to create effects
 * that require periodic execution, such as countdowns or recurring animations.
 *
 * @see BaseEffect
 * @see EffectManager
 */
@Getter
public abstract class BaseTimedEffect extends BaseEffect {

    /**
     * The time unit for the effect's execution interval (ticks, seconds, or minutes).
     */
    private final Type timeType;

    /**
     * The interval (in ticks) at which the effect is applied.
     * For example, 20 ticks for seconds, 1200 ticks for minutes.
     */
    private final int interval;

    /**
     * Constructs a new time-based effect with the specified configuration and time type.
     *
     * @param effect The effect configuration defining metadata like name and permissions.
     * @param type   The time unit for the effect's execution interval (e.g., {@link Type#SECONDS}).
     */
    public BaseTimedEffect(Effect effect, Type type) {
        super(effect);
        this.timeType = type;
        this.interval = getInterval(type);
    }

    /**
     * Calculates the tick interval based on the specified time type.
     *
     * @param type The time unit (ticks, seconds, or minutes).
     * @return The number of ticks for the interval (e.g., 20 for seconds).
     */
    private static int getInterval(Type type) {
        switch (type) {
            case SECONDS:
                return 20; // 20 ticks = 1 second
            case MINUTES:
                return 1200; // 1200 ticks = 1 minute
            case TICKS:
            default:
                return 2; // Default to 2 ticks (every 0.1 seconds)
        }
    }

    /**
     * Applies the time-based effect to a player at the specified tick count.
     * This method is called when the tick count is divisible by the effect's interval
     * and the effect has not been stopped.
     *
     * @param player    The player to apply the effect to.
     * @param tickCount The current tick count, used for timing the effect.
     */
    public abstract void applyTimedEffect(Player player, int tickCount);

    /**
     * Applies the effect to a player, checking if the current tick count matches the interval.
     * Only calls {@link #applyTimedEffect(Player, int)} if the effect is not stopped and the
     * tick count is divisible by the interval.
     *
     * @param player    The player to apply the effect to.
     * @param tickCount The current tick count.
     */
    @Override
    public void applyEffect(Player player, int tickCount) {
        if (!hasToStop(player, false) && tickCount % interval == 0) {
            applyTimedEffect(player, tickCount);
        }
    }

    /**
     * Enum defining the possible time units for the effect's execution interval.
     */
    public enum Type {
        /**
         * Effect triggers every 2 ticks (0.1 seconds).
         */
        TICKS,

        /**
         * Effect triggers every second (20 ticks).
         */
        SECONDS,

        /**
         * Effect triggers every minute (1200 ticks).
         */
        MINUTES
    }
}