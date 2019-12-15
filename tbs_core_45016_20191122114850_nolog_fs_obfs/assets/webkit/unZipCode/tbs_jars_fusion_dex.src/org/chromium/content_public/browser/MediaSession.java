package org.chromium.content_public.browser;

import android.support.annotation.Nullable;
import org.chromium.base.ObserverList.RewindableIterator;
import org.chromium.base.VisibleForTesting;
import org.chromium.content.browser.MediaSessionImpl;

public abstract class MediaSession
{
  @Nullable
  public static MediaSession fromWebContents(WebContents paramWebContents)
  {
    return MediaSessionImpl.fromWebContents(paramWebContents);
  }
  
  public abstract void didReceiveAction(int paramInt);
  
  @VisibleForTesting
  public abstract ObserverList.RewindableIterator<MediaSessionObserver> getObserversForTesting();
  
  public abstract void resume();
  
  public abstract void seekBackward(long paramLong);
  
  public abstract void seekForward(long paramLong);
  
  public abstract void stop();
  
  public abstract void suspend();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\MediaSession.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */