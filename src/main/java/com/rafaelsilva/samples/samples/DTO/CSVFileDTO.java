package com.rafaelsilva.samples.samples.DTO;

import com.opencsv.bean.CsvBindByName;

public class CSVFileDTO {

	private String fileName;
	@CsvBindByName(column = "A")
	private String column_1;
	@CsvBindByName(column = "B")
	private String column_2;
	@CsvBindByName(column = "C")
	private String column_3;
	@CsvBindByName(column = "D")
	private String column_4;
	@CsvBindByName(column = "E")
	private String column_5;
	@CsvBindByName(column = "F")
	private String column_6;
	@CsvBindByName(column = "G")
	private String column_7;
	@CsvBindByName(column = "H")
	private String column_8;

	public CSVFileDTO() {

	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getColumn_1() {
		return column_1;
	}

	public void setColumn_1(String column_1) {
		this.column_1 = column_1;
	}

	public String getColumn_2() {
		return column_2;
	}

	public void setColumn_2(String column_2) {
		this.column_2 = column_2;
	}

	public String getColumn_3() {
		return column_3;
	}

	public void setColumn_3(String column_3) {
		this.column_3 = column_3;
	}

	public String getColumn_4() {
		return column_4;
	}

	public void setColumn_4(String column_4) {
		this.column_4 = column_4;
	}

	public String getColumn_5() {
		return column_5;
	}

	public void setColumn_5(String column_5) {
		this.column_5 = column_5;
	}

	public String getColumn_6() {
		return column_6;
	}

	public void setColumn_6(String column_6) {
		this.column_6 = column_6;
	}

	public String getColumn_7() {
		return column_7;
	}

	public void setColumn_7(String column_7) {
		this.column_7 = column_7;
	}

	public String getColumn_8() {
		return column_8;
	}

	public void setColumn_8(String column_8) {
		this.column_8 = column_8;
	}

	public boolean isAllPropertiesNull() {
		return (column_1 == null || column_1.trim().isEmpty());
	}
}
