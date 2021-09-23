### 0. 如何引入

#### 0.1 根目录下的build.gradle添加如下代码

```groovy
allprojects {
    repositories {
        google()
        jcenter()
        //添加下面这个maven配置
        maven{
            //这是maven仓库地址
            url 'https://raw.githubusercontent.com/xintanggithub/maven/master'
        }
    }
}
```
#### 0.2 需要使用的module下build.gradle添加引用

```groovy
    implementation "com.easy.assembly.aop:lib:1.0.0"
```

#### 0.3 根目录的build.gradle添加配置
```groovy
        classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.4'
```

#### 0.4 在APP入口模块添加配置
- 入口模块：即配置了 `com.android.application`的module, 如app这个module的build.gradle：
```groovy
plugins {
    // 就是文件最顶部的这一行配置
    id 'com.android.application'
    id 'kotlin-android'
    id 'kotlin-kapt'
    id 'kotlin-android-extensions'
    id 'android-aspectjx'
}
.....其他配置.....
```

这个配置非常关键，不然会导致注解无效

