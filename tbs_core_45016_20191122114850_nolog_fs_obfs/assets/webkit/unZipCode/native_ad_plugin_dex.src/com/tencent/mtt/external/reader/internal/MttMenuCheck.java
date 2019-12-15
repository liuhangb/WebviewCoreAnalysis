package com.tencent.mtt.external.reader.internal;

import android.os.Message;
import com.tencent.mtt.external.reader.base.ReaderMessage;
import com.tencent.mtt.external.reader.base.ReaderMessage.MessageEvent;
import java.io.File;

public class MttMenuCheck
{
  public static final int HIDE_PROGRESSTIP = 1;
  public static final int READER_OPEN_ERROR = 7;
  public static final int READER_SO_FAILE = 4;
  public static final int READER_SO_INTO_START = 9;
  public static final int READER_SO_PROGRESS = 5;
  public static final int READER_SO_START = 6;
  public static final int READER_SO_SUCCESS = 2;
  public static final int READER_SO_WILL_START = 8;
  public static final int READER_WAIT_IN_QUEUE = 3;
  static long jdField_a_of_type_Long = -1L;
  ReaderMessage.MessageEvent jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent = null;
  ReaderMessage jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage = new ReaderMessage();
  private MenuCheckCallback jdField_a_of_type_ComTencentMttExternalReaderInternalMttMenuCheck$MenuCheckCallback = null;
  ReaderFiletypeDetector jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector = null;
  String jdField_a_of_type_JavaLangString = "";
  boolean jdField_a_of_type_Boolean = false;
  
  public MttMenuCheck()
  {
    a();
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.setEvent(this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent);
  }
  
  ReaderFiletypeDetector.onFiletypeDetectorCallback a()
  {
    new ReaderFiletypeDetector.onFiletypeDetectorCallback()
    {
      public void onSoDownloadError(int paramAnonymousInt)
      {
        MttMenuCheck.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(4, paramAnonymousInt);
      }
      
      public void onSoDownloadProgress(int paramAnonymousInt)
      {
        MttMenuCheck.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(5, paramAnonymousInt);
      }
      
      public void onSoDownloadStart(int paramAnonymousInt)
      {
        MttMenuCheck.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(6, paramAnonymousInt);
      }
      
      public void onSoDownloadSuccess(int paramAnonymousInt)
      {
        MttMenuCheck.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(2);
        MttMenuCheck localMttMenuCheck = MttMenuCheck.this;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(MttMenuCheck.this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.getSoCachePath());
        localStringBuilder.append(File.separator);
        localStringBuilder.append("MenueRes.apk");
        localMttMenuCheck.jdField_a_of_type_JavaLangString = localStringBuilder.toString();
      }
      
      public void onSoDownloadWillStart()
      {
        MttMenuCheck.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(8);
      }
      
      public boolean onSoILoad()
      {
        MttMenuCheck localMttMenuCheck = MttMenuCheck.this;
        return localMttMenuCheck.a(localMttMenuCheck.getMenuPath(), "MenueRes.apk");
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
          
        case 9: 
          MttMenuCheck.this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.downloadSo(false);
          return;
        case 4: 
          if (MttMenuCheck.a(MttMenuCheck.this) != null)
          {
            MttMenuCheck.a(MttMenuCheck.this).onCheckFailed();
            return;
          }
          break;
        case 2: 
          MttMenuCheck.jdField_a_of_type_Long = System.currentTimeMillis();
          if (MttMenuCheck.a(MttMenuCheck.this) != null) {
            MttMenuCheck.a(MttMenuCheck.this).onCheckSuccess();
          }
          break;
        }
      }
    };
  }
  
  boolean a()
  {
    return System.currentTimeMillis() - jdField_a_of_type_Long >= 86400000L;
  }
  
  boolean a(String paramString1, String paramString2)
  {
    return new File(paramString1, paramString2).exists();
  }
  
  public void check()
  {
    if (a()) {
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.prepare(false);
    }
  }
  
  public void close()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancelAll();
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.finish();
  }
  
  public String getMenuPath()
  {
    return this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.getSoCachePath();
  }
  
  public String getMenuResPath()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public void setMenuCheckCallback(MenuCheckCallback paramMenuCheckCallback)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttMenuCheck$MenuCheckCallback = paramMenuCheckCallback;
  }
  
  public static abstract interface MenuCheckCallback
  {
    public abstract void onCheckFailed();
    
    public abstract void onCheckSuccess();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\MttMenuCheck.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */