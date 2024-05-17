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
