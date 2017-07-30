package model;

/**
 * Created by vector01.yao on 2017/7/29.
 * 作者信息
 */
public class AuthorInfoDto {
    private String organization;
//    /** 一级组织 */
//    private String primaryOrg;
//    /** 二级组织 */
//    private String secondOrg;
//    /** 三级组织 */
//    private String thirdOrg;
    /** 城市 */
    private String city;
    /** 论文链接 */
    private String paperLink;

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPaperLink() {
        return paperLink;
    }

    public void setPaperLink(String paperLink) {
        this.paperLink = paperLink;
    }

    public static void main(String[] args) {
        String authorInfoStr="Department of Pharmacology, Harbin Medical University(Daqing), Daqing, China.";
        String organization=authorInfoStr.replaceAll("((.*)+),((.*)?),((.*)?)", "$1");
        System.out.println(organization);
    }
}
