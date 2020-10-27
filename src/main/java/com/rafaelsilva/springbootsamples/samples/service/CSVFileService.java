package com.rafaelsilva.springbootsamples.samples.service;

import java.beans.PropertyDescriptor;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rafaelsilva.springbootsamples.samples.data.entity.CSVContentEntity;
import com.rafaelsilva.springbootsamples.samples.data.repository.CSVContentRepository;
import com.rafaelsilva.springbootsamples.samples.dto.CSVFileDTO;
import com.rafaelsilva.springbootsamples.samples.dto.CustomFilesDTO;
import com.rafaelsilva.springbootsamples.samples.helper.CSVHelper;

@Service
public class CSVFileService {
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CSVFileService.class);

	@Autowired
	private CSVHelper csvHelper;

	@Autowired
	private CSVContentRepository csvContentRepository;

	public List<CSVFileDTO> CSVToBean(MultipartFile file) throws Exception {
		return CSVToBean(file, ',');
	}

	public List<CSVFileDTO> CSVToBean(MultipartFile file, char fileSeparator) throws Exception {
		if (file.isEmpty()) {
			throw new Exception("File is empty");
		}
		try {
			String originalFilename = file.getOriginalFilename();
			List<CSVFileDTO> listCSVFiles = csvHelper.CSVToBean(file, fileSeparator);
			Iterator<CSVFileDTO> interatorCSVFile = listCSVFiles.iterator();
			while (interatorCSVFile.hasNext()) {
				CSVFileDTO csvFileDTO = interatorCSVFile.next();
				if (csvFileDTO.isAllPropertiesNull()) {
					interatorCSVFile.remove();
				} else {
					csvFileDTO.setFileName(originalFilename);
				}
			}
			persistCSVContent(listCSVFiles);
			return listCSVFiles;
		} catch (Exception ex) {
			LOGGER.error("Error to parse CSV content to bean - " + ex.getCause());
			throw ex;
		}
	}

	public void persistCSVContent(List<CSVFileDTO> listCSVFiles) throws Exception {
		try {
			for (CSVFileDTO csvFileDTO : listCSVFiles) {
				CSVContentEntity entity = new CSVContentEntity(csvFileDTO.getFileName(), csvFileDTO.getColumn_1(),
						csvFileDTO.getColumn_2(), csvFileDTO.getColumn_3(), csvFileDTO.getColumn_4(),
						csvFileDTO.getColumn_5(), csvFileDTO.getColumn_6(), csvFileDTO.getColumn_7(),
						csvFileDTO.getColumn_8());
				csvContentRepository.delete(entity);
				csvContentRepository.save(entity);
			}
		} catch (Exception ex) {
			LOGGER.error("Error to persist CSV Content - " + ex.getCause());
			throw ex;
		}
	}

	public ZipOutputStream exportCustomFiles(OutputStream outputStream, String csvFileName, String customFileName, String customFileContent) throws Exception {
		try {
			List<CSVContentEntity> listCSVContent = csvContentRepository.findByCsvFileName(csvFileName);
			String[] dynamicFieldNameArray = customFileName.split(",");
			
	        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
	        HashSet<CustomFilesDTO> customFilesHashSet = new HashSet<CustomFilesDTO>();
	        
			for (CSVContentEntity csvContentEntity : listCSVContent) {
				String extractedCustomFileName = extractCustomFileName(dynamicFieldNameArray, csvContentEntity);
				String extractedCustomContentFile = extractCustomContentFile(customFileContent, csvContentEntity);

				//avoid files with the same name. CustomFilesDTO equals method is checking for the FileName
				customFilesHashSet.add(new CustomFilesDTO(extractedCustomFileName, extractedCustomContentFile));
			}
			
			for (CustomFilesDTO customFilesDTO : customFilesHashSet) {
				File fileToZip = createCustomFile(customFilesDTO.getCustomFileName(), customFilesDTO.getCustomFileContent());

		        //new zip entry and copying inputstream with file to zipOutputStream, after all closing streams
		        zipOutputStream.putNextEntry(new ZipEntry(fileToZip.getName()));
		        FileInputStream fileInputStream = new FileInputStream(fileToZip);

		        IOUtils.copy(fileInputStream, zipOutputStream);

		        fileInputStream.close();
		        zipOutputStream.closeEntry();				
			}

			zipOutputStream.close();
			
			return zipOutputStream;
		} catch (Exception ex) {
			throw ex;
		}
	}

	private File createCustomFile(String customFileName, String customContentFile) throws Exception {
		File fileToZip = new File(customFileName);
		FileOutputStream fos = new FileOutputStream(fileToZip);
		DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
		outStream.write(customContentFile.getBytes());
		outStream.close();
		return fileToZip;
	}

	private String extractCustomContentFile(String customFileContent, CSVContentEntity csvContentEntity) throws Exception {
		String content = null;
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(csvContentEntity);
		for (PropertyDescriptor objDescriptor : propertyDescriptors) {
			String propertyName = objDescriptor.getName();
			if (customFileContent.equals(propertyName)) {
				Object propValue = PropertyUtils.getProperty(csvContentEntity, propertyName);
				content = propValue.toString();
				break;
			}
		}
		return content;
	}

	private String extractCustomFileName(String[] dynamicFieldNameArray, CSVContentEntity csvContentEntity)	throws Exception {
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(csvContentEntity);
		StringBuilder fileName = new StringBuilder();
		// Create File Name
		for (String dynamicField : dynamicFieldNameArray) {
			for (PropertyDescriptor objDescriptor : propertyDescriptors) {
				String propertyName = objDescriptor.getName();
				if (dynamicField.equals(propertyName)) {
					Object propValue = PropertyUtils.getProperty(csvContentEntity, propertyName);
					if (fileName.length() == 0) {
						fileName.append(propValue);
					} else {
						fileName.append("_").append(propValue);
					}
					break;
				}
			}
		}
		if (fileName.length() > 0) {
			fileName.append(".txt");
		}
		return fileName.toString();
	}

}
