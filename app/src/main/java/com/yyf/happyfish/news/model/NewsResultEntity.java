package com.yyf.happyfish.news.model;

import java.util.List;

/**
 * Created by yyf on 2016/7/6.
 */
public class NewsResultEntity {
    private String stat;
    /**
     * title : 超大尺度女主播：脱衣揉胸呻吟如色情片
     * date : 2016-07-06 06:58
     * author_name : 凤凰网
     * thumbnail_pic_s : http://05.imgmini.eastday.com/mobile/20160706/20160706065849_2a19904a360eeed37d80aa8e78cde423_1_mwpm_03200403.jpeg
     * thumbnail_pic_s02 : http://05.imgmini.eastday.com/mobile/20160706/20160706065849_2a19904a360eeed37d80aa8e78cde423_1_mwpl_05500201.jpeg
     * thumbnail_pic_s03 : http://05.imgmini.eastday.com/mobile/20160706/20160706065849_2a19904a360eeed37d80aa8e78cde423_1_mwpl_05500201.jpeg
     * url : http://mini.eastday.com/mobile/160706065849509.html?qid=juheshuju
     * uniquekey : 160706065849509
     * type : 头条
     * realtype : 科技
     */

    private List<NewsListEntity> data;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public List<NewsListEntity> getData() {
        return data;
    }

    public void setData(List<NewsListEntity> data) {
        this.data = data;
    }
}
