package org.game.core.game;

import com.google.gson.annotations.Expose;
import org.game.gui.match.components.Unforeseen;

import java.util.Arrays;
import java.util.List;

public class UnforeseenEvent extends Event {

    @Expose private final Integer money;

    private static final List<UnforeseenEvent> unforeseenEvents = Arrays.asList(
            new UnforeseenEvent("La mafia russa ti ha beccato paga 10000 RUB", -10000)
    );

    public UnforeseenEvent(String text, int money) {
        super(text);
        this.money = money;
    }

    public static UnforeseenEvent getUnforeseenEvent() {
        return unforeseenEvents.get((int) (Math.random() * unforeseenEvents.size()));
    }

    public int getMoney() {
        return money;
    }
}
