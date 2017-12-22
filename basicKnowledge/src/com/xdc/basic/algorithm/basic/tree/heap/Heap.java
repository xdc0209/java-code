package com.xdc.basic.algorithm.basic.tree.heap;

import java.util.Arrays;

/**
 * 1 堆：由于堆是一棵完全二叉树，所以在实现上一般使用一维数组，这样仅仅通过数组下标就可以得出父子关系。
 * 1.1 大顶堆：是一棵完全二叉树，且所有非终结点的值均不小于其左右孩子结点的值。注意左右孩子无大小关系。简记为：父大子小。
 * 1.2 小顶堆：是一棵完全二叉树，且所有非终结点的值均不大于其左右孩子结点的值。注意左右孩子无大小关系。简记为：父小子大。
 * 1.3 应用：
 * 1.3.1 推排序。
 * 1.3.2 比如求10亿个数中的最大的前10个数，时时构建只有10个元素的小顶堆，如果比堆顶小，则不处理；如果比堆顶大，则替换堆顶，然后依次下沉到适当的位置。
 * 1.3.3 比如求10亿个数中的最小的前10个数，时时构建只有10个元素的大顶堆，如果比堆顶大，则不处理；如果比堆顶小，则替换堆顶，然后依次下沉到适当的位置。
 * 
 * 注：参考了若干篇文章，千辛万苦地实现堆的结构后，才发现java已经提供了此结构，囧了。其实在Java 1.5版本后就提供了一个具备了小根堆性质的数据结构也就是优先队列java.util.PriorityQueue。PriorityQueue默认是一个小顶堆，然而可以通过传入自定义的Comparator函数来实现大顶堆。
 */
public class Heap<T extends Comparable<T>>
{
    private int type; // 1大顶堆，-1小顶堆。
    private T[] data;
    private int size;

    @SafeVarargs
    public static <S extends Comparable<S>> Heap<S> newMaxHeap(int maxNodeCount, S... data)
    {
        return new Heap<S>(1, maxNodeCount, data);
    }

    @SafeVarargs
    public static <S extends Comparable<S>> Heap<S> newMinHeap(int maxNodeCount, S... data)
    {
        return new Heap<S>(-1, maxNodeCount, data);
    }

    @SafeVarargs
    private Heap(int type, int maxNodeCount, T... data)
    {
        this.type = type;
        this.data = Arrays.copyOf(data, maxNodeCount);
        this.size = data.length;

        // 构建堆方法一(推荐)。
        for (int i = getParentIndex(size - 1); i >= 0; i--)
        {
            shiftDown(this.data, i, size);
        }

        // // 构建堆方法二。
        // for (int i = 1; i <= size; i++)
        // {
        // shiftUp(data, 0, i);
        // }
    }

    /**
     * 向堆中添加元素。
     */
    public void push(T t)
    {
        if (size >= data.length)
        {
            throw new RuntimeException("Head is full.");
        }

        data[size++] = t;

        shiftUp(data, 0, size);
    }

    /**
     * 获取并移除堆顶元素。
     */
    public T pop()
    {
        if (size <= 0)
        {
            throw new RuntimeException("Head is empty.");
        }

        T top = data[0];
        data[0] = data[--size];
        data[size] = null;

        shiftDown(data, 0, size);

        return top;
    }

    /**
     * 获取堆顶元素。
     */
    public T top()
    {
        if (size <= 0)
        {
            throw new RuntimeException("Head is empty.");
        }

        return data[0];
    }

    /**
     * 获取并替换堆顶元素。
     */
    public T top(T t)
    {
        if (size <= 0)
        {
            throw new RuntimeException("Head is empty.");
        }

        T top = data[0];
        data[0] = t;

        shiftDown(data, 0, size);

        return top;
    }

    /**
     * 获取堆内部数组的副本。
     */
    public T[] toArray()
    {
        return Arrays.copyOf(data, size);
    }

    /**
     * 获取堆内部数组的副本，并排序此副本。
     */
    public T[] toSortedArray()
    {
        T[] clonedArray = toArray();

        for (int i = clonedArray.length - 1; i >= 0; i--)
        {
            swap(clonedArray, 0, i);
            shiftDown(clonedArray, 0, i);
        }

        return clonedArray;
    }

