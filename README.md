## Eunii Template Engine 사용 방법

#### java 버전: jdk11

1. 커멘트 창에서 jar파일이 위치한 곳에서 아래 명령어를 실행합니다.
``` shell
$ java -jar EuniiTemplateEngine.jar
```
2. '데이터 파일 경로를 입력해주세요' 문구가 출력되면 jason 형태의 데이터 파일의 경로를 입력 후 엔터를 칩니다.

ex)
```shell
데이터 파일 경로를 입력해주세요
users.txt
```
2. '탬플릿 파일 경로를 입력해주세요.' 문구가 출력되면 문법에 맞는 Template 형태의 데이터 파일의 경로를 입력 후 엔터를 칩니다. 
  
ex)
```shell
탬플릿 파일 경로를 입력해주세요
template.txt
```
3. 현재 경로에 결과 파일(out.txt)을 저장했습니다. 문구가 나오고 프로그램이 종료되면 해당 경로에 out.txt 파일이 생성됩니다.
파일은 기존 파일에 덧붙여 계속 추가됩니다.
   
4. 문법에 맞지 않거나 jason형태의 데이터를 입력하지 않으면 프로그램의 오류가 출력되면서 종료됩니다.
