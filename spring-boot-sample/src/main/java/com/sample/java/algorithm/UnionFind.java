package com.sample.java.algorithm;

import java.util.Arrays;
import java.util.Scanner;

public class UnionFind {
	
	@SuppressWarnings("resource")
	public static void main(String[] args)   
    {  
        //System.out.println(Long.MAX_VALUE);  
        Scanner jin = new Scanner(System.in); 
        int N = jin.nextInt();  
        UF uf = new UF(N);  
        while (jin.hasNext()) {  
            int p = jin.nextInt();  
            int q = jin.nextInt();  
            if (uf.connected(p, q)) {  
                continue;  
            }  
            uf.union(p, q);  
        }  
        System.out.println(uf.count());  
          
        System.out.println(uf);  
          
    }

}

class UF  
{  
    private int[] id;  
    private int[] size;  
    private int count;  
      
    public UF(int N)  
    {  
        count = N;  
        id = new int[N];  
        size = new int[N];  
        for (int i = 0; i < N; i++) {  
            id[i] = i; size[i] = 1;  
        }  
    }  
    public int count(){  
        return count;  
    }  
    public int find(int p){  
        while (p != id[p]) {  
            p = id[p];  
        }  
        return p;  
    }  
    public boolean connected(int p, int q){  
        return find(p) == find(q);  
    }  
    public void union(int p, int q){  
        int proot = find(p);  
        int qroot = find(q);  
          
        if (proot == qroot) {  
            return;  
        }  
        if (size[proot] < size[qroot]) { id[proot] = qroot; size[qroot] += size[proot];}  
        else {id[qroot] = proot; size[proot] += size[qroot];}  
        count--;  
    }  
    public String toString()  
    {  
        return Arrays.toString(id);  
    }  
}
