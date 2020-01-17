package org.game.gui.match;

import net.miginfocom.layout.*;
import net.miginfocom.swing.MigLayout;

import org.game.core.game.Player;
import org.game.gui.match.components.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

class Window extends JFrame {

    private Player player;
    private JLabel budgetIndicator;

    private JPanel window;

    private JPanel board;
    private JPanel panel;

    private Container contentPane;

    public Window(Player player) {
        super("Monopoli");

        this.player = player;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        window = new JPanel();
        contentPane = window;
        setContentPane(contentPane);

        contentPane.setSize(1000, 700);

        contentPane.setLayout(new MigLayout(
            new LC().fill(),
            new AC().gap("0"),
            new AC().gap("0")
        ));


        buildBoard();
        buildPanel();


        contentPane.add(board, new CC().growX(60).growY());
        contentPane.add(panel, new CC().growX(40).growY());

        // Full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLocationRelativeTo(null);
        pack();

        setVisible(true);
    }

    private void buildBoard() {
        board = new JPanel();
        board.setLayout(new MigLayout(
                new LC().fill(),
                new AC().gap("0"),
                new AC().gap("0")
        ));

        Corner areaRiposo = new Corner();
        board.add(areaRiposo, buildConstraints());

        buildTessile();
        NonBuyable nonBuyable1 = new NonBuyable("assets/RET_VER_BIANCO.png");
        board.add(nonBuyable1, buildConstraints(56, 98));
        buildSviluppo();

        Corner toJail = new Corner();
        board.add(toJail, buildConstraintsWithWrap());

        buildMiddleOfBoard();

        Corner jail = new Corner();
        board.add(jail, buildConstraints());

        buildSpaziale();
        NonBuyable nonBuyable2 = new NonBuyable("assets/RET_VER_BIANCO.png");
        board.add(nonBuyable2, buildConstraints(56, 98));
        buildAutomobilistica();

        Corner start = new Corner();
        board.add(start, buildConstraintsWithWrap());
    }

    private void buildPanel() {
        panel = new JPanel();
        panel.setLayout(new MigLayout(
                new LC().insets("60").fill().alignX("center"),
                new AC(),
                new AC()
        ));

        panel.setBorder(BorderFactory.createTitledBorder("Ciao"));

        // Uso un jbutton per poter usare lo sfondo nero
        JPanel budgetContainer = new JPanel();
        budgetContainer.setLayout(new MigLayout(
                new LC().alignX("right")
        ));

        budgetContainer.setBackground(new Color(0, 0, 0));

        budgetIndicator = new JLabel(String.valueOf(player.getBudget()));
        budgetIndicator.setForeground(new Color(2, 255, 0));
        budgetIndicator.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));

        budgetContainer.add(budgetIndicator);



        panel.add(budgetContainer, new CC().growX().alignX("right").wrap());


        JLabel playerName = new JLabel(player.getName());
        playerName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));

        panel.add(playerName, new CC().alignX("left").wrap().grow());

    }

    private void buildMiddleOfBoard() {
        Iterator<Buyable> armi = buildProdArmi();
        Iterator<Buyable> cibarie = buildCibarie();
        Iterator<Buyable> alberghiero = buildAlberghiera();
        Iterator<Buyable> ferrovie = buildFerroviera();

        for(int i = 1; i <= 18; i++) {
            switch (i) {
                case 5: {
                    NonBuyable probabilty1 = new NonBuyable("assets/RET_ORR_BIANCO.png");
                    board.add(probabilty1, buildConstraints(98, 56));

                    break;
                }
                case 6: {
                    NonBuyable probabilty2 = new NonBuyable("assets/RET_ORR_BIANCO.png");
                    board.add(probabilty2, buildConstraintsWithWrap(98, 56));

                    break;
                }
                case 9: {
                    NonBuyable probabilty3 = new NonBuyable("assets/RET_ORR_BIANCO.png");
                    board.add(probabilty3, buildConstraints(98, 56));

                    break;
                }
                case 10: {
                    NonBuyable probabilty4 = new NonBuyable("assets/RET_ORR_BIANCO.png");
                    board.add(probabilty4, buildConstraintsWithWrap(98, 56));

                    break;
                }
                case 15: {
                    NonBuyable probabilty5 = new NonBuyable("assets/RET_ORR_BIANCO.png");
                    board.add(probabilty5, buildConstraints(98, 56));
                    break;
                }
                case 16: {
                    NonBuyable probabilty6 = new NonBuyable("assets/RET_ORR_BIANCO.png");
                    board.add(probabilty6, buildConstraintsWithWrap(98, 56));

                    break;
                }
                default: {
                    if(i % 2 == 1) {
                        if(i < 9) {
                            board.add(armi.next(), buildConstraints(98, 56));
                        } else {
                            board.add(cibarie.next(), buildConstraints(98, 56));
                        }
                    } else {
                        if(i < 9) {
                            board.add(alberghiero.next(), buildConstraintsWithWrap(98, 56));
                        } else {
                            board.add(ferrovie.next(), buildConstraintsWithWrap(98, 56));
                        }
                    }

                    if(i == 1) {
                        Corner main = new Corner("", "assets/main.png");
                        board.add(main, buildConstraints(500, 500).span(9, 9));
                    }
                    break;
                }
            }
        }
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
    }

    private Iterator<Buyable> buildProdArmi() {
        Vector<Buyable> vector = new Vector<>();

        vector.add(new Buyable("assets/property/RET_ORI_VIOLA.png"));
        vector.add(new Buyable("assets/property/RET_ORI_VIOLA.png"));
        vector.add(new Buyable("assets/property/RET_ORI_VIOLA.png"));

        return vector.iterator();
    }

    private Iterator<Buyable> buildCibarie() {
        Vector<Buyable> vector = new Vector<>();

        vector.add(new Buyable("assets/property/RET_ORI_VERDE.png"));
        vector.add(new Buyable("assets/property/RET_ORI_VERDE.png"));
        vector.add(new Buyable("assets/property/RET_ORI_VERDE.png"));

        return vector.iterator();
    }

    private Iterator<Buyable> buildAlberghiera() {
        Vector<Buyable> vector = new Vector<>();

        vector.add(new Buyable("assets/property/RET_ORI_ARANCIONE.png"));
        vector.add(new Buyable("assets/property/RET_ORI_ARANCIONE.png"));
        vector.add(new Buyable("assets/property/RET_ORI_ARANCIONE.png"));

        return vector.iterator();
    }

    private Iterator<Buyable> buildFerroviera() {
        Vector<Buyable> vector = new Vector<>();

        vector.add(new Buyable("assets/property/RET_ORI_ROSA.png"));
        vector.add(new Buyable("assets/property/RET_ORI_ROSA.png"));
        vector.add(new Buyable("assets/property/RET_ORI_ROSA.png"));

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
