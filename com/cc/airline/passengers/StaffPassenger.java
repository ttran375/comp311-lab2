package com.cc.airline.passengers;

public class PassengerName {
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
