package com.tencent.mtt.external.reader.base;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.external.reader.internal.ReaderFileStatistic;

public class ReaderCoreWrapper
{
  static int jdField_a_of_type_Int = 0;
  static int b = 0;
  static int c = 1;
  static int d = 2;
  View jdField_a_of_type_AndroidViewView = null;
  ReaderController jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController = null;
  ReaderCore jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCore = null;
  ReaderMessage.MessageEvent jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent = null;
  ReaderMessage jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage = new ReaderMessage();
  String jdField_a_of_type_JavaLangString = "";
  final int e = 100;
  final int f = 10000;
  int g = 0;
  int h = 0;
  int i = 0;
  
  public ReaderCoreWrapper(ReaderCore paramReaderCore, ReaderController paramReaderController)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCore = paramReaderCore;
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController = paramReaderController;
  }
  
  void a()
  {
    ReaderFileStatistic localReaderFileStatistic = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.a;
    try
    {
      a(10000);
      localReaderFileStatistic.openResult = 0;
      localReaderFileStatistic.initFileOpenTM();
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCore.openBook();
      this.g = 0;
      return;
    }
    catch (Error localError)
    {
      localError.printStackTrace();
      localReaderFileStatistic.doReportException("MttReader:openBook", localError);
      localReaderFileStatistic.openResult = 4;
      localReaderFileStatistic.errCode = 2000;
      localReaderFileStatistic.addToStatManager(false);
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.b(0);
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      localRuntimeException.printStackTrace();
      localReaderFileStatistic.doReportException("MttReader:openBook", localRuntimeException);
      localReaderFileStatistic.openResult = 4;
      localReaderFileStatistic.errCode = 2000;
      localReaderFileStatistic.addToStatManager(false);
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.b(0);
    }
  }
  
  void a(int paramInt)
  {
    ReaderMessage localReaderMessage = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage;
    if (localReaderMessage != null) {
      localReaderMessage.sendDelayed(100, paramInt);
    }
  }
  
  void b()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.a(-1);
  }
  
  void c()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("checkReaderCore:gReaderCoreUsing=");
    localStringBuilder.append(jdField_a_of_type_Int);
    LogUtils.d("MttReader", localStringBuilder.toString());
    int j = jdField_a_of_type_Int;
    if (j == b)
    {
      createReader(this.jdField_a_of_type_JavaLangString);
      return;
    }
    if (j == c) {
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.postRun(200);
    }
  }
  
  public void cancelTimer()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancelAll();
  }
  
  public boolean createReader(String paramString)
  {
    LogUtils.d("MttReader", "createReader");
    jdField_a_of_type_Int = d;
    d();
    b();
    Object localObject = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCore.getReaderPack();
    String str1 = ((Bundle)localObject).getString("name");
    String str2 = ((Bundle)localObject).getString("version");
    ReaderFileStatistic localReaderFileStatistic = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.a;
    localReaderFileStatistic.setSoName(str1);
    localReaderFileStatistic.soVersion = str2;
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.doCallBackEvent(5030, localObject, null);
    boolean bool = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCore.initReader(paramString);
    try
    {
      paramString = new Bundle();
      paramString.putInt("extra_title_height", this.h);
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("createReader mTitleBarHeight: ");
      ((StringBuilder)localObject).append(this.h);
      LogUtils.d("allenhan", ((StringBuilder)localObject).toString());
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCore.doAction(58, paramString, null);
      if (this.jdField_a_of_type_AndroidViewView != null) {
        this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCore.doAction(71, this.jdField_a_of_type_AndroidViewView, null);
      }
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    if (bool)
    {
      LogUtils.d("MttReader", "createReader:1");
      a();
      LogUtils.d("MttReader", "createReader:2");
    }
    else if (localReaderFileStatistic != null)
    {
      localReaderFileStatistic.openResult = 3;
      localReaderFileStatistic.addToStatManager(false);
    }
    LogUtils.d("MttReader", "createReader:end");
    return bool;
  }
  
  void d()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent = new ReaderMessage.MessageEvent()
    {
      public void onMessage(Message paramAnonymousMessage)
      {
        LogUtils.d("MttReader", "onMessage ...");
        if ((paramAnonymousMessage != null) && (paramAnonymousMessage.what == 100))
        {
          LogUtils.d("MttReader", "timeout message ...");
          paramAnonymousMessage = ReaderCoreWrapper.this;
          paramAnonymousMessage.g += 10000;
          paramAnonymousMessage = ReaderCoreWrapper.this.a.a;
          Object localObject = ReaderCoreWrapper.this.a.a;
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Open file timeout: exceeds ");
          ((StringBuilder)localObject).append(ReaderCoreWrapper.this.g);
          ((StringBuilder)localObject).append("(ms)");
          paramAnonymousMessage.doReport("MttReader:createMessageEvent", 1007, ((StringBuilder)localObject).toString());
          return;
        }
        ReaderCoreWrapper.this.b();
        ReaderCoreWrapper.this.c();
      }
    };
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.setEvent(this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent);
  }
  
  public ReaderCore getReaderCore()
  {
    return this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCore;
  }
  
  public int getTitleBarHeight()
  {
    return this.h;
  }
  
  public int load(String paramString)
  {
    if (!this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCore.getReaderView().mReaderConfig.core_Using_One)
    {
      if (createReader(paramString)) {
        return 0;
      }
      return -1;
    }
    if (jdField_a_of_type_Int != b)
    {
      jdField_a_of_type_Int = c;
      this.jdField_a_of_type_JavaLangString = paramString;
      b();
      return 1;
    }
    if (createReader(paramString)) {
      return 0;
    }
    return -1;
  }
  
  public void setAdView(View paramView)
  {
    this.jdField_a_of_type_AndroidViewView = paramView;
  }
  
  public void setTitleBarHeight(int paramInt)
  {
    this.h = paramInt;
  }
  
  public void unload()
  {
    jdField_a_of_type_Int = b;
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancelAll();
    try
    {
      if (this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCore != null)
      {
        this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCore.destoryMyReader();
        return;
      }
    }
    catch (Exception localException)
    {
      if (this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.a != null) {
        this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.a.doReportException("MttReader:unload", localException);
      }
      localException.printStackTrace();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderCoreWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */