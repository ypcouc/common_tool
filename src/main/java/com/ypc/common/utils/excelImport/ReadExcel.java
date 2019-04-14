package com.ypc.common.utils.excelImport;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


public class ReadExcel {
    /**
     * 对外提供读取excel 的方法
     */
    public static List<Map<String, String>> readExcel(String fileName, int sheet, HttpServletRequest request) throws IOException {
        String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
                .substring(fileName.lastIndexOf(".") + 1);
        String path = request.getServletContext().getRealPath("temp");
        String fileDir = path + File.separator + fileName;
        if ("xls".equals(extension)) {
            /*return read2003Excel(fileDir,sheet);*/
            return null;
        } else if ("xlsx".equals(extension)) {
            return read2007Excel(fileDir, sheet);
        } else {
            throw new IOException("不支持的文件类型");
        }
    }

    /**
     * 对外提供读取excel 的方法
     */
    public static List<List<Object>> readExcelNew(String fileDir, int sheet) throws IOException {
        if (StringUtils.isEmpty(fileDir)) {
            return null;
        }
        String extension = fileDir.lastIndexOf(".") == -1 ? "" : fileDir
                .substring(fileDir.lastIndexOf(".") + 1);

        if ("xls".equals(extension)) {
            return read2003ExcelNew(fileDir, sheet);
        } else if ("xlsx".equals(extension)) {
            return read2007ExcelNew(fileDir, sheet);
        } else {
            throw new IOException("不支持的文件类型");
        }
    }

    /**
     * creates an {@link HSSFWorkbook} with the specified OS filename.
     */
    private static HSSFWorkbook HSSFReadFile(String fileName) throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        try {
            return new HSSFWorkbook(fis);        // NOSONAR - should not be closed here
        } finally {
            fis.close();
        }
    }

    /**
     * creates an {@link HSSFWorkbook} with the specified OS filename.
     */
    private static XSSFWorkbook XSSFReadFile(String fileName) throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        try {
            return new XSSFWorkbook(fis);        // NOSONAR - should not be closed here
        } finally {
            fis.close();
        }
    }

    /**
     * 读取 office 2003 excel
     *
     * @throws IOException
     * @throws FileNotFoundException
     */
	/*private static List<Map<String, String>> read2003Excel(String fileName,int sheetIndex)
			throws IOException {
		List<List<Object>> list = new LinkedList<List<Object>>();
		try {
			HSSFWorkbook wb = ReadExcel.HSSFReadFile(fileName);

			try {
				System.out.println("Data dump:\n");

				HSSFSheet sheet = wb.getSheetAt(sheetIndex);
				int rows = sheet.getPhysicalNumberOfRows();
				System.out.println("Sheet " + sheetIndex + " \"" + wb.getSheetName(sheetIndex) + "\" has " + rows
						+ " row(s).");
				for (int r = 1; r < rows; r++) {
					HSSFRow row = sheet.getRow(r);
					if (row == null) {
						continue;
					}

					System.out.println("\nROW " + row.getRowNum() + " has " + row.getPhysicalNumberOfCells() + " cell(s).");
					List<Object> linked = new LinkedList<Object>();
					for (int c = 0; c < row.getLastCellNum(); c++) {
						HSSFCell cell = row.getCell(c);
						String value;

						if(cell != null) {
							switch (cell.getCellTypeEnum()) {

								case FORMULA:
									value = "FORMULA value=" + cell.getCellFormula();
									break;

								case NUMERIC:
									value = "NUMERIC value=" + cell.getNumericCellValue();
									break;

								case STRING:
									value = "STRING value=" + cell.getStringCellValue();
									break;

								case BLANK:
									value = "<BLANK>";
									break;

								case BOOLEAN:
									value = "BOOLEAN value-" + cell.getBooleanCellValue();
									break;

								case ERROR:
									value = "ERROR value=" + cell.getErrorCellValue();
									break;

								default:
									value = "UNKNOWN value of type " + cell.getCellTypeEnum();
							}
							linked.add(value);
							System.out.println("CELL col=" + cell.getColumnIndex() + " VALUE="
									+ value);
						}else{
							value = "";
							linked.add(value);
							continue;
						}
					}
					list.add(linked);
				}
			} finally {
				wb.close();
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}*/

    /**
     * 读取Office 2007 excel
     */
    private static List<Map<String, String>> read2007Excel(String fileName, int sheetIndex)
            throws IOException {
        List<List<Object>> list = new LinkedList<List<Object>>();
        List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
        try {
            XSSFWorkbook wb = ReadExcel.XSSFReadFile(fileName);

            try {
                XSSFSheet sheet = wb.getSheetAt(sheetIndex);
                int rows = sheet.getPhysicalNumberOfRows();
                if (rows > 0) {
                    for (int r = 1; r < rows; r++) {
                        XSSFRow row = sheet.getRow(r);
                        if (row == null) {
                            continue;
                        }

                        //表头信息
                        XSSFRow headerRow = sheet.getRow(0);
                        List<Object> linked = new LinkedList<Object>();
                        Map<String, String> object = new HashMap<String, String>();
                        for (int c = 0; c < row.getLastCellNum(); c++) {
                            XSSFCell cell = row.getCell(c);
                            String value;

                            if (cell != null) {
                                switch (cell.getCellTypeEnum()) {

                                    case FORMULA:
                                        value = cell.getCellFormula();
                                        break;

                                    case NUMERIC:
                                        value = cell.getNumericCellValue() + "";
                                        break;

                                    case STRING:
                                        value = cell.getStringCellValue();
                                        break;

                                    case BLANK:
                                        value = "";
                                        break;

                                    case BOOLEAN:
                                        value = cell.getBooleanCellValue() + "";
                                        break;

                                    case ERROR:
                                        value = "";
                                        break;

                                    default:
                                        value = cell.getCellTypeEnum() + "";
                                }
                                linked.add(value);
                                object.put(headerRow.getCell(c).getStringCellValue(), value);
                            } else {
                                value = "";
                                linked.add(value);
                                object.put(headerRow.getCell(c).getStringCellValue(), value);
                                continue;
                            }
                        }
                        list.add(linked);
                        returnList.add(object);
                    }
                }
            } finally {
                wb.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnList;
    }


    /**
     * 读取Office 2007 excel
     */
    private static List<List<Object>> read2007ExcelNew(String fileName, int sheetIndex)
            throws IOException {
        List<List<Object>> list = new LinkedList<List<Object>>();
        try {
            XSSFWorkbook wb = ReadExcel.XSSFReadFile(fileName);

            try {
                XSSFSheet sheet = wb.getSheetAt(sheetIndex);
                int rows = sheet.getPhysicalNumberOfRows();
                if (rows > 0) {
                    for (int r = 0; r < rows; r++) {
                        XSSFRow row = sheet.getRow(r);
                        if (row == null) {
                            continue;
                        }

                        List<Object> linked = new LinkedList<Object>();
                        for (int c = 0; c < row.getLastCellNum(); c++) {
                            XSSFCell cell = row.getCell(c);
                            String value;
                            if (cell != null) {
                                switch (cell.getCellTypeEnum()) {
                                    case FORMULA:
                                        value = cell.getCellFormula();
                                        break;
                                    case NUMERIC:
                                        HSSFDataFormatter dataFormatter = new HSSFDataFormatter();
                                        value = dataFormatter.formatCellValue(cell);
                                        //value = cell.getNumericCellValue()+"";
                                        break;
                                    case STRING:
                                        value = cell.getStringCellValue();
                                        break;
                                    case BLANK:
                                        value = "";
                                        break;
                                    case BOOLEAN:
                                        value = cell.getBooleanCellValue() + "";
                                        break;
                                    case ERROR:
                                        value = "";
                                        break;
                                    default:
                                        value = cell.getCellTypeEnum() + "";
                                }
                                linked.add(value);
                            } else {
                                value = "";
                                linked.add(value);
                                continue;
                            }
                        }
                        list.add(linked);
                    }
                }
            } finally {
                wb.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 读取 office 2003 excel
     *
     * @throws IOException
     * @throws FileNotFoundException
     */
    private static List<List<Object>> read2003ExcelNew(String fileName, int sheetIndex)
            throws IOException {
        List<List<Object>> list = new LinkedList<List<Object>>();
        try {
            HSSFWorkbook wb = ReadExcel.HSSFReadFile(fileName);

            try {
                HSSFSheet sheet = wb.getSheetAt(sheetIndex);
                int rows = sheet.getPhysicalNumberOfRows();
                if (rows > 0) {
                    for (int r = 0; r < rows; r++) {
                        HSSFRow row = sheet.getRow(r);
                        if (row == null) {
                            continue;
                        }
                        List<Object> linked = new LinkedList<Object>();
                        for (int c = 0; c < row.getLastCellNum(); c++) {
                            HSSFCell cell = row.getCell(c);
                            String value;
                            if (cell != null) {
                                switch (cell.getCellTypeEnum()) {

                                    case FORMULA:
                                        value = cell.getCellFormula();
                                        break;
                                    case NUMERIC:
                                        HSSFDataFormatter dataFormatter = new HSSFDataFormatter();
                                        value = dataFormatter.formatCellValue(cell);
                                        break;
                                    case STRING:
                                        value = cell.getStringCellValue();
                                        break;
                                    case BLANK:
                                        value = "";
                                        break;
                                    case BOOLEAN:
                                        value = cell.getBooleanCellValue() + "";
                                        break;
                                    case ERROR:
                                        value = "";
                                        break;
                                    default:
                                        value = cell.getCellTypeEnum() + "";
                                }
                                linked.add(value);
                            } else {
                                value = "";
                                linked.add(value);
                                continue;
                            }
                        }
                        list.add(linked);
                    }
                }
            } finally {
                wb.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


}
