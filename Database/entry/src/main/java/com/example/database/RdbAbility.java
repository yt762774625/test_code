package com.example.database;

import com.example.database.slice.RdbAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class RdbAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(RdbAbilitySlice.class.getName());
    }
}
