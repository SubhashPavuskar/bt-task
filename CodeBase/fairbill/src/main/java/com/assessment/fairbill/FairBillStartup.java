package com.assessment.fairbill;

import java.util.ArrayList;


//This Class is the start up which loads the log file  
public class FairBillStartup {

	public static void main(String[] args) {
		try {
			FairBilling fairBilling = new FairBilling();
			fairBilling.calculateBills(args[0]);
			ArrayList<FairBillDetails> lOfBills = fairBilling.getlOfBillRecords();
			for (FairBillDetails br : lOfBills) {
				System.out.println(br.getName() + " " + br.getNoOfSessions() + " " + br.getTotalTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
