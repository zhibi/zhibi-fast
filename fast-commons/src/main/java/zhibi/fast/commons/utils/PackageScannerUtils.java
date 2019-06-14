package zhibi.fast.commons.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * 包扫描工具类
 * @author 执笔
 * @date 2019/4/18 22:00
 */
public class PackageScannerUtils {
    /**
     * 扫描这个包下面所有的类
     */
    public static List<String> getClassNameList(String packName) throws IOException {
        if (StringUtils.isEmpty(packName)) {
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>();
        if (packName.contains(",")) {
            String[] arr = packName.split(",");
            for (String pack : arr) {
                list.addAll(getClassNameList(pack));
            }
            return list;
        }
        return doScan(packName, new ArrayList<>());
    }


    /**
     * doScan函数
     *
     * @param basePackage
     * @param nameList
     * @return
     * @throws IOException
     */
    private static List<String> doScan(String basePackage, List<String> nameList) throws IOException {
        String splashPath = dotToSplash(basePackage);
        URL url = PackageScannerUtils.class.getClassLoader().getResource(splashPath);   //file:/D:/WorkSpace/java/ScanTest/target/classes/com/scan
        if (url == null) return new ArrayList<>();
        String filePath = getRootPath(url);
        List<String> names; // contains the name of the class file. e.g., Apple.class will be stored as "Apple"
        if (isJarFile(filePath)) {// 先判断是否是jar包，如果是jar包，通过JarInputStream产生的JarEntity去递归查询所有类
            names = readFromJarFile(filePath, splashPath);
        } else {
            names = readFromDirectory(filePath);
        }
        assert names != null;
        for (String name : names) {
            if (isClassFile(name)) {
                nameList.add(toFullyQualifiedName(name, basePackage));
            } else {
                doScan(basePackage + "." + name, nameList);
            }
        }
        return nameList;
    }

    private static String toFullyQualifiedName(String shortName, String basePackage) {
        return basePackage + '.' + trimExtension(shortName);
    }

    private static boolean isClassFile(String name) {
        return name.endsWith(".class");
    }

    private static boolean isJarFile(String name) {
        return name.endsWith(".jar");
    }

    private static List<String> readFromJarFile(String jarPath, String splashedPackageName) throws IOException {
        JarInputStream jarIn = new JarInputStream(new FileInputStream(jarPath));
        JarEntry entry = jarIn.getNextJarEntry();
        List<String> nameList = new ArrayList<String>();
        while (null != entry) {
            String name = entry.getName();
            if (name.startsWith(splashedPackageName) && isClassFile(name)) {
                nameList.add(name);
            }
            entry = jarIn.getNextJarEntry();
        }

        return nameList;
    }

    private static List<String> readFromDirectory(String path) {
        File file = new File(path);
        String[] names = file.list();
        if (null == names) {
            return null;
        }
        return Arrays.asList(names);
    }

    /**
     * "file:/home/whf/cn/fh" -> "/home/whf/cn/fh"
     * "jar:file:/home/whf/foo.jar!cn/fh" -> "/home/whf/foo.jar"
     */
    private static String getRootPath(URL url) {
        String fileUrl = url.getFile();
        int pos = fileUrl.indexOf('!');

        if (-1 == pos) {
            return fileUrl;
        }

        return fileUrl.substring(5, pos);
    }

    /**
     * "cn.fh.lightning" -> "cn/fh/lightning"
     *
     * @param name
     * @return
     */
    private static String dotToSplash(String name) {
        return name.replaceAll("\\.", "/");
    }

    /**
     * "com/Apple.class" -> "Apple"
     */
    private static String trimExtension(String name) {
        int pos = name.indexOf('.');
        int index = name.lastIndexOf("/");
        if (-1 != pos) {
            name = name.substring(0, pos);
        }
        if (index != -1) {
            name = name.substring(index + 1);
        }
        return name;
    }
}
