package com.sample.java.algorithm.lru;

public class LRUTest {
	
	public static void main(String[] args) {
        LUR<Integer,Integer> lur = new LUR<>(4);
        for (int i = 0; i < 10; i++){
            lur.put(i,i);
        }
    }

}
