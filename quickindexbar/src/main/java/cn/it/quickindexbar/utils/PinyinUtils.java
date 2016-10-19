package cn.it.quickindexbar.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉字装换成汉语拼音的工具类
 */
public class PinyinUtils {

    private static HanyuPinyinOutputFormat format;

    //汉字转拼音
    public static String hanZiToPinyin(String hanZi){
        if (format==null){
            //设置输出配置
            format = new HanyuPinyinOutputFormat();
        }
        //设置大写
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        //去掉声调
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        StringBuilder sb=new StringBuilder();
        char[] chars = hanZi.toCharArray();
        for (char aChar : chars) {
            if (Character.isWhitespace(aChar)){
                continue;
            }
            if (Character.toString(aChar).matches("[\u4E00-\u9FA5]")){
                //是汉字
                try {
                    String pinyin = PinyinHelper.toHanyuPinyinStringArray(aChar, format)[0];
                    sb.append(pinyin);

                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            }else {
                if (Character.isLetter(aChar)){
                    //是字母
                    sb.append(Character.toUpperCase(aChar));
                }else{
                    //一些乱七八糟的字符，#@#@%￥￥%……%&
                    sb.append("#");
                }
            }

        }
        return sb.toString();
    }

}
