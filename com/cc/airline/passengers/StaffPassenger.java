package com.cc.airline.passengers;

import com.cc.airline.ticketing.Discountable;

public class StaffPassenger extends Passenger 
        implements Discountable {
	
	private String employeeId;
	
	public StaffPassenger( PassengerName pName, String employeeId) {
		super(pName);
		this.employeeId = employeeId;
	}
	
	public double disountPrice(double price) {
		return price/2.0;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

}
