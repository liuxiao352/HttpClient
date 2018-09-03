package com.github.httpclient;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @data 2018-09-03
 * @desc 线程池管理
 */

public class ThreadPoolManager {

    //把任务添加到请求队列中
    private LinkedBlockingDeque<Runnable> mQueue = new LinkedBlockingDeque<>();
    //添加任务
    public void execute(Runnable runnable){
        if (runnable != null) {
            mQueue.add(runnable);
        }
    }
    //把队列中的任务放到线程池
    private ThreadPoolExecutor mThreadPoolExecutor;
    private ThreadPoolManager(){
        mThreadPoolExecutor = new ThreadPoolExecutor(4, 20, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), mRejectedExecutionHandler);
        //开启传送带, 让程序运行起来
        mThreadPoolExecutor.execute(runnable);
    }

    //如果在15秒内 任务被抛弃
    private RejectedExecutionHandler mRejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            //runnable 被抛弃的任务
            try {
                mQueue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    //让他们工作起来
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while(true){
                Runnable runnable = null;
                try {
                    runnable = mQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (runnable != null){
                    mThreadPoolExecutor.execute(runnable);
                }
            }
        }
    };

    //单利模式
    public static ThreadPoolManager ourInstance = new ThreadPoolManager();
    public static ThreadPoolManager getOurInstance(){
        return ourInstance;
    }

}
