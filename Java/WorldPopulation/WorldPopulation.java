import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WorldPopulation {
    
    //美国统计局显示世界人口数量的页面http://www.census.gov/popclock/index.php
    //经分析,该页面的世界人口数据请求地址是http://www.census.gov/popclock/data/population/world
    private static final String QUERY_URL = "http://www.census.gov/popclock/data/population/world";
    
    /**
     * 获得字符串形式的世界人口数
     * @return 从美国人口统计局抓取的世界人口数的字符串
     * @throws IOException
     */
    public static String getWorldPoulation() throws IOException{
        URL url = new URL(QUERY_URL);
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);
        urlConnection.connect();
        
        InputStream inputStream = urlConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
        BufferedReader in = new BufferedReader(inputStreamReader);
        
        StringBuilder content = new StringBuilder();
        String line = null;
        while( (line = in.readLine()) != null ){
            content.append(line);
        }
        in.close();
        urlConnection.disconnect();
        //System.out.println(content);
        
        String population = content.substring(66,76);
        //System.out.println(population);
        return population;
    }
}

