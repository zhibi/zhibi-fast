package zhibi.fast.poi.excel.read;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import zhibi.fast.poi.excel.read.enums.ExcelTypeEnum;
import zhibi.fast.poi.excel.read.utils.ExcelValueUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Excel 导入
 *
 * @author 执笔
 * @date 2019/7/30 14:43
 */
public class ExcelImport {


    /**
     * 导入excel为javabean
     *
     * @param fileName   excel 文件全路径
     * @param clazz      要转换的java类
     * @param sheetIndex 第几个sheet
     * @param startRow   从第几行开始读
     * @return
     */
    public  static<E> List<E> importToBean(String fileName, Class<E> clazz, int startRow, int sheetIndex) throws IOException {
        if (StringUtils.isBlank(fileName)) {
            return Collections.EMPTY_LIST;
        }
        File file = new File(fileName);
        if (!file.exists()) {
            return Collections.EMPTY_LIST;
        }
        String extension = FilenameUtils.getExtension(fileName);
        if ("xls".equalsIgnoreCase(extension)) {
            return importToBean(new FileInputStream(file), clazz, ExcelTypeEnum.XLS, startRow, sheetIndex);
        } else if ("xlsx".equalsIgnoreCase(extension)) {
            return importToBean(new FileInputStream(file), clazz, ExcelTypeEnum.XLSX, startRow, sheetIndex);
        } else {
            throw new IOException("不支持的excel文件类型 " + fileName);
        }
    }


    /**
     * 导入excel为javabean
     *
     * @param inputStream excel 文件流
     * @param clazz       要转换的java类
     * @param sheetIndex  第几个sheet
     * @param startRow    从第几行开始读
     * @return
     */
    public static<E> List<E> importToBean(InputStream inputStream, Class<E> clazz, ExcelTypeEnum typeEnum, int startRow, int sheetIndex) throws IOException {
        Workbook workbook;
        List<E>  list = new ArrayList<>();
        if (typeEnum == ExcelTypeEnum.XLS) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            workbook = new XSSFWorkbook(inputStream);
        }
        Sheet sheet  = workbook.getSheetAt(sheetIndex);
        int   rowNum = sheet.getLastRowNum();
        if (startRow > rowNum) {
            return list;
        }
        // 表头
        List<String> head = readHead(sheet.getRow(startRow));
        // 内容
        for (int i = startRow + 1; i <= rowNum; i++) {
            Row row = sheet.getRow(i);
            rowToBean(clazz, list, head, row);
        }
        return list;
    }

    /**
     * 每行数据封装成一个java对象
     *
     * @param clazz
     * @param list
     * @param head
     * @param row
     */
    private static<E> void rowToBean(Class<E> clazz, List<E> list, List<String> head, Row row) {
        short      cellNum    = row.getLastCellNum();
        JSONObject jsonObject = new JSONObject();
        for (int j = 0; j <=cellNum; j++) {
            Cell   cell  = row.getCell(j);
            Object value = ExcelValueUtils.readValue(cell);
            jsonObject.put(head.get(j), value);
        }
        E object = jsonObject.toJavaObject(clazz);
        list.add(object);
    }

    /**
     * 读取表头
     *
     * @return
     */
    private static List<String> readHead(Row row) {
        List<String> list    = new ArrayList<>();
        short        cellNum = row.getLastCellNum();
        for (int j = 0; j <= cellNum; j++) {
            Cell   cell  = row.getCell(j);
            Object value = ExcelValueUtils.readValue(cell);
            list.add(String.valueOf(value));
        }
        return list;
    }
}

