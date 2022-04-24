package com.example.database.slice;

import com.example.database.RdbAbility;
import com.example.database.ResourceTable;
import com.example.database.Utils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.data.DatabaseHelper;
import ohos.data.rdb.RdbOpenCallback;
import ohos.data.rdb.RdbStore;
import ohos.data.rdb.StoreConfig;
import ohos.data.rdb.ValuesBucket;

import java.util.ArrayList;
import java.util.List;

public class RdbAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_rdb_slice);

        /**
         *插入数据
         */
        Button btn1 = findComponentById(ResourceTable.Id_btn1);
        btn1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                RdbStore store = getTestRdbStore();
                ValuesBucket values = new ValuesBucket();
                values.putString("name","杨涛");
                values.putInteger("age",32);
                values.putInteger("sex",1);
                values.putString("class","思中高三七班");
                long id = store.insert("student",values);
                if(id == -1){
                    Utils.showToast(RdbAbilitySlice.this,"数据插入失败");
                }else{
                    Utils.showToast(RdbAbilitySlice.this,"数据插入成功，Row ID："+id+"!");
                }
                //使用完毕后要关闭Store对象
                Utils.log("数据库路径是："+ getDatabaseDir());
                Utils.log("应用缓存路径为："+getExternalCacheDir().getAbsolutePath());
                store.close();
            }
        });

        /**
         *插入多条数据
         */
         Button btn2 = findComponentById(ResourceTable.Id_btn2);
         btn2.setClickedListener(new Component.ClickedListener() {
             @Override
             public void onClick(Component component) {
                RdbStore store = getTestRdbStore();
                List<ValuesBucket> students = new ArrayList<>();
                ValuesBucket student1 = new ValuesBucket();
                student1.putString("name","王娜");
                student1.putInteger("age",30);
                student1.putInteger("sex",0);
                student1.putString("class","鸿蒙基础学习班");
                students.add(student1);

                ValuesBucket student2 = new ValuesBucket();
                student2.putString("name","董沐辰松");
                student2.putInteger("age",20);
                student2.putInteger("sex",1);
                student2.putString("class","鸿蒙基础学习班");
                students.add(student2);

                List<Long> ids = store.batchInsertOrThrowException("student",students,
                        RdbStore.ConflictResolution.ON_CONFLICT_ABORT);
                String output = "";
                 for (Long id : ids) {
                     if(id == -1){
                         Utils.log("数据插入失败！");
                     }else{
                         Utils.log("数据插入成功，Row ID："+ id + "!");
                     }
                 }

                 //使用完毕后要关闭RdbStore对象
                 store.close();
             }
         });

    }


    /**
     *获取test.sqlite数据库的RdbStore对象
     * @return RdbStore对象
     */
    private RdbStore getTestRdbStore(){
        //实例化DatabaseHelper对象
        DatabaseHelper helper = new DatabaseHelper(this);
        //创建可读写的test.sqlite数据库
        StoreConfig config = StoreConfig.newDefaultConfig("test.sqlite");
        //获取RdbStore对象，版本号为1
        RdbStore store = helper.getRdbStore(config, 1, new RdbOpenCallback() {
            @Override
            public void onCreate(RdbStore rdbStore) {
                //在创建test.sqlite数据库时，创建student数据表
                rdbStore.executeSql("CREATE TABLE IF NOT EXISTS student(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "name TEXT NOT NULL,"+
                        "age INTEGER," +
                        "sex TINYINT," +
                        "class TEXT)");
            }

            @Override
            public void onUpgrade(RdbStore rdbStore, int i, int i1) {
                //更新数据时回调
            }
        });
        return store;
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
