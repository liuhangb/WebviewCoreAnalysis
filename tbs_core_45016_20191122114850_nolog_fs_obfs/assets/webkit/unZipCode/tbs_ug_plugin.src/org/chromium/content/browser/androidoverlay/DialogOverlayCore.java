package org.chromium.content.browser.androidoverlay;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.IBinder;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback2;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import java.lang.reflect.Field;
import org.chromium.gfx.mojom.Rect;
import org.chromium.media.mojom.AndroidOverlayConfig;

class DialogOverlayCore
{
  private Dialog jdField_a_of_type_AndroidAppDialog;
  private WindowManager.LayoutParams jdField_a_of_type_AndroidViewWindowManager$LayoutParams;
  private Callbacks jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore$Callbacks;
  private Host jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore$Host;
  private boolean jdField_a_of_type_Boolean;
  
  @SuppressLint({"RtlHardcoded"})
  private WindowManager.LayoutParams a(boolean paramBoolean)
  {
    WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
    localLayoutParams.gravity = 51;
    int i;
    if (this.jdField_a_of_type_Boolean) {
      i = 1000;
    } else {
      i = 1001;
    }
    localLayoutParams.type = i;
    localLayoutParams.flags = 568;
    if (paramBoolean) {
      localLayoutParams.flags |= 0x2000;
    }
    try
    {
      i = ((Integer)localLayoutParams.getClass().getField("privateFlags").get(localLayoutParams)).intValue();
      localLayoutParams.getClass().getField("privateFlags").set(localLayoutParams, Integer.valueOf(i | 0x40));
      return localLayoutParams;
    }
    catch (NoSuchFieldException|NullPointerException|SecurityException|IllegalAccessException|IllegalArgumentException|ExceptionInInitializerError localNoSuchFieldException) {}
    return localLayoutParams;
  }
  
  private void a()
  {
    Dialog localDialog = this.jdField_a_of_type_AndroidAppDialog;
    if ((localDialog != null) && (localDialog.isShowing())) {}
    try
    {
      this.jdField_a_of_type_AndroidAppDialog.dismiss();
      this.jdField_a_of_type_AndroidAppDialog = null;
      this.jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore$Callbacks = null;
      return;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
  }
  
  private void a(Rect paramRect)
  {
    this.jdField_a_of_type_AndroidViewWindowManager$LayoutParams.x = paramRect.a;
    this.jdField_a_of_type_AndroidViewWindowManager$LayoutParams.y = paramRect.b;
    this.jdField_a_of_type_AndroidViewWindowManager$LayoutParams.width = paramRect.c;
    this.jdField_a_of_type_AndroidViewWindowManager$LayoutParams.height = paramRect.d;
  }
  
  public void initialize(Context paramContext, AndroidOverlayConfig paramAndroidOverlayConfig, Host paramHost, boolean paramBoolean)
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore$Host = paramHost;
    this.jdField_a_of_type_Boolean = paramBoolean;
    this.jdField_a_of_type_AndroidAppDialog = new Dialog(paramContext, 16973909);
    this.jdField_a_of_type_AndroidAppDialog.requestWindowFeature(1);
    this.jdField_a_of_type_AndroidAppDialog.setCancelable(false);
    this.jdField_a_of_type_AndroidViewWindowManager$LayoutParams = a(paramAndroidOverlayConfig.jdField_a_of_type_Boolean);
    a(paramAndroidOverlayConfig.jdField_a_of_type_OrgChromiumGfxMojomRect);
  }
  
  public void layoutSurface(Rect paramRect)
  {
    if (this.jdField_a_of_type_AndroidAppDialog != null)
    {
      if (this.jdField_a_of_type_AndroidViewWindowManager$LayoutParams.token == null) {
        return;
      }
      a(paramRect);
      this.jdField_a_of_type_AndroidAppDialog.getWindow().setAttributes(this.jdField_a_of_type_AndroidViewWindowManager$LayoutParams);
      return;
    }
  }
  
  public void onWindowToken(IBinder paramIBinder)
  {
    if (this.jdField_a_of_type_AndroidAppDialog != null)
    {
      if (this.jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore$Host == null) {
        return;
      }
      if ((paramIBinder != null) && ((this.jdField_a_of_type_AndroidViewWindowManager$LayoutParams.token == null) || (paramIBinder == this.jdField_a_of_type_AndroidViewWindowManager$LayoutParams.token)))
      {
        if (this.jdField_a_of_type_AndroidViewWindowManager$LayoutParams.token == paramIBinder) {
          return;
        }
        this.jdField_a_of_type_AndroidViewWindowManager$LayoutParams.token = paramIBinder;
        this.jdField_a_of_type_AndroidAppDialog.getWindow().setAttributes(this.jdField_a_of_type_AndroidViewWindowManager$LayoutParams);
        this.jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore$Callbacks = new Callbacks(null);
        this.jdField_a_of_type_AndroidAppDialog.getWindow().takeSurface(this.jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore$Callbacks);
        this.jdField_a_of_type_AndroidAppDialog.show();
        return;
      }
      this.jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore$Host.onOverlayDestroyed();
      this.jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore$Host = null;
      a();
      return;
    }
  }
  
  public void release()
  {
    a();
    this.jdField_a_of_type_AndroidViewWindowManager$LayoutParams.token = null;
    this.jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore$Host = null;
  }
  
  private class Callbacks
    implements SurfaceHolder.Callback2
  {
    private Callbacks() {}
    
    public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3) {}
    
    public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
    {
      if (DialogOverlayCore.a(DialogOverlayCore.this) == null) {
        return;
      }
      if (DialogOverlayCore.a(DialogOverlayCore.this) != null) {
        DialogOverlayCore.a(DialogOverlayCore.this).onSurfaceReady(paramSurfaceHolder.getSurface());
      }
    }
    
    public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
    {
      if (DialogOverlayCore.a(DialogOverlayCore.this) != null)
      {
        if (DialogOverlayCore.a(DialogOverlayCore.this) == null) {
          return;
        }
        DialogOverlayCore.a(DialogOverlayCore.this).onOverlayDestroyed();
        DialogOverlayCore.a(DialogOverlayCore.this).waitForClose();
        DialogOverlayCore.a(DialogOverlayCore.this).enforceClose();
        DialogOverlayCore.a(DialogOverlayCore.this, null);
        return;
      }
    }
    
    public void surfaceRedrawNeeded(SurfaceHolder paramSurfaceHolder) {}
  }
  
  public static abstract interface Host
  {
    public abstract void enforceClose();
    
    public abstract void onOverlayDestroyed();
    
    public abstract void onSurfaceReady(Surface paramSurface);
    
    public abstract void waitForClose();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\androidoverlay\DialogOverlayCore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */