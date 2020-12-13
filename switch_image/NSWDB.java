import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NSWDB {
	// tag값의 정보를 가져오는 메소드
	private static String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		if (nValue == null)
			return null;
		return nValue.getNodeValue();
	}
	
	public static Connection dbConn;
    
    public static Connection getConnection()
    {
        try {
            String user = "system"; 
            String pw = "Dhxkzn2120";
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            
            Class.forName("oracle.jdbc.driver.OracleDriver");        
            dbConn = DriverManager.getConnection(url, user, pw);
            
            System.out.println("Database에 연결되었습니다.");
            
        } catch (ClassNotFoundException cnfe) {
            System.out.println("DB 드라이버 로딩 실패 :"+cnfe.toString());
        } catch (SQLException sqle) {
            System.out.println("DB 접속실패 : "+sqle.toString());
        } catch (Exception e) {
            System.out.println("Unkonwn error");
            e.printStackTrace();
        }
        return dbConn;     
    }
	
	public static void main(String[] args) {
		try {
			dbConn = getConnection();
			PreparedStatement pstm = null;
			List<String> list = new ArrayList<String>();
			
			String id, name, publisher, region, languages, group, imagesize, serial, titleid, imgcrc, filename, releasename, trimmedsize, firmware, type, card, kor_yn;
			
			// parsing할 url 지정(API 키 포함해서)
			String url = "http://nswdb.com/xml.php";

			DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
			Document doc = dBuilder.parse(url);

			// root tag
			doc.getDocumentElement().normalize();
			System.out.println("작업이 시작하였습니다.");

			// 파싱할 tag
			NodeList nList = doc.getElementsByTagName("release");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					
					id = getTagValue("id", eElement) == null ? "" : getTagValue("id", eElement);
					titleid = getTagValue("titleid", eElement) == null ? "" : getTagValue("titleid", eElement);
					name = getTagValue("name", eElement) == null ? "" : getTagValue("name", eElement).replaceAll(":", "").replaceAll("\\[", "").replaceAll("\\]", "");
					publisher = getTagValue("publisher", eElement) == null ? "" : getTagValue("publisher", eElement);
					region = getTagValue("region", eElement) == null ? "" : getTagValue("region", eElement);
					languages = getTagValue("languages", eElement) == null ? "" : getTagValue("languages", eElement);
					group = getTagValue("group", eElement) == null ? "" : getTagValue("group", eElement);
					imagesize = getTagValue("imagesize", eElement) == null ? "" : getTagValue("imagesize", eElement);
					serial = getTagValue("serial", eElement) == null ? "" : getTagValue("serial", eElement);
					imgcrc = getTagValue("imgcrc", eElement) == null ? "" : getTagValue("imgcrc", eElement);
					filename = getTagValue("filename", eElement) == null ? "" : getTagValue("filename", eElement);
					releasename = getTagValue("releasename", eElement) == null ? "" : getTagValue("releasename", eElement);
					trimmedsize = getTagValue("trimmedsize", eElement) == null ? "" : getTagValue("trimmedsize", eElement);
					firmware = getTagValue("firmware", eElement) == null ? "" : getTagValue("firmware", eElement);
					type = getTagValue("type", eElement) == null ? "" : getTagValue("type", eElement);
					card = getTagValue("card", eElement) == null ? "" : getTagValue("card", eElement);
					kor_yn = getTagValue("languages", eElement).indexOf("ko") > 0 ? "Y" : "N";
					
					if ("1".equals(type)) {
						type = "NSW Game";
					} else if ("3".equals(type)) {
						type = "NSWWare";
					} else if ("4".equals(type)) {
						type = "eShop";
					}; 
					
					list = new ArrayList<String>(Arrays.asList(titleid.split("\\+")));
					
					for (int i =0; i < list.size(); i++) {
						String quary = "MERGE INTO SWITCH_NSWDB"
						+ " USING DUAL"
						+ "	ON (ID = '" + id + "' AND TITLEID = '" + list.get(i) + "')"
						+ " WHEN MATCHED THEN"
						+ "	UPDATE SET GAME_NAME = '" + name + "'"
						+           ", PUBLISHER = '" + publisher + "'"
						+           ", KOR_YN = '" + kor_yn + "'"
						+           ", REGION = '" + region + "'"	
						+           ", LANGUAGES = '" + languages + "'"
						+           ", RELEASE_GROUP = '" + group + "'"
						+           ", IMAGESIZE = '" + imagesize + "'"
						+           ", SERIAL = '" + serial + "'"
						+           ", IMGCRC = '" + imgcrc + "'"
						+           ", FILENAME = '" + filename + "'"
						+           ", RELEASENAME = '" + releasename + "'"
						+           ", TRIMMEDSIZE = '" + trimmedsize + "'"
						+           ", FIRMWARE = '" + firmware + "'"
						+           ", SHOP_TYPE = '" + type + "'"
						+           ", CARD = '" + card + "'"
						+           ", LSCHG_DT = TO_CHAR(SYSDATE, 'YYYYMMDD')"
						+ " WHEN NOT MATCHED THEN"
						+ "	INSERT (ID, TITLEID,GAME_NAME,PUBLISHER,KOR_YN,REGION,LANGUAGES,RELEASE_GROUP,IMAGESIZE,SERIAL,IMGCRC,FILENAME,RELEASENAME,TRIMMEDSIZE,FIRMWARE,SHOP_TYPE,CARD,LSCHG_DT)"
						+ "	VALUES ('" + id + "',"
						+          "'" + list.get(i) + "',"
						+          "'" + name + "',"
						+          "'" + publisher + "',"
						+          "'" + kor_yn + "',"
						+          "'" + region + "',"
						+          "'" + languages + "',"
						+          "'" + group + "',"
						+          "'" + imagesize + "',"
						+          "'" + serial + "',"
						+          "'" + imgcrc + "',"
						+          "'" + filename + "',"
						+          "'" + releasename + "',"
						+          "'" + trimmedsize + "',"
						+          "'" + firmware + "',"
						+          "'" + type + "',"
						+          "'" + card + "',"
						+          " TO_CHAR(SYSDATE, 'YYYYMMDD'))"
						;
						//System.out.println(quary);
						pstm = dbConn.prepareStatement(quary);
						pstm.executeQuery();
						pstm.close();
					}
				}
			}
			
			dbConn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		System.out.println("작업이 완료되었습니다.");
	}
}