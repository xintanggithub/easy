### view常用UI组件库/工具

#### SubmitLoading加载框工具

可以查阅 [参考文档](https://tson.top/#/blogDetail?md5=bookIdSOMdM8gVHbH5Y2xHHAmwFv3eB8nLg6r5X3cG) 的实现方式和使用方法

#### BaseAdapter

可以查阅 [参考文档](https://tson.top/#/blogDetail?md5=bookId1lIHR5oMvNxTm8oO7rQdUSPlXFtVQ5jwd1tq) 的实现方式和使用方法

#### 状态栏工具

可以查阅 [参考文档](https://tson.top/#/blogDetail?md5=bookId9x9nYzf7tLzr8MmU71mzIl4bTUgOOCrrY4my) 的实现方式和使用方法

#### NoScrollViewPager(可以设置是否能左右滑动的viewpager)

- 使用方式（xml）
```
<com.tson.view.viewpager.NoScrollViewPager
    android:id="@+id/viewpager"
    ...   >
</com.tson.view.viewpager.NoScrollViewPager>
```

- 设置是否可滑动
```
viewpager.setNoScroll(true  or  false)
```

#### BitmapUtils工具类

- drawable转bitmap
```
fun drawableToBitmap(drawable: Drawable): Bitmap
```

- 资源图片转bitmap
```
fun decodeSampledBitmapFromResource(res: Resources,resId: Int,reqWidth: Int,reqHeight: Int): Bitmap
```

#### RecyclerViewHelper辅助类

- 打开默认局部刷新动画
```
fun openDefaultAnimator(rv: RecyclerView)
```

- 关闭默认局部刷新动画
```
fun closeDefaultAnimator(rv: RecyclerView)
```

#### ScrollSpeedUtils滚动速度控制工具
-  velocity - 滑动速度，默认800
```
fun setMaxFlingVelocity(recyclerView: RecyclerView, velocity: Int)
```