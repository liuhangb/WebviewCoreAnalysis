package com.tencent.tbs.tbsshell.partner.reader;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebResourceResponse;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.ContextHolder;
import com.tencent.mtt.external.reader.MttFileReaderWrapper;
import com.tencent.mtt.external.reader.MttPluginCheck;
import com.tencent.mtt.external.reader.export.IReaderEventProxy;
import com.tencent.mtt.external.reader.export.IReaderWebViewClient;
import com.tencent.mtt.external.reader.export.IReaderWebViewProxy;
import com.tencent.smtt.export.external.DexLoader;
import com.tencent.tbs.common.baseinfo.TbsWupManager;
import com.tencent.tbs.common.config.Configuration;
import com.tencent.tbs.common.resources.TBSResources;
import com.tencent.tbs.common.stat.TBSStatManager;
import java.util.Map;

public class TbsReader
{
  private static final String KEY_FILE_PATH = "filePath";
  private static final String KEY_TEMP_PATH = "tempPath";
  public static final int READER_PLUGIN_RES_DOC_GUIDE = 5029;
  public static final int READER_PLUGIN_RES_PDF_GUIDE = 5023;
  public static final int READER_PLUGIN_RES_PPT_GUIDE = 5021;
  public static final int READER_PLUGIN_RES_STRING_FIXSCREEN = 5015;
  public static final int READER_PLUGIN_RES_STRING_ROTATIONSCREEN = 5018;
  public static final int READER_PLUGIN_RES_TXT_GUIDE = 5022;
  private static final String TAG = "TbsReader";
  static boolean sDoWupRequest = false;
  private Object mCallBackListener;
  private boolean mDeliverEvent = true;
  private boolean mIsWebviewBasedPlugin = false;
  private DexLoader mLoader;
  private MttPluginCheck mPluginCheck = null;
  private TbsReaderProxy mReaderProxy;
  private MttFileReaderWrapper mReaderWrapper;
  private LinearLayout mScrollContainer = null;
  private QBScrollView mScrollView = null;
  
  private TbsReaderProxy createReaderProxy(Context paramContext)
  {
    paramContext = new TbsReaderProxy(paramContext);
    paramContext.setLoader(this.mLoader);
    return paramContext;
  }
  
  public static Drawable getResDrawable(Integer paramInteger)
  {
    switch (paramInteger.intValue())
    {
    case 5018: 
    default: 
      return null;
    case 5020: 
      return TBSResources.getDrawable("reader_rotate_screen_press");
    case 5019: 
      return TBSResources.getDrawable("reader_rotate_screen_normal");
    case 5017: 
      return TBSResources.getDrawable("reader_fix_screen_press");
    }
    return TBSResources.getDrawable("reader_fix_screen_normal");
  }
  
  public static String getResString(Integer paramInteger)
  {
    int i = paramInteger.intValue();
    if (i != 5015)
    {
      if (i != 5018)
      {
        if (i != 5029)
        {
          switch (i)
          {
          default: 
            return "";
          case 5023: 
            return TBSResources.getString("reader_pdf_intall_browser");
          case 5022: 
            return TBSResources.getString("reader_txt_intall_browser");
          }
          return TBSResources.getString("reader_ppt_intall_browser");
        }
        return TBSResources.getString("reader_doc_intall_browser");
      }
      return TBSResources.getString("reader_rotation_screen");
    }
    return TBSResources.getString("reader_pdf_select_fit_screen");
  }
  
  private View getRootView()
  {
    return this.mReaderWrapper.getRootView();
  }
  
  private boolean initReader(Context paramContext, DexLoader paramDexLoader, Bundle paramBundle)
  {
    if (paramBundle.containsKey("filePath"))
    {
      if (!paramBundle.containsKey("tempPath")) {
        return false;
      }
      this.mReaderWrapper = new MttFileReaderWrapper(paramContext, paramBundle, this.mReaderProxy);
    }
    try
    {
      paramContext = new Bundle();
      this.mReaderProxy.getReaderEventProxy().doCallBackEvent(5045, null, paramContext);
      this.mReaderWrapper.setTitleBarHeight(paramContext.getInt("TitleHeight", 0));
      return true;
      return false;
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
  }
  
  public static boolean isSupportCurrentPlatform(Context paramContext)
  {
    if (!sDoWupRequest)
    {
      TbsWupManager.getInstance().doMultiWupRequest();
      sDoWupRequest = true;
    }
    boolean bool = Configuration.getInstance(paramContext).isFileReaderEnabled();
    paramContext = new StringBuilder();
    paramContext.append("isSupportCurrentPlatform :ret=");
    paramContext.append(bool);
    paramContext.toString();
    return bool;
  }
  
  public static boolean isSupportExt(String paramString)
  {
    boolean bool2 = MttFileReaderWrapper.isSupportExt(paramString);
    boolean bool1 = bool2;
    if (bool2)
    {
      bool1 = bool2;
      if (ContextHolder.getAppContext() != null) {
        bool1 = Configuration.getInstance(ContextHolder.getAppContext()).isFileReaderSupportExtEnabled(paramString);
      }
    }
    return bool1;
  }
  
  private void setDeliverEventToWebview(boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("setDeliverEventToWebview: ");
    localStringBuilder.append(paramBoolean);
    localStringBuilder.toString();
    this.mDeliverEvent = paramBoolean;
  }
  
  private boolean startLoadFile()
  {
    this.mReaderWrapper.load();
    return true;
  }
  
  void StackPrint()
  {
    StackTraceElement[] arrayOfStackTraceElement = (StackTraceElement[])Thread.getAllStackTraces().get(Thread.currentThread());
    int j = arrayOfStackTraceElement.length;
    int i = 0;
    while (i < j)
    {
      LogUtils.d("TbsReader", arrayOfStackTraceElement[i].toString());
      i += 1;
    }
  }
  
  public boolean checkPlugin(Context paramContext, String paramString, Boolean paramBoolean)
  {
    this.mPluginCheck = new MttPluginCheck(paramContext, paramString, this.mReaderProxy, paramBoolean.booleanValue());
    this.mPluginCheck.check();
    return true;
  }
  
  public void destroy()
  {
    Object localObject = this.mPluginCheck;
    if (localObject != null)
    {
      ((MttPluginCheck)localObject).close();
      this.mPluginCheck = null;
    }
    localObject = this.mReaderWrapper;
    if (localObject != null) {
      ((MttFileReaderWrapper)localObject).unload();
    }
    localObject = this.mReaderProxy;
    if (localObject != null)
    {
      ((TbsReaderProxy)localObject).destroy();
      this.mReaderProxy = null;
    }
    this.mReaderWrapper = null;
    this.mLoader = null;
    this.mScrollView = null;
    this.mScrollContainer = null;
  }
  
  public void doCommand(Integer paramInteger, Object paramObject1, Object paramObject2)
  {
    this.mReaderWrapper.doCommand(paramInteger.intValue(), paramObject1, paramObject2);
  }
  
  public boolean init(Context paramContext, DexLoader paramDexLoader, Object paramObject)
  {
    boolean bool = Configuration.getInstance(paramContext).isFileReaderEnabled();
    if (bool)
    {
      this.mCallBackListener = paramObject;
      this.mLoader = paramDexLoader;
      this.mReaderProxy = createReaderProxy(paramContext);
      this.mReaderProxy.setCallBackListener(this.mCallBackListener);
    }
    return bool;
  }
  
  public boolean isDocumentTop()
  {
    return false;
  }
  
  public boolean isWebviewBasedPlugin()
  {
    return this.mIsWebviewBasedPlugin;
  }
  
  public void onSizeChanged(Integer paramInteger1, Integer paramInteger2)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("onSizeChanged, width=");
    ((StringBuilder)localObject).append(paramInteger1);
    ((StringBuilder)localObject).append(" screenHeight=");
    ((StringBuilder)localObject).append(paramInteger2);
    ((StringBuilder)localObject).append(" mReaderWrapper is null?");
    boolean bool;
    if (this.mReaderWrapper == null) {
      bool = true;
    } else {
      bool = false;
    }
    ((StringBuilder)localObject).append(bool);
    ((StringBuilder)localObject).toString();
    localObject = this.mReaderWrapper;
    if (localObject != null)
    {
      if (this.mScrollView != null)
      {
        if (((MttFileReaderWrapper)localObject).getRootView() != null) {
          this.mReaderWrapper.getRootView().setLayoutParams(new LinearLayout.LayoutParams(-1, paramInteger2.intValue()));
        }
        this.mScrollContainer.requestLayout();
      }
      this.mReaderWrapper.onSizeChanged(paramInteger1.intValue(), paramInteger2.intValue());
    }
  }
  
  public boolean openFile(Context paramContext, Bundle paramBundle, FrameLayout paramFrameLayout)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Open file, param:");
    ((StringBuilder)localObject).append(paramBundle.toString());
    ((StringBuilder)localObject).toString();
    localObject = this.mPluginCheck;
    if (localObject != null)
    {
      ((MttPluginCheck)localObject).close();
      this.mPluginCheck = null;
    }
    if (!initReader(paramContext, this.mLoader, paramBundle))
    {
      Log.e("TbsReader", "initReader failed!");
      return false;
    }
    try
    {
      paramFrameLayout.addView(getRootView());
      return startLoadFile();
    }
    catch (Throwable paramContext)
    {
      paramBundle = new StringBuilder();
      paramBundle.append("Set reader view exception:");
      paramBundle.append(paramContext.getMessage());
      Log.e("TbsReader", paramBundle.toString());
    }
    return false;
  }
  
  public boolean openFile(Context paramContext, Bundle paramBundle, final FrameLayout paramFrameLayout, final View paramView)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Open file, param:");
    ((StringBuilder)localObject).append(paramBundle.toString());
    ((StringBuilder)localObject).toString();
    int j = paramBundle.getInt("set_content_view_height", 0);
    int i = j;
    if (j == 0) {
      i = ((Activity)paramContext).getWindowManager().getDefaultDisplay().getHeight();
    }
    UIResourceDimen.mDensity = paramContext.getResources().getDisplayMetrics().density;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("contentHeight :");
    ((StringBuilder)localObject).append(i);
    ((StringBuilder)localObject).toString();
    localObject = this.mPluginCheck;
    if (localObject != null)
    {
      ((MttPluginCheck)localObject).close();
      this.mPluginCheck = null;
    }
    if (!initReader(paramContext, this.mLoader, paramBundle))
    {
      Log.e("TbsReader", "initReader failed!");
      return false;
    }
    this.mReaderWrapper.setAdView(paramView);
    try
    {
      this.mScrollView = new QBScrollView(paramContext)
      {
        public boolean onInterceptTouchEvent(MotionEvent paramAnonymousMotionEvent)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("QBScrollView onInterceptTouchEvent: ");
          localStringBuilder.append(paramAnonymousMotionEvent.getAction());
          LogUtils.d("allenhan", localStringBuilder.toString());
          if (TbsReader.this.mReaderWrapper != null)
          {
            if (!TbsReader.this.mReaderWrapper.isDocumentContentEnded()) {
              return false;
            }
            if (!TbsReader.this.mDeliverEvent)
            {
              if (paramAnonymousMotionEvent.getPointerCount() > 1) {
                return false;
              }
              return super.onInterceptTouchEvent(paramAnonymousMotionEvent);
            }
            return false;
          }
          return false;
        }
        
        public boolean onTouchEvent(MotionEvent paramAnonymousMotionEvent)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("QBScrollView onTouchEvent: ");
          localStringBuilder.append(paramAnonymousMotionEvent.getAction());
          LogUtils.d("allenhan", localStringBuilder.toString());
          if ((TbsReader.this.mReaderWrapper != null) && (TbsReader.this.mReaderWrapper.isDocumentContentEnded())) {
            return super.onTouchEvent(paramAnonymousMotionEvent);
          }
          return false;
        }
        
        protected boolean overScrollBy(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5, int paramAnonymousInt6, int paramAnonymousInt7, int paramAnonymousInt8, boolean paramAnonymousBoolean)
        {
          if ((TbsReader.this.mReaderWrapper != null) && (TbsReader.this.mReaderWrapper.isDocumentContentEnded()) && (paramAnonymousInt4 + paramAnonymousInt2 <= 0) && (paramAnonymousInt2 < 0)) {
            TbsReader.this.setDeliverEventToWebview(true);
          }
          return super.overScrollBy(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, paramAnonymousInt5, paramAnonymousInt6, paramAnonymousInt7, paramAnonymousInt8, paramAnonymousBoolean);
        }
        
        protected void scrollby(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, boolean paramAnonymousBoolean1, boolean paramAnonymousBoolean2)
        {
          if ((TbsReader.this.mReaderWrapper != null) && (TbsReader.this.mReaderProxy != null))
          {
            if (TbsReader.this.mReaderWrapper.isDocumentContentEnded())
            {
              if ((getOffset() - paramAnonymousInt3 >= 0) && (paramAnonymousInt3 < 0))
              {
                LogUtils.d("allenhan", "scrollby: 露出品牌显示toolbar");
                TbsReader.this.mReaderProxy.getReaderEventProxy().doCallBackEvent(5050, new Boolean(true), null);
                TbsReader.this.setDeliverEventToWebview(true);
                if (paramAnonymousBoolean1)
                {
                  dispatchTouchEvent(MotionEvent.obtain(SystemClock.currentThreadTimeMillis(), SystemClock.uptimeMillis(), 0, paramAnonymousInt1, paramAnonymousInt2, 0));
                }
                else
                {
                  float f = getCurrVelocity();
                  StringBuilder localStringBuilder = new StringBuilder();
                  localStringBuilder.append("QBscrollView getVelocity：");
                  double d = f;
                  Double.isNaN(d);
                  int i = (int)(d / 0.65D);
                  localStringBuilder.append(i);
                  LogUtils.d("allenhan", localStringBuilder.toString());
                  TbsReader.this.mReaderProxy.getWebViewProxy().fling(0, -i);
                }
              }
              if ((getOffset() - paramAnonymousInt3 <= 0) && (paramAnonymousInt3 >= 0))
              {
                LogUtils.d("allenhan", "scrollby: 露出品牌隐藏toolbar");
                TbsReader.this.mReaderProxy.getReaderEventProxy().doCallBackEvent(5050, new Boolean(false), null);
              }
            }
            super.scrollby(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousBoolean1, paramAnonymousBoolean2);
            return;
          }
          super.scrollby(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousBoolean1, paramAnonymousBoolean2);
        }
      };
      paramBundle = new FrameLayout.LayoutParams(-1, -1);
      this.mScrollView.setLayoutParams(paramBundle);
      paramFrameLayout.addView(this.mScrollView, paramBundle);
      this.mScrollContainer = new LinearLayout(paramContext);
      this.mScrollContainer.setOrientation(1);
      this.mScrollContainer.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
      this.mScrollView.addView(this.mScrollContainer);
      this.mScrollContainer.addView(getRootView(), new LinearLayout.LayoutParams(-1, i));
      if (this.mReaderProxy.getWebViewProxy() != null) {
        this.mReaderProxy.getWebViewProxy().setReaderWebViewClient(new IReaderWebViewClient()
        {
          public void onPageFinished(String paramAnonymousString) {}
          
          public void onScrollChanged(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, View paramAnonymousView)
          {
            paramFrameLayout.setLabelFor(paramAnonymousInt2);
          }
          
          public void onSingleTaped() {}
          
          public void onWebviewCreated()
          {
            TbsReader.access$402(TbsReader.this, true);
            if (paramView != null) {
              TbsReader.this.mScrollContainer.addView(paramView);
            }
          }
          
          public boolean overScrollBy(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5, int paramAnonymousInt6, int paramAnonymousInt7, int paramAnonymousInt8, boolean paramAnonymousBoolean)
          {
            Object localObject = TbsReader.this.mReaderWrapper;
            boolean bool = false;
            if (localObject != null)
            {
              if (!TbsReader.this.mReaderWrapper.isDocumentContentEnded()) {
                return false;
              }
              float f;
              if (!paramAnonymousBoolean)
              {
                localObject = TbsReader.this.mReaderProxy.getWebViewProxy();
                if ((localObject != null) && ((localObject instanceof TbsReaderWebViewProxy)))
                {
                  f = ((TbsReaderWebViewProxy)localObject).getVelocity();
                  localObject = new StringBuilder();
                  ((StringBuilder)localObject).append("getCurrVelocity: ");
                  ((StringBuilder)localObject).append((int)f);
                  LogUtils.d("allenhan", ((StringBuilder)localObject).toString());
                }
              }
              if ((paramAnonymousInt4 + paramAnonymousInt2 >= paramAnonymousInt6) && (paramAnonymousInt2 > 0))
              {
                TbsReader.this.setDeliverEventToWebview(false);
                if (!paramAnonymousBoolean)
                {
                  localObject = TbsReader.this.mReaderProxy.getWebViewProxy();
                  if ((localObject != null) && ((localObject instanceof TbsReaderWebViewProxy)))
                  {
                    f = ((TbsReaderWebViewProxy)localObject).getVelocity();
                    localObject = new StringBuilder();
                    ((StringBuilder)localObject).append("getVelocity: ");
                    paramAnonymousInt1 = (int)(f * 0.65F);
                    ((StringBuilder)localObject).append(paramAnonymousInt1);
                    LogUtils.d("allenhan", ((StringBuilder)localObject).toString());
                    TbsReader.this.mScrollView.fling(-paramAnonymousInt1);
                  }
                }
              }
              paramAnonymousBoolean = bool;
              if (TbsReader.this.mScrollView.getScrollY() > 0) {
                paramAnonymousBoolean = true;
              }
              return paramAnonymousBoolean;
            }
            return false;
          }
          
          public WebResourceResponse shouldInterceptRequest(String paramAnonymousString)
          {
            return null;
          }
          
          public boolean shouldOverrideUrlLoading(String paramAnonymousString)
          {
            return false;
          }
        });
      }
      return startLoadFile();
    }
    catch (Throwable paramContext)
    {
      paramBundle = new StringBuilder();
      paramBundle.append("Set reader view exception:");
      paramBundle.append(paramContext.getMessage());
      Log.e("TbsReader", paramBundle.toString());
    }
    return false;
  }
  
  public void setLogoDrawable(Drawable paramDrawable) {}
  
  public void userStatistics(String paramString)
  {
    TBSStatManager.getInstance().userBehaviorStatistics(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\reader\TbsReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */