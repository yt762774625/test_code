package com.example.distributedapp.slice;

import com.example.distributedapp.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.IAbilityContinuation;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.distributedschedule.interwork.DeviceInfo;
import ohos.distributedschedule.interwork.DeviceManager;

import java.util.ArrayList;
import java.util.List;

public class MainAbilitySlice extends AbilitySlice implements IAbilityContinuation {
    //迁移时传递的数据对象
    private int index = 0;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Button btn = findComponentById(ResourceTable.Id_button_continue);
        btn.setText("迁移（当前数据："+index+ ")");
        btn.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                //获取组网内在线设备列表
                List<String> deviceIds = getAvailableDeviceIds();
                if(deviceIds != null){
                    //迁移到组网内设备列表中的第1个设备上
                    continueAbility(deviceIds.get(0));
                }
            }
        });
    }

    /**
     *获取所有已经连接的设备ID
     */
    public static List<String> getAvailableDeviceIds(){
        //获取DeviceInfo列表，包含已经连接的所有设备信息
        List<DeviceInfo> deviceInfoList = DeviceManager.getDeviceList(
                DeviceInfo.FLAG_GET_ONLINE_DEVICE);

        //如果DeviceInfo列表为空则返回
        if(deviceInfoList == null || deviceInfoList.size() == 0){
            return null;
        }
        //遍历DeviceInfo列表，获取所有的设备ID
        List<String> deviceIds = new ArrayList<>();
        for(DeviceInfo deviceInfo:deviceInfoList){
            deviceIds.add(deviceInfo.getDeviceId());
        }
        //返回所有的设备ID
        return deviceIds;
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
    public boolean onStartContinuation() {
        return true;
    }

    @Override
    public boolean onSaveData(IntentParams intentParams) {
        //传递数据
        intentParams.setParam("index",index);
        return true;
    }

    @Override
    public boolean onRestoreData(IntentParams intentParams) {
        //接收数据
        index = (int)intentParams.getParam("index") + 1;
        return true;
    }

    @Override
    public void onCompleteContinuation(int i) {
        //迁移完成后关闭原设备上的MainAbility
        terminateAbility();
    }
}
