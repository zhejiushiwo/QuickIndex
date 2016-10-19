package cn.it.quickindexbar.bean;

import cn.it.quickindexbar.utils.PinyinUtils;

/**
 * bean对象
 */
public class GoodMan implements Comparable<GoodMan> {

    private String name;
    private String pinyin;

    public GoodMan(String name) {
        this.name = name;
        this.pinyin = PinyinUtils.hanZiToPinyin(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public int compareTo(GoodMan goodMan) {
        return this.pinyin.compareTo(goodMan.pinyin);
    }
}
