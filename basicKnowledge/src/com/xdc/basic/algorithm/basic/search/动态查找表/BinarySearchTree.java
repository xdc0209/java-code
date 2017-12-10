package com.xdc.basic.algorithm.basic.search.动态查找表;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉查找树，代码部基于递归实现(非递归算法复杂一些但效率高)。
 * 主要包含BST的如下操作：大小、查找、插入、最大键、最小键、向上取整、向下取整、排名k的键、获取键key的排名、删除最大键、删除最小键、删除操作、范围查找。
 * 
 * 参考：http://blog.csdn.net/u010853261/article/details/54174609
 */
public class BinarySearchTree<Key extends Comparable<Key>, Value>
{
    private class Node
    {
        private Key   key;         // 键。
        private Value value;       // 值。
        private Node  left, right; // 指向子树的链接：包括左子树和右子树。
        private int   size;        // 以当前节点为根的子树的结点总数。

        private Node(Key key, Value value, int size)
        {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    private Node root = null;

    /**
     * 大小：在整棵树中，获取二叉查找树的大小。
     */
    public int size()
    {
        return size(root);
    }

    /**
     * 大小：在以某个节点为根节点的子树中，获取二叉查找树的大小。
     */
    private int size(Node x)
    {
        if (x == null)
        {
            return 0;
        }
        else
        {
            return x.size;
        }
    }

    /**
     * 查找：在整棵树中，通过key获取value，如果找不到就返回null。
     */
    public Value get(Key key)
    {
        return get(root, key);
    }

    /**
     * 查找：在以某个节点为根节点的子树中，通过key获取value，如果找不到就返回null。
     */
    private Value get(Node x, Key key)
    {
        if (x == null)
        {
            return null;
        }

        int cmp = key.compareTo(x.key);
        if (cmp < 0)
        {
            return get(x.left, key);
        }
        else if (cmp > 0)
        {
            return get(x.right, key);
        }
        else
        {
            return x.value;
        }
    }

    /**
     * 插入：在整棵树中，如果找到key，则更新它的值；否则将key与value键值对创建并插入一个新的结点。
     */
    public void put(Key key, Value value)
    {
        root = put(root, key, value);
    }

    /**
     * 插入：在以某个节点为根节点的子树中，如果找到key，则更新它的值；否则将key与value键值对创建并插入一个新的结点。
     */
    private Node put(Node x, Key key, Value value)
    {
        if (x == null)
        {
            // 创建新节点
            x = new Node(key, value, 1);
            return x;
        }

        int cmp = key.compareTo(x.key);
        if (cmp < 0)
        {
            x.left = put(x.left, key, value);
        }
        else if (cmp > 0)
        {
            x.right = put(x.right, key, value);
        }
        else
        {
            // 更新value的值
            x.value = value;
        }

        // 设置根节点的N属性。
        x.size = size(x.left) + size(x.right) + 1;

        return x;
    }

    /**
     * 最小键：在整棵树中，查找最小的key。根据BST的特性，其实原理很简单：最小值就是最左下边的一个节点。
     */
    public Key min()
    {
        Node x = min(root);
        if (x == null)
        {
            return null;
        }
        return x.key;
    }

    /**
     * 最小键：在以某个节点为根节点的子树中，查找最小的key。根据BST的特性，其实原理很简单：最小值就是最左下边的一个节点。
     */
    private Node min(Node x)
    {
        if (x == null || x.left == null)
        {
            return x;
        }
        else
        {
            return min(x.left);
        }
    }

    /**
     * 最大键：在整棵树中，查找最大的key。根据BST的特性，其实原理很简单：最大键值就是最右下边的结点。
     */
    public Key max()
    {
        Node x = max(root);
        if (x == null)
        {
            return null;
        }
        return x.key;
    }

    /**
     * 最大键：在以某个节点为根节点的子树中，查找最大的key。根据BST的特性，其实原理很简单：最大键值就是最右下边的结点。
     */
    private Node max(Node x)
    {
        if (x == null || x.right == null)
        {
            return x;
        }
        else
        {
            return max(x.right);
        }
    }

    /**
     * key向下取整：在整棵树中，key向下取整，即查找所有小于等于key中的最大者。
     */
    public Key floor(Key key)
    {
        Node x = floor(root, key);
        if (x == null)
        {
            return null;
        }
        return x.key;
    }

    /**
     * key向下取整：在以某个节点为根节点的子树中，key向下取整，即查找所有小于等于key中的最大者。
     */
    private Node floor(Node x, Key key)
    {
        if (x == null)
        {
            return null;
        }

        int cmp = key.compareTo(x.key);

        // 如果key等于x结点的key，则直接返回。
        if (cmp == 0)
        {
            return x;
        }

        // 如果key小于x结点的key，则向下取整的结果必在左子树。
        if (cmp < 0)
        {
            return floor(x.left, key);
        }

        // 根据前面的判断，此时key大于x结点的key，即x已满足小于等于key的条件，又即x节点为候选节点之一。
        // 之后再继续在右子树中查找，如果存在另一候选节点t(因为在右子树查找，所以结果肯定比x节点大)，则t节点为最终结果，否则x节点为最终结果。
        Node t = floor(x.right, key);
        if (t != null)
        {
            return t;
        }
        else
        {
            return x;
        }
    }

    /**
     * key向上取整：在整棵树中，key向上取整，即查找所有大于等于key中的最小者。
     */
    public Key ceil(Key key)
    {
        Node x = ceil(root, key);
        if (x == null)
        {
            return null;
        }
        return x.key;
    }

    /**
     * key向上取整：在以某个节点为根节点的子树中，key向上取整，即查找所有大于等于key中的最小者。
     */
    private Node ceil(Node x, Key key)
    {
        if (x == null)
        {
            return null;
        }

        int cmp = key.compareTo(x.key);

        // 如果key等于x结点的key，则直接返回。
        if (cmp == 0)
        {
            return x;
        }

        // 如果key大于x结点的key，则向上取整的结果必在右子树。
        if (cmp > 0)
        {
            return ceil(x.right, key);
        }

        // 根据前面的判断，此时key小于x结点的key，即x已满足小大于等于key的条件，又即x节点为候选节点之一。
        // 之后再继续在左子树中查找，如果存在另一候选节点t(因为在左子树查找，所以结果肯定比x节点小)，则t节点为最终结果，否则x节点为最终结果。
        Node t = ceil(x.left, key);
        if (t != null)
        {
            return t;
        }
        else
        {
            return x;
        }
    }

    /**
     * 排名k的键：在整棵树中，查找排名k的键。注：排名从1开始。
     */
    public Key select(int k)
    {
        Node x = select(root, k);
        if (x == null)
        {
            return null;
        }
        return x.key;
    }

    /**
     * 排名k的键：在以某个节点为根节点的子树中，查找排名k的键。注：排名从1开始。
     */
    private Node select(Node x, int k)
    {
        if (x == null)
        {
            return null;
        }

        // 获取根节点的排名。
        int t = size(x.left) + 1;
        if (k == t)
        {
            // 排名k等于根节点的排名。
            return x;
        }
        else if (k < t)
        {
            // 排名k小于根节点的排名。
            return select(x.left, k);
        }
        else
        {
            // 排名k大于根节点的排名。
            return select(x.right, k - t);
        }
    }

    /**
     * 键key的排名：在整棵树中，计算键key的排名。排名从1开始，0代表无此元素。
     */
    public int rank(Key key)
    {
        return rank(root, key);
    }

    /**
     * 键key的排名：在以某个节点为根节点的子树中，计算键key的排名。排名从1开始，0代表无此元素。
     */
    private int rank(Node x, Key key)
    {
        if (x == null)
        {
            // 排名从1开始，0代表无此元素。
            return 0;
        }

        int cmp = key.compareTo(x.key);
        if (cmp == 0)
        {
            // key等于根节点的key，所以key的排名为左子树大小+1。
            return size(x.left) + 1;
        }
        else if (cmp < 0)
        {
            // key小于根节点的key，所以key在左子树中。
            return rank(x.left, key);
        }
        else
        {
            // key大于根节点的key，所以key在右子树中。
            int rankRight = rank(x.right, key);
            return rankRight == 0 ? 0 : size(x.left) + 1 + rankRight;
        }
    }

    /**
     * 删除键值最小结点：在整棵树中，删除键值最小结点，其实也就是最左边的结点。
     */
    public void deleteMin()
    {
        root = deleteMin(root);
    }

    /**
     * 删除键值最小结点：在以某个节点为根节点的子树中，删除键值最小结点。
     */
    private Node deleteMin(Node x)
    {
        if (x == null)
        {
            return null;
        }

        if (x.left == null)
        {
            // 删除根节点，这时返回的是新的二叉查找树的根节点。
            return x.right;
        }

        x.left = deleteMin(x.left);

        // 设置根节点的N属性。
        x.size = size(x.left) + size(x.right) + 1;

        return x;
    }

    /**
     * 删除键值最大结点：在整棵树中，删除键值最大结点，其实也就是最右边的结点。
     */
    public void deleteMax()
    {
        root = deleteMax(root);
    }

    /**
     * 删除键值最大结点：在以某个节点为根节点的子树中，删除键值最大结点，其实也就是最右边的结点。
     */
    private Node deleteMax(Node x)
    {
        if (x == null)
        {
            return null;
        }

        if (x.right == null)
        {
            // 删除根节点，这时返回的是新的二叉查找树的根节点。
            return x.left;
        }

        x.right = deleteMax(x.right);

        // 设置根节点的N属性。
        x.size = size(x.left) + size(x.right) + 1;

        return x;
    }

    /**
     * 删除键key结点：在整棵树中，删除键key结点。
     */
    public void delete(Key key)
    {
        root = delete(root, key);
    }

    /**
     * 删除键key结点：在以某个节点为根节点的子树中，删除键key结点。
     */
    private Node delete(Node x, Key key)
    {
        if (x == null)
        {
            return null;
        }

        int cmp = key.compareTo(x.key);
        if (cmp < 0)
        {
            x.left = delete(x.left, key);
        }
        else if (cmp > 0)
        {
            x.right = delete(x.right, key);
        }
        else
        {
            if (x.left == null)
            {
                // 左子树为空，则只需重接它的右子树。
                return x.right;
            }
            else if (x.right == null)
            {
                // 右子树为空，则只需重接它的左子树。
                return x.left;
            }
            else
            {
                // 根据前面的判断，此时左右子树均不为空。
                Node t = x;

                // 1. 先求出x的右子树中最小键值的结点(即x的直接后继，同理找到x的直接前驱也是可以的，不过后面代码要适配修改)并让x指向它。
                x = min(t.right);

                // 2. 将t的右子树删除最小的结点之后的根节点返回。
                x.right = deleteMin(t.right);

                // 3. 将t的左子树给x的左子树。
                x.left = t.left;
            }
        }

        // 设置根节点的N属性。
        x.size = size(x.left) + size(x.right) + 1;

        return x;
    }

    /**
     * 范围查找：在整棵树中，返回所有的键值。
     */
    public List<Key> keys()
    {
        return keys(min(), max());
    }

    /**
     * 范围查找：在整棵树中，输入最小键值和最大键值，返回符合条件的键值。
     */
    public List<Key> keys(Key low, Key high)
    {
        List<Key> keys = new ArrayList<>();
        keys(root, keys, low, high);
        return keys;
    }

    /**
     * 范围查找：在以某个节点为根节点的子树中，输入最小键值和最大键值，返回符合条件的键值。查找原理就是中序遍历二叉搜索树，得到升序的键值序列。
     */
    private void keys(Node x, List<Key> keys, Key low, Key high)
    {
        if (x == null)
        {
            return;
        }

        int cmpLow = low.compareTo(x.key);
        int cmpHigh = high.compareTo(x.key);

        if (cmpLow < 0)
        {
            keys(x.left, keys, low, high);
        }

        if (cmpLow <= 0 && cmpHigh >= 0)
        {
            keys.add(x.key);
        }

        if (cmpHigh > 0)
        {
            keys(x.right, keys, low, high);
        }
    }

    public static void main(String[] args)
    {
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<Integer, String>();
        bst.put(5, "5");
        bst.put(1, "1");
        bst.put(4, "4");
        bst.put(7, "7");
        bst.put(3, "3");
        bst.put(9, "9");

        System.out.println("树结构：");
        bst.treeGraph(bst.root);
        System.out.println();

        System.out.println("大小：");
        System.out.println(bst.size());
        System.out.println();

        System.out.println("最小键和最大键：");
        System.out.println(bst.min());
        System.out.println(bst.max());
        System.out.println();

        System.out.println("向下取整和向上取整：");
        System.out.println("2：" + bst.floor(2));
        System.out.println("8：" + bst.ceil(8));
        System.out.println();

        System.out.println("排名k的键：");
        System.out.println("-1：" + bst.select(-1));
        System.out.println("2：" + bst.select(2));
        System.out.println("8：" + bst.select(8));
        System.out.println();

        System.out.println("获取键key的排名：");
        System.out.println("2：" + bst.rank(2));
        System.out.println("3：" + bst.rank(3));
        System.out.println("10：" + bst.rank(10));
        System.out.println();

        System.out.println("所有键：");
        System.out.println(bst.keys());
        System.out.println();

        System.out.println("范围搜索：3~8");
        System.out.println(bst.keys(3, 8));
        System.out.println();

        System.out.println("树结构：");
        bst.treeGraph(bst.root);
        System.out.println();

        System.out.println("查找和插入：");
        System.out.println("5：" + bst.get(5));
        System.out.println("4：" + bst.get(4));
        System.out.println("5：" + bst.get(5));
        System.out.println("9：" + bst.get(9));
        System.out.println("6：" + bst.get(6));
        bst.put(6, "6");
        System.out.println("6：" + bst.get(6));
        System.out.println();

        System.out.println("树结构：");
        bst.treeGraph(bst.root);
        System.out.println();

        System.out.println("删除最小键：" + bst.min());
        bst.deleteMin();
        System.out.println();

        System.out.println("树结构：");
        bst.treeGraph(bst.root);
        System.out.println();

        System.out.println("删除最大键：" + bst.max());
        bst.deleteMax();
        System.out.println();

        System.out.println("树结构：");
        bst.treeGraph(bst.root);
        System.out.println();

        System.out.println("删除键：5");
        bst.delete(5);
        System.out.println();

        System.out.println("树结构：");
        bst.treeGraph(bst.root);
        System.out.println();
    }

    /**
     * 输出树的图形
     */
    private void treeGraph(Node root)
    {
        treeGraph(root, 1);
    }

    /**
     * 输出树的图形
     */
    private void treeGraph(Node root, int deep)
    {
        if (root == null)
        {
            for (int i = 0; i < deep; i++)
            {
                System.out.print("....");
            }
            System.out.println("$");

            return;
        }

        // 输出右子树。
        treeGraph(root.right, deep + 1);

        // 输出根节点。
        for (int i = 0; i < deep; i++)
        {
            System.out.print("....");
        }
        System.out.println(root.key);

        // 输出左子树。
        treeGraph(root.left, deep + 1);
    }
}
