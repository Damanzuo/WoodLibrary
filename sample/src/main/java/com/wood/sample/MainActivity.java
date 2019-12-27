package com.wood.sample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.wood.library.view.AutoBannerView;
import com.wood.sample.bean.BannerInfo;
import com.wood.sample.constants.Constant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AutoBannerView<BannerInfo> mBannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBannerView = findViewById(R.id.auto_banner);
        List<BannerInfo> list = new ArrayList<>();
        for (int i = 0; i < Constant.bannerUrl.length; i++) {
            BannerInfo bannerInfo = new BannerInfo();
            bannerInfo.setImageUrl(Constant.bannerUrl[i]);
            bannerInfo.setTitle(Constant.bannerTitle[i]);
            list.add(bannerInfo);
        }
        mBannerView.setBannerData(list);

    }

    private void createDimensFile() {
        File sdcard = new File("sdcard", "dimens.xml");
        String start = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n";
        String end = "</resources>";
        try {
            FileOutputStream os = new FileOutputStream(sdcard);
            os.write(start.getBytes("UTF-8"));
            for (int i = 0; i < 1000; i++) {
                String format = String.format("<dimen name=\"dp%1$d\">%2$ddp</dimen>\n", i + 1, i + 1);
                os.write(format.getBytes("UTF-8"));
            }
            os.write(end.getBytes("UTF-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
