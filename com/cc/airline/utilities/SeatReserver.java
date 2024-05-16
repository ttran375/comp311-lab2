package com.cc.airline.utilities;

import java.util.ArrayList;
import java.util.Random;

import com.cc.airline.passengers.FrequentFlyer;
import com.cc.airline.passengers.Passenger;
import com.cc.airline.passengers.PassengerName;
import com.cc.airline.passengers.StaffPassenger;
import com.cc.airline.ticketing.Discountable;
import com.cc.airline.ticketing.Seat;
import com.cc.airline.ticketing.SeatingClass;
import com.cc.airline.ticketing.SeatingPlan;
import com.cc.airline.ticketing.Ticket;

public class SeatReserver {
	private Random seatFinder;

	private SeatingPlan plan;

	private int numSeats, numSeatsSold;

	public SeatReserver(SeatingPlan plan) {
		this.plan = plan;
		seatFinder = new Random(this.hashCode());
		numSeats = plan.getTotalSeats();
		numSeatsSold = 0;
	}

	private Passenger getPassenger() {
		Passenger passenger = null;
		String firstName = new UserPrompter("First name?").getAnswer();
		String lastName = new UserPrompter("Last name?").getAnswer();
		String initial = new UserPrompter("Initial?").getAnswer();
		PassengerName pName = new PassengerName(firstName, initial, lastName);
		if (new UserPrompter("Are you a frequent flyer?").getYesNoAnswer()) {
			String fFlyerId = new UserPrompter("Frequent flyer number?")
					.getAnswer();
			passenger = new FrequentFlyer(pName, fFlyerId);
		} else if (new UserPrompter("Are you an airline employee?")
				.getYesNoAnswer()) {
			String employeeId = new UserPrompter("Employee ID?").getAnswer();
			passenger = new StaffPassenger(pName, employeeId);
		} else {
			passenger = new Passenger(pName);
		}
		return passenger;
	}

	private Seat assignSeat(SeatingClass sClass) {
		int seatNumber;
		// seat assignment from random number generator
		seatNumber = seatFinder.nextInt(sClass.getNumSeats()) +  sClass.getIndexFirstSeat();
		ArrayList<Seat> seats = plan.getSeats();
		// random numbers may issue same seat twice.
		// if that happens take first available seat in section
		Seat seat = seats.get(seatNumber);
		if (seat.getTicket() != null) {
			seat = findFirstEmptySeat(seats, sClass);
			if (seat == null) {
				return null;
			}
		}
		numSeatsSold++;
		sClass.setNumSeatsSold(sClass.getNumSeatsSold()+ 1);
		return seat;
	}

	private Seat findFirstEmptySeat(ArrayList<Seat> seats, SeatingClass sClass) {
		Seat seat = null;
		int firstSeat = 0;
		for (int i = 0; i < sClass.getNumSeats(); i++) {
			seat = seats.get(i + firstSeat);
			if (seat.getTicket() == null) {
				return seat;
			}
		}
		return null;
	}

	public boolean sellTicket(SeatingClass sClass) {
		double price = sClass.getPrice();
		if (numSeats == numSeatsSold) {
			System.out.println("This flight is sold out.");
			return false;
		}
		Passenger passenger = getPassenger();
		Seat seat = assignSeat(sClass);
		if (seat == null) {
			System.out.println("Unable to assign seat, flight closed.");
			return false;
		}
		if (passenger instanceof Discountable) {
			price = ((Discountable) passenger).disountPrice(price);
		}
		Ticket ticket = new Ticket(passenger, seat, price);
		// important step omitted here
		System.out.println("Ticket issued: " + ticket);
		System.out.println();
		return true;
	}
}