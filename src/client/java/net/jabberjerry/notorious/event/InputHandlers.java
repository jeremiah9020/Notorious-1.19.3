package net.jabberjerry.notorious.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.jabberjerry.notorious.screen.MenuScreen;
import net.jabberjerry.notorious.screen.TitleScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class InputHandlers {
    public static void register() {
        shootKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(KEY_SHOOT, InputUtil.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_RIGHT,KEY_CATEGORY));
        adsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(KEY_ADS, InputUtil.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_LEFT,KEY_CATEGORY));
        reloadKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(KEY_RELOAD, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,KEY_CATEGORY));
        changeKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(KEY_CHANGE_FIRE_TYPE, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F,KEY_CATEGORY));
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(KEY_MENU, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_M,KEY_CATEGORY));

        registerKeyInputs();
    }

    public static KeyBinding shootKey, adsKey, reloadKey, changeKey, menuKey;

    private static final String KEY_CATEGORY = "key.category.notorious";
    private static final String KEY_SHOOT = "key.notorious.shoot";
    private static final String KEY_ADS = "key.notorious.aim_down_sight";
    private static final String KEY_RELOAD = "key.notorious.reload";
    private static final String KEY_CHANGE_FIRE_TYPE = "key.notorious.change_fire_type";
    private static final String KEY_MENU = "key.notorious.menu";


    private static void registerKeyInputs() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            long handle = client.getWindow().getHandle();



            if (getPressed(shootKey.getBoundKeyTranslationKey(),handle)) {
                if (client.player != null && client.currentScreen != null) client.player.sendMessage(client.currentScreen.getTitle());
            }
            if (getPressed(adsKey.getBoundKeyTranslationKey(),handle)) {
                if (client.player != null) client.player.sendMessage(Text.literal("ADSing!"));
            }
            if (getPressed(reloadKey.getBoundKeyTranslationKey(),handle)) {
                if (client.player != null) client.player.sendMessage(Text.literal("Reloading!"));
            }
            if (getPressed(changeKey.getBoundKeyTranslationKey(),handle)) {
                if (client.player != null) client.player.sendMessage(Text.literal("Changing Fire Type!"));
            }
            if (getPressed(menuKey.getBoundKeyTranslationKey(),handle)) {
                if (client.player != null) client.setScreen(new MenuScreen());
            }
        });
    }
    private static boolean getPressed(String key,long windowHandle) {
        int code = InputUtil.fromTranslationKey(key).getCode();
        if (code >= 0 && code <= 7) {
            return GLFW.glfwGetMouseButton(windowHandle,code) == 1;
        } else if (code >= 32) {
            return GLFW.glfwGetKey(windowHandle,code) == 1;
        }
        return false;
    }
}
