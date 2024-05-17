package com.cc.airline.ticketing;

import java.util.ArrayList;

import com.cc.airline.utilities.SeatReserver;

public class SeatingPlan {
	private ArrayList<Seat> seats;

	private SeatReserver seatReserver;

	int totalRows = 0;

	private static String seatLetters = "ABCDEFGHJK";

	public SeatingPlan() {
		seats = new ArrayList<Seat>();
		Seat seat;
		for (SeatingClass sClass : SeatingClass.values()) {
			for (int r = 0; r < sClass.getRows(); r++) {
				totalRows++;
				for (int s = 0; s < sClass.getSeatsAcross(); s++) {
					seat = new Seat();
					seat.setRow(totalRows);
					seat.setLetter(seatLetters.charAt(s));
					seats.add(seat);
				}
			}
		}		
		System.out.println ("Seating plan has " + SeatingClass.getTotalSeats() + " seats.");
		seatReserver = new SeatReserver(this);
	}

	public int getNumBusSeats() {
		return SeatingClass.BUSINESS.getRows()
				* SeatingClass.BUSINESS.getSeatsAcross();
	}

	public int getTotalRows() {
		return totalRows;
	}

	public int getTotalSeats() {
		return SeatingClass.getTotalSeats();
	}

	public SeatReserver getSeatReserver() {
		return seatReserver;
	}

	public ArrayList<Seat> getSeats() {
		return seats;
	}

}
