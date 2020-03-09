package com.yangdiansheng.music.generators;

import com.yangdiansheng.lib_annotations.anntations.AppRegisterGenerator;
import com.yangdiansheng.music.wechat.WxPayEntryTemplate;

@AppRegisterGenerator(
        packageName="com.yangdiansheng.music",
        registerTemple = WxPayEntryTemplate.class
)
public interface AppRegister {
}
