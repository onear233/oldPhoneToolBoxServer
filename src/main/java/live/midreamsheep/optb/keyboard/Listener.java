package live.midreamsheep.optb.keyboard;

import live.midreamsheep.optb.executes.ExecuteHandlerInter;
import live.midreamsheep.optb.executes.ExecutesController;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.*;

/**
 * @author midreamsheep
 * 监听快建键
 * */
public class Listener implements NativeKeyListener {
    private final Set<Integer> keyEvents = new HashSet<>();
    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        keyEvents.add(nativeKeyEvent.getKeyCode());
        StringBuilder sb = new StringBuilder();
        keyEvents.stream().sorted().forEach(sb::append);
        ExecuteHandlerInter handlers = ExecutesController.getExecuteHandlers(sb.toString());
        if(handlers!=null){
            handlers.execute();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        keyEvents.removeIf(event -> event == nativeKeyEvent.getKeyCode());
    }
}
