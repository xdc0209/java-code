package com.xdc.basic.algorithm.basic.tree;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 二叉树的遍历
 */
public class BinaryTreeTraverse
{
    /**
     * 二叉树的先序(根)遍历，递归实现
     */
    public static void preOrderTraverse(BinaryTreeNode root)
    {
        if (root == null)
        {
            return;
        }

        System.out.println(root.getData()); // 先访问根结点
        preOrderTraverse(root.getLchild()); // 再先序遍历左子树
        preOrderTraverse(root.getRchild()); // 最后先序遍历右子树
    }

    /**
     * 二叉树的先序(根)遍历，第一种栈实现(推荐)
     */
    public static void preOrderTraverse1(BinaryTreeNode root)
    {
        if (root == null)
        {
            return;
        }

        Stack<BinaryTreeNode> stack = new Stack<BinaryTreeNode>();

        BinaryTreeNode node = root;
        while (node != null || !stack.empty())
        {
            while (node != null)
            {
                stack.push(node);
                System.out.println(node.getData());
                node = node.getLchild();
            }

            node = stack.pop();
            node = node.getRchild();
        }
    }

    /**
     * 二叉树的先序(根)遍历，第二种栈实现
     */
    public static void preOrderTraverse2(BinaryTreeNode root)
    {
        if (root == null)
        {
            return;
        }

        Stack<BinaryTreeNode> stack = new Stack<BinaryTreeNode>();

        stack.push(root);
        while (!stack.isEmpty())
        {
            BinaryTreeNode node = stack.pop();

            System.out.println(node.getData());

            // 注意此处右孩子先于左孩子入栈
            if (node.getRchild() != null)
            {
                stack.push(node.getRchild());
            }

            if (node.getLchild() != null)
            {
                stack.push(node.getLchild());
            }
        }
    }

    /**
     * 二叉树的中序(根)遍历，递归实现
     */
    public static void inOrderTraverse(BinaryTreeNode root)
    {
        if (root == null)
        {
            return;
        }

        inOrderTraverse(root.getLchild()); // 先中序遍历左子树
        System.out.println(root.getData()); // 再访问根结点
        inOrderTraverse(root.getRchild()); // 最后中序遍历右子树
    }

    /**
     * 二叉树的中序(根)遍历，栈实现(推荐)
     */
    public static void inOrderTraverse1(BinaryTreeNode root)
    {
        if (root == null)
        {
            return;
        }

        Stack<BinaryTreeNode> stack = new Stack<BinaryTreeNode>();

        BinaryTreeNode node = root;
        while (node != null || !stack.empty())
        {
            while (node != null)
            {
                stack.push(node);
                node = node.getLchild();
            }

            node = stack.pop();
            System.out.println(node.getData());
            node = node.getRchild();
        }
    }

    /**
     * 二叉树的后序(根)遍历，递归实现
     */
    public static void postOrderTraverse(BinaryTreeNode root)
    {
        if (root == null)
        {
            return;
        }

        postOrderTraverse(root.getLchild()); // 先后序遍历左子树
        postOrderTraverse(root.getRchild()); // 再后序遍历右子树
        System.out.println(root.getData()); // 最后访问根结点
    }

    /**
     * 二叉树的后序(根)遍历，第一种栈实现(推荐)
     */
    public static void postOrderTraverse1(BinaryTreeNode root)
    {
        if (root == null)
        {
            return;
        }

        Stack<BinaryTreeNode> stack = new Stack<BinaryTreeNode>();

        BinaryTreeNode curNode = root;
        BinaryTreeNode preNode = null;
        while (curNode != null || (!stack.isEmpty()))
        {
            while (curNode != null)
            {
                stack.push(curNode);
                curNode = curNode.getLchild();
            }

            curNode = stack.peek();
            if (curNode.getRchild() != null && curNode.getRchild() != preNode)
            {
                curNode = curNode.getRchild();
            }
            else
            {
                System.out.println(curNode.getData());
                preNode = curNode;
                curNode = null;
                stack.pop();
            }
        }
    }

    /**
     * 二叉树的后序(根)遍历，第二种栈实现。实现思想：要保证根结点在左孩子和右孩子访问之后才能访问，因此对于任一结点P，先将其入栈。如果P不存在左孩子和右孩子，则可以直接访问它；或者P存在左孩子或者右孩子，但是其左孩子和右孩子都已被访问过了，则同样可以直接访问该结点。若非上述两种情况，则将P的右孩子和左孩子依次入栈，这样就保证了每次取栈顶元素的时候，左孩子在右孩子前面被访问，左孩子和右孩子都在根结点前面被访问。
     */
    public static void postOrderTraverse2(BinaryTreeNode root)
    {
        if (root == null)
        {
            return;
        }

        Stack<BinaryTreeNode> stack = new Stack<BinaryTreeNode>();

        BinaryTreeNode curNode = null;
        BinaryTreeNode preNode = null;

        stack.push(root);
        while (!stack.isEmpty())
        {
            curNode = stack.peek();
            if ((curNode.getLchild() == null && curNode.getRchild() == null)
                    || (preNode != null && (preNode == curNode.getLchild() || preNode == curNode.getRchild())))
            {
                System.out.println(curNode.getData());
                preNode = curNode;
                stack.pop();
            }
            else
            {
                // 注意此处右孩子先于左孩子入栈
                if (curNode.getRchild() != null)
                {
                    stack.push(curNode.getRchild());
                }

                if (curNode.getLchild() != null)
                {
                    stack.push(curNode.getLchild());
                }
            }
        }
    }

