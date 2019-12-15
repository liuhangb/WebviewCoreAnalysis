package com.tencent.common.manifest;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

public class EventEmiter
  implements Handler.Callback
{
  private static EventEmiter jdField_a_of_type_ComTencentCommonManifestEventEmiter;
  private Handler jdField_a_of_type_AndroidOsHandler = new Handler(Looper.getMainLooper(), this);
  private HandlerThread jdField_a_of_type_AndroidOsHandlerThread = new HandlerThread(getClass().getName());
  private Handler b;
  
  private EventEmiter()
  {
    this.jdField_a_of_type_AndroidOsHandlerThread.start();
    this.b = new Handler(this.jdField_a_of_type_AndroidOsHandlerThread.getLooper(), this);
  }
  
  public static EventEmiter getDefault()
  {
    if (jdField_a_of_type_ComTencentCommonManifestEventEmiter == null) {
      try
      {
        if (jdField_a_of_type_ComTencentCommonManifestEventEmiter == null) {
          jdField_a_of_type_ComTencentCommonManifestEventEmiter = new EventEmiter();
        }
      }
      finally {}
    }
    return jdField_a_of_type_ComTencentCommonManifestEventEmiter;
  }
  
  public void emit(EventMessage paramEventMessage)
  {
    EventReceiverImpl[] arrayOfEventReceiverImpl = AppManifest.getInstance().queryEventReceivers(paramEventMessage.eventName);
    if (arrayOfEventReceiverImpl == null) {
      return;
    }
    int j = arrayOfEventReceiverImpl.length;
    int i = 0;
    while (i < j)
    {
      EventReceiverImpl localEventReceiverImpl = arrayOfEventReceiverImpl[i];
      Handler localHandler;
      Object[] arrayOfObject;
      if ("MAINTHREAD".equals(localEventReceiverImpl.b))
      {
        localHandler = this.jdField_a_of_type_AndroidOsHandler;
        arrayOfObject = new Object[2];
        arrayOfObject[0] = localEventReceiverImpl;
        arrayOfObject[1] = paramEventMessage;
      }
      for (;;)
      {
        Message.obtain(localHandler, 1, arrayOfObject).sendToTarget();
        break label130;
        if (!"ASYNCTHREAD".equals(localEventReceiverImpl.b)) {
          break;
        }
        localHandler = this.b;
        arrayOfObject = new Object[2];
        arrayOfObject[0] = localEventReceiverImpl;
        arrayOfObject[1] = paramEventMessage;
      }
      localEventReceiverImpl.invoke(paramEventMessage);
      label130:
      i += 1;
    }
  }
  
  public boolean handleMessage(Message paramMessage)
  {
    if ((paramMessage != null) && ((paramMessage.obj instanceof Object[])))
    {
      paramMessage = (Object[])paramMessage.obj;
      ((EventReceiverImpl)paramMessage[0]).invoke((EventMessage)paramMessage[1]);
      return true;
    }
    return false;
  }
  
  public void register(String paramString, Object paramObject)
  {
    paramString = AppManifest.getInstance().queryEventReceivers(paramString);
    if (paramString != null)
    {
      int j = paramString.length;
      int i = 0;
      while (i < j)
      {
        if (paramString[i].register(paramObject)) {
          return;
        }
        i += 1;
      }
    }
  }
  
  public void unregister(String paramString, Object paramObject)
  {
    paramString = AppManifest.getInstance().queryEventReceivers(paramString);
    if (paramString != null)
    {
      int j = paramString.length;
      int i = 0;
      while (i < j)
      {
        if (paramString[i].unregister(paramObject)) {
          return;
        }
        i += 1;
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\manifest\EventEmiter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */