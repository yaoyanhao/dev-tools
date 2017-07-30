package utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by vector01.yao on 2017/7/15.
 */
public class HttpUtil {
    private static final String CONTENT_CHARSET = "UTF-8";// 请求内容字符编码
    private static final int CONNECTION_TIMEOUT = 8 * 1000;// 连接超时时间

    /**
     * 根据URL获取响应字符串内容
     *
     * @param urlString 请求的URL（注意：URL字符串不能包含URL特殊字符）
     * @return 响应字符串
     * @throws Exception
     */
    public static String getString(String urlString) throws Exception {
        return new String(getByteArray(urlString), "UTF-8");
    }

    /**
     * 根据URL获取响应字符串内容
     *
     * @param urlString
     *            请求的URL（注意：URL字符串不能包含URL特殊字符）
     * @return 响应字符串
     * @throws Exception
     */
    public static String getString(String urlString,Map<String,String> params) throws Exception {
        if (params != null && params.size() != 0) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getValue() != null) {
                    sb.append(entry.getKey()
                            + "="
                            + URLEncoder.encode(entry.getValue(),
                            "UTF-8") + "&");
                }
            }
            urlString = urlString+"?"+sb.toString();
        }
        return new String(getByteArray(urlString), "UTF-8");
    }


    private static byte[] getByteArray(String urlString) throws Exception {
        byte[] result = null;
        HttpURLConnection connection = null;
        URL url = null;
        InputStream is = null;
        try {
            // 获得URL对象
            url = new URL(urlString);
            // 获得HttpURLConnection链接对象
            connection = (HttpURLConnection) url.openConnection();
            // 设置不适用缓存
            connection.setRequestProperty("Pragma", "no-cache");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);// 设置链接超时时间
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("NCBI-SID","CE8E7724969C1291_0071SID");
            connection.setRequestProperty("NCBI-PHID","CE8A171397BF77A100000000000B0002.m_2");
            // 进行连接，但是实际上connection.getInputStream()函数中才会真正发到
            connection.connect();
            // 取得输入流
            connection.getInputStream();
            // 获取输入流
            is = connection.getInputStream();
            // 创建缓冲输出流
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];// 1KB的字节缓冲区
            int length = 0;
            while ((length = is.read(buffer)) != -1) {
                bos.write(buffer, 0, length);
            }
            // 获得响应字节数组
            result = bos.toByteArray();
        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
        } finally {
            try {
                if (is != null) {
                    // 关闭响应输入流
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    // 断开HTTP链接
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 根据URL字符串和POST请求参数获取响应字节数组
     *
     * @param urlString 请求的URL（注意：URL字符串不能包含URL特殊字符）
     * @param params 请求参数的键值对
     * @return 响应字符串
     * @throws Exception
     */
    public static String postString(String urlString, Map<String, String> params) throws Exception {
        return new String(postByteArray(urlString, params), CONTENT_CHARSET);
    }

    /**
     * 根据URL字符串和POST请求参数获取响应字节数组
     *
     * @param urlString 请求的URL（注意：URL字符串不能包含URL特殊字符）
     * @param params 请求参数的键值对
     * @param charSet 设定返回报文字符集
     * @return 响应字符串
     * @throws Exception
     */
    public static String postString(String urlString, Map<String, String> params, String charSet) throws Exception {
        return new String(postByteArray(urlString, params), charSet);
    }

    /**
     * 根据URL字符串和POST请求参数获取响应字节数组
     *
     * @param urlString 请求的URL（注意：URL字符串不能包含URL特殊字符）
     * @param params 请求参数的键值对
     * @return 响应字节数组
     * @throws Exception
     */
    public static byte[] postByteArray(String urlString, Map<String, String> params) throws Exception {
        byte[] result = null;
        HttpURLConnection connection = null;
        URL url = null;
        InputStream is = null;
        try {
            // 获得URL对象
            url = new URL(urlString);
            // 获得HttpURLConnection链接对象
            connection = (HttpURLConnection) url.openConnection();
            // 设置不适用缓存
            connection.setRequestProperty("Pragma", "no-cache");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);// 设置链接超时时间
            connection.setRequestMethod("POST");
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "text/html;charset="+CONTENT_CHARSET);
            connection.setRequestProperty("NCBI-SID","CE8E7724969C1291_0071SID");
            connection.setRequestProperty("NCBI-PHID","CE8A171397BF77A100000000000B0002.m_2");
            // 进行连接，但是实际上connection.getInputStream()函数中才会真正发到
            connection.connect();
            // 判断是否有POST参数，如果有则将数据传到服务器
            if (params != null && params.size() != 0) {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (entry.getValue() != null) {
                        sb.append(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), CONTENT_CHARSET) + "&");
                    }
                }
                if (sb.length()>1){
                    sb.deleteCharAt(sb.length()-1);
                }

                // 获得输出流
                OutputStream os = connection.getOutputStream();
                os.write(sb.toString().getBytes(CONTENT_CHARSET));
                // 刷新输出流，确保数据被传到服务器
                os.flush();
                // 关闭输出流
                os.close();
            }
            // 取得输入流
            connection.getInputStream();
            // 获取输入流
            is = connection.getInputStream();
            // 创建缓冲输出流
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];// 1KB的字节缓冲区
            int length = 0;
            while ((length = is.read(buffer)) != -1) {
                bos.write(buffer, 0, length);
            }
            // 获得响应字节数组
            result = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (is != null) {
                    // 关闭响应输入流
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    // 断开HTTP链接
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String url = "https://www.ncbi.nlm.nih.gov/pubmed?EntrezSystem2.PEntrez.PubMed.Pubmed_ResultsPanel.Pubmed_Pager.CurrPage=10&EntrezSystem2.PEntrez.DbConnector.Term=PDGF-BB&EntrezSystem2.PEntrez.DbConnector.Cmd=PageChanged&EntrezSystem2.PEntrez.DbConnector.LastQueryKey=1";

        String response = null;
        try {
            response = HttpUtil.getString(url);
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println(response);
    }
}
