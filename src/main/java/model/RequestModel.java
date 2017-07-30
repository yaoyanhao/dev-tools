package model;

/**
 * Created by vector01.yao on 2017/7/28.
 *
 */
public class RequestModel {
    /** 查询关键词  */
    private String keyWord;
    /** 论文年份  */
    private String paperYear;
    /** 作者所属城市 */
    private String city;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getPaperYear() {
        return paperYear;
    }

    public void setPaperYear(String paperYear) {
        this.paperYear = paperYear;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
