package xyz.gabear.quartz.job.back;

import xyz.gabear.quartz.job.AbstractCustomJob;

/**
 * job 中带事务
 */
public class TransactionJob extends AbstractCustomJob {

    @Override
    public void doExecute(String workDate) {
        // 1、非业务方法

        // 2、业务方法 (此方法建议抽取到一个 Service 中，并且是带有事务的)
        // (此方法调用失败,此业务方法的事务会回滚，但是上一步的非业务方法如果存在insert或update或delete数据库操作，则不会回滚)

        // 3、非业务方法 ( 此方法执行失败，上一步的业务方法和非业务方法如果操作了数据库也不会回滚 )
    }
}
