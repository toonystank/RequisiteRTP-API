package com.toonystank.effect;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public abstract class BaseTimedEffect extends BaseEffect {

    private final Type timeType;
    private final int interval;

    public BaseTimedEffect(Effect effect, Type type) {
        super(effect);
        this.timeType = type;
        this.interval = getInterval(type);
    }

    private static int getInterval(Type type) {
        return switch (type) {
            case SECONDS -> 20;
            case MINUTES -> 1200;
            default -> 2;
        };
    }

    public abstract void applyTimedEffect(Player player, int tickCount);

    @Override
    public void applyEffect(Player player, int tickCount) {
        if (!hasToStop(player, false) && tickCount % interval == 0) {
            applyTimedEffect(player, tickCount);
        }
    }

    public enum Type {
        TICKS,
        SECONDS,
        MINUTES
    }
}
