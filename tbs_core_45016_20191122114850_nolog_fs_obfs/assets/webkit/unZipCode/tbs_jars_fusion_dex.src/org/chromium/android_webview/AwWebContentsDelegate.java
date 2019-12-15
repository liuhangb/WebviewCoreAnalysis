package org.chromium.android_webview;

import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.components.web_contents_delegate_android.WebContentsDelegateAndroid;

@JNINamespace("android_webview")
@VisibleForTesting
public abstract class AwWebContentsDelegate
  extends WebContentsDelegateAndroid
{
  protected static native void nativeFilesSelectedInChooser(int paramInt1, int paramInt2, int paramInt3, String[] paramArrayOfString1, String[] paramArrayOfString2);
  
  @CalledByNative
  public abstract void activateContents();
  
  @CalledByNative
  public abstract boolean addNewContents(boolean paramBoolean1, boolean paramBoolean2);
  
  @CalledByNative
  public abstract void closeContents();
  
  @CalledByNative
  public abstract void loadingStateChanged();
  
  @CalledByNative
  public abstract void navigationStateChanged(int paramInt);
  
  @CalledByNative
  public abstract void runFileChooser(int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2, String paramString3, boolean paramBoolean);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwWebContentsDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */