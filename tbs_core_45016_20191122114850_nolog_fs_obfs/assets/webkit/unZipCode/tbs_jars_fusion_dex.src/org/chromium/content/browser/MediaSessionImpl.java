package org.chromium.content.browser;

import java.util.HashSet;
import org.chromium.base.ObserverList;
import org.chromium.base.ObserverList.RewindableIterator;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content_public.browser.MediaSession;
import org.chromium.content_public.browser.MediaSessionObserver;
import org.chromium.content_public.browser.WebContents;
import org.chromium.content_public.common.MediaMetadata;

@JNINamespace("content")
public class MediaSessionImpl
  extends MediaSession
{
  private long jdField_a_of_type_Long;
  private ObserverList.RewindableIterator<MediaSessionObserver> jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator;
  private ObserverList<MediaSessionObserver> jdField_a_of_type_OrgChromiumBaseObserverList;
  
  private MediaSessionImpl(long paramLong)
  {
    this.jdField_a_of_type_Long = paramLong;
    this.jdField_a_of_type_OrgChromiumBaseObserverList = new ObserverList();
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator = this.jdField_a_of_type_OrgChromiumBaseObserverList.rewindableIterator();
  }
  
  @CalledByNative
  private static MediaSessionImpl create(long paramLong)
  {
    return new MediaSessionImpl(paramLong);
  }
  
  public static MediaSessionImpl fromWebContents(WebContents paramWebContents)
  {
    return nativeGetMediaSessionFromWebContents(paramWebContents);
  }
  
  @CalledByNative
  private boolean hasObservers()
  {
    return this.jdField_a_of_type_OrgChromiumBaseObserverList.isEmpty() ^ true;
  }
  
  @CalledByNative
  private void mediaSessionActionsChanged(int[] paramArrayOfInt)
  {
    HashSet localHashSet = new HashSet();
    int j = paramArrayOfInt.length;
    int i = 0;
    while (i < j)
    {
      localHashSet.add(Integer.valueOf(paramArrayOfInt[i]));
      i += 1;
    }
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((MediaSessionObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).a(localHashSet);
    }
  }
  
  @CalledByNative
  private void mediaSessionDestroyed()
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((MediaSessionObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).a();
    }
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((MediaSessionObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).stopObserving();
    }
    this.jdField_a_of_type_OrgChromiumBaseObserverList.clear();
    this.jdField_a_of_type_Long = 0L;
  }
  
  @CalledByNative
  private void mediaSessionMetadataChanged(MediaMetadata paramMediaMetadata)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((MediaSessionObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).a(paramMediaMetadata);
    }
  }
  
  @CalledByNative
  private void mediaSessionStateChanged(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((MediaSessionObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).a(paramBoolean1, paramBoolean2);
    }
  }
  
  private native void nativeDidReceiveAction(long paramLong, int paramInt);
  
  private static native MediaSessionImpl nativeGetMediaSessionFromWebContents(WebContents paramWebContents);
  
  private native void nativeResume(long paramLong);
  
  private native void nativeSeekBackward(long paramLong1, long paramLong2);
  
  private native void nativeSeekForward(long paramLong1, long paramLong2);
  
  private native void nativeStop(long paramLong);
  
  private native void nativeSuspend(long paramLong);
  
  public void addObserver(MediaSessionObserver paramMediaSessionObserver)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList.addObserver(paramMediaSessionObserver);
  }
  
  public void didReceiveAction(int paramInt)
  {
    nativeDidReceiveAction(this.jdField_a_of_type_Long, paramInt);
  }
  
  public ObserverList.RewindableIterator<MediaSessionObserver> getObserversForTesting()
  {
    return this.jdField_a_of_type_OrgChromiumBaseObserverList.rewindableIterator();
  }
  
  public void removeObserver(MediaSessionObserver paramMediaSessionObserver)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList.removeObserver(paramMediaSessionObserver);
  }
  
  public void resume()
  {
    nativeResume(this.jdField_a_of_type_Long);
  }
  
  public void seekBackward(long paramLong)
  {
    if ((!jdField_a_of_type_Boolean) && (paramLong < 0L)) {
      throw new AssertionError("Attempted to seek by a negative number of milliseconds");
    }
    nativeSeekBackward(this.jdField_a_of_type_Long, paramLong);
  }
  
  public void seekForward(long paramLong)
  {
    if ((!jdField_a_of_type_Boolean) && (paramLong < 0L)) {
      throw new AssertionError("Attempted to seek by a negative number of milliseconds");
    }
    nativeSeekForward(this.jdField_a_of_type_Long, paramLong);
  }
  
  public void stop()
  {
    nativeStop(this.jdField_a_of_type_Long);
  }
  
  public void suspend()
  {
    nativeSuspend(this.jdField_a_of_type_Long);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\MediaSessionImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */