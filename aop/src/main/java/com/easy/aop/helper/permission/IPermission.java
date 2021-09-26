package com.easy.aop.helper.permission;

import java.util.List;

public interface IPermission {
    //同意权限
    void permissionGranted();

    //拒绝权限并且选中不再提示
    void permissionNoAskDenied(int requestCode, List<String> denyNoAskList);

    //取消权限
    void permissionDenied(int requestCode, List<String> denyList);
}
