package com.yt.hmstudy.commoneventtest.slice;

import com.yt.hmstudy.commoneventtest.ResourceTable;
import com.yt.hmstudy.commoneventtest.Utils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.event.commonevent.*;
import ohos.global.resource.Resource;
import ohos.rpc.RemoteException;
import ohos.system.OsHelperErrnoException;

import java.security.interfaces.RSAKey;

public class MainAbilitySlice extends AbilitySlice {
    //公共事件订阅者
    private CommonEventSubscriber subscriber;
    private CommonEventSubscriber customCommonEventSubscriber;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        /**
         *发布普通公共事件
         */
        Button btn1 = (Button)findComponentById(ResourceTable.Id_btn1);
        btn1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                try{
                    //公共事件Intent对象，定义了公共事件字符串
                    Intent intent = new Intent();
                    Operation operation = new Intent.OperationBuilder()
                            .withAction("com.yt.hmstudy.commoneventtest.NormalCommonEvent")
                            .build();
                    intent.setOperation(operation);
                    //创建CommonEventData对象
                    CommonEventData eventData = new CommonEventData(intent);
                    //发布公共事件
                    CommonEventManager.publishCommonEvent(eventData);
                }catch (RemoteException e){
                    Utils.log("发布普通公共事件失败："+e.getLocalizedMessage());
                }
            }
        });

        /**
         * 发布有序公共事件
         * */
        Button btn2 = (Button)findComponentById(ResourceTable.Id_btn2);
        btn2.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                try{
                    //公共事件Intent对象，定义公共事件字符串
                    Intent intent = new Intent();
                    Operation operation = new Intent.OperationBuilder()
                            .withAction("com.yt.hmstudy.commoneventtest.OrderedCommonEvent")
                            .build();
                    intent.setOperation(operation);
                    //创建CommonEventData对象
                    CommonEventData eventData = new CommonEventData(intent);
                    eventData.setCode(0);       //设置结果码
                    eventData.setData("初始数据");      //设置结果数据
                    //创建CommonEventPublishInfo对象
                    CommonEventPublishInfo info = new CommonEventPublishInfo();
                    info.setOrdered(true);          //定义了有序公共事件
                    //发布公共事件
                    CommonEventManager.publishCommonEvent(eventData);
                }catch (RemoteException e){
                    Utils.log("发布有序公共事件失败："+e.getLocalizedMessage());
                }
            }
        });

        /**
         * 发布粘性公共事件
         * */
        Button btn3 = (Button)findComponentById(ResourceTable.Id_btn3);
        btn3.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                try{
                    //公共事件Intent对象，定义公共事件字符串
                    Intent intent = new Intent();
                    Operation operation = new Intent.OperationBuilder()
                            .withAction("com.yt.hmstudy.commoneventtest.StickyCommonEvent")
                            .build();
                    intent.setOperation(operation);
                    //创建CommonEventData对象
                    CommonEventData eventData = new CommonEventData(intent);
                    //创建CommonEventPublishInfo对象
                    CommonEventPublishInfo info = new CommonEventPublishInfo();
                    info.setSticky(true);       //定义粘性公共事件
                    //发布公共事件
                    CommonEventManager.publishCommonEvent(eventData,info);
                }catch (RemoteException e){
                    Utils.log("发布粘性公共事件失败："+e.getLocalizedMessage());
                }
            }
        });


        /**
         * 发布开始充电公共事件
         * */
        /*
        //通过MatchingSkills定义需要订阅的事件
        MatchingSkills matchingSkills = new MatchingSkills();
        //订阅充电状态改变事件
        matchingSkills.addEvent(CommonEventSupport.COMMON_EVENT_CHARGING);
        //通过CommonEventSubscribeInfo对象设置订阅参数
        CommonEventSubscribeInfo subscribeInfo = new CommonEventSubscribeInfo(matchingSkills);
        //实例化订阅者
        subscriber = new CommonEventSubscriber(subscribeInfo) {
            @Override
            public void onReceiveEvent(CommonEventData commonEventData) {
                Utils.showToast(MainAbilitySlice.this,"开始充电！");
            }
        };
        //通过CommonEventManager开始订阅
        try {
            CommonEventManager.subscribeCommonEvent(subscriber);
        } catch (RemoteException e) {
            Utils.log("订阅失败："+e.getLocalizedMessage());
        }
        */

        /**
         * 订阅公共事件
         * */
        MatchingSkills skills = new MatchingSkills();
        //订阅普通公共事件
        skills.addEvent("com.yt.hmstudy.commoneventtest.NormalCommonEvent");
        //订阅有序公共事件
        skills.addEvent("com.yt.hmstudy.commoneventtest.OrderedCommonEvent");
        //订阅粘性公共事件
        skills.addEvent("com.yt.hmstudy.commoneventtest.StickyCommonEvent");
        //实例化自定义公共事件订阅者
        customCommonEventSubscriber = new CommonEventSubscriber(
                new CommonEventSubscribeInfo(skills)) {
            @Override
            public void onReceiveEvent(CommonEventData commonEventData) {
                //公共事件字符串
                String action = commonEventData.getIntent().getAction();
                if(action == "com.yt.hmstudy.commoneventtest.NormalCommonEvent"){
                    Utils.showToast(MainAbilitySlice.this,"接收普通公共事件");
                }
                if(action == "com.yt.hmstudy.commoneventtest.OrderedCommonEvent"){
                    Utils.showToast(MainAbilitySlice.this,
                            "接收有序公共事件。结果码："+ commonEventData.getData()
                            +"结果数据："+commonEventData.getData());
                }
                if(action == "com.yt.hmstudy.commoneventtest.StickyCommonEvent"){
                    Utils.showToast(MainAbilitySlice.this,
                            "接收粘性公共事件");
                }
            }
        };

        /**
         * 订阅自定义公共事件
         * */
        Button btn4 = (Button)findComponentById(ResourceTable.Id_btn4);
        btn4.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                try {
                    CommonEventManager.subscribeCommonEvent(customCommonEventSubscriber);
                } catch (RemoteException e) {
                    Utils.log("订阅失败："+e.getLocalizedMessage());
                }
            }
        });

        /**
         * 取消订阅自定义公共事件
         * */
        Button btn5 = (Button)findComponentById(ResourceTable.Id_btn5);
        btn5.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                try {
                    CommonEventManager.unsubscribeCommonEvent(customCommonEventSubscriber);
                } catch (RemoteException e) {
                    Utils.log("退订失败："+e.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            CommonEventManager.unsubscribeCommonEvent(subscriber);
        } catch (RemoteException e) {
            Utils.log("退订失败："+e.getLocalizedMessage());
        }
    }
}

