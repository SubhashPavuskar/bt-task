package com.assessment.fairbill;

public class FairBillDetails {
	private String name;
	private int noOfSessions;
	private int totalTime;

	public FairBillDetails(String name, int noOfSessions, int totalTime) {
		super();
		this.name = name;
		this.noOfSessions = noOfSessions;
		this.totalTime = totalTime;
	}

	public String getName() {
		return name;
	}

	public int getNoOfSessions() {
		return noOfSessions;
	}

	public int getTotalTime() {
		return totalTime;
	}

	@Override
	public String toString() {
		return "BillRecord [name=" + name + ", noOfSessions=" + noOfSessions + ", totalTime=" + totalTime + "]";
	}

}
