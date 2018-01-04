package com.xdc.basic.algorithm.basic.sort.external.sort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.algorithm.basic.tree.losertree.LoserTree;
import com.xdc.basic.commons.collection.ListUtil;
import com.xdc.basic.skills.GetPath;

/**
 * k路归并排序。
 */
public class ExternalSort
{
    /**
     * 配置K路排序的路数。
     */
    private static final int    K                   = 10;

    /**
     * Unix换行符。如果文件行数很多，使用Unix换行符比Windows换行符省下不少空间，与此同时程序读取的就更快了。
     * 比如有六千七百万行的文件，每行一个整数，windows格式的文件大小是766M，而Unix格式的文件大小是702M。
     */
    private static final String UNIX_LINE_SEPARATOR = "\n";

    /**
     * 外部排序。
     * 
     * @param filePath
     *            待排序的文件路径
     * @return
     *         返回排序好的文件路径
     * @throws IOException
     *             文件不存在，或磁盘错误
     */
    public static String sort(String filePath) throws IOException
    {
        // 记录归并次数，用于生成临时文件名。
        int mergeIndex = 0;

        // 切分、并局部排序。
        List<String> filePaths = splitAndSort(filePath, mergeIndex++);

        // K路归并。
        while (filePaths.size() > 1)
        {
            filePaths = kMerge(filePaths, K, filePath, mergeIndex++);
        }

        // 返回排序好的文件路径。
        return ListUtil.getFirstElement(filePaths);
    }

    /**
     * 切分、并局部排序。
     * 
     * @param filePath
     *            待排序的文件路径
     * @param mergeIndex
     *            归并次数，用于生成临时文件名
     * @return
     *         排序好的文件路径
     * @throws IOException
     *             文件不存在，或磁盘错误
     */
    private static List<String> splitAndSort(String filePath, int mergeIndex) throws IOException
    {
        // 获取当前虚拟机剩余内存。
        long freeMemory = Runtime.getRuntime().freeMemory();

        // 剩余内存的20%用于其他计算，免得内存溢出。因为每个整型占4个字节，所以每次可排序的总数为：freeMemory * 4 / 5 / 4，化简为 freeMemory / 5。
        int elementCount = (int) Math.min(freeMemory / 5, Integer.MAX_VALUE);

        // 根据内存大小创建缓存。
        int[] intBuffer = new int[elementCount];
        int bufferSize = 0;

        // 排序完成了的文件路径。
        List<String> sortedFilePaths = new LinkedList<String>();

        // 记录生成文件次数，用于生成临时文件名。
        int fileIndex = 1;

        // 处理文件。
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);
        while (true)
        {
            // 读取文件，读取一定量的数字。
            String line = null;
            while (bufferSize < intBuffer.length && (line = br.readLine()) != null)
            {
                intBuffer[bufferSize++] = Integer.parseInt(line);
            }

            // 如果缓存中无数字，则表示切割文件完成。
            if (bufferSize == 0)
            {
                break;
            }

            // 排序。
            Arrays.sort(intBuffer, 0, bufferSize);

            // 生成文件名。
            String sortedFilePath = filePath + "-" + mergeIndex + "#" + fileIndex++;
            sortedFilePaths.add(sortedFilePath);

            // 写入文件。
            FileWriter fw = new FileWriter(sortedFilePath);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < bufferSize; i++)
            {
                bw.write(String.valueOf(intBuffer[i]) + UNIX_LINE_SEPARATOR);
            }
            bw.close();

            // 清空缓存。
            bufferSize = 0;
        }
        br.close();

        return sortedFilePaths;
    }

    /**
     * K路归并。
     * 
     * @param filePaths
     *            待归并的文件。
     * @param k
     *            归并路数
     * @param filePath
     *            待排序的文件路径
     * @param mergeIndex
     *            归并次数，用于生成临时文件名
     * @return
     *         返回排序好的文件路径
     * @throws IOException
     *             文件不存在，或磁盘错误
     */
    private static List<String> kMerge(List<String> filePaths, int k, String filePath, int mergeIndex)
            throws IOException
    {
        // 归并完成了的文件路径。
        List<String> mergedFilePaths = new LinkedList<String>();

        // 记录生成文件次数，用于生成临时文件名。
        int fileIndex = 1;

        for (int i = 0; i < filePaths.size(); i = i + k)
        {
            // 由于文件个数可能不是k的整数倍，需要求出每轮实际归并的路数。
            int l = Math.min(i + k, filePaths.size()) - i;

            // 构建败者树。
            Integer[] firstInts = new Integer[l];
            FileReader[] frs = new FileReader[l];
            BufferedReader[] brs = new BufferedReader[l];
            for (int j = 0; j < l; j++)
            {
                // 打开每个文件。
                frs[j] = new FileReader(filePaths.get(j + i));
                brs[j] = new BufferedReader(frs[j]);

                // 读取每个文件的第一个整数。
                firstInts[j] = Integer.parseInt(brs[j].readLine());
            }
            LoserTree<Integer> loserTree = LoserTree.newMinLoserTree(firstInts);

            // 生成文件名。
            String mergedFilePath = filePath + "-" + mergeIndex + "#" + fileIndex++;
            mergedFilePaths.add(mergedFilePath);

            // 处理文件。
            FileWriter fw = new FileWriter(mergedFilePath);
            BufferedWriter bw = new BufferedWriter(fw);
            while (true)
            {
                // 获取当前败者树的胜者。
                int winnerIndex = loserTree.winner();

                // 如果败者树返回-1，代表所有文件都处理完成。
                if (winnerIndex == -1)
                {
                    break;
                }

                // 读取文件。
                String line = brs[winnerIndex].readLine();
                if (StringUtils.isEmpty(line))
                {
                    // 使用空置换胜利者，并写入文件。
                    Integer winner = loserTree.adjust(null);
                    bw.write(String.valueOf(winner) + UNIX_LINE_SEPARATOR);

                    brs[winnerIndex].close();
                }
                else
                {
                    // 使用下个整数置换胜利者，并写入文件。
                    Integer winner = loserTree.adjust(Integer.parseInt(line));
                    bw.write(String.valueOf(winner) + UNIX_LINE_SEPARATOR);
                }
            }
            bw.close();
        }

        return mergedFilePaths;
    }

    /**
     * 生成大文件，测试方法。
     */
    private static void creatBigFile(String filePath) throws IOException
    {
        Random random = new Random();

        FileWriter fw = new FileWriter(filePath);
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i = 1; i <= (2 << 25); i++)
        {
            bw.write(String.valueOf(random.nextInt()) + UNIX_LINE_SEPARATOR);
        }
        bw.close();
    }

    public static void main(String[] args) throws IOException
    {
        String curPath = GetPath.getRelativePath();
        String filePath = GetPath.connect(curPath, "sort.txt");

        System.out.println(new Date() + " 生成原始文件开始：" + filePath);
        creatBigFile(filePath);
        System.out.println(new Date() + " 生成原始文件结束：" + filePath);

        System.out.println(new Date() + " 外部排序开始：" + filePath);
        String sortedFilePath = ExternalSort.sort(filePath);
        System.out.println(new Date() + " 外部排序开始：" + sortedFilePath);
    }
}
