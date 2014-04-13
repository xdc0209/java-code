package com.xdc.basic.api.thread.extendsthead;

class ThreadExt extends Thread{
	public void run(){
		for(int i = 0; i < 100;i++){
			System.out.println("MyThread-->" + i);
		}
	}			
}
