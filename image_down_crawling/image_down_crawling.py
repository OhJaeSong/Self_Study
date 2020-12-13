'''1. 파일 목록 얻기
(1) glob.glob(wildcard) - 유닉스 경로명 패턴 스타일로 파일 목록을 얻을 수 있다.
(2) os.listdir(path) - 지정된 디렉토리의 전체 파일 목록을 얻을 수 있다.
(3) dircache.listdir(path) - os.listdir(path)와 동일한 파일 목록을 전달한다.
path가 변경되지 않았을 때, dircache.listdir()은 다시 디렉토리 구조를 읽지 않고 이미 읽은 정보를 활용
dircache.annotate(head, list) - 일반 파일명과 디렉토리명을 구분해주는 함수

2. 디렉토리 다루기
os.chdir(path) - 작업하고 있는 디렉토리 변경
os.getcwd() - 현재 프로세스의 작업 디렉토리 얻기
os.remove( filename or path ) - 파일이나 디렉토리 지우기
os.mkdir( path ) - 디렉토리 만들기
os.makedirs( path ) - 디렉토리 만들기와 동일하지만 /tmp/include/gl/temp 처럼 긴 경로를 한번에 만들어 준다.
os.path.abspath(filename) - 파일의 상대 경로를 절대 경로로 바꾸는 함수
os.path.exists(filename) - 주어진 경로의 파일이 있는지 확인하는 함수
os.curdir() - 현재 디렉토리 얻기
os.pardir() - 부모 디렉토리 얻기
os.sep() - 디렉토리 분리 문자 얻기. windows는 \ linux는 / 를 반환한다.

4. 경로명 분리하기
os.path.basename(filename) - 파일명만 추출
os.path.dirname(filename) - 디렉토리 경로 추출
os.path.split(filename) - 경로와 파일명을 분리
os.path.splitdrive(filename) - 드라이브명과 나머지 분리 (MS Windows의 경우)
os.path.splitext(filename) - 확장자와 나머지 분리

https://tiktikeuro.tistory.com/174
pip install git+https://github.com/Joeclinton1/google-images-download.git
'''
def search(dirname):
    filenames = os.listdir(dirname)
    for filename in filenames:
        full_filename = os.path.join(dirname, filename)

        if os.path.isdir(full_filename):
            #googleImageDownload(full_filename, filename + " SWITCH IN GAMEPLAY", 30)
        else:
            filenameWithoutExt = os.path.splitext(full_filename)[0]
            ext = os.path.splitext(full_filename)[-1]

            print(filenameWithoutExt)
            #if ext == '.py': 
            #    print(full_filename)

def subSearch(dirname):
    try:
        filenames = os.listdir(dirname)
        for filename in filenames:
            full_filename = os.path.join(dirname, filename)
            if os.path.isdir(full_filename):
                search(full_filename)
            else:
                ext = os.path.splitext(full_filename)[-1]
                if ext == '.py': 
                    print(full_filename)
    except PermissionError:
        pass

def googleImageDownload(sDirname, sKeyword, nImageCount):
    response = google_images_download.googleimagesdownload()

    arguments = {
              "keywords" : sKeyword
            , "limit" : nImageCount
            , "print_urls": False
            , "output_directory" : sDirname
            , "image_directory" : "Images"  
            #, "no_directory": True
            , "format" : "jpg"
            }
    paths = response.download(arguments)
    print(paths)

def makeDir(sDirname, sKeyword, nImageCount):
    response = google_images_download.googleimagesdownload()    
    
    
import os
from google_images_download import google_images_download

targetDirPath = "F:\스위치 게임 Rom"
search(targetDirPath)




