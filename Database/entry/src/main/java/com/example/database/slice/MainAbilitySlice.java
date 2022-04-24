package com.example.database.slice;

import com.example.database.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.utils.Rect;

public class MainAbilitySlice extends AbilitySlice {
    private static String abilityName_1 = "com.example.database.RdbAbility";
    private static String bundleName = "com.example.database";

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        Button btn1 = findComponentById(ResourceTable.Id_btn1);
        addClick(btn1,bundleName,abilityName_1);


    }

    /**
     *添加单击事件的方法
     */
    private void addClick(Button button,String bundleName,String abilityName){
        button.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withAbilityName(abilityName)
                        .withBundleName(bundleName)
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
