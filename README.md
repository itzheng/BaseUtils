# BaseUtils
开发中一些基本工具，比如，log，json等等，后期还会加入一些平时积累的工具，比如文件操作，等等
 ```
# 使用说明：
## 第一步：
### 在 Project 的 build.gradle 文件中 添加仓库支持
```groovy
allprojects {
    repositories {
        
        maven { url 'https://jitpack.io' }
    }
} 
```
## 第二步：
### 在需要引用的项目的 build.gradle 添加依赖
[see javadoc](https://javadoc.jitpack.io/com/github/itzheng/BaseUtils/latest/javadoc/index.html)
[![](https://jitpack.io/v/itzheng/BaseUtils.svg)](https://jitpack.io/#itzheng/BaseUtils)
```groovy
dependencies {
        
       implementation 'com.github.itzheng:BaseUtils:0.0.1'
}
```

