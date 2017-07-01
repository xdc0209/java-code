package com.xdc.basic.api.pinyin;

import java.io.FileNotFoundException;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

public class JPinyinTest
{
    public static void main(String[] args) throws PinyinException, FileNotFoundException
    {
        // PinyinHelper.addPinyinDict("user_pinyin.dict"); // 添加用户自定义的字音字典，参考：jpinyin-1.1.8.jar/data/pinyin.dict
        // PinyinHelper.addMutilPinyinDict("user_mutil_pinyin.dict"); // 添加用户自定义的多音字字典，参考：jpinyin-1.1.8.jar/data/mutil_pinyin.dict

        String str = "你好世界pinyin";
        String pinYinWithToneMark = PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_MARK); // nǐ,hǎo,shì,jiè
        String pinYinWithToneNumber = PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_NUMBER); // ni3,hao3,shi4,jie4
        String pinYinWithoutTone = PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITHOUT_TONE); // ni,hao,shi,jie
        String pinYinShort = PinyinHelper.getShortPinyin(str); // nhsj

        System.out.println(str);
        System.out.println(pinYinWithToneMark);
        System.out.println(pinYinWithToneNumber);
        System.out.println(pinYinWithoutTone);
        System.out.println(pinYinShort);
    }
}
