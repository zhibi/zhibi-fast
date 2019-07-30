package zhibi.fast.poi.excel.export.core;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import zhibi.fast.poi.excel.export.annotation.Excel;
import zhibi.fast.poi.excel.export.entity.ExportCell;
import zhibi.fast.poi.excel.export.enums.ExcelValueTypeEnum;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * 核心代码
 *
 * @author 执笔
 * @date 2018/11/23 21:53
 */
public class ExcelCellHandler {

    /**
     * 得到实际类型
     *
     * @param type
     * @return
     */
    public static Class<?> getActualType(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType pt           = (ParameterizedType) type;
            Type              typeArgument = pt.getActualTypeArguments()[0];
            return (Class<?>) typeArgument;
        }
        return null;
    }

    /**
     * 根据实体类型得到所有需要导出的列属性
     *
     * @param entity
     * @return
     */
    public List<ExportCell> getAllExportCell(Class<?> entity) {
        List<ExportCell> list = new ArrayList<>(0);
        if (entity.isAnnotation() || entity.isInterface()) {
            return list;
        }
        //  java基本类型
        if (ClassUtils.isPrimitiveOrWrapper(entity)) {
            ExportCell exportCell = new ExportCell();
            exportCell.setName(entity.getSimpleName());
            list.add(exportCell);
            return list;
        }
        List<Field> fieldList = FieldUtils.getAllFieldsList(entity);
        if (fieldList.size() == 0) {
            return list;
        }
        /**
         * 把属性转换成ExportCell
         */
        fieldList.forEach(field -> {
            ExportCell exportCell = fieldToExportCell(field);
            if (null != exportCell) {
                list.add(exportCell);
            }
        });
        //对list进行排序
        list.sort(Comparator.comparing(ExportCell::getSort));
        countRowMerge(list);
        return list;
    }

    /**
     * 把属性转换成ExportCell
     *
     * @param field
     * @return
     */
    private ExportCell fieldToExportCell(Field field) {
        if (!field.isAnnotationPresent(Excel.class)) {
            return null;
        }
        ExportCell exportCell = new ExportCell().setField(field);
        Excel      excel      = handleAnnotation(exportCell, field);
        if (field.getType().isEnum()) {
            return exportCell;
        }
        /**
         * 不是文本类型的时候才处理
         */
        if (!excel.valueType().equals(ExcelValueTypeEnum.TEXT)) {
            // 集合类型
            if (Collection.class.isAssignableFrom(field.getType())) {
                /**
                 * 得到数组类型的实际类型
                 */
                Class<?>         actualType = getActualType(field.getGenericType());
                List<ExportCell> cellList   = getAllExportCell(actualType);
                exportCell.setList(cellList);
                countCellMerge(exportCell, cellList);
            } else if (!ClassUtils.isPrimitiveOrWrapper(field.getType())) {
                /**
                 * 复合类型的
                 * 需要合并单元格
                 */
                List<ExportCell> cellList = getAllExportCell(field.getType());
                exportCell.setList(cellList);
                countCellMerge(exportCell, cellList);
            }
        }
        return exportCell;
    }

    /**
     * 处理注解
     *
     * @param exportCell
     */
    private Excel handleAnnotation(ExportCell exportCell, Field field) {
        Excel excel = null;
        if (field.isAnnotationPresent(Excel.class)) {
            excel = field.getAnnotation(Excel.class);
            exportCell.setName(excel.value());
            exportCell.setWidth(excel.width());
            exportCell.setCellStyle(excel.cellStyle());
            exportCell.setSort(excel.sort());
            exportCell.setValueSerial(excel.valueSerial());
            exportCell.setValueType(excel.valueType());
            exportCell.setPattern(excel.pattern());
        } else {
            exportCell.setName(field.getName());
        }
        return excel;
    }

    /**
     * 计算列合并属性
     *
     * @param exportCell 需要计算的
     * @param list       下级列表
     */
    private void countCellMerge(ExportCell exportCell, List<ExportCell> list) {
        if (null == list || list.size() == 0) {
            return;
        }
        int cellMerge = 0;
        for (ExportCell cell : list) {
            cellMerge += cell.getMergeCell();
        }
        exportCell.setMergeCell(cellMerge);
        /**
         * 计算子属性的最大行数
         */
        Integer max = list.stream().max(Comparator.comparing(ExportCell::getTotalRow)).get().getTotalRow();
        exportCell.setTotalRow(max + 1);
    }

    /**
     * 计算行合并属性
     *
     * @param list
     */
    private void countRowMerge(List<ExportCell> list) {
        if (null == list || list.size() == 0) {
            return;
        }
        Integer max = list.stream().max(Comparator.comparing(ExportCell::getTotalRow)).get().getTotalRow();
        if (max > 1) {
            for (ExportCell cell : list) {
                if (cell.getList() == null) {
                    cell.setMergeRow(max);
                }
            }
        }
    }
}
