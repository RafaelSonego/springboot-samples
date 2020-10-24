package com.rafaelsilva.samples.samples.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.rafaelsilva.samples.samples.dto.CSVFileDTO;

@Component
public class CSVHelper {
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CSVHelper.class);

	/***
	 * Will convert the CSV file into a Java DTO
	 * @param file CSV File
	 * @return List of Objects
	 * @throws Exception
	 */
	public List<CSVFileDTO> CSVToBean(MultipartFile file) throws Exception {
		return CSVToBean(file, ',');
	}
	
	/***
	 * Will convert the CSV file into a Java DTO
	 * @param file CSV File
	 * @param fileSeparator to read the CSV file
	 * @return List of Objects
	 * @throws Exception
	 */
	public List<CSVFileDTO> CSVToBean(MultipartFile file, char fileSeparator) throws Exception {
		// parse CSV file to create a list of objects
		try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            // create csv bean reader
            CsvToBean<CSVFileDTO> csvToBean = new CsvToBeanBuilder<CSVFileDTO>(reader)
                    .withType(CSVFileDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSeparator(fileSeparator)
                    .build();
            
            // convert to a list of objects
            List<CSVFileDTO> fileRows = csvToBean.parse();
            return fileRows;
		} catch (Exception ex) {
			LOGGER.error("Error to parse CSV content to bean - " + ex.getMessage());
			throw ex;
		}
	}

}
