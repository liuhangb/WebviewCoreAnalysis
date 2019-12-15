package com.tencent.smtt.webkit;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ValueCallback;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebChromeClientExtension;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.interfaces.ISelectionInterface;
import com.tencent.smtt.export.external.interfaces.IX5WebBackForwardListClient;
import com.tencent.smtt.export.external.interfaces.IX5WebHistoryItem;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.ImageInfo;
import com.tencent.smtt.export.internal.interfaces.DownloadListenerExtension;
import com.tencent.smtt.export.internal.interfaces.IX5QQBrowserClient;
import com.tencent.smtt.export.internal.interfaces.IX5WebView.TRANSLATE_STATE;
import com.tencent.smtt.jsApi.export.OpenJsApiBridge;
import com.tencent.smtt.net.X5BadJsReporter.a;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract interface WebViewProviderExtension
{
  public abstract void active();
  
  public abstract void cancelFling(long paramLong);
  
  public abstract Picture capturePicture();
  
  public abstract void changeOverScrollState(int paramInt, boolean paramBoolean);
  
  public abstract void clearServiceWorkerCache();
  
  public abstract void clearTextEntry();
  
  public abstract int contentToViewDimension(int paramInt);
  
  public abstract int contentToViewX(int paramInt);
  
  public abstract int contentToViewY(int paramInt);
  
  public abstract void copySelection();
  
  public abstract void cutSelection();
  
  public abstract void deactive();
  
  public abstract int detectTranslateWebSiteIsNeeded(String paramString, boolean paramBoolean);
  
  public abstract void discardCurrentHiddenPage();
  
  public abstract void doFingerSearchIfNeed();
  
  public abstract void doTranslateAction(int paramInt);
  
  public abstract boolean drawPreReadBaseLayer(Canvas paramCanvas, boolean paramBoolean);
  
  public abstract void enterFullScreen(View paramView);
  
  public abstract void enterSelectionMode(boolean paramBoolean);
  
  public abstract void enterSelectionModeWaitFS(boolean paramBoolean);
  
  public abstract void evaluateJavaScriptInFrame(String paramString1, String paramString2);
  
  public abstract void evaluateJavaScriptInSubFrame(String paramString);
  
  public abstract void executeCopyItem();
  
  public abstract void executeSearchItem();
  
  public abstract void exitFullScreen();
  
  public abstract void exitPluginFullScreen();
  
  public abstract void exitX5LongClickPopMenu();
  
  public abstract void focusAndPopupIM(String paramString);
  
  public abstract void focusTtsNode(int paramInt, boolean paramBoolean);
  
  public abstract void fullScreenPluginHidden();
  
  public abstract int getAddressbarDisplayMode();
  
  public abstract ArrayList<IX5WebViewBase.ImageInfo> getAllImageInfo();
  
  public abstract Bitmap getBitmapByIndex(int paramInt);
  
  public abstract float getCurrScrollVelocity();
  
  public abstract int getCurrentEntryIndex();
  
  public abstract int getCurrentHistoryItemIndex();
  
  public abstract String getDocumentOuterHTML();
  
  public abstract void getFakeLoginStatus(Bundle paramBundle, ValueCallback<Bundle> paramValueCallback);
  
  public abstract int getGoBackOrForwardToDesiredSteps(int paramInt);
  
  public abstract IX5WebHistoryItem getHistoryItem(int paramInt);
  
  public abstract String getNavigateUrl();
  
  public abstract OpenJsApiBridge getOpenJsApiBridge();
  
  public abstract boolean getOverScrollState(int paramInt);
  
  public abstract String[] getPasswordFromDatabase(String paramString);
  
  public abstract IX5QQBrowserClient getQQBrowserClient();
  
  public abstract int getRoutingID();
  
  public abstract String getSelection();
  
  public abstract WebSettingsExtension getSettingsExtension();
  
  public abstract IX5WebView.TRANSLATE_STATE getTranslateState(String paramString);
  
  public abstract void getTtsTextAsync(int paramInt);
  
  public abstract List<String> getUserSelectedHiddenDomains();
  
  public abstract IX5WebChromeClientExtension getWebChromeClientExtension();
  
  public abstract IX5WebViewClientExtension getWebViewClientExtension();
  
  public abstract void goBackOrForward(int paramInt);
  
  public abstract void gotoPreRead();
  
  public abstract boolean hasWebAR();
  
  public abstract void hideUserSelectedElement(String paramString);
  
  public abstract void injectJavascript(String paramString);
  
  public abstract boolean isHandleViewDragging();
  
  public abstract boolean isPluginFullScreen();
  
  public abstract boolean isPreReadCanGoForward();
  
  public abstract void leaveSelectionMode();
  
  public abstract boolean needSniff();
  
  public abstract void notifyADWebviewVisiableHeight(int paramInt);
  
  public abstract void notifyMemoryPressure(int paramInt);
  
  public abstract void notifyMiniQbStatus(String paramString1, String paramString2);
  
  public abstract void onAppExit();
  
  public abstract void onFingerSearchResult(String paramString, int paramInt1, int paramInt2);
  
  public abstract void onPageFinished(String paramString);
  
  public abstract void onPageStarted(String paramString);
  
  public abstract void onTouchEvent(MotionEvent paramMotionEvent);
  
  public abstract void pasteFromClipboard(CharSequence paramCharSequence);
  
  public abstract void pauseAudio();
  
  public abstract void playAudio();
  
  public abstract void preConnect(String paramString, int paramInt);
  
  public abstract void preLoad(String paramString, int paramInt1, int paramInt2, Map<String, String> paramMap);
  
  public abstract void pruneMemoryCache();
  
  public abstract void registerServiceWorkerBackground(String paramString1, String paramString2);
  
  public abstract void reload();
  
  public abstract void reloadCustomMetaData();
  
  public abstract void removeHistoryItem(int paramInt);
  
  public abstract void removeUserSelectedAdInfoByDomain(String paramString);
  
  public abstract void replaceAllInputText(String paramString);
  
  public abstract void replyListBox(int paramInt);
  
  public abstract void replyMultiListBox(int paramInt, boolean[] paramArrayOfBoolean);
  
  public abstract void reportBadJsMsg(X5BadJsReporter.a parama);
  
  public abstract void reportShowLongClickPopupMenu();
  
  public abstract void requestExplorerStatistics();
  
  public abstract boolean requestFocusForInputNode(int paramInt);
  
  public abstract void retrieveFingerSearchContext(int paramInt);
  
  public abstract int selectionType();
  
  public abstract void sendRememberMsg(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
  
  public abstract void setAddressbarDisplayMode(int paramInt, boolean paramBoolean);
  
  public abstract void setAddressbarDisplayMode(int paramInt, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void setAudioAutoPlayNotify(boolean paramBoolean);
  
  public abstract void setBackFromSystem();
  
  public abstract void setContentCacheCurrentFrameWhenJsLocation(boolean paramBoolean);
  
  public abstract void setDownLoadListenerExtension(DownloadListenerExtension paramDownloadListenerExtension);
  
  public abstract void setEnableAutoPageDiscarding(boolean paramBoolean);
  
  public abstract void setEnableAutoPageRestoration(boolean paramBoolean);
  
  public abstract void setEntryDataForSearchTeam(String paramString);
  
  public abstract void setEyeShieldMode(boolean paramBoolean, int paramInt);
  
  public abstract void setFakeLoginParams(Bundle paramBundle);
  
  public abstract void setForceEnableZoom(boolean paramBoolean);
  
  public abstract void setHandleViewBitmap(Bitmap paramBitmap1, Bitmap paramBitmap2, int paramInt1, int paramInt2);
  
  public abstract void setHandleViewLineColor(int paramInt1, int paramInt2);
  
  public abstract void setHandleViewLineIsShowing(boolean paramBoolean, int paramInt);
  
  public abstract void setHandleViewSelectionColor(int paramInt1, int paramInt2);
  
  public abstract void setOverScrollParams(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3);
  
  public abstract void setQQBrowserClient(IX5QQBrowserClient paramIX5QQBrowserClient);
  
  public abstract void setRenderMode(int paramInt);
  
  public abstract void setScreenScrollableForHandleView(boolean paramBoolean);
  
  public abstract void setSelectListener(ISelectionInterface paramISelectionInterface);
  
  public abstract void setSiteSecurityInfo(String paramString1, String paramString2);
  
  public abstract void setSkvDataForSearchTeam(String paramString);
  
  public abstract boolean setTranslateInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
  
  public abstract void setWebBackForwardListClient(IX5WebBackForwardListClient paramIX5WebBackForwardListClient);
  
  public abstract void setWebChromeClientExtension(IX5WebChromeClientExtension paramIX5WebChromeClientExtension);
  
  public abstract void setWebViewClientExtension(IX5WebViewClientExtension paramIX5WebViewClientExtension);
  
  public abstract boolean shouldPopupHideAdDialog();
  
  public abstract void showImage(int paramInt1, int paramInt2);
  
  public abstract void showInputMethodExtBar(boolean paramBoolean, int paramInt);
  
  public abstract Drawable snapshot(int paramInt, boolean paramBoolean);
  
  public abstract void snapshotVisible(Canvas paramCanvas, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
  
  public abstract void snapshotVisibleWithBitmap(Bitmap paramBitmap, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, float paramFloat1, float paramFloat2);
  
  public abstract void snapshotVisibleWithBitmap(Bitmap paramBitmap, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, float paramFloat1, float paramFloat2, Runnable paramRunnable);
  
  public abstract void snapshotVisibleWithBitmapThreaded(Bitmap paramBitmap, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, float paramFloat1, float paramFloat2, int paramInt);
  
  public abstract void snapshotWholePage(Canvas paramCanvas, boolean paramBoolean);
  
  public abstract void snapshotWholePage(Canvas paramCanvas, boolean paramBoolean, Runnable paramRunnable);
  
  public abstract void startMainThreadBlockedDetect();
  
  public abstract void stopMainThreadBlockedDetect();
  
  public abstract void stopPreLoad(String paramString);
  
  public abstract void suspendPageScroll(boolean paramBoolean);
  
  public abstract void translateWord(String paramString);
  
  public abstract void trimMemory(int paramInt);
  
  public abstract void updateImageList(int paramInt1, int paramInt2, boolean paramBoolean);
  
  public abstract void updateImageList2(int paramInt1, int paramInt2, boolean paramBoolean, ValueCallback<Integer> paramValueCallback);
  
  public abstract void updateServiceWorkerBackground(String paramString);
  
  public abstract void uploadX5CoreLiveLog(String paramString1, String paramString2);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\WebViewProviderExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */