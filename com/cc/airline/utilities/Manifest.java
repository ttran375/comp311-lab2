package com.cc.airline.utilities;

import java.util.ArrayList;

import com.cc.airline.ticketing.Seat;
import com.cc.airline.ticketing.SeatingClass;
import com.cc.airline.ticketing.SeatingPlan;

public class Manifest {
	SeatingPlan plan;

	public Manifest(SeatingPlan plan) {
		this.plan = plan;
	}

	private void printOut() {
		ArrayList<Seat> seats = plan.getSeats();
		System.out.println("Flight manifest");
		for (Seat s: seats) {
			System.out.println(s);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Flight open for ticket sales.");
		SeatingPlan plan = new SeatingPlan();
		Manifest manifest = new Manifest(plan);
		SeatReserver reserver = plan.getSeatReserver();
		int seatsSold = 0;
		UserPrompter prompter = new UserPrompter("Do you want to purchace a ticket?");
		while ( prompter.getYesNoAnswer() && seatsSold < plan.getTotalSeats() ) {
			for ( int c = 0; c < SeatingClass.values().length; c++ ) {
				SeatingClass sClass = SeatingClass.values()[c];
				if ( sClass.getNumSeatsSold() < sClass.getNumSeats() ){
					UserPrompter prompt2 = new UserPrompter(" Do you want " + sClass + " class? " );
					if ( prompt2.getYesNoAnswer()) {
						reserver.sellTicket(sClass);
						break;
					}
				}
			}
		}
		manifest.printOut();
	}
}