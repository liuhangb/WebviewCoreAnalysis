package com.tencent.mtt.video.browser.export.player.ui;

import android.content.Context;
import com.tencent.mtt.video.browser.export.player.ILocalFileLister;
import com.tencent.mtt.video.browser.export.player.IPlayConfirmController;
import com.tencent.mtt.video.browser.export.player.IPlayerAccountController;
import com.tencent.mtt.video.browser.export.player.IPlayerDanmuHeadController;
import com.tencent.mtt.video.browser.export.player.IPlayerShareController;
import com.tencent.mtt.video.browser.export.player.OnPlayConfirmListener;
import java.util.ArrayList;

public abstract interface IVideoExtraAbilityControllerHolder
{
  public static final byte VIDEO_CONTROLLER_ID_CACHEVIDEO = 0;
  public static final byte VIDEO_CONTROLLER_ID_COLLECT = 1;
  public static final byte VIDEO_CONTROLLER_ID_DLNA = 3;
  public static final byte VIDEO_CONTROLLER_ID_EPISODE = 2;
  public static final byte VIDEO_CONTROLLER_ID_SHARE = 4;
  
  public abstract IAlertWndHinter getAlertWndHinter(IAlertSettingListener paramIAlertSettingListener);
  
  public abstract VideoMediaCacheAbilityController getCacheVideoController(IH5VideoMediaController paramIH5VideoMediaController);
  
  public abstract VideoMediaAbilityControllerBase getCollectController(IH5VideoMediaController paramIH5VideoMediaController);
  
  public abstract VideoMediaDlnaAbilityController getDLNAController(IH5VideoMediaController paramIH5VideoMediaController);
  
  public abstract VideoMediaAbilityControllerBase getEpisodeController(IH5VideoMediaController paramIH5VideoMediaController);
  
  public abstract IH5VideoErrorController getErrorController(IH5VideoMediaController paramIH5VideoMediaController);
  
  public abstract VideoMediaAbilityControllerBase getLiveController(IH5VideoMediaController paramIH5VideoMediaController);
  
  public abstract ILocalFileLister getLocalFileLister(String paramString);
  
  public abstract IMultiSrcSniffRequest getMultiSrcSniffRequest(IMultiSrcSniffListener paramIMultiSrcSniffListener);
  
  public abstract IPlayerAccountController getPlayerAccountController();
  
  public abstract IPlayConfirmController getPlayerConfirmController(OnPlayConfirmListener paramOnPlayConfirmListener);
  
  public abstract IPlayerDanmuHeadController getPlayerDanmuHeadController();
  
  public abstract IPlayerShareController getShareController(IH5VideoMediaController paramIH5VideoMediaController);
  
  public abstract IVideoDownloadProxy getVideoDownloadProxy();
  
  public static abstract interface IH5VideoErrorController
  {
    public abstract void onNotFoundVideoUrl(String paramString);
    
    public abstract void onSdcardNoSpace(boolean paramBoolean);
  }
  
  public static abstract class VideoMediaCacheAbilityController
    extends VideoMediaAbilityControllerBase
  {
    public VideoMediaCacheAbilityController(Context paramContext, IH5VideoMediaController paramIH5VideoMediaController)
    {
      super(paramIH5VideoMediaController);
    }
    
    public abstract void showDownloadController();
  }
  
  public static abstract class VideoMediaDlnaAbilityController
    extends VideoMediaAbilityControllerBase
  {
    public VideoMediaDlnaAbilityController(Context paramContext, IH5VideoMediaController paramIH5VideoMediaController)
    {
      super(paramIH5VideoMediaController);
    }
    
    public abstract void dlnaPlay(int paramInt1, int paramInt2);
    
    public abstract ArrayList<String> getDeviceList();
    
    public abstract boolean getSearchStartStatus();
    
    public abstract void removeDevice(int paramInt);
    
    public abstract void searchDlnaDevice();
    
    public abstract void setSearchStartStatus(boolean paramBoolean);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\player\ui\IVideoExtraAbilityControllerHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */