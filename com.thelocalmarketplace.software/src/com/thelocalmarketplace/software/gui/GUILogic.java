

package com.thelocalmarketplace.software.gui;

import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.software.database.CreateTestDatabases;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/*
 * This is where Project 3 Logic will be entered 
 * Please do not insert Logic into RunGUI (which should only contain
 * GUI code) and Panels
 */
public class GUILogic {
	private final Set<GUIListener> listeners = new HashSet<>();

	/**
	 * Registers the given listener so that it will receive events from this
	 * communication facade.
	 *
	 * @param listener
	 *            The listener to be registered. If it is already registered, this
	 *            call has no effect.
	 */
	public void register(GUIListener listener) {
		listeners.add(listener);
	}

// LOGIC PANEL
	// add logic for when customer adds item via PLU/Visual Search
	private void notifyItemAdded(PLUCodedProduct pk) {
		for(GUIListener listener : listeners)
			listener.added(this, pk);
	}

	// add logic for when customer scans a barcoded item
	private void notifyItemScanned(BarcodedProduct pk) {
		for(GUIListener listener : listeners)
			listener.scanned(this, pk);
	}

	// add logic for when customer indicates to add their own bags
	private void notifyOwnBags(boolean ownBags) {
		for(GUIListener listener : listeners)
			listener.ownBags(this, ownBags);
	}

	// add logic for when customer wants to call an attendant
	public void callAttendant() {
		for(GUIListener listener : listeners)
			listener.attendantCalled(this);
	}

	// add logic for when customer indicates to add their membership card/id (? might need to be changed)
	// string changed to whatever the membership identifier is
	private void addMembership(String id) {
		for(GUIListener listener : listeners)
			listener.memberLogin(this, id);
	}

	// add logic for when customer chooses how they would like to pay
	private void selectedPayOption(int payOption) {
		for(GUIListener listener : listeners)
			listener.payOption(this, payOption);
	}
	
//----------------------------------------------------------------
//Start Session Panel, 
	
	//when Customer presses [Start Session] 
	public void StartSessionButtonPressed() {
		System.out.println("Start Session");
		new CreateTestDatabases();
	}
	
//----------------------------------------------------------------
//Add Items Panel


	
	// RIGHT BUTTON PANEL 
	public void buttonR1_AddMemberNoButton() {
        System.out.println("Add Member #");
        //Logic Here
	}
	
	public void buttonR2_SignUpForMembershipButton() {
        System.out.println("Sign Up For Membership");
        //Logic Here
	}
	
	public void buttonR3_CustomerCallsAttendant() {
        System.out.println("CustomerCallsAttendant");
        //Logic Here
	}
	
	public void buttonR4_CustomerAddsOwnBag() {
        System.out.println("buttonR4_CustomerAddsOwnBag"); 
        //Logic Here
	}
	
	public void buttonR5_CustomerWantstoRemoveItem() {
        System.out.println("buttonR5_CustomerWantstoRemoveItem"); 
        //Logic Here
	}
	
	public void buttonR6_BLANK() {
        System.out.println("buttonR6_BLANK");       
        //Logic Here
	}
	
	/*
	 * return the string that will be displayed in GUI "Receipt"
	 */
	public String buttonR7_CustomerAddsItem_PLUCode() {
        System.out.println("buttonR7_CustomerAddsItem_PLUCode");
        //Logic Here
        //Example Code Here 
        String addItemPLU_result = "New Item thru PLU Code";
		return addItemPLU_result;
	}
	
	/*
	 * return the string that will be displayed in GUI "Receipt"
	 */
	public String buttonR8_CustomerAddsItem_VisualCatalogue() {
        System.out.println("buttonR8_CustomerAddsItem_VisualCatalogue");
        //Logic Here
        //Example Code Here 
        String addItemVC_result = "New Item thru Visual Catalogue";
		return addItemVC_result;
	}
	
	// This will switch to the Payment Panel
	public void buttonR9_CustomerWantsToPay() {
        System.out.println("buttonR9_CustomerWantsToPay!");
        //Logic Here
	}
	
//----------------------------------------------------------------
// Add Items Panel Pop Up 
	
	public String addItemPopUp_button1_CustomersAddsToBaggingArea() {
        System.out.println("addItemPopUp_button1_CustomersAddsToBaggingArea");
        //Example Code Here 
        // Logic Here 
        String addItemB1_result = "addItemPopUp_button1_CustomersAddsToBaggingArea";
		return addItemB1_result;
	}
	
	public String addItemPopUp_button2_CustomersDOESNOTAddsToBaggingArea() {
        System.out.println("addItemPopUp_button1_CustomersAddsToBaggingArea");
        //Example Code Here 
        // Logic Here 
        String addItemB1_result = "addItemPopUp_button1_CustomersAddsToBaggingArea";
		return addItemB1_result;
	}
	
	public String addItemPopUp_button3_BLANK() {
        System.out.println("Extra Button");
        //Example Code Here 
        // Logic Here 
        String addItemB1_result = "addItemPopUp_button3_BLANK - Extra Button";
		return addItemB1_result;
	}
	
//----------------------------------------------------------------
//Add Items Panel 	
	
	//BOTTOM PANEL
	public String buttonB1_CustomerScansBarcodedProduct_MainScanner() {
        System.out.println("buttonB1_CustomerScansBarcodedProduct_MainScanner");
        //Example Code Here 
        // Logic Here 
        String addItemB1_result = "New Barcoded Product thru Main Scanner";
		return addItemB1_result;
	}
	
	public String buttonB2_CustomerScansBarcodedProduct_HandheldScanner() {
        System.out.println("buttonB2_CustomerScansBarcodedProduct_HandheldScanner");
        //Example Code Here 
        // Logic Here 
        String addItemB2_result = "New Barcoded Product thru Handheld Scanner";
		return addItemB2_result;
	}
	
	public String buttonB3_CustomerScansBarcodedProduct_RFIDTag() {
        System.out.println("buttonB3_CustomerScansBarcodedProduct_RFIDTag");
        //Example Code Here 
        // Logic Here 
        String addItemB3_result = "New Barcoded Product thru RFID Tag";
		return addItemB3_result;
	}
	
	
	
	

	
	
	
	
	
	
	
//----------------------------------------------------------------
// FOR TESTING PURPOSES - DO NOT SUBMIT FOR PROJECT 3 

	public int total = 0; 
	
	//Observer Design 
	private List<logicObserver> observers = new ArrayList<>();
	
	// Observer Methods Below 
    public void addObserver(logicObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(logicObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (logicObserver observer : observers) {
            observer.updateTotal(total);
        }
    }
	
	
	// Logic Methods Below 
	public void printMeow(){
		System.out.println("Meow");
	}
	
	public void initTotal() {
		total = 0;
		System.out.println("The total is "+total);
	}
	
	public int addOneTotal() {
		total = total +1;
		System.out.println("The total is "+total);
		return total;
	}
	
	public int getTotal() {
		System.out.println("The total is "+total);
		return total;
	}
	
	public void addtoTotal(int meow) {
		total = total + meow;
		System.out.println("The total is "+total);
	}
	
	public void subtractTotal(int meow) {
		total = total - meow;
		System.out.println("The total is "+total);
	}
}

