package com.thelocalmarketplace.software.gui;

import com.thelocalmarketplace.software.logic.CentralStationLogic;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class SessionBlockedPopUp {

    private static JDialog popupDialog;
    private static HashMap<Integer, JDialog> popUps = new HashMap<Integer, JDialog>();
    
    /** popup for customer */
    public static void customerDiscrepancyDetected(GUILogic guiLogic) {
        /* popupDialog = new JDialog(parentFrame);
        popUps.put(stationNumber, popupDialog);
        popupDialog.setUndecorated(true);
        popupDialog.setSize(800, 700);
        popupDialog.setLocationRelativeTo(parentFrame);

        JLabel label = new JLabel("Session Blocked. Attendant Notified.");

        // Set a larger font for the label
        Font font = new Font("Arial", Font.PLAIN, 24);
        label.setFont(font);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        popupDialog.add(label);

        popupDialog.setVisible(true);*/
        guiLogic.blockGUI();
    }
    
    /** popup for attendant */
    public static void discrepancyDetected(JFrame parentFrame, int stationNumber) {
        popupDialog = new JDialog(parentFrame);
        popupDialog.setSize(300, 200);
        popupDialog.setLocationRelativeTo(parentFrame);

        JLabel label = new JLabel("Weight issue at Station " + stationNumber);
        label.setFont(new Font("Sans", Font.BOLD, 18));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        popupDialog.add(label);

        popupDialog.setVisible(true);
    }
    
    public static void outOfOrder(JPanel parentPanel, int stationNumber) {
        popupDialog = new JDialog();
        popUps.put(stationNumber, popupDialog);
        popupDialog.setUndecorated(true);
        popupDialog.setSize(800, 700);
        popupDialog.setLocationRelativeTo(parentPanel);

        JLabel label = new JLabel("OUT OF ORDER");

        // Set a larger font for the label
        Font font = new Font("Arial", Font.PLAIN, 24);
        label.setFont(font);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        popupDialog.add(label);

        popupDialog.setVisible(true);
    }

    public static void removeOutOfOrder(int stationNumber) {
        popUps.get(stationNumber).dispose();
        popUps.remove(stationNumber);
    }


    public static void maintenanceRequired(JFrame parentFrame, String issue) {
        popupDialog = new JDialog(parentFrame);
        popupDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        popupDialog.setSize(800, 700);
        popupDialog.setLocationRelativeTo(parentFrame);

        JLabel label = new JLabel("MAINTENANCE REQUIRED FOR "+issue);
        label.setFont(new Font("Arial", Font.PLAIN, 24));

        popupDialog.add(label);

        popupDialog.setVisible(true);
    }

}
