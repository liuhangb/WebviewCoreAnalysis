package com.tencent.mtt.external.reader.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.FrameLayout;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.external.reader.IReader;
import com.tencent.mtt.external.reader.IReaderCallbackListener;

public class ReaderCore
  implements IReaderCallbackListener
{
  Bundle jdField_a_of_type_AndroidOsBundle = null;
  ReaderDB jdField_a_of_type_ComTencentMttExternalReaderBaseReaderDB = null;
  ReaderTypeView jdField_a_of_type_ComTencentMttExternalReaderBaseReaderTypeView = null;
  String jdField_a_of_type_JavaLangString = "";
  protected String docPath;
  protected ReaderContentView mContentView = null;
  protected Context mContext = null;
  protected String mExt = null;
  protected IReader mReader = null;
  protected FrameLayout mRootView = null;
  
  public ReaderCore(Context paramContext, String paramString1, String paramString2, ReaderContentView paramReaderContentView)
  {
    this.mContext = paramContext;
    this.docPath = paramString1;
    this.mExt = paramString2;
    this.mContentView = paramReaderContentView;
    this.mRootView = this.mContentView.getFrameLayout();
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderDB = new ReaderDB();
  }
  
  ReaderTypeView.IViewCommand a()
  {
    new ReaderTypeView.IViewCommand()
    {
      public void doAction(int paramAnonymousInt, Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        if (ReaderCore.this.mReader != null) {
          ReaderCore.this.mReader.doAction(paramAnonymousInt, paramAnonymousObject1, paramAnonymousObject2);
        }
      }
    };
  }
  
  public void callbackAction(int paramInt, Object paramObject1, Object paramObject2)
  {
    ReaderTypeView localReaderTypeView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderTypeView;
    if (localReaderTypeView != null) {
      localReaderTypeView.postEvent(paramInt, paramObject1, paramObject2);
    }
  }
  
  protected void destoryMyReader()
  {
    final Object localObject = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderTypeView;
    if (localObject != null) {
      ((ReaderTypeView)localObject).destroy();
    }
    if (this.mReader != null) {
      try
      {
        localObject = new Bundle();
        this.mReader.doAction(8, null, localObject);
        if (this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderTypeView != null) {
          ((Bundle)localObject).putInt("Mode", this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderTypeView.mViewMode);
        }
        new Thread(new Runnable()
        {
          public void run()
          {
            if (ReaderCore.this.a != null) {
              ReaderCore.this.a.savePosition(ReaderCore.this.docPath, localObject);
            }
          }
        }).start();
        this.mReader.toFinish();
        this.mReader.setListener(null);
        this.mReader = null;
        this.mRootView = null;
        this.mContentView = null;
        this.mContext = null;
        return;
      }
      catch (NullPointerException localNullPointerException)
      {
        localNullPointerException.printStackTrace();
      }
    }
  }
  
  public void doAction(int paramInt, Object paramObject1, Object paramObject2)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("doAction,actionType=");
    ((StringBuilder)localObject).append(paramInt);
    LogUtils.d("MttReaderCore", ((StringBuilder)localObject).toString());
    localObject = this.mReader;
    if (localObject != null) {
      ((IReader)localObject).doAction(paramInt, paramObject1, paramObject2);
    }
  }
  
  public Bundle getReaderPack()
  {
    Bundle localBundle = new Bundle();
    IReader localIReader = this.mReader;
    if (localIReader != null)
    {
      localIReader.doAction(10001, null, localBundle);
      this.mReader.doAction(10000, null, localBundle);
    }
    return localBundle;
  }
  
  public ReaderTypeView getReaderView()
  {
    return this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderTypeView;
  }
  
  public boolean initReader(String paramString)
  {
    LogUtils.d("MttReaderCore", "initReader ...");
    if (this.mReader == null) {
      return false;
    }
    Bundle localBundle1 = new Bundle();
    try
    {
      String str = this.jdField_a_of_type_JavaLangString;
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderDB.jdField_a_of_type_JavaLangString = str;
      if (this.mReader != null)
      {
        if (this.mExt.equalsIgnoreCase("pdf"))
        {
          Bundle localBundle2 = new Bundle();
          localBundle2.putInt("mode", 2);
          this.mReader.doAction(10002, localBundle2, null);
        }
        this.mReader.setLibsPath(paramString, str);
        this.mReader.setActivity((Activity)this.mContext);
        this.mReader.setListener(this);
        this.mReader.setRootView(this.mRootView);
        localBundle1.putInt("left_margin", this.mContentView.getContentLeftMargin());
        localBundle1.putInt("top_margin", this.mContentView.getContentTopMargin());
        localBundle1.putInt("right_margin", this.mContentView.getContentRightMargin());
        localBundle1.putInt("bottom_margin", this.mContentView.getContentButtomMargin());
        localBundle1.putInt("view_height", this.mContentView.getContentHeight());
        localBundle1.putInt("view_width", this.mContentView.getContentWidth());
        this.mReader.doAction(3, localBundle1, null);
        if (this.jdField_a_of_type_AndroidOsBundle != null)
        {
          this.mReader.doAction(15, this.jdField_a_of_type_AndroidOsBundle, null);
          this.mReader.setNightMode(this.jdField_a_of_type_AndroidOsBundle.getBoolean("nightmode"));
        }
      }
      paramString = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderTypeView;
      if (paramString != null) {
        paramString.create();
      }
      return true;
    }
    catch (Throwable paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isDocumentTop()
  {
    IReader localIReader = this.mReader;
    if (localIReader != null) {
      return localIReader.isDocumentTop();
    }
    return true;
  }
  
  public void notifyScrollThumbRatio(float paramFloat)
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderTypeView != null)
    {
      Bundle localBundle = new Bundle();
      localBundle.putFloat("ratio", paramFloat);
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderTypeView.postEvent(3006, localBundle, null);
    }
  }
  
  public void onScaleBegin(float paramFloat)
  {
    ReaderTypeView localReaderTypeView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderTypeView;
    if (localReaderTypeView != null) {
      localReaderTypeView.postEvent(3004, null, null);
    }
  }
  
  public void onScaleEnd(float paramFloat)
  {
    ReaderTypeView localReaderTypeView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderTypeView;
    if (localReaderTypeView != null) {
      localReaderTypeView.postEvent(3005, null, null);
    }
  }
  
  public void onScroll(float paramFloat)
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderTypeView != null)
    {
      Bundle localBundle = new Bundle();
      localBundle.putFloat("ratio", paramFloat);
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderTypeView.postEvent(3003, localBundle, null);
    }
  }
  
  public void onScrollBegin(float paramFloat)
  {
    ReaderTypeView localReaderTypeView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderTypeView;
    if (localReaderTypeView != null) {
      localReaderTypeView.postEvent(3001, null, null);
    }
  }
  
  public void onScrollEnd(float paramFloat)
  {
    ReaderTypeView localReaderTypeView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderTypeView;
    if (localReaderTypeView != null) {
      localReaderTypeView.postEvent(3002, null, null);
    }
  }
  
  public void onSingleTap(int paramInt1, int paramInt2)
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderTypeView != null)
    {
      Bundle localBundle = new Bundle();
      localBundle.putInt("tap_x", paramInt1);
      localBundle.putInt("tap_y", paramInt2);
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderTypeView.postEvent(3000, localBundle, null);
    }
  }
  
  public void onSizeChanged(int paramInt1, int paramInt2)
  {
    if (this.mReader != null)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("onSizeChanged screenWidth=");
      ((StringBuilder)localObject).append(paramInt1);
      ((StringBuilder)localObject).append(" screenHeight=");
      ((StringBuilder)localObject).append(paramInt2);
      LogUtils.d("MttReaderCore", ((StringBuilder)localObject).toString());
      localObject = new Bundle();
      ((Bundle)localObject).putInt("left_margin", this.mContentView.getContentLeftMargin());
      ((Bundle)localObject).putInt("top_margin", this.mContentView.getContentTopMargin());
      ((Bundle)localObject).putInt("right_margin", this.mContentView.getContentRightMargin());
      ((Bundle)localObject).putInt("bottom_margin", this.mContentView.getContentButtomMargin());
      ((Bundle)localObject).putInt("view_height", paramInt2);
      ((Bundle)localObject).putInt("view_width", paramInt1);
      this.mReader.doAction(3, localObject, null);
    }
  }
  
  protected void openBook()
  {
    LogUtils.d("MttReaderCore", "MttFileReader openBook");
    Bundle localBundle = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderDB.getPosition(this.docPath);
    if (localBundle != null)
    {
      this.mReader.doAction(7, localBundle, null);
      ReaderTypeView localReaderTypeView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderTypeView;
      if (localReaderTypeView != null) {
        localReaderTypeView.mViewMode = localBundle.getInt("Mode", -1);
      }
    }
    this.mReader.openBook(this.docPath, this.mExt);
  }
  
  @Deprecated
  public void openBookFailed() {}
  
  public void setColor(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("fontColor", paramInt1);
    localBundle.putInt("tbColor", paramInt1);
    localBundle.putInt("bgColor", paramInt2);
    localBundle.putInt("selectColor", paramInt3);
    localBundle.putBoolean("nightmode", paramBoolean);
    IReader localIReader = this.mReader;
    if (localIReader != null)
    {
      localIReader.doAction(15, localBundle, null);
      this.mReader.setNightMode(paramBoolean);
      return;
    }
    this.jdField_a_of_type_AndroidOsBundle = localBundle;
  }
  
  public void setIReader(Object paramObject)
  {
    this.mReader = ((IReader)paramObject);
  }
  
  public void setReaderView(ReaderTypeView paramReaderTypeView)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderTypeView = paramReaderTypeView;
    if (paramReaderTypeView != null) {
      paramReaderTypeView.setViewCommand(a());
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderCore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */