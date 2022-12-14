package com.xinniu.android.qiqueqiao.richtexteditor;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.ImageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 可编辑富文本
 */
public class RichTextEditor extends ScrollView {
    private static final int EDIT_PADDING = 2; // edittext常规padding是10dp

    private int viewTagIndex = 1; // 新生的view都会打一个tag，对每个view来说，这个tag是唯一的。
    private LinearLayout allLayout; // 这个是所有子view的容器，scrollView内部的唯一一个ViewGroup
    private LayoutInflater inflater;
    private OnKeyListener keyListener; // 所有EditText的软键盘监听器
    private OnClickListener btnListener; // 图片右上角红叉按钮监听器
    private OnFocusChangeListener focusListener; // 所有EditText的焦点监听listener
    private EditText lastFocusEdit; // 最近被聚焦的EditText
    private LayoutTransition mTransitioner; // 只在图片View添加或remove时，触发transition动画
    private int editNormalPadding = 0; //
    private int disappearingImageIndex = 0;
    private Context context;

    public RichTextEditor(Context context) {
        this(context, null);
    }

    public RichTextEditor(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RichTextEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflater = LayoutInflater.from(context);
        this.context = context;
        // 1. 初始化allLayout
        allLayout = new LinearLayout(context);
        allLayout.setOrientation(LinearLayout.VERTICAL);
        //allLayout.setBackgroundColor(Color.WHITE);
        setupLayoutTransitions();
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        allLayout.setPadding(0, 14, 0, 14);//设置间距，防止生成图片时文字太靠边，不能用margin，否则有黑边
        addView(allLayout, layoutParams);

        // 2. 初始化键盘退格监听
        // 主要用来处理点击回删按钮时，view的一些列合并操作
        keyListener = new OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    EditText edit = (EditText) v;
                    onBackspacePress(edit);
                }
                return false;
            }
        };

        // 3. 图片叉掉处理
        btnListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                RelativeLayout parentView = (RelativeLayout) v.getParent();
                onImageCloseClick(parentView);
            }
        };

        focusListener = new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    lastFocusEdit = (EditText) v;
                }
            }
        };

        LinearLayout.LayoutParams firstEditParam = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //editNormalPadding = dip2px(EDIT_PADDING);
        EditText firstEdit = createEditText("请输入详情内容", dip2px(context, EDIT_PADDING));
        firstEdit.requestFocus();

        allLayout.addView(firstEdit, firstEditParam);
        lastFocusEdit = firstEdit;
    }

    /**
     * 初始化transition动画
     */
    private void setupLayoutTransitions() {
        mTransitioner = new LayoutTransition();
        allLayout.setLayoutTransition(mTransitioner);
        mTransitioner.addTransitionListener(new LayoutTransition.TransitionListener() {

            @Override
            public void startTransition(LayoutTransition transition,
                                        ViewGroup container, View view, int transitionType) {

            }

            @Override
            public void endTransition(LayoutTransition transition,
                                      ViewGroup container, View view, int transitionType) {
                if (!transition.isRunning()
                        && transitionType == LayoutTransition.CHANGE_DISAPPEARING) {
                    // transition动画结束，合并EditText
                    // mergeEditText();
                }
            }
        });
        mTransitioner.setDuration(300);
    }

    public int dip2px(Context context, float dipValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }

    /**
     * 处理软键盘backSpace回退事件
     *
     * @param editTxt 光标所在的文本输入框
     */
    private void onBackspacePress(EditText editTxt) {
        int startSelection = editTxt.getSelectionStart();
        // 只有在光标已经顶到文本输入框的最前方，在判定是否删除之前的图片，或两个View合并
        if (startSelection == 0) {
            int editIndex = allLayout.indexOfChild(editTxt);
            View preView = allLayout.getChildAt(editIndex - 1); // 如果editIndex-1<0,
            // 则返回的是null
            if (null != preView) {
                if (preView instanceof RelativeLayout) {
                    // 光标EditText的上一个view对应的是图片
                    onImageCloseClick(preView);
                } else if (preView instanceof EditText) {
                    // 光标EditText的上一个view对应的还是文本框EditText
                    String str1 = editTxt.getText().toString();
                    EditText preEdit = (EditText) preView;
                    String str2 = preEdit.getText().toString();

                    allLayout.removeView(editTxt);

                    // 文本合并
                    preEdit.setText(str2 + str1);
                    Log.i("shuju===", (str2 + str1));
                    preEdit.requestFocus();
                    preEdit.setSelection(str2.length(), str2.length());
                    lastFocusEdit = preEdit;
                }
            }
        }
    }

    /**
     * 处理图片叉掉的点击事件
     *
     * @param view 整个image对应的relativeLayout view
     * @type 删除类型 0代表backspace删除 1代表按红叉按钮删除
     */
    private void onImageCloseClick(View view) {
        disappearingImageIndex = allLayout.indexOfChild(view);
        //删除文件夹里的图片
        List<EditData> dataList = buildEditData();
        EditData editData = dataList.get(disappearingImageIndex);
        //Log.i("", "editData: "+editData);
//        if (editData.imagePath != null) {
//            SDCardUtil.deleteFile(editData.imagePath);
//        }
        onDeleteListener.onDelete(editData.imagePath);
        allLayout.removeView(view);
        int num = getNum();
        onDeleteListener.onNumChange(num);
    }

    public void clearAllLayout() {
        allLayout.removeAllViews();
    }

    public int getLastIndex() {
        int lastEditIndex = allLayout.getChildCount();
        return lastEditIndex;
    }

    /**
     * 生成文本输入框
     */
    public EditText createEditText(String hint, int paddingTop) {
        final EditText editText = (EditText) inflater.inflate(R.layout.rich_edittext, null);
        editText.setOnKeyListener(keyListener);
        editText.setTag(viewTagIndex++);
        editText.setPadding(editNormalPadding, paddingTop, editNormalPadding, paddingTop);
        editText.setHint(hint);
        editText.setOnFocusChangeListener(focusListener);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i("当前标识===", editText.getTag() + "");
                int num = getNum();
                onDeleteListener.onNumChange(num);
            }
        });
        return editText;
    }

    /**
     * 生成图片View
     */
    private RelativeLayout createImageLayout() {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(
                R.layout.edit_imageview, null);
        layout.setTag(viewTagIndex++);
        final View closeView = layout.findViewById(R.id.image_close);
        final DataImageView imageView = (DataImageView) layout.findViewById(R.id.edit_imageView);
        imageView.setBackground(null);
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setBackgroundResource(R.drawable.bg_image_frame);
                closeView.setVisibility(VISIBLE);
            }
        });
        closeView.setVisibility(GONE);
        closeView.setTag(layout.getTag());
        closeView.setOnClickListener(btnListener);
        return layout;
    }

    /**
     * 根据绝对路径添加view
     *
     * @param imagePath
     */
    public void insertImage(String imagePath, int width) {
        Bitmap bmp = getScaledBitmap(imagePath, width);
        insertImage(bmp, imagePath);
    }

    /**
     * 插入一张图片
     */
    public void insertImage(Bitmap bitmap, String imagePath) {
        String lastEditStr = lastFocusEdit.getText().toString();
        int cursorIndex = lastFocusEdit.getSelectionStart();
        String editStr1 = lastEditStr.substring(0, cursorIndex).trim();
        int lastEditIndex = allLayout.indexOfChild(lastFocusEdit);

        if (lastEditStr.length() == 0 || editStr1.length() == 0) {
            // 如果EditText为空，或者光标已经顶在了editText的最前面，则直接插入图片，并且EditText下移即可
            addImageViewAtIndex(lastEditIndex, imagePath);
        } else {
            // 如果EditText非空且光标不在最顶端，则需要添加新的imageView和EditText
            lastFocusEdit.setText(editStr1);
            String editStr2 = lastEditStr.substring(cursorIndex).trim();
            if (editStr2.length() == 0) {
                editStr2 = " ";
            }
            if (allLayout.getChildCount() - 1 == lastEditIndex) {
                addEditTextAtIndex(lastEditIndex + 1, editStr2);
            }

            addImageViewAtIndex(lastEditIndex + 1, imagePath);
            lastFocusEdit.requestFocus();
            lastFocusEdit.setSelection(editStr1.length(), editStr1.length());//TODO
        }
        hideKeyBoard();
    }

    /**
     * 隐藏小键盘
     */
    public void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(lastFocusEdit.getWindowToken(), 0);
    }

    /**
     * 在特定位置插入EditText
     *
     * @param index   位置
     * @param editStr EditText显示的文字
     */
    public void addEditTextAtIndex(final int index, CharSequence editStr) {
        EditText editText2 = createEditText("", dip2px(context, EDIT_PADDING));
        editText2.setText(editStr);
        editText2.requestFocus();
        editText2.setOnFocusChangeListener(focusListener);
        allLayout.addView(editText2, index);
    }

    /**
     * 在特定位置添加ImageView
     */
    public void addImageViewAtIndex(final int index, String imagePath) {
        final RelativeLayout imageLayout = createImageLayout();
        final DataImageView imageView = (DataImageView) imageLayout.findViewById(R.id.edit_imageView);
        //    final LinearLayout closeView1 = (LinearLayout) imageLayout.findViewById(R.id.rlayout_close);
        //   Glide.with(getContext()).load(imagePath).crossFade().centerCrop().into(imageView);
        imageView.setAbsolutePath(imagePath);//保留这句，后面保存数据会用
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);//裁剪剧中

        //        // 调整imageView的高度，根据宽度来调整高度
//        Bitmap bmp = BitmapFactory.decodeFile(imagePath);

        Glide.with(getContext())
                .asBitmap()
                .load(imagePath)

                .into(new SimpleTarget<Bitmap>() {


                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        //加载完成后的处理
                        imageView.setImageBitmap(bitmap);
                        int imageHeight = DensityUtil.dp2px(200);

                        if (bitmap != null) {
                            imageHeight = allLayout.getWidth() * bitmap.getHeight() / bitmap.getWidth();

                        }
                        // TODO: 17/3/1 调整图片高度，这里是否有必要，如果出现微博长图，可能会很难看
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                                LayoutParams.MATCH_PARENT, imageHeight);//设置图片固定高度
                        lp.bottomMargin = 10;
                        imageView.setLayoutParams(lp);
                    }
                });


        //   closeView1.setLayoutParams(lp);
        allLayout.addView(imageLayout, index);
        int num = getNum();
        onDeleteListener.onNumChange(num);
    }

    private int getNum() {
        List<RichTextEditor.EditData> editList = buildEditData();
        int num = 0;
        for (RichTextEditor.EditData itemData : editList) {
            if (itemData.inputStr != null && itemData.inputStr.length() > 0) {
                //   content.append(itemData.inputStr);
                String s = itemData.inputStr.replace("\n", "").replaceAll("\\s*", "");
                num = num + s.length();
                Log.d("RichEditor", "commit inputStr=" + itemData.inputStr.length());
            } else if (itemData.imagePath != null) {
                num = num + 50;
                Log.d("RichEditor", "commit imgePath=" + itemData.imagePath);

            }
        }
        return num;
    }

    /**
     * 根据view的宽度，动态缩放bitmap尺寸
     *
     * @param width view的宽度
     */
    public Bitmap getScaledBitmap(String filePath, int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int sampleSize = options.outWidth > width ? options.outWidth / width
                + 1 : 1;
        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 对外提供的接口, 生成编辑数据上传
     */
    public List<EditData> buildEditData() {
        List<EditData> dataList = new ArrayList<EditData>();
        int num = allLayout.getChildCount();
        for (int index = 0; index < num; index++) {
            View itemView = allLayout.getChildAt(index);
            EditData itemData = new EditData();
            if (itemView instanceof EditText) {
                EditText item = (EditText) itemView;
                itemData.inputStr = item.getText().toString();
            } else if (itemView instanceof RelativeLayout) {
                DataImageView item = (DataImageView) itemView.findViewById(R.id.edit_imageView);
                itemData.imagePath = item.getAbsolutePath();
            }
            dataList.add(itemData);
        }

        return dataList;
    }


    //获取最后一个edittext
    public String getFristEdit() {
        String text = "";
        int num = allLayout.getChildCount();
        for (int index = 0; index < num; index++) {
            View itemView = allLayout.getChildAt(index);
            if (itemView instanceof EditText) {
                EditText item = (EditText) itemView;
                text = item.getHint().toString();
            }
        }
        return text;
    }

    public class EditData {
        public String inputStr;
        public String imagePath;
    }

    public OnDeleteListener onDeleteListener;

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public static interface OnDeleteListener {
        public void onDelete(String url);

        public void onNumChange(int num);
    }


}
