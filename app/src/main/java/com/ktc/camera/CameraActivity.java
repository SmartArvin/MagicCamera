package com.ktc.camera;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ktc.camerafilter.ConstantFilters;
import com.ktc.camerafilter.ConstantFrames;
import com.ktc.camerafilter.callback.LoadAssetsImageCallback;
import com.ktc.camerafilter.listener.EndRecordingFilterCallback;
import com.ktc.camerafilter.listener.StartRecordingFilterCallback;
import com.ktc.camerafilter.listener.TakePhotoFilterCallback;
import com.ktc.dialog.DialogEffects;
import com.ktc.utils.Constant;
import com.ktc.utils.SPUtil;

import org.wysaid.myUtils.ImageUtil;
import org.wysaid.nativePort.CGENativeLibrary;
import org.wysaid.view.CameraRecordGLSurfaceView;

/**
 * 拍照&滤镜
 * @author Arvin
 * @date 2020.01.09
 */
public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = CameraActivity.class.getName();

    private CameraRecordGLSurfaceView mCameraView;
    private ImageView imgFrame;
    private RelativeLayout lyBottomControl;
    private Button btnAlbum , btnCamera , btnRecord;

    private DialogEffects mDialogEffects;
    private int mCurrentFrame;
    private String mCurrentFilter;
    private int mCurrentSticker;
    private int mCurrentLabel;
    /*拍照的回调*/
    private TakePhotoFilterCallback mTakePhotoFilterCallback;
    /*开始录制视频的回调*/
    private StartRecordingFilterCallback mStartRecordingFilterCallback;
    /*录制视频结束的回调*/
    private EndRecordingFilterCallback mEndRecordingFilterCallback;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_camera);

        CGENativeLibrary.setLoadImageCallback(new LoadAssetsImageCallback(this), null);

        initView();
        initDialogEffects();
    }

    private void initView() {
        mCameraView = findViewById(R.id.camera_view);
        imgFrame = findViewById(R.id.img_frame);

        lyBottomControl = findViewById(R.id.ly_bottom_control);
        btnAlbum = findViewById(R.id.btn_album);
        btnCamera = findViewById(R.id.btn_camera);
        btnRecord = findViewById(R.id.btn_record);

        btnAlbum.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
        btnRecord.setOnClickListener(this);


        /*设置摄像头方向*/
        mCameraView.presetCameraForward(false);
        /*录制视频大小*/
        mCameraView.presetRecordingSize(1920, 1080);
        /*拍照大小。*/
        mCameraView.setPictureSize(1920, 1080, false);
        /*充满view*/
        mCameraView.setFitFullView(true);

        /*设置图片保存的目录*/
        ImageUtil.setDefaultFolder("MagicCamera");

        mTakePhotoFilterCallback = new TakePhotoFilterCallback(this);
        mStartRecordingFilterCallback = new StartRecordingFilterCallback(this);
        mEndRecordingFilterCallback = new EndRecordingFilterCallback(this);

        //自动对焦
        mCameraView.cameraInstance().setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
    }

    private void initDialogEffects(){
        mDialogEffects = new DialogEffects(this);
        mDialogEffects.setOnChangeListener(new DialogEffects.OnChangeListener() {
            @Override
            public void onFrameChangedListener(int position) {
                mCurrentFrame = ConstantFrames.IMG_FRAMES[position];
                imgFrame.setBackgroundResource(mCurrentFrame);

                SPUtil.getInstance().putInt(Constant.SPKEY.KEY_FRAME , position);
            }

            @Override
            public void onFilterChangedListener(final int position) {
                mCameraView.setFilterWithConfig(ConstantFilters.FILTERS[position]);
                mCurrentFilter = ConstantFilters.FILTERS[position];

                SPUtil.getInstance().putInt(Constant.SPKEY.KEY_FILTER , position);
            }

            @Override
            public void onStickerChangedListener(int position) {
                //贴纸
                SPUtil.getInstance().putInt(Constant.SPKEY.KEY_STICKER , position);
            }

            @Override
            public void onLabelChangedListener(int position) {
                //标签
                SPUtil.getInstance().putInt(Constant.SPKEY.KEY_LABEL , position);
            }

            @Override
            public void onTakePictureListener() {
                //拍照
                if(mCameraView != null && mTakePhotoFilterCallback != null){
                    mCameraView.takePicture(mTakePhotoFilterCallback, null, mCurrentFilter, 1.0f, true);
                }
            }
        });

        mDialogEffects.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                lyBottomControl.animate().alpha(0).setDuration(1000).start();
            }
        });

        mDialogEffects.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                lyBottomControl.animate().alpha(1).setDuration(1000).start();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_album:
                //打开相册
                break;
            case R.id.btn_camera:
                if(mCameraView != null && mTakePhotoFilterCallback != null){
                    mCameraView.takePicture(mTakePhotoFilterCallback, null, mCurrentFilter, 1.0f, true);
                }
                break;
            case R.id.btn_record:
                if(mCameraView != null && mEndRecordingFilterCallback != null){
                    if(mCameraView.isRecording()){
                        mCameraView.endRecording(mEndRecordingFilterCallback);
                        //切换图标状态

                    }else{
                        String videoFileName = ImageUtil.getPath()+ "/" + System.currentTimeMillis()+".mp4";
                        mEndRecordingFilterCallback.setVideoFilePath(videoFileName);
                        mCameraView.startRecording(videoFileName , mStartRecordingFilterCallback);
                        //切换图标状态

                    }

                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN){
            //从底部退出效果选择窗口
            mDialogEffects.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mCameraView != null){
            mCameraView.resumePreview();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mCameraView != null){
            mCameraView.stopPreview();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCameraView != null){
            mCameraView.release(null);
            mCameraView.cameraInstance().stopCamera();
        }
    }

}
