package org.game.core.game;

import com.google.gson.annotations.Expose;

abstract class Event {

    @Expose protected final String text;

    protected Event(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
