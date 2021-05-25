- 初始化

```
HttpConfig.instance.host("http://channel.xtcodev.cn/")
```

- 创建api server

```
lateinit var api: TestApi
if (!this::api.isInitialized) {
            api = RetrofitFactory.defaultCreateApi(TestApi::class.java)
}
```

- 请求

这里使用的携程，请求方式可自定义，比如用传统的rxJava等，已默认依赖rxJava2

```
 Request.call({ 
            api.login(LoginRequest()) 
        }, {
            loadStatus.showLoading()
        }, {
            loadStatus.hideLoading()
        }, {
            loadStatus.error(it)
        })
```

- 线程切换

```
 // 切换到主线程
 this.ktxRunOnUi {
    // todo
 }

 // 延迟delayMillis后切换到主线程
 this.ktxRunOnUiDelay {
    // todo
 }

 // 子线程执行。SingleThreadPool
 this.ktxRunOnBgSingle {
    // todo
 }

 // 子线程执行。FixedThreadPool
 this.ktxRunOnBgFix {
    // todo
 }

 // 子线程执行。CachedThreadPool
 this.ktxRunOnBgCache {
    // todo
 }

```