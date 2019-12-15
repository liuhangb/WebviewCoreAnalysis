package org.chromium.tencent.audio;

import android.content.Context;
import android.net.Uri;
import java.io.IOException;
import java.util.HashMap;

public abstract interface RemoteMediaPlayerDelegate
{
  public abstract int getCurrentPosition();
  
  public abstract int getDuration();
  
  public abstract boolean isPlaying();
  
  public abstract void pause();
  
  public abstract void prepareAsync();
  
  public abstract void release();
  
  public abstract void seekTo(int paramInt);
  
  public abstract void setAudioInfo(String paramString1, String paramString2);
  
  public abstract void setDataSource(Context paramContext, Uri paramUri, HashMap<String, String> paramHashMap)
    throws IOException;
  
  public abstract void setMediaExtra(String paramString1, String paramString2);
  
  public abstract void setMediaPlayerListener(RemotePlayerListenerDelegate paramRemotePlayerListenerDelegate);
  
  public abstract void setVolume(float paramFloat1, float paramFloat2);
  
  public abstract void start();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\audio\RemoteMediaPlayerDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */