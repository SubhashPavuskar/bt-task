package com.assessment.fairbill;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FairBilling extends ValidateLogFile {

	private ArrayList<FairBillDetails> lOfBillRecords;

	public ArrayList<FairBillDetails> getlOfBillRecords() {
		return lOfBillRecords;
	}

	public void calculateBills(String filePath) throws FileNotFoundException {
		readFile(filePath);
		lOfBillRecords = new ArrayList<FairBillDetails>();
		Map<String, ArrayList<LoggingDetails>> mapOfLogRecords = getlogRecordMap();
		for (Map.Entry<String, ArrayList<LoggingDetails>> entry : mapOfLogRecords.entrySet()) {
			lOfBillRecords.add(calculateBill(entry.getValue()));
		}
	}

	private FairBillDetails calculateBill(List<LoggingDetails> lOfRecords) {
		ArrayList<Integer> startArray = new ArrayList<Integer>();
		ArrayList<Integer> endArray = new ArrayList<Integer>();
		for (LoggingDetails log : lOfRecords) {
			if (log.getLabel().equals(START)) {
				startArray.add(getSeconds(log.getTime()));
			} else if (log.getLabel().equals(END)) {
				endArray.add(getSeconds(log.getTime()));
				if (startArray.isEmpty() || (startArray.size() < endArray.size())) {
					startArray.add(getSeconds(getTimeOfFirstRecord()));
				}
			}
		}
		if (startArray.size() > endArray.size()) {
			while (startArray.size() != endArray.size()) {
				endArray.add(getSeconds(getTimeOfLastRecord()));
			}
		}
		int time = 0;
		for (int x = 0; x < startArray.size(); x++) {
			time = time + endArray.get(x) - startArray.get(x);
		}
		FairBillDetails billRecord = new FairBillDetails(lOfRecords.get(1).getName(), startArray.size(), time);
		return billRecord;
	}

}
