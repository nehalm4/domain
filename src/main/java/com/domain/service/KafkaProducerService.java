package com.domain.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.domain.pojo.Address;
import com.domain.pojo.Department;
import com.domain.pojo.Employee;

import jakarta.transaction.Transactional;

@Service
public class KafkaProducerService {

	private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);

	@Value("${kafka.directory.name}")
	private String filePath;

	@Value("${kafka.topic.name}")
	private String topicName;

	private KafkaTemplate<String, Employee> kafkaTemplate;

	@Autowired
	KafkaProducerService(KafkaTemplate<String, Employee> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Transactional
	@Scheduled(fixedRate = 60000) // 1 minute = 60000 ms
	public void readExcelAndSaveToDB() {

		File file = new File(filePath);

		// Check if the file exists
		if (file.exists()) {
			log.info("File found! Processing...");
			processExcel(file.getPath());

			// Delete file after processing
			try {
				Path fileToPath = Paths.get(file.getAbsolutePath());
				Files.delete(fileToPath);
				log.info("File processed and deleted successfully.");
			} catch (Exception e) {
				log.error("File could not be deleted {}" + e.getMessage());
			}

		} else {
			log.info("File not found, waiting for the next run...");
		}
	}

	public void processExcel(String filePath) {

		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {

			Sheet sheet = workbook.getSheetAt(0); // Assuming the first sheet
			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				if (row.getRowNum() == 0) {
					continue; // Skip header row
				}

				String employeeName = row.getCell(0).getStringCellValue();
				Boolean isActive = row.getCell(1).getBooleanCellValue();
				String addressCity = row.getCell(2).getStringCellValue();
				String departmentName = row.getCell(3).getStringCellValue();

				Address city = Address.builder().city(addressCity).build();
				Department department = Department.builder().departmentName(departmentName).build();

				Employee employee = Employee.builder().employeeName(employeeName).isActive(isActive).address(city)
						.department(department).build();

				kafkaTemplate.send(topicName, employee);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
