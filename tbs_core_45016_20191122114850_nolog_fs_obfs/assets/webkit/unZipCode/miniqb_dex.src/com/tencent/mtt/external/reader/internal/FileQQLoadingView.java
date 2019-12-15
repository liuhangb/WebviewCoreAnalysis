package com.tencent.mtt.external.reader.internal;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import com.tencent.tbs.common.resources.TBSResources;

public class FileQQLoadingView
  extends FileLoadingView
{
  long jdField_a_of_type_Long = 0L;
  Handler jdField_a_of_type_AndroidOsHandler = new Handler();
  MttFileLoading jdField_a_of_type_ComTencentMttExternalReaderInternalMttFileLoading;
  Runnable jdField_a_of_type_JavaLangRunnable = new Runnable()
  {
    public void run()
    {
      FileQQLoadingView.this.clearAndRemoveFromParent();
    }
  };
  long b = 0L;
  
  public FileQQLoadingView(Context paramContext, FrameLayout paramFrameLayout)
  {
    super(paramContext, paramFrameLayout);
  }
  
  public void clearAndRemoveFromParent()
  {
    if (Math.abs(System.currentTimeMillis() - this.jdField_a_of_type_Long) < this.b)
    {
      this.jdField_a_of_type_AndroidOsHandler.postDelayed(this.jdField_a_of_type_JavaLangRunnable, 100L);
      return;
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFileLoading.stopLoading();
    super.clearAndRemoveFromParent();
  }
  
  protected View createLoading()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFileLoading = new MttFileLoading(this.mContext);
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFileLoading.setText(TBSResources.getString("reader_progress_tip"));
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFileLoading.setTextColor(TBSResources.getColor("reader_file_loading_txt_bg"));
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFileLoading.setProgress(0);
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFileLoading.startLoading();
    return this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFileLoading;
  }
  
  public void setDetailProgress(String paramString)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFileLoading.setDetailProgress(paramString);
  }
  
  public void setProgress(int paramInt)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFileLoading.setProgress(paramInt);
  }
  
  public void setText(String paramString)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFileLoading.setText(paramString);
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFileLoading.layout();
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFileLoading.invalidate();
  }
  
  public void startLoading()
  {
    this.jdField_a_of_type_Long = System.currentTimeMillis();
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFileLoading.startLoading();
  }
  
  public void stopLoading()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFileLoading.stopLoading();
  }
  
  public void switchSkin()
  {
    MttFileLoading localMttFileLoading = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFileLoading;
    if (localMttFileLoading != null)
    {
      localMttFileLoading.setTextColor(TBSResources.getColor("reader_file_loading_txt_bg"));
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFileLoading.switchSkin();
    }
    super.switchSkin();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\FileQQLoadingView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */