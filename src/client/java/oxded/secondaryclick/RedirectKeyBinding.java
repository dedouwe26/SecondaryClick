package oxded.secondaryclick;

import java.lang.reflect.Field;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil.Type;

public class RedirectKeyBinding extends KeyBinding {
    @Nullable
    public final Supplier<KeyBinding> redirection;
    private static Field timesPressed = null;

    static {
        MappingResolver resolver = FabricLoader.getInstance().getMappingResolver();

        try {
            timesPressed = KeyBinding.class.getDeclaredField(resolver.mapFieldName("intermediary", resolver.unmapClassName("intermediary", KeyBinding.class.getName()), "field_1661", "I"));
            timesPressed.setAccessible(true);
        } catch (Exception e) {}

        if (timesPressed == null) {
            SecondaryClickClient.LOGGER.error("Failed to find timesPressed field in KeyBinding class");
        }
    }
    
    public RedirectKeyBinding(String translationKey, Type type, int code, String category, Supplier<KeyBinding> redirection) {
        super(translationKey, type, code, category);
        this.redirection = redirection;

        if (timesPressed == null) {
            return;
        }
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (this.wasPressed()) {
                KeyBinding r = redirection.get();
                if (r != null) {
                    int t = 0;
                    try {
                        t = (int)timesPressed.get(r);
                    } catch (IllegalAccessException e) {
                        SecondaryClickClient.LOGGER.error("Failed to access timesPressed field in KeyBinding class");
                        return;
                    }
                    try {
                        timesPressed.set(r, ++t);
                    } catch (IllegalAccessException e) {
                        SecondaryClickClient.LOGGER.error("Failed to set timesPressed field in KeyBinding class");
                        return;
                    }
                    
                }
            }
        });
    }

    public RedirectKeyBinding(String translationKey, int code, String category, Supplier<KeyBinding> redirection) {
        super(translationKey, code, category);
        this.redirection = redirection;
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        KeyBinding r = redirection.get();
        if (r != null)
            r.setPressed(pressed);
    }
}
