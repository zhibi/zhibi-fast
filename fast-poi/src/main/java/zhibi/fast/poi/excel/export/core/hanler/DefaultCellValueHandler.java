package zhibi.fast.poi.excel.export.core.hanler;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.*;
import zhibi.fast.commons.base.RemarkEnum;
import zhibi.fast.poi.excel.export.core.ImageFileLoader;
import zhibi.fast.poi.excel.export.core.serializable.CellValueSerializable;
import zhibi.fast.poi.excel.export.core.serializable.EnumCellValueSerial;
import zhibi.fast.poi.excel.export.core.serializable.NullCellValueSerial;
import zhibi.fast.poi.excel.export.entity.ExportCell;
import zhibi.fast.poi.excel.export.enums.ExcelValueTypeEnum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 默认值处理器
 *
 * @author 执笔
 * @date 2018/11/25 19:08
 */
public class DefaultCellValueHandler implements CellValueHandler {

    /**
     * 图片加载
     */
    private ImageFileLoader imageFileLoader = new ImageFileLoader();

    @Override
    public void insertValue(ExportCell exportCell, Cell cell, Object value) {
        Class<? extends CellValueSerializable> valueSerial = exportCell.getValueSerial();
        ExcelValueTypeEnum                     valueType   = exportCell.getValueType();
        String                                 serial;
        try {
            if (valueSerial.equals(NullCellValueSerial.class)) {
                if (null == value) {
                    serial = NullCellValueSerial.class.newInstance().serial(null);
                } else if (value.getClass().equals(Date.class)) {
                    String pattern = exportCell.getPattern();
                    if (StringUtils.isBlank(pattern)) {
                        pattern = "yyyy-MM-dd HH:mm:ss";
                    }
                    serial = DateFormatUtils.format((Date) value, pattern);
                } else if (value.getClass().equals(LocalDateTime.class)) {
                    String pattern = exportCell.getPattern();
                    if (StringUtils.isBlank(pattern)) {
                        pattern = "yyyy-MM-dd HH:mm:ss";
                    }
                    serial = ((LocalDateTime) value).format(DateTimeFormatter.ofPattern(pattern));
                } else if (value instanceof RemarkEnum) {
                    serial = EnumCellValueSerial.class.newInstance().serial(value);
                } else {
                    serial = String.valueOf(value);
                }
            } else {
                serial = valueSerial.newInstance().serial(value);
            }
            /**
             * 处理图片
             */
            if (valueType.equals(ExcelValueTypeEnum.IMG)) {
                Sheet      sheet            = cell.getSheet();
                Drawing<?> drawingPatriarch = getShapes(sheet);
                int        pictureIndex     = getPictureIndex(serial, sheet.getWorkbook());
                if (pictureIndex > 0) {
                    ClientAnchor anchor = drawingPatriarch.createAnchor(0, 0, 0, 0, cell.getColumnIndex(), cell.getRowIndex(), cell.getColumnIndex() + 1, cell.getRowIndex() + 1);
                    drawingPatriarch.createPicture(anchor, pictureIndex);
                    /**
                     * 设置高
                     */
                    cell.getRow().setHeightInPoints(sheet.getColumnWidthInPixels(cell.getColumnIndex()));
                }
                return;
            }
            cell.setCellValue(serial);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取画布
     *
     * @param sheet
     * @return
     */
    private Drawing<?> getShapes(Sheet sheet) {
        Drawing<?> drawingPatriarch = sheet.getDrawingPatriarch();
        if (drawingPatriarch == null) {
            drawingPatriarch = sheet.createDrawingPatriarch();
        }
        return drawingPatriarch;
    }

    /**
     * 获取图片索引
     *
     * @param imgPath
     * @param workbook
     * @return
     */
    private int getPictureIndex(String imgPath, Workbook workbook) {
        byte[] bytes = imageFileLoader.loadData(imgPath);
        if (bytes == null) {
            return -1;
        }
        return workbook.addPicture(bytes, imageFileLoader.getFileExtendName(bytes));
    }
}
