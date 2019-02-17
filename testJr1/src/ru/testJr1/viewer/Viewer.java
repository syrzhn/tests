package ru.testJr1.viewer;

import java.util.ArrayList;
import java.util.List;

public abstract class Viewer {
	class Table {
		class TableString {
			String serialNumber;
			String dateCreation;
			String insurer;
			String prize;
			String actualTime;
		}
		List<TableString> tableStrings;
		public Table() {
			tableStrings = new ArrayList<>();
		}
		public void addStr() {
			tableStrings.add(new TableString());
		}
	}
	
	class FullView {
		String tender;
		String before;
		String until;
		List<String> realtyFactor;
		String currentRealtyFactor;
		String oldFactor;
		String squareFactor;
		String calculateDate;
		String prize;
		
		String serialNumber;
		String createDate;
		String fio;
		String birthDate;
		String passSerial;
		String passNumber;
		String state;
		String idx;
		String statcount;
		String district;
		String city;
		String street;
		String building;
		String structure;
		String house;
		String comment;
	}
	Table table;
	FullView fullView;
	public Viewer() {
		table = new Table();
		fullView = new FullView();
	}
}
