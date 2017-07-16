package service;

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

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by vector01.yao on 2017/7/15.
 */
@Service
public class DocServiceImpl implements DocService {
    private Logger logger= LoggerFactory.getLogger(DocServiceImpl.class);

    private static final String ROOT="https://www.ncbi.nlm.nih.gov";
    private static final String MAIN_HOST="https://www.ncbi.nlm.nih.gov/pubmed";

    @Override
    public String getDoc(final String keyWord) throws InterruptedException {
        //参数校验
        if (StringUtils.isBlank(keyWord)){
            logger.error("keyWord参数不能为空！");
            throw new RuntimeException("keyWord参数不能为空！");
        }

        // 获取总记录数，并计算总页数
        int defaultPageSize=20;
        String rootHtml=HttpUtil.sendGet(MAIN_HOST,"term="+keyWord);//搜索主页内容
        Document parentDoc= Jsoup.parse(rootHtml);
        Element totalCountElement=parentDoc.getElementById("resultcount");
        int resultCount=Integer.parseInt(totalCountElement.attr("value"));
        logger.error("总记录数：{}",resultCount);
        int pageCount= PageUtil.getPageCount(defaultPageSize,resultCount);

        //开始异步查询
        logger.error("start.........");
        List<Future<List<String>>> futureList=new ArrayList<Future<List<String>>>();
        ExecutorService executorService= Executors.newCachedThreadPool();
        for (int i=1;i<=1;i++){//每页开启一个线程
            //final String params="term="+keyWord+"&EntrezSystem2.PEntrez.PubMed.Pubmed_ResultsPanel.Pubmed_Pager.CurrPage="+i;
            String params="term="+keyWord;
            futureList.add(executorService.submit(new DocSearcher(params)));
        }

        //获取结果
        Map<String,Integer> resultMap=combineResultFromFutureList(futureList);

        //将结果写入txt文件
        writeInTxt(resultMap,"d:\\result.txt");

        //关闭线程池
        executorService.shutdown();
        return "ok";
    }

    /**
     *
     * @return
     */
    private Map<String,Integer> combineResultFromFutureList(List<Future<List<String>>> futureList){
        Map<String,Integer> resultMap=new HashMap<>();
        for (Future<List<String>> future:futureList){
            try {
                List<String> orgList=future.get();
                if (CollectionUtils.isEmpty(orgList)){
                    continue;
                }
                for (String org:orgList){
                    if (resultMap.containsKey(org)){
                        resultMap.put(org,resultMap.get(org)+1);
                    }else {
                        resultMap.put(org,1);
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                logger.error("执行发生异常！");
            }
        }
        return resultMap;
    }


    class DocSearcher implements Callable<List<String>>{
        private String params;
        DocSearcher(String params){
            this.params=params;
        }
        @Override
        public List<String> call() throws Exception {
            String pageHtml=HttpUtil.sendPost(MAIN_HOST,params,false);//请求每一页
            Elements elements=Jsoup.parse(pageHtml).select(".rslt");
            List<String> orgList=new ArrayList<>();
            for (Element element:elements){//请求每个子链接
                Elements subElements=element.select("[href]");
                String childAddr=subElements.get(0).attr("href");
                //请求子页面
                String childUrl=ROOT+childAddr;
                logger.error("链接：{}",childUrl);
                orgList.addAll(getPubInfo(childUrl));
            }
            return orgList;
        }
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

    private List<String> getPubInfo(String url){
        List<String> organizationList=new ArrayList<>();
        String htmlStr=HttpUtil.sendGet(url,"");
        Element element=Jsoup.parse(htmlStr);
        Elements unitElementList=element.select("dl.ui-ncbi-toggler-slave");
        for (Element unitElement:unitElementList){
            Elements elements=unitElement.getElementsByTag("dd");
            for (Element element1:elements){
                String authorInfo=element1.text();
                String[] infoArray=authorInfo.split(",");
                if (!authorInfo.contains("China")){
                    logger.info("非国内....");
                    continue;
                }
                organizationList.add(infoArray[1]);
            }
        }
        return organizationList;
    }


    public static void main(String[] args) throws InterruptedException {
        DocServiceImpl test=new DocServiceImpl();
        test.getDoc("PDGF-BB");
        //test.getPubInfo("https://www.ncbi.nlm.nih.gov/pubmed/28708583");
    }
}
