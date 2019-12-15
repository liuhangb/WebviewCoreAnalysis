package org.chromium.content.browser;

import org.chromium.base.ObserverList;
import org.chromium.base.ObserverList.RewindableIterator;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content.browser.webcontents.WebContentsImpl;
import org.chromium.content.browser.webcontents.WebContentsUserData;
import org.chromium.content.browser.webcontents.WebContentsUserData.UserDataFactory;
import org.chromium.content_public.browser.GestureListenerManager;
import org.chromium.content_public.browser.GestureStateListener;
import org.chromium.content_public.browser.WebContents;

@JNINamespace("content")
public class GestureListenerManagerImpl
  extends GestureListenerManager
  implements WindowEventObserver
{
  private int jdField_a_of_type_Int;
  private long jdField_a_of_type_Long;
  private final ObserverList.RewindableIterator<GestureStateListener> jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator;
  private final ObserverList<GestureStateListener> jdField_a_of_type_OrgChromiumBaseObserverList;
  private final WebContentsImpl jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl;
  
  public GestureListenerManagerImpl(WebContents paramWebContents)
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl = ((WebContentsImpl)paramWebContents);
    this.jdField_a_of_type_OrgChromiumBaseObserverList = new ObserverList();
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator = this.jdField_a_of_type_OrgChromiumBaseObserverList.rewindableIterator();
    this.jdField_a_of_type_Long = nativeInit(this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl);
  }
  
  private int a()
  {
    return this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl.getRenderCoordinates().getScrollYPixInt();
  }
  
  private int b()
  {
    return this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl.getRenderCoordinates().getLastFrameViewportHeightPixInt();
  }
  
  public static GestureListenerManagerImpl fromWebContents(WebContents paramWebContents)
  {
    return (GestureListenerManagerImpl)WebContentsUserData.fromWebContents(paramWebContents, GestureListenerManagerImpl.class, UserDataFactoryLazyHolder.a());
  }
  
  private native long nativeInit(WebContentsImpl paramWebContentsImpl);
  
  private native void nativeReset(long paramLong);
  
  @CalledByNative
  private void onDestroy()
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((GestureStateListener)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).onDestroyed();
    }
    this.jdField_a_of_type_OrgChromiumBaseObserverList.clear();
    this.jdField_a_of_type_Long = 0L;
  }
  
  @CalledByNative
  private void onDoubleTapEventAck()
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((GestureStateListener)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).onDoubleTapEventAck();
    }
  }
  
  @CalledByNative
  private void onFlingEnd()
  {
    int i = this.jdField_a_of_type_Int;
    if (i > 0) {
      this.jdField_a_of_type_Int = (i - 1);
    }
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((GestureStateListener)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).onFlingEndGesture(a(), b());
    }
  }
  
  @CalledByNative
  private void onFlingStartEventConsumed(int paramInt1, int paramInt2)
  {
    this.jdField_a_of_type_Int += 1;
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((GestureStateListener)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).onFlingStartGesture(paramInt1, paramInt2, a(), b());
    }
  }
  
  @CalledByNative
  private void onLongPressAck()
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((GestureStateListener)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).onLongPress();
    }
  }
  
  @CalledByNative
  private void onPinchBeginEventAck()
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((GestureStateListener)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).onPinchStarted();
    }
  }
  
  @CalledByNative
  private void onPinchEndEventAck()
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((GestureStateListener)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).onPinchEnded();
    }
  }
  
  @CalledByNative
  private void onScrollBeginEventAck()
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((GestureStateListener)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).onScrollStarted(a(), b());
    }
  }
  
  @CalledByNative
  private void onScrollEndEventAck()
  {
    updateOnScrollEnd();
  }
  
  @CalledByNative
  private void onScrollUpdateGestureConsumed()
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((GestureStateListener)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).onScrollUpdateGestureConsumed();
    }
  }
  
  @CalledByNative
  private void onSingleTapEventAck(boolean paramBoolean)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((GestureStateListener)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).onSingleTap(paramBoolean);
    }
  }
  
  public void addListener(GestureStateListener paramGestureStateListener)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList.addObserver(paramGestureStateListener);
  }
  
  public boolean hasPotentiallyActiveFling()
  {
    return this.jdField_a_of_type_Int > 0;
  }
  
  public void onAttachedToWindow() {}
  
  public void onDetachedFromWindow() {}
  
  public void onWindowFocusChanged(boolean paramBoolean)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((GestureStateListener)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).onWindowFocusChanged(paramBoolean);
    }
  }
  
  public void removeListener(GestureStateListener paramGestureStateListener)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList.removeObserver(paramGestureStateListener);
  }
  
  public void reset()
  {
    long l = this.jdField_a_of_type_Long;
    if (l != 0L) {
      nativeReset(l);
    }
  }
  
  public void resetFlingGesture()
  {
    if (this.jdField_a_of_type_Int > 0)
    {
      onFlingEnd();
      this.jdField_a_of_type_Int = 0;
    }
  }
  
  public void updateOnScaleLimitsChanged(float paramFloat1, float paramFloat2)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((GestureStateListener)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).onScaleLimitsChanged(paramFloat1, paramFloat2);
    }
  }
  
  public void updateOnScrollChanged(int paramInt1, int paramInt2)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((GestureStateListener)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).onScrollOffsetOrExtentChanged(paramInt1, paramInt2);
    }
  }
  
  public void updateOnScrollEnd()
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((GestureStateListener)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).onScrollEnded(a(), b());
    }
  }
  
  public void updateOnTouchDown()
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((GestureStateListener)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).onTouchDown();
    }
  }
  
  private static final class UserDataFactoryLazyHolder
  {
    private static final WebContentsUserData.UserDataFactory<GestureListenerManagerImpl> a = new WebContentsUserData.UserDataFactory()
    {
      public GestureListenerManagerImpl create(WebContents paramAnonymousWebContents)
      {
        return new GestureListenerManagerImpl(paramAnonymousWebContents);
      }
    };
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\GestureListenerManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */