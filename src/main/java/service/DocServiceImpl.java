package service;

import model.AuthorInfoDto;
import model.RequestModel;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import utils.HttpUtil;
import utils.PageUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by vector01.yao on 2017/7/15.
 */
@Service
public class DocServiceImpl implements DocService {
    private Logger logger= LoggerFactory.getLogger(DocServiceImpl.class);

    private static final String ROOT_HOST="https://www.ncbi.nlm.nih.gov";
    private static final String MAIN_HOST="https://www.ncbi.nlm.nih.gov/pubmed";
    private static final int DEFAULT_PAGESIZE=20;

    @Override
    public String findDoc(RequestModel requestModel) throws Exception {
        String keyWord=requestModel.getKeyWord();
        String city=requestModel.getCity();
        if (StringUtils.isBlank(keyWord)){
            logger.error("关键词不能为空！");
            throw new RuntimeException("关键词不能为空！");
        }

        // 获取总记录数，并计算总页数
        String mainURL=MAIN_HOST+"?term="+keyWord;
        Document parentDoc= Jsoup.parse(HttpUtil.getString(mainURL));
        Element totalCountElement=parentDoc.getElementById("resultcount");
        int resultCount=Integer.parseInt(totalCountElement.attr("value"));
        int totalPageCount= PageUtil.getPageCount(DEFAULT_PAGESIZE,resultCount);
        logger.error("关键词{}：共{}篇论文，每页{}条，共{}页",keyWord,resultCount,DEFAULT_PAGESIZE,totalPageCount);


        //每页开启一个线程，并行查询
        logger.error("开始异步查询>>>>>>>>>>>>>>>");
        long startTime=new Date().getTime();
        List<Future<List<AuthorInfoDto>>> futureList=new ArrayList<Future<List<AuthorInfoDto>>>();
        ExecutorService executorService= Executors.newFixedThreadPool(100);
        //ExecutorService executorService=Executors.newSingleThreadExecutor();
        for (int pageIndex=1;pageIndex<=totalPageCount;pageIndex++){//每页开启一个线程
            Map<String,String> pageParamMap=new HashMap<>();
            pageParamMap.put("EntrezSystem2.PEntrez.DbConnector.Term",keyWord);
            pageParamMap.put("EntrezSystem2.PEntrez.DbConnector.Cmd","PageChanged");
            pageParamMap.put("EntrezSystem2.PEntrez.PubMed.Pubmed_ResultsPanel.Pubmed_Pager.CurrPage",String.valueOf(pageIndex));
            pageParamMap.put("EntrezSystem2.PEntrez.DbConnector.LastQueryKey","1");
            futureList.add(executorService.submit(new PageDocSearcher(pageParamMap,city)));
        }

        //获取结果
        Map<String,Integer> resultMap=combineResultFromFutureList(futureList);

        //将结果写入txt文件
        writeInTxt(resultMap,"d:\\result.txt");

        //关闭线程池
        executorService.shutdown();
        long endTime=new Date().getTime();
        logger.error("执行完成,共耗时{}秒",(endTime-startTime)/1000);
        return "ok";
    }

    private Map<String,Integer> combineResultFromFutureList(List<Future<List<AuthorInfoDto>>> futureList){
        Map<String,Integer> resultMap=new HashMap<>();
        for (Future<List<AuthorInfoDto>> future:futureList){
            try {
                List<AuthorInfoDto> orgList=future.get();
                if (CollectionUtils.isEmpty(orgList)){
                    continue;
                }
                for (AuthorInfoDto org:orgList){
                    String organization=org.getOrganization();
                    if (resultMap.containsKey(organization)){
                        resultMap.put(organization,resultMap.get(organization)+1);
                    }else {
                        resultMap.put(organization,1);
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                logger.error("执行发生异常！",e);

            }
        }
        return resultMap;
    }

    private void writeInTxt(Map<String,Integer> resultMap,String txtFilePath){
        File targetFile=new File(txtFilePath);
        BufferedWriter bufferedWriter=null;
        try {
            bufferedWriter=new BufferedWriter(new FileWriter(targetFile));
            for (Map.Entry<String,Integer> entry:resultMap.entrySet()){
                String org=entry.getKey();
                String count= String.valueOf(entry.getValue());
                bufferedWriter.write(org);
                bufferedWriter.write("========>>>");
                bufferedWriter.write(count);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (bufferedWriter!=null){
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 分别请求每一页的每一个论文链接，解析作者信息
     */
    class PageDocSearcher implements Callable<List<AuthorInfoDto>> {
        private Map<String,String> params;
        private String city;
        PageDocSearcher(Map<String,String> params,String city){
            this.params=params;
            this.city=city;
        }
        @Override
        public List<AuthorInfoDto> call() throws Exception {
            List<AuthorInfoDto> authorInfoDtoList =new ArrayList<>();
            String pageHtml= HttpUtil.postString(MAIN_HOST,params);
            //String pageHtml=LocalHttpUtils.getHttpClient(MAIN_HOST+"?term=PDGF-BB");
            if (StringUtils.isBlank(pageHtml)){
                return authorInfoDtoList;
            }
            Elements elements=Jsoup.parse(pageHtml).select(".rslt");
            for (Element element:elements){
                Elements subElements=element.select("[href]");
                String paperIndex=subElements.get(0).attr("href");//论文ID
                String leafURL=ROOT_HOST+paperIndex;
                logger.error("论文链接：{}",leafURL);
                authorInfoDtoList.addAll(resolvePubInfo(leafURL,city));
            }
            return authorInfoDtoList;
        }
    }

    /**
     * 请求每个论文链接，解析作者信息
     * @param url 论文链接
     * @return
     * @throws Exception
     */
    private List<AuthorInfoDto> resolvePubInfo(String url,String city) throws Exception {
        List<AuthorInfoDto> organizationList=new ArrayList<AuthorInfoDto>();
        Element element=Jsoup.parse(HttpUtil.getString(url));
        Elements unitElementList=element.select("dl.ui-ncbi-toggler-slave");
        for (Element unitElement:unitElementList){
            Elements elements=unitElement.getElementsByTag("dd");
            for (Element element1:elements){
                String authorInfoStr=element1.text();//作者信息
                if (!authorInfoStr.contains("China")){//剔除非国内作者信息
                    logger.info("非国内作者论文，剔除");
                    continue;
                }
                if (StringUtils.isNoneBlank(city)&&!authorInfoStr.contains(city)){//过滤城市
                    continue;
                }

                String [] realAuthorList=authorInfoStr.split(";");//一条作者信息可能由；分割了多个作者
                for (String realAuthorInfoStr:realAuthorList){
                    if (realAuthorInfoStr.startsWith(" ")){
                        realAuthorInfoStr=realAuthorInfoStr.substring(1,realAuthorInfoStr.length());
                    }
                    String[] infoArray= realAuthorInfoStr.split(",");
                    if (infoArray.length<3||!infoArray[infoArray.length-1].contains("China")){
                        logger.info("格式非法！");
                        continue;
                    }

                    //获取组织信息(倒数第二个，之前的)
                    StringBuilder sb=new StringBuilder();
                    sb.append(infoArray[0]);
                    sb.append(",");
                    sb.append(infoArray[0]);
                    sb.append(",");
                    sb.append(infoArray[0]);
                    AuthorInfoDto authorInfo=new AuthorInfoDto();
                    authorInfo.setCity(city);
                    authorInfo.setPaperLink(url);
                    authorInfo.setOrganization(sb.toString());
                    organizationList.add(authorInfo);
                }

            }
        }
        return organizationList;
    }


    public static void main(String[] args) {
        DocServiceImpl docService=new DocServiceImpl();
        RequestModel requestModel=new RequestModel();
        requestModel.setKeyWord("PDGF-BB");
        try {
            docService.findDoc(requestModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
