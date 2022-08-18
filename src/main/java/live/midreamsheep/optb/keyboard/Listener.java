package live.midreamsheep.optb.keyboard;

import live.midreamsheep.optb.execute.ExecutesController;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Listener implements NativeKeyListener {
    private final List<NativeKeyEvent> keyEvents = new LinkedList<>();
    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        //keyEvents去重
        keyEvents.removeIf(keyEvent -> keyEvent.getKeyCode() == nativeKeyEvent.getKeyCode());
        keyEvents.add(nativeKeyEvent);
        //ketEvents排序
        keyEvents.sort(Comparator.comparingInt(NativeKeyEvent::getKeyCode));
        StringBuilder sb = new StringBuilder();
        keyEvents.forEach(k->sb.append(k.getKeyCode()));
        Optional.ofNullable(ExecutesController.getExecuteHandlers(sb.toString())).orElse(()->{}).execute();
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        keyEvents.removeIf(event -> event.getKeyCode() == nativeKeyEvent.getKeyCode());
    }
}
