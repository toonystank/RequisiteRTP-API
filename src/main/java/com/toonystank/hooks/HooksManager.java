package com.toonystank.hooks;

/**
 * Interface for managing hooks in the RequisiteRTP plugin.
 * Hooks allow integration with external plugins to control random teleportation (RTP) behavior,
 * such as restricting teleportation to specific locations based on plugin-specific rules
 * (e.g., GriefPrevention claims). Developers can use this interface to register custom hooks
 * by extending the {@link Hook} class and integrating them with the RTP system.
 *
 * <p>Example usage:
 * <pre>
 * HooksManager manager = HooksManager.getInstance();
 * manager.registerHook(
 *     "MyPluginHook",
 *     true,
 *     "requisitertp.bypass.myplugin",
 *     MyPluginHook.class
 * );
 * </pre>
 *
 * @see Hook
 */
@SuppressWarnings("unused")
public interface HooksManager {

    /**
     * Retrieves the singleton instance of the HooksManager.
     * This method provides access to the hook management system for registering and managing hooks.
     *
     * @return The {@link HooksManager} instance.
     * @throws IllegalStateException If the RequisiteRTP plugin is not initialized or the manager is not available.
     */
    static HooksManager getInstance() {
        throw new IllegalStateException("HooksManager instance not initialized. Ensure RequisiteRTP plugin is enabled.");
    }

    /**
     * Registers a custom hook using a pre-configured {@link Hook.HookData} object.
     * The hook is added to the manager's registry and can be used to control RTP behavior.
     *
     * @param hookData The hook configuration, including name, enabled status, and bypass permission.
     * @param hookClass The class implementing the hook's logic, extending {@link Hook}.
     * @return The registered {@link Hook} instance, or null if registration fails (e.g., due to invalid constructor).
     * @throws IllegalArgumentException If the hookData or hookClass is invalid (e.g., null).
     */
    Hook registerHook(Hook.HookData hookData, Class<? extends Hook> hookClass);

    /**
     * Registers a custom hook by specifying its details directly.
     * Creates a new {@link Hook.HookData} object with the provided parameters and registers the hook.
     *
     * @param name             The unique name of the hook (e.g., "GriefPrevention").
     * @param isEnabled        Whether the hook is enabled and should be used during RTP checks.
     * @param bypassPermission The permission node that allows players to bypass the hook's restrictions
     *                         (e.g., "requisitertp.bypass.griefprevention").
     * @param hookClass        The class implementing the hook's logic, extending {@link Hook}.
     * @return The registered {@link Hook} instance, or null if registration fails or the hook is disabled.
     * @throws IllegalArgumentException If any parameter is invalid (e.g., null name or hookClass).
     */
    Hook registerHook(String name, boolean isEnabled, String bypassPermission, Class<? extends Hook> hookClass);
}