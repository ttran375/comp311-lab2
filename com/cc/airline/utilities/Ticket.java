package com.cc.airline.utilities;

import java.util.ArrayList;
import java.util.Random;

import com.cc.airline.utilities.SeatReserver;

class PassengerName {
	private String firstName;

	private String lastName;

	private String mInitial;

	public PassengerName(String first, String middle, String last) {
		firstName = first;
		mInitial = middle;
		lastName = last;
	}

	public PassengerName(String first, String last) {
		firstName = first;
		lastName = last;
	}

	public String toString() {
		String name = firstName;
		if (mInitial != null && mInitial.length() > 0) {
			name += " " + mInitial;
		}
		return name + " " + lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMInitial() {
		return mInitial;
	}

	public void setMInitial(String initial) {
		mInitial = initial;
	}

}

class Passenger {
	private PassengerName pName;

	public Passenger() {
		this.pName = new PassengerName("T.", "B.", "A.");
	}

	public Passenger(PassengerName pName) {
		this();
		this.pName = pName;
	}

	public PassengerName getPName() {
		return pName;
	}

	public void setPName(PassengerName name) {
		pName = name;
	}
}

class StaffPassenger extends Passenger
		implements Discountable {

	private String employeeId;

	public StaffPassenger(PassengerName pName, String employeeId) {
		super(pName);
		this.employeeId = employeeId;
	}

	public double disountPrice(double price) {
		return price / 2.0;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

}

class FrequentFlyer extends Passenger {

	private String fFlyerId;

	public FrequentFlyer(PassengerName pName, String fFlyerId) {
		super(pName);
		this.fFlyerId = fFlyerId;
	}

	public String getFFlyerId() {
		return fFlyerId;
	}

	public void setFFlyerId(String flyerId) {
		fFlyerId = flyerId;
	}
}

class SeatReserver {
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
					.getAnswerId();
			passenger = new FrequentFlyer(pName, fFlyerId);
		} else if (new UserPrompter("Are you an airline employee?")
				.getYesNoAnswer()) {
			String employeeId = new UserPrompter("Employee ID?").getAnswerId();
			passenger = new StaffPassenger(pName, employeeId);
		} else {
			passenger = new Passenger(pName);
		}
		return passenger;
	}

	private Seat assignSeat(SeatingClass sClass) {
		int seatNumber;
		// seat assignment from random number generator
		seatNumber = seatFinder.nextInt(sClass.getNumSeats()) + sClass.getIndexFirstSeat();
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
		sClass.setNumSeatsSold(sClass.getNumSeatsSold() + 1);
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

interface Discountable {
	double disountPrice(double price);
}

enum SeatingClass {
	BUSINESS(1, 2, 750.0),
	ECONOMY(3, 4, 500.0);

	private int rows;
	private int seatsAcross;
	private double price;
	private int numSeats, numSeatsSold;
	private int indexFirstSeat;
	private static int totalSeats = -1;

	private SeatingClass(int rows, int seatsAcross, double price) {
		this.rows = rows;
		this.seatsAcross = seatsAcross;
		this.price = price;
		numSeats = rows * seatsAcross;
		numSeatsSold = 0;
		indexFirstSeat = -1;
	}

	public double getPrice() {
		return price;
	}

	public int getNumSeats() {
		return numSeats;
	}

	public int getRows() {
		return rows;
	}

	public int getSeatsAcross() {
		return seatsAcross;
	}

	public int getIndexFirstSeat() {
		if (indexFirstSeat < 0) {
			setIndexSeats();
		}
		return indexFirstSeat;
	}

	public int getNumSeatsSold() {
		return numSeatsSold;
	}

	public void setNumSeatsSold(int numSeatsSold) {
		this.numSeatsSold = numSeatsSold;
	}

	public static int getTotalSeats() {
		if (totalSeats < 0) {
			setIndexSeats();
		}
		return totalSeats;
	}

	private static void setIndexSeats() {
		totalSeats = 0;
		for (int i = 0; i < values().length; i++) {
			SeatingClass sClass = values()[i];
			sClass.indexFirstSeat = totalSeats;
			totalSeats += sClass.getNumSeats();
		}
	}
}

class Seat {
	private int row;

	private char letter;

	private Ticket ticket;

	public String toString() {
		String seatName = new Integer(row).toString();
		seatName += letter;
		seatName += (ticket == null) ? " Available"
				: " "
						+ ticket.getPassenger().getPName();
		return seatName;
	}

	public char getLetter() {
		return letter;
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

}

class SeatingPlan {
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
		System.out.println("Seating plan has " + SeatingClass.getTotalSeats() + " seats.");
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

class Ticket {
	private Passenger passenger;

	private Seat seat;

	private double price;

	private long ticketNo;

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public double getPrice() {
		return price;
	}

	private static long count = 0;

	public Ticket(Passenger passenger, Seat seat, double price) {
		this.passenger = passenger;
		this.price = price;
		this.seat = seat;
		this.ticketNo = ++count + 1000000;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public long getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(long ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String toString() {
		return ticketNo + " for seat " + seat.getRow() + seat.getLetter() + " at $" + price;
	}
}
