## Android 6.0 的权限申请示例 ##

本例演示了如果判断及申请拨打电话的权限。

同时发现了一个有趣的现象，调用相机是不需要申请权限的，无法哪个版本都是可以正常调用的，但如果在AndroidManifest.xml中声明了相机权限，就必须做权限判断，否则在6.0以上的机型运行就会crash

![](screenshot.png)