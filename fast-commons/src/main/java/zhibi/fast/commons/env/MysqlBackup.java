package zhibi.fast.commons.env;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * mysql备份
 *
 * @author 执笔
 * @date 2019/7/25 17:04
 */
@Slf4j
public class MysqlBackup {

    /**
     * @param hostIp       ip地址，可以是本机也可以是远程
     * @param userName     数据库的用户名
     * @param password     数据库的密码
     * @param savePath     备份的路径
     * @param fileName     备份的文件名
     * @param databaseName 需要备份的数据库的名称
     * @return
     */
    public static void backup(String hostIp, String userName, String password, String savePath, String fileName, String databaseName) {
        fileName += ".sql";
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        //拼接命令行的命令
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mysqldump").append(" --opt").append(" -h").append(hostIp)
                .append(" --user=").append(userName).append(" --password=").append(password)
                .append(" --lock-all-tables=false").append(" --result-file=").append(savePath)
                .append(File.separator).append(fileName).append(" --default-character-set=utf8 ")
                .append(databaseName);
        try {
            //调用外部执行exe文件的javaAPI
            Process process = Runtime.getRuntime().exec(stringBuilder.toString());
            // 0 表示线程正常终止。
            if (process.waitFor() == 0) {
                log.info("【备份成功】 {} - {}", saveFile, fileName);
            }
        } catch (Exception e) {
            log.info("【备份失败】", e);
        }
    }
}
