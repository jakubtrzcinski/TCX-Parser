# TCX-Parser


## Installation

### Maven
```xml
<dependencies>
    <dependency>
        <groupId>pl.jakubtrzcinski</groupId>
        <artifactId>tcx-parser</artifactId>
        <version>1.0.1.RELEASE</version>
    </dependency>
</dependencies>
```

### Gradle
```groovy
dependencies {
    implementation 'pl.jakubtrzcinski:tcx-parser:1.0.1.RELEASE'
}
```

### Usage

```java
var parser = new TcxParser();
var tcx = parser.parseTCX(new FileInputStream("sample.tcx"));
```
