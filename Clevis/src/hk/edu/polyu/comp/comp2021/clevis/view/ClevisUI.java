package hk.edu.polyu.comp.comp2021.clevis.view;

import hk.edu.polyu.comp.comp2021.clevis.Application;
import hk.edu.polyu.comp.comp2021.clevis.controller.Console;
import hk.edu.polyu.comp.comp2021.clevis.controller.Data;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * ClevisUI
 */
public class ClevisUI extends JFrame {
    private static final int UI_WIDTH = 1200;
    private static final int UI_HEIGHT = 750;
    private static final int TERMINAL_WIDTH = 450;
    private final Console console;
    private final DrawingPanel drawingPanel;
    private final TerminalPanel terminalPanel;

    /**
     * Construtcion
     * @param htmlPath      html Path
     * @param txtPath       txt Path
     */
    public ClevisUI(String htmlPath, String txtPath) {
//        Application.main(new String[]{"-html", htmlPath, "-txt", txtPath});

        this.console = Application.console();
        this.drawingPanel = new DrawingPanel(console.data());
        this.terminalPanel = new TerminalPanel(console, this);

        setupUI();
        // automatically refresh the Canvas Panel
        new Timer(100, e -> drawingPanel.repaint()).start();
    }

    private void setupUI() {
        setTitle("Clevis");
        setSize(UI_WIDTH, UI_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Termianl Panel on the left
        terminalPanel.setPreferredSize(new Dimension(TERMINAL_WIDTH, 0));
        terminalPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Terminal",
                TitledBorder.LEFT, TitledBorder.TOP));

        // Drawing Panel on the right
        JScrollPane scrollPane = new JScrollPane(drawingPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Drawing",
                TitledBorder.LEFT, TitledBorder.TOP));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, terminalPanel, scrollPane);
        splitPane.setDividerLocation(TERMINAL_WIDTH);
        add(splitPane);

        // press ESC to quit
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("ESCAPE"), "quit");
        getRootPane().getActionMap().put("quit", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                console.quit();
            }
        });
    }

    /**
     * refresh the Canvas
     */
    public void refreshCanvas() {
        drawingPanel.repaint();
    }

    /**
     * getData
     * @return      data
     */
    public Data getData() {
        return console.data();
    }
}