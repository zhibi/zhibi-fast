package zhibi.fast.commons.utils;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.layout.font.FontProvider;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 执笔
 * @date 2019/6/11 15:47
 */
public class PdfUtils {

    /**
     * html 转换成pdf文件
     *
     * @param html         内容
     * @param outputStream 输出流
     * @throws IOException
     */
    public static void html2Pdf(String html, OutputStream outputStream) throws IOException {
        ConverterProperties props = new ConverterProperties();
        // 编码
        props.setCharset("UFT-8");
        FontProvider fp = new FontProvider();
        fp.addStandardPdfFonts();
        // 设置字体 .ttf 字体所在目录
        String resources = PdfUtils.class.getResource("/fonts").getPath();
        // html中使用的图片等资源目录（图片也可以直接用url或者base64格式而不放到资源里）
        // props.setBaseUri(resources);
        fp.addDirectory(resources);
        props.setFontProvider(fp);
        HtmlConverter.convertToPdf(html, outputStream, props);
    }
}
