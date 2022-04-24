package com.example.serviceability.slice;

import com.example.serviceability.ForegroundService;
import com.example.serviceability.ResourceTable;
import com.example.serviceability.TestService;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.IAbilityConnection;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.bundle.ElementName;
import ohos.distributedschedule.interwork.DeviceManager;
import ohos.rpc.IRemoteObject;
import ohos.system.DeviceInfo;

import java.util.List;

public class MainAbilitySlice extends AbilitySlice {
    //连接远程的Service的IAbilityConnection对象
    private IAbilityConnection connection = new IAbilityConnection() {
        @Override
        public void onAbilityConnectDone(ElementName elementName, IRemoteObject iRemoteObject, int i) {
            //通过远程对象操纵Service
            TestService.MyRemoteObject object = (TestService.MyRemoteObject)iRemoteObject;
            object.manipulateService();
        }

        @Override
        public void onAbilityDisconnectDone(ElementName elementName, int i) {

        }
    };

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        /**
         * 启动服务
         * */
        Button btn1 = findComponentById(ResourceTable.Id_btn1);
        btn1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.serviceability")
                        .withAbilityName("com.example.serviceability.TestService")
                        .build();
                intent.setOperation(operation);
                startAbility(intent);
            }
        });

        /**
         * 停止服务
         * */
        Button btn2 = findComponentById(ResourceTable.Id_btn2);
        btn2.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.serviceability")
                        .withAbilityName("com.example.serviceability.TestService")
                        .build();
                intent.setOperation(operation);
                stopAbility(intent);
            }
        });

        /**
         * 连接服务
         * */
        Button btn3 = findComponentById(ResourceTable.Id_btn3);
        btn3.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                //TestService的intent对象
                Intent intent = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.serviceability")
                        .withAbilityName("com.example.serviceability.TestService")
                        .build();
                intent.setOperation(operation);
                //连接Service
                connectAbility(intent,connection);
            }
        });

        /**
         * 断开服务
         * */
        Button btn4 = findComponentById(ResourceTable.Id_btn4);
        btn4.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                disconnectAbility(connection);
            }
        });

        /**
         * 启动前台Service
         * */
        Button btn5 = findComponentById(ResourceTable.Id_btn5);
        btn5.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.serviceability")
                        .withAbilityName(ForegroundService.class.getName())
                        .build();
                intent.setOperation(operation);
                startAbility(intent);
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
}
