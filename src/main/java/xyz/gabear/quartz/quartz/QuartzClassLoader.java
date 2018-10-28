package xyz.gabear.quartz.quartz;

import lombok.extern.slf4j.Slf4j;

/**
 * quartz 类加载器,当文件修改后上传此文件
 */
@Slf4j
public class QuartzClassLoader extends ClassLoader {

    /**
     * 定时任务所在的包
     */
    private static final String QUARTZ_JOB_PACKAGE = "xyz.gabear.quartz.job.impl";

    private byte[] clazzData;

    public QuartzClassLoader(byte[] clazzData) {
        this(ClassLoader.getSystemClassLoader(), clazzData);
    }

    public QuartzClassLoader(ClassLoader classLoader, byte[] clazzData) {
        super(classLoader);
        this.clazzData = clazzData;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (needFindClass(name)) {
            return this.findClass(name);
        }
        return super.loadClass(name, resolve);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        log.info("QuartzClassLoader开始加载类:[{}]", name);
        Class<?> loadedClass;
        try {
            loadedClass = defineClass(name, clazzData, 0, clazzData.length);
            if (loadedClass != null) {
                log.info("QuartzClassLoader成功加载类:[{}]", name);
            } else {
                log.info("QuartzClassLoader无法加载类:[{}]", name);
            }
        } catch (NoClassDefFoundError noClassDefFoundError) {
            return super.findClass(name);
        }
        return loadedClass;
    }

    /**
     * 是否需要使用QuartzClassLoader来进行加载类
     *
     * @param name 需要加载的类名
     * @return
     */
    public boolean needFindClass(String name) {
        return name.startsWith(QUARTZ_JOB_PACKAGE);
    }
}
