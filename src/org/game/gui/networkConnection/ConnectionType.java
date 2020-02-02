package org.game.gui.networkConnection;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class ConnectionType extends JFrame {

    private Container contentPane;
    private JPanel choose;

    private ChooseConnection chooseCallBack;

    public ConnectionType(ChooseConnection callback) {

        super("Scegli se hostare o connetterti");
        setSize(700, 700);
        contentPane = getContentPane();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        chooseCallBack = callback;

        buildChoose();

        contentPane.add(choose, BorderLayout.CENTER);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void buildChoose() {
        choose = new JPanel();

        choose.setLayout(new MigLayout(
                new LC().fill()
        ));

        JLabel banner = new JLabel();
        banner.setIcon(new ImageIcon(new ImageIcon("assets/banner.png").getImage().getScaledInstance(
                banner.getBounds().width != 0 ? banner.getBounds().width : 100,
                banner.getBounds().height != 0 ? banner.getBounds().height : 100,
                Image.SCALE_FAST
        )));

        banner.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                banner.setIcon(new ImageIcon(new ImageIcon("assets/banner.png").getImage().getScaledInstance(
                        banner.getBounds().width,
                        banner.getBounds().height,
                        Image.SCALE_FAST
                )));
            }
        });

        JButton clientButton = new JButton("Connect to a server");
        clientButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        clientButton.setName("client");
        clientButton.addMouseListener(mouseClickListener);

        JButton serverButton = new JButton("Create a server");
        serverButton.setName("server");
        serverButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        serverButton.addMouseListener(mouseClickListener);

        choose.add(banner, new CC().span(2, 1).growX().growY((float) 50).width("150").height("300").minWidth("1").minHeight("1").wrap());
        choose.add(clientButton, new CC().growX().growY((float) 50).width("50").height("50").minWidth("1").minHeight("1"));
        choose.add(serverButton, new CC().growX().growY((float) 50).width("50").height("50").minWidth("1").minHeight("1"));

    }

    private MouseAdapter mouseClickListener = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            JButton source = (JButton) e.getSource();
            if(source.getName().equals("server"))
                chooseCallBack.choose(true);
            else {
                System.out.println(source.getName());
                chooseCallBack.choose(false);
            }
            dispose();
        }
    };
}
