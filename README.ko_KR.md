# ruberdriver

## Other languages
* [ENGLISH](README.md)

## 사용법

* [chromedriver](https://sites.google.com/a/chromium.org/chromedriver/downloads) 를 다운로드해야 크롬을 사용할 수 있습니다.
* ruberdriver.json 를 편집합니다. (참고 [sample file](https://github.com/johngrib/ruberdriver/blob/master/ruberdriver.json)) 
    * 다운로드 받은 chromedriver 경로를 명시해줍니다. ([example](https://github.com/johngrib/ruberdriver/blob/master/ruberdriver.json#L2))
    * item 을 추가합니다.
    * scenario 를 추가합니다. item 을 배열 형태로 나열해주면 됩니다.
* ruberdriver.jar 를 실행합니다. 다양한 옵션을 줄 수 있습니다.

#### 실행 예제
* 'go_test' 테스트 시나리오 실행
```
java -jar ruberdriver.jar --source ./ruberdriver.json --scenario go_test
java -jar ruberdriver.jar -s ./ruberdriver.json -c go_test
java -jar ruberdriver.jar -c go_test
```
* 여러 개의 테스트 시나리오 실행
```
java -jar ruberdriver.jar --source ./ruberdriver.json --scenario go_test,github_test
java -jar ruberdriver.jar -c go_test,github_test
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

## 빌드하는 방법

직접 빌드해서 사용하려면 다음과 같이 하면 됩니다.

#### Requirements
* Java 1.8
* Maven

#### Build
```
git clone https://github.com/johngrib/ruberdriver.git

cd ruberdriver

mvn package
```

