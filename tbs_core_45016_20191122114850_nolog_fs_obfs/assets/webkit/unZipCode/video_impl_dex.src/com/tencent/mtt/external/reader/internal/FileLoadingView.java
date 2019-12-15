package com.tencent.mtt.external.reader.internal;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.common.utils.StringUtils;
import com.tencent.tbs.common.resources.TBSResources;

public class FileLoadingView
{
  FrameLayout jdField_a_of_type_AndroidWidgetFrameLayout = null;
  TextView jdField_a_of_type_AndroidWidgetTextView = null;
  TBSLoadingView jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView = null;
  protected RelativeLayout mContentView = null;
  protected Context mContext = null;
  public int mDownloadProgress = 0;
  public int mDownloadedSize = 0;
  public float mPlugTotalSize = 1.0F;
  
  public FileLoadingView(Context paramContext, FrameLayout paramFrameLayout)
  {
    this.mContext = paramContext;
    this.jdField_a_of_type_AndroidWidgetFrameLayout = paramFrameLayout;
    this.mContentView = new RelativeLayout(paramContext);
    paramFrameLayout = createLoading();
    paramFrameLayout.setId(1);
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-1, -2);
    localLayoutParams.addRule(13);
    this.mContentView.addView(paramFrameLayout, localLayoutParams);
    localLayoutParams = new RelativeLayout.LayoutParams(-1, -2);
    localLayoutParams.addRule(3, paramFrameLayout.getId());
    this.jdField_a_of_type_AndroidWidgetTextView = new TextView(paramContext);
    this.jdField_a_of_type_AndroidWidgetTextView.setTextColor(TBSResources.getColor("reader_theme_common_color_a4"));
    this.jdField_a_of_type_AndroidWidgetTextView.setTextSize(0, TBSResources.getDimensionPixelSize("reader_common_fontsize_t1"));
    this.jdField_a_of_type_AndroidWidgetTextView.setText("");
    this.jdField_a_of_type_AndroidWidgetTextView.setGravity(17);
    this.mContentView.addView(this.jdField_a_of_type_AndroidWidgetTextView, localLayoutParams);
    paramContext = new FrameLayout.LayoutParams(-1, -1);
    paramContext.gravity = 17;
    this.jdField_a_of_type_AndroidWidgetFrameLayout.addView(this.mContentView, paramContext);
    this.mContentView.setBackgroundColor(TBSResources.getColor("reader_theme_func_content_bkg_normal"));
  }
  
  public void bringToFront()
  {
    this.mContentView.bringToFront();
  }
  
  public void clearAndRemoveFromParent()
  {
    Object localObject = this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView;
    if (localObject != null) {
      ((TBSLoadingView)localObject).removeAllViews();
    }
    localObject = this.mContentView;
    if ((localObject != null) && (((RelativeLayout)localObject).getParent() != null)) {
      this.jdField_a_of_type_AndroidWidgetFrameLayout.removeView(this.mContentView);
    }
    this.mContext = null;
  }
  
  protected View createLoading()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView = new TBSLoadingView(this.mContext, false, true);
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView.setTextColor(TBSResources.getColor("reader_file_loading_txt_bg"));
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView.setProgressTextColor(TBSResources.getColor("reader_file_loading_txt_bg"));
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView.setLoadingDrawable(TBSResources.getDrawable("reader_loading_fg_normal_gray"));
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView.setFontSize(TBSResources.getDimensionPixelSize("reader_dr_loading_text_size"));
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView.setText(TBSResources.getString("reader_progress_tip"));
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView.setSpaceHeight(TBSResources.getDimensionPixelSize("reader_control_loading_default") * 4);
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView.startLoading();
    return this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView;
  }
  
  public void setCallbackListener(MttFileLoadingView.FileLoadingCallback paramFileLoadingCallback)
  {
    TBSLoadingView localTBSLoadingView = this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView;
    if (localTBSLoadingView != null) {
      localTBSLoadingView.setCallbackListener(paramFileLoadingCallback);
    }
  }
  
  public void setCancelable(boolean paramBoolean)
  {
    TBSLoadingView localTBSLoadingView = this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView;
    if (localTBSLoadingView != null) {
      localTBSLoadingView.setCancelable(paramBoolean);
    }
  }
  
  public void setCurrentSize(int paramInt)
  {
    double d = paramInt * this.mPlugTotalSize;
    Double.isNaN(d);
    this.mDownloadedSize = ((int)(d / 100.0D));
    String str1 = StringUtils.getSizeString(this.mDownloadedSize);
    String str2 = StringUtils.getSizeString(this.mPlugTotalSize);
    TextView localTextView = this.jdField_a_of_type_AndroidWidgetTextView;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(str1);
    localStringBuilder.append("/");
    localStringBuilder.append(str2);
    localTextView.setText(localStringBuilder.toString());
    this.mDownloadProgress = paramInt;
    setProgress(this.mDownloadProgress);
  }
  
  public void setDetailProgress(String paramString)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView.setDetailProgress(paramString);
  }
  
  public void setInitialText()
  {
    this.jdField_a_of_type_AndroidWidgetTextView.setText("--/--");
  }
  
  public void setProgress(int paramInt)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView.setProgress(paramInt);
  }
  
  public void setSizeShow(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.jdField_a_of_type_AndroidWidgetTextView.setVisibility(0);
      return;
    }
    this.jdField_a_of_type_AndroidWidgetTextView.setVisibility(8);
  }
  
  public void setText(String paramString)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView.setText(paramString);
  }
  
  public void setTotalSize(int paramInt)
  {
    this.mPlugTotalSize = paramInt;
    TextView localTextView = this.jdField_a_of_type_AndroidWidgetTextView;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(StringUtils.getSizeString(this.mDownloadedSize));
    localStringBuilder.append("/");
    localStringBuilder.append(StringUtils.getSizeString(this.mPlugTotalSize));
    localTextView.setText(localStringBuilder.toString());
  }
  
  public void startLoading()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView.startLoading();
  }
  
  public void stopLoading()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView.stopLoading();
    clearAndRemoveFromParent();
  }
  
  public void switchSkin()
  {
    this.mContentView.setBackgroundColor(TBSResources.getColor("reader_theme_func_content_bkg_normal"));
    TBSLoadingView localTBSLoadingView = this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView;
    if (localTBSLoadingView != null)
    {
      localTBSLoadingView.setTextColor(TBSResources.getColor("reader_file_loading_txt_bg"));
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView.setProgressTextColor(TBSResources.getColor("reader_file_loading_txt_bg"));
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView.setLoadingDrawable(TBSResources.getDrawable("reader_loading_fg_normal"));
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\FileLoadingView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */