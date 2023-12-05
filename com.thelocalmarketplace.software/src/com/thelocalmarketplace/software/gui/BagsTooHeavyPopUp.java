/** SENG300 Group Project
 * (Adapted from Project Iteration 2 - Group 5)
 *
 * Iteration 3 - Group 3
 * @author Jaimie Marchuk - 30112841
 * @author Wyatt Deichert - 30174611
 * @author Jane Magai - 30180119
 * @author Enzo Mutiso - 30182555
 * @author Mauricio Murillo - 30180713
 * @author Ahmed Ibrahim Mohamed Seifeldin Hassan - 30174024
 * @author Aryaman Sandhu - 30017164
 * @author Nikki Kim - 30189188
 * @author Jayden Ma - 30184996
 * @author Braden Beler - 30084941
 * @author Danish Sharma - 30172600
 * @author Angelina Rochon - 30087177
 * @author Amira Wishah - 30182579
 * @author Walija Ihsan - 30172565
 * @author Hannah Pohl - 30173027
 * @author Akashdeep Grewal - 30179657
 * @author Rhett Bramfield - 30170520
 * @author Arthur Huan - 30197354
 * @author Jaden Myers - 30152504
 * @author Jincheng Li - 30172907
 * @author Anandita Mahika - 30097559
 */


package com.thelocalmarketplace.software.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BagsTooHeavyPopUp {

	public BagsTooHeavyPopUp() {
		
	}
	
	public void notifyBagHeavyPopUp() {
		JFrame bagsPopUp = new JFrame("Bags Too Heavy");
		bagsPopUp.setSize(400, 125);
		bagsPopUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bagsPopUp.setLocation(1025, 400);
    
		JPanel popupPanel = createLabelPanel("A Customer's Bags are Too Heavy", 40, 50);
		JPanel buttonPanel = new JPanel();
		JButton confirmButton2 = new JButton("Confirm");
		confirmButton2.addActionListener(e -> handleButtonClick(12));
		buttonPanel.add(confirmButton2);
		bagsPopUp.add(popupPanel, BorderLayout.NORTH);
		bagsPopUp.add(buttonPanel, BorderLayout.CENTER);
    
		bagsPopUp.setVisible(true);
	}
	
	private void handleButtonClick(int buttonNumber) {
        switch (buttonNumber) {
            case 12:
                //NotifyPopUp.notifyPopUp.setVisible(false);
                break;
        }
	}
	
	private JPanel createLabelPanel(String labelText, int width, int height) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(width, height));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label, gbc);
        return panel;
    }

}
