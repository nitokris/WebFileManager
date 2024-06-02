package com.nitokrisalpha.lock;

public class FileLockImpl implements DistributedLock {



    @Override
    public boolean tryLock() {
        return false;
    }
}
