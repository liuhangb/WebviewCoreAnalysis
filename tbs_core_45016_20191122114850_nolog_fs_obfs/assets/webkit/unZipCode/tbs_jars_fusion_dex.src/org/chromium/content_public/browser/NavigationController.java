package org.chromium.content_public.browser;

import org.chromium.base.VisibleForTesting;

public abstract interface NavigationController
{
  public abstract boolean canCopyStateOver();
  
  public abstract boolean canGoBack();
  
  public abstract boolean canGoForward();
  
  public abstract boolean canGoToOffset(int paramInt);
  
  public abstract boolean canPruneAllButLastCommitted();
  
  public abstract void cancelPendingReload();
  
  @VisibleForTesting
  public abstract void clearHistory();
  
  public abstract void clearSslPreferences();
  
  public abstract void continuePendingReload();
  
  public abstract void copyStateFrom(NavigationController paramNavigationController, boolean paramBoolean);
  
  public abstract void copyStateFromAndPrune(NavigationController paramNavigationController, boolean paramBoolean);
  
  public abstract int getCurrentEntryIndex();
  
  public abstract NavigationHistory getDirectedNavigationHistory(boolean paramBoolean, int paramInt);
  
  @VisibleForTesting
  public abstract NavigationEntry getEntryAtIndex(int paramInt);
  
  public abstract String getEntryExtraData(int paramInt, String paramString);
  
  public abstract int getLastCommittedEntryIndex();
  
  public abstract NavigationHistory getNavigationHistory();
  
  public abstract String getOriginalUrlForVisibleNavigationEntry();
  
  public abstract NavigationEntry getPendingEntry();
  
  public abstract boolean getUseDesktopUserAgent();
  
  public abstract void goBack();
  
  public abstract void goForward();
  
  public abstract void goToNavigationIndex(int paramInt);
  
  public abstract void goToOffset(int paramInt);
  
  public abstract boolean isInitialNavigation();
  
  public abstract void loadIfNecessary();
  
  public abstract void loadUrl(LoadUrlParams paramLoadUrlParams);
  
  public abstract void reload(boolean paramBoolean);
  
  public abstract void reloadBypassingCache(boolean paramBoolean);
  
  public abstract boolean removeEntryAtIndex(int paramInt);
  
  public abstract void requestRestoreLoad();
  
  public abstract void setEntryExtraData(int paramInt, String paramString1, String paramString2);
  
  public abstract void setUseDesktopUserAgent(boolean paramBoolean1, boolean paramBoolean2);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\NavigationController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */