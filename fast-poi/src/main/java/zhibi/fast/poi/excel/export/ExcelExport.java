package zhibi.fast.poi.excel.export;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import zhibi.fast.poi.excel.export.config.ExcelSheetConfig;
import zhibi.fast.poi.excel.export.entity.SheetData;
import zhibi.fast.poi.excel.export.enums.ExcelTypeEnum;
import zhibi.fast.poi.excel.export.exception.ExcelExportException;
import zhibi.fast.poi.excel.export.service.ExcelExportService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * excel excel 导出工具类
 *
 * @author 执笔
 * @date 2018/11/23 17:23
 */
public class ExcelExport {

    /**
     * HSSFWorkbook XSSFWorkbook 能导出的最大的数据行
     * 减去表头和标题可能需要占用的行数
     */
    private static final int MAX_ROW = 65536 - 5;

    /**
     * 导出excel的格式
     */
    private ExcelTypeEnum excelType;

    /**
     * 存放多个sheet的数据和配置
     */
    private List<SheetData> sheetDataList;

    /**
     * 最后的excel表格
     */
    private Workbook workbook;

    public ExcelExport() {
        this(ExcelTypeEnum.XLS);
    }


    public ExcelExport(ExcelTypeEnum excelType) {
        this.excelType = excelType;
        sheetDataList = new ArrayList<>(1);
    }

    /**
     * 添加一个sheet
     *
     * @param sheetConfig 这个sheet的配置
     * @param dataList    需要导出的数据
     * @return
     */
    public ExcelExport addSheet(ExcelSheetConfig sheetConfig, List<?> dataList) {
        if (null == dataList || dataList.size() == 0) {
            throw new ExcelExportException("sheet要导出的数据不能为空");
        }
        //清除workbook 保证添加数据的时候workbook一直是null
        if (null != workbook) {
            workbook = null;
        }
        int size = dataList.size();
        // 超过最大行
        if (size >= MAX_ROW) {
            Object[] objects = dataList.toArray();
            int      index   = 0;
            while (MAX_ROW * index <= size) {
                int dataSize = MAX_ROW;
                if (size < MAX_ROW * (index + 1)) {
                    dataSize = size - MAX_ROW * index;
                }
                Object[] data = new Object[dataSize];
                System.arraycopy(objects, MAX_ROW * index, data, 0, dataSize);
                sheetDataList.add(new SheetData(sheetConfig, Arrays.asList(data)));
                index++;
            }
        } else {
            sheetDataList.add(new SheetData(sheetConfig, dataList));
        }

        return this;
    }

    /**
     * 添加一个sheet
     *
     * @param dataList
     * @return
     */
    public ExcelExport addSheet(List<?> dataList) {
        ExcelSheetConfig sheetConfig = new ExcelSheetConfig("sheet-0");
        return addSheet(sheetConfig, dataList);
    }

    /**
     * 获取要导出的excel表格
     *
     * @return
     */
    public Workbook getWorkbook() {
        if (workbook == null) {
            createWorkbook();
        }
        return workbook;
    }

    /**
     * 导出excel表格
     *
     * @param response
     * @throws IOException
     */
    public void export(HttpServletResponse response, String fileName) throws IOException {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), StandardCharsets.ISO_8859_1) + ".xls");
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        if (workbook == null) {
            createWorkbook();
        }
        workbook.write(response.getOutputStream());
    }

    /**
     * 导出excel表格
     *
     * @param outputStream 输出流
     */
    public void export(OutputStream outputStream) throws IOException {
        if (workbook == null) {
            createWorkbook();
        }
        workbook.write(outputStream);
    }

    /**
     * 创建workbook
     */
    private void createWorkbook() {
        switch (excelType) {
            case XLSX:
                workbook = new XSSFWorkbook();
                break;
            case XLSX_PLUS:
                workbook = new SXSSFWorkbook();
                break;
            default:
                workbook = new HSSFWorkbook();
                break;
        }
        //创建sheet
        ExcelExportService excelExportService = new ExcelExportService(workbook);
        for (SheetData sheetData : sheetDataList) {
            excelExportService.createSheet(sheetData);
        }
    }

}
