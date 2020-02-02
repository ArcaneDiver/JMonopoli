package org.game.core.game;

import com.google.gson.annotations.Expose;

import java.util.Arrays;
import java.util.List;

public class GlobalEvent extends Event {

    @Expose private final double modifier;
    @Expose private final PropertyType propertyType;

    private static final List<GlobalEvent> globalevent = Arrays.asList(
        new GlobalEvent("La moda è cambiata ora tutti volgiono dei nuovi abiti<br>I prezzi raddoppiano", 2, PropertyType.TESSILE),
        new GlobalEvent("Nessuno ha più bisogno di vestiti<br>I prezzi si dimezzano", 0.5, PropertyType.TESSILE),
        new GlobalEvent("Il regime ha deciso di incrementare le ricerche per sviluppare nuove tecnologie<br>I prezzi raddoppiano", 2, PropertyType.SVILUPPO),
        new GlobalEvent("Il regime ha troppo criceti e non sa cosa farsene<br>I prezzi si dimezzano", 0.5, PropertyType.SVILUPPO),
        new GlobalEvent("Di recente diverse case sono diventate inagibili, molte persone devono essere ricollocate<br>I prezzi raddoppiano", 2, PropertyType.CASE_DEL_POPOLO),
        new GlobalEvent("I prezzi di quelle case sono decisamente troppo alti, nessuno vuole comprare<br>I prezzi si dimezzano", 0.5, PropertyType.CASE_DEL_POPOLO),
        new GlobalEvent("Un nuovo treno ad altissima velocità è stato inagurato<br>I prezzi raddoppiano", 2, PropertyType.FERROVIERE),
        new GlobalEvent("Ieri un treno si è schiantato e ora nessuno vuole piu salire su un treno<br>I prezzi si dimezzano", 0.5, PropertyType.FERROVIERE),
        new GlobalEvent("E' stata rilasciata una nuovissima versione di lada<br>I prezzi raddoppiano", 2, PropertyType.AUTOMOBILISTICA),
        new GlobalEvent("A nessuno serve comprare una nuova auto, hanno gia tutti la lada<br>I prezzi si dimezzano", 0.5, PropertyType.AUTOMOBILISTICA),
        new GlobalEvent("La corsa allo spazio è iniziata e la madre patria non ha intenzione di perdere<br>I prezzi triplicano", 3, PropertyType.SPAZIALE),
        new GlobalEvent("Gli americani hanno vinto la corsa allo spazio, la russia abbandona il progetto<br>I prezzi si dimezzano", 0.5, PropertyType.SPAZIALE),
        new GlobalEvent("Il cibo scarseggia<br>I prezzi raddoppiano", 2, PropertyType.ALIMENTARI),
        new GlobalEvent("C'è cibo in abbondanza ma nessuno ne ha bisogno<br>I prezzi si dimezzano", 0.5, PropertyType.ALIMENTARI),
        new GlobalEvent("C'è una grande domanda di armi<br>I prezzi raddoppiano", 2, PropertyType.PRODUZIONE_ARMI),
        new GlobalEvent("Tutti hanno oramai un ak sotto il cuscino da bravi russi<br>I prezzi di dimezzano", 0.5, PropertyType.PRODUZIONE_ARMI)
    );


    public GlobalEvent(String text, double modifier, PropertyType propertyType) {
        super(text);

        this.modifier = modifier;
        this.propertyType = propertyType;
    }



    public double getModifier() {
        return modifier;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public static GlobalEvent getGlobalEvent() {
        return globalevent.get((int) (Math.random() * globalevent.size()));
    }

    @Override
    public String toString() {
        return String.format("Text: %s, Modifier: %s, PropType: %s", text, modifier, propertyType.name());
    }
}
