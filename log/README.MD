日志统一处理工具

### 初始化

```
                    LogUtils.run {
                        setDebuggable("是否debug模式，true是，false否，debug模式的时候会输出全部日志，否则只输出error级别日志", context)
                        setVersionName("版本号，用于区分打印日志的APP版本")
                    }
```

### 使用

每个级别都提供了两种输出方式

```
// debug级别日志
LogUtils.d("msg")

LogUtils.d("TAG","msg")

// error级别日志
LogUtils.e("msg")

LogUtils.e("TAG","msg")

...

```

### 日志监听

如果需要对日志进行监听，比如error级别的日志直接上报，可以绑定如下接口

```
                    LogUtils.addListener(object: LogListener{
                        override fun log(lv: String, tag: String, content: String) {
                            // todo
                        }
                    })
```

### 本地日志

- 目录结构

```

          目录结构：


          ----- rootFile // XXXX PackageName / log
          --------- log
          -------------- 2019-03-13  (跟随日期变动)
          ------------------- 1  (按数量递增) // 每个文件最大10MB，超过10MB时，后面的日志写入新的日志文件

```

- 写入

```
                    LogUtils.writeTxtToFile("msg")
```

- 格式化文件大小

```
                    LogUtils.formatFileSize("byte size ，long类型")
```
