package com.rafaelsilva.springbootsamples.samples.dto;

public class CustomFilesDTO {

	private String customFileName;
	private String customFileContent;
	
	public CustomFilesDTO() {
		
	}

	public CustomFilesDTO(String customFileName, String customFileContent) {
		super();
		this.customFileName = customFileName;
		this.customFileContent = customFileContent;
	}

	public String getCustomFileName() {
		return customFileName;
	}

	public void setCustomFileName(String customFileName) {
		this.customFileName = customFileName;
	}

	public String getCustomFileContent() {
		return customFileContent;
	}

	public void setCustomFileContent(String customFileContent) {
		this.customFileContent = customFileContent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customFileName == null) ? 0 : customFileName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomFilesDTO other = (CustomFilesDTO) obj;
		if (customFileName == null) {
			if (other.customFileName != null)
				return false;
		} else if (!customFileName.equals(other.customFileName))
			return false;
		return true;
	}

}
