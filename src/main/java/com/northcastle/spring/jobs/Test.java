package com.northcastle.spring.jobs;

class Test {
	public static void test(Object o){
		System.out.println("Object");
	}
	public static void test(String o){
		System.out.println("String");
	}
	//public static void main(String[] args) {
	//test(null);
	//}

	public static void main(String[] args) {
		var console = System.console();
		if(console != null) {
			console.format("%d %<x", 10);
		}
	}
}
