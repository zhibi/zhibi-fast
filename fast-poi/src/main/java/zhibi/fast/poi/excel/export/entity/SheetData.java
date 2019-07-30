package zhibi.fast.poi.excel.export.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import zhibi.fast.poi.excel.export.config.ExcelSheetConfig;

import java.util.List;

/**
 * 存放sheet的配置和数据
 * 相当于一个sheet的所有数据
 * @author 执笔
 * @date 2018/11/23 17:49
 */
@Data
@AllArgsConstructor
public class SheetData {

    private ExcelSheetConfig config;
    private List<?>          data;
}
