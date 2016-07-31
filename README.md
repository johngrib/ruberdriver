# ruberdriver
* version : 0.5.12.v20160724

## index
1. [특징](#특징)
1. [사용법](#사용법)
    1. [초간편 사용법](#초간편-사용법)
    1. [좀 더 자세한 사용법](#좀-더-자세한-사용법)
        1. [설정 파일의 구조](#설정-파일의-구조)
        1. [Syntax](#syntax)
        1. [Options](#options)
        1. [실행 예제](#실행-예제)
        1. [로그 파일](#로그-파일)
1. [빌드하는 방법](#빌드하는-방법)
1. [library](#library-license)

## 특징
* ruberdriver 는 프로그래밍 언어를 잘 모르는 사람을 위한 간단한 웹 브라우저 매크로 프로그램입니다.
* json 설정 파일에 시나리오 스크립트를 작성하는 방식으로 사용할 수 있습니다.
* 옵션에 따라 여러 시나리오를 병렬로 실행할 수도 있습니다.
* 반복적인 웹 작업(인기 가수 콘서트 예매, 대학교 수강신청 등), 프론트 엔드 테스트 등의 용도에 사용할 수 있습니다.
* 작업 내용과 결과는 log 파일에 저장되어 따로 모아볼 수 있습니다.
* 스크린샷을 찍는 기능이 있어 log 와 비교해가며 결과를 분석할 수 있습니다.

## 사용법
### 초간편 사용법
1. [releases](https://github.com/johngrib/ruberdriver/releases) 에서 최신 버전의 압축 파일을 다운받습니다.
1. 압축을 풀고 install.sh 를 실행합니다.
1. run.sh 를 실행하면 예제 시나리오 스크립트 5 개를 병렬로 실행합니다.
1. 크롬 브라우저가 여러 개가 동시에 실행되고, 시나리오대로 페이지를 이동하고 클릭하는 등의 모습을 볼 수 있습니다.
1. ruberdriver.json 파일을 열고 scenario 와 item 을 자신의 필요와 용도에 맞게 편집해 사용하면 됩니다.

### 좀 더 자세한 사용법
1. [releases](https://github.com/johngrib/ruberdriver/releases) 에서 최신 버전의 압축 파일을 다운받습니다.
1. 압축을 풀고 install.sh 를 실행하면 chromedriver 를 다운받아, 실행할 수 있는 상태가 됩니다.
1. [ruberdriver.json](https://github.com/johngrib/ruberdriver/blob/master/ruberdriver.json) 을 편집합니다. 문법은 아래의 [script syntax](#syntax) 를 참조합니다.
1. `java -jar ruberdriver.jar [options]...` 와 같이 실행합니다. 옵션 목록은 아래의 [실행 옵션](#options) 항목을 참조합니다.

#### 설정 파일의 구조
* chromedriver : 다운로드 받은 [chromedriver](https://sites.google.com/a/chromium.org/chromedriver/downloads) 의 경로를 지정해줍니다. chromedriver 가 없으면 크롬을 사용할 수 없습니다.
* pics : 스크린샷을 보관할 경로를 지정해줍니다.
* log_path : 로그 파일을 보관할 경로를 지정해줍니다.
* scenario : 테스트 시나리오의 집합입니다.
    * 테스트 시나리오 하나하나는 name 과 action 으로 구성되며, action 은 item 의 배열입니다.
* item : 테스트 시나리오에서 사용할 action 들의 집합이며, 기능의 목적에 따라 모듈화를 할 수 있습니다.
    * item 하나하나는 name 과 action 으로 구성되며, action 은 [명령어 sentence](#syntax) 의 배열입니다.

```javascript
{
    "chromedriver" : "./chromedriver",
    "pics" : "~/pics/",
    "log_path" : "./logs/",
    "scenario" : {
        "go_test" : {
            "name" : "google search",
            "action" : [
                "open_chrome",
                "goto_google",
                "search_google_ruberdriver",
                "close_chrome",
            ]
        },
    },
    "item" : {
        "open_chrome" : {
            "name" : "open chrome",
            "action" : [ "Open chrome", "Wait 1000" ],
        },
        "close_chrome" : {
            "name" : "close chrome",
            "action" : [ "Close chrome" ],
        },
        "goto_google" : {
            "name" : "goto google",
            "action" : [
                "Goto http://www.google.com",
                "Wait 500"
            ]
        },
        "search_google_ruberdriver" : {
            "name" : "search google",
            "action" : [
                "Find id lst-ib",
                "Sendkeys ruberdriver",
                "Sendkey ENTER",
                "Wait 1000",
                "Find linkText Commits · johngrib/ruberdriver · GitHub",
                "Click 1",
                "Find linkText ruberdriver",
                "Click 1",
                "Wait 5000"
            ]
        },
    }
}
```

#### Syntax
* Goto
    * 웹 브라우저 주소창에 주어진 주소를 입력합니다.
    * examples
    ```
    "Goto https://github.com/johngrib/ruberdriver",
    ```
* Open
    * 웹 브라우저를 실행합니다.
    * 모든 시나리오에서 첫 번째로 실행해야 하는 명령어입니다.
    * examples
    ```
    "Open chrome",
    ```
* Close
    * 웹 브라우저 탭을 닫습니다.
    * examples
    ```
    "Close chrome",
    ```
* Quit
    * 웹 브라우저를 종료합니다.
    * examples
    ```
    "Quit chrome",
    ```
* Alert
    * alert 경고창을 핸들링합니다.
    * 두 가지 파라미터를 지정하여 사용합니다.
        * access : 확인 버튼을 누릅니다.
        * dissmiss : 경고창을 닫습니다.
    * examples
    ```
    "Alert access",
    "Alert dismiss",
    ```
* Find
    * DOM 엘리먼트를 검색합니다.
    * 8 가지 메소드를 사용할 수 있습니다.
        * className : class 이름으로 검색합니다.
        * cssSelector : cssSelector 로 검색합니다.
        * id : id 로 검색합니다.
        * linkText : a 태그의 링크 텍스트로 검색합니다. 화면상의 텍스트와 일치하는 링크를 검색합니다.
        * name : name attribute 로 검색합니다.
        * partialLinkText : 주어진 문자열을 포함하는 링크 텍스트를 갖는 링크를 검색합니다.
        * tagName : 태그 이름으로 검색합니다.
        * xpath : xpath 로 검색합니다.
    * examples
    ```
    "Find cssSelector #syntax",
    "Find linkText 로그인",
    ```
* Cfind
    * Find 로 찾은 엘리먼트의 child 엘리먼트를 찾습니다.
    * 반드시 Find 를 먼저 호출한 이후 실행해야 합니다.
    * 그 외의 사용법은 Find 와 똑같습니다.
    * example : 다음 예제는 Find 와 Cfind 의 연계를 보여줍니다.
    ```
    "Find id gnb-beverage",
    "Mouseover",
    "Cfind linkText 신선주스",
    "Click 1",
    ```
* Click
    * 엘리먼트를 클릭합니다.
    * 숫자 파라미터를 지정하여 사용합니다. 파라미터를 지정하지 않으면 디폴트 값으로 1 이 지정됩니다.
        * 1 : 클릭합니다.
        * 2 : 더블 클릭합니다.
    * examples
    ```
    "Click",
    "Click 1",
    "Click 2",
    ```
* Select
    * select 엘리먼트의 option 을 선택합니다.
    * 3 가지 메소드를 사용할 수 있습니다.
        * index : 순서로 option 을 선택합니다.
        * text : 화면상에 표시되는 텍스트로 option 을 선택합니다.
        * value : value 값으로 option 을 선택합니다.
    * examples
    ```
    "Select index 0",
    "Select text 짜장면",
    "Select value jjajang",
    ```
* Echo
    * 주어진 파라미터 문자열을 System.out.println 으로 콘솔에 출력합니다.
    * examples
    ```
    "Echo test",
    ```
* Frame
    * frame 을 전환하는 명령어입니다.
    * iFrame 등에 속해 있는 엘리먼트는 Find 로 찾을 수 없습니다.
    * 따라서 iframe 을 Find 로 찾은 다음, Frame 으로 frame 을 전환하면, Find 로 원하는 엘리먼트를 찾을 수 있습니다.
    * examples : 다음 예제는 [w3schools 의 select 연습장](http://www.w3schools.com/tags/tryit.asp?filename=tryhtml_select) 에서 iframe 안쪽의 select 엘리먼트를 선택합니다.
    ```
    "Find cssSelector #iframeResult",
    "Frame",
    "Find cssSelector body > select",
    "Select text Audi",
    ```
* Maximize
    * 웹 브라우저를 위아래로 최대화합니다.
    * examples
    ```
    "Maximize",
    ```
* Mouseover
    * Find, Cfind 로 찾은 엘리먼트 위에 마우스 커서를 올려놓습니다.
    * examples
    ```
    "Find id gnb-beverage",
    "Mouseover",
    ```
* Printscreen
    * 현재 웹 브라우저의 스크린샷을 찍습니다.
    * 스크린샷은 png 형식으로, 설정 파일의 pics 에서 지정된 path 에 저장됩니다.
    * examples
    ```
    "Printscreen",
    ```
* Wait
    * 주어진 시간만큼 아무것도 하지 않고 대기합니다.
    * 숫자 파라미터를 받으며, 단위는 천분의 1 초(ms) 입니다.
    * examples
    ```
    "Echo 다음 명령어는 3초를 기다린다는 의미입니다.",
    "Wait 3000",
    "Echo 다음 명령어는 1분을 기다린다는 의미입니다.",
    "Wait 60000",
    ```
* Sendkeys
    * 엘리먼트에 키 입력을 보냅니다. 주로 input 상자나 textarea 등에 입력을 보낼 때 씁니다.
    * examples : 다음 예제는 입력 칸에 이메일 정보를 입력합니다.
    ```
    "Find cssSelector #member_email",
    "Sendkeys johngrib82@gamil.com",
    ```
* SendKey
    * Sendkeys 는 문자열 입력을 보내는 데에는 유용하지만 ENTER, TAB, BACKSPACE 등의 특수한 키를 보낼 수는 없습니다.
    * Sendkey 는 Sendkeys 와는 달리 하나의 키만 입력합니다.
    * 다음 파라미터들을 사용할 수 있습니다.
        * ADD, ALT, ARROW_DOWN, ARROW_LEFT, ARROW_RIGHT, ARROW_UP, BACK_SPACE, CANCEL, CLEAR, COMMAND, CONTROL, DECIMAL, DELETE, DIVIDE, DOWN, END, ENTER, EQUALS, ESCAPE, F1, F10, F11, F12, F2, F3, F4, F5, F6, F7, F8, F9, HELP, HOME, INSERT, LEFT, LEFT_ALT, LEFT_CONTROL, LEFT_SHIFT, META, MULTIPLY, NULL, NUMPAD0, NUMPAD1, NUMPAD2, NUMPAD3, NUMPAD4, NUMPAD5, NUMPAD6, NUMPAD7, NUMPAD8, NUMPAD9, PAGE_DOWN, PAGE_UP, PAUSE, RETURN, RIGHT, SEMICOLON, SEPARATOR, SHIFT, SPACE, SUBTRACT, TAB, UP, ZENKAKU_HANKAKU
    * examples : 다음 예제는 입력 칸에 이메일 정보를 입력하고 엔터 키를 입력합니다.
    ```
    "Find id email",
    "Sendkeys johngrib82@gamil",
    "Sendkey ENTER",
    ```
* Forward
    * 웹 브라우저의 '앞으로' 명령을 입력합니다.
    ```
    "Forward",
    ```
* Back
    * 웹 브라우저의 '뒤로' 명령을 입력합니다.
    ```
    "Back",
    ```
* Refresh
    * 웹 브라우저의 '새로고침' 명령을 입력합니다.
    ```
    "Refresh",
    ```

#### Options
* --source, -s
    * 설정 파일의 path 를 지정합니다.
    * 예 : ./ruberdriver.json
* --async, -a
    * 이 옵션을 주면 지정된 모든 시나리오 수 만큼 웹 브라우저를 열어 동시에 시나리오를 실행합니다.
    * 이 옵션이 없으면 지정된 시나리오들을 순서대로 실행합니다.
* --interactive, -i
    * 시나리오를 구성하는 아이템의 각 명령어가 한 줄씩 실행될 때마다 사용자 키 입력을 기다립니다.
    * ENTER 키를 입력할 때마다 다음 명령어가 실행됩니다.
    * end 를 입력하고 ENTER 키를 입력하면 interactive 모드를 종료합니다. 나머지 명령어는 자동으로 실행됩니다.
    * quit 를 입력하고 ENTER 키를 입력하면 실행 중인 프로그램을 종료할 수 있습니다.
* --all, -l
    * 모든 시나리오를 실행하는 옵션입니다.
* --syntax
    * 지정된 시나리오를 구성하는 명령어들의 syntax 체크를 합니다.
    * 이 모드는 웹 브라우저를 구동하지 않고 syntax 체크만 합니다.
* --version, -v
    * 프로그램의 버전을 확인합니다.
* --debug, -d
    * 이 옵션을 주면 에러가 발생했을 경우, stacktrace 가 출력됩니다.
* --autoclose, --autoquit, -q
    * 모든 시나리오가 끝까지 실행되면, ruberdriver 가 open 한 모든 웹 브라우저를 종료합니다.
    * 시나리오 종료 후 수동 조작이 필요하다면 이 옵션을 주지 않도록 합니다.

#### 실행 예제
* 'go_test' 테스트 시나리오 실행
```
echo ./ruberdriver.json 파일을 설정 파일로 설정하고, go_test 시나리오를 실행한다.
java -jar ruberdriver.jar --source ./ruberdriver.json --scenario go_test
java -jar ruberdriver.jar -s ./ruberdriver.json -c go_test
```
```
echo ./ruberdriver.json 은 디폴트 값이기 때문에 생략해줘도 된다.
java -jar ruberdriver.jar -c go_test
```
* 여러 개의 테스트 시나리오 실행
```
echo go_test 를 실행하고, get_heart 를 실행한다.
java -jar ruberdriver.jar --source ./ruberdriver.json --scenario go_test,get_heart
java -jar ruberdriver.jar -c go_test,github_test
```
```
echo go_test 를 2번 실행하고, get_heart 를 3번 실행한다.
java -jar ruberdriver.jar -c go_test:2,github_test:3
```
```
echo go_test 를 2개 실행, get_heart 를 3개 실행한다. (5 개의 브라우저가 실행된다)
java -jar ruberdriver.jar -c go_test:2,github_test:3 -a
```
* 모든 테스트 시나리오 실행
```
java -jar ruberdriver.jar --all
java -jar ruberdriver.jar -l
```
* 모든 테스트 시나리오 실행 (동시에 여러 개의 웹 브라우저를 열고 실행)
```
java -jar ruberdriver.jar --all --async
java -jar ruberdriver.jar -l -a
```
* 모든 테스트 시나리오의 syntax 검사를 수행한다.
```
java -jar ruberdriver.jar --all --syntax
java -jar ruberdriver.jar -l -syntax
```
* 테스트 시나리오를 한 단계씩 진행한다. (사용자가 엔터 키를 입력할 때마다 진행)
```
java -jar ruberdriver.jar --scenario go_test --interactive
java -jar ruberdriver.jar -c go_test -i
```

#### 로그 파일
* ruberdriver 를 실행하면 세 종류의 로그 파일이 생성되어 쌓입니다.
1. ruberdriver 로그 파일
    * 파일 이름 형식은 `ruberdriver--년-월-일--시:분:초.밀리초.log` 입니다.
    * 예 : `ruberdriver--2016-07-24--23:24:11.447.log`
    * 아래 예제와 같이 설정 파일의 위치, 실행시 부여된 옵션 목록, 실행한 시나리오의 성공 여부 등을 기록합니다.
```
2016-07-24 23:23:38.185 [INFO] SOURCE JSON : ./ruberdriver.json
2016-07-24 23:23:38.200 [INFO] EXE OPTIONS : -c select_test --syntax
2016-07-24 23:23:38.200 [INFO] INITIATED RUBERDRIVER
2016-07-24 23:23:38.233 [INFO] SUCCESS 0007/0007 [select_test]--2016-07-24--23:23:38.206--1232367853
2016-07-24 23:23:38.234 [INFO] CLOSING RUBERDRIVER...
```

2. 시나리오별 로그 파일
    * 파일 이름 형식은 `[시나리오이름]--년-월-일--시:분:초.밀리초--scenarioRunner해시값.log` 입니다.
    * 예 : `[login_test]--2016-07-24--16:28:52.505--1846992172.log`
    * 아래 예제와 같이 설정 파일의 위치, 실행시 부여된 옵션 목록, 시나리오 진행 사항, 성공 여부 등을 기록합니다.
```
2016-07-24 23:23:38.208 [INFO] SOURCE JSON : ./ruberdriver.json
2016-07-24 23:23:38.209 [INFO] EXE OPTIONS : -c select_test --syntax
2016-07-24 23:23:38.209 [INFO] STARTED [select_test]--2016-07-24--23:23:38.206--1232367853
2016-07-24 23:23:38.210 [INFO] 0001/0007 Open chrome
2016-07-24 23:23:38.216 [INFO] 0002/0007 Wait 1000
2016-07-24 23:23:38.219 [INFO] 0003/0007 Goto http://www.w3schools.com/tags/tryit.asp?filename=tryhtml_select
2016-07-24 23:23:38.222 [INFO] 0004/0007 Find cssSelector #iframeResult
2016-07-24 23:23:38.228 [INFO] 0005/0007 Frame
2016-07-24 23:23:38.229 [INFO] 0006/0007 Find cssSelector body > select
2016-07-24 23:23:38.230 [INFO] 0007/0007 Select text Audi
2016-07-24 23:23:38.233 [INFO] SUCCESS 0007/0007 [select_test]--2016-07-24--23:23:38.206--1232367853
2016-07-24 23:23:38.235 [INFO] SUCCESS 0007/0007 [select_test]--2016-07-24--23:23:38.206--1232367853
2016-07-24 23:23:38.235 [INFO] CLOSED WebDriver : [select_test]--2016-07-24--23:23:38.206--1232367853
```
3. 스크린샷 그림 파일
    * 파일 이름 형식은 `[시나리오이름]--년-월-일--시:분:초.밀리초--scenarioRunner해시값--실행한명령어숫자:전체명령어숫자.png` 입니다.
    * 예 : `[get_heart]--2016-07-24--21:43:27.218--914513703--0016:0017.png`
    * 예제 파일 이름은 다음을 의미합니다. `2016년 7월 24일 21시 42분 27초에 시작한 get_heart 시나리오의 16번째 명령어를 수행하여 생성된 파일`

## 빌드하는 방법

#### Requirements
* Java 1.8
* Maven

#### Build
```
git clone https://github.com/johngrib/ruberdriver.git

cd ruberdriver

mvn package
```

