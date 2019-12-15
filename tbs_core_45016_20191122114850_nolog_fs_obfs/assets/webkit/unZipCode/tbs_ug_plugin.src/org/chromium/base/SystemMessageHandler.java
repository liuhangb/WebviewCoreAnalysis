package org.chromium.base;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.MessageQueue.IdleHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;

@JNINamespace("base")
@MainDex
class SystemMessageHandler
  extends Handler
{
  private long jdField_a_of_type_Long;
  private final MessageQueue.IdleHandler jdField_a_of_type_AndroidOsMessageQueue$IdleHandler = new MessageQueue.IdleHandler()
  {
    public boolean queueIdle()
    {
      if (SystemMessageHandler.a(SystemMessageHandler.this) == 0L) {
        return false;
      }
      SystemMessageHandler localSystemMessageHandler = SystemMessageHandler.this;
      SystemMessageHandler.a(localSystemMessageHandler, SystemMessageHandler.a(localSystemMessageHandler));
      return true;
    }
  };
  private boolean jdField_a_of_type_Boolean;
  
  protected SystemMessageHandler(long paramLong)
  {
    this.jdField_a_of_type_Long = paramLong;
    Looper.myQueue().addIdleHandler(this.jdField_a_of_type_AndroidOsMessageQueue$IdleHandler);
  }
  
  private Message a(int paramInt)
  {
    Message localMessage = Message.obtain();
    localMessage.what = paramInt;
    MessageCompat.setAsynchronous(localMessage, true);
    return localMessage;
  }
  
  @CalledByNative
  private static SystemMessageHandler create(long paramLong)
  {
    return new SystemMessageHandler(paramLong);
  }
  
  private native void nativeDoIdleWork(long paramLong);
  
  private native void nativeDoRunLoopOnce(long paramLong, boolean paramBoolean);
  
  @CalledByNative
  private void scheduleDelayedWork(long paramLong)
  {
    if (this.jdField_a_of_type_Boolean) {
      removeMessages(2);
    }
    this.jdField_a_of_type_Boolean = true;
    sendMessageDelayed(a(2), paramLong);
  }
  
  @CalledByNative
  private void scheduleWork()
  {
    sendMessage(a(1));
  }
  
  @CalledByNative
  private void shutdown()
  {
    this.jdField_a_of_type_Long = 0L;
  }
  
  public void handleMessage(Message paramMessage)
  {
    if (this.jdField_a_of_type_Long == 0L) {
      return;
    }
    boolean bool;
    if (paramMessage.what == 2) {
      bool = true;
    } else {
      bool = false;
    }
    if (bool) {
      this.jdField_a_of_type_Boolean = false;
    }
    nativeDoRunLoopOnce(this.jdField_a_of_type_Long, bool);
  }
  
  private static class MessageCompat
  {
    static final MessageWrapperImpl a = new LegacyMessageWrapperImpl();
    
    static
    {
      if (Build.VERSION.SDK_INT >= 22)
      {
        a = new LollipopMr1MessageWrapperImpl();
        return;
      }
    }
    
    public static void setAsynchronous(Message paramMessage, boolean paramBoolean)
    {
      a.setAsynchronous(paramMessage, paramBoolean);
    }
    
    static class LegacyMessageWrapperImpl
      implements SystemMessageHandler.MessageCompat.MessageWrapperImpl
    {
      private Method a;
      
      LegacyMessageWrapperImpl()
      {
        try
        {
          this.a = Message.class.getMethod("setAsynchronous", new Class[] { Boolean.TYPE });
          return;
        }
        catch (RuntimeException localRuntimeException)
        {
          Log.e("cr.SysMessageHandler", "Exception while loading Message.setAsynchronous method", new Object[] { localRuntimeException });
          return;
        }
        catch (NoSuchMethodException localNoSuchMethodException)
        {
          Log.e("cr.SysMessageHandler", "Failed to load Message.setAsynchronous method", new Object[] { localNoSuchMethodException });
        }
      }
      
      public void setAsynchronous(Message paramMessage, boolean paramBoolean)
      {
        Method localMethod = this.a;
        if (localMethod == null) {
          return;
        }
        try
        {
          localMethod.invoke(paramMessage, new Object[] { Boolean.valueOf(paramBoolean) });
          return;
        }
        catch (IllegalAccessException paramMessage)
        {
          for (;;) {}
        }
        catch (IllegalArgumentException paramMessage)
        {
          for (;;) {}
        }
        catch (InvocationTargetException paramMessage)
        {
          for (;;) {}
        }
        catch (RuntimeException paramMessage)
        {
          for (;;) {}
        }
        Log.e("cr.SysMessageHandler", "Runtime exception during async message creation, disabling.", new Object[0]);
        this.a = null;
        return;
        Log.e("cr.SysMessageHandler", "Invocation exception during async message creation, disabling.", new Object[0]);
        this.a = null;
        return;
        Log.e("cr.SysMessageHandler", "Illegal argument for async message creation, disabling.", new Object[0]);
        this.a = null;
        return;
        Log.e("cr.SysMessageHandler", "Illegal access to async message creation, disabling.", new Object[0]);
        this.a = null;
      }
    }
    
    static class LollipopMr1MessageWrapperImpl
      implements SystemMessageHandler.MessageCompat.MessageWrapperImpl
    {
      @SuppressLint({"NewApi"})
      public void setAsynchronous(Message paramMessage, boolean paramBoolean)
      {
        paramMessage.setAsynchronous(paramBoolean);
      }
    }
    
    static abstract interface MessageWrapperImpl
    {
      public abstract void setAsynchronous(Message paramMessage, boolean paramBoolean);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\SystemMessageHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */