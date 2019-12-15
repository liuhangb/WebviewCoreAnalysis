package com.tencent.mtt.external.reader;

import android.content.Context;
import android.os.Message;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.external.reader.base.ReaderMessage;
import com.tencent.mtt.external.reader.base.ReaderMessage.MessageEvent;
import com.tencent.mtt.external.reader.export.IReaderProxy;
import com.tencent.mtt.external.reader.internal.MttFilePreDownload;
import com.tencent.mtt.external.reader.internal.ReaderFiletypeDetector;
import com.tencent.mtt.external.reader.internal.ReaderFiletypeDetector.onFiletypeDetectorCallback;
import com.tencent.mtt.external.reader.utils.AppEngine;
import java.io.File;

public final class MttPluginCheck
{
  public static final int HIDE_PROGRESSTIP = 1;
  public static final int READER_OPEN_ERROR = 7;
  public static final int READER_SO_CANCEL = 10;
  public static final int READER_SO_FAILE = 4;
  public static final int READER_SO_INTO_START = 9;
  public static final int READER_SO_PROGRESS = 5;
  public static final int READER_SO_START = 6;
  public static final int READER_SO_SUCCESS = 2;
  public static final int READER_SO_WILL_START = 8;
  public static final int READER_WAIT_IN_QUEUE = 3;
  Context jdField_a_of_type_AndroidContentContext = null;
  ReaderCallbackAction jdField_a_of_type_ComTencentMttExternalReaderReaderCallbackAction = null;
  ReaderMessage.MessageEvent jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent = null;
  ReaderMessage jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage = new ReaderMessage();
  ReaderFiletypeDetector jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector = null;
  boolean jdField_a_of_type_Boolean = false;
  boolean b = false;
  
  public MttPluginCheck(Context paramContext, String paramString, IReaderProxy paramIReaderProxy, boolean paramBoolean)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    AppEngine.getInstance().setApplicationContext(this.jdField_a_of_type_AndroidContentContext.getApplicationContext());
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector = new ReaderFiletypeDetector(paramString, a());
    a();
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.setEvent(this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent);
    this.jdField_a_of_type_Boolean = paramBoolean;
    this.jdField_a_of_type_ComTencentMttExternalReaderReaderCallbackAction = new ReaderCallbackAction(this.jdField_a_of_type_AndroidContentContext, paramIReaderProxy);
  }
  
  ReaderFiletypeDetector.onFiletypeDetectorCallback a()
  {
    new ReaderFiletypeDetector.onFiletypeDetectorCallback()
    {
      public void onSoDownloadError(int paramAnonymousInt)
      {
        MttPluginCheck.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(4, paramAnonymousInt);
      }
      
      public void onSoDownloadProgress(int paramAnonymousInt)
      {
        MttPluginCheck.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(5, paramAnonymousInt);
      }
      
      public void onSoDownloadStart(int paramAnonymousInt)
      {
        MttPluginCheck.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(6, paramAnonymousInt);
      }
      
      public void onSoDownloadSuccess(int paramAnonymousInt)
      {
        MttPluginCheck.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(2);
      }
      
      public void onSoDownloadWillStart()
      {
        MttPluginCheck.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(8);
      }
      
      public boolean onSoILoad()
      {
        MttPluginCheck localMttPluginCheck = MttPluginCheck.this;
        return localMttPluginCheck.a(localMttPluginCheck.getReaderPath(), MttPluginCheck.this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.getDexPath());
      }
    };
  }
  
  void a()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent = new ReaderMessage.MessageEvent()
    {
      public void onMessage(Message paramAnonymousMessage)
      {
        switch (paramAnonymousMessage.what)
        {
        case 3: 
        case 7: 
        default: 
          
        case 10: 
          LogUtils.d("MttPluginCheck", "READER_SO_CANCEL:");
          MttPluginCheck.this.close();
          return;
        case 9: 
          LogUtils.d("MttPluginCheck", "READER_SO_INTO_START:");
          if (MttPluginCheck.this.jdField_a_of_type_Boolean)
          {
            MttPluginCheck.this.jdField_a_of_type_ComTencentMttExternalReaderReaderCallbackAction.doCallBackEvent(5012, Integer.valueOf(5014), null);
            MttPluginCheck.this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.downloadSo(false);
            return;
          }
          if (MttPluginCheck.this.jdField_a_of_type_ComTencentMttExternalReaderReaderCallbackAction != null) {
            if (MttPluginCheck.this.b) {
              MttPluginCheck.this.jdField_a_of_type_ComTencentMttExternalReaderReaderCallbackAction.doCallBackEvent(5012, Integer.valueOf(5013), null);
            } else {
              MttPluginCheck.this.jdField_a_of_type_ComTencentMttExternalReaderReaderCallbackAction.doCallBackEvent(5012, Integer.valueOf(-1), null);
            }
          }
          MttPluginCheck.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(10);
          return;
        case 5: 
          int i = paramAnonymousMessage.arg1;
          return;
        case 4: 
          LogUtils.d("MttPluginCheck", "READER_SO_FAILE:");
          if (MttPluginCheck.this.jdField_a_of_type_ComTencentMttExternalReaderReaderCallbackAction != null) {
            MttPluginCheck.this.jdField_a_of_type_ComTencentMttExternalReaderReaderCallbackAction.doCallBackEvent(5012, Integer.valueOf(paramAnonymousMessage.arg1), null);
          }
          MttFilePreDownload.getInstance().start();
          return;
        case 2: 
          LogUtils.d("MttPluginCheck", "READER_SO_SUCCESS:");
          if (MttPluginCheck.this.jdField_a_of_type_ComTencentMttExternalReaderReaderCallbackAction != null) {
            MttPluginCheck.this.jdField_a_of_type_ComTencentMttExternalReaderReaderCallbackAction.doCallBackEvent(5012, Integer.valueOf(0), null);
          }
          MttFilePreDownload.getInstance().start();
        }
      }
    };
  }
  
  boolean a(String paramString1, String paramString2)
  {
    return new File(paramString1, paramString2).exists();
  }
  
  public void check()
  {
    LogUtils.d("MttPluginCheck", "check:");
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.prepare(false);
  }
  
  public void close()
  {
    LogUtils.d("MttPluginCheck", "close:");
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancelAll();
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.finish();
    this.jdField_a_of_type_AndroidContentContext = null;
    this.jdField_a_of_type_ComTencentMttExternalReaderReaderCallbackAction = null;
  }
  
  public String getReaderPath()
  {
    return this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.getSoCachePath();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\MttPluginCheck.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */