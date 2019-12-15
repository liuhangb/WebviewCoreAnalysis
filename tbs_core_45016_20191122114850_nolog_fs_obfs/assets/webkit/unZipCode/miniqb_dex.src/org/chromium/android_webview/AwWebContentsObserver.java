package org.chromium.android_webview;

import android.text.TextUtils;
import java.lang.ref.WeakReference;
import org.chromium.base.ThreadUtils;
import org.chromium.content_public.browser.WebContents;
import org.chromium.content_public.browser.WebContentsObserver;

public class AwWebContentsObserver
  extends WebContentsObserver
{
  private String jdField_a_of_type_JavaLangString;
  private boolean jdField_a_of_type_Boolean;
  private final WeakReference<AwContents> b;
  private final WeakReference<AwContentsClient> c;
  
  public AwWebContentsObserver(WebContents paramWebContents, AwContents paramAwContents, AwContentsClient paramAwContentsClient)
  {
    super(paramWebContents);
    this.b = new WeakReference(paramAwContents);
    this.c = new WeakReference(paramAwContentsClient);
  }
  
  private AwContentsClient a(String paramString)
  {
    AwContentsClient localAwContentsClient = (AwContentsClient)this.c.get();
    if (localAwContentsClient != null)
    {
      String str = AwContentsStatics.getUnreachableWebDataUrl();
      if ((str == null) || (!str.equals(paramString))) {
        return localAwContentsClient;
      }
    }
    return null;
  }
  
  public boolean didEverCommitNavigation()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public void didFailLoad(boolean paramBoolean, int paramInt, String paramString1, String paramString2)
  {
    paramString1 = (AwContentsClient)this.c.get();
    if (paramString1 == null) {
      return;
    }
    String str = AwContentsStatics.getUnreachableWebDataUrl();
    int i;
    if ((str != null) && (str.equals(paramString2))) {
      i = 1;
    } else {
      i = 0;
    }
    if ((paramBoolean) && (i == 0) && (paramInt == -3))
    {
      boolean bool1 = paramString1.skipPageFinishEventForFailLoad(paramString2);
      boolean bool2 = paramString1.skipPageFinishEventForErrAborted(paramString2);
      if ((!bool1) && (!bool2)) {
        paramString1.getCallbackHelper().postOnPageFinished(paramString2);
      }
    }
    if ((paramBoolean) && (paramInt != -3) && (!TextUtils.isEmpty(paramString2))) {
      paramString1.getCallbackHelper().postOnReceivedFailLoad(paramString2, paramInt);
    }
  }
  
  public void didFinishLoad(long paramLong, String paramString, boolean paramBoolean)
  {
    if ((paramBoolean) && (a(paramString) != null)) {
      this.jdField_a_of_type_JavaLangString = paramString;
    }
  }
  
  public void didFinishNavigation(final String paramString1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, Integer paramInteger, int paramInt1, String paramString2, int paramInt2)
  {
    if (paramInt1 != 0) {
      didFailLoad(paramBoolean1, paramInt1, paramString2, paramString1);
    }
    if (!paramBoolean3) {
      return;
    }
    paramBoolean2 = true;
    this.jdField_a_of_type_Boolean = true;
    if (!paramBoolean1) {
      return;
    }
    paramString2 = (AwContentsClient)this.c.get();
    if ((paramBoolean3) && (paramString2 != null))
    {
      if ((paramInteger != null) && ((paramInteger.intValue() & 0xFF) == 8)) {
        paramBoolean1 = paramBoolean2;
      } else {
        paramBoolean1 = false;
      }
      paramString2.getCallbackHelper().postDoUpdateVisitedHistory(paramString1, paramBoolean1);
    }
    if (!paramBoolean4) {
      ThreadUtils.postOnUiThread(new Runnable()
      {
        public void run()
        {
          AwContents localAwContents = (AwContents)AwWebContentsObserver.a(AwWebContentsObserver.this).get();
          if (localAwContents != null) {
            localAwContents.insertVisualStateCallbackIfNotDestroyed(0L, new AwContents.VisualStateCallback()
            {
              public void onComplete(long paramAnonymous2Long)
              {
                AwContentsClient localAwContentsClient = (AwContentsClient)AwWebContentsObserver.b(AwWebContentsObserver.1.this.jdField_a_of_type_OrgChromiumAndroid_webviewAwWebContentsObserver).get();
                if (localAwContentsClient == null) {
                  return;
                }
                localAwContentsClient.onPageCommitVisible(AwWebContentsObserver.1.this.jdField_a_of_type_JavaLangString);
              }
            });
          }
        }
      });
    }
    if ((paramString2 != null) && (paramBoolean5)) {
      paramString2.getCallbackHelper().postOnPageFinished(paramString1);
    }
  }
  
  public void didStopLoading(String paramString)
  {
    String str = paramString;
    if (paramString.length() == 0) {
      str = "about:blank";
    }
    paramString = a(str);
    if ((paramString != null) && (str.equals(this.jdField_a_of_type_JavaLangString)))
    {
      paramString.getCallbackHelper().postOnPageFinished(str);
      this.jdField_a_of_type_JavaLangString = null;
    }
  }
  
  public void titleWasSet(String paramString)
  {
    AwContentsClient localAwContentsClient = (AwContentsClient)this.c.get();
    if (localAwContentsClient == null) {
      return;
    }
    localAwContentsClient.updateTitle(paramString, true);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwWebContentsObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */