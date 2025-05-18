package com.toonystank.hooks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Abstract base class for creating hooks in the RequisiteRTP plugin.
 * Hooks allow integration with external plugins to control whether random teleportation (RTP)
 * is allowed at specific locations. Developers should extend this class to implement custom
 * hook logic, such as checking permissions or region restrictions in other plugins.
 *
 * <p>Example implementation:
 * <pre>
 * public class MyPluginHook extends Hook {
 *     public MyPluginHook(HookData hookData) {
 *         super(hookData);
 *     }
 *
 *     @Override
 *     public boolean isAllowed(Player player, Location location) {
 *         // Custom logic to check if RTP is allowed
 *         return true;
 *     }
 * }
 * </pre>
 *
 * @see HooksManager
 */
@Setter
@Getter
@SuppressWarnings("unused")
public abstract class Hook {

    /**
     * The configuration data for this hook, including its name, enabled status, and bypass permission.
     */
    private HookData hookData;

    /**
     * Constructs a new hook with the specified configuration.
     * Subclasses should call this constructor and initialize any plugin-specific logic.
     *
     * @param hookData The hook configuration, including name, enabled status, and bypass permission.
     */
    public Hook(HookData hookData) {
        this.hookData = hookData;
    }

    /**
     * Determines whether random teleportation is allowed for a player at the specified location.
     * Subclasses must implement this method to define the hook's logic, such as checking
     * if the location is within a protected region or if the player has appropriate permissions.
     *
     * @param player   The player attempting to teleport.
     * @param location The target location for the teleportation.
     * @return True if teleportation is allowed, false otherwise.
     */
    public abstract boolean isAllowed(Player player, Location location);

    /**
     * Configuration class for a hook, defining its properties.
     */
    @AllArgsConstructor
    @Getter
    @Setter
    public static class HookData {
        /**
         * The unique name of the hook (e.g., "GriefPrevention").
         */
        private String name;

        /**
         * Whether the hook is enabled and should be used during RTP checks.
         */
        private boolean enabled;

        /**
         * The permission node that allows players to bypass the hook's restrictions.
         * This should be a high-level permission node, as all permissions will automatically
         * be prefixed with "RequisiteRTP." (e.g., "bypass.griefprevention").
         */
        private String bypassPermission;
    }
}