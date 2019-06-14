package zhibi.fast.generate.enums;

/**
 * 自动生成策略
 * @author 执笔
 * @date 2019/4/18 21:07
 */
public enum SyncStrategy {

    /**
     * 根据实体自动生成数据库
     */
    ENTITY_TO_DATABASE,
    /**
     * 根据数据库自动生成实体
     */
    DATABASE_TO_ENTITY
}
