package com.thelocalmarketplace.software.controllers;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;

import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.printer.ReceiptPrinterListener;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.software.AbstractLogicDependant;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.StateLogic.States;


/**
 * Adapted from Project Iteration 2 - Group 5
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
public class ReceiptPrintingController extends AbstractLogicDependant implements ReceiptPrinterListener {
	
	// A duplicate receipt that can be printed by the attendant.
	String duplicateReceipt;
	
	// Flags to see if ink or paper is low or empty
	boolean inkLow = false;
	boolean paperLow = false;
	
	/**
	 * Base constructor
	 */
    public ReceiptPrintingController(CentralStationLogic logic) throws NullPointerException {
    	super(logic);
    	
    	this.duplicateReceipt = "";
    	this.logic.hardware.getPrinter().register(this);
    }
    
    /**
     * Generates a string that represents a receipt to be printed
     * @return The receipt as a string.
     */
    public String createPaymentRecord(BigDecimal change) {
        StringBuilder paymentRecord = new StringBuilder();

        Map<Product, Float> cartItems = this.logic.cartLogic.getCart();
        BigDecimal totalCost = BigDecimal.ZERO; 
        String membershipNumber = this.logic.membershipLogic.getMembershipNumber();
        String membershipName = this.logic.membershipLogic.getAccountName();

        //Begin the receipt.
        paymentRecord.append("Customer Receipt\n");
        paymentRecord.append("=========================\n");
        
        int i = 0;
        // Iterate through each item in the cart, adding printing them on the receipt.
        for (Entry<Product, Float> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            Float quantity = entry.getValue();
            
            BigDecimal price = new BigDecimal(product.getPrice());
            BigDecimal totalItemCost = price.multiply(new BigDecimal(quantity));
            totalCost.add(totalItemCost);
            paymentRecord.append("Item " + ++i + ":\n");
            paymentRecord.append(" - Qty: ");
            paymentRecord.append(quantity);
            paymentRecord.append(", Unit Price: $");
            paymentRecord.append(price);
            paymentRecord.append(", Total: $");
            paymentRecord.append(totalItemCost);
            paymentRecord.append("\n");
            
        }

        paymentRecord.append("=========================\n");
        paymentRecord.append("Membership Number: ").append(membershipNumber).append("\n");
        paymentRecord.append("Total Cost: $").append(totalCost).append("\n");
        paymentRecord.append("Change Given: $").append(change.toString()).append("\n");
        
        System.out.print(paymentRecord);
        
        return paymentRecord.toString();
    }
    
    /**Generates receipt and calls receipt printing hardware to print it.
     * @param change
     */
    public void handlePrintReceipt(BigDecimal change) {
        String receiptText = createPaymentRecord(change);
        
        try {        	
        	this.printReceipt(receiptText);
        	this.finish();
        }
        catch (Exception e) {
        	this.onPrintingFail();
        	this.duplicateReceipt = receiptText;
        	
        }
    }

    /**
     * Helper method for printing receipt
     * @param receiptText Is the string to print
     * @throws OverloadedDevice 
     * @throws EmptyDevice 
     */
	private void printReceipt(String receiptText) throws EmptyDevice, OverloadedDevice {
		for (char c : receiptText.toCharArray()) {
	    	this.logic.hardware.getPrinter().print(c);
	    }
	    
	    this.logic.hardware.getPrinter().cutPaper(); 
	}
	
	
	/**
	 * Prints out a duplicate receipt. Only meant to be used by the attendant.
	 * Returns the machine to normal as there is no longer a receipt that hasn't been printed.
	 */
	public void printDuplicateReceipt() {
		try {
			// Try to print out the receipt once more.
			this.printReceipt(duplicateReceipt);
			// Returns the machine to normal as the receipt is printed and the machine can resume.
			this.logic.stateLogic.gotoState(States.NORMAL);
			// Removes the receipts as it is no longer needed.
			this.duplicateReceipt = "";
		} catch (Exception e) {
			// If the receipt fails to print again, this will be called.
			this.onPrintingFail();
		}
	}
	
	/**
	 * Executes after a receipt is successfully printed
	 */
	private void finish() {
		
		// Thank customer
        System.out.println("Thank you for your business with The Local Marketplace");
        
        // End session
        this.logic.stopSession();
    }
	
	/**
	 * Executes if a receipt fails to print. Prints message to console and sets the state control software to suspended.
	 */
	private void onPrintingFail() {
		System.out.println("Failed to print receipt");
		
		// Suspend station
		this.logic.stateLogic.gotoState(States.SUSPENDED);
	}
	
	@Override
	public void thePrinterIsOutOfPaper() {
		// TODO: Notify Attendant
		
		// TODO: Change GUI display message about no Paper
		System.out.println("Paper Storage Empty. System shutting off.");
		
		// Flag to see if paper remains empty
		paperLow = true;
		
		// Disable System so attendant can add paper
		if	(!inkLow) {
			this.logic.attendantLogic.disableCustomerStation();
		}
		
	}

	@Override
	public void thePrinterIsOutOfInk() {
		// TODO: Notify Attendant
		
		// TODO: Change GUI display message about no Ink
		System.out.println("Ink Storage Empty. System shutting off.");
		
		// Flag to see if Ink remains empty
		inkLow = true;
		
		// Disable System so attendant can add Ink
		if	(!paperLow) {
			this.logic.attendantLogic.disableCustomerStation();
		}
	}

	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void thePrinterHasLowInk() {
		// TODO: Notify Attendant
		
		// TODO: Change GUI display message about no Ink
		System.out.println("Ink Level is low. System shutting off.");
		
		// Flag to see if paper remains low
		paperLow = true;
		
		
		// Disable System so attendant can add paper
		this.logic.hardware.turnOff();
		
		//System.out.println("Does it reach here?");
	}

	@Override
	public void thePrinterHasLowPaper() {
		// TODO: Notify Attendant
		
		// TODO: Change GUI display message about no Paper
		System.out.println("Paper Level is low. System shutting off.");
		
		// Flag to see if paper remains low
		paperLow = true;
		
		// Disable System so attendant can add paper
		this.logic.attendantLogic.disableCustomerStation();
		
	}

	@Override
	public void paperHasBeenAddedToThePrinter() {
		
		// Set flag to false
		paperLow = false;
		
		// TODO: Change GUI display message about paper being added
		System.out.println("Paper has been added");
		
		// Check if ink is low or empty
		if (!inkLow) {
			// TODO: Change GUI display message about printer ready
			System.out.println("Printer ready for usage");
			
			// If enough ink is there enable system
			this.logic.attendantLogic.enableCustomerStation();
		} else {
			// TODO: Change GUI display message about printer not ready due to low ink levels
			System.out.println("Printer not ready for usage; Low Ink; Add Ink");
			
			// TODO: Notify Attendant
		}
		
	}

	@Override
	public void inkHasBeenAddedToThePrinter() {
		
		// Set flag to false
		inkLow = false;
		
		// TODO: Change GUI display message about paper being added
		System.out.println("Ink has been added");
		
		// Check if paper is low or empty
		if (!paperLow) {
			// TODO: Change GUI display message about printer ready
			System.out.println("Printer ready for usage");
			
			// If enough ink is there enable system
			this.logic.attendantLogic.enableCustomerStation();
		} else {
			// TODO: Change GUI display message about printer not ready due to low paper levels
			System.out.println("Printer not ready for usage; Low Paper; Add Paper");
			
			// TODO: Notify Attendant
		}

	}
	
	public boolean getInkLow() {
		return inkLow;
	}
	
	public boolean getPaperLow() {
		return paperLow;
	}
}
