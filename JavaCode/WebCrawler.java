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
    // ����ʼ��URL��ӵ�listOfPendingURLs��Ȼ��ͨ��һ��whileѭ���ظ�����listOfPendingURLs��ÿһ��URL
     listOfPendingURLs.add(startingURL);
    while (!listOfPendingURLs.isEmpty() && 
        listOfTraversedURLs.size() <= 30) {
    	// ���б��е�һ��RULȥ���������RULû�б�������������д���
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
     // ����ʹ��foreachѭ������ҳ���е�ÿ��������listOfTraversedURLs�е�URL��ӵ�listOfPendingURLs��
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
					String line = input.nextLine();// ��Web��ȡÿһ��
					current = line.indexOf("http:", current);// Ѱ�Ҹ����е�URL
					while (current > 0) {
						int endIndex = line.indexOf("\"", current);// ����URL������"����
						if (endIndex > 0) { // Ensure that a correct URL is
											// found
							list.add(line.substring(current, endIndex)); // һ���п��ܰ������URL��
							current = line.indexOf("http:", endIndex);// ��������Ѱ����һ��URL
						} else
							current = -1;// ҳ���а�����URL��һ���б����ʽ����
					}
				}
			}
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		return list;
	}
}