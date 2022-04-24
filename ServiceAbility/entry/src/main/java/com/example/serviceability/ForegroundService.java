package com.example.serviceability;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.event.notification.NotificationConstant;
import ohos.event.notification.NotificationRequest;
import ohos.rpc.IRemoteObject;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class ForegroundService extends Ability {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "Demo");

    @Override
    public void onStart(Intent intent) {
        //普通文本通知内容
        NotificationRequest.NotificationNormalContent content = new NotificationRequest.NotificationNormalContent()
                .setTitle("测试应用")
                .setText("该Service会常驻后台");
        //创建NotificationContent通知内容对象
        NotificationRequest.NotificationContent notificationContent = new NotificationRequest.NotificationContent(content);
        //创建NotificationRequest通知请求对象
        NotificationRequest request = new NotificationRequest(1001)
                .setContent(notificationContent);
        //绑定通知，1005为创建通知时传入的notificationId
        keepBackgroundRunning(1001,request);
        HiLog.error(LABEL_LOG, "ForegroundService::onStart");
        super.onStart(intent);
    }

    @Override
    public void onBackground() {
        super.onBackground();
        HiLog.info(LABEL_LOG, "ForegroundService::onBackground");
    }

    @Override
    public void onStop() {
        super.onStop();
        HiLog.info(LABEL_LOG, "ForegroundService::onStop");
    }

    @Override
    public void onCommand(Intent intent, boolean restart, int startId) {
    }

    @Override
    public IRemoteObject onConnect(Intent intent) {
        return null;
    }

    @Override
    public void onDisconnect(Intent intent) {
    }
}