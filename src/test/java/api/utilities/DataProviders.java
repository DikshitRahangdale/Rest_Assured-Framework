package api.utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	@DataProvider(name = "allData")
	public String[][] getAllData() throws IOException {
		String path = System.getProperty("user.dir") + "//testData//apiUserData.xlsx";
		ExcelData excelData = new ExcelData(path);   
		int lastRowIndex = excelData.getRowCount("Users"); // returns last row index (0-based)
		int rowCount = lastRowIndex; // assuming first row is header and data starts from row 1
		int cellCount = excelData.getCellCount("Users", 0); // get cell count from header row (row 0)

		String[][] data = new String[rowCount][cellCount];

		// Read rows starting from 1 (first data row) to lastRowIndex inclusive
		for (int i = 1; i <= lastRowIndex; i++) {
			for (int j = 0; j < cellCount; j++) {
				// store into zero-based array index (i-1)
				data[i - 1][j] = excelData.getCellData("Users", i, j);
			}
		}
		return data;

	}

	@DataProvider(name = "userName")
	public Object[][] getUserName() throws IOException {
		String path = System.getProperty("user.dir") + "//testData//apiUserData.xlsx";
		ExcelData excelData = new ExcelData(path);
		int lastRowIndex = excelData.getRowCount("Users");

		Object[][] username = new Object[lastRowIndex][1];
		for (int i = 1; i <= lastRowIndex; i++) {
			// read username from column 1 (username column) of the data row i and put into [i-1][0]
			username[i - 1][0] = excelData.getCellData("Users", i, 1);
		}
		return username;
	}
}
