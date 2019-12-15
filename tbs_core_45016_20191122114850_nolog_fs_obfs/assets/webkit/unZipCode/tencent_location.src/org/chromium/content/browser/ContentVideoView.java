package org.chromium.content.browser;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Point;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import org.chromium.base.Log;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content_public.browser.ContentViewCore;
import org.chromium.tencent.SharedResource;
import org.chromium.ui.base.WindowAndroid;

@JNINamespace("content")
public class ContentVideoView
  extends FrameLayout
  implements SurfaceHolder.Callback
{
  public static final int MEDIA_ERROR_INVALID_CODE = 3;
  public static final int MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK = 2;
  private static final ContentVideoViewEmbedder jdField_b_of_type_OrgChromiumContentBrowserContentVideoViewEmbedder = new ContentVideoViewEmbedder()
  {
    public void enterFullscreenVideo(View paramAnonymousView, boolean paramAnonymousBoolean) {}
    
    public void exitFullscreenVideo() {}
    
    public void fullscreenVideoLoaded() {}
    
    public void setSystemUiVisibility(boolean paramAnonymousBoolean) {}
  };
  private int jdField_a_of_type_Int;
  private long jdField_a_of_type_Long;
  private SurfaceHolder jdField_a_of_type_AndroidViewSurfaceHolder;
  private final Runnable jdField_a_of_type_JavaLangRunnable = new Runnable()
  {
    public void run()
    {
      ContentVideoView.this.exitFullscreen(true);
    }
  };
  private String jdField_a_of_type_JavaLangString;
  private VideoSurfaceView jdField_a_of_type_OrgChromiumContentBrowserContentVideoView$VideoSurfaceView;
  private final ContentVideoViewEmbedder jdField_a_of_type_OrgChromiumContentBrowserContentVideoViewEmbedder;
  private boolean jdField_a_of_type_Boolean;
  private int jdField_b_of_type_Int;
  private long jdField_b_of_type_Long;
  private String jdField_b_of_type_JavaLangString;
  private boolean jdField_b_of_type_Boolean;
  private int jdField_c_of_type_Int = 0;
  private long jdField_c_of_type_Long;
  private String jdField_c_of_type_JavaLangString;
  private boolean jdField_c_of_type_Boolean;
  private String jdField_d_of_type_JavaLangString;
  private boolean jdField_d_of_type_Boolean;
  
  private ContentVideoView(Context paramContext, long paramLong, ContentVideoViewEmbedder paramContentVideoViewEmbedder, int paramInt1, int paramInt2)
  {
    super(paramContext);
    this.jdField_a_of_type_Long = paramLong;
    if (paramContentVideoViewEmbedder == null) {
      paramContentVideoViewEmbedder = jdField_b_of_type_OrgChromiumContentBrowserContentVideoViewEmbedder;
    }
    this.jdField_a_of_type_OrgChromiumContentBrowserContentVideoViewEmbedder = paramContentVideoViewEmbedder;
    this.jdField_d_of_type_Boolean = false;
    this.jdField_c_of_type_Boolean = false;
    boolean bool;
    if ((paramInt1 > 0) && (paramInt2 > 0)) {
      bool = true;
    } else {
      bool = false;
    }
    this.jdField_a_of_type_Boolean = bool;
    a(paramContext);
    this.jdField_a_of_type_OrgChromiumContentBrowserContentVideoView$VideoSurfaceView = new VideoSurfaceView(paramContext);
    a();
    setVisibility(0);
    this.jdField_a_of_type_OrgChromiumContentBrowserContentVideoViewEmbedder.enterFullscreenVideo(this, this.jdField_a_of_type_Boolean);
    onVideoSizeChanged(paramInt1, paramInt2);
  }
  
  private void a()
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserContentVideoView$VideoSurfaceView.getHolder().addCallback(this);
    addView(this.jdField_a_of_type_OrgChromiumContentBrowserContentVideoView$VideoSurfaceView, new FrameLayout.LayoutParams(-2, -2, 17));
  }
  
  private void a(Context paramContext)
  {
    if (this.jdField_a_of_type_JavaLangString != null) {
      return;
    }
    try
    {
      this.jdField_a_of_type_JavaLangString = paramContext.getString(SharedResource.loadIdentifierResource("x5_media_player_error_text_invalid_progressive_playback", "string"));
      this.jdField_b_of_type_JavaLangString = paramContext.getString(SharedResource.loadIdentifierResource("x5_media_player_error_text_unknown", "string"));
      this.jdField_c_of_type_JavaLangString = paramContext.getString(SharedResource.loadIdentifierResource("x5_media_player_error_button", "string"));
      this.jdField_d_of_type_JavaLangString = paramContext.getString(SharedResource.loadIdentifierResource("x5_media_player_error_title", "string"));
      return;
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    this.jdField_a_of_type_JavaLangString = "";
    this.jdField_b_of_type_JavaLangString = "";
    this.jdField_c_of_type_JavaLangString = "";
    this.jdField_d_of_type_JavaLangString = "";
  }
  
  private boolean a()
  {
    Display localDisplay = ((WindowManager)getContext().getSystemService("window")).getDefaultDisplay();
    boolean bool = false;
    Point localPoint = new Point(0, 0);
    localDisplay.getSize(localPoint);
    if (localPoint.x <= localPoint.y) {
      bool = true;
    }
    return bool;
  }
  
  private void b()
  {
    removeView(this.jdField_a_of_type_OrgChromiumContentBrowserContentVideoView$VideoSurfaceView);
    this.jdField_a_of_type_OrgChromiumContentBrowserContentVideoView$VideoSurfaceView = null;
  }
  
  @CalledByNative
  private static ContentVideoView createContentVideoView(ContentViewCore paramContentViewCore, ContentVideoViewEmbedder paramContentVideoViewEmbedder, long paramLong, int paramInt1, int paramInt2)
  {
    ThreadUtils.assertOnUiThread();
    return new ContentVideoView(paramContentViewCore.getContext(), paramLong, paramContentVideoViewEmbedder, paramInt1, paramInt2);
  }
  
  @CalledByNative
  private void destroyContentVideoView(boolean paramBoolean)
  {
    if (this.jdField_a_of_type_OrgChromiumContentBrowserContentVideoView$VideoSurfaceView != null)
    {
      b();
      setVisibility(8);
      this.jdField_a_of_type_OrgChromiumContentBrowserContentVideoViewEmbedder.exitFullscreenVideo();
    }
    if (paramBoolean) {
      this.jdField_a_of_type_Long = 0L;
    }
  }
  
  public static ContentVideoView getContentVideoView()
  {
    return nativeGetSingletonJavaContentVideoView();
  }
  
  private native void nativeDidExitFullscreen(long paramLong, boolean paramBoolean);
  
  private static native ContentVideoView nativeGetSingletonJavaContentVideoView();
  
  private native void nativeRecordExitFullscreenPlayback(long paramLong1, boolean paramBoolean, long paramLong2, long paramLong3);
  
  private native void nativeRecordFullscreenPlayback(long paramLong, boolean paramBoolean1, boolean paramBoolean2);
  
  private native void nativeSetSurface(long paramLong, Surface paramSurface);
  
  @CalledByNative
  private void onVideoSizeChanged(int paramInt1, int paramInt2)
  {
    this.jdField_a_of_type_Int = paramInt1;
    this.jdField_b_of_type_Int = paramInt2;
    boolean bool2 = this.jdField_a_of_type_Boolean;
    boolean bool1 = true;
    if ((!bool2) && (this.jdField_a_of_type_Int > 0) && (this.jdField_b_of_type_Int > 0))
    {
      this.jdField_a_of_type_Boolean = true;
      this.jdField_a_of_type_OrgChromiumContentBrowserContentVideoViewEmbedder.fullscreenVideoLoaded();
    }
    this.jdField_a_of_type_OrgChromiumContentBrowserContentVideoView$VideoSurfaceView.getHolder().setFixedSize(this.jdField_a_of_type_Int, this.jdField_b_of_type_Int);
    if (this.jdField_d_of_type_Boolean) {
      return;
    }
    try
    {
      paramInt1 = Settings.System.getInt(getContext().getContentResolver(), "accelerometer_rotation");
      if (paramInt1 == 0) {
        return;
      }
      this.jdField_b_of_type_Boolean = a();
      this.jdField_d_of_type_Boolean = true;
      this.jdField_c_of_type_Long = System.currentTimeMillis();
      this.jdField_b_of_type_Long = this.jdField_c_of_type_Long;
      long l = this.jdField_a_of_type_Long;
      if (this.jdField_b_of_type_Int <= this.jdField_a_of_type_Int) {
        bool1 = false;
      }
      nativeRecordFullscreenPlayback(l, bool1, this.jdField_b_of_type_Boolean);
      return;
    }
    catch (Settings.SettingNotFoundException localSettingNotFoundException) {}
  }
  
  @CalledByNative
  private void openVideo()
  {
    SurfaceHolder localSurfaceHolder = this.jdField_a_of_type_AndroidViewSurfaceHolder;
    if (localSurfaceHolder != null)
    {
      this.jdField_c_of_type_Int = 0;
      long l = this.jdField_a_of_type_Long;
      if (l != 0L) {
        nativeSetSurface(l, localSurfaceHolder.getSurface());
      }
    }
  }
  
  @CalledByNative
  public void exitFullscreen(boolean paramBoolean)
  {
    if (this.jdField_a_of_type_Long != 0L)
    {
      destroyContentVideoView(false);
      if ((this.jdField_d_of_type_Boolean) && (!this.jdField_c_of_type_Boolean))
      {
        long l1 = System.currentTimeMillis();
        long l2 = this.jdField_b_of_type_Long;
        long l3 = l2 - this.jdField_c_of_type_Long;
        l1 -= l2;
        if (l3 == 0L)
        {
          l2 = 0L;
        }
        else
        {
          l2 = l1;
          l1 = l3;
        }
        nativeRecordExitFullscreenPlayback(this.jdField_a_of_type_Long, this.jdField_b_of_type_Boolean, l1, l2);
      }
      nativeDidExitFullscreen(this.jdField_a_of_type_Long, paramBoolean);
      this.jdField_a_of_type_Long = 0L;
    }
  }
  
  public void onFullscreenWindowFocused()
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserContentVideoViewEmbedder.setSystemUiVisibility(true);
  }
  
  @CalledByNative
  public void onMediaPlayerError(int paramInt)
  {
    if (this.jdField_c_of_type_Int == -1) {
      return;
    }
    if (paramInt == 3) {
      return;
    }
    this.jdField_c_of_type_Int = -1;
    if (WindowAndroid.activityFromContext(getContext()) == null) {
      return;
    }
    if (getWindowToken() != null)
    {
      String str;
      if (paramInt == 2) {
        str = this.jdField_a_of_type_JavaLangString;
      } else {
        str = this.jdField_b_of_type_JavaLangString;
      }
      try
      {
        new AlertDialog.Builder(getContext()).setTitle(this.jdField_d_of_type_JavaLangString).setMessage(str).setPositiveButton(this.jdField_c_of_type_JavaLangString, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {}
        }).setCancelable(false).show();
        return;
      }
      catch (RuntimeException localRuntimeException)
      {
        Log.e("cr.ContentVideoView", "Cannot show the alert dialog, error message: %s", new Object[] { str, localRuntimeException });
      }
    }
  }
  
  public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3) {}
  
  public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
  {
    this.jdField_a_of_type_AndroidViewSurfaceHolder = paramSurfaceHolder;
    openVideo();
  }
  
  public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
  {
    long l = this.jdField_a_of_type_Long;
    if (l != 0L) {
      nativeSetSurface(l, null);
    }
    this.jdField_a_of_type_AndroidViewSurfaceHolder = null;
    post(this.jdField_a_of_type_JavaLangRunnable);
  }
  
  private class VideoSurfaceView
    extends SurfaceView
  {
    public VideoSurfaceView(Context paramContext)
    {
      super();
    }
    
    protected void onMeasure(int paramInt1, int paramInt2)
    {
      if ((ContentVideoView.a(ContentVideoView.this) > 0) && (ContentVideoView.b(ContentVideoView.this) > 0))
      {
        int i = getDefaultSize(ContentVideoView.a(ContentVideoView.this), paramInt1);
        int j = getDefaultSize(ContentVideoView.b(ContentVideoView.this), paramInt2);
        if (ContentVideoView.a(ContentVideoView.this) * j > ContentVideoView.b(ContentVideoView.this) * i)
        {
          paramInt2 = ContentVideoView.b(ContentVideoView.this) * i / ContentVideoView.a(ContentVideoView.this);
          paramInt1 = i;
        }
        else
        {
          paramInt1 = i;
          paramInt2 = j;
          if (ContentVideoView.a(ContentVideoView.this) * j < ContentVideoView.b(ContentVideoView.this) * i)
          {
            paramInt1 = ContentVideoView.a(ContentVideoView.this) * j / ContentVideoView.b(ContentVideoView.this);
            paramInt2 = j;
          }
        }
      }
      else
      {
        paramInt1 = 1;
        paramInt2 = 1;
      }
      if (ContentVideoView.a(ContentVideoView.this)) {
        if (ContentVideoView.a(ContentVideoView.this) == ContentVideoView.b(ContentVideoView.this))
        {
          if (ContentVideoView.b(ContentVideoView.this) != ContentVideoView.c(ContentVideoView.this)) {
            ContentVideoView.a(ContentVideoView.this, System.currentTimeMillis());
          }
        }
        else if ((!ContentVideoView.d(ContentVideoView.this)) && (ContentVideoView.b(ContentVideoView.this) == ContentVideoView.c(ContentVideoView.this)) && (System.currentTimeMillis() - ContentVideoView.b(ContentVideoView.this) < 5000L)) {
          ContentVideoView.a(ContentVideoView.this, true);
        }
      }
      setMeasuredDimension(paramInt1, paramInt2);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\ContentVideoView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */