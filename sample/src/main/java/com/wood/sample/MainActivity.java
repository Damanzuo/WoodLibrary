package com.wood.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.wood.amaplib.SearchUtils;
import com.wood.amaplib.interfaces.OnPoiSearchCallback;
import com.wood.databaselib.ChinaCityHelper;
import com.wood.databaselib.constants.DataBaseConst;
import com.wood.databaselib.entity.City;
import com.wood.databaselib.entity.Province;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //    private AutoBannerView<BannerInfo> mBannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdaptScreenUtils.adaptWidth(getResources(), 1080);
        AdaptScreenUtils.adaptHeight(getResources(), 1920, true);
        setContentView(R.layout.activity_main);
//        mBannerView = findViewById(R.id.auto_banner);
//        List<BannerInfo> list = new ArrayList<>();
//        for (int i = 0; i < Constant.bannerUrl.length; i++) {
//            BannerInfo bannerInfo = new BannerInfo();
//            bannerInfo.setImageUrl(Constant.bannerUrl[i]);
//            bannerInfo.setTitle(Constant.bannerTitle[i]);
//            list.add(bannerInfo);
//        }
//        mBannerView.setBannerData(list);
//
//        CommonDialog dialog = new CommonDialog(this);
//        dialog.show();
//        String pioType = "120000|120100|120200|120201|120202|120203|120300|120301|120302|120303|120304|170000|170100|170200|170201|170202|170203|170204|170205|170206|170207|170208|170209|170300|180000|180500|190000|190100|190105|190106|190107|190108|190109|190200|190203|190400|190401|190402|190403|190500|190600|190700";
//
//        SearchUtils.getInstance().search(this, "云浮", pioType, "", 100, 0, new OnPoiSearchCallback() {
//            @Override
//            public void onPoiSearched() {
//
//            }
//        });
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LogUtils.d(intent.getAction());
            }
        },new IntentFilter(DataBaseConst.ACTION_CITY_DATA_SUCCESS));
        ChinaCityHelper.getInstance().init(this,true);
//        List<Province> provinces = ChinaCityHelper.getInstance().loadAllProvince();
//        createDimensFile("provinces", GsonUtils.toJson(provinces));
//        createDimensFile("city", GsonUtils.toJson(ChinaCityHelper.getInstance().loadAllCity()));
//        createDimensFile("area", GsonUtils.toJson(ChinaCityHelper.getInstance().loadAllArea()));
//        createDimensFile("street",GsonUtils.toJson(ChinaCityHelper.getInstance().loadAllStreet()));

        List<Province> provinces = ChinaCityHelper.getInstance().loadAllProvince();
        LogUtils.d(provinces);
    }

    private void createDimensFile(String name,String json) {
        File sdcard = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), name+".json");
        try {
            FileOutputStream os = new FileOutputStream(sdcard);
            os.write(json.getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
