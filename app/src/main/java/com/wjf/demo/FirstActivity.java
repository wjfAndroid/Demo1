package com.wjf.demo;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;

public class FirstActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        int[] ints = {1, 3, 5, 7, 9, 11, 13};
        int result = jump(ints, 5);
        System.out.println("FirstActivity result = " + result);
        sort();
    }
//二分查找   折半查找
    public int jump(int[] ints, int a) {
        int left = 0;
        int right = ints.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (a == ints[mid]) {
                return mid;
            } else if (a > ints[mid]) {
                left = mid + 1;
            } else if (a < ints[mid]) {
                right = mid - 1;
            }
        }
        return -1;
    }
//冒泡排序
    public void sort() {
        int[] arr = {6, 3, 8, 2, 9, 1};
        for (int i = 0; i < arr.length - 1; i++) {//外层循环控制排序趟数
            for (int j = 0; j < arr.length - 1 - i; j++) {//内层循环控制每一趟排序多少次
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            for (int q = 0; q < arr.length ; q++) {
                System.out.print(arr[q]);
            }
            System.out.print("--------------------");
        }
    }
}