    /**
     * 已知data[start,end)中的记录除data[end-1]之外均满足堆的定义。本函数调整data[end-1]，使data[start,end)成为一个堆。
     * 
     * @param data
     *            数组
     * @param start
     *            起始元素位置(包含此元素)
     * @param end
     *            终止元素位置(不包含此元素)
     */
    private void shiftUp(T[] data, int start, int end)
    {
        // 记录堆中最后一个元素的值。
        T last = data[end - 1];

        int child = end - 1;
        int parent = getParentIndex(child);
        while (child > 0)
        {
            if (compare(data[parent], last) >= 0)
            {
                break;
            }

            data[child] = data[parent];

            child = parent;
            parent = getParentIndex(child);
        }

        data[child] = last;
    }

    /**
     * 已知data[start,end)中的记录除data[start]之外均满足堆的定义。本函数调整data[start]，使data[start,end)成为一个堆。
     * 
     * @param data
     *            数组
     * @param start
     *            起始元素位置(包含此元素)
     * @param end
     *            终止元素位置(不包含此元素)
     */
    private void shiftDown(T[] data, int start, int end)
    {
        // 记录堆顶元素的值。
        T top = data[start];

        // 在以0为起始的数组体系中，如果某个元素的坐标为i，那么它的左孩子的坐标为2*i+1，它的右孩子的坐标为2*i+2。
        int parent = start;
        int leftChild = getLeftChildIndex(parent);
        int rightChild = getRightChildIndex(parent);
        while (leftChild < end)
        {
            // 记录左右孩子中的较大者。
            int maxChild = leftChild;
            if (rightChild < end && compare(data[leftChild], data[rightChild]) < 0)
            {
                maxChild = rightChild;
            }

            // 如果堆顶元素大于等于左右孩子中的较大者，则停止循环。
            if (compare(top, data[maxChild]) >= 0)
            {
                break;
            }

            // 左右孩子中的较大者上移。
            data[parent] = data[maxChild];

            parent = maxChild;
            leftChild = getLeftChildIndex(parent);
            rightChild = getRightChildIndex(parent);
        }

        // 堆顶元素下移到应在的位置。
        data[parent] = top;
    }

    /**
     * 获取child的父节点的索引。
     */
    private int getParentIndex(int childIndex)
    {
        return (childIndex - 1) >> 1;
    }

    /**
     * 获取parent的左孩子索引。
     * 
     * @param parentIndex
     * @return
     */
    private int getLeftChildIndex(int parentIndex)
    {
        return (parentIndex << 1) + 1;
    }

    /**
     * 获取parent的右孩子索引。
     */
    private int getRightChildIndex(int parentIndex)
    {
        return (parentIndex << 1) + 2;
    }

    /**
     * 比较堆中元素的大小。
     */
    private int compare(T t1, T t2)
    {
        return t1.compareTo(t2) * type;
    }

    /**
     * 交换堆中元素。
     */
    private void swap(T[] array, int i, int j)
    {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args)
    {
        System.out.println("测试最大堆：");
        Heap<Integer> maxHeap = Heap.newMaxHeap(1000, 1, 2, 3, 4, 5);
        maxHeap.push(6);
        maxHeap.push(7);

        System.out.println(Arrays.toString(maxHeap.toArray()));
        System.out.println(Arrays.toString(maxHeap.toSortedArray()));
        System.out.println();

        System.out.println(maxHeap.top());
        System.out.println(maxHeap.pop());
        System.out.println();

        System.out.println(maxHeap.top());
        System.out.println(maxHeap.pop());
        System.out.println();

        System.out.println("测试最小堆：");
        Heap<Integer> minHeap = Heap.newMinHeap(1000, 7, 6, 5, 4, 3);
        minHeap.push(2);
        minHeap.push(1);

        System.out.println(Arrays.toString(minHeap.toArray()));
        System.out.println(Arrays.toString(minHeap.toSortedArray()));
        System.out.println();

        System.out.println(minHeap.top());
        System.out.println(minHeap.pop());
        System.out.println();

        System.out.println(minHeap.top());
        System.out.println(minHeap.pop());
        System.out.println();
    }
}
