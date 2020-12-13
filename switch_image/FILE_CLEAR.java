import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FILE_CLEAR {
	public static String TARGET_PATH   = "E:\\JDownloader_Down";
	public static String ETC_FILE_PATH = "E:\\SWITCH_TEMP\\";
	public static String NEW_FILE_PATH = "E:\\SWITCH\\";
	
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
	
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	public static void main(String[] args) {
		File dirFile = new File(TARGET_PATH);
		if (dirFile.exists() == false) {
			dirFile.mkdir();
		}
		
		dirFile = new File(ETC_FILE_PATH);
		if (dirFile.exists() == false) {
			dirFile.mkdir();
		}
		
		dirFile = new File(NEW_FILE_PATH);
		if (dirFile.exists() == false) {
			dirFile.mkdir();
		}
		
		String fileName, filePathName, fileExt, fileTitleId;
		long fileSize, fileVersion;
		dbConn = getConnection();
		
		try {
			File f = new File(TARGET_PATH);
			
			ArrayList<File> subFiles = new ArrayList<File>();
			
			if (!f.exists()) {
				System.out.println("디렉토리가 존재하지 않습니다");
				return;
			}
			
			System.out.println("작업이 시작하였습니다.");
			
			findSubFiles(f, subFiles); //경로 안에서 파일을 전부 subFiles로 리턴함.
			
			for (File file : subFiles) {
				if (file.isFile()) {
					fileName = file.getName();
					filePathName = file.getCanonicalPath();
					fileSize = file.length();
					fileExt = file.getName().substring(file.getName().lastIndexOf( "." ) + 1).toUpperCase();
					
					if ("NSP".equals(fileExt) == false) {
						moveFile(file, ETC_FILE_PATH + fileName);
					} else {
						Map gameInfoMap = getFileInfo(fileName);
						
						if (gameInfoMap != null) {
							String fileDsc = gameInfoMap.get("fileDsc").toString();
							long fileVer = Long.parseLong(gameInfoMap.get("fileVer").toString());
							long dlcSeq = Long.parseLong(gameInfoMap.get("DLC_SEQ").toString());
							String newFilePath = gameInfoMap.get("GAME_NAME").toString().replaceAll("_", " ");
							String newFileNm = gameInfoMap.get("GAME_NAME").toString().replaceAll("_", " ") + " [" + gameInfoMap.get("fileTitleId").toString() + "][" + ("DLC".equals(fileDsc) ? "DLC" : "v" + fileVer) +"].nsp";
							
							gameInfoMap.put("fileNm", newFileNm);
							gameInfoMap.put("fileSize", fileSize);
							
							if (gameInfoMap.get("TITLEID_CNTNT_DSC") == null) {
								moveFile(file, NEW_FILE_PATH + newFilePath + "\\" + newFileNm, NEW_FILE_PATH + newFilePath);
								gameInfoMap.put("fileNm", NEW_FILE_PATH + newFilePath + "\\" + newFileNm);
								
								insertData(dbConn, gameInfoMap); 
							} else if ((fileVer > Long.parseLong(gameInfoMap.get("UPDATE_VER").toString()) && "UPDATE".equals(gameInfoMap.get("fileDsc")))
									|| (fileSize > Long.parseLong(gameInfoMap.get("TITLEID_FL_SIZE").toString()))){
								File delFile = new File(gameInfoMap.get("UPDATE_FL_NM").toString());
								delFile.delete();
								
								moveFile(file, NEW_FILE_PATH + newFilePath + "\\" + newFileNm, NEW_FILE_PATH + newFilePath);
								gameInfoMap.put("fileNm", NEW_FILE_PATH + newFilePath + "\\" + newFileNm);
								
								deleteData(dbConn, gameInfoMap);
								insertData(dbConn, gameInfoMap);
							} else {
								System.out.println("---------------");
						    	System.out.println("Delete");
						    	System.out.println(gameInfoMap);
						    	System.out.println("---------------");
								file.delete();
							}
						} else {
							moveFile(file, ETC_FILE_PATH + fileName); //파일명 규칙에 안 맞는 녀석들은 옮긴다.
						}
					}
				} 
			}
		
			deleteEmptyDir(f); // 빈 디렉토리 삭제
			
			dbConn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("작업이 완료되었습니다.");
		
	}

	public static void findSubFiles(File parentFile, ArrayList<File> subFiles) {
		if (parentFile.isFile()) {
			subFiles.add(parentFile);
		} else if (parentFile.isDirectory()) {
			subFiles.add(parentFile);
			File[] childFiles = parentFile.listFiles();
			for (File childFile : childFiles) {
				findSubFiles(childFile, subFiles);
			}
		}
	}
	
	public static int deleteEmptyDir(File file) {
        if (!file.isDirectory()) return 0;
        
        int delCnt=0;
        
        for (File subFile : file.listFiles()) {
            if (subFile.isDirectory()) {
                delCnt+=deleteEmptyDir(subFile);
            }
        }
        if (file.listFiles().length==0) {
        	try {
        		if (TARGET_PATH.equals(file.getCanonicalPath()) == false) {
        			file.delete();
                    delCnt++;        			
        		}
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        }        
        return delCnt;
    }
	
	public static String moveFile(File originalFile, String afterFilePathName) {
        try{
            if(originalFile.renameTo(new File(afterFilePathName))){ //파일 이동
            	Thread.sleep(200); //0.2초 대기
                return afterFilePathName; //성공시 성공 파일 경로 return
            }else{
                return null;
            }
 
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
	
	public static String moveFile(File originalFile, String afterFilePathName, String afteFilePath) {
		File dirFile = new File(afteFilePath);
		if (dirFile.exists() == false) {
			dirFile.mkdir();
		}
		
		return moveFile(originalFile, afterFilePathName);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getFileInfo(String fileName) {
		int beginIndex = fileName.indexOf("[");
		int endIndex = fileName.indexOf("]");
		int versionIndex = fileName.indexOf("[v");
		
		if (beginIndex > 0 && endIndex > 0 && (endIndex - beginIndex) == 17) { //titleId가 확인되는 경우
			String fileTitleId = fileName.substring(beginIndex + 1, endIndex).toUpperCase();
			
			if ("0100".equals(fileTitleId.substring(0, 4)) == true) {
				Map gameInfoMap = getDbInfo(dbConn, fileTitleId);
				
				if (gameInfoMap.isEmpty() == true) {
					
					gameInfoMap = new HashMap();
					gameInfoMap.put("DSC", "3");
					gameInfoMap.put("TITLEID_CNTNT_DSC", null);
					gameInfoMap.put("TITLEID_FL_SIZE", 0);
					gameInfoMap.put("GAME_NAME", fileName.substring(0, beginIndex).trim().toUpperCase());
					gameInfoMap.put("PUBLISHER", null);
					gameInfoMap.put("ID", 0);

					if ("000".equals(fileTitleId.substring(13)) == false && "800".equals(fileTitleId.substring(13)) == false) { //dlc인 경우 1을 리턴
						gameInfoMap.put("DLC_SEQ", 1);
					} else {
						gameInfoMap.put("DLC_SEQ", 0);
					}
					
					gameInfoMap.put("UPDATE_FL_NM", null);
					gameInfoMap.put("UPDATE_VER", 0);
		        	gameInfoMap.put("EXE_YN", "N");
		        	gameInfoMap.put("KOR_YN", "N");
		        	gameInfoMap.put("PLAY_YN", "N");
		        	gameInfoMap.put("END_YN", "N");
		        	gameInfoMap.put("KOR_PATCH_YN", "N");
		        	gameInfoMap.put("KOR_PATCH_FL_NM", null);
		        	gameInfoMap.put("KOR_PATCH_REQ_VER", null);
				}
				
				if ("000".equals(fileTitleId.substring(13)) == false && "800".equals(fileTitleId.substring(13)) == false) { //dlc인 경우 1을 리턴
					gameInfoMap.put("fileVer", 1);
					gameInfoMap.put("fileDsc", "DLC");
				} else if ("000".equals(fileTitleId.substring(13)) == true ){
					gameInfoMap.put("fileVer", 0);
					gameInfoMap.put("fileDsc", "GAME");
				} else if ("800".equals(fileTitleId.substring(13)) == true && isStringLong(fileName.substring(versionIndex + 2, fileName.indexOf("]", versionIndex + 1))) == true) {
					gameInfoMap.put("fileVer", Long.parseLong(fileName.substring(versionIndex + 2, fileName.indexOf("]", versionIndex + 1))));
					gameInfoMap.put("fileDsc", "UPDATE");
				} else {
					return null;
				}
				
				gameInfoMap.put("fileTitleId", fileTitleId);
				
				return gameInfoMap;
			}
		} 
		
		return null;
	}
    
    public static boolean isStringLong(String s) {
	    try {
	        Long.parseLong(s);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked"})
	public static Map getDbInfo(Connection dbConn, String titleId) {
    	PreparedStatement pstm = null;
    	ResultSet rs = null;
    	Map resultMap = new HashMap();;
		
		String query =  "SELECT *\r\n" + 
						"  FROM (\r\n" + 
						"		SELECT DSC\r\n" + 
						"			 , MAX(TITLEID_CNTNT_DSC) TITLEID_CNTNT_DSC\r\n" + 
						"			 , MAX(TITLEID_FL_SIZE) TITLEID_FL_SIZE\r\n" +
						"			 , GAME_NAME\r\n" + 
						"			 , PUBLISHER\r\n" + 
						"			 , ID\r\n" + 
						"			 , MAX(UPDATE_VER) UPDATE_VER\r\n" + 
						"			 , MAX(UPDATE_FL_NM) UPDATE_FL_NM\r\n" + 
						"			 , NVL(MAX(DLC_SEQ), 0) + 1 DLC_SEQ\r\n" + 
						"			 , MAX(EXE_YN) EXE_YN\r\n" + 
						"			 , MAX(KOR_YN) KOR_YN\r\n" + 
						"			 , MAX(PLAY_YN) PLAY_YN\r\n" + 
						"			 , MAX(END_YN) END_YN\r\n" + 
						"			 , MAX(KOR_PATCH_YN) KOR_PATCH_YN\r\n" + 
						"			 , MAX(KOR_PATCH_FL_NM) KOR_PATCH_FL_NM\r\n" + 
						"			 , MAX(KOR_PATCH_REQ_VER) KOR_PATCH_REQ_VER\r\n" + 
						"		  FROM (\r\n" + 
						"				SELECT '1' DSC\r\n" + 
						"				     , CASE WHEN TITLEID = ? THEN CNTNT_DSC END TITLEID_CNTNT_DSC\r\n" + 
						"				     , CASE WHEN TITLEID = ? THEN FL_SIZE END TITLEID_FL_SIZE\r\n" +
						"				     , GAME_NAME\r\n" + 
						"				     , PUBLISHER\r\n" + 
						"				     , ID\r\n" + 
						"				     , CASE WHEN CNTNT_DSC = 'UPDATE' THEN VER END UPDATE_VER\r\n" + 
						"				     , FL_NM AS UPDATE_FL_NM\r\n" + 
						"					 , CASE WHEN CNTNT_DSC = 'DLC' THEN DLC_SEQ ELSE -1 END DLC_SEQ\r\n" + 
						"					 , EXE_YN\r\n" + 
						"					 , KOR_YN\r\n" + 
						"					 , PLAY_YN\r\n" + 
						"					 , END_YN\r\n" + 
						"					 , KOR_PATCH_YN\r\n" + 
						"					 , KOR_PATCH_FL_NM\r\n" + 
						"					 , KOR_PATCH_REQ_VER\r\n" + 
						"				  FROM SWITCH_IMAGE\r\n" + 
						"				 WHERE TITLEID LIKE CONCAT(SUBSTR(?, 1, 12), '%')\r\n" + 
						"				)\r\n" + 
						"		GROUP BY DSC, GAME_NAME, PUBLISHER, ID\r\n" + 
						"\r\n" + 
						"		UNION ALL\r\n" + 
						"\r\n" + 
						"		SELECT '2', NULL, 0, GAME_NAME, PUBLISHER, ID, 0, NULL, 0, 'N', 'N', 'N', 'N', 'N', NULL, NULL\r\n" + 
						"		  FROM SWITCH_NSWDB\r\n" + 
						"		 WHERE TITLEID LIKE CONCAT(SUBSTR(?, 1, 12), '%')\r\n" + 
						"		ORDER BY ID\r\n" + 
						"		)\r\n" + 
						"WHERE ROWNUM = 1";
		try {
			pstm = dbConn.prepareStatement(query);
			
			pstm.setString(1, titleId);
			pstm.setString(2, titleId);
			pstm.setString(3, titleId);
			pstm.setString(4, titleId);
			
	        rs = pstm.executeQuery();
        	
	        while(rs.next()){
	        	resultMap.put("DSC", rs.getString("DSC"));
	        	resultMap.put("TITLEID_CNTNT_DSC", rs.getString("TITLEID_CNTNT_DSC"));
	        	resultMap.put("TITLEID_FL_SIZE", rs.getString("TITLEID_FL_SIZE"));
	        	resultMap.put("GAME_NAME", rs.getString("GAME_NAME"));
	        	resultMap.put("PUBLISHER", rs.getString("PUBLISHER"));
	        	resultMap.put("ID", rs.getLong("ID"));
	        	resultMap.put("UPDATE_VER", rs.getLong("UPDATE_VER"));
	        	resultMap.put("UPDATE_FL_NM", rs.getString("UPDATE_FL_NM"));
	        	resultMap.put("DLC_SEQ", rs.getLong("DLC_SEQ"));
	        	resultMap.put("EXE_YN", rs.getString("EXE_YN"));
	        	resultMap.put("KOR_YN", rs.getString("KOR_YN"));
	        	resultMap.put("PLAY_YN", rs.getString("PLAY_YN"));
	        	resultMap.put("END_YN", rs.getString("END_YN"));
	        	resultMap.put("KOR_PATCH_YN", rs.getString("KOR_PATCH_YN"));
	        	resultMap.put("KOR_PATCH_FL_NM", rs.getString("KOR_PATCH_FL_NM"));
	        	resultMap.put("KOR_PATCH_REQ_VER", rs.getString("KOR_PATCH_REQ_VER"));
	        }
		} catch (Exception e) {
            e.printStackTrace();
		} finally{
			if(pstm != null) try{ pstm.close();} catch(SQLException e){};
			if(rs != null) try{ rs.close();} catch(SQLException e){};
		}
    	
    	return resultMap;
    }
    
    @SuppressWarnings({ "rawtypes"})
	public static void insertData(Connection dbConn, Map gameInfoMap) {
    	PreparedStatement pstm = null;
		
		String query =  "INSERT INTO SWITCH_IMAGE\r\n" + 
				"(TITLEID, GAME_NAME, PUBLISHER, ID, CNTNT_DSC, VER, DLC_SEQ, FL_NM, FL_SIZE, EXE_YN, KOR_YN, PLAY_YN, END_YN, KOR_PATCH_YN, KOR_PATCH_FL_NM, KOR_PATCH_REQ_VER, LSCHG_DT)\r\n" + 
				"VALUES\r\n" + 
				"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,TO_CHAR(SYSDATE, 'YYYYMMDD'))";
		try {
			pstm = dbConn.prepareStatement(query);
			
			pstm.setString(1, gameInfoMap.get("fileTitleId").toString());
			pstm.setString(2, gameInfoMap.get("GAME_NAME") == null ? "" : gameInfoMap.get("GAME_NAME").toString());
			pstm.setString(3, gameInfoMap.get("PUBLISHER") == null ? "" : gameInfoMap.get("PUBLISHER").toString());
			pstm.setLong(4, gameInfoMap.get("ID") == null ? 0 : Long.parseLong(gameInfoMap.get("ID").toString()));
			pstm.setString(5, gameInfoMap.get("fileDsc") == null ? "" : gameInfoMap.get("fileDsc").toString());
			pstm.setLong(6, gameInfoMap.get("fileVer") == null ? 0 : Long.parseLong(gameInfoMap.get("fileVer").toString()));
			pstm.setString(7, gameInfoMap.get("DLC_SEQ") == null ? "" : gameInfoMap.get("DLC_SEQ").toString());
			pstm.setString(8, gameInfoMap.get("fileNm") == null ? "" : gameInfoMap.get("fileNm").toString());
			pstm.setLong(9, gameInfoMap.get("fileSize") == null ? 0 : Long.parseLong(gameInfoMap.get("fileSize").toString()));
			pstm.setString(10, gameInfoMap.get("DSC") == null ? "" : gameInfoMap.get("DSC").toString());
			pstm.setString(11, gameInfoMap.get("KOR_YN") == null ? "" : gameInfoMap.get("KOR_YN").toString());
			pstm.setString(12, gameInfoMap.get("PLAY_YN") == null ? "" : gameInfoMap.get("PLAY_YN").toString());
			pstm.setString(13, gameInfoMap.get("END_YN") == null ? "" : gameInfoMap.get("END_YN").toString());
			pstm.setString(14, gameInfoMap.get("KOR_PATCH_YN") == null ? "" : gameInfoMap.get("KOR_PATCH_YN").toString());
			pstm.setString(15, gameInfoMap.get("KOR_PATCH_FL_NM") == null ? "" : gameInfoMap.get("KOR_PATCH_FL_NM").toString());
			pstm.setString(16, gameInfoMap.get("KOR_PATCH_REQ_VER") == null ? "" : gameInfoMap.get("KOR_PATCH_REQ_VER").toString());
			
	        pstm.executeQuery();
        	
		} catch (Exception e) {
            e.printStackTrace();
            System.out.println("---------------");
        	System.out.println("Insert Error");
        	System.out.println(gameInfoMap);
        	System.out.println("---------------");
		} finally{
			if(pstm != null) try{ pstm.close();} catch(SQLException e){};
		}
    }
    
    @SuppressWarnings({ "rawtypes"})
	public static void deleteData(Connection dbConn, Map gameInfoMap) {
    	System.out.println("---------------");
    	System.out.println("Data Update");
    	System.out.println(gameInfoMap);
    	System.out.println("---------------");
    	
    	PreparedStatement pstm = null;
		
		String query =  "DELETE FROM SWITCH_IMAGE WHERE TITLEID = ?";
		try {
			pstm = dbConn.prepareStatement(query);
			
			pstm.setString(1, gameInfoMap.get("fileTitleId").toString());
			
	        pstm.executeQuery();
		} catch (Exception e) {
            e.printStackTrace();
		} finally{
			if(pstm != null) try{ pstm.close();} catch(SQLException e){};
		}
    }
}