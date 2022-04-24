package com.example.serviceability;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.LocalRemoteObject;
import ohos.aafwk.content.Intent;
import ohos.rpc.IRemoteObject;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class TestService extends Ability {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0x00001, "TestService");

    //创建自定义IRemoteObject实现类
    public class MyRemoteObject extends LocalRemoteObject{
        public MyRemoteObject() {
            HiLog.info(LABEL_LOG,"MyRemoteObject被创建");
        }

        public void manipulateService(){
            HiLog.info(LABEL_LOG,"自定义方法，用于控制Service");
        }
    }

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        HiLog.info(LABEL_LOG, "onStart");
    }

    @Override
    public void onBackground() {
        super.onBackground();
        HiLog.info(LABEL_LOG, "onBackground");
    }

    @Override
    public void onStop() {
        super.onStop();
        HiLog.info(LABEL_LOG, "onStop");
    }

    @Override
    public void onCommand(Intent intent, boolean restart, int startId) {
        HiLog.info(LABEL_LOG, "onCommand");
    }

    @Override
    public IRemoteObject onConnect(Intent intent) {
        HiLog.info(LABEL_LOG, "onConnect");
        //返回远程对象MyRemoteObject
        return new MyRemoteObject();
    }

    @Override
    public void onDisconnect(Intent intent) {
        HiLog.info(LABEL_LOG, "onDisconnect");
    }
}