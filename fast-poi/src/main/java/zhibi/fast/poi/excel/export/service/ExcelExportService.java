package zhibi.fast.poi.excel.export.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import zhibi.fast.poi.excel.export.config.ExcelSheetConfig;
import zhibi.fast.poi.excel.export.core.ExcelCellHandler;
import zhibi.fast.poi.excel.export.core.hanler.CellValueHandler;
import zhibi.fast.poi.excel.export.entity.ExportCell;
import zhibi.fast.poi.excel.export.entity.SheetData;
import zhibi.fast.poi.excel.export.style.ExcelStyle;
import zhibi.fast.poi.excel.export.style.StyleUtils;
import zhibi.fast.poi.excel.export.style.cell.ExcelCellStyle;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

/**
 * 导出excel的服务
 *
 * @author 执笔
 * @date 2018/11/23 21:02
 */
@Slf4j
public class ExcelExportService {

    /**
     * 保存一个shell所有的cell
     * 通过实体映射得到
     */
    private List<ExportCell> exportCells;

    /**
     * sheet
     */
    private Sheet sheet;

    /**
     * workbook
     */
    private Workbook workbook;

    /**
     * 表格的样式
     */
    private ExcelStyle excelStyle;

    /**
     * 值处理器
     */
    private CellValueHandler cellValueHandler;


    public ExcelExportService(Workbook workbook) {
        this.workbook = workbook;
    }

    /**
     * 创建sheet
     *
     * @param sheetData sheet的数据
     */
    public void createSheet(SheetData sheetData) {
        ExcelSheetConfig config = sheetData.getConfig();
        List<?>          data   = sheetData.getData();
        /**
         * 创建sheet
         */
        createSheet(workbook, config);

        excelStyle = StyleUtils.getExcelStyle(config.getExcelStyle(), workbook);
        cellValueHandler = config.getCellValueHandler();

        /**
         * 得到所有的ExcelCell
         */
        if (data != null && data.size() > 0) {
            ExcelCellHandler excelCellHandler = new ExcelCellHandler();
            exportCells = excelCellHandler.getAllExportCell(data.get(0).getClass());
        }
        insertSheetData(config, sheetData);
    }

    /**
     * 创建sheet
     * 有重名的直接创建默认的
     *
     * @param config
     * @return
     */
    private void createSheet(Workbook workbook, ExcelSheetConfig config) {
        String sheetName = config.getSheetName();
        if (StringUtils.isNotEmpty(sheetName) && null == workbook.getSheet(sheetName)) {
            sheet = workbook.createSheet(sheetName);
        } else {
            sheet = workbook.createSheet();
        }
    }

    /**
     * 插入标题
     *
     * @param config
     * @return
     */
    private int insertTitle(ExcelSheetConfig config) {
        /**
         * 当前行
         */
        int    rowIndex    = 0;
        String title       = config.getTitle();
        String secondTitle = config.getSecondTitle();
        int    maxCell     = 0;
        if (exportCells != null && exportCells.size() > 0) {
            for (ExportCell exportCell : exportCells) {
                maxCell += exportCell.getMergeCell();
            }
        }
        if (StringUtils.isNotEmpty(title)) {
            Row row = sheet.createRow(rowIndex);
            row.setHeightInPoints(30);
            Cell cell = row.createCell(0);
            cell.setCellValue(title);
            cell.setCellStyle(excelStyle.titleStyle());
            rowIndex++;
            if (maxCell > 1) {
                //合并
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, maxCell - 1));
            }
        }
        if (StringUtils.isNotEmpty(secondTitle)) {
            Row row = sheet.createRow(rowIndex);
            row.setHeightInPoints(25);
            Cell cell = row.createCell(0);
            cell.setCellValue(secondTitle);
            cell.setCellStyle(excelStyle.secondTitleStyle());
            rowIndex++;
            if (maxCell > 1) {
                //合并
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, maxCell - 1));
            }
        }
        if (config.isFixTitle()) {
            sheet.createFreezePane(0, rowIndex, 0, rowIndex);
        }
        return rowIndex;
    }

    /**
     * 插入数据
     *
     * @param config
     * @param sheetData
     */
    private void insertSheetData(ExcelSheetConfig config, SheetData sheetData) {
        //插入标题
        int rowIndex = insertTitle(config);
        if (exportCells == null || exportCells.size() == 0) {
            return;
        }
        //插入表头
        insertTableHead(exportCells, 0, rowIndex);
        rowIndex = sheet.getLastRowNum() + 1;
        if (config.isFixHead()) {
            sheet.createFreezePane(0, rowIndex, 0, rowIndex);
        }
        //插入数据
        insertListData(sheetData.getData(), exportCells, 0, rowIndex);
    }

    /**
     * 插入list表格数据
     *
     * @param data      数据
     * @param cellIndex 结构
     * @param cellList  开始列的索引
     * @param rowNum    开始行的索引
     */
    private int insertListData(List<?> data, List<ExportCell> cellList, int cellIndex, int rowNum) {
        int flag;
        //一共插入了多少行
        int totalRow = 0;
        for (int i = 0; i < data.size(); i++) {
            Object obj = data.get(i);
            Row    row;
            if (sheet.getLastRowNum() >= rowNum) {
                row = sheet.getRow(rowNum);
            } else {
                row = sheet.createRow(rowNum);
            }
            flag = insertObjectData(obj, cellList, cellIndex, row);
            /**
             * 开始合并该行数据
             */
            if (flag > 1) {
                for (ExportCell exportCell : cellList) {
                    if (exportCell.getList() == null || exportCell.getList().size() == 0) {
                        sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum() + flag - 1, exportCell.getCellIndex(), exportCell.getCellIndex()));
                    }
                }
            }
            rowNum += flag;
            totalRow += flag;
        }
        return totalRow;
    }

    /**
     * 插入对象数据
     *
     * @param data      数据
     * @param cellList  结构
     * @param cellIndex 开始列的索引
     * @param row       当前行
     * @return 返回一共插入了多少行数据
     */
    private int insertObjectData(Object data, List<ExportCell> cellList, int cellIndex, Row row) {
        int rowTotal = 1;
        for (ExportCell exportCell : cellList) {
            exportCell.setCellIndex(cellIndex);
            /**
             * 得到列样式
             */
            ExcelCellStyle cellStyle = StyleUtils.getExcelCellStyle(exportCell.getCellStyle(), workbook);
            Field          field     = exportCell.getField();
            /**
             * 普通列表
             * 直接输出
             */
            if (field == null) {
                Cell cell = row.createCell(cellIndex);
                cellIndex++;
                cell.setCellStyle(cellStyle.getCellStyle());
                cellValueHandler.insertValue(exportCell, cell, data);
            } else if (null != exportCell.getList() && exportCell.getList().size() > 0) {
                /**
                 * 有下级
                 */
                field.setAccessible(true);
                try {
                    Object o = field.get(data);
                    if (null == o) {
                        continue;
                    }
                    /**
                     * 插入多行  减去一
                     */
                    if (Collection.class.isAssignableFrom(o.getClass())) {
                        rowTotal += insertListData((List<?>) o, exportCell.getList(), cellIndex, row.getRowNum()) - 1;
                    } else {
                        rowTotal += insertObjectData(o, exportCell.getList(), cellIndex, row) - 1;
                    }
                } catch (IllegalAccessException ignore) {
                }
                cellIndex += exportCell.getMergeCell();
            } else {
                /**
                 * 可以直接取值的属性
                 */
                Cell cell = row.createCell(cellIndex);
                cellIndex++;
                cell.setCellStyle(cellStyle.getCellStyle());
                field.setAccessible(true);
                try {
                    Object o = field.get(data);
                    cellValueHandler.insertValue(exportCell, cell, o);
                } catch (IllegalAccessException ignore) {
                }
            }
        }
        return rowTotal;
    }


    /**
     * 插入表头
     *
     * @param cellList  格式
     * @param cellIndex 当前列
     * @param rowIndex  当前行
     * @return 一共插入了几列
     */
    private int insertTableHead(List<ExportCell> cellList, int cellIndex, int rowIndex) {
        int cellTotal = 0;
        Row currentRow;
        if (sheet.getLastRowNum() > 0 && sheet.getLastRowNum() >= rowIndex) {
            currentRow = sheet.getRow(rowIndex);
        } else {
            currentRow = sheet.createRow(rowIndex);
        }
        int currentCell = cellIndex;
        for (int i = 0; i < cellList.size(); i++) {
            ExportCell exportCell = cellList.get(i);
            Cell       cell       = currentRow.createCell(currentCell);
            cell.setCellStyle(excelStyle.tableHeadStyle());
            cell.setCellValue(exportCell.getName());
            /**
             * 没有下级的时候
             */
            if (exportCell.getList() == null || exportCell.getList().size() == 0) {
                //设置列宽度
                sheet.setColumnWidth(cell.getColumnIndex(), exportCell.getWidth());
                currentCell++;
                cellTotal++;
            } else {
                /**
                 * 处理下级
                 */
                int total = insertTableHead(exportCell.getList(), currentCell, rowIndex + 1);
                currentCell += total;
                cellTotal += total;
            }
            //合并的信息
            if (exportCell.getMergeCell() > 1 || exportCell.getMergeRow() > 1) {
                sheet.addMergedRegion(new CellRangeAddress(currentRow.getRowNum(), currentRow.getRowNum() + exportCell.getMergeRow() - 1, cell.getColumnIndex(), cell.getColumnIndex() + exportCell.getMergeCell() - 1));
            }
        }
        return cellTotal;
    }


}
