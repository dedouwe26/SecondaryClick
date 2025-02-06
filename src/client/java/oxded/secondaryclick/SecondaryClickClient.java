package oxded.secondaryclick;

import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class SecondaryClickClient implements ClientModInitializer {
	public static final String MOD_ID = "secondary-click";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public RedirectKeyBinding secondaryAttack;
	public RedirectKeyBinding secondaryUse;
	@Override
	public void onInitializeClient() {
		secondaryAttack = new RedirectKeyBinding("key.oxded.secondaryclick.secondary.attack", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, KeyBinding.GAMEPLAY_CATEGORY, () -> MinecraftClient.getInstance().options.attackKey);
		secondaryUse = new RedirectKeyBinding("key.oxded.secondaryclick.secondary.use", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, KeyBinding.GAMEPLAY_CATEGORY, () -> MinecraftClient.getInstance().options.useKey);

		KeyBindingHelper.registerKeyBinding(secondaryAttack);
		KeyBindingHelper.registerKeyBinding(secondaryUse);
	}
}