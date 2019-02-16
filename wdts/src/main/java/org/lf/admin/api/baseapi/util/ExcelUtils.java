package org.lf.admin.api.baseapi.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.lf.admin.api.baseapi.core.ServiceException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * excel处理工具类
 *
 * @author sunwill
 */
@Slf4j
public class ExcelUtils {
    /**
     * excel后缀名数组
     */
    private static final String[] EXCEL_SUFFIX = {"xls", "xlsx"};
    /**
     * 默认excel最大size(单位：bytes)
     */
    private static final long DEFAULT_MAX_SIZE = 10 * 1024 * 1024;

    /**
     * 导出下载excel文件
     *
     * @param wb
     * @param response
     * @param fileName
     */
    public static final void exportExcel(Workbook wb, HttpServletResponse response, String fileName) {
        OutputStream out = null;
        try {
            response.setContentType("multipart/form-data");
            response.reset(); //非常重要
            //纯下载方式
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            out = response.getOutputStream();
            wb.write(out);
        } catch (Exception e) {
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
            if (wb != null) {
                try {
                    wb.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    public static final String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            cell.setCellType(CellType.STRING);
        }
        //判断数据的类型
        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case STRING:
                //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN:
                //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK:
                //空值
                cellValue = "";
                break;
            case ERROR:
                //故障
//                cellValue = "非法字符";
                cellValue = "";
                break;
            default:
//                cellValue = "未知类型";
                cellValue = "";
                break;
        }
        return cellValue.trim();
    }

    /**
     * 文件验证(默认不能超过10MB)
     *
     * @param excelFile
     */
    public static final void checkFile(MultipartFile excelFile) {
        checkFile(excelFile, DEFAULT_MAX_SIZE);
    }

    /**
     * 文件验证
     *
     * @param excelFile
     * @param maxSize
     */
    public static final void checkFile(MultipartFile excelFile, long maxSize) {
        if (excelFile == null) {
            throw new ServiceException("文件不存在");
        }
        log.debug("文件扩展名：{}", FilenameUtils.getExtension(excelFile.getOriginalFilename()));
        // 检查文件类型是否为excel文件
        if (!ArrayUtils.contains(EXCEL_SUFFIX, FilenameUtils.getExtension(excelFile.getOriginalFilename()).toLowerCase())) {
            throw new ServiceException("文件不是excel文件");
        }
        // 检查文件大小是否过大
        if (excelFile.getSize() > maxSize) {
            throw new ServiceException(String.format("文件大小不能大于%.3fMB", (maxSize / 1024.0 / 1024.0)));
        }
    }

    /**
     * 获得工作簿对象
     *
     * @param file
     * @return
     */
    public static final Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename().toLowerCase();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName.endsWith(EXCEL_SUFFIX[0])) {
                // excel 2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(EXCEL_SUFFIX[1])) {
                // excel 2007以上
                workbook = new XSSFWorkbook(is);
            }
        } catch (Exception e) {
            throw new ServiceException("读取excel失败");
        }
        return workbook;
    }

    /**
     * 将所有sheet中的数据读出来；
     * 每一行数据是一个字符串数组
     *
     * @param workbook
     * @return
     */
    public static final List<String[]> getData(Workbook workbook) {
        List<String[]> list = new ArrayList<String[]>();
        try {
            if (workbook != null) {
                for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                    //获得当前sheet工作表
                    Sheet sheet = workbook.getSheetAt(sheetNum);
                    if (sheet == null) {
                        continue;
                    }
                    //获得当前sheet的开始行
                    int firstRowNum = sheet.getFirstRowNum();
                    //获得当前sheet的结束行
                    int lastRowNum = sheet.getLastRowNum();
                    //循环除了第一行的所有行
                    for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                        //获得当前行
                        Row row = sheet.getRow(rowNum);
                        if (row == null) {
                            continue;
                        }
                        //获得当前行的开始列
                        int firstCellNum = row.getFirstCellNum();
                        //获得当前行的列数
                        int lastCellNum = row.getPhysicalNumberOfCells();
                        String[] cells = new String[row.getPhysicalNumberOfCells()];
                        //循环当前行
                        for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                            Cell cell = row.getCell(cellNum);
                            cells[cellNum] = getCellValue(cell);
                        }
                        list.add(cells);
                    }
                }
            }
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
            }
        }
        return list;
    }


    /**
     * 将所有sheet中的数据读出来；
     * 每一行数据是一个字符串数组
     * <p>
     * 因为有的excel格式不标准 扩展一下
     *
     * @param workbook
     * @return
     */
    public static final List<String[]> getData(Workbook workbook, Integer startRowNum, Integer lastColumnNum) {
        List<String[]> list = new ArrayList<String[]>();
        try {
            if (workbook != null) {
                for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                    //获得当前sheet工作表
                    Sheet sheet = workbook.getSheetAt(sheetNum);
                    if (sheet == null) {
                        continue;
                    }
                    //获得当前sheet的开始行
                    int firstRowNum;
                    if (startRowNum == null) {
                        firstRowNum = sheet.getFirstRowNum();
                    } else {
                        firstRowNum = startRowNum;
                    }
                    //获得当前sheet的结束行
                    int lastRowNum = sheet.getLastRowNum();
                    //循环除了第一行的所有行
                    for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
                        //获得当前行
                        Row row = sheet.getRow(rowNum);
                        if (row == null) {
                            continue;
                        }
                        //获得当前行的开始列
                        int firstCellNum = row.getFirstCellNum();
                        //获得当前行的列数
                        int lastCellNum;
                        if (lastColumnNum == null) {
                            lastCellNum = row.getPhysicalNumberOfCells();
                        } else {
                            lastCellNum = lastColumnNum;
                        }

                        String[] cells = new String[lastCellNum];
                        //循环当前行
                        for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                            Cell cell = row.getCell(cellNum);
                            cells[cellNum] = getCellValue(cell);
                        }
                        list.add(cells);
                    }
                }
            }
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
            }
        }
        return list;
    }
}
