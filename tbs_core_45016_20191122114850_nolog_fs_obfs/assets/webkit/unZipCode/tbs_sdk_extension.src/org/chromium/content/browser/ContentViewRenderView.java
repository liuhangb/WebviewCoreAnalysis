package org.chromium.content.browser;

import android.content.Context;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content_public.browser.ContentViewCore;
import org.chromium.content_public.browser.WebContents;
import org.chromium.ui.base.WindowAndroid;

@JNINamespace("content")
public class ContentViewRenderView
  extends FrameLayout
{
  private int jdField_a_of_type_Int;
  private long jdField_a_of_type_Long;
  private SurfaceHolder.Callback jdField_a_of_type_AndroidViewSurfaceHolder$Callback;
  private final SurfaceView jdField_a_of_type_AndroidViewSurfaceView = a(getContext());
  protected ContentViewCore a;
  private WindowAndroid jdField_a_of_type_OrgChromiumUiBaseWindowAndroid;
  private int b;
  
  public ContentViewRenderView(Context paramContext)
  {
    super(paramContext);
    this.jdField_a_of_type_AndroidViewSurfaceView.setZOrderMediaOverlay(true);
    setSurfaceViewBackgroundColor(-1);
    addView(this.jdField_a_of_type_AndroidViewSurfaceView, new FrameLayout.LayoutParams(-1, -1));
    this.jdField_a_of_type_AndroidViewSurfaceView.setVisibility(8);
  }
  
  @CalledByNative
  private void didSwapFrame()
  {
    if (this.jdField_a_of_type_AndroidViewSurfaceView.getBackground() != null) {
      post(new Runnable()
      {
        public void run()
        {
          ContentViewRenderView.a(ContentViewRenderView.this).setBackgroundResource(0);
        }
      });
    }
  }
  
  private native void nativeDestroy(long paramLong);
  
  private native long nativeInit(WindowAndroid paramWindowAndroid);
  
  private native void nativeOnPhysicalBackingSizeChanged(long paramLong, WebContents paramWebContents, int paramInt1, int paramInt2);
  
  private native void nativeSetCurrentWebContents(long paramLong, WebContents paramWebContents);
  
  private native void nativeSetOverlayVideoMode(long paramLong, boolean paramBoolean);
  
  private native void nativeSurfaceChanged(long paramLong, int paramInt1, int paramInt2, int paramInt3, Surface paramSurface);
  
  private native void nativeSurfaceCreated(long paramLong);
  
  private native void nativeSurfaceDestroyed(long paramLong);
  
  protected SurfaceView a(Context paramContext)
  {
    return new SurfaceView(paramContext);
  }
  
  protected void a() {}
  
  public void destroy()
  {
    this.jdField_a_of_type_AndroidViewSurfaceView.getHolder().removeCallback(this.jdField_a_of_type_AndroidViewSurfaceHolder$Callback);
    this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid = null;
    nativeDestroy(this.jdField_a_of_type_Long);
    this.jdField_a_of_type_Long = 0L;
  }
  
  public SurfaceView getSurfaceView()
  {
    return this.jdField_a_of_type_AndroidViewSurfaceView;
  }
  
  public boolean isInitialized()
  {
    return this.jdField_a_of_type_AndroidViewSurfaceView.getHolder().getSurface() != null;
  }
  
  public void onNativeLibraryLoaded(WindowAndroid paramWindowAndroid)
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_AndroidViewSurfaceView.getHolder().getSurface().isValid())) {
      throw new AssertionError("Surface created before native library loaded.");
    }
    if ((!jdField_a_of_type_Boolean) && (paramWindowAndroid == null)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_Long = nativeInit(paramWindowAndroid);
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Long == 0L)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid = paramWindowAndroid;
    this.jdField_a_of_type_AndroidViewSurfaceHolder$Callback = new SurfaceHolder.Callback()
    {
      public void surfaceChanged(SurfaceHolder paramAnonymousSurfaceHolder, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        if ((!jdField_a_of_type_Boolean) && (ContentViewRenderView.a(ContentViewRenderView.this) == 0L)) {
          throw new AssertionError();
        }
        ContentViewRenderView localContentViewRenderView = ContentViewRenderView.this;
        ContentViewRenderView.a(localContentViewRenderView, ContentViewRenderView.a(localContentViewRenderView), paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousSurfaceHolder.getSurface());
        if (ContentViewRenderView.this.a != null)
        {
          paramAnonymousSurfaceHolder = ContentViewRenderView.this;
          ContentViewRenderView.a(paramAnonymousSurfaceHolder, ContentViewRenderView.a(paramAnonymousSurfaceHolder), ContentViewRenderView.this.a.getWebContents(), paramAnonymousInt2, paramAnonymousInt3);
        }
      }
      
      public void surfaceCreated(SurfaceHolder paramAnonymousSurfaceHolder)
      {
        if ((!jdField_a_of_type_Boolean) && (ContentViewRenderView.a(ContentViewRenderView.this) == 0L)) {
          throw new AssertionError();
        }
        paramAnonymousSurfaceHolder = ContentViewRenderView.this;
        ContentViewRenderView.a(paramAnonymousSurfaceHolder, ContentViewRenderView.a(paramAnonymousSurfaceHolder));
        ContentViewRenderView.a(ContentViewRenderView.this).setVisibility(ContentViewRenderView.a(ContentViewRenderView.this).getVisibility());
        ContentViewRenderView.this.a();
      }
      
      public void surfaceDestroyed(SurfaceHolder paramAnonymousSurfaceHolder)
      {
        if ((!jdField_a_of_type_Boolean) && (ContentViewRenderView.a(ContentViewRenderView.this) == 0L)) {
          throw new AssertionError();
        }
        paramAnonymousSurfaceHolder = ContentViewRenderView.this;
        ContentViewRenderView.b(paramAnonymousSurfaceHolder, ContentViewRenderView.a(paramAnonymousSurfaceHolder));
      }
    };
    this.jdField_a_of_type_AndroidViewSurfaceView.getHolder().addCallback(this.jdField_a_of_type_AndroidViewSurfaceHolder$Callback);
    this.jdField_a_of_type_AndroidViewSurfaceView.setVisibility(0);
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.jdField_a_of_type_Int = paramInt1;
    this.b = paramInt2;
    Object localObject = this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore;
    if (localObject != null) {
      localObject = ((ContentViewCore)localObject).getWebContents();
    } else {
      localObject = null;
    }
    if (localObject != null) {
      ((WebContents)localObject).setSize(paramInt1, paramInt2);
    }
  }
  
  protected void onWindowVisibilityChanged(int paramInt)
  {
    super.onWindowVisibilityChanged(paramInt);
    WindowAndroid localWindowAndroid = this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid;
    if (localWindowAndroid == null) {
      return;
    }
    if (paramInt == 8)
    {
      localWindowAndroid.onVisibilityChanged(false);
      return;
    }
    if (paramInt == 0) {
      localWindowAndroid.onVisibilityChanged(true);
    }
  }
  
  public void setCurrentContentViewCore(ContentViewCore paramContentViewCore)
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Long == 0L)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore = paramContentViewCore;
    if (paramContentViewCore != null) {
      paramContentViewCore = paramContentViewCore.getWebContents();
    } else {
      paramContentViewCore = null;
    }
    if (paramContentViewCore != null) {
      nativeOnPhysicalBackingSizeChanged(this.jdField_a_of_type_Long, paramContentViewCore, this.jdField_a_of_type_Int, this.b);
    }
    nativeSetCurrentWebContents(this.jdField_a_of_type_Long, paramContentViewCore);
  }
  
  public void setOverlayVideoMode(boolean paramBoolean)
  {
    int i;
    if (paramBoolean) {
      i = -3;
    } else {
      i = -1;
    }
    this.jdField_a_of_type_AndroidViewSurfaceView.getHolder().setFormat(i);
    nativeSetOverlayVideoMode(this.jdField_a_of_type_Long, paramBoolean);
  }
  
  public void setSurfaceViewBackgroundColor(int paramInt)
  {
    SurfaceView localSurfaceView = this.jdField_a_of_type_AndroidViewSurfaceView;
    if (localSurfaceView != null) {
      localSurfaceView.setBackgroundColor(paramInt);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\ContentViewRenderView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */