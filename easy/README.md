这是一个基础架构，基于dataBinding，使用前还需要做简单封装，如果不需要使用全局loadingView和errorView的，只需要新建抽象类BaseActivity继承并空实现方法即可

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
    implementation "com.easy.assembly.base:lib:1.0.14"
```

### 1. Activity

####  1.1 基础版

新建BaseActivity

```kotlin
abstract class BaseActivity<T : ViewDataBinding, E : BaseViewModel>(modelClass: Class<E>) :
    EasyBaseActivity<T, E>(modelClass) {
    
    override fun initLoadingViewEnd() {
    }

    override fun defaultHideLoadingView() {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {

    }

    override fun error(error: Throwable) {
    }

    override fun retry() {
    }

}
```

- 新建MainViewModel

```kotlin
class MainViewModel : BaseViewModel() {
    var content = ObservableField("hello world！")
}
```

- 新建activity_main.xml

```kotlin
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <variable
            name="vm"
            type="com.XXXX.MainViewModel" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        tools:context="com.tson.easydemo.MainActivity">
        
        
        <TextView
            android:layout_width="match_parent"
            android:gravity="center"
            android:text="@{vm.content}"
            android:layout_height="match_parent"/>
            
    </LinearLayout>
```

- 新建MainActivity

里面的ActivityMainBinding在上面新建activity_main.xml之后就会自动生成
```kotlin
class MainActivity(override val layoutId: Int = R.layout.activity_main) :
    BaseActivity<ActivityMainBinding, MainViewModel>(MainViewModel::class.java) {

    override fun initView() {

    }

}
```

以上就是基础使用方法，MainViewModel中实现逻辑，activity_main.xml处理显示，MainActivity则对他们进行绑定。

#### 1.2 自定义公共loadingView和errorView(推荐，但不是唯一方式，参考demo中的LoadingViewModel类)

以下内容在上面的基础版之上添加即可

- 新增公共布局viewModel(LoadingViewModel)和xml(loading_layout.xml)

这里只是demo，内部逻辑可自定义

```kotlin
class LoadingViewModel : ViewModel() {

    var loadingMessage = ObservableField("")

    fun retry() {
        loadStatus.retry()
    }
}
```
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

        <variable
            name="loading"
            type="com.tson.easydemo.model.LoadingViewModel" />

        <import type="android.view.View" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@color/cardview_shadow_start_color"
        android:gravity="center"
        android:orientation="vertical">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{loading.loadingMessage}"
            android:textSize="22sp" />

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:onClick="@{()->loading.retry()}"
            android:text="重试"
            android:visibility="@{loading.loadingMessage.contains(`错误`)?View.VISIBLE:View.GONE}" />
    </LinearLayout>
</layout>
```
- BaseActivity处理公共布局的加载逻辑，注意注释
```kotlin
abstract class BaseActivity<T : ViewDataBinding, E : BaseViewModel>(modelClass: Class<E>) :
    EasyBaseActivity<T, E>(modelClass) {

    // loadingView布局binding
    lateinit var loadingBinding: LoadingLayoutBinding

    lateinit var loadingViewModel: LoadingViewModel

    // 设定loadingView布局，也可以在子类实现，如果在子类实现，以下binding和viewModel的操作也同步在子类处理
    override fun requestLoadingViewId(): Int = R.layout.loading_layout

    override fun defaultHideLoadingView() {
        super.defaultHideLoadingView()
        // 默认的公共布局处理，一般做loadingView默认显示或隐藏，触发时机在initView方法之前
        hideRoot()
    }

    private fun showRoot() {
        loadingBinding.root.visibility = View.VISIBLE
    }

    private fun hideRoot() {
        loadingBinding.root.visibility = View.GONE
    }

    /**
     * loadingView绑定到contentView成功后触发，触发时机在 [defaultHideLoadingView] 之前
     */
    override fun initLoadingViewEnd() {
        // dataBinding绑定view
        loadingBinding = DataBindingUtil.bind(loadingView)!!
        // 获取ViewModel
        loadingViewModel = getViewModel(LoadingViewModel::class.java)
        // 绑定ViewModel
        loadingBinding.loading = loadingViewModel
        // 关联页面业务ViewModel和公共loadingView和errorView的核心代码，由接口实现
        viewModel.loadStatus = this
        loadingViewModel.loadStatus = this
    }

    override fun error(error: Throwable) {
        // 显示错误信息
        showRoot()
        loadingViewModel.loadingMessage.set("❌错误信息：${error.message}")
    }

    override fun hideLoading() {
        // 隐藏loading 或 error
        loadingViewModel.loadingMessage.set("")
        hideRoot()
    }

    override fun retry() {
        //  重试
        hideRoot()
        Toast.makeText(this, "handle  retry ", Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        // 显示loading
        showRoot()
        loadingViewModel.loadingMessage.set("")
        loadingViewModel.loadingMessage.set("现在正在加载中...")
    }

}
```

以上便完成了公共view的实现，下面看看在activity的业务viewModel里面怎么使用的

```kotlin
class MainViewModel : BaseViewModel() {

    var content = ObservableField("hello world！")

    fun userLoadingViewMethod() {
        loadStatus.showLoading() // 显示
        loadStatus.hideLoading() // 隐藏
        loadStatus.error(Exception("error message")) //错误
        loadStatus.retry() //重试
    }
}
```

BaseFragment同理，只是更改一下继承为EasyBaseFragment即可，所以baseActivity和BaseFragment中的公共loadingView也可以抽出来，给baseActivity和BaseFragment共用。



#### 1.3  不注入布局ID的使用方式

- BaseActivity添加如下配置：

```kotlin
    override val layoutId: Int = -1 // 无布局

    override fun bindModelType(): Int {
        return Limit.METHOD // 通过方法反射进行初始化和model绑定
    }

    override fun viewModelType(): Int {
        return Limit.PROJECT// viewModel生命周期跟随当前工程类，PUBLIC代表周期全局（跟随主进程）
    }
```

- 子类继承时，可不传ID

```kotlin
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(MainViewModel::class.java) {
        
}
```



end