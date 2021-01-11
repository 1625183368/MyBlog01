package com.mxx.blogs.locks;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BLogsWriteLock {

    private BLogsWriteLock() {
    }

    private static BLogsWriteLock bLogsWriteLock = new BLogsWriteLock();

    public static BLogsWriteLock getInstance() {
        return bLogsWriteLock;
    }

    private ReentrantReadWriteLock lock;

    public ReentrantReadWriteLock getLock() {

        if (lock == null) {
            synchronized (this) {
                if (lock == null) {
                    this.lock = new ReentrantReadWriteLock();
                }
            }
        }
        return lock;
    }

    public void setLock(ReentrantReadWriteLock lock) {
        this.lock = lock;
    }
}
