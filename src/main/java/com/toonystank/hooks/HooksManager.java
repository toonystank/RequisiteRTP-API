package com.toonystank.hooks;


public interface HooksManager {

    Hook registerHook(Hook.HookData hookData,Class<? extends Hook> hookClass);
    Hook registerHook(String name
            ,boolean isEnabled
            ,String bypassPermission
            ,Class<? extends Hook> hookClass);

}
