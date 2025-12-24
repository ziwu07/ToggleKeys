package ziwu07.togglekeys;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

@Mod(modid = ToggleKeys.MOD_ID, version = ToggleKeys.VERSION, name = ToggleKeys.MOD_NAME, clientSideOnly = true)
public class ToggleKeys {
    public static final String MOD_ID = "ToggleKeys";
    public static final String VERSION = "1.1";
    public static final String MOD_NAME = "ToggleKeys";
    private KeyBinding unPressAll;

    private KeyBinding holdForward;
    private KeyBinding holdBackward;
    private KeyBinding holdLeft;
    private KeyBinding holdRight;
    private KeyBinding holdLMouse;
    KeyBinding[] keyList;
    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        final String[] KEY_DESCRIPTIONS = {
                "Forward",
                "Backward",
                "Left",
                "Right",
                "LMouse",
                "UnPressAll"
        };
        GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
        keyList = new KeyBinding[]{
                gameSettings.keyBindForward,
                gameSettings.keyBindBack,
                gameSettings.keyBindLeft,
                gameSettings.keyBindRight,
                gameSettings.keyBindAttack
        };
        holdForward = new KeyBinding(KEY_DESCRIPTIONS[0], 0, MOD_NAME);
        holdBackward = new KeyBinding(KEY_DESCRIPTIONS[1], 0, MOD_NAME);
        holdLeft = new KeyBinding(KEY_DESCRIPTIONS[2], 0, MOD_NAME);
        holdRight = new KeyBinding(KEY_DESCRIPTIONS[3], 0, MOD_NAME);
        holdLMouse = new KeyBinding(KEY_DESCRIPTIONS[4], 0, MOD_NAME);
        unPressAll = new KeyBinding(KEY_DESCRIPTIONS[5], 0, MOD_NAME);
        ClientRegistry.registerKeyBinding(unPressAll);
        ClientRegistry.registerKeyBinding(holdBackward);
        ClientRegistry.registerKeyBinding(holdForward);
        ClientRegistry.registerKeyBinding(holdLeft);
        ClientRegistry.registerKeyBinding(holdRight);
        ClientRegistry.registerKeyBinding(holdLMouse);
    }

    @SubscribeEvent
    public void onInput(InputEvent.KeyInputEvent event) {
        if (holdForward.isPressed()) {
            KeyBinding.setKeyBindState(keyList[0].getKeyCode(), !keyList[0].isKeyDown());
            KeyBinding.setKeyBindState(keyList[1].getKeyCode(), false);
        }
        if (holdBackward.isPressed()) {
            KeyBinding.setKeyBindState(keyList[1].getKeyCode(), !keyList[1].isKeyDown());
            KeyBinding.setKeyBindState(keyList[0].getKeyCode(), false);
        }
        if (holdLeft.isPressed()) {
            KeyBinding.setKeyBindState(keyList[2].getKeyCode(), !keyList[2].isKeyDown());
            KeyBinding.setKeyBindState(keyList[3].getKeyCode(), false);
        }
        if (holdRight.isPressed()) {
            KeyBinding.setKeyBindState(keyList[3].getKeyCode(), !keyList[3].isKeyDown());
            KeyBinding.setKeyBindState(keyList[2].getKeyCode(), false);
        }
        if (holdLMouse.isPressed()) {
            KeyBinding.setKeyBindState(keyList[4].getKeyCode(), !keyList[4].isKeyDown());
        }
        if (unPressAll.isPressed()) {
            KeyBinding.unPressAllKeys();
        }
    }
}
