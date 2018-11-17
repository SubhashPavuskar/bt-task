package com.assessment.fairbill;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class ValidateLogFile extends UtilitiesFairBill {
	
	private String timeOfFirstRecord = "";

	public String getTimeOfFirstRecord() {
		return timeOfFirstRecord;
	}

	private String timeOfLastRecord = "";
	
	public String getTimeOfLastRecord() {
		return timeOfLastRecord;
	}

	private Map<String, ArrayList<LoggingDetails>> logRecordMap;

	public Map<String, ArrayList<LoggingDetails>> getlogRecordMap() {
		return logRecordMap;
	}

	public void readFile(String filePath) throws FileNotFoundException {
		logRecordMap = new TreeMap<String, ArrayList<LoggingDetails>>();
		String latestLogRecord ="";
		Scanner in = new Scanner(new FileReader(filePath));
		while (in.hasNextLine()) {
			String line = in.nextLine().trim();
			LoggingDetails log = parseLine(line);
			if (log.isValid()) {
				latestLogRecord = log.getTime();
				addTologRecordMap(log);
			}
		}
		timeOfLastRecord = latestLogRecord;
		in.close();
	}

	private void addTologRecordMap(LoggingDetails log) {
		if (logRecordMap.isEmpty()) {
			timeOfFirstRecord = log.getTime();
		}
		if (logRecordMap.containsKey(log.getName())) {
			ArrayList<LoggingDetails> lOflogRecord = logRecordMap.get(log.getName());
			lOflogRecord.add(log);
		} else {
			ArrayList<LoggingDetails> lOflogRecord = new ArrayList<LoggingDetails>();
			lOflogRecord.add(log);
			logRecordMap.put(log.getName(), lOflogRecord);
		}
	}

	private LoggingDetails parseLine(String line) {
		LoggingDetails log = new LoggingDetails();
		String[] fields = getfields(line);
		if (fields.length != 3) {
			return log;
		}
		if (parseTime(fields[0])) {
			log.setTime(fields[0]);
		}
		log.setName(fields[1]);
		if (fields[2].equals(START)) {
			log.setLabel(START);
		} else if (fields[2].equals(END)) {
			log.setLabel(END);
		}
		return log;
	}

	private String[] getfields(String line) {
		String[] fields = new String[3];
		Scanner scan = new Scanner(line);
		int index = 0;
		try {
			while (scan.hasNext()) {
				String field = scan.next();
				fields[index] = field;
				index++;
			}
		} catch (IndexOutOfBoundsException e) {
			return new String[1];
		} finally {
			scan.close();
		}
		return fields;
	}

	private boolean parseTime(String time) {
		if (Pattern.matches("^([0-1]?[0-9]|[2][0-3]):([0-5][0-9])(:[0-5][0-9])?$", time)) {
			return true;
		} else {
			return false;
		}
	}

}
