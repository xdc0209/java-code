package com.xdc.basic.api.apache.commons.codec.digest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.digest.DigestUtils;

import com.xdc.basic.commons.PathUtil;

public class DigestUtilsTest
{
    public static void main(String[] args)
    {
        String curPath = PathUtil.getRelativePath();

        File file = new File(curPath + "data.txt");

        InputStream inputMd5 = null;
        InputStream inputSha1 = null;
        InputStream inputSha256 = null;
        InputStream inputSha384 = null;
        InputStream inputSha512 = null;

        try
        {
            // 算法强度小于sha1
            inputMd5 = new FileInputStream(file);
            String md5Hex = DigestUtils.md5Hex(inputMd5);
            System.out.println("MD5值:" + md5Hex);

            // 算法强度小于sha256
            inputSha1 = new FileInputStream(file);
            String sha1Hex = DigestUtils.sha1Hex(inputSha1);
            System.out.println("SHA1值:" + sha1Hex);

            inputSha256 = new FileInputStream(file);
            String sha256Hex = DigestUtils.sha256Hex(inputSha256);
            System.out.println("SHA256值:" + sha256Hex);

            inputSha384 = new FileInputStream(file);
            String sha384Hex = DigestUtils.sha384Hex(inputSha384);
            System.out.println("SHA384值:" + sha384Hex);

            inputSha512 = new FileInputStream(file);
            String sha512Hex = DigestUtils.sha512Hex(inputSha512);
            System.out.println("SHA512值:" + sha512Hex);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
