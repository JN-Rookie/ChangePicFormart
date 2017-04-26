package com.example.sjsm.changepicformart;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.take)
    Button mTake;
    @Bind(R.id.check)
    Button mCheck;
    @Bind(R.id.hide)
    Button mHide;
    @Bind(R.id.gv_list)
    GridView mGvList;
    private Context mContext;
    private Uri imgUri;
    private List<Bitmap> Flist;
    private GridViewAdapter mAdapter;
    private String filePath = Environment.getExternalStorageDirectory() + "/sjsm/" + System.currentTimeMillis() + ".sjsm";
    ;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        ButterKnife.bind(this);
        Flist = new ArrayList<>();

    }

    @OnClick({R.id.take, R.id.check, R.id.hide})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.take:
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(MainActivity.this, new String[]{
                        Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionsResultAction() {
                    @Override
                    public void onGranted() {
                        File file = new File(filePath);
                        if (!file.exists()) {
                            try {
                                file.getParentFile().mkdirs();
                                file.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        imgUri = Uri.fromFile(new File(filePath));
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    }

                    @Override
                    public void onDenied(String permission) {

                    }
                });
                break;
            case R.id.check:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_FROM_FILE);
                break;
            case R.id.hide:
                File file = new File(Environment.getExternalStorageDirectory() + "/sjsm");
                File[] files = file.listFiles();
                for (File file1 : files) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file1.getAbsolutePath());
                    Flist.add(bitmap);
                }
                mAdapter = new GridViewAdapter(mContext, Flist);
                mGvList.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();


                break;
        }
    }


}
