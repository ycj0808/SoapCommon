package com.icefire.common.network;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/**
 * 线程池单例模式 
 * @author yangchj
 */
public class ThreadPool {

	private static final int MAX_NUMS=5;
	private static ExecutorService mPool= null;
	private static ThreadPool instance=null;
	
	public ThreadPool(){
		mPool= Executors.newFixedThreadPool(MAX_NUMS);
	}
	
	public static ThreadPool getInstance(){
		if(instance==null){
			instance=new ThreadPool();
		}
		return instance;
	}
	
	public void execute(Runnable r) {
		mPool.execute(r);
	}
	
	/**
	 * 关闭，并等待任务执行完成，不接受新任务
	 */
	public static void shutdown() {
		if (mPool != null) {
			mPool.shutdown();
		}
	}
	
	/**
	 * 关闭，立即关闭，并挂起所有正在执行的线程，不接受新任务
	 */
	public static void shutdownRightnow() {
		if (mPool != null) {
			mPool.shutdownNow();
			try {
				// 设置超时极短，强制关闭所有任务
				mPool.awaitTermination(1, TimeUnit.MICROSECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
