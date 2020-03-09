package com.yangdiansheng.music.generators;

import com.yangdiansheng.lib_annotations.anntations.EntryGenerator;
import com.yangdiansheng.music.wechat.WxEntryTemplate;

@EntryGenerator(
        packageName="com.yangdiansheng.music",
        entryTemple= WxEntryTemplate.class
)
public interface WeChatEntry {
}
