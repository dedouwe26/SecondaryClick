package oxded.secondaryclick;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface OptionsLoadedCallback {
     
    Event<OptionsLoadedCallback> EVENT = EventFactory.createArrayBacked(OptionsLoadedCallback.class,
        (listeners) -> () -> {
            for (OptionsLoadedCallback listener : listeners) {
                listener.interact();
            }
        }
    );
 
    void interact();
}