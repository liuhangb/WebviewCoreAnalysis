package org.chromium.content.browser.framehost;

import android.graphics.Bitmap;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content_public.browser.LoadUrlParams;
import org.chromium.content_public.browser.NavigationController;
import org.chromium.content_public.browser.NavigationEntry;
import org.chromium.content_public.browser.NavigationHistory;
import org.chromium.content_public.common.Referrer;
import org.chromium.content_public.common.ResourceRequestBody;

@JNINamespace("content")
class NavigationControllerImpl
  implements NavigationController
{
  private long a;
  
  private NavigationControllerImpl(long paramLong)
  {
    this.a = paramLong;
  }
  
  @CalledByNative
  private static void addToNavigationHistory(Object paramObject1, Object paramObject2)
  {
    ((NavigationHistory)paramObject1).addEntry((NavigationEntry)paramObject2);
  }
  
  @CalledByNative
  private static NavigationControllerImpl create(long paramLong)
  {
    return new NavigationControllerImpl(paramLong);
  }
  
  @CalledByNative
  private static NavigationEntry createNavigationEntry(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, Bitmap paramBitmap, int paramInt3)
  {
    return new NavigationEntry(paramInt1, paramInt2, paramString1, paramString2, paramString3, paramString4, paramBitmap, paramInt3);
  }
  
  @CalledByNative
  private void destroy()
  {
    this.a = 0L;
  }
  
  private native boolean nativeCanCopyStateOver(long paramLong);
  
  private native boolean nativeCanGoBack(long paramLong);
  
  private native boolean nativeCanGoForward(long paramLong);
  
  private native boolean nativeCanGoToOffset(long paramLong, int paramInt);
  
  private native boolean nativeCanPruneAllButLastCommitted(long paramLong);
  
  private native void nativeCancelPendingReload(long paramLong);
  
  private native void nativeClearHistory(long paramLong);
  
  private native void nativeClearSslPreferences(long paramLong);
  
  private native void nativeContinuePendingReload(long paramLong);
  
  private native void nativeCopyStateFrom(long paramLong1, long paramLong2, boolean paramBoolean);
  
  private native void nativeCopyStateFromAndPrune(long paramLong1, long paramLong2, boolean paramBoolean);
  
  private native int nativeGetCurrentEntryIndex(long paramLong);
  
  private native void nativeGetDirectedNavigationHistory(long paramLong, NavigationHistory paramNavigationHistory, boolean paramBoolean, int paramInt);
  
  private native NavigationEntry nativeGetEntryAtIndex(long paramLong, int paramInt);
  
  private native String nativeGetEntryExtraData(long paramLong, int paramInt, String paramString);
  
  private native int nativeGetLastCommittedEntryIndex(long paramLong);
  
  private native int nativeGetNavigationHistory(long paramLong, Object paramObject);
  
  private native String nativeGetOriginalUrlForVisibleNavigationEntry(long paramLong);
  
  private native NavigationEntry nativeGetPendingEntry(long paramLong);
  
  private native boolean nativeGetUseDesktopUserAgent(long paramLong);
  
  private native void nativeGoBack(long paramLong);
  
  private native void nativeGoForward(long paramLong);
  
  private native void nativeGoToNavigationIndex(long paramLong, int paramInt);
  
  private native void nativeGoToOffset(long paramLong, int paramInt);
  
  private native boolean nativeIsInitialNavigation(long paramLong);
  
  private native void nativeLoadIfNecessary(long paramLong);
  
  private native void nativeLoadUrl(long paramLong, String paramString1, int paramInt1, int paramInt2, String paramString2, int paramInt3, int paramInt4, String paramString3, ResourceRequestBody paramResourceRequestBody, String paramString4, String paramString5, String paramString6, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
  
  private native void nativeReload(long paramLong, boolean paramBoolean);
  
  private native void nativeReloadBypassingCache(long paramLong, boolean paramBoolean);
  
  private native boolean nativeRemoveEntryAtIndex(long paramLong, int paramInt);
  
  private native void nativeRequestRestoreLoad(long paramLong);
  
  private native void nativeSetEntryExtraData(long paramLong, int paramInt, String paramString1, String paramString2);
  
  private native void nativeSetUseDesktopUserAgent(long paramLong, boolean paramBoolean1, boolean paramBoolean2);
  
  public boolean canCopyStateOver()
  {
    long l = this.a;
    return (l != 0L) && (nativeCanCopyStateOver(l));
  }
  
  public boolean canGoBack()
  {
    long l = this.a;
    return (l != 0L) && (nativeCanGoBack(l));
  }
  
  public boolean canGoForward()
  {
    long l = this.a;
    return (l != 0L) && (nativeCanGoForward(l));
  }
  
  @VisibleForTesting
  public boolean canGoToOffset(int paramInt)
  {
    long l = this.a;
    return (l != 0L) && (nativeCanGoToOffset(l, paramInt));
  }
  
  public boolean canPruneAllButLastCommitted()
  {
    long l = this.a;
    return (l != 0L) && (nativeCanPruneAllButLastCommitted(l));
  }
  
  public void cancelPendingReload()
  {
    long l = this.a;
    if (l != 0L) {
      nativeCancelPendingReload(l);
    }
  }
  
  @VisibleForTesting
  public void clearHistory()
  {
    long l = this.a;
    if (l != 0L) {
      nativeClearHistory(l);
    }
  }
  
  public void clearSslPreferences()
  {
    long l = this.a;
    if (l != 0L) {
      nativeClearSslPreferences(l);
    }
  }
  
  public void continuePendingReload()
  {
    long l = this.a;
    if (l != 0L) {
      nativeContinuePendingReload(l);
    }
  }
  
  public void copyStateFrom(NavigationController paramNavigationController, boolean paramBoolean)
  {
    long l1 = this.a;
    if (l1 == 0L) {
      return;
    }
    long l2 = ((NavigationControllerImpl)paramNavigationController).a;
    if (l2 == 0L) {
      return;
    }
    nativeCopyStateFrom(l1, l2, paramBoolean);
  }
  
  public void copyStateFromAndPrune(NavigationController paramNavigationController, boolean paramBoolean)
  {
    long l1 = this.a;
    if (l1 == 0L) {
      return;
    }
    long l2 = ((NavigationControllerImpl)paramNavigationController).a;
    if (l2 == 0L) {
      return;
    }
    nativeCopyStateFromAndPrune(l1, l2, paramBoolean);
  }
  
  public int getCurrentEntryIndex()
  {
    long l = this.a;
    if (l != 0L) {
      return nativeGetCurrentEntryIndex(l);
    }
    return -1;
  }
  
  public NavigationHistory getDirectedNavigationHistory(boolean paramBoolean, int paramInt)
  {
    if (this.a == 0L) {
      return null;
    }
    NavigationHistory localNavigationHistory = new NavigationHistory();
    nativeGetDirectedNavigationHistory(this.a, localNavigationHistory, paramBoolean, paramInt);
    return localNavigationHistory;
  }
  
  public NavigationEntry getEntryAtIndex(int paramInt)
  {
    long l = this.a;
    if (l != 0L) {
      return nativeGetEntryAtIndex(l, paramInt);
    }
    return null;
  }
  
  public String getEntryExtraData(int paramInt, String paramString)
  {
    long l = this.a;
    if (l == 0L) {
      return null;
    }
    return nativeGetEntryExtraData(l, paramInt, paramString);
  }
  
  public int getLastCommittedEntryIndex()
  {
    long l = this.a;
    if (l != 0L) {
      return nativeGetLastCommittedEntryIndex(l);
    }
    return -1;
  }
  
  public NavigationHistory getNavigationHistory()
  {
    if (this.a == 0L) {
      return null;
    }
    NavigationHistory localNavigationHistory = new NavigationHistory();
    try
    {
      localNavigationHistory.setCurrentEntryIndex(nativeGetNavigationHistory(this.a, localNavigationHistory));
      return localNavigationHistory;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return localNavigationHistory;
    }
    catch (Error localError)
    {
      localError.printStackTrace();
    }
    return localNavigationHistory;
  }
  
  public String getOriginalUrlForVisibleNavigationEntry()
  {
    long l = this.a;
    if (l == 0L) {
      return null;
    }
    return nativeGetOriginalUrlForVisibleNavigationEntry(l);
  }
  
  public NavigationEntry getPendingEntry()
  {
    long l = this.a;
    if (l != 0L) {
      return nativeGetPendingEntry(l);
    }
    return null;
  }
  
  public boolean getUseDesktopUserAgent()
  {
    long l = this.a;
    if (l == 0L) {
      return false;
    }
    return nativeGetUseDesktopUserAgent(l);
  }
  
  public void goBack()
  {
    long l = this.a;
    if (l != 0L) {
      nativeGoBack(l);
    }
  }
  
  public void goForward()
  {
    long l = this.a;
    if (l != 0L) {
      nativeGoForward(l);
    }
  }
  
  public void goToNavigationIndex(int paramInt)
  {
    long l = this.a;
    if (l != 0L) {
      nativeGoToNavigationIndex(l, paramInt);
    }
  }
  
  public void goToOffset(int paramInt)
  {
    long l = this.a;
    if (l != 0L) {
      nativeGoToOffset(l, paramInt);
    }
  }
  
  public boolean isInitialNavigation()
  {
    long l = this.a;
    return (l != 0L) && (nativeIsInitialNavigation(l));
  }
  
  public void loadIfNecessary()
  {
    long l = this.a;
    if (l != 0L) {
      nativeLoadIfNecessary(l);
    }
  }
  
  public void loadUrl(LoadUrlParams paramLoadUrlParams)
  {
    long l = this.a;
    if (l != 0L)
    {
      String str2 = paramLoadUrlParams.a();
      int j = paramLoadUrlParams.c();
      int k = paramLoadUrlParams.a();
      String str1;
      if (paramLoadUrlParams.a() != null) {
        str1 = paramLoadUrlParams.a().a();
      } else {
        str1 = null;
      }
      int i;
      if (paramLoadUrlParams.a() != null) {
        i = paramLoadUrlParams.a().a();
      } else {
        i = 0;
      }
      nativeLoadUrl(l, str2, j, k, str1, i, paramLoadUrlParams.b(), paramLoadUrlParams.c(), paramLoadUrlParams.a(), paramLoadUrlParams.b(), paramLoadUrlParams.e(), paramLoadUrlParams.f(), paramLoadUrlParams.a(), paramLoadUrlParams.b(), paramLoadUrlParams.c());
    }
  }
  
  public void reload(boolean paramBoolean)
  {
    long l = this.a;
    if (l != 0L) {
      nativeReload(l, paramBoolean);
    }
  }
  
  public void reloadBypassingCache(boolean paramBoolean)
  {
    long l = this.a;
    if (l != 0L) {
      nativeReloadBypassingCache(l, paramBoolean);
    }
  }
  
  public boolean removeEntryAtIndex(int paramInt)
  {
    long l = this.a;
    if (l != 0L) {
      return nativeRemoveEntryAtIndex(l, paramInt);
    }
    return false;
  }
  
  public void requestRestoreLoad()
  {
    long l = this.a;
    if (l != 0L) {
      nativeRequestRestoreLoad(l);
    }
  }
  
  public void setEntryExtraData(int paramInt, String paramString1, String paramString2)
  {
    long l = this.a;
    if (l == 0L) {
      return;
    }
    nativeSetEntryExtraData(l, paramInt, paramString1, paramString2);
  }
  
  public void setUseDesktopUserAgent(boolean paramBoolean1, boolean paramBoolean2)
  {
    long l = this.a;
    if (l != 0L) {
      nativeSetUseDesktopUserAgent(l, paramBoolean1, paramBoolean2);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\framehost\NavigationControllerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */