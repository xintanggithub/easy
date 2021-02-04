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

- request

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
