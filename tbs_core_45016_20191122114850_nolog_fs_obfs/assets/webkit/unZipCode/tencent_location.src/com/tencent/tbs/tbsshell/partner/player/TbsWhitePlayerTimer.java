package com.tencent.tbs.tbsshell.partner.player;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;

public class TbsWhitePlayerTimer
  implements Handler.Callback
{
  private static int VR_SIMPLE_TIMER_MESSAGE = 101;
  private TbsWhitePlayerTimerCallback callback = null;
  private long delay = 500L;
  private Handler handler = null;
  
  public TbsWhitePlayerTimer(long paramLong, TbsWhitePlayerTimerCallback paramTbsWhitePlayerTimerCallback)
  {
    this.delay = paramLong;
    this.callback = paramTbsWhitePlayerTimerCallback;
  }
  
  public boolean handleMessage(Message paramMessage)
  {
    if (paramMessage.what == VR_SIMPLE_TIMER_MESSAGE)
    {
      paramMessage = this.callback;
      if (paramMessage != null) {
        paramMessage.onSimpleTimerCall(this);
      }
      paramMessage = this.handler;
      if (paramMessage != null) {
        paramMessage.sendEmptyMessageDelayed(VR_SIMPLE_TIMER_MESSAGE, this.delay);
      }
    }
    return true;
  }
  
  public void startTimer()
  {
    if (this.handler == null)
    {
      this.handler = new Handler(Looper.getMainLooper(), this);
      this.handler.sendEmptyMessageDelayed(VR_SIMPLE_TIMER_MESSAGE, this.delay);
    }
  }
  
  public void stopTimer()
  {
    Handler localHandler = this.handler;
    if (localHandler != null)
    {
      localHandler.removeMessages(VR_SIMPLE_TIMER_MESSAGE);
      this.handler = null;
    }
  }
  
  public static abstract interface TbsWhitePlayerTimerCallback
  {
    public abstract void onSimpleTimerCall(TbsWhitePlayerTimer paramTbsWhitePlayerTimer);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\player\TbsWhitePlayerTimer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */