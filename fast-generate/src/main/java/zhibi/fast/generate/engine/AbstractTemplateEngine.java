package zhibi.fast.generate.engine;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import zhibi.fast.generate.build.Configuration;
import zhibi.fast.generate.config.GlobalConfig;
import zhibi.fast.generate.config.TemplateConfig;
import zhibi.fast.generate.dto.FileOutPath;
import zhibi.fast.generate.dto.TableEntity;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 生成文件的模板
 *
 * @author 执笔
 * @date 2019/4/18 21:09
 */
@Slf4j
public abstract class AbstractTemplateEngine {
    /**
     * 配置信息
     */
    private Configuration configuration;
    /**
     * 输出文件夹
     */
    private FileOutPath   fileOutPath;
    /**
     * 需要导入的包名
     */
    private List<String>  importPackages;

    /**
     * <p>
     * 模板引擎初始化
     * </p>
     */
    public AbstractTemplateEngine(Configuration configuration, FileOutPath fileOutPath) {
        this.configuration = configuration;
        this.fileOutPath = fileOutPath;
    }


    /**
     * <p>
     * 输出 java xml 文件
     * </p>
     */
    public AbstractTemplateEngine batchOutput() {
        try {
            List<TableEntity> tableEntityList = configuration.getTableEntityList();
            for (TableEntity tableEntity : tableEntityList) {
                importPackages = new ArrayList<>();
                //包信息
                tableEntity.getColumnFields().forEach(item -> {
                    String pkg = item.getColumnType().getPkg();
                    if (StringUtils.isNotEmpty(pkg) && !importPackages.contains(pkg)) {
                        importPackages.add(pkg);
                    }
                });
                Map<String, Object> objectMap      = getObjectMap(tableEntity);
                TemplateConfig      templateConfig = configuration.getTemplateConfig();
                String              entityName     = tableEntity.getEntityName();
                if (null != entityName) {
                    // entity.java
                    String entityFile = fileOutPath.getEntityPath() + tableEntity.getEntityName() + suffixJava();
                    writer(objectMap, templateFilePath(templateConfig.getEntity()), entityFile);
                    // MpMapper.java
                    String mapperFile = fileOutPath.getMapperPath() + tableEntity.getMapperName() + suffixJava();
                    writer(objectMap, templateFilePath(templateConfig.getMapper()), mapperFile);
                    //Mapper.xml
                    String xmlFile = fileOutPath.getXmlPath() + tableEntity.getXmlName() + suffixXml();
                    writer(objectMap, templateFilePath(templateConfig.getXml()), xmlFile);
                    // MpService.java
                    String serviceFile = fileOutPath.getServicePath() + tableEntity.getServiceName() + suffixJava();
                    writer(objectMap, templateFilePath(templateConfig.getService()), serviceFile);
                    // MpServiceImpl.java
                    String implFile = fileOutPath.getServiceImplPath() + tableEntity.getServiceImplName() + suffixJava();
                    writer(objectMap, templateFilePath(templateConfig.getServiceImpl()), implFile);
                    // MpController.java
                    String controllerFile = fileOutPath.getControllerPath() + tableEntity.getControllerName() + suffixJava();
                    writer(objectMap, templateFilePath(templateConfig.getController()), controllerFile);
                }
            }
        } catch (Exception e) {
            log.error("无法创建文件，请检查配置信息！", e);
        }
        return this;
    }


    /**
     * <p>
     * 处理输出目录
     * </p>
     */
    public AbstractTemplateEngine mkdirs() throws IllegalAccessException {
        Field[] fields = FileOutPath.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String dirPath = field.get(fileOutPath).toString();
            File   dir     = new File(dirPath);
            if (!dir.exists()) {
                boolean result = dir.mkdirs();
                if (result) {
                    log.info("创建目录： [" + dirPath + "]");
                }
            }
        }
        return this;
    }


    /**
     * <p>
     * 打开输出目录
     * </p>
     */
    public void open() {
        String outDir = configuration.getGlobalConfig().getOutputDir();
        if (configuration.getGlobalConfig().isOpen() && !StringUtils.isEmpty(outDir)) {
            try {
                String osName = System.getProperty("os.name");
                if (osName != null) {
                    if (osName.contains("Mac")) {
                        Runtime.getRuntime().exec("open " + outDir);
                    } else if (osName.contains("Windows")) {
                        Runtime.getRuntime().exec("cmd /c start " + outDir);
                    } else {
                        log.info("文件输出目录:" + outDir);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * <p>
     * 渲染对象 MAP 信息
     * </p>
     *
     * @param tableEntity 表信息对象
     * @return
     */
    private Map<String, Object> getObjectMap(TableEntity tableEntity) {
        Map<String, Object> objectMap = new HashMap<>(30);
        objectMap.put("config", configuration);
        objectMap.put("package", configuration.getPackInfo());
        GlobalConfig globalConfig = configuration.getGlobalConfig();
        objectMap.put("author", globalConfig.getAuthor());
        objectMap.put("date", DateFormatUtils.format(new Date(), "yyyy-MM-dd  HH:mm:ss"));
        objectMap.put("table", tableEntity);
        objectMap.put("entity", tableEntity.getEntityName());
        objectMap.put("superEntityClassPackage", configuration.getStrategyConfig().getSuperEntityClass());
        objectMap.put("superEntityClass", getSuperClassName(configuration.getStrategyConfig().getSuperEntityClass()));
        objectMap.put("superMapperClassPackage", configuration.getStrategyConfig().getSuperMapperClass());
        objectMap.put("superMapperClass", getSuperClassName(configuration.getStrategyConfig().getSuperMapperClass()));
        objectMap.put("superServiceClassPackage", configuration.getStrategyConfig().getSuperServiceClass());
        objectMap.put("superServiceClass", getSuperClassName(configuration.getStrategyConfig().getSuperServiceClass()));
        objectMap.put("superServiceImplClassPackage", configuration.getStrategyConfig().getSuperServiceImplClass());
        objectMap.put("superServiceImplClass", getSuperClassName(configuration.getStrategyConfig().getSuperServiceImplClass()));
        objectMap.put("superControllerClassPackage", configuration.getStrategyConfig().getSuperControllerClass());
        objectMap.put("superControllerClass", getSuperClassName(configuration.getStrategyConfig().getSuperControllerClass()));
        objectMap.put("importPackages", importPackages);
        return objectMap;
    }


    /**
     * 获取类名
     *
     * @param classPath
     * @return
     */
    private String getSuperClassName(String classPath) {
        if (StringUtils.isEmpty(classPath)) {
            return null;
        }
        return classPath.substring(classPath.lastIndexOf(".") + 1);
    }

    /**
     * 文件后缀
     */
    private String suffixJava() {
        return ".java";
    }

    /**
     * 文件后缀
     */
    private String suffixXml() {
        return ".xml";
    }


    /**
     * <p>
     * 模板真实文件路径
     * </p>
     *
     * @param filePath 文件路径
     * @return
     */
    public abstract String templateFilePath(String filePath);


    /**
     * <p>
     * 将模板转化成为文件
     * </p>
     *
     * @param objectMap    渲染对象 MAP 信息
     * @param templatePath 模板文件
     * @param outputFile   文件生成的目录
     */
    public abstract void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception;


}
