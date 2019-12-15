package com.tencent.tbs.tbsshell.partner.player;

import java.lang.reflect.Method;

public class TbsMediaPlayerListener
{
  public static int MEDIA_EXTRA_AUDIOTRACK_INDEX = 104;
  public static int MEDIA_EXTRA_AUDIOTRACK_TITLES = 103;
  public static int MEDIA_EXTRA_SUBTITLE_COUNT = 101;
  public static int MEDIA_EXTRA_SUBTITLE_INDEX = 102;
  public static int ROTATE_ACTION_HASROTATE = 3;
  public static int ROTATE_ACTION_NOTHING = 1;
  public static int ROTATE_ACTION_SETDEGREE = 2;
  public static int ROTATE_ACTION_UNKNOWN;
  private Object mListenerImpl = null;
  private Method mMethodBuffering = null;
  private Method mMethodCompleted = null;
  private Method mMethodError = null;
  private Method mMethodExtra = null;
  private Method mMethodInfo = null;
  private Method mMethodPaused = null;
  private Method mMethodPlaying = null;
  private Method mMethodPrepared = null;
  private Method mMethodProgress = null;
  private Method mMethodSeeked = null;
  private Method mMethodSubtitle = null;
  
  public TbsMediaPlayerListener(Object paramObject)
  {
    this.mListenerImpl = paramObject;
  }
  
  public void onBufferingUpdate(float paramFloat)
  {
    if (this.mMethodBuffering == null) {}
    try
    {
      this.mMethodBuffering = this.mListenerImpl.getClass().getDeclaredMethod("onBufferingUpdate", new Class[] { Float.TYPE });
      this.mMethodBuffering.setAccessible(true);
    }
    catch (Exception localException1)
    {
      for (;;)
      {
        try
        {
          Method localMethod;
          localMethod.invoke(this.mListenerImpl, new Object[] { Float.valueOf(paramFloat) });
          return;
        }
        catch (Exception localException2) {}
        localException1 = localException1;
      }
    }
    localMethod = this.mMethodBuffering;
    if (localMethod != null) {}
  }
  
  public void onPlayerCompleted()
  {
    if (this.mMethodCompleted == null) {}
    try
    {
      this.mMethodCompleted = this.mListenerImpl.getClass().getDeclaredMethod("onPlayerCompleted", new Class[0]);
      this.mMethodCompleted.setAccessible(true);
    }
    catch (Exception localException1)
    {
      for (;;)
      {
        try
        {
          Method localMethod;
          localMethod.invoke(this.mListenerImpl, new Object[0]);
          return;
        }
        catch (Exception localException2) {}
        localException1 = localException1;
      }
    }
    localMethod = this.mMethodCompleted;
    if (localMethod != null) {}
  }
  
