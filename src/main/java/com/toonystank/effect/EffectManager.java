package com.toonystank.effect;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletionStage;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.bukkit.entity.Player;

/**
 * Interface for managing effects in the RequisiteRTP plugin.
 * Provides methods to register, retrieve, and apply effects that enhance the random teleportation (RTP) experience.
 * Effects are visual or gameplay modifications (e.g., particle animations, titles) applied to players during RTP.
 * Developers can use this interface to register custom effects by extending {@link BaseEffect} or {@link BaseTimedEffect}
 * and integrate them with the RTP system.
 *
 * <p>Example usage:
 * <pre>
 * EffectManager manager = EffectManager.getInstance();
 * BaseEffect.Effect effect = manager.registerEffect(
 *     "GlowEffect",
 *     true,
 *     Arrays.asList("Applies a glowing effect during RTP"),
 *     null,
 *     "effect.glow", // This will be automatically checked as RequisiteRTP.effect.glow
 *     GlowEffect.class
 * );
 * </pre>
 *
 * @see BaseEffect
 * @see BaseTimedEffect
 */
@SuppressWarnings("unused")
public interface EffectManager {

    /**
     * Retrieves the singleton instance of the EffectManager.
     * This method provides access to the effect management system for registering and applying effects.
     *
     * @return The {@link EffectManager} instance.
     * @throws IllegalStateException If the RequisiteRTP plugin is not initialized or the manager is not available.
     */
    static EffectManager getInstance() {
        throw ThrowError();
    }

    /**
     * Retrieves a string value from the configuration file.
     * This method is used to access configuration settings related to effects.
     *
     * @param path The path to the configuration setting.
     * @param defaultValue The default value to return if the setting is not found.
     * @return The string value from the configuration or the default value if not found.
     * @throws IOException If an error occurs while accessing the configuration file.
     */
    static String getStringValue(String path, String defaultValue) throws IOException {
        throw ThrowError();
    }
    static boolean getBooleanValue(String path, boolean defaultValue) throws IOException {
        throw ThrowError();
    }
    static int getIntValue(String path, int defaultValue) throws IOException {
        throw ThrowError();
    }
    static List<String> getStringListValue(String path, List<String> defaultValue) throws IOException {
        throw ThrowError();
    }

    /**
     * Retrieves an effect instance based on the provided {@link BaseEffect.Effect} object.
     * This method is used to retrieve the loaded effect class instance.
     *
     * @param effect The effect configuration to retrieve the instance for.
     * @return The {@link BaseEffect} instance associated with the provided effect configuration.
     * @throws IllegalStateException If the RequisiteRTP plugin is not initialized or the manager is not available.
     */
    static <T extends BaseEffect> T getEffectInstance(BaseEffect.Effect effect) {
        throw ThrowError();
    }

    static IllegalArgumentException ThrowError(){
        throw new IllegalStateException("EffectManager instance not initialized. Ensure RequisiteRTP plugin is enabled.");
    }

    /**
     * Registers a custom effect using a pre-configured {@link BaseEffect.Effect} object.
     * The effect is added to the manager's registry and can be applied to players during RTP if enabled.
     *
     * @param effect The effect configuration to register.
     * @return The registered {@link BaseEffect.Effect} object.
     * @throws IOException If an error occurs while accessing or saving the effect's configuration.
     * @throws IllegalArgumentException If the effect configuration is invalid (e.g., null name or effect class).
     */
    BaseEffect.Effect registerEffect(BaseEffect.Effect effect) throws IOException;

    /**
     * Registers a custom effect by specifying its details directly.
     * Creates a new {@link BaseEffect.Effect} object with the provided parameters and registers it.
     * The effect can be applied to players during RTP if enabled.
     *
     * @param name           The unique name of the effect (e.g., "SpiralEffect").
     * @param enabled        Whether the effect is enabled and can be applied.
     * @param description    A list of strings describing the effect, used for display purposes.
     * @param commandsToRun  An optional list of commands to execute when the effect is applied (nullable).
     * @param permissionNode The permission node required to use the effect (e.g., "effect.spiral").
     *                       Note that RequisiteRTP will automatically prepend "RequisiteRTP." to this node, so
     *                       a permission of effect.glow will be checked as RequisiteRTP.effect.glow.
     * @param effectClass    The class implementing the effect's logic, extending {@link BaseEffect}.
     * @return The registered {@link BaseEffect.Effect} object.
     * @throws IOException If an error occurs while accessing or saving the effect's configuration.
     * @throws IllegalArgumentException If any parameter is invalid (e.g., null name or effect class).
     */
    BaseEffect.Effect registerEffect(String name,
                                     boolean enabled,
                                     List<String> description,
                                     List<String> commandsToRun,
                                     String permissionNode,
                                     Class<? extends BaseEffect> effectClass) throws IOException;

    /**
     * Retrieves a registered effect by its name.
     *
     * @param name The name of the effect to retrieve.
     * @return The {@link BaseEffect.Effect} object if found, or null if no effect with the given name exists.
     */
    BaseEffect.Effect getEffect(String name);

    /**
     * Retrieves a list of all registered effects.
     *
     * @return A list of all {@link BaseEffect.Effect} objects managed by this manager.
     */
    List<BaseEffect.Effect> getEffects();

    /**
     * Retrieves a list of all enabled effects.
     *
     * @return A list of enabled {@link BaseEffect.Effect} objects.
     */
    List<BaseEffect.Effect> getEnabledEffects();

    /**
     * Unregisters an effect by its name, removing it from the manager's registry.
     *
     * @param name The name of the effect to unregister.
     * @return True if the effect was successfully unregistered, false if it was not found.
     * @throws IOException If an error occurs while updating the configuration.
     */
    boolean unregisterEffect(String name) throws IOException;

    /**
     * Applies suitable enabled effects to a player during random teleportation.
     * Effects are applied asynchronously based on their configuration and may continue until the
     * teleportation completes or is cancelled (e.g., due to player movement).
     *
     * @param player The player to apply effects to.
     * @return A {@link CompletionStage} indicating whether the effect application was successful
     *         (true if effects were applied successfully, false if cancelled or failed).
     */
    CompletionStage<Boolean> runSuitableEffect(Player player);


}