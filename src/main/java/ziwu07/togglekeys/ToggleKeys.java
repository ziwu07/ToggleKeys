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
import java.util.HashMap;
import java.util.Map;


@Mod(modid = ToggleKeys.MOD_ID, version = ToggleKeys.VERSION, name = ToggleKeys.MOD_NAME, clientSideOnly = true)
public class ToggleKeys {
    public static final String MOD_ID = "ToggleKeys";
    public static final String VERSION = "1.0";
    public static final String MOD_NAME = "ToggleKeys";
    private final HashMap<KeyBinding,KeyBinding> keyBinds = new HashMap<>();
    private KeyBinding unPressAll;

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
                "LMouse"
        };
        GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
        KeyBinding[] keyList = new KeyBinding[]{
                gameSettings.keyBindForward,
                gameSettings.keyBindBack,
                gameSettings.keyBindLeft,
                gameSettings.keyBindRight,
                gameSettings.keyBindAttack
        };
        KeyBinding keyBind;
        for (int i = 0; i < KEY_DESCRIPTIONS.length; i++) {
            keyBind = new KeyBinding(KEY_DESCRIPTIONS[i], 0, MOD_NAME);
            keyBinds.put(keyBind, keyList[i]);
            ClientRegistry.registerKeyBinding(keyBind);
        }
        unPressAll = new KeyBinding("UnPressAll", 0, MOD_NAME);
        ClientRegistry.registerKeyBinding(unPressAll);
    }

    @SubscribeEvent
    public void onInput(InputEvent.KeyInputEvent event) {
        for (Map.Entry<KeyBinding,KeyBinding> entry: keyBinds.entrySet()){
            if (entry.getKey().isPressed()) {
                KeyBinding tempKey = entry.getValue();
                KeyBinding.setKeyBindState(tempKey.getKeyCode(), !tempKey.isKeyDown());
            }
        }
        if (unPressAll.isPressed()) {
            KeyBinding.unPressAllKeys();
        }
    }
}
