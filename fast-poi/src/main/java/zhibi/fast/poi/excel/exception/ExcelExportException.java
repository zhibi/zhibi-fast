package zhibi.fast.poi.excel.exception;

/**
 * 导出异常
 *
 * @author 执笔
 * @date 2018/11/23 17:53
 */
public class ExcelExportException extends RuntimeException {

    public ExcelExportException(String message) {
        super(message);
    }

    public ExcelExportException(Throwable cause) {
        super(cause);
    }
}
