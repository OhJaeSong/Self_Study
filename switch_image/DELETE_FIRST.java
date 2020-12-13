import java.io.File;
import java.util.ArrayList;

public class DELETE_FIRST {
	public static String TARGET_PATH   = "E:\\SWITCH_TEMP\\22";
	public static String NEW_PATH   = "E:\\SWITCH_TEMP";
    
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	public static void main(String[] args) {
		String fileName, filePathName, fileExt, fileTitleId;
		long fileSize, fileVersion;
		
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
					
					int beginIndex = fileName.indexOf("[");
					int endIndex = fileName.indexOf("]");
					
					moveFile(file, NEW_PATH + "\\" +  fileName.replace(".NSP",""));
				} 
			}
		
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

}