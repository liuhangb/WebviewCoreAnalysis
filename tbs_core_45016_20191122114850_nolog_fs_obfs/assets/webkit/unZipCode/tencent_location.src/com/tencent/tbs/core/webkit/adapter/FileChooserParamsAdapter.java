package com.tencent.tbs.core.webkit.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.tencent.tbs.core.webkit.WebChromeClient.FileChooserParams;
import org.chromium.android_webview.AwContentsClient.FileChooserParamsImpl;
import org.chromium.base.annotations.UsedByReflection;

public class FileChooserParamsAdapter
  extends WebChromeClient.FileChooserParams
{
  private AwContentsClient.FileChooserParamsImpl mParams;
  
  public FileChooserParamsAdapter(AwContentsClient.FileChooserParamsImpl paramFileChooserParamsImpl, Context paramContext)
  {
    this.mParams = paramFileChooserParamsImpl;
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static Uri[] parseFileChooserResult(int paramInt, Intent paramIntent)
  {
    Uri[] arrayOfUri = null;
    if (paramInt == 0) {
      return null;
    }
    if ((paramIntent != null) && (paramInt == -1)) {
      paramIntent = paramIntent.getData();
    } else {
      paramIntent = null;
    }
    if (paramIntent != null)
    {
      arrayOfUri = new Uri[1];
      arrayOfUri[0] = paramIntent;
    }
    return arrayOfUri;
  }
  
  public Intent createIntent()
  {
    Object localObject2 = "*/*";
    Object localObject1 = localObject2;
    if (this.mParams.getAcceptTypes() != null)
    {
      localObject1 = localObject2;
      if (this.mParams.getAcceptTypes().length > 0) {
        localObject1 = this.mParams.getAcceptTypes()[0];
      }
    }
    localObject2 = new Intent("android.intent.action.GET_CONTENT");
    ((Intent)localObject2).addCategory("android.intent.category.OPENABLE");
    ((Intent)localObject2).setType((String)localObject1);
    return (Intent)localObject2;
  }
  
  public String[] getAcceptTypes()
  {
    if ((this.mParams.getAcceptTypes() != null) && (this.mParams.getAcceptTypes().length != 0)) {
      return this.mParams.getAcceptTypes();
    }
    return new String[0];
  }
  
  public String getFilenameHint()
  {
    return this.mParams.getFilenameHint();
  }
  
  public int getMode()
  {
    return this.mParams.getMode();
  }
  
  public CharSequence getTitle()
  {
    return this.mParams.getTitle();
  }
  
  public boolean isCaptureEnabled()
  {
    return this.mParams.isCaptureEnabled();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\adapter\FileChooserParamsAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */