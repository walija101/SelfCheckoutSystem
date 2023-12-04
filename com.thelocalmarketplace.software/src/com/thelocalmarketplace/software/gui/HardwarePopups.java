package com.thelocalmarketplace.software.gui;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.logic.CentralStationLogic;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HardwarePopups {

    private CentralStationLogic centralStationLogic;
    private static BarcodedItem Apple = new BarcodedItem(new Barcode(new Numeral[]{Numeral.one,Numeral.two, Numeral.three}), new Mass(10));
    private static BarcodedItem Orange = new BarcodedItem(new Barcode(new Numeral[]{Numeral.three,Numeral.two, Numeral.three}), new Mass(10));

    public HardwarePopups(CentralStationLogic centralStationLogic) {
    	
    	this.centralStationLogic = centralStationLogic;
    	
    }

//	public static void showScanMainScannerPopup(JFrame parentFrame) {
//		JDialog dialog = createDialog(parentFrame, "Scan Main Scanner");
//		JTextField textField = addTextField(dialog, "Scan barcode using the main scanner:");
//		String barcode;
//		Consumer<String> onSubmit = inputText -> {
//			
//		};			
//		addSubmitButton(dialog, textField, onSubmit);
//		showDialog(dialog);
//	}
	
	public void showScanMainScannerPopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Scan Main Scanner");
		JButton apple = new JButton("Apple");
		JButton orange = new JButton("Orange");
		JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        apple.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	centralStationLogic.hardware.getMainScanner().scan(Apple);
            	//idk how to make the panel close
            	parentFrame.remove(panel);
            	}
        });
        orange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	centralStationLogic.hardware.getMainScanner().scan(Orange);
            }
        });
        
        dialog.add(panel);
        panel.add(apple);
        panel.add(orange);
		showDialog(dialog);
	}

	public void showScanHandheldScannerPopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Scan Handheld Scanner");
		JButton apple = new JButton("Apple");
		JButton orange = new JButton("Orange");

        apple.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	centralStationLogic.hardware.getHandheldScanner().scan(Apple);
            }
        });
        orange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	centralStationLogic.hardware.getHandheldScanner().scan(Orange);
            }
        });
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        dialog.add(panel);
        panel.add(apple);
        panel.add(orange);
		showDialog(dialog);
	}


	public void showAddItemToScalePopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Add an item to the scale");
		JButton apple = new JButton("Apple");
		JButton orange = new JButton("Orange");

        apple.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	centralStationLogic.hardware.getBaggingArea().addAnItem(Apple);
            }
        });
        orange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	centralStationLogic.hardware.getBaggingArea().addAnItem(Orange);            }
        });
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        dialog.add(panel);
        panel.add(apple);
        panel.add(orange);
		showDialog(dialog);
	}

	public static void showRemoveItemFromScalePopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Remove Item from Scale");
		JTextField textField = addTextField(dialog, "Remove item from scale:");
		Consumer<String> onSubmit = inputText -> {
            Mass weight = new Mass(Double.parseDouble(inputText));
            //guiLogic.removeItemFromScale(weight);
		};
		addSubmitButton(dialog, textField, onSubmit);
		showDialog(dialog);
	}

	public static void showPayWithCreditPopup(JFrame parentFrame, boolean real) {
		JDialog dialog = createDialog(parentFrame, real ? "Pay With Real Credit" : "Pay With Fake Credit");
		JTextField textField = addTextField(dialog, "Enter credit card details:");
		Consumer<String> onSubmit = inputText -> {
			int cardNumber = Integer.parseInt(inputText);
			if (real) {
				//guiLogic.payWithCredit(cardNumber);
			} else {
				//TODO: can we just have an error message here?
			}
		};
		addSubmitButton(dialog, textField, onSubmit);
		showDialog(dialog);
	}

	public static void showPayWithDebitPopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Pay With Debit");
		JTextField textField = addTextField(dialog, "Enter debit card details:");
		Consumer<String> onSubmit = inputText -> {
            int cardNumber = Integer.parseInt(inputText);
            //guiLogic.payWithDebit(cardNumber);
		};
		addSubmitButton(dialog, textField, onSubmit);
		showDialog(dialog);
	}

	public static void showInsertCoinPopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Insert Coin");
		JTextField textField = addTextField(dialog, "Enter coin value:");
		Consumer<String> onSubmit = inputText -> {
			int coinValue = Integer.parseInt(inputText);
            //guiLogic.insertCoin(coinValue);
		};
		addSubmitButton(dialog, textField, onSubmit);
		showDialog(dialog);
	}

	public static void showInsertBanknotePopup(JFrame parentFrame) {
		JDialog dialog = createDialog(parentFrame, "Insert Banknote");
		JTextField textField = addTextField(dialog, "Enter banknote value:");
		Consumer<String> onSubmit = inputText -> {
            int banknoteValue = Integer.parseInt(inputText);
            //guiLogic.insertBanknote(banknoteValue);
		};
		addSubmitButton(dialog, textField, onSubmit);
		showDialog(dialog);
	}

	// Pop-up creation logic
	
	private static void addSubmitButton(JDialog dialog, JTextField textField, Consumer<String> onSubmit) {
	    JButton submitButton = new JButton("Submit");
	    submitButton.addActionListener(e -> {
	        String inputText = textField.getText();
	        onSubmit.accept(inputText); // Calls the passed consumer function with the text as input
	        dialog.dispose();
	    });
	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    buttonPanel.add(submitButton);
	    dialog.add(buttonPanel, BorderLayout.PAGE_END);
	}

	private static JDialog createDialog(JFrame parentFrame, String title) {
		JDialog dialog = new JDialog(parentFrame, title, true);
		dialog.setLayout(new BorderLayout());
		dialog.setSize(new Dimension(300, 200));
		dialog.setLocationRelativeTo(parentFrame);
		return dialog;
	}

    private static JTextField addTextField(JDialog dialog, String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT); 
        JTextField textField = new JTextField(20);
        panel.add(label);
        panel.add(Box.createVerticalStrut(5)); 
        panel.add(textField); 
        dialog.add(panel, BorderLayout.CENTER);
        return textField;
    }

	private static void showDialog(JDialog dialog) {
		dialog.setVisible(true);
	}
}
