package org.game.gui.match;

import javafx.util.Pair;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import org.game.core.Utils;
import org.game.core.game.Player;
import org.game.core.game.PropertyType;
import org.game.core.game.TurnHook;
import org.game.gui.match.components.Box;
import org.game.gui.match.components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Window extends JFrame {

    private Player me;
    private Player playingPlayer;

    private HashMap<PropertyType, ArrayList<Box>> proprieties = new HashMap<>();
    private ArrayList<Box> street = new ArrayList<>();
    private TurnHook handler;

    private JPanel window;

    private JPanel board;

    private JPanel panel;
    private JLabel budgetIndicator;
    private JLabel actualPlayerName;

    private Container contentPane;

    private boolean isMyTurn = false;
    private boolean isRolled = false;

    public Window(Player me, TurnHook handleNextTurn) {

        super("Monopoli");

        this.me = me;
        this.handler = handleNextTurn;

        System.out.println(me);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        contentPane = getContentPane();


        contentPane.setBounds(0, 0, 1000, 700);

        contentPane.setLayout(new MigLayout(
            new LC().fill().insets("0 0 0 0"),
            new AC().gap("0").fill(),
            new AC().gap("0").fill()
        ));



        buildBoard();
        buildPanel();


        contentPane.add(board, new CC().growX(60).growY().minHeight("1").minWidth("1"));
        contentPane.add(panel, new CC().growX(40).growY().minHeight("1").minWidth("1"));


        // Full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);


        setLocationRelativeTo(null);
        pack();

        setVisible(false);


    }

    public void startGame(ArrayList<Player> players) {


        for(Player player : players) {
            street.get(0).hoverActualPlayer(player);
        }

        setVisible(true);
    }

    public void startNewTurn(Player playerThatIsGoingToPlay) {

        isRolled = false;
        playingPlayer = playerThatIsGoingToPlay;
        actualPlayerName.setText(playingPlayer.getName());

        isMyTurn = playingPlayer.equals(me);
        System.out.println(me.getName() + " dice " + isMyTurn);
    }

    public void movePawn(Pair<Integer, Integer> roll) {

        isRolled = true;

        street.get(playingPlayer.getPosition()).moveAwayPlayer(playingPlayer);


        if (playingPlayer.move(/*roll.getKey() + roll.getValue()*/ 1)) {
            playingPlayer.passedFromTheStart();
            budgetIndicator.setText(String.valueOf(playingPlayer.getBudget()));
        }

        Box box = street.get(playingPlayer.getPosition());
        box.hoverActualPlayer(playingPlayer);

        if(isMyTurn) {
            if (box instanceof Buyable) {
                /*if (((Buyable) box).isBuyable()) {
                    int res = JOptionPane.showConfirmDialog(contentPane, String.format("<html><h1>Vuoi comprare %s?</h1></html>", box.getName()));

                    if (res == 0) {
                        ((Buyable) box).buy(playingPlayer);
                        budgetIndicator.setText(String.valueOf(playingPlayer.getBudget()));
                    }
                }*/
            } else if (box instanceof Unforeseen) {

            } else if (box instanceof DrinkingZone) {
                JOptionPane.showMessageDialog(null, messageBuilder("Fratelo russ"));
            } else if (box instanceof Gülag) {
                if(playingPlayer.isDeported()) {
                    JOptionPane.showMessageDialog(null, messageBuilder("Stai attento al sapone..."));
                }
            } else if (box instanceof ToTheGülag) {
                street.get(playingPlayer.getPosition()).moveAwayPlayer(playingPlayer);
                playingPlayer.deportToTheGülag();
                street.get(playingPlayer.getPosition()).hoverActualPlayer(playingPlayer);

                JOptionPane.showMessageDialog(null, messageBuilder("Ti hanno catturato! <br>Sarai deportato nel Gülag mi raccomando stai attento al sapone...</h1></html>"));
            }
        }

    }

    public void updateProprierties(ArrayList<Player> players) {
        for(Player player : players) {
            for(Buyable proprierty : player.getProperties()) {
                for(Box box : new ArrayList<>(street)) {
                    if(box.equals(proprierty)) {
                        street.set(street.indexOf(box), proprierty);
                    }
                }
            }
        }
    }


    public void loosePlayer(Player playerThatLoose) {
        JOptionPane.showMessageDialog(null, messageBuilder(String.format("")));
    }

    private void buildBoard() {
        board = new JPanel();
        board.setLayout(new MigLayout(
                new LC().fill().insets("0 0 0 0"),
                new AC().gap("0"),
                new AC().gap("0")
        ));

        buildTopOfBoard();
        buildMiddleOfBoard();
        buildBottomOfBoard();
    }

    private void buildPanel() {
        panel = new JPanel();
        panel.setLayout(new MigLayout(
                new LC().insets("60").fill().alignX("center"),
                new AC(),
                new AC()
        ));


        // Uso un jbutton per poter usare lo sfondo nero
        JPanel budgetContainer = new JPanel();
        budgetContainer.setLayout(new MigLayout(
                new LC().alignX("right")
        ));

        budgetContainer.setBackground(new Color(0, 0, 0));

        budgetIndicator = new JLabel(String.valueOf(me.getBudget()));
        budgetIndicator.setForeground(new Color(33, 255, 34));
        budgetIndicator.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));

        budgetContainer.add(budgetIndicator);

        panel.add(budgetContainer, new CC().growX().alignX("right").wrap());


        JPanel playerDatas = new JPanel();
        playerDatas.setLayout(new MigLayout(
                new LC().alignY("center")
        ));

        JLabel nameLabel = new JLabel("Io:");
        nameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));

        JLabel name = new JLabel(me.getName());
        name.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));

        JLabel actualPlayerLabel = new JLabel("Giocatore attuale: ");
        actualPlayerLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));

        actualPlayerName = new JLabel(playingPlayer != null ? playingPlayer.getName() : "null");
        actualPlayerName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));



        JButton rool = new JButton("Tira i dadi");
        rool.setMargin(new Insets(30,  30, 30, 30));
        rool.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        rool.setBackground(Color.WHITE);
        rool.setForeground(Color.BLACK);
        rool.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 5),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        rool.addActionListener(e -> {
            if(isMyTurn && !isRolled && !playingPlayer.isDeported()) {
                handler.roll(this);
            }
        });

        JButton nextTurn = new JButton("Passa la turno successivo");
        nextTurn.setMargin(new Insets(30,  30, 30, 30));
        nextTurn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        nextTurn.setBackground(Color.WHITE);
        nextTurn.setForeground(Color.BLACK);
        nextTurn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        nextTurn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 5),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        nextTurn.addActionListener(e -> {

            if(isMyTurn && isRolled) {
                if(!playingPlayer.isInGame()) {
                    dispose();
                }
                handler.next(this);
            }
        });

        JLabel myProprieties = new JLabel("My own proprieties:");
        myProprieties.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));

        JPanel ownedProperties = new JPanel();
        ownedProperties.setLayout(new MigLayout(
                new LC().wrapAfter(6).insets("0 0 0 0").gridGapY("10")
        ));

        for (PropertyType type : proprieties.keySet()) {
            for(Box proprierty : proprieties.get(type)) {
                if(proprierty instanceof Buyable) {
                    JLabel label = new JLabel();
                    label.setIcon(new ImageIcon(((Buyable) proprierty).getVerticalIcon().getImage().getScaledInstance(
                            label.getBounds().width != 0 ? label.getBounds().width : 10,
                            label.getBounds().height != 0 ? label.getBounds().height : 10,
                            Image.SCALE_FAST
                    )));

                    label.addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentResized(ComponentEvent e) {
                            super.componentResized(e);
                            label.setIcon(new ImageIcon(((Buyable) proprierty).getVerticalIcon().getImage().getScaledInstance(
                                    label.getBounds().width,
                                    label.getBounds().height,
                                    Image.SCALE_FAST
                            )));
                        }
                    });


                    ownedProperties.add(label, new CC().width("60").height("60").minHeight("1").minHeight("1").grow());
                }
            }
        }

        playerDatas.add(nameLabel, new CC().grow());
        playerDatas.add(name, new CC().wrap().grow());

        playerDatas.add(actualPlayerLabel, new CC().grow());
        playerDatas.add(actualPlayerName, new CC().wrap().gapY("0", "70").grow());

        playerDatas.add(myProprieties, new CC().wrap().grow());
        playerDatas.add(ownedProperties, new CC().grow().minHeight("1").minWidth("1").height("100").width("200"));

        panel.add(playerDatas, new CC().alignX("left").wrap().grow().minWidth("1").minHeight("1"));
        panel.add(nextTurn, new CC().dockSouth().minWidth("1").minHeight("1"));
        panel.add(rool, new CC().dockSouth().minWidth("1").minHeight("1"));
    }

    private void buildTopOfBoard() {
        Start start = new Start();
        board.add(start, buildConstraints());
        street.add(start);

        buildTessile();
        Unforeseen nonBuyable1 = new Unforeseen("assets/RET_VER_BIANCO.png");
        board.add(nonBuyable1, buildConstraints(56, 98));
        street.add(nonBuyable1);

        buildSviluppo();

        Gülag jail = new Gülag();
        board.add(jail, buildConstraintsWithWrap());
        street.add(jail);
    }
    
    private void buildMiddleOfBoard() {

        Iterator<Box> alberghiero = buildAlberghiera();
        Iterator<Box> ferrovie = buildFerroviera();

        Iterator<Box> cibarie = buildCibarie();
        Iterator<Box> armi = buildProdArmi();




        for(int i = 1; i <= 18; i++) {


            if(i % 2 == 1) {
                if(i < 11) {
                    board.add(armi.next(), buildConstraints(98, 56));
                } else {
                    board.add(cibarie.next(), buildConstraints(98, 56));
                }
            } else {
                if(i < 11) {
                    board.add(alberghiero.next(), buildConstraintsWithWrap(98, 56));
                } else {
                    board.add(ferrovie.next(), buildConstraintsWithWrap(98, 56));
                }
            }

            if(i == 1) {
                Main main = new Main();
                board.add(main, buildConstraints(500, 500).span(9, 9));
            }



        }
    }

    private void buildBottomOfBoard() {

        ToTheGülag toTheGülag = new ToTheGülag();
        board.add(toTheGülag, buildConstraints());

        buildSpaziale();

        Unforeseen unforeseen = new Unforeseen("assets/RET_VER_BIANCO.png");
        board.add(unforeseen, buildConstraints(56, 98));

        buildAutomobilistica();

        DrinkingZone drinkingZone = new DrinkingZone();
        board.add(drinkingZone, buildConstraintsWithWrap());


        ArrayList<Box> bottom = new ArrayList<>();

        bottom.add(toTheGülag);
        bottom.addAll(proprieties.get(PropertyType.SPAZIALE));
        bottom.add(unforeseen);
        bottom.addAll(proprieties.get(PropertyType.AUTOMOBILISTICA));
        bottom.add(drinkingZone);

        street.addAll(20, Utils.reverseList(bottom));
        
    }
        
    private void buildTessile() {
        // Nome Temporanei
        Buyable tessile1 = new Buyable("", "assets/property/tessile.png", 1000, PropertyType.TESSILE);
        board.add(tessile1, buildConstraints(56, 98));


        Unforeseen unforeseen = new Unforeseen("assets/RET_VER_BIANCO.png");
        board.add(unforeseen, buildConstraints(56, 98));


        Buyable tessile2 = new Buyable("", "assets/property/tessile.png", 1000, PropertyType.TESSILE);
        board.add(tessile2, buildConstraints(56, 98));

        Buyable tessile3 = new Buyable("", "assets/property/tessile.png", 1000, PropertyType.TESSILE);
        board.add(tessile3, buildConstraints(56, 98));


        ArrayList<Box> vector = new ArrayList<>();

        vector.add(tessile1);
        vector.add(tessile2);
        vector.add(tessile3);

        street.add(tessile1);
        street.add(unforeseen);
        street.add(tessile2);
        street.add(tessile3);

        proprieties.put(PropertyType.TESSILE, vector);
    }

    private void buildSviluppo() {
        Buyable sviluppo1 = new Buyable("", "assets/property/sviluppo.png", 1000, PropertyType.SVILUPPO);
        board.add(sviluppo1, buildConstraints(56, 98));

        Buyable sviluppo2 = new Buyable("", "assets/property/sviluppo.png", 1000, PropertyType.SVILUPPO);
        board.add(sviluppo2, buildConstraints(56, 98));


        Unforeseen unforeseen = new Unforeseen("assets/RET_VER_BIANCO.png");
        board.add(unforeseen, buildConstraints(56, 98));


        Buyable sviluppo3 = new Buyable("", "assets/property/sviluppo.png", 1000, PropertyType.SVILUPPO);
        board.add(sviluppo3, buildConstraints(56, 98));



        ArrayList<Box> vector = new ArrayList<>();

        vector.add(sviluppo1);
        vector.add(sviluppo2);
        vector.add(sviluppo3);

        street.add(sviluppo1);
        street.add(sviluppo2);
        street.add(unforeseen);
        street.add(sviluppo3);

        proprieties.put(PropertyType.SVILUPPO, vector);
    }

    private Iterator<Box> buildProdArmi() {
        ArrayList<Box> vector = new ArrayList<>();

        Buyable armi1 = new Buyable("", "assets/property/prod_armi.png", 1000, PropertyType.PRODUZIONE_ARMI);
        Unforeseen probilità = new Unforeseen("assets/RET_ORR_BIANCO.png");
        Buyable armi2 = new Buyable("", "assets/property/prod_armi.png", 1000, PropertyType.PRODUZIONE_ARMI);
        Buyable armi3 = new Buyable("", "assets/property/prod_armi.png", 1000, PropertyType.PRODUZIONE_ARMI);
        Unforeseen probabilitàCentrale = new Unforeseen("assets/RET_ORR_BIANCO.png");

        vector.add(armi1);
        vector.add(probilità);
        vector.add(armi2);
        vector.add(armi3);
        vector.add(probabilitàCentrale);


        street.add(probabilitàCentrale);
        street.add(armi3);
        street.add(armi2);
        street.add(probilità);
        street.add(armi1);

        proprieties.put(PropertyType.PRODUZIONE_ARMI, vector);

        return vector.iterator();
    }

    private Iterator<Box> buildCibarie() {
        ArrayList<Box> vector = new ArrayList<>();

        Buyable alimentari1 = new Buyable("", "assets/property/alimentari.png", 1000, PropertyType.ALIMENTARI);
        Unforeseen probilità = new Unforeseen("assets/RET_ORR_BIANCO.png");
        Buyable alimentari2 = new Buyable("", "assets/property/alimentari.png", 1000, PropertyType.ALIMENTARI);
        Buyable alimentari3 = new Buyable("", "assets/property/alimentari.png", 1000, PropertyType.ALIMENTARI);


        vector.add(alimentari1);
        vector.add(probilità);
        vector.add(alimentari2);
        vector.add(alimentari3);

        street.add(alimentari3);
        street.add(alimentari2);
        street.add(probilità);
        street.add(alimentari1);

        proprieties.put(PropertyType.ALIMENTARI, vector);

        return vector.iterator();
    }

    private Iterator<Box> buildAlberghiera() {
        ArrayList<Box> vector = new ArrayList<>();

        Buyable alberghiero1 = new Buyable("", "assets/property/alberghiere.png", 1000, PropertyType.ALBERGHIERE);
        Unforeseen probabilità = new Unforeseen("assets/RET_ORR_BIANCO.png");
        Buyable alberghiero2 = new Buyable("", "assets/property/alberghiere.png", 1000, PropertyType.ALBERGHIERE);
        Buyable alberghiero3 = new Buyable("", "assets/property/alberghiere.png", 1000, PropertyType.ALBERGHIERE);
        Unforeseen probabilitàCentrale = new Unforeseen("assets/RET_ORR_BIANCO.png");

        vector.add(alberghiero1);
        vector.add(probabilità);
        vector.add(alberghiero2);
        vector.add(alberghiero3);
        vector.add(probabilitàCentrale);


        street.add(alberghiero1);
        street.add(probabilità);
        street.add(alberghiero2);
        street.add(alberghiero3);
        street.add(probabilitàCentrale);

        proprieties.put(PropertyType.ALBERGHIERE, vector);

        return vector.iterator();
    }

    private Iterator<Box> buildFerroviera() {
        ArrayList<Box> vector = new ArrayList<>();

        vector.add(new Buyable("", "assets/property/ferroviere.png", 1000, PropertyType.FERROVIERE));
        vector.add(new Buyable("", "assets/property/ferroviere.png", 1000, PropertyType.FERROVIERE));
        vector.add(new Unforeseen("assets/RET_ORR_BIANCO.png"));
        vector.add(new Buyable("", "assets/property/ferroviere.png", 1000, PropertyType.FERROVIERE));

        street.addAll(vector);

        proprieties.put(PropertyType.FERROVIERE, vector);

        return vector.iterator();
    }

    private void buildSpaziale() {
        Buyable spaziale1 = new Buyable("", "assets/property/spaziale.png", 1000, PropertyType.SPAZIALE);
        board.add(spaziale1, buildConstraints(56, 98));

        Unforeseen unforeseen = new Unforeseen("assets/RET_VER_BIANCO.png");
        board.add(unforeseen, buildConstraints(56, 98));

        Buyable spaziale2 = new Buyable("", "assets/property/spaziale.png", 1000, PropertyType.SPAZIALE);
        board.add(spaziale2, buildConstraints(56, 98));

        Buyable spaziale3 = new Buyable("", "assets/property/spaziale.png", 1000, PropertyType.SPAZIALE);
        board.add(spaziale3, buildConstraints(56, 98));

        ArrayList<Box> vector = new ArrayList<>();

        vector.add(spaziale1);
        vector.add(unforeseen);
        vector.add(spaziale2);
        vector.add(spaziale3);

        street.add(spaziale1);
        street.add(unforeseen);
        street.add(spaziale2);
        street.add(spaziale3);

        proprieties.put(PropertyType.SPAZIALE, vector);
    }

    private void buildAutomobilistica() {
        Buyable automobilistica1 = new Buyable("", "assets/property/automobilistica.png", 1000, PropertyType.AUTOMOBILISTICA);
        board.add(automobilistica1, buildConstraints(56, 98));

        Buyable automobilistica2 = new Buyable("", "assets/property/automobilistica.png", 1000, PropertyType.AUTOMOBILISTICA);
        board.add(automobilistica2, buildConstraints(56, 98));

        Unforeseen unforeseen = new Unforeseen("assets/RET_VER_BIANCO.png");
        board.add(unforeseen, buildConstraints(56, 98));

        Buyable automobilistica3 = new Buyable("", "assets/property/automobilistica.png", 1000, PropertyType.AUTOMOBILISTICA);
        board.add(automobilistica3, buildConstraints(56, 98));

        ArrayList<Box> vector = new ArrayList<>();

        vector.add(automobilistica1);
        vector.add(automobilistica2);
        vector.add(unforeseen);
        vector.add(automobilistica3);

        street.add(automobilistica1);
        street.add(automobilistica2);
        street.add(unforeseen);
        street.add(automobilistica3);

        proprieties.put(PropertyType.AUTOMOBILISTICA, vector);
    }

    private CC buildConstraints() {
        return buildConstraints(98, 98);
    }

    private CC buildConstraints(int width, int height) {
        return new CC().grow().width(Integer.toString(width)).height(Integer.toString(height)).minWidth("1").minHeight("1");
    }

    private CC buildConstraintsWithWrap() {
        return buildConstraints().wrap();
    }

    private CC buildConstraintsWithWrap(int width, int height) {
        return buildConstraints(width, height).wrap();
    }

    private String messageBuilder(String body) {
        return String.format("<html><h1>%s</h1></html>", body);
    }
}
