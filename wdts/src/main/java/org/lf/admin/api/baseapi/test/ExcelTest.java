package org.lf.admin.api.baseapi.test;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.lf.admin.api.baseapi.core.ServiceException;
import org.lf.admin.api.baseapi.util.ExcelUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class ExcelTest {

    public static void main(String[] args) {

        File file = new File("C:\\Users\\LHY\\Desktop\\测试500.xlsx");
        //获得文件名
        String fileName = file.getName().toLowerCase();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = new FileInputStream(file);
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName.endsWith("xls")) {
                // excel 2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith("xlsx")) {
                // excel 2007以上
                workbook = new XSSFWorkbook(is);
            }
        } catch (Exception e) {
            throw new ServiceException("读取excel失败");
        }

        List<String[]> list = ExcelUtils.getData(workbook);
        for (String[] strs : list) {
            for (String str : strs) {
                System.out.print(str + " ");
            }
            System.out.println();
        }
    }
}
