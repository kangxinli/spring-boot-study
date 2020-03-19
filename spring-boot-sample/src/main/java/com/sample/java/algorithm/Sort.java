package com.sample.java.algorithm;

/**
 *
 * 排序算法
 *
 */
public final class Sort {
	
	/**
     * 冒泡排序
     *
     * 参数说明：
     *     a -- 待排序的数组
     *     n -- 数组的长度
     */
    public static void bubbleSort1(int[] a, int n) {
        int i,j;

        for (i=n-1; i>0; i--) {
            // 将a[0...i]中最大的数据放在末尾
            for (j=0; j<i; j++) {

                if (a[j] > a[j+1]) {
                    // 交换a[j]和a[j+1]
                    int tmp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = tmp;
                }
            }
        }
    }

    /**
     * 冒泡排序(改进版)
     *
     * 参数说明：
     *     a -- 待排序的数组
     *     n -- 数组的长度
     */
    public static void bubbleSort2(int[] a, int n) {
        int i,j;
        int flag;                 // 标记

        for (i=n-1; i>0; i--) {

            flag = 0;            // 初始化标记为0
            // 将a[0...i]中最大的数据放在末尾
            for (j=0; j<i; j++) {
                if (a[j] > a[j+1]) {
                    // 交换a[j]和a[j+1]
                    int tmp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = tmp;

                    flag = 1;    // 若发生交换，则设标记为1
                }
            }

            if (flag==0)
                break;            // 若没发生交换，则说明数列已有序。
        }
    }
	
	/**
	 * 快速排序
	 * 
	 */
	public static void quicksort(int[] a) {
		quickSort(a, 0, a.length - 1);
	}
	
	

	/**
     * 快速排序
     *
     * 参数说明：
     *     a -- 待排序的数组
     *     l -- 数组的左边界(例如，从起始位置开始排序，则l=0)
     *     r -- 数组的右边界(例如，排序截至到数组末尾，则r=a.length-1)
     */
	private static void quickSort(int[] a, int l, int r) {
		if (l < r) {
            int i,j,x;

            i = l;
            j = r;
            x = a[i];
            while (i < j) {
                while(i < j && a[j] > x)
                    j--; // 从右向左找第一个小于x的数
                if(i < j)
                    a[i++] = a[j];
                while(i < j && a[i] < x)
                    i++; // 从左向右找第一个大于x的数
                if(i < j)
                    a[j--] = a[i];
            }
            a[i] = x;
            quickSort(a, l, i-1); /* 递归调用 */
            quickSort(a, i+1, r); /* 递归调用 */
        }
	}
	
	/**
     * 直接插入排序
     *
     * 参数说明：
     *     a -- 待排序的数组
     *     n -- 数组的长度
     */
    public static void insertSort(int[] a, int n) {
        int i, j, k;

        for (i = 1; i < n; i++) {

            //为a[i]在前面的a[0...i-1]有序区间中找一个合适的位置
            for (j = i - 1; j >= 0; j--)
                if (a[j] < a[i])
                    break;

            //如找到了一个合适的位置
            if (j != i - 1) {
                //将比a[i]大的数据向后移
                int temp = a[i];
                for (k = i - 1; k > j; k--)
                    a[k + 1] = a[k];
                //将a[i]放到正确位置上
                a[k + 1] = temp;
            }
        }
    }
    
    /**
     * 选择排序
     *
     * 参数说明：
     *     a -- 待排序的数组
     *     n -- 数组的长度
     */
    public static void selectSort(int[] a, int n) {
        int i;        // 有序区的末尾位置
        int j;        // 无序区的起始位置
        int min;    // 无序区中最小元素位置

        for(i=0; i<n; i++) {
            min=i;

            // 找出"a[i+1] ... a[n]"之间的最小元素，并赋值给min。
            for(j=i+1; j<n; j++) {
                if(a[j] < a[min])
                    min=j;
            }

            // 若min!=i，则交换 a[i] 和 a[min]。
            // 交换之后，保证了a[0] ... a[i] 之间的元素是有序的。
            if(min != i) {
                int tmp = a[i];
                a[i] = a[min];
                a[min] = tmp;
            }
        }
    }
    
    /** 
     * (最大)堆的向下调整算法
     *
     * 注：数组实现的堆中，第N个节点的左孩子的索引值是(2N+1)，右孩子的索引是(2N+2)。
     *     其中，N为数组下标索引值，如数组中第1个数对应的N为0。
     *
     * 参数说明：
     *     a -- 待排序的数组
     *     start -- 被下调节点的起始位置(一般为0，表示从第1个开始)
     *     end   -- 截至范围(一般为数组中最后一个元素的索引)
     */
    public static void maxHeapDown(int[] a, int start, int end) {
        int c = start;            // 当前(current)节点的位置
        int l = 2*c + 1;        // 左(left)孩子的位置
        int tmp = a[c];            // 当前(current)节点的大小

        for (; l <= end; c=l,l=2*l+1) {
            // "l"是左孩子，"l+1"是右孩子
            if ( l < end && a[l] < a[l+1])
                l++;        // 左右两孩子中选择较大者，即m_heap[l+1]
            if (tmp >= a[l])
                break;        // 调整结束
            else {            // 交换值
                a[c] = a[l];
                a[l]= tmp;
            }
            
            printSorting(a);
        }
    }

    /**
     * 堆排序(从小到大)
     *
     * 参数说明：
     *     a -- 待排序的数组
     *     n -- 数组的长度
     */
    public static void heapSortAsc(int[] a, int n) {
        int i,tmp;

        // 从(n/2-1) --> 0逐次遍历。遍历之后，得到的数组实际上是一个(最大)二叉堆。
        for (i = n / 2 - 1; i >= 0; i--)
            maxHeapDown(a, i, n-1);

        // 从最后一个元素开始对序列进行调整，不断的缩小调整的范围直到第一个元素
        for (i = n - 1; i > 0; i--) {
            // 交换a[0]和a[i]。交换后，a[i]是a[0...i]中最大的。
            tmp = a[0];
            a[0] = a[i];
            a[i] = tmp;
            // 调整a[0...i-1]，使得a[0...i-1]仍然是一个最大堆。
            // 即，保证a[i-1]是a[0...i-1]中的最大值。
            maxHeapDown(a, 0, i-1);
        }
    }

    /** 
     * (最小)堆的向下调整算法
     *
     * 注：数组实现的堆中，第N个节点的左孩子的索引值是(2N+1)，右孩子的索引是(2N+2)。
     *     其中，N为数组下标索引值，如数组中第1个数对应的N为0。
     *
     * 参数说明：
     *     a -- 待排序的数组
     *     start -- 被下调节点的起始位置(一般为0，表示从第1个开始)
     *     end   -- 截至范围(一般为数组中最后一个元素的索引)
     */
    public static void minHeapDown(int[] a, int start, int end) {
        int c = start;            // 当前(current)节点的位置
        int l = 2*c + 1;        // 左(left)孩子的位置
        int tmp = a[c];            // 当前(current)节点的大小

        for (; l <= end; c=l,l=2*l+1) {
            // "l"是左孩子，"l+1"是右孩子
            if ( l < end && a[l] > a[l+1])
                l++;        // 左右两孩子中选择较小者
            if (tmp <= a[l])
                break;        // 调整结束
            else {            // 交换值
                a[c] = a[l];
                a[l]= tmp;
            }
            
            printSorting(a);
        }
    }

    /**
     * 堆排序(从大到小)
     *
     * 参数说明：
     *     a -- 待排序的数组
     *     n -- 数组的长度
     */
    public static void heapSortDesc(int[] a, int n) {
        int i,tmp;

        // 从(n/2-1) --> 0逐次遍历每。遍历之后，得到的数组实际上是一个最小堆。
        for (i = n / 2 - 1; i >= 0; i--)
            minHeapDown(a, i, n-1);

        // 从最后一个元素开始对序列进行调整，不断的缩小调整的范围直到第一个元素
        for (i = n - 1; i > 0; i--) {
            // 交换a[0]和a[i]。交换后，a[i]是a[0...i]中最小的。
            tmp = a[0];
            a[0] = a[i];
            a[i] = tmp;
            // 调整a[0...i-1]，使得a[0...i-1]仍然是一个最小堆。
            // 即，保证a[i-1]是a[0...i-1]中的最小值。
            minHeapDown(a, 0, i-1);
        }
    }
    
    /**
     * 将一个数组中的两个相邻有序区间合并成一个
     *
     * 参数说明：
     *     a -- 包含两个有序区间的数组
     *     start -- 第1个有序区间的起始地址。
     *     mid   -- 第1个有序区间的结束地址。也是第2个有序区间的起始地址。
     *     end   -- 第2个有序区间的结束地址。
     */
    public static void merge(int[] a, int start, int mid, int end) {
        int[] tmp = new int[end-start+1];    // tmp是汇总2个有序区的临时区域
        int i = start;            // 第1个有序区的索引
        int j = mid + 1;        // 第2个有序区的索引
        int k = 0;                // 临时区域的索引

        while(i <= mid && j <= end) {
            if (a[i] <= a[j])
                tmp[k++] = a[i++];
            else
                tmp[k++] = a[j++];
        }

        while(i <= mid)
            tmp[k++] = a[i++];

        while(j <= end)
            tmp[k++] = a[j++];

        // 将排序后的元素，全部都整合到数组a中。
        for (i = 0; i < k; i++)
            a[start + i] = tmp[i];

        tmp=null;
    }

