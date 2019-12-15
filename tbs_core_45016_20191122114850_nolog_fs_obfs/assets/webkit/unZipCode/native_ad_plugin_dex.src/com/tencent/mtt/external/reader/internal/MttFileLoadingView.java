package com.tencent.mtt.external.reader.internal;

import android.content.Context;
import android.widget.FrameLayout;
import com.tencent.common.utils.StringUtils;
import com.tencent.mtt.external.reader.base.ReaderLoadingView;

public class MttFileLoadingView
  extends ReaderLoadingView
{
  FrameLayout jdField_a_of_type_AndroidWidgetFrameLayout = null;
  FileLoadingView jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadingView = null;
  private int e = 0;
  
  public MttFileLoadingView(Context paramContext, boolean paramBoolean)
  {
    this.jdField_a_of_type_AndroidWidgetFrameLayout = new FrameLayout(paramContext);
    if (paramBoolean)
    {
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadingView = new FileQQLoadingView(paramContext, this.jdField_a_of_type_AndroidWidgetFrameLayout);
      return;
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadingView = new FileLoadingView(paramContext, this.jdField_a_of_type_AndroidWidgetFrameLayout);
  }
  
  public void destroy()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadingView.clearAndRemoveFromParent();
    this.jdField_a_of_type_AndroidWidgetFrameLayout.removeAllViews();
  }
  
  public FrameLayout getFrameLayout()
  {
    return this.jdField_a_of_type_AndroidWidgetFrameLayout;
  }
  
  public void setCallbackListener(FileLoadingCallback paramFileLoadingCallback)
  {
    FileLoadingView localFileLoadingView = this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadingView;
    if (localFileLoadingView != null) {
      localFileLoadingView.setCallbackListener(paramFileLoadingCallback);
    }
  }
  
  public void setCancelable(boolean paramBoolean)
  {
    FileLoadingView localFileLoadingView = this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadingView;
    if (localFileLoadingView != null) {
      localFileLoadingView.setCancelable(paramBoolean);
    }
  }
  
  public void setDetailProgress(int paramInt)
  {
    if (-1 == paramInt)
    {
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadingView.setDetailProgress("");
      return;
    }
    String str = "--/--";
    int i = this.e;
    if (i > 0)
    {
      double d = i * paramInt;
      Double.isNaN(d);
      str = String.format("%s/%s", new Object[] { StringUtils.getSizeString((int)(d * 0.01D)), StringUtils.getSizeString(this.e) });
    }
    FileLoadingView localFileLoadingView = this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadingView;
    if (localFileLoadingView != null) {
      localFileLoadingView.setDetailProgress(str);
    }
  }
  
  public void setProgress(int paramInt)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadingView.setProgress(paramInt);
  }
  
  public void setText(String paramString)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadingView.setText(paramString);
  }
  
  public void setTotalSize(int paramInt)
  {
    this.e = paramInt;
  }
  
  public void switchSkin()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadingView.switchSkin();
  }
  
  public static abstract interface FileLoadingCallback
  {
    public abstract void handleLoadingCancel();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\MttFileLoadingView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */