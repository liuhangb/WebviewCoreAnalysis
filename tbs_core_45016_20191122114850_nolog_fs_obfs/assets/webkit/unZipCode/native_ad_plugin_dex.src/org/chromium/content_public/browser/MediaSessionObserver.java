package org.chromium.content_public.browser;

import android.support.annotation.Nullable;
import java.util.Set;
import org.chromium.content.browser.MediaSessionImpl;
import org.chromium.content_public.common.MediaMetadata;

public abstract class MediaSessionObserver
{
  private MediaSessionImpl a;
  
  public void a() {}
  
  public void a(Set<Integer> paramSet) {}
  
  public void a(MediaMetadata paramMediaMetadata) {}
  
  public void a(boolean paramBoolean1, boolean paramBoolean2) {}
  
  @Nullable
  public final MediaSession getMediaSession()
  {
    return this.a;
  }
  
  public final void stopObserving()
  {
    MediaSessionImpl localMediaSessionImpl = this.a;
    if (localMediaSessionImpl == null) {
      return;
    }
    localMediaSessionImpl.removeObserver(this);
    this.a = null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\MediaSessionObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */