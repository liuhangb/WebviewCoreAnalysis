package org.chromium.android_webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.tencent.tbs.core.webkit.URLUtil;
import org.chromium.base.Callback;
import org.chromium.base.ContentUriUtils;
import org.chromium.base.ThreadUtils;
import org.chromium.content.browser.ContentVideoViewEmbedder;
import org.chromium.content_public.browser.NavigationController;
import org.chromium.content_public.common.ResourceRequestBody;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;

public class AwWebContentsDelegateAdapter
  extends AwWebContentsDelegate
{
  private static final String TAG = "AwWebContentsDelegateAdapter";
  private final AwContents mAwContents;
  private final AwSettings mAwSettings;
  private View mContainerView;
  private final AwContentsClient mContentsClient;
  private final Context mContext;
  private FrameLayout mCustomView;
  private AwContentVideoViewEmbedder mVideoViewEmbedder;
  
  public AwWebContentsDelegateAdapter(AwContents paramAwContents, AwContentsClient paramAwContentsClient, AwSettings paramAwSettings, Context paramContext, View paramView)
  {
    this.mAwContents = paramAwContents;
    this.mContentsClient = paramAwContentsClient;
    this.mAwSettings = paramAwSettings;
    this.mContext = paramContext;
    setContainerView(paramView);
  }
  
  private void enterFullscreen()
  {
    if (this.mAwContents.isFullScreen()) {
      return;
    }
    View localView = this.mAwContents.enterFullScreen();
    if (localView == null) {
      return;
    }
    AwContentsClient.CustomViewCallback local3 = new AwContentsClient.CustomViewCallback()
    {
      public void onCustomViewHidden()
      {
        if (AwWebContentsDelegateAdapter.this.mCustomView != null) {
          AwWebContentsDelegateAdapter.this.mAwContents.requestExitFullscreen();
        }
      }
    };
    this.mCustomView = new FrameLayout(this.mContext);
    this.mCustomView.addView(localView);
    this.mContentsClient.onShowCustomView(this.mCustomView, local3);
  }
  
  private void exitFullscreen()
  {
    if (this.mCustomView != null)
    {
      this.mCustomView = null;
      AwContentVideoViewEmbedder localAwContentVideoViewEmbedder = this.mVideoViewEmbedder;
      if (localAwContentVideoViewEmbedder != null) {
        localAwContentVideoViewEmbedder.setCustomView(null);
      }
      this.mAwContents.exitFullScreen();
      this.mContentsClient.onHideCustomView();
    }
  }
  
  private void handleMediaKey(KeyEvent paramKeyEvent)
  {
    int i = paramKeyEvent.getKeyCode();
    if ((i != 79) && (i != 222)) {
      switch (i)
      {
      default: 
        switch (i)
        {
        default: 
          return;
        }
        break;
      }
    }
    AudioManager localAudioManager = (AudioManager)this.mContext.getSystemService("audio");
    try
    {
      localAudioManager.dispatchMediaKeyEvent(paramKeyEvent);
      return;
    }
    catch (Throwable paramKeyEvent)
    {
      paramKeyEvent.printStackTrace();
    }
  }
  
  private boolean tryToMoveFocus(int paramInt)
  {
    View localView = this.mContainerView.focusSearch(paramInt);
    return (localView != null) && (localView != this.mContainerView) && (localView.requestFocus());
  }
  
  public void activateContents()
  {
    this.mContentsClient.onRequestFocus();
  }
  
  public boolean addMessageToConsole(int paramInt1, String paramString1, int paramInt2, String paramString2)
  {
    switch (paramInt1)
    {
    default: 
      paramInt1 = 4;
      break;
    case 3: 
      paramInt1 = 3;
      break;
    case 2: 
      paramInt1 = 2;
      break;
    case 1: 
      paramInt1 = 1;
      break;
    case 0: 
      paramInt1 = 0;
    }
    boolean bool = this.mContentsClient.onConsoleMessage(new AwConsoleMessage(paramString1, paramString2, paramInt2, paramInt1));
    if ((bool) && (paramString1 != null) && (paramString1.startsWith("[blocked]")))
    {
      paramString2 = new StringBuilder();
      paramString2.append("Blocked URL: ");
      paramString2.append(paramString1);
      Log.e("AwWebContentsDelegateAdapter", paramString2.toString());
    }
    return bool;
  }
  
  public boolean addNewContents(boolean paramBoolean1, boolean paramBoolean2)
  {
    return this.mContentsClient.onCreateWindow(paramBoolean1, paramBoolean2);
  }
  
  public void closeContents()
  {
    this.mContentsClient.onCloseWindow();
  }
  
  public ContentVideoViewEmbedder getContentVideoViewEmbedder()
  {
    this.mVideoViewEmbedder = new AwContentVideoViewEmbedder(this.mContext, this.mContentsClient, this.mCustomView);
    return this.mVideoViewEmbedder;
  }
  
  public void handleKeyboardEvent(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() == 0)
    {
      int i;
      switch (paramKeyEvent.getKeyCode())
      {
      default: 
        i = 0;
        break;
      case 22: 
        i = 66;
        break;
      case 21: 
        i = 17;
        break;
      case 20: 
        i = 130;
        break;
      case 19: 
        i = 33;
      }
      if ((i != 0) && (tryToMoveFocus(i))) {
        return;
      }
    }
    handleMediaKey(paramKeyEvent);
    this.mContentsClient.onUnhandledKeyEvent(paramKeyEvent);
  }
  
  public void loadingStateChanged()
  {
    this.mContentsClient.updateTitle(this.mAwContents.getTitle(), false);
  }
  
  public void navigationStateChanged(int paramInt)
  {
    if (((paramInt & 0x1) != 0) && (this.mAwContents.isPopupWindow()) && (this.mAwContents.hasAccessedInitialDocument()))
    {
      String str2 = this.mAwContents.getLastCommittedUrl();
      String str1 = str2;
      if (TextUtils.isEmpty(str2)) {
        str1 = "about:blank";
      }
      this.mContentsClient.getCallbackHelper().postSynthesizedPageLoadingForUrlBarUpdate(str1);
    }
  }
  
  public void onLoadProgressChanged(int paramInt)
  {
    this.mContentsClient.getCallbackHelper().postOnProgressChanged(paramInt);
  }
  
  public void onUpdateUrl(String paramString) {}
  
  public void openNewTab(String paramString1, String paramString2, ResourceRequestBody paramResourceRequestBody, int paramInt, boolean paramBoolean) {}
  
  public void runFileChooser(final int paramInt1, final int paramInt2, final int paramInt3, String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    paramString1 = new AwContentsClient.FileChooserParamsImpl(paramInt3, paramString1, paramString2, paramString3, paramBoolean);
    this.mContentsClient.showFileChooser(new Callback()
    {
      boolean jdField_a_of_type_Boolean;
      
      public void onResult(String[] paramAnonymousArrayOfString)
      {
        if (!this.jdField_a_of_type_Boolean)
        {
          this.jdField_a_of_type_Boolean = true;
          if (paramAnonymousArrayOfString == null)
          {
            AwWebContentsDelegate.nativeFilesSelectedInChooser(paramInt1, paramInt2, paramInt3, null, null);
            return;
          }
          new AwWebContentsDelegateAdapter.GetDisplayNameTask(AwWebContentsDelegateAdapter.this.mContext, paramInt1, paramInt2, paramInt3, paramAnonymousArrayOfString).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
          return;
        }
        throw new IllegalStateException("Duplicate showFileChooser result");
      }
    }, paramString1);
  }
  
  public void setContainerView(View paramView)
  {
    this.mContainerView = paramView;
  }
  
  public boolean shouldBlockMediaRequest(String paramString)
  {
    AwSettings localAwSettings = this.mAwSettings;
    boolean bool = true;
    if (localAwSettings != null)
    {
      if ((localAwSettings.getBlockNetworkLoads()) && (URLUtil.isNetworkUrl(paramString))) {
        return true;
      }
      bool = false;
    }
    return bool;
  }
  
  public void showRepostFormWarningDialog()
  {
    Object localObject = new Handler(ThreadUtils.getUiThreadLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (AwWebContentsDelegateAdapter.this.mAwContents.getNavigationController() == null) {
          return;
        }
        switch (paramAnonymousMessage.what)
        {
        default: 
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("WebContentsDelegateAdapter: unhandled message ");
          localStringBuilder.append(paramAnonymousMessage.what);
          throw new IllegalStateException(localStringBuilder.toString());
        case 2: 
          AwWebContentsDelegateAdapter.this.mAwContents.getNavigationController().cancelPendingReload();
          return;
        }
        AwWebContentsDelegateAdapter.this.mAwContents.getNavigationController().continuePendingReload();
      }
    };
    Message localMessage = ((Handler)localObject).obtainMessage(1);
    localObject = ((Handler)localObject).obtainMessage(2);
    this.mContentsClient.getCallbackHelper().postOnFormResubmission((Message)localObject, localMessage);
  }
  
  public boolean takeFocus(boolean paramBoolean)
  {
    if (paramBoolean == X5ApiCompatibilityUtils.isLayoutRtl(this.mContainerView)) {
      i = 66;
    } else {
      i = 17;
    }
    boolean bool = tryToMoveFocus(i);
    int i = 1;
    if (bool) {
      return true;
    }
    if (!paramBoolean) {
      i = 2;
    }
    return tryToMoveFocus(i);
  }
  
  public void toggleFullscreenModeForTab(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      enterFullscreen();
      return;
    }
    exitFullscreen();
  }
  
  private static class GetDisplayNameTask
    extends AsyncTask<Void, Void, String[]>
  {
    final int jdField_a_of_type_Int;
    @SuppressLint({"StaticFieldLeak"})
    final Context jdField_a_of_type_AndroidContentContext;
    final String[] jdField_a_of_type_ArrayOfJavaLangString;
    final int b;
    final int c;
    
    public GetDisplayNameTask(Context paramContext, int paramInt1, int paramInt2, int paramInt3, String[] paramArrayOfString)
    {
      this.jdField_a_of_type_Int = paramInt1;
      this.b = paramInt2;
      this.c = paramInt3;
      this.jdField_a_of_type_ArrayOfJavaLangString = paramArrayOfString;
      this.jdField_a_of_type_AndroidContentContext = paramContext;
    }
    
    private String a(String paramString)
    {
      if (paramString == null) {
        return "";
      }
      try
      {
        paramString = ContentUriUtils.getDisplayName(Uri.parse(paramString), this.jdField_a_of_type_AndroidContentContext, "_display_name");
        return paramString;
      }
      catch (Throwable paramString)
      {
        paramString.printStackTrace();
      }
      return "";
    }
    
    protected void a(String[] paramArrayOfString)
    {
      AwWebContentsDelegate.nativeFilesSelectedInChooser(this.jdField_a_of_type_Int, this.b, this.c, this.jdField_a_of_type_ArrayOfJavaLangString, paramArrayOfString);
    }
    
    protected String[] a(Void... paramVarArgs)
    {
      paramVarArgs = new String[this.jdField_a_of_type_ArrayOfJavaLangString.length];
      int i = 0;
      for (;;)
      {
        String[] arrayOfString = this.jdField_a_of_type_ArrayOfJavaLangString;
        if (i >= arrayOfString.length) {
          break;
        }
        paramVarArgs[i] = a(arrayOfString[i]);
        i += 1;
      }
      return paramVarArgs;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwWebContentsDelegateAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */