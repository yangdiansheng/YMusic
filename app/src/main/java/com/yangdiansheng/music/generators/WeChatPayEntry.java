package com.yangdiansheng.music.generators;

import com.yangdiansheng.lib_annotations.anntations.PayEntryGenerator;
import com.yangdiansheng.music.wechat.WxPayEntryTemplate;

@PayEntryGenerator(
        payPackageName="com.yangdiansheng.music",
        payEntryTemple= WxPayEntryTemplate.class
)
public interface WeChatPayEntry {
}
