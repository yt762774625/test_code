package com.example.distributedapp;

import com.example.distributedapp.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.IAbilityConnection;
import ohos.aafwk.ability.IAbilityContinuation;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
import ohos.bundle.IBundleManager;

public class MainAbility extends Ability implements IAbilityContinuation {

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());

        //动态申请权限
        if(verifySelfPermission("ohos.permission.DISTRIBUTED_DATASYNC") != IBundleManager.PERMISSION_GRANTED){
            //应用未被授权
            if(canRequestPermission("ohos.permission.DISTRIBUTED_DATASYNC")){
                requestPermissionsFromUser(
                        new String[]{"ohos.permission.DISTRIBUTED_DATASYNC"},10
                );
            }
        }
    }

    @Override
    public boolean onStartContinuation() {
        return true;
    }

    @Override
    public boolean onSaveData(IntentParams intentParams) {
        return true;
    }

    @Override
    public boolean onRestoreData(IntentParams intentParams) {
        return true;
    }

    @Override
    public void onCompleteContinuation(int i) {

    }
}