    /**
     * 归并排序(从上往下)
     *
     * 参数说明：
     *     a -- 待排序的数组
     *     start -- 数组的起始地址
     *     endi -- 数组的结束地址
     */
    public static void mergeSortUp2Down(int[] a, int start, int end) {
        if(a==null || start >= end)
            return ;

        int mid = (end + start)/2;
        mergeSortUp2Down(a, start, mid); // 递归排序a[start...mid]
        mergeSortUp2Down(a, mid+1, end); // 递归排序a[mid+1...end]

        // a[start...mid] 和 a[mid...end]是两个有序空间，
        // 将它们排序成一个有序空间a[start...end]
        merge(a, start, mid, end);
    }


    /**
     * 对数组a做若干次合并：数组a的总长度为len，将它分为若干个长度为gap的子数组；
     *             将"每2个相邻的子数组" 进行合并排序。
     *
     * 参数说明：
     *     a -- 待排序的数组
     *     len -- 数组的长度
     *     gap -- 子数组的长度
     */
    public static void mergeGroups(int[] a, int len, int gap) {
        int i;
//        int twolen = 2 * gap;    // 两个相邻的子数组的长度

        // 将"每2个相邻的子数组" 进行合并排序。
        for(i = 0; i+2*gap-1 < len; i+=(2*gap))
            merge(a, i, i+gap-1, i+2*gap-1);

        // 若 i+gap-1 < len-1，则剩余一个子数组没有配对。
        // 将该子数组合并到已排序的数组中。
        if ( i+gap-1 < len-1)
            merge(a, i, i + gap - 1, len - 1);
    }

    /**
     * 归并排序(从下往上)
     *
     * 参数说明：
     *     a -- 待排序的数组
     */
    public static void mergeSortDown2Up(int[] a) {
        if (a==null)
            return ;

        for(int n = 1; n < a.length; n*=2)
            mergeGroups(a, a.length, n);
    }

	public static void checkSort(Integer[] a) {
		for (int i = 0; i < a.length; i++)
			if (a[i].intValue() != i)
				System.out.println("Error at " + i);
		System.out.println("Finished checksort");
	}

	public static void main(String[] args) {
		
        int num[] = {30,40,60,10,20,50};

        System.out.println("====== quick sort ======");
        int[] a1 = num.clone();
        printbefore(a1);
        quickSort(a1, 0, a1.length-1);
        printafter(a1);
        
        System.out.println("====== bubble sort ======");
        int[] a2 = num.clone();
        printbefore(a2);
        bubbleSort1(a2, a2.length);
        //bubbleSort2(a2, a2.length);
        printafter(a2);
        
        System.out.println("====== insert sort ======");
        int[] a3 = num.clone();
        printbefore(a3);
        insertSort(a3, a3.length);
        printafter(a3);
        
        System.out.println("====== select sort ======");
        int[] a4 = num.clone();
        printbefore(a4);
        selectSort(a4, a4.length);
        printafter(a4);
        
        System.out.println("====== heap sort ======");
        int a5[] = {20,30,90,40,70,110,60,10,100,50,80};
        printbefore(a5);
        heapSortAsc(a5, a5.length);            // 升序排列
        //heapSortDesc(a5, a5.length);        // 降序排列
        printafter(a5);
        
        System.out.println("====== merge sort ======");
        int a6[] = {80,30,60,40,20,10,50,70};
        printbefore(a6);
        mergeSortUp2Down(a6, 0, a6.length-1);        // 归并排序(从上往下)
        //mergeSortDown2Up(a6);                    // 归并排序(从下往上)
        printafter(a6);
	}
	
	public static void printbefore(int[] a) {
		int i;
		System.out.printf("before sort:");
        for (i=0; i<a.length; i++)
            System.out.printf("%d ", a[i]);
        System.out.printf("\n");
	}
	
	public static void printafter(int[] a) {
		int i;
		System.out.printf("after  sort:");
        for (i=0; i<a.length; i++)
            System.out.printf("%d ", a[i]);
        System.out.printf("\n");
	}
	
	public static void printSorting(int[] a) {
		int i;
		System.out.printf("sorting....:");
        for (i=0; i<a.length; i++)
            System.out.printf("%d ", a[i]);
        System.out.printf("\n");
	}
}
