package com.tencent.mtt.external.reader.internal;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.external.reader.base.ReaderContentView;
import com.tencent.tbs.common.resources.TBSResources;

final class MttReaderContentView
  extends ReaderContentView
{
  Context jdField_a_of_type_AndroidContentContext = null;
  FileReaderContentView jdField_a_of_type_ComTencentMttExternalReaderInternalFileReaderContentView = null;
  int e = 0;
  int f = 0;
  int g = 0;
  int h = 0;
  
  public MttReaderContentView(Context paramContext)
  {
    super(paramContext);
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    paramContext = (Activity)this.jdField_a_of_type_AndroidContentContext;
    this.e = paramContext.getWindowManager().getDefaultDisplay().getHeight();
    this.f = paramContext.getWindowManager().getDefaultDisplay().getWidth();
  }
  
  public int create()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileReaderContentView = new FileReaderContentView(this.jdField_a_of_type_AndroidContentContext);
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileReaderContentView.setBackgroundColor(-7829368);
    return 0;
  }
  
  public void destroy()
  {
    FileReaderContentView localFileReaderContentView = this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileReaderContentView;
    if (localFileReaderContentView != null) {
      localFileReaderContentView.removeAllViews();
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileReaderContentView = null;
    this.jdField_a_of_type_AndroidContentContext = null;
  }
  
  public int getContentButtomMargin()
  {
    return TBSResources.getDimensionPixelSize("reader_content_text_bottommargin");
  }
  
  public int getContentHeight()
  {
    int i = this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileReaderContentView.getHeight();
    int j = this.g;
    if (j > 0) {
      i = j;
    } else if (i <= 0) {
      i = this.e;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("height=");
    localStringBuilder.append(i);
    localStringBuilder.append(",mScreenHeight=");
    localStringBuilder.append(this.e);
    LogUtils.d("MttReaderContentView", localStringBuilder.toString());
    return i;
  }
  
  public int getContentLeftMargin()
  {
    return TBSResources.getDimensionPixelSize("reader_content_text_leftmargin");
  }
  
  public int getContentRightMargin()
  {
    return TBSResources.getDimensionPixelSize("reader_content_text_rightmargin");
  }
  
  public int getContentTopMargin()
  {
    return TBSResources.getDimensionPixelSize("reader_content_text_topmargin");
  }
  
  public int getContentWidth()
  {
    int i = this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileReaderContentView.getWidth();
    int j = this.h;
    if (j > 0) {
      i = j;
    } else if (i <= 0) {
      i = this.f;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("width=");
    localStringBuilder.append(i);
    localStringBuilder.append(",mScreenWidth=");
    localStringBuilder.append(this.f);
    LogUtils.d("MttReaderContentView", localStringBuilder.toString());
    return i;
  }
  
  public FrameLayout getFrameLayout()
  {
    return this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileReaderContentView;
  }
  
  public void setContentHeight(int paramInt)
  {
    if (paramInt > 0) {
      this.g = paramInt;
    }
  }
  
  public void setContentWidth(int paramInt)
  {
    if (paramInt > 0) {
      this.h = paramInt;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\MttReaderContentView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */