package com.rafaelsilva.springbootsamples.samples.data.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "FILES_GENERATED")
public class FileGeneratedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "CSV_FILE_NAME")
	private String csvFileName;
	
	@Column(name = "FILE_NAME")
	private String fileName;

	@Column(name = "CONTENT")
	private String fileContent;

	@Column(name = "DATE_CREATION")
	@CreationTimestamp
	private Timestamp fileDateCreation;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCsvFileName() {
		return csvFileName;
	}

	public void setCsvFileName(String csvFileName) {
		this.csvFileName = csvFileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public Timestamp getFileDateCreation() {
		return fileDateCreation;
	}

	public void setFileDateCreation(Timestamp fileDateCreation) {
		this.fileDateCreation = fileDateCreation;
	}

	@Override
	public String toString() {
		return "FileGeneratedEntity [id=" + id + ", csvFileName=" + csvFileName + ", fileName=" + fileName
				+ ", fileContent=" + fileContent + ", fileDateCreation=" + fileDateCreation + "]";
	}

}
