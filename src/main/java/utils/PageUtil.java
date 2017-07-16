package utils;

public class PageUtil {

    public static int getPageStart(int currentPage,int pageSize){
        return (currentPage-1) <= 0 ? 0:(currentPage-1)*pageSize;
    }
    
    public static  int getPageEnd(int currentPage,int pageSize,int totalRecorls) {
        int pageStart = getPageStart(currentPage, pageSize);
		return pageStart+pageSize > totalRecorls ? totalRecorls : pageStart+pageSize;
	}

    public static int getPageCount(int pageSize,int totalRecords){
        return  totalRecords % pageSize == 0 ? (totalRecords / pageSize) : (totalRecords / pageSize + 1);
    }

    /**
     * 获取当前开始数
     * periodCount 21 currentPage 3 pageSize 12 ,则返回3
     * @param periodCount 分期的总条数
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @return
     */
    public static int getPageStart(int periodCount,int currentPage,int pageSize){
        int pageStart = 0;
        if(pageSize==0){
            return 0;
        }
        int periodPageSize = periodCount/pageSize;
        int pageSizeRemain = periodCount%pageSize;
        pageStart = (currentPage-periodPageSize-1)*pageSize-pageSizeRemain;
        if(pageStart<0){
            pageStart = 0;
        }
        return pageStart;

    }
}