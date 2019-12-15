package org.chromium.tencent;

import android.graphics.Rect;
import java.util.List;
import org.chromium.tencent.media.MediaSourceBridge;
import org.chromium.tencent.media.TencentMediaPlayerBridge;
import org.chromium.tencent.video.IMediaPlayer;

public abstract interface ContentViewClientExtension
{
  public abstract IMediaPlayer createMediaPlayer(boolean paramBoolean, TencentMediaPlayerBridge paramTencentMediaPlayerBridge);
  
  public abstract IMediaPlayer createMediaSourcePlayer(MediaSourceBridge paramMediaSourceBridge);
  
  public abstract List<String> getAccessibilityBlackList();
  
  public abstract boolean getJavaScriptEnabled();
  
  public abstract boolean isEnableTbsAR();
  
  public abstract boolean isNightMode();
  
  public abstract boolean isWebViewActive();
  
  public abstract void onHide();
  
  public abstract void onScrollChanged();
  
  public abstract void onShow();
  
  public abstract void onShowImageBrowser(boolean paramBoolean);
  
  public abstract void onShowSelectPopup(Rect paramRect, String[] paramArrayOfString, int[] paramArrayOfInt1, boolean paramBoolean, int[] paramArrayOfInt2);
  
  public abstract void onSoftKeyBoardIsShowing(boolean paramBoolean);
  
  public abstract void onSoftKeyBoardShow();
  
  public abstract void onTopControlsChanged(float paramFloat);
  
  public abstract void onTopControlsChanged(float paramFloat1, float paramFloat2, float paramFloat3);
  
  public abstract boolean performContextMenuActionPaste();
  
  public abstract void reportDoFingerSearch();
  
  public abstract void unselect();
  
  public abstract void updateForceScaleStatus(float paramFloat1, float paramFloat2);
  
  public abstract void updateHitTestEditable(String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\ContentViewClientExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */