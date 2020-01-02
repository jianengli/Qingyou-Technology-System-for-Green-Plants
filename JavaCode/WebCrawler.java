package qingyouSystem;
 import java.util.Scanner;
 import javafx.event.ActionEvent;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
 import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
 public class WebCrawler {
  public static void main(String[] args) {
  }
   public static void crawler(String startingURL, String word) {
	LoginInterface.webBrowser.getChildren().clear();
	LoginInterface.pWC.getChildren().clear();
    ArrayList<String> listOfPendingURLs = new ArrayList<String>();
    ArrayList<String> listOfTraversedURLs = new ArrayList<String>();
    // 将起始的URL添加到listOfPendingURLs，然后通过一个while循环重复处理listOfPendingURLs中每一个URL
     listOfPendingURLs.add(startingURL);
    while (!listOfPendingURLs.isEmpty() && 
        listOfTraversedURLs.size() <= 30) {
    	// 将列表中第一个RUL去除，如果该RUL没有被处理过则对其进行处理
      String urlString = listOfPendingURLs.remove(0);
      if (!listOfTraversedURLs.contains(urlString)) {
        listOfTraversedURLs.add(urlString);
        //System.out.println("Craw " + urlString);
        if (contains(urlString, word)) {
        	Hyperlink hpl = new Hyperlink(urlString);
        	LoginInterface.pWC.getChildren().add(hpl);
        	hpl.setOnAction((ActionEvent e) -> {
                LoginInterface.webEngine.load(urlString);
            });
        	 VBox.setVgrow(LoginInterface.browser, Priority.ALWAYS);
        	//str += "The URL " + hpl+ "contains the word" + word+System.getProperty("line.separator");
          //System.out.println("The URL " + urlString + " contains the word " + word);
          System.out.println("The number of pages been searched is " + listOfTraversedURLs.size());
          if(listOfTraversedURLs.size()>=30)break;
        }
     // 程序使用foreach循环，将页面中的每个不存在listOfTraversedURLs中的URL添加到listOfPendingURLs中
        for (String s: getSubURLs(urlString)) {
          if (!listOfTraversedURLs.contains(s))
            listOfPendingURLs.add(s);
        }
      }
    }
    LoginInterface.webBrowser.getChildren().add( LoginInterface.browser);
  }
   public static boolean contains(String urlString, String word) {
    try {
      java.net.URL url = new java.net.URL(urlString);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
		//Scanner input = new Scanner(br);
 		String line;
        while ((line = br.readLine()) != null) {
          if (line.indexOf(word) > 0)
            return true;
      }
    }
    catch (Exception ex) {
      //System.out.println("Error: " + ex.getMessage());
    }
     return false;
  }
 	public static ArrayList<String> getSubURLs(String urlString) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			java.net.URL url = new java.net.URL(urlString);
			try (Scanner input = new Scanner(url.openStream())) {
				int current = 0;
				while (input.hasNext()) {
					String line = input.nextLine();// 从Web读取每一行
					current = line.indexOf("http:", current);// 寻找该行中的URL
					while (current > 0) {
						int endIndex = line.indexOf("\"", current);// 假设URL以引号"结束
						if (endIndex > 0) { // Ensure that a correct URL is
											// found
							list.add(line.substring(current, endIndex)); // 一行中可能包含多个URL，
							current = line.indexOf("http:", endIndex);// 方法继续寻找下一个URL
						} else
							current = -1;// 页面中包含的URL以一个列表的形式返回
					}
				}
			}
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		return list;
	}
}