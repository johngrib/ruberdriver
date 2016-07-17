# ruberdriver

## Other languages
* [한국어](README.ko_KR.md)

## How to use

* download [chromedriver](https://sites.google.com/a/chromium.org/chromedriver/downloads)
* edit ruberdriver.json (see [sample file](https://github.com/johngrib/ruberdriver/blob/master/ruberdriver.json))
    * add chromedriver path ([example](https://github.com/johngrib/ruberdriver/blob/master/ruberdriver.json#L2))
    * add items
    * add scenario
* run ruberdriver.jar with options

#### Run examples
* run 'go_test' test scenario
```
java -jar ruberdriver.jar --source ./ruberdriver.json --scenario go_test
java -jar ruberdriver.jar -s ./ruberdriver.json -c go_test
java -jar ruberdriver.jar -c go_test
```
* run multiple test scenarios
```
java -jar ruberdriver.jar --source ./ruberdriver.json --scenario go_test,github_test
java -jar ruberdriver.jar -c go_test,github_test
```
* run all test scenarios
```
java -jar ruberdriver.jar --all
java -jar ruberdriver.jar -l
```
* run all test scenarios (asynchronous)
```
java -jar ruberdriver.jar --all --async
java -jar ruberdriver.jar -l -a
```

## How to build

#### Requirements
* Java 1.8
* Maven

#### Build
```
git clone https://github.com/johngrib/ruberdriver.git

cd ruberdriver

mvn package
```






// TODO : add logger
// TODO : syntax checker
