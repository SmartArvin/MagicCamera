package com.ktc.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ktc.camera.R;


public class DialogEffects extends Dialog implements View.OnClickListener {

    private Button btnCamera ;
    private RecyclerView mRecycler;
    private Button navFrame , navFilter , navSticker , navLabel;


    private LinearLayoutManager mLayoutManager;
    private DialogEffectAdapter mAdapter;
    private OnChangeListener mOnChangeListener;

    public interface OnChangeListener{
        //相框
        void onFrameChangedListener(int position);
        //滤镜
        void onFilterChangedListener(int position);
        //贴纸
        void onStickerChangedListener(int position);
        //标签
        void onLabelChangedListener(int position);
        //拍照
        void onTakePictureListener();
    }

    public void setOnChangeListener(OnChangeListener listener){
        this.mOnChangeListener = listener;
    }

    public DialogEffects(@NonNull Context context) {
        super(context, R.style.BottomDialog);

        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_filter,null);

        initView(contentView);
        setContentView(contentView);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.windowAnimations = R.style.BottomDialog_Animation;
        getWindow().setAttributes(layoutParams);
        getWindow().setDimAmount(0f);/*使用时设置窗口后面的暗淡量*/
    }

    private void initView(View contentView) {
        btnCamera = contentView.findViewById(R.id.btn_camera);
        mRecycler = contentView.findViewById(R.id.recycler);
        navFrame = contentView.findViewById(R.id.btn_frame);
        navFilter = contentView.findViewById(R.id.btn_filter);
        navSticker = contentView.findViewById(R.id.btn_sticker);
        navLabel = contentView.findViewById(R.id.btn_label);

        mLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false);
        mAdapter = new DialogEffectAdapter(getContext());
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);

        initListener();
    }

    private void initListener() {
        /*mAdapter.setOnItemClickListener(new DialogEffectAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                if (null != mOnChangeListener) {
                    mOnChangeListener.onFilterChangedListener(position);
                }
                dismiss();
            }
        });*/

        btnCamera.setOnClickListener(this);
        navFrame.setOnClickListener(this);
        navFilter.setOnClickListener(this);
        navSticker.setOnClickListener(this);
        navLabel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_camera:
                if (null != mOnChangeListener) {
                    mOnChangeListener.onTakePictureListener();
                }
                break;
            case R.id.btn_frame:
                break;
            case R.id.btn_filter:
                break;
            case R.id.btn_sticker:
                break;
            case R.id.btn_label:
                break;
        }
    }

}