  public void onPlayerError(String paramString, int paramInt1, int paramInt2, Throwable paramThrowable)
  {
    if (this.mMethodError == null) {}
    try
    {
      this.mMethodError = this.mListenerImpl.getClass().getDeclaredMethod("onPlayerError", new Class[] { String.class, Integer.TYPE, Integer.TYPE, Throwable.class });
      this.mMethodError.setAccessible(true);
    }
    catch (Exception localException)
    {
      for (;;)
      {
        try
        {
          Method localMethod;
          localMethod.invoke(this.mListenerImpl, new Object[] { paramString, Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), paramThrowable });
          return;
        }
        catch (Exception paramString) {}
        localException = localException;
      }
    }
    localMethod = this.mMethodError;
    if (localMethod != null) {}
  }
  
  public void onPlayerExtra(int paramInt, Object paramObject)
  {
    if (this.mMethodExtra == null) {}
    try
    {
      this.mMethodExtra = this.mListenerImpl.getClass().getDeclaredMethod("onPlayerExtra", new Class[] { Integer.TYPE, Object.class });
      this.mMethodExtra.setAccessible(true);
    }
    catch (Exception localException)
    {
      for (;;)
      {
        try
        {
          Method localMethod;
          localMethod.invoke(this.mListenerImpl, new Object[] { Integer.valueOf(paramInt), paramObject });
          return;
        }
        catch (Exception paramObject) {}
        localException = localException;
      }
    }
    localMethod = this.mMethodExtra;
    if (localMethod != null) {}
  }
  
  public void onPlayerInfo(int paramInt1, int paramInt2)
  {
    if (this.mMethodInfo == null) {}
    try
    {
      this.mMethodInfo = this.mListenerImpl.getClass().getDeclaredMethod("onPlayerInfo", new Class[] { Integer.TYPE, Integer.TYPE });
      this.mMethodInfo.setAccessible(true);
    }
    catch (Exception localException1)
    {
      for (;;)
      {
        try
        {
          Method localMethod;
          localMethod.invoke(this.mListenerImpl, new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) });
          return;
        }
        catch (Exception localException2) {}
        localException1 = localException1;
      }
    }
    localMethod = this.mMethodInfo;
    if (localMethod != null) {}
  }
  
  public void onPlayerPaused()
  {
    if (this.mMethodPaused == null) {}
    try
    {
      this.mMethodPaused = this.mListenerImpl.getClass().getDeclaredMethod("onPlayerPaused", new Class[0]);
      this.mMethodPaused.setAccessible(true);
    }
    catch (Exception localException1)
    {
      for (;;)
      {
        try
        {
          Method localMethod;
          localMethod.invoke(this.mListenerImpl, new Object[0]);
          return;
        }
        catch (Exception localException2) {}
        localException1 = localException1;
      }
    }
    localMethod = this.mMethodPaused;
    if (localMethod != null) {}
  }
  
  public void onPlayerPlaying()
  {
    if (this.mMethodPlaying == null) {}
    try
    {
      this.mMethodPlaying = this.mListenerImpl.getClass().getDeclaredMethod("onPlayerPlaying", new Class[0]);
      this.mMethodPlaying.setAccessible(true);
    }
    catch (Exception localException1)
    {
      for (;;)
      {
        try
        {
          Method localMethod;
          localMethod.invoke(this.mListenerImpl, new Object[0]);
          return;
        }
        catch (Exception localException2) {}
        localException1 = localException1;
      }
    }
    localMethod = this.mMethodPlaying;
    if (localMethod != null) {}
  }
  
  public void onPlayerPrepared(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.mMethodPrepared == null) {}
    try
    {
      this.mMethodPrepared = this.mListenerImpl.getClass().getDeclaredMethod("onPlayerPrepared", new Class[] { Long.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE });
      this.mMethodPrepared.setAccessible(true);
    }
    catch (Exception localException1)
    {
      for (;;)
      {
        try
        {
          Method localMethod;
          localMethod.invoke(this.mListenerImpl, new Object[] { Long.valueOf(paramLong), Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4) });
          return;
        }
        catch (Exception localException2) {}
        localException1 = localException1;
      }
    }
    localMethod = this.mMethodPrepared;
    if (localMethod != null) {}
  }
  
  public void onPlayerProgress(long paramLong)
  {
    if (this.mMethodProgress == null) {}
    try
    {
      this.mMethodProgress = this.mListenerImpl.getClass().getDeclaredMethod("onPlayerProgress", new Class[] { Long.TYPE });
      this.mMethodProgress.setAccessible(true);
    }
    catch (Exception localException1)
    {
      for (;;)
      {
        try
        {
          Method localMethod;
          localMethod.invoke(this.mListenerImpl, new Object[] { Long.valueOf(paramLong) });
          return;
        }
        catch (Exception localException2) {}
        localException1 = localException1;
      }
    }
    localMethod = this.mMethodProgress;
    if (localMethod != null) {}
  }
  
  public void onPlayerSeeked(long paramLong)
  {
    if (this.mMethodSeeked == null) {}
    try
    {
      this.mMethodSeeked = this.mListenerImpl.getClass().getDeclaredMethod("onPlayerSeeked", new Class[] { Long.TYPE });
      this.mMethodSeeked.setAccessible(true);
    }
    catch (Exception localException1)
    {
      for (;;)
      {
        try
        {
          Method localMethod;
          localMethod.invoke(this.mListenerImpl, new Object[] { Long.valueOf(paramLong) });
          return;
        }
        catch (Exception localException2) {}
        localException1 = localException1;
      }
    }
    localMethod = this.mMethodSeeked;
    if (localMethod != null) {}
  }
  
  public void onPlayerSubtitle(String paramString)
  {
    if (this.mMethodSubtitle == null) {}
    try
    {
      this.mMethodSubtitle = this.mListenerImpl.getClass().getDeclaredMethod("onPlayerSubtitle", new Class[] { String.class });
      this.mMethodSubtitle.setAccessible(true);
    }
    catch (Exception localException)
    {
      for (;;)
      {
        try
        {
          Method localMethod;
          localMethod.invoke(this.mListenerImpl, new Object[] { paramString });
          return;
        }
        catch (Exception paramString) {}
        localException = localException;
      }
    }
    localMethod = this.mMethodSubtitle;
    if (localMethod != null) {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\player\TbsMediaPlayerListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */