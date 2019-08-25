package com.katana.tenement.web.app.controller;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.System.out;

public class CountDownDemo implements Runnable {
    static CountDownLatch countDownLatch = new CountDownLatch(1);
    static CountDownLatch countDown = new CountDownLatch(6);
    static ExecutorService executorService = Executors.newFixedThreadPool(10);
    @Override
    public void run() {
            out.println("选手"+ Thread.currentThread().getName()+"正在等待裁判指令");
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.println("选手"+ Thread.currentThread().getName()+"开跑");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println("选手"+Thread.currentThread().getName()+"到达终点");
            countDown.countDown();
        }

    public static void main(String[] args) {
        for(int i = 0;i<6;i++){
            CountDownDemo countDownDemo = new CountDownDemo();
            executorService.execute(countDownDemo);
        }
        out.println("裁判即将发出指令");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        countDownLatch.countDown();
        out.println("裁判发出指令");
        try {
            countDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.println("裁判");
    }
}
