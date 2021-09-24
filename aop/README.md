
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
- 入口模块：即配置了 `com.android.application` 的 `module`, 如 `app` 这个 `module` 的 `build.gradle`：
```groovy
plugins {
    // 就是文件最顶部的这一行配置
    id 'com.android.application'
    id 'kotlin-android'
    id 'kotlin-kapt'
    id 'kotlin-android-extensions'
    id 'android-aspectjx'
}
..其他配置..
```

这个配置非常关键，不然会导致注解无效

### 1. 使用说明

#### 1.1 方法调用日志插桩
- 注解：`Stub`, 参数 `tag`、`content`
```kotlin
    @Stub(tag = "插桩tag", content = "插桩日志内容")
    fun testMt(name: String) {
        Log.d("StubAspect", " = !23 $name")
    }
```

#### 1.2 注解指定执行的线程
- 注解：`Run`, 参数 `type`
    - `type` 的值：`IO` `UI` `MAIN` `SINGLE` `FIXED` `CACHE`
```kotlin
    @Run(type = MAIN)
    fun testIO() {
        try {
            run1.text = "123123"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
```

#### 1.3 给方法加锁
- 注释：`Lock`, 参数：无
```kotlin
    @Lock
    fun lockUtils(value: String) {
        Thread.sleep(1000)//每个停一会儿
        Log.d("value = ", value)// 不会一次性的把 1 2 3全部输出，而是间隔1000ms左右逐个输出
    }
```
- 使用示例：
```kotlin
        lock1.setOnClickListener {
            ktxRunOnUiDelay(1000) {
                lockUtils("1")
            }
            ktxRunOnUiDelay(1000) {
                lockUtils("2")
            }
            ktxRunOnUiDelay(1000) {
                lockUtils("3")
            }
        }
```

- 日志输出（注意看 `1`、`2`、`3` 的执行时间是按顺序出来的）：
```java
2021-09-23 17:12:16.488 5091-5091/com.tson.easydemo D/value =: 1
2021-09-23 17:12:16.488 5091-5091/com.tson.easydemo W/LockAspect: proceed done, use time = 1002 ms
2021-09-23 17:12:17.491 5091-5091/com.tson.easydemo D/value =: 2
2021-09-23 17:12:17.492 5091-5091/com.tson.easydemo W/LockAspect: proceed done, use time = 1003 ms
2021-09-23 17:12:18.494 5091-5091/com.tson.easydemo D/value =: 3
2021-09-23 17:12:18.495 5091-5091/com.tson.easydemo W/LockAspect: proceed done, use time = 1001 ms
```

#### 1.4 万能注解
> 该注解，会告诉你什么时候是方法执行前、执行后，但是需要自己实现逻辑。
>
> 这种注解，适合自己写一些前置处理，比如：点击一个按钮前判断权限、点击一个按钮前判断登录状态，如果未登录怎么样，已登录又怎么样、执行某个方法后输出日志等等。

- 注解：`Auto`, 参数： `action` 事件的 `action`、 `parameter` 参数，类型为数组
```kotlin
    @Auto(action = "action1", parameter = [AutoParameter(key = "1", value = "2")])
    fun testAuto() {
        Log.d("auto", "auto doing")
    }
```
- 自定义方法执行前后逻辑：
```kotlin
        AopManager.instance.setAutoListener(object : AutoAction {
            override fun proceedBefore(action: String, map: MutableMap<String, String>, proceed: DoProceed) {
                Log.d("统一处理", "在方法执行前  action=$action")
                proceed.runMethod() // 这一句代码是指调用执行加了注解的方法
            }

            override fun proceedAfter(action: String, map: MutableMap<String, String>) {
                Log.d("统一处理", "在方法执行后  action=$action")
            }
        })
```

- 执行结果：
```java
2021-09-23 17:16:31.961 5091-5091/com.tson.easydemo W/testAuto: action = action1
2021-09-23 17:16:31.961 5091-5091/com.tson.easydemo W/testAuto: parameter = {1=2}
2021-09-23 17:16:31.961 5091-5091/com.tson.easydemo D/统一处理: 在方法执行前  action=action1
2021-09-23 17:16:31.961 5091-5091/com.tson.easydemo D/auto: auto doing
2021-09-23 17:16:31.962 5091-5091/com.tson.easydemo W/testAuto: proceed done, use time = 0
2021-09-23 17:16:31.962 5091-5091/com.tson.easydemo D/统一处理: 在方法执行后  action=action1
```


