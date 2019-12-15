package com.tencent.mtt.external.reader.internal;

import android.os.Message;
import com.tencent.common.http.Apn;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.external.reader.base.ReaderMessage;
import com.tencent.mtt.external.reader.base.ReaderMessage.MessageEvent;
import java.io.File;
import java.util.FormatterClosedException;
import java.util.IllegalFormatException;
import java.util.LinkedList;

public class MttFilePreDownload
{
  public static final int READER_FINISH = 11;
  public static final int READER_FINISH_IN_QUEUE = 13;
  public static final int READER_FORCE_IN_QUEUE = 12;
  public static final int READER_OPEN_ERROR = 7;
  public static final int READER_SO_CANCEL = 10;
  public static final int READER_SO_FAILE = 4;
  public static final int READER_SO_PROGRESS = 5;
  public static final int READER_SO_START = 6;
  public static final int READER_SO_SUCCESS = 2;
  public static final int READER_SO_WILL_START = 8;
  public static final int READER_WAIT_IN_QUEUE = 3;
  static MttFilePreDownload jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload = null;
  static final String[] jdField_a_of_type_ArrayOfJavaLangString = { "docx", "pptx", "xlsx", "pdf", "epub", "reader_menu", "txt" };
  int jdField_a_of_type_Int;
  ReaderMessage.MessageEvent jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent = null;
  ReaderMessage jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage = null;
  ReaderFiletypeDetector jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector = null;
  String jdField_a_of_type_JavaLangString;
  LinkedList<String> jdField_a_of_type_JavaUtilLinkedList = new LinkedList();
  boolean jdField_a_of_type_Boolean;
  
  public MttFilePreDownload()
  {
    int i = 0;
    this.jdField_a_of_type_Boolean = false;
    this.jdField_a_of_type_JavaLangString = "";
    this.jdField_a_of_type_Int = 1;
    Object localObject1 = jdField_a_of_type_ArrayOfJavaLangString;
    int j = localObject1.length;
    while (i < j)
    {
      Object localObject2 = localObject1[i];
      this.jdField_a_of_type_JavaUtilLinkedList.add(localObject2);
      i += 1;
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage = new ReaderMessage();
    a();
    long l = System.currentTimeMillis();
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("unknown_");
    ((StringBuilder)localObject1).append(String.valueOf(l));
    ((StringBuilder)localObject1).toString();
  }
  
  private void b()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancel(2);
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancel(3);
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancel(4);
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancel(5);
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancel(6);
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancel(7);
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancel(8);
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancel(10);
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancel(11);
  }
  
  public static MttFilePreDownload getInstance()
  {
    if (jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload == null) {
      jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload = new MttFilePreDownload();
    }
    return jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload;
  }
  
  ReaderFiletypeDetector.onFiletypeDetectorCallback a()
  {
    new ReaderFiletypeDetector.onFiletypeDetectorCallback()
    {
      public void onSoDownloadError(int paramAnonymousInt)
      {
        MttFilePreDownload.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(4, paramAnonymousInt);
      }
      
      public void onSoDownloadProgress(int paramAnonymousInt)
      {
        MttFilePreDownload.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(5, paramAnonymousInt);
      }
      
      public void onSoDownloadStart(int paramAnonymousInt)
      {
        MttFilePreDownload.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(6, paramAnonymousInt);
      }
      
      public void onSoDownloadSuccess(int paramAnonymousInt)
      {
        MttFilePreDownload.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(2);
      }
      
      public void onSoDownloadWillStart()
      {
        MttFilePreDownload.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(8);
      }
      
      public boolean onSoILoad()
      {
        String str;
        if (MttFilePreDownload.this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector != null) {
          str = MttFilePreDownload.this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.getDexPath();
        } else {
          str = "";
        }
        MttFilePreDownload localMttFilePreDownload = MttFilePreDownload.this;
        return localMttFilePreDownload.a(localMttFilePreDownload.a(), str);
      }
    };
  }
  
  String a()
  {
    ReaderFiletypeDetector localReaderFiletypeDetector = this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector;
    if (localReaderFiletypeDetector != null) {
      return localReaderFiletypeDetector.getSoCachePath();
    }
    return "";
  }
  
  void a()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent = new ReaderMessage.MessageEvent()
    {
      public void onMessage(Message paramAnonymousMessage)
      {
        int i = paramAnonymousMessage.what;
        Object localObject;
        switch (i)
        {
        default: 
          switch (i)
          {
          default: 
            return;
          case 13: 
            this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.shutdown();
            return;
          case 12: 
            LogUtils.d("MttFilePreDownload", "READER_FORCE_IN_QUEUE:");
            if (this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_JavaUtilLinkedList.isEmpty()) {
              break;
            }
            paramAnonymousMessage = (String)this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_JavaUtilLinkedList.removeFirst();
            this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_JavaLangString = paramAnonymousMessage;
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("READER_FORCE_IN_QUEUE,ext=");
            ((StringBuilder)localObject).append(paramAnonymousMessage);
            LogUtils.d("MttFilePreDownload", ((StringBuilder)localObject).toString());
            localObject = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload;
            ((MttFilePreDownload)localObject).jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector = new ReaderFiletypeDetector(paramAnonymousMessage, ((MttFilePreDownload)localObject).a());
            this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.prepare(false);
            return;
          case 11: 
            this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancelAll();
            if (this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector != null) {
              this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.finish();
            }
            MttFilePreDownload.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload = null;
            return;
          }
          break;
        case 4: 
          LogUtils.d("MttFilePreDownload", "READER_SO_FAILE");
          if (this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector != null)
          {
            this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.finish();
            this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector = null;
          }
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("MttFilePreDownload:READER_SO_FAILE,err:");
          ((StringBuilder)localObject).append(paramAnonymousMessage.arg1);
          ((StringBuilder)localObject).append(",ext=");
          ((StringBuilder)localObject).append(this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_JavaLangString);
          ((StringBuilder)localObject).toString();
          this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_JavaUtilLinkedList.addLast(this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_JavaLangString);
          if (this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_Int >= 30) {
            return;
          }
          LogUtils.d("MttFilePreDownload", "READER_SO_FAILE,try to start");
          paramAnonymousMessage = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload;
          paramAnonymousMessage.jdField_a_of_type_Int *= 5;
          this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.sendDelayed(3, this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_Int * 60000);
          return;
        case 3: 
          int j = Apn.isWifiMode();
          if ((paramAnonymousMessage.arg1 == 10) && (Apn.is4GMode(true))) {
            i = 1;
          } else {
            i = 0;
          }
          j = i | j;
          paramAnonymousMessage = new StringBuilder();
          paramAnonymousMessage.append("READER_WAIT_IN_QUEUE,wifi=");
          paramAnonymousMessage.append(j);
          LogUtils.d("MttFilePreDownload", paramAnonymousMessage.toString());
          if ((!this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_JavaUtilLinkedList.isEmpty()) && (!this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_Boolean) && (j != 0))
          {
            paramAnonymousMessage = (String)this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_JavaUtilLinkedList.removeFirst();
            this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_JavaLangString = paramAnonymousMessage;
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("READER_WAIT_IN_QUEUE,ext=");
            ((StringBuilder)localObject).append(paramAnonymousMessage);
            LogUtils.d("MttFilePreDownload", ((StringBuilder)localObject).toString());
            localObject = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload;
            ((MttFilePreDownload)localObject).jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector = new ReaderFiletypeDetector(paramAnonymousMessage, ((MttFilePreDownload)localObject).a());
            this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.prepare(true);
            return;
          }
          if ((this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_JavaUtilLinkedList.isEmpty()) || (this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_Int >= 30)) {
            return;
          }
          LogUtils.d("MttFilePreDownload", "READER_WAIT_IN_QUEUE,try to start");
          paramAnonymousMessage = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload;
          paramAnonymousMessage.jdField_a_of_type_Int *= 2;
          this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.sendDelayed(3, this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_Int * 60000);
          try
          {
            String.format("MttFilePreDownload:READER_WAIT_IN_QUEUE,try to start,mDelayRatio=%d,ext=%s,wifi=%b", new Object[] { Integer.valueOf(this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_Int), this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_JavaLangString, Boolean.valueOf(j) });
            return;
          }
          catch (FormatterClosedException paramAnonymousMessage)
          {
            paramAnonymousMessage.printStackTrace();
            return;
          }
          catch (IllegalFormatException paramAnonymousMessage)
          {
            paramAnonymousMessage.printStackTrace();
            return;
          }
        }
        LogUtils.d("MttFilePreDownload", "READER_SO_SUCCESS");
        if (!this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.haseMessage(3)) {
          this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.sendDelayed(3, 60000);
        }
        paramAnonymousMessage = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFilePreDownload;
        paramAnonymousMessage.jdField_a_of_type_JavaLangString = "";
        paramAnonymousMessage.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector = null;
        paramAnonymousMessage.jdField_a_of_type_Int = 1;
      }
    };
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.setEvent(this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent);
  }
  
  boolean a(String paramString1, String paramString2)
  {
    return new File(paramString1, paramString2).exists();
  }
  
  public void pause()
  {
    LogUtils.d("MttFilePreDownload", "pause");
    this.jdField_a_of_type_Boolean = true;
  }
  
  public void shutdown()
  {
    LogUtils.d("MttFilePreDownload", "shutdown");
    this.jdField_a_of_type_JavaUtilLinkedList.clear();
    b();
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(11);
  }
  
  public void start()
  {
    LogUtils.d("MttFilePreDownload", "start");
    int i = 0;
    this.jdField_a_of_type_Boolean = false;
    this.jdField_a_of_type_Int = 1;
    if (!Apn.isWifiMode())
    {
      LogUtils.d("MttFilePreDownload", "no wifi");
      return;
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancel(3);
    boolean bool1 = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.haseMessage(8);
    boolean bool2 = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.haseMessage(6);
    boolean bool3 = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.haseMessage(5);
    boolean bool4 = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.haseMessage(4);
    boolean bool5 = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.haseMessage(2);
    if (this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector != null) {
      i = 1;
    }
    if ((i | bool1 | false | bool2 | bool3 | bool4 | bool5) == 0)
    {
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.sendDelayed(3, 60000);
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.sendDelayed(13, 1800000);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\MttFilePreDownload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */