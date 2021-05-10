
## utils download

##### 下载模块

### 0. 如何引入

#### 0.1 根目录下的build.gradle添加如下代码

```
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

```
    implementation "com.tson.utils.lib.download:lib:1.0.8"
```

### 1. 使用

#### 1.1 在application的onCreate中初始化，以及配置

```
                            //初始化
    DownLoadManager.instance.init(application).connectService(object : ConnectServiceCallback {
                    override fun connect() {
                        DownLoadManager.instance.run {
                             //开启debug日志，默认false
                            setDebugLog(true)
                            //错误之后重试次数3次
                            setRetryCount(3)
                            //更新到UI的频率，单位ms
                            setGlobalPost2UIInterval(60)
                            //并发任务设置为最大3个
                            setMaxThreadCount(3)
                            //设置下载路径(对应路径确保有读写权限)
                            setPath(this@DownloadActivity.filesDir.path + "/download/")
                            //设置HttpClient
                            creatorOkHttpClientBuilder(OkHttpClient.Builder())
                        }
                    }

                    override fun disConnect() {
                    }
                })
                           
```

- 做之后的任何操作前，必须先初始化

- 如果启动速度不理想，可以将以下代码放置子线程中(虽然这里的耗时非常少)

- 开启debug日志建议放在第一个，因为设置之后才会输出日志，放在后面设置可能会造成日志缺少，默认false，关闭日志

- 更新到UI的频率，建议60ms，如果更新频率过高（此处设置更新频率的值过低），可能会造成掉帧、卡顿的现象

- 设置下载前请确保有读写权限，如果不设置，下载路径则为以下之一

    1. Environment.getDownloadCacheDirectory().getAbsolutePath()
    
    2. getAppContext().getExternalCacheDir().getAbsolutePath()

- 并发设置范围为【1 - 12】 ，建议值：1 ~ 3

- 错误之后重试次数建议 2 ~ 5

- 设置HttpClient 可以做SSL证书验证处理、超时、日志打印、header设置等操作

#### 1.2 绑定下载监听

```
//绑定，一般在activity的onCreate （view初始化的地方）
DownLoadManager.bindChangeListener(downloadListener);
//解绑，一般是activity的onDestroy (销毁的)
DownLoadManager.unBindChangeListener(downloadListener);


//监听器
DownloadListener downloadListener=new DownloadListener() {
    @Override
    public void completed(BaseDownloadTask task) {
        //下载完成  
    }
};
```

其中completed为必须实现的回调方法，还有其他的下载状态变化回调方法可根据需求使用:

```
    //已准备好，等待下载，此时已经在下载队列   soFarBytes 已下载的byte数，totalBytes总大小
    void pending(task:BaseDownloadTask , soFarBytes:int , totalBytes:int );
    //下载进度
    void progress(task:BaseDownloadTask , soFarBytes:int , totalBytes:int );
    //暂停
    void paused(task:BaseDownloadTask , soFarBytes:int , totalBytes:int );
    //错误
    void error(task:BaseDownloadTask , e:Throwable);
    //下载完成
    void completed(task:BaseDownloadTask);
    //存在开始了一个在下载队列里已存在的任务(默认继续下载，不创建新的下载任务)
    void warn(task:BaseDownloadTask);
    //重试  retryingTimes 重试次数  ex异常信息  soFarBytes 已下载大小
    void retry(task:BaseDownloadTask, ex:Throwable, retryingTimes:int , soFarBytes:int );
```

BaseDownloadTask里包含了当前下载任务的信息，包括：下载任务的ID、下载的url，保存在本地的路径等等。
下载任务的ID是下载任务的标识，可以根据ID查询下载状态、大小等。


#### 1.3 下载任务的操作

```

//开始（创建/继续）
DownLoadManager.instance.start(url);
//如果没有设置监听器，这里也可以设置
DownLoadManager.instance.start(url,listener);

//暂停 id为BaseDownloadTask返回的任务ID
DownLoadManager.instance.pause(id);
//暂停所有
DownLoadManager.instance.pauseAll();

//清除单条下载任务数据 id为BaseDownloadTask返回的任务ID
//path为该文件本地存储的路径
DownLoadManager.instance.clear(id,path);
//清除所有下载任务数据
DownLoadManager.instance.clearAll();

//根据ID获取下载进度
DownLoadManager.instance.getSoFar(id);
//根据ID获取下载任务对象总大小
DownLoadManager.instance.getTotal(id);

```