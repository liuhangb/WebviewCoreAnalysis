package com.tencent.smtt.webkit.h5video;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import com.tencent.mtt.video.browser.export.player.ui.IVideoExtraAbilityControllerHolder;
import com.tencent.mtt.video.export.H5VideoInfo;
import com.tencent.mtt.video.export.IX5VideoPlayer;
import com.tencent.mtt.video.export.VideoProxyDefault;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.ui.e.a;

public class NullVideoPlayer
  implements IX5VideoPlayer
{
  private Context jdField_a_of_type_AndroidContentContext = null;
  private e.a jdField_a_of_type_ComTencentSmttWebkitUiE$a = null;
  
  public NullVideoPlayer(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
  }
  
  public void a(final String paramString)
  {
    Context localContext = this.jdField_a_of_type_AndroidContentContext;
    if (localContext != null)
    {
      if (this.jdField_a_of_type_ComTencentSmttWebkitUiE$a != null) {
        return;
      }
      try
      {
        this.jdField_a_of_type_ComTencentSmttWebkitUiE$a = new e.a(localContext, 2, false).a(SmttResource.getText("x5_create_video_player_failed", "string")).b(SmttResource.getText("x5_play_video_with_sys_app", "string"), new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            try
            {
              paramAnonymousDialogInterface = Uri.parse(paramString);
              Intent localIntent = new Intent("android.intent.action.VIEW");
              localIntent.setDataAndType(paramAnonymousDialogInterface, "video/*");
              NullVideoPlayer.a(NullVideoPlayer.this).startActivity(localIntent);
              NullVideoPlayer.a(NullVideoPlayer.this, null);
              return;
            }
            catch (Exception paramAnonymousDialogInterface)
            {
              for (;;) {}
            }
          }
        }).a(SmttResource.getText("x5_cancel", "string"), new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            NullVideoPlayer.a(NullVideoPlayer.this, null);
          }
        }).a(new DialogInterface.OnCancelListener()
        {
          public void onCancel(DialogInterface paramAnonymousDialogInterface)
          {
            NullVideoPlayer.a(NullVideoPlayer.this, null);
          }
        }).b();
        this.jdField_a_of_type_ComTencentSmttWebkitUiE$a.a();
        return;
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
        return;
      }
    }
  }
  
  public void active() {}
  
  public void deactive() {}
  
  public void doDestory() {}
  
  public void doExitPlay(boolean paramBoolean) {}
  
  public void fillVideoInfo(H5VideoInfo paramH5VideoInfo) {}
  
  public int getCurrentPosition()
  {
    return 0;
  }
  
  public String getData(int paramInt)
  {
    return null;
  }
  
  public int getDuration()
  {
    return 0;
  }
  
  public int getScreenMode()
  {
    return 0;
  }
  
  public int getVideoHeight()
  {
    return 0;
  }
  
  public String getVideoUrl()
  {
    return null;
  }
  
  public View getVideoView()
  {
    return null;
  }
  
  public int getVideoWidth()
  {
    return 0;
  }
  
  public boolean handlePluginTag(String paramString1, String paramString2, boolean paramBoolean, String paramString3)
  {
    return false;
  }
  
  public boolean isVideoPlaying()
  {
    return false;
  }
  
  public void onAppExit() {}
  
  public void onCustomViewHidden() {}
  
  public Object onMiscCallBack(String paramString, Bundle paramBundle)
  {
    return null;
  }
  
  public void onSurfaceTextureCreated(Object paramObject) {}
  
  public void onVideoSizeChanged(int paramInt1, int paramInt2) {}
  
  public void pause(int paramInt) {}
  
  public void play(H5VideoInfo paramH5VideoInfo, int paramInt)
  {
    a(paramH5VideoInfo.mVideoUrl);
  }
  
  public void preload(H5VideoInfo paramH5VideoInfo) {}
  
  public boolean renderRelease()
  {
    return false;
  }
  
  public void seek(int paramInt) {}
  
  public void setExtraAbilityHolder(IVideoExtraAbilityControllerHolder paramIVideoExtraAbilityControllerHolder) {}
  
  public void setPlaybackRate(double paramDouble) {}
  
  public void setVideoProxy(VideoProxyDefault paramVideoProxyDefault) {}
  
  public void setVolume(float paramFloat1, float paramFloat2) {}
  
  public void switchScreen(int paramInt) {}
  
  public void unmountProxy() {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\h5video\NullVideoPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */