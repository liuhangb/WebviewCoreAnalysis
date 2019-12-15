package com.tencent.mtt.external.reader.base;

import android.os.Handler;
import android.os.Message;

public class ReaderMessage
{
  final Handler jdField_a_of_type_AndroidOsHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (ReaderMessage.this.jdField_a_of_type_Boolean) {
        return;
      }
      if (ReaderMessage.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent != null) {
        ReaderMessage.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent.onMessage(paramAnonymousMessage);
      }
    }
  };
  MessageEvent jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent = null;
  final Runnable jdField_a_of_type_JavaLangRunnable = new Runnable()
  {
    public void run()
    {
      if (ReaderMessage.this.jdField_a_of_type_Boolean) {
        return;
      }
      if (ReaderMessage.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent != null) {
        ReaderMessage.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent.onMessage(null);
      }
    }
  };
  boolean jdField_a_of_type_Boolean = false;
  
  public void cancel(int paramInt)
  {
    this.jdField_a_of_type_AndroidOsHandler.removeMessages(paramInt);
  }
  
  public void cancelAll()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent = null;
    this.jdField_a_of_type_AndroidOsHandler.removeCallbacksAndMessages(null);
    this.jdField_a_of_type_Boolean = true;
    this.jdField_a_of_type_AndroidOsHandler.removeCallbacks(this.jdField_a_of_type_JavaLangRunnable);
  }
  
  public boolean haseMessage(int paramInt)
  {
    return this.jdField_a_of_type_AndroidOsHandler.hasMessages(paramInt);
  }
  
  public void postRun(int paramInt)
  {
    this.jdField_a_of_type_AndroidOsHandler.postDelayed(this.jdField_a_of_type_JavaLangRunnable, paramInt);
  }
  
  public void send(int paramInt)
  {
    Message localMessage = this.jdField_a_of_type_AndroidOsHandler.obtainMessage(paramInt);
    this.jdField_a_of_type_AndroidOsHandler.sendMessage(localMessage);
  }
  
  public void send(int paramInt1, int paramInt2)
  {
    Message localMessage = this.jdField_a_of_type_AndroidOsHandler.obtainMessage(paramInt1);
    localMessage.arg1 = paramInt2;
    this.jdField_a_of_type_AndroidOsHandler.sendMessage(localMessage);
  }
  
  public void send(int paramInt, Object paramObject)
  {
    Message localMessage = this.jdField_a_of_type_AndroidOsHandler.obtainMessage(paramInt);
    localMessage.obj = paramObject;
    this.jdField_a_of_type_AndroidOsHandler.sendMessage(localMessage);
  }
  
  public void sendDelayed(int paramInt1, int paramInt2)
  {
    Message localMessage = this.jdField_a_of_type_AndroidOsHandler.obtainMessage(paramInt1);
    this.jdField_a_of_type_AndroidOsHandler.sendMessageDelayed(localMessage, paramInt2);
  }
  
  public void setEvent(MessageEvent paramMessageEvent)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent = paramMessageEvent;
  }
  
  public static abstract interface MessageEvent
  {
    public abstract void onMessage(Message paramMessage);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */