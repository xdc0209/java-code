package com.xdc.basic.api.apache.poi.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xdc.basic.api.apache.poi.core.annotation.XlsColum;
import com.xdc.basic.api.apache.poi.core.annotation.XlsSheet;
import com.xdc.basic.api.apache.poi.core.model.RowObj;

public class XlsUtils
{
    /**
     * 解析工作表
     */
    public static <T extends RowObj> List<T> parseSheet(String xlsFilePath, Class<T> clazz)
    {
        // 存放结果
        List<T> rows = new ArrayList<T>();

        // 后缀必须为.xls或.xlsx
        if (!StringUtils.endsWith(xlsFilePath, ".xls") && !StringUtils.endsWith(xlsFilePath, ".xlsx"))
        {
            System.err.println("File [" + xlsFilePath + "] must be end with [.xls] or [.xlsx].");
            return rows;
        }

        // 判断文件存在性
        File xlsFile = new File(xlsFilePath);
        if (!xlsFile.isFile())
        {
            System.err.println("File [" + xlsFilePath + "] not found.");
            return rows;
        }

        FileInputStream fileInputStream = null;
        try
        {
            fileInputStream = new FileInputStream(xlsFile);

            Workbook workbook = null;
            if (StringUtils.endsWith(xlsFilePath, ".xls"))
            {
                workbook = new HSSFWorkbook(fileInputStream);
            }
            else if (StringUtils.endsWith(xlsFilePath, ".xlsx"))
            {
                workbook = new XSSFWorkbook(fileInputStream);
            }
            else
            {
                // 方法开头已校验，此处不可达
            }

            XlsSheet xlsSheet = clazz.getAnnotation(XlsSheet.class);
            if (xlsSheet == null)
            {
                System.err.println("There is no Annotation @XlsSheet at class [" + clazz.getCanonicalName() + "]");
                return rows;
            }

            Sheet sheet = workbook.getSheet(xlsSheet.name());
            if (sheet == null)
            {
                System.err.println("Sheet [" + xlsSheet.name() + "] not found in file [" + xlsFilePath + "].");
                return rows;
            }

            rows = parseSheet(sheet, clazz);
        }
        catch (IOException e)
        {
            System.err.println("Parse sheet failed." + e.getMessage());
            e.printStackTrace();
            return rows;
        }
        finally
        {
            IOUtils.closeQuietly(fileInputStream);
        }

        return rows;
    }

    private static <T extends RowObj> List<T> parseSheet(Sheet sheet, Class<T> clazz)
    {
        // 存放结果
        List<T> rows = new ArrayList<T>();

        // 解析标题行(注意行号从0开始)
        Row headerRow = sheet.getRow(0);
        Map<Field, Integer> fieldIndexOfSheetMap = parseHeaderRow(clazz, headerRow);

        // 解析行
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++)
        {
            Row row = sheet.getRow(rowNum);
            T t = parseRow(clazz, fieldIndexOfSheetMap, row);
            if (t != null)
            {
                rows.add(t);
            }
        }

        return rows;
    }

    /**
     * 解析标题行
     * 
     * @param clazz
     * @param headerRow
     * @return
     */
    private static <T extends RowObj> Map<Field, Integer> parseHeaderRow(Class<T> clazz, Row headerRow)
    {
        // 建立java类的字段在excel的sheet中列编号
        Map<Field, Integer> fieldIndexOfSheetMap = new HashMap<Field, Integer>();

        // 行为空，直接返回
        if (isEmptyRow(headerRow))
        {
            return fieldIndexOfSheetMap;
        }

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields)
        {
            XlsColum annotation = field.getAnnotation(XlsColum.class);
            if (annotation == null)
            {
                continue;
            }

            for (int cellIndex = 0; cellIndex < headerRow.getLastCellNum(); cellIndex++)
            {
                Cell cell = headerRow.getCell(cellIndex);
                String cellString = getStringValue(cell);
                if (StringUtils.equalsIgnoreCase(cellString, annotation.name()))
                {
                    fieldIndexOfSheetMap.put(field, cellIndex);
                    break;
                }
            }
        }

        return fieldIndexOfSheetMap;
    }

    /**
     * 解析行
     * 
     * @param clazz
     * @param fieldIndexOfSheetMap
     * @param row
     * @return
     */
    private static <T extends RowObj> T parseRow(Class<T> clazz, Map<Field, Integer> fieldIndexOfSheetMap, Row row)
    {
        T t = null;

        // 行为空，直接返回
        if (isEmptyRow(row))
        {
            return t;
        }

        try
        {
            t = clazz.newInstance();
            t.setRowNum(row.getRowNum() + 1);
            for (Entry<Field, Integer> entry : fieldIndexOfSheetMap.entrySet())
            {
                Field field = entry.getKey();
                Integer cellIndex = entry.getValue();

                Cell cell = row.getCell(cellIndex);
                if (cell == null)
                {
                    continue;
                }

                field.setAccessible(true);
                field.set(t, getStringValue(cell));
            }
        }
        catch (InstantiationException e)
        {
            System.err.println("Parse row failed." + e.getMessage());
            e.printStackTrace();
            return t;
        }
        catch (IllegalAccessException e)
        {
            System.err.println("Parse row failed." + e.getMessage());
            e.printStackTrace();
            return t;
        }

        return t;
    }

    /**
     * 判断是否空行
     * 
     * @param row
     * @return
     */
    private static boolean isEmptyRow(Row row)
    {
        if (row == null)
        {
            return true;
        }

        boolean isEmpty = true;
        int physicalNumberOfCells = row.getPhysicalNumberOfCells();

        // 从第二列开始，第一列为超链接，不算
        for (int i = 1; i < physicalNumberOfCells; i++)
        {
            Cell cell = row.getCell(i);
            if (cell != null && StringUtils.isNotBlank(getStringValue(cell)))
            {
                isEmpty = false;
                break;
            }
        }

        // 再次确认，有些页签前几列有空的场景，保证也能取到值
        if (isEmpty)
        {
            Iterator<Cell> iter = row.iterator();
            while (iter.hasNext())
            {
                Cell cell = iter.next();
                int columnIndex = cell.getColumnIndex();
                // 第一列要过滤掉，跟上面保持一致
                if (columnIndex < 1)
                {
                    continue;
                }

                String value = getStringValue(cell);
                if (StringUtils.isNotBlank(value))
                {
                    isEmpty = false;
                    break;
                }
            }
        }

        return isEmpty;
    }

    /**
     * 获取单元格内容
     * 
     * @param cell
     * @return
     */
    private static String getStringValue(Cell cell)
    {
        // Cell.CELL_TYPE_BLANK
        // Cell.CELL_TYPE_NUMERIC
        // Cell.CELL_TYPE_STRING
        // Cell.CELL_TYPE_FORMULA
        // Cell.CELL_TYPE_BOOLEAN
        // Cell.CELL_TYPE_ERROR

        if (cell == null)
        {
            return null;
        }

        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN)
        {
            return String.valueOf(cell.getBooleanCellValue());
        }
        else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
        {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            return cell.getStringCellValue();
        }
        else
        {
            return cell.getStringCellValue();
        }
    }
}