    /**
     * 二叉树的后序(根)遍历，第三种栈实现。实现原理：把先序遍历的代码中的left全部换为right，right全换为left，最后就得到了后序遍历的逆序，反向输出就是后序遍历的顺序了。
     */
    public static void postOrderTraverse3(BinaryTreeNode root)
    {
        if (root == null)
        {
            return;
        }

        Stack<BinaryTreeNode> stack1 = new Stack<BinaryTreeNode>();
        Stack<BinaryTreeNode> stack2 = new Stack<BinaryTreeNode>();

        BinaryTreeNode node1 = root;
        while (node1 != null || !stack1.empty())
        {
            while (node1 != null)
            {
                stack1.push(node1);
                stack2.push(node1);
                node1 = node1.getRchild();
            }

            node1 = stack1.pop();
            node1 = node1.getLchild();
        }

        while (!stack2.isEmpty())
        {
            BinaryTreeNode node2 = stack2.pop();
            System.out.println(node2.getData());
        }
    }

    public enum State
    {
        // 尚未处理任何一个节点(前序遍历)
        STATE_NONE,

        // 处理完左节点(中序遍历)
        STATE_LEFT_DONE,

        // 左右节点都已经处理完(后序遍历)
        STATE_LEFT_RIGHT_DONE
    }

    /**
     * 先序遍历、中序遍历、后序遍历通用的栈实现
     */
    public static void traverse(BinaryTreeNode root, State when)
    {
        if (root == null)
        {
            return;
        }

        Stack<BinaryTreeNode> stackNode = new Stack<BinaryTreeNode>(); // 保存节点的栈
        Stack<State> stackState = new Stack<State>(); // 保存节点状态的栈

        stackNode.push(root); // 初始时加入根节点
        stackState.push(State.STATE_NONE); // 标记根节点为尚未处理任何子节点的状态

        // 算法说明：
        // 初始时放入根节点，将其标记为左右节点尚未处理的状态
        // 每个循环，从栈中取出一个节点和其状态，根据其当前状态转移到下一个状态(很显然，你可以从状态转换机的角度解读这个算法)
        // 状态转换规则： STATE_NONE-->STATE_LEFT_DONE-->STATE_LEFT_RIGTH_DONE-->弹出栈
        // 伴随状态的变化，还需要相应的操作，如将左右子节点放入栈中，或者将当前节点弹出栈
        // 最重要的一点是，当当前节点的状态符合处理状态的要求时，就会将节点打印出来(即访问该节点)
        while (!stackNode.isEmpty())
        {
            BinaryTreeNode n = stackNode.peek();
            State state = stackState.peek();

            // 当前状态可处理节点
            if (state == when)
            {
                System.out.println(n.getData());
            }

            // 3种状态之间的转换
            if (state == State.STATE_NONE)
            {
                stackState.set(stackState.size() - 1, State.STATE_LEFT_DONE);
                if (n.getLchild() != null)
                {
                    stackNode.push(n.getLchild());
                    stackState.push(State.STATE_NONE);
                }
            }
            else if (state == State.STATE_LEFT_DONE)
            {
                stackState.set(stackState.size() - 1, State.STATE_LEFT_RIGHT_DONE);
                if (n.getRchild() != null)
                {
                    stackNode.push(n.getRchild());
                    stackState.push(State.STATE_NONE);
                }
            }
            else if (state == State.STATE_LEFT_RIGHT_DONE)
            {
                stackNode.pop();
                stackState.pop();
            }
        }
    }

    /**
     * 二叉树的层次遍历
     */
    public static void levelOrderTraverse(BinaryTreeNode root)
    {
        if (root == null)
        {
            return;
        }

        Queue<BinaryTreeNode> queue = new LinkedBlockingQueue<BinaryTreeNode>();

        queue.add(root);
        while (!queue.isEmpty())
        {
            BinaryTreeNode node = queue.remove();

            System.out.println(node.getData());

            if (node.getLchild() != null)
            {
                queue.add(node.getLchild());
            }

            if (node.getRchild() != null)
            {
                queue.add(node.getRchild());
            }
        }
    }

    public static void main(String[] args)
    {
        BinaryTreeNode binaryTreeNode1 = new BinaryTreeNode(10);

        BinaryTreeNode binaryTreeNode2 = new BinaryTreeNode(6);
        BinaryTreeNode binaryTreeNode3 = new BinaryTreeNode(14);

        BinaryTreeNode binaryTreeNode4 = new BinaryTreeNode(4);
        BinaryTreeNode binaryTreeNode5 = new BinaryTreeNode(8);
        BinaryTreeNode binaryTreeNode6 = new BinaryTreeNode(12);
        BinaryTreeNode binaryTreeNode7 = new BinaryTreeNode(16);

        binaryTreeNode1.setLchild(binaryTreeNode2);
        binaryTreeNode1.setRchild(binaryTreeNode3);

        binaryTreeNode2.setLchild(binaryTreeNode4);
        binaryTreeNode2.setRchild(binaryTreeNode5);

        binaryTreeNode3.setLchild(binaryTreeNode6);
        binaryTreeNode3.setRchild(binaryTreeNode7);

        System.out.println("先序：10 6 4 8 14 12 16");
        System.out.println("中序：4 6 8 10 12 14 16");
        System.out.println("后序：4 8 6 12 16 14 10");

        postOrderTraverse3(binaryTreeNode1);
    }
}
