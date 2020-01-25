package org.game.gui.match;

import javafx.util.Pair;
import net.miginfocom.layout.*;
import net.miginfocom.swing.MigLayout;

import org.game.core.Utils;
import org.game.core.game.TurnHook;
import org.game.core.game.Player;
import org.game.core.game.PropertyType;
import org.game.gui.match.components.*;
import org.game.gui.match.components.Box;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Window extends JFrame {

    private ArrayList<Player> players;
    private Player me;
    private Player playingPlayer;

    private HashMap<PropertyType, ArrayList<Box>> proprieties = new HashMap<>();
    private ArrayList<Box> street = new ArrayList<>();
    private TurnHook handler;

    private JPanel window;

    private JPanel board;

    private JPanel panel;
    private JLabel budgetIndicator;


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


        contentPane.add(board, new CC().growX(60).growY());
        contentPane.add(panel, new CC().growX(40).growY());


        // Full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);


        setLocationRelativeTo(null);
        pack();

        setVisible(false);


    }

    public void startGame(ArrayList<Player> players) {
        this.players = players;

        System.out.println(players.get(0));

        for(Player player : players) {
            street.get(0).hoverActualPlayer(player);
        }

        setVisible(true);
    }

    public void startNewTurn(Player playerThatIsGoingToPlay) {

        isRolled = false;
        playingPlayer = playerThatIsGoingToPlay;

        isMyTurn = playingPlayer.equals(me);
        System.out.println(me.getName() + " dice " + isMyTurn);
    }

    public void movePawn(Pair<Integer, Integer> roll) {
        isRolled = true;
        street.get(playingPlayer.getPosition()).moveAwayPlayer(playingPlayer);

        playingPlayer.move(/*roll.getKey() + roll.getValue()*/ 1);

        street.get(playingPlayer.getPosition()).hoverActualPlayer(playingPlayer);

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

        JLabel nameLabel = new JLabel("Nome:");
        nameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));

        JLabel name = new JLabel(me.getName());
        name.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));




        JPanel ownedProperties = new JPanel();
        ownedProperties.setLayout(new MigLayout(
                new LC()
        ));

        JButton rool = new JButton("Tira i dadi");
        rool.setMargin(new Insets(30,  30, 30, 30));
        rool.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        rool.addActionListener(e -> {
            if(isMyTurn && !isRolled) {
                handler.roll(this);
            }
        });

        JButton nextTurn = new JButton("Passa la turno successivo");
        nextTurn.setMargin(new Insets(30,  30, 30, 30));
        nextTurn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        nextTurn.addActionListener(e -> {
            if(isMyTurn && isRolled) {
                handler.next(this);
            }
        });

        /*
        for (PropertyType type : proprieties.keySet()) {
            for(int i = 0; i < prop)
                JButton prop = new JButton(proprierty);
                ownedProperties.add(prop, new CC().width("60!").height("60!"));
            }
        }*/
        playerDatas.add(nameLabel);
        playerDatas.add(name);

        //playerDatas.add(ownedProperties, new CC());

        panel.add(playerDatas, new CC().alignX("left").wrap().grow());
        panel.add(nextTurn, new CC().dockSouth());
        panel.add(rool, new CC().dockSouth());
    }

    private void buildTopOfBoard() {
        Corner start = new Corner("Via");
        board.add(start, buildConstraints());
        street.add(start);

        buildTessile();
        NonBuyable nonBuyable1 = new NonBuyable("assets/RET_VER_BIANCO.png");
        board.add(nonBuyable1, buildConstraints(56, 98));
        street.add(nonBuyable1);

        buildSviluppo();

        Corner jail = new Corner("Gülag");
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
                Corner main = new Corner("", "assets/main.png");
                board.add(main, buildConstraints(500, 500).span(9, 9));
            }



        }
    }

    private void buildBottomOfBoard() {
        
        
        Corner areaRiposo = new Corner("Area riposo");
        board.add(areaRiposo, buildConstraints());

        buildSpaziale();

        NonBuyable nonBuyable2 = new NonBuyable("assets/RET_VER_BIANCO.png");
        board.add(nonBuyable2, buildConstraints(56, 98));

        buildAutomobilistica();

        Corner toJail = new Corner("Deportazione");
        board.add(toJail, buildConstraintsWithWrap());


        ArrayList<Box> bottom = new ArrayList<>();

        bottom.add(areaRiposo);
        bottom.addAll(proprieties.get(PropertyType.SPAZIALE));
        bottom.add(nonBuyable2);
        bottom.addAll(proprieties.get(PropertyType.AUTOMOBILISTICA));
        bottom.add(toJail);

        street.addAll(20, Utils.reverseList(bottom));
        
    }
        
    private void buildTessile() {
        // Nome Temporanei
        Buyable tessile1 = new Buyable("assets/property/RET_VER_MARRONE.png");
        board.add(tessile1, buildConstraints(56, 98));


        NonBuyable nonBuyable1 = new NonBuyable("assets/RET_VER_BIANCO.png");
        board.add(nonBuyable1, buildConstraints(56, 98));


        Buyable tessile2 = new Buyable("assets/property/RET_VER_MARRONE.png");
        board.add(tessile2, buildConstraints(56, 98));

        Buyable tessile3 = new Buyable("assets/property/RET_VER_MARRONE.png");
        board.add(tessile3, buildConstraints(56, 98));


        ArrayList<Box> vector = new ArrayList<>();

        vector.add(tessile1);
        vector.add(tessile2);
        vector.add(tessile3);

        street.add(tessile1);
        street.add(nonBuyable1);
        street.add(tessile2);
        street.add(tessile3);

        proprieties.put(PropertyType.TESSILE, vector);
    }

    private void buildSviluppo() {
        Buyable sviluppo1 = new Buyable("assets/property/RET_XVER_MARRONE.png");
        board.add(sviluppo1, buildConstraints(56, 98));

        Buyable sviluppo2 = new Buyable("assets/property/RET_XVER_MARRONE.png");
        board.add(sviluppo2, buildConstraints(56, 98));


        NonBuyable nonBuyable1 = new NonBuyable("assets/RET_VER_BIANCO.png");
        board.add(nonBuyable1, buildConstraints(56, 98));


        Buyable sviluppo3 = new Buyable("assets/property/RET_XVER_MARRONE.png");
        board.add(sviluppo3, buildConstraints(56, 98));



        ArrayList<Box> vector = new ArrayList<>();

        vector.add(sviluppo1);
        vector.add(sviluppo2);
        vector.add(sviluppo3);

        street.add(sviluppo1);
        street.add(sviluppo2);
        street.add(nonBuyable1);
        street.add(sviluppo3);

        proprieties.put(PropertyType.SVILUPPO, vector);
    }

    private Iterator<Box> buildProdArmi() {
        ArrayList<Box> vector = new ArrayList<>();

        Buyable armi1 = new Buyable("assets/property/RET_ORI_VIOLA.png");
        Unforeseen probilità = new Unforeseen("assets/RET_ORR_BIANCO.png");
        Buyable armi2 = new Buyable("assets/property/RET_ORI_VIOLA.png");
        Buyable armi3 = new Buyable("assets/property/RET_ORI_VIOLA.png");
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

        Buyable alimenti1 = new Buyable("assets/property/RET_ORI_VERDE.png");
        Unforeseen probilità = new Unforeseen("assets/RET_ORR_BIANCO.png");
        Buyable alimenti2 = new Buyable("assets/property/RET_ORI_VERDE.png");
        Buyable alimenti3 = new Buyable("assets/property/RET_ORI_VERDE.png");


        vector.add(alimenti1);
        vector.add(probilità);
        vector.add(alimenti2);
        vector.add(alimenti3);

        street.add(alimenti3);
        street.add(alimenti2);
        street.add(probilità);
        street.add(alimenti1);

        proprieties.put(PropertyType.ALIMENTARI, vector);

        return vector.iterator();
    }

    private Iterator<Box> buildAlberghiera() {
        ArrayList<Box> vector = new ArrayList<>();

        Buyable alberghiero1 = new Buyable("assets/property/RET_ORI_ARANCIONE.png");
        Unforeseen probabilità = new Unforeseen("assets/RET_ORR_BIANCO.png");
        Buyable alberghiero2 = new Buyable("assets/property/RET_ORI_ARANCIONE.png");
        Buyable alberghiero3 = new Buyable("assets/property/RET_ORI_ARANCIONE.png");
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

        proprieties.put(PropertyType.ALBERGHERE, vector);

        return vector.iterator();
    }

    private Iterator<Box> buildFerroviera() {
        ArrayList<Box> vector = new ArrayList<>();

        vector.add(new Buyable("assets/property/RET_ORI_ROSA.png"));
        vector.add(new Buyable("assets/property/RET_ORI_ROSA.png"));
        vector.add(new Unforeseen("assets/RET_ORR_BIANCO.png"));
        vector.add(new Buyable("assets/property/RET_ORI_ROSA.png"));

        street.addAll(vector);

        proprieties.put(PropertyType.FERROVIERE, vector);

        return vector.iterator();
    }

    private void buildSpaziale() {
        Buyable spaziale1 = new Buyable("assets/property/RET_VER_GIALLO.png");
        board.add(spaziale1, buildConstraints(56, 98));

        NonBuyable nonBuyable = new NonBuyable("assets/RET_VER_BIANCO.png");
        board.add(nonBuyable, buildConstraints(56, 98));

        Buyable spaziale2 = new Buyable("assets/property/RET_VER_GIALLO.png");
        board.add(spaziale2, buildConstraints(56, 98));

        Buyable spaziale3 = new Buyable("assets/property/RET_VER_GIALLO.png");
        board.add(spaziale3, buildConstraints(56, 98));

        ArrayList<Box> vector = new ArrayList<>();

        vector.add(spaziale1);
        vector.add(nonBuyable);
        vector.add(spaziale2);
        vector.add(spaziale3);

        street.add(spaziale1);
        street.add(nonBuyable);
        street.add(spaziale2);
        street.add(spaziale3);

        proprieties.put(PropertyType.SPAZIALE, vector);
    }

    private void buildAutomobilistica() {
        Buyable automobilistica1 = new Buyable("assets/property/RET_VER_ROSSO.png");
        board.add(automobilistica1, buildConstraints(56, 98));

        Buyable automobilistica2 = new Buyable("assets/property/RET_VER_ROSSO.png");
        board.add(automobilistica2, buildConstraints(56, 98));

        NonBuyable nonBuyable = new NonBuyable("assets/RET_VER_BIANCO.png");
        board.add(nonBuyable, buildConstraints(56, 98));

        Buyable automobilistica3 = new Buyable("assets/property/RET_VER_ROSSO.png");
        board.add(automobilistica3, buildConstraints(56, 98));

        ArrayList<Box> vector = new ArrayList<>();

        vector.add(automobilistica1);
        vector.add(automobilistica2);
        vector.add(nonBuyable);
        vector.add(automobilistica3);

        street.add(automobilistica1);
        street.add(automobilistica2);
        street.add(nonBuyable);
        street.add(automobilistica3);

        proprieties.put(PropertyType.AUTOMOBILISTICA, vector);
    }

    private CC buildConstraints() {
        return buildConstraints(98, 98);
    }

    private CC buildConstraints(int width, int height) {
        return new CC().grow().width(Integer.toString(width)).height(Integer.toString(height)).minWidth("10").minHeight("10");
    }

    private CC buildConstraintsWithWrap() {
        return buildConstraints().wrap();
    }

    private CC buildConstraintsWithWrap(int width, int height) {
        return buildConstraints(width, height).wrap();
    }
}
