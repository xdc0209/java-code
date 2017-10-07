package com.xdc.basic.algorithm.basic.sort.exchange.sort;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.xdc.basic.algorithm.basic.sort.exchange.sort.QuickSort;

public class QuickSortTest
{
    @Test
    public void sortAll()
    {
        sortOne(64, 44, 11, 50, 31, 73, 65, 42, 55, 17, 22, 85, 25, 71, 27, 45, 79, 23, 91, 0);
        sortOne(44, 22, 46, 47, 82, 76, 96, 82, 79, 35, 8, 75, 85, 84, 78, 98, 59, 72, 81, 44);
        sortOne(72, 68, 86, 25, 80, 73, 76, 0, 93, 17, 26, 96, 12, 23, 36, 48, 49, 62, 35, 83);
        sortOne(25, 43, 1, 4, 17, 76, 82, 45, 51, 80, 32, 5, 7, 72, 1, 81, 60, 96, 81, 66);
        sortOne(59, 24, 64, 63, 66, 23, 60, 90, 87, 25, 77, 7, 8, 18, 89, 42, 93, 54, 74, 93);
        sortOne(1, 12, 94, 40, 44, 74, 99, 9, 44, 19, 7, 20, 28, 65, 68, 94, 20, 28, 38, 68);
        sortOne(71, 30, 97, 88, 71, 37, 27, 24, 80, 29, 31, 25, 15, 12, 73, 8, 94, 64, 21, 97);
        sortOne(4, 82, 26, 54, 83, 27, 64, 35, 11, 74, 5, 50, 49, 32, 30, 80, 52, 22, 35, 13);
        sortOne(18, 97, 70, 7, 83, 95, 64, 43, 83, 0, 85, 35, 39, 85, 76, 98, 90, 64, 59, 43);
        sortOne(59, 67, 63, 33, 30, 26, 12, 50, 68, 42, 37, 9, 63, 78, 78, 66, 60, 74, 4, 41);
        sortOne(88, 88, 65, 1, 56, 28, 24, 82, 94, 58, 25, 43, 50, 86, 32, 15, 52, 16, 15, 17);
        sortOne(30, 92, 3, 5, 96, 89, 65, 42, 35, 74, 65, 71, 61, 96, 5, 41, 24, 24, 61, 44);
        sortOne(59, 46, 70, 11, 9, 35, 6, 65, 48, 19, 12, 86, 91, 1, 13, 61, 39, 83, 9, 5);
        sortOne(52, 20, 91, 86, 12, 20, 23, 18, 49, 35, 34, 49, 0, 64, 83, 61, 85, 2, 60, 96);
        sortOne(66, 42, 23, 32, 80, 98, 96, 11, 48, 22, 31, 0, 78, 35, 64, 63, 99, 91, 15, 9);
        sortOne(69, 71, 45, 38, 43, 74, 36, 0, 6, 6, 30, 15, 24, 7, 86, 4, 3, 22, 91, 31);
        sortOne(63, 45, 42, 86, 26, 79, 15, 9, 42, 80, 65, 9, 56, 41, 42, 99, 3, 55, 15, 3);
        sortOne(89, 19, 15, 86, 15, 13, 1, 62, 70, 39, 0, 91, 19, 26, 90, 1, 90, 78, 56, 6);
        sortOne(89, 26, 17, 82, 24, 28, 47, 45, 28, 67, 44, 40, 34, 87, 28, 60, 37, 6, 65, 14);
        sortOne(88, 77, 5, 9, 1, 94, 96, 23, 77, 28, 87, 38, 81, 55, 24, 48, 88, 57, 95, 90);
        sortOne(98, 36, 5, 6, 32, 6, 70, 32, 77, 66, 86, 43, 71, 88, 85, 31, 17, 66, 6, 17);
        sortOne(86, 54, 42, 63, 49, 79, 93, 54, 69, 88, 79, 35, 85, 33, 68, 16, 45, 57, 93, 82);
        sortOne(54, 47, 45, 6, 7, 18, 36, 41, 49, 98, 23, 70, 98, 0, 23, 38, 31, 11, 43, 6);
        sortOne(96, 45, 52, 50, 98, 10, 73, 33, 49, 8, 60, 67, 4, 28, 91, 4, 38, 26, 89, 34);
        sortOne(11, 34, 93, 64, 71, 94, 59, 5, 49, 94, 35, 89, 15, 87, 93, 46, 13, 92, 33, 71);
        sortOne(91, 95, 13, 39, 46, 95, 49, 43, 30, 0, 53, 7, 48, 96, 80, 45, 50, 15, 85, 21);
        sortOne(95, 70, 79, 72, 23, 74, 57, 30, 63, 60, 40, 0, 75, 83, 80, 36, 40, 31, 38, 92);
        sortOne(89, 44, 13, 66, 31, 50, 83, 30, 78, 0, 91, 35, 68, 2, 20, 64, 37, 61, 78, 50);
        sortOne(0, 64, 63, 45, 63, 75, 70, 61, 21, 72, 21, 15, 75, 56, 15, 60, 43, 42, 2, 51);
        sortOne(12, 45, 50, 36, 81, 98, 86, 93, 25, 22, 1, 84, 46, 56, 61, 79, 80, 2, 25, 23);
    }

    public void sortOne(int... originArray)
    {
        int[] sortedArrayByJavaLib = Arrays.copyOf(originArray, originArray.length);
        int[] sortedArrayByMyLib = Arrays.copyOf(originArray, originArray.length);

        Arrays.sort(sortedArrayByJavaLib);
        QuickSort.sort(sortedArrayByMyLib);

        Assert.assertArrayEquals(
                String.format("The two array is not same, array1 is %s, array2 is %s.",
                        Arrays.toString(sortedArrayByJavaLib), Arrays.toString(sortedArrayByMyLib)),
                sortedArrayByJavaLib, sortedArrayByMyLib);
    }
}
