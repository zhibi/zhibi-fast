package zhibi.fast.commons.env;

import java.io.File;

/**
 * 清理maven仓库
 *
 * @author 执笔
 * @date 2019/3/11 10:53
 */
public class ClearMaven {

    public ClearMaven(String mavenPath) {
        File mavenFile = new File(mavenPath);
        clearLastUpdate(mavenFile);
        clearEmptyDirectory(mavenFile);
    }

    /**
     * 清理下载失败的文件
     */
    public void clearLastUpdate(File file) {
        if (file != null) {
            if (file.isFile()) {
                if (file.getName().endsWith("lastUpdated")) {
                    file.delete();
                    System.out.println("删除文件：" + file.getAbsolutePath());
                }
            } else {
                File[] files = file.listFiles();
                for (File f : files) {
                    clearLastUpdate(f);
                }
            }
        }
    }

    /**
     * 清理空文件夹
     */
    public void clearEmptyDirectory(File file) {
        if (file != null && file.isDirectory()) {
            File[] fs = file.listFiles();
            if (fs != null && fs.length == 0) {
                File parentFile = file.getParentFile();
                System.out.println("删除文件夹：" + file.getAbsolutePath());
                file.delete();
                clearEmptyDirectory(parentFile);
            } else {
                for (File fc : fs) {
                    clearEmptyDirectory(fc);
                }
            }
        }
    }

    public static void main(String[] args) {
        ClearMaven clearMaven = new ClearMaven("F:\\Repository-Maven");

    }


}
