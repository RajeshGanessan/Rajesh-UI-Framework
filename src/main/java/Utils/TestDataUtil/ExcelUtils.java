package Utils.TestDataUtil;

import java.io.*;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	static XSSFWorkbook workbook;
	static XSSFCell cell;
	static XSSFRow row;
	static XSSFSheet sheet;
	static FileInputStream file;
	static FileOutputStream outputFile;
	private static final String TEST_DATA_FILEPATH = System.getProperty("user.dir") + File.separator+ "resources"+File.separator +"TestData/ExcelData.xlsx";

	public ExcelUtils(String sheetName) {
		try(FileInputStream file = new FileInputStream(TEST_DATA_FILEPATH)){
				workbook = new XSSFWorkbook(file);
				sheet = workbook.getSheet(sheetName);
				System.out.println(sheet.getSheetName());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// To get the no of rows
	public synchronized int getRowCount() {
		try {
			int rowCount = sheet.getPhysicalNumberOfRows();
			return rowCount;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return 0;
		}
	}

	// To get String cell value
	public synchronized String getCellData(int rowNum, int celNum) {
		String cellValue = null;
		try {
			cellValue = sheet.getRow(rowNum).getCell(celNum).toString();
			return cellValue;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// To get cell value from different Sheet
	public synchronized String getCellDataFromSheet(int rowNum, int cellNum, String sheetName) {
		sheet = workbook.getSheet(sheetName);
		String cellVal = "";
		try {
			cellVal = sheet.getRow(rowNum).getCell(cellNum).toString();
			return cellVal;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	// To get numeric Value
	public synchronized double getCellNumeric(int rowNum, int celNum) {
		try {
			double cellValue = sheet.getRow(rowNum).getCell(celNum).getNumericCellValue();
			return cellValue;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	// to set Stringvalue
	public synchronized void setCellData(int rowNum, int celNum, String data)  {
		try {
			row = sheet.getRow(rowNum);
			cell = row.getCell(celNum);
			if (cell == null) {
				cell = row.createCell(celNum);
				cell.setCellValue(data);
			} else {
				cell.setCellValue(data);
			}
			file.close();
			outputFile = new FileOutputStream(
					(TEST_DATA_FILEPATH));
			workbook.write(outputFile);
			outputFile.flush();
			outputFile.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
