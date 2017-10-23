package com.xdc.basic.algorithm.basic.search.动态查找表;

/**
 * Trie树， 又称字典树，单词查找树。它来源于retrieval(检索)中取中间四个字符构成(读音同try)。
 * 
 * 参考：http://blog.csdn.net/lisonglisonglisong/article/details/45584721
 */
public class TrieTree
{
    private TrieTreeNode root = new TrieTreeNode();

    /**
     * 插入单词。
     */
    public void insert(String word)
    {
        TrieTreeNode node = root;

        for (int i = 0; i < word.length(); i++)
        {
            int index = word.charAt(i) - 'a';
            if (node.getChildren()[index] == null)
            {
                node.getChildren()[index] = new TrieTreeNode();
            }
            node = node.getChildren()[index];
        }

        node.increase();
    }

    /**
     * 查询，不存在返回0，存在返回出现的次数。
     */
    public int search(String word)
    {
        TrieTreeNode node = root;

        for (int i = 0; i < word.length(); i++)
        {
            int index = word.charAt(i) - 'a';
            if (node.getChildren()[index] == null)
            {
                return 0;
            }
            node = node.getChildren()[index];
        }

        return node.getCount();
    }

    public static void main(String[] args)
    {
        TrieTree trieTree = new TrieTree();

        String[] words = { "the", "a", "there", "answer", "any", "by", "bye", "the", "their" };
        for (String word : words)
        {
            trieTree.insert(word);
        }

        System.out.println(trieTree.search("the"));
        System.out.println(trieTree.search("these"));
        System.out.println(trieTree.search("their"));
        System.out.println(trieTree.search("thaw"));
    }
}

class TrieTreeNode
{
    // 由于只是演示程序，这里为了简化，只考虑了26个小写字母。
    private static int     KEY_SIZE = 26;

    private int            count    = 0;

    private TrieTreeNode[] children = new TrieTreeNode[KEY_SIZE];

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public void increase()
    {
        this.count++;
    }

    public void decrease()
    {
        this.count--;
    }

    public TrieTreeNode[] getChildren()
    {
        return children;
    }

    public void setChildren(TrieTreeNode[] children)
    {
        this.children = children;
    }
}
