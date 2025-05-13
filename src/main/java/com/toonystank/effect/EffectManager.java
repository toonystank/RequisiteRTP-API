package com.toonystank.effect;

import java.util.List;

public interface EffectManager {


    BaseEffect.Effect registerEffect(BaseEffect.Effect effect);
    BaseEffect.Effect registerEffect(String name,
                                     boolean enabled,
                                     List<String> description,
                                     List<String> commandsToRun,
                                     String permissionNode,
                                     Class<? extends BaseEffect> effectClass);


    BaseEffect.Effect getEffect(String name);
    List<BaseEffect.Effect> getEffects();
}
