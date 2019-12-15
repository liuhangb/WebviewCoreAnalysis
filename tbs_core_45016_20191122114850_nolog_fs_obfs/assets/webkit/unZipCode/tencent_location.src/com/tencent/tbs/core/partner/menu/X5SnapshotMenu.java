package com.tencent.tbs.core.partner.menu;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.ValueCallback;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.smtt.webkit.ui.TbsConfirmDialog;
import com.tencent.smtt.webkit.ui.TbsConfirmDialog.BtnClickListener;
import com.tencent.smtt.webkit.ui.g;
import com.tencent.tbs.core.partner.tencentfiledownload.TFMDownloadListener;
import com.tencent.tbs.core.partner.tencentfiledownload.TFMDownloadManager;
import com.tencent.tbs.core.partner.tencentfiledownload.TFMInstallListener;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class X5SnapshotMenu
  implements TFMDownloadListener, TFMInstallListener
{
  private static final int MASK_VIEW_ID = Integer.MAX_VALUE;
  private static final String SCREEN_SNAP_SHOT_TAG = "catch-screen";
  private static final int SNAPSHOT_ERROR_CODE_1 = 1;
  private static final int SNAPSHOT_FINISH = 2;
  private static final int SNAPSHOT_IMG_LOAD_TIME = 1000;
  private static final int SNAPSHOT_LOOP = 1;
  private static final int SNAPSHOT_MAX_TIMES = 25;
  private static final int SNAPSHOT_OPEN_IMAGE = 5;
  private static final int SNAPSHOT_REMOVE_MASK = 4;
  private static final int SNAPSHOT_SAVE_BITMAP_FINISH = 3;
  private static final int SNAPSHOT_SCROLL_ANIMATION_DURATION = 500;
  private static final int SNAPSHOT_START = 0;
  private static final int VIEW_PICTURE_ID = 2147483646;
  static final String functionJs = "var loadedImgList=new Array();\nfunction isInArray(arr,value){\n    for(var i = 0; i < arr.length; i++){\n        if(value === arr[i]){\n            return true;\n        }\n    }\n    return false;\n}\nfunction isAnyPartOfElementInViewport(el) {\n    const rect = el.getBoundingClientRect();\n    const windowHeight = (window.innerHeight || document.documentElement.clientHeight);\n    const windowWidth = (window.innerWidth || document.documentElement.clientWidth);\n\n    const vertInView = (rect.top <= windowHeight) && ((rect.top + rect.height) >= 0);\n    const horInView = (rect.left <= windowWidth) && ((rect.left + rect.width) >= 0);\n\n    return (vertInView && horInView);\n}\n\n\nfunction isImgInViewport() {\n    var list = document.getElementsByTagName(\"IMG\");\n    var i = list.length;\n    while(i--) {\n        var imgtag = list[i];\n        const rect = imgtag.getBoundingClientRect();\n        var width = rect.right - rect.left;\n        if (isAnyPartOfElementInViewport(imgtag) && (width >= 100)) {\n            if (isInArray(loadedImgList, imgtag)) {\n            } else {\n                loadedImgList[loadedImgList.length] = imgtag;\n                return true;\n            }\n        }\n    }\n    return false;\n}";
  int mContentHeight = 0;
  private Context mContext;
  float mDensity = 0.0F;
  private boolean mHasLoadJs = false;
  int mMargin = 32;
  int mScreenHeight = 0;
  int mScreenWidth = 0;
  RelativeLayout mSnapShotStatusContainer = null;
  private ArrayList<Bitmap> mSnapshotBitmaps = new ArrayList();
  private boolean mSnapshotCurrentRet = false;
  private boolean mSnapshotFinished = false;
  private Handler mSnapshotHandler = null;
  private String mSnapshotPath = "";
  private int mSnapshotTimes = 0;
  int mStatusBarHeight = 0;
  int mTextSize = 16;
  int mTitleBarHeight = 0;
  TextView mViewPicture = null;
  View mViewPictureDivideLine = null;
  TextView mViewTips = null;
  private IX5WebView mWebView;
  int mWebviewHeight = 0;
  int mWebviewWidth = 0;
  
  public X5SnapshotMenu(Context paramContext, IX5WebView paramIX5WebView)
  {
    this.mContext = paramContext;
    this.mWebView = paramIX5WebView;
  }
  
  private boolean canConinueSnapshot()
  {
    if (this.mSnapshotTimes >= 25)
    {
      Log.e("catch-screen", "over max times");
      return false;
    }
    int i = this.mWebView.getScrollY();
    if (i >= this.mContentHeight - this.mWebviewHeight)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("scrollY >= mContentHeight - mWebviewHeight--scrollY=");
      localStringBuilder.append(i);
      localStringBuilder.append(",mContentHeight - mWebviewHeight=");
      localStringBuilder.append(this.mContentHeight - this.mWebviewHeight);
      Log.e("catch-screen", localStringBuilder.toString());
      return false;
    }
    return true;
  }
  
  private Bitmap combineImageIntoOne(ArrayList<Bitmap> paramArrayList)
  {
    if (paramArrayList != null)
    {
      if (paramArrayList.size() == 0) {
        return null;
      }
      int k = ((Bitmap)paramArrayList.get(0)).getWidth();
      int i = 0;
      int j = 0;
      while (i < paramArrayList.size())
      {
        j += ((Bitmap)paramArrayList.get(i)).getHeight();
        i += 1;
      }
      Bitmap localBitmap = Bitmap.createBitmap(k, j, Bitmap.Config.RGB_565);
      Canvas localCanvas = new Canvas(localBitmap);
      j = 0;
      i = 0;
      while (j < paramArrayList.size())
      {
        if (j == 0) {
          i = 0;
        } else {
          i += ((Bitmap)paramArrayList.get(j - 1)).getHeight();
        }
        localCanvas.drawBitmap((Bitmap)paramArrayList.get(j), 0.0F, i, null);
        j += 1;
      }
      return localBitmap;
    }
    return null;
  }
  
  private void createHandler()
  {
    this.mSnapshotHandler = new SnapShotHandler(this.mContext)
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        int j = paramAnonymousMessage.what;
        int i = 0;
        switch (j)
        {
        default: 
          
        case 5: 
          X5SnapshotMenu.openImageWithFileManager(X5SnapshotMenu.this.mContext, X5SnapshotMenu.this.mSnapshotPath);
          return;
        case 4: 
          X5SnapshotMenu.this.removeViewMask();
          return;
        case 3: 
          j = paramAnonymousMessage.arg1;
          while (i < X5SnapshotMenu.this.mSnapshotBitmaps.size())
          {
            paramAnonymousMessage = (Bitmap)X5SnapshotMenu.this.mSnapshotBitmaps.get(i);
            if (paramAnonymousMessage != null) {
              paramAnonymousMessage.recycle();
            }
            X5SnapshotMenu.this.mSnapshotBitmaps.clear();
            i += 1;
          }
          if (j == 0)
          {
            Toast.makeText(X5SnapshotMenu.this.mContext, SmttResource.getString("x5_pop_menu_snapshot_save", "string"), 1).show();
            X5SnapshotMenu.this.showPreviewButton();
            SmttServiceProxy.getInstance().userBehaviorStatistics("BZBC006");
          }
          else
          {
            Log.e("catch-screen", "exitSnapshotMode -- savePictureToSys return false");
            Toast.makeText(X5SnapshotMenu.this.mContext, X5SnapshotMenu.this.getErrorMsg(j), 1).show();
            X5SnapshotMenu.this.removeViewMask();
          }
          paramAnonymousMessage = ((X5WebViewAdapter)X5SnapshotMenu.this.mWebView).getRealWebView().getRootView().findViewById(Integer.MAX_VALUE);
          if (paramAnonymousMessage != null)
          {
            paramAnonymousMessage.setOnTouchListener(null);
            return;
          }
          break;
        case 2: 
          X5SnapshotMenu.this.exitSnapshotMode();
          return;
        case 1: 
          paramAnonymousMessage = X5SnapshotMenu.this.createTmpBitmap();
          if (paramAnonymousMessage == null)
          {
            X5SnapshotMenu.this.sendSnapshotFinish();
            paramAnonymousMessage = new StringBuilder();
            paramAnonymousMessage.append("OOM -- createTmpBitmap return null,mSnapshotTimes=");
            paramAnonymousMessage.append(X5SnapshotMenu.this.mSnapshotTimes);
            Log.e("catch-screen", paramAnonymousMessage.toString());
            return;
          }
          X5SnapshotMenu localX5SnapshotMenu = X5SnapshotMenu.this;
          X5SnapshotMenu.access$1102(localX5SnapshotMenu, localX5SnapshotMenu.snapShotVisible(paramAnonymousMessage, null));
          if (!X5SnapshotMenu.this.mSnapshotCurrentRet)
          {
            X5SnapshotMenu.access$1302(X5SnapshotMenu.this, true);
            paramAnonymousMessage.recycle();
            X5SnapshotMenu.this.sendSnapshotFinish();
            return;
          }
          X5SnapshotMenu.access$1008(X5SnapshotMenu.this);
          X5SnapshotMenu.this.mSnapshotBitmaps.add(paramAnonymousMessage);
          if (X5SnapshotMenu.this.canConinueSnapshot())
          {
            SmttServiceProxy.getInstance().userBehaviorStatistics("BZBC003");
            X5SnapshotMenu.this.mWebView.scrollBy(0, X5SnapshotMenu.this.mWebviewHeight);
            i = X5SnapshotMenu.this.mWebView.getScrollY();
            paramAnonymousMessage = X5SnapshotMenu.this;
            paramAnonymousMessage.startAnimationAndSendLoopMessage(i - paramAnonymousMessage.mWebviewHeight, i);
            return;
          }
          X5SnapshotMenu.this.sendSnapshotFinish();
          return;
        case 0: 
          if (!X5SnapshotMenu.this.mHasLoadJs)
          {
            X5SnapshotMenu.this.mWebView.loadUrl("javascript:var loadedImgList=new Array();\nfunction isInArray(arr,value){\n    for(var i = 0; i < arr.length; i++){\n        if(value === arr[i]){\n            return true;\n        }\n    }\n    return false;\n}\nfunction isAnyPartOfElementInViewport(el) {\n    const rect = el.getBoundingClientRect();\n    const windowHeight = (window.innerHeight || document.documentElement.clientHeight);\n    const windowWidth = (window.innerWidth || document.documentElement.clientWidth);\n\n    const vertInView = (rect.top <= windowHeight) && ((rect.top + rect.height) >= 0);\n    const horInView = (rect.left <= windowWidth) && ((rect.left + rect.width) >= 0);\n\n    return (vertInView && horInView);\n}\n\n\nfunction isImgInViewport() {\n    var list = document.getElementsByTagName(\"IMG\");\n    var i = list.length;\n    while(i--) {\n        var imgtag = list[i];\n        const rect = imgtag.getBoundingClientRect();\n        var width = rect.right - rect.left;\n        if (isAnyPartOfElementInViewport(imgtag) && (width >= 100)) {\n            if (isInArray(loadedImgList, imgtag)) {\n            } else {\n                loadedImgList[loadedImgList.length] = imgtag;\n                return true;\n            }\n        }\n    }\n    return false;\n}");
            X5SnapshotMenu.access$502(X5SnapshotMenu.this, true);
          }
          i = X5SnapshotMenu.this.mWebView.getScrollY();
          if (i > 0)
          {
            X5SnapshotMenu.this.mWebView.scrollBy(0, -i);
            X5SnapshotMenu.this.startAnimationAndSendLoopMessage(i, 0);
            return;
          }
          sendEmptyMessage(1);
        }
      }
    };
  }
  
  private boolean createSnapshotViewMask()
  {
    try
    {
      ViewGroup localViewGroup = (ViewGroup)((X5WebViewAdapter)this.mWebView).getRealWebView().getRootView();
      localObject = new RelativeLayout(this.mContext);
      ((RelativeLayout)localObject).setId(Integer.MAX_VALUE);
      ((RelativeLayout)localObject).setLayoutParams(new ViewGroup.LayoutParams(this.mScreenWidth, this.mScreenHeight));
      this.mSnapShotStatusContainer = new RelativeLayout(this.mContext);
      this.mSnapShotStatusContainer.setLayoutParams(new ViewGroup.LayoutParams(this.mScreenWidth, this.mTitleBarHeight));
      this.mSnapShotStatusContainer.setX(0.0F);
      this.mSnapShotStatusContainer.setY(this.mStatusBarHeight);
      this.mViewTips = new TextView(this.mContext);
      RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(this.mScreenWidth, this.mTitleBarHeight);
      localLayoutParams.addRule(15, -1);
      localLayoutParams.setMargins(this.mMargin, 0, 0, 0);
      this.mViewTips.setLayoutParams(localLayoutParams);
      this.mViewTips.setGravity(16);
      this.mViewTips.setTextSize(this.mTextSize);
      this.mViewTips.setText(SmttResource.getString("x5_pop_menu_snapshot_scrolling", "string"));
      this.mViewPicture = new TextView(this.mContext);
      this.mViewPicture.setId(2147483646);
      localLayoutParams = new RelativeLayout.LayoutParams(this.mScreenWidth, this.mTitleBarHeight);
      localLayoutParams.addRule(12, -1);
      localLayoutParams.setMargins(0, 0, 0, 0);
      this.mViewPicture.setLayoutParams(localLayoutParams);
      this.mViewPicture.setGravity(17);
      this.mViewPicture.setTextSize(this.mTextSize);
      this.mViewPicture.setText(SmttResource.getString("x5_qq_snapshot_stop", "string"));
      this.mViewPicture.setBackgroundColor(-328966);
      this.mViewPicture.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          X5SnapshotMenu.this.exitSnapshotMode();
          SmttServiceProxy.getInstance().userBehaviorStatistics("BZBC005");
        }
      });
      this.mViewPictureDivideLine = new View(this.mContext);
      localLayoutParams = new RelativeLayout.LayoutParams(this.mScreenWidth, 1);
      localLayoutParams.addRule(2, 2147483646);
      this.mViewPictureDivideLine.setLayoutParams(localLayoutParams);
      this.mViewPictureDivideLine.setBackgroundColor(-7171438);
      this.mSnapShotStatusContainer.addView(this.mViewTips);
      this.mSnapShotStatusContainer.setBackgroundColor(-328966);
      ((RelativeLayout)localObject).setBackgroundColor(-1895825408);
      ((RelativeLayout)localObject).addView(this.mViewPicture);
      ((RelativeLayout)localObject).addView(this.mViewPictureDivideLine);
      ((RelativeLayout)localObject).addView(this.mSnapShotStatusContainer);
      localViewGroup.addView((View)localObject);
      ((RelativeLayout)localObject).setOnTouchListener(new View.OnTouchListener()
      {
        public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
        {
          return true;
        }
      });
      return true;
    }
    catch (Exception localException)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("add dynamic view exception:");
      ((StringBuilder)localObject).append(localException.getMessage());
      ((StringBuilder)localObject).append(",");
      ((StringBuilder)localObject).append(localException.getCause());
      Log.e("catch-screen", ((StringBuilder)localObject).toString());
      SmttServiceProxy.getInstance().userBehaviorStatistics("BZBC101");
    }
    return false;
  }
  
  private Bitmap createTmpBitmap()
  {
    try
    {
      Bitmap localBitmap = Bitmap.createBitmap(this.mWebviewWidth, this.mWebviewHeight, Bitmap.Config.RGB_565);
      return localBitmap;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("createTmpBitmap exception:");
      localStringBuilder.append(localException.getMessage());
      localStringBuilder.append(",");
      localStringBuilder.append(localException.getCause());
      Log.e("catch-screen", localStringBuilder.toString());
    }
    return null;
  }
  
  private void deleteRepeateBitmaps(ArrayList<Bitmap> paramArrayList)
  {
    if (paramArrayList != null)
    {
      if (paramArrayList.size() < 2) {
        return;
      }
      Bitmap localBitmap1 = (Bitmap)paramArrayList.get(paramArrayList.size() - 1);
      int i = this.mWebView.getScrollY();
      int j = this.mWebviewHeight;
      int k = this.mContentHeight;
      if (j + i <= k) {
        return;
      }
      i = k - i;
      Bitmap localBitmap2 = Bitmap.createBitmap(localBitmap1, 0, localBitmap1.getHeight() - i, localBitmap1.getWidth(), i);
      if (localBitmap2 != null)
      {
        paramArrayList.remove(localBitmap1);
        paramArrayList.add(localBitmap2);
        localBitmap1.recycle();
        return;
      }
      Log.e("catch-screen", "bitmap2_clipping == null");
      return;
    }
  }
  
  private void exitSnapshotMode()
  {
    this.mSnapshotFinished = true;
    removeAllMessage();
    if (this.mSnapshotBitmaps.size() >= 1)
    {
      new Thread()
      {
        public void run()
        {
          Object localObject = X5SnapshotMenu.this;
          ((X5SnapshotMenu)localObject).deleteRepeateBitmaps(((X5SnapshotMenu)localObject).mSnapshotBitmaps);
          localObject = X5SnapshotMenu.this;
          localObject = ((X5SnapshotMenu)localObject).combineImageIntoOne(((X5SnapshotMenu)localObject).mSnapshotBitmaps);
          int i = X5SnapshotMenu.this.savePictureToSys((Bitmap)localObject);
          ((Bitmap)localObject).recycle();
          localObject = new Message();
          ((Message)localObject).what = 3;
          ((Message)localObject).arg1 = i;
          X5SnapshotMenu.this.mSnapshotHandler.sendMessage((Message)localObject);
        }
      }.start();
    }
    else
    {
      Message localMessage = new Message();
      localMessage.what = 3;
      localMessage.arg1 = 1;
      this.mSnapshotHandler.sendMessage(localMessage);
    }
    this.mViewPicture.setText(SmttResource.getString("x5_pop_menu_snapshot_processing", "string"));
  }
  
  private static long getAvailableRAM(Context paramContext)
  {
    try
    {
      paramContext = (ActivityManager)paramContext.getSystemService("activity");
      ActivityManager.MemoryInfo localMemoryInfo = new ActivityManager.MemoryInfo();
      paramContext.getMemoryInfo(localMemoryInfo);
      long l = localMemoryInfo.availMem;
      return l;
    }
    catch (Throwable paramContext)
    {
      paramContext.printStackTrace();
    }
    return -1L;
  }
  
  private String getErrorMsg(int paramInt)
  {
    if (paramInt == 1) {
      return SmttResource.getString("x5_qq_snapshot_to_quick", "string");
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(SmttResource.getString("x5_pop_menu_snapshot_error_tips", "string"));
    localStringBuilder.append(paramInt);
    return localStringBuilder.toString();
  }
  
  private Intent getImageFileIntent(String paramString)
  {
    Intent localIntent = new Intent("android.intent.action.VIEW");
    localIntent.addCategory("android.intent.category.DEFAULT");
    localIntent.addFlags(268435456);
    localIntent.setDataAndType(Uri.fromFile(new File(paramString)), "image/*");
    return localIntent;
  }
  
  private int getStatusBarHeightFromSystem()
  {
    try
    {
      Class localClass = Class.forName("com.android.internal.R$dimen");
      localObject = localClass.newInstance();
      i = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
      j = this.mContext.getResources().getDimensionPixelSize(i);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("getStatusBarHeightFromSystem exception:");
      ((StringBuilder)localObject).append(localException.getMessage());
      ((StringBuilder)localObject).append(",");
      ((StringBuilder)localObject).append(localException.getCause());
      Log.e("catch-screen", ((StringBuilder)localObject).toString());
      j = -1;
    }
    int i = j;
    if (j < 1)
    {
      i = this.mContext.getResources().getIdentifier("statebar_height", "dimen", this.mContext.getPackageName());
      i = Math.round(this.mContext.getResources().getDimension(i));
    }
    int j = i;
    if (i < 1) {
      j = (int)(this.mContext.getResources().getDisplayMetrics().density * 25.0F);
    }
    return j;
  }
  
  /* Error */
  private static int getTotalRAM()
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_1
    //   2: iconst_0
    //   3: istore_2
    //   4: new 638	java/io/BufferedReader
    //   7: dup
    //   8: new 640	java/io/FileReader
    //   11: dup
    //   12: ldc_w 642
    //   15: invokespecial 643	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   18: sipush 8192
    //   21: invokespecial 646	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   24: astore 4
    //   26: aload 4
    //   28: astore_3
    //   29: aload 4
    //   31: invokevirtual 649	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   34: astore 5
    //   36: iload_2
    //   37: istore_0
    //   38: aload 5
    //   40: ifnull +101 -> 141
    //   43: aload 4
    //   45: astore_3
    //   46: aload 5
    //   48: ldc_w 651
    //   51: invokevirtual 656	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   54: istore_0
    //   55: iconst_m1
    //   56: iload_0
    //   57: if_icmpeq -31 -> 26
    //   60: aload 4
    //   62: astore_3
    //   63: aload 5
    //   65: iload_0
    //   66: bipush 9
    //   68: iadd
    //   69: invokevirtual 659	java/lang/String:substring	(I)Ljava/lang/String;
    //   72: invokevirtual 662	java/lang/String:trim	()Ljava/lang/String;
    //   75: astore 5
    //   77: iload_2
    //   78: istore_0
    //   79: aload 5
    //   81: ifnull +60 -> 141
    //   84: iload_2
    //   85: istore_0
    //   86: aload 4
    //   88: astore_3
    //   89: aload 5
    //   91: invokevirtual 665	java/lang/String:length	()I
    //   94: ifeq +47 -> 141
    //   97: iload_2
    //   98: istore_0
    //   99: aload 4
    //   101: astore_3
    //   102: aload 5
    //   104: ldc_w 667
    //   107: invokevirtual 671	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   110: ifeq +31 -> 141
    //   113: aload 4
    //   115: astore_3
    //   116: aload 5
    //   118: iconst_0
    //   119: aload 5
    //   121: ldc_w 667
    //   124: invokevirtual 656	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   127: invokevirtual 674	java/lang/String:substring	(II)Ljava/lang/String;
    //   130: invokevirtual 662	java/lang/String:trim	()Ljava/lang/String;
    //   133: invokestatic 589	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   136: sipush 1024
    //   139: idiv
    //   140: istore_0
    //   141: iload_0
    //   142: istore_1
    //   143: aload 4
    //   145: invokevirtual 677	java/io/BufferedReader:close	()V
    //   148: iload_0
    //   149: ireturn
    //   150: astore_3
    //   151: aload_3
    //   152: invokevirtual 678	java/io/IOException:printStackTrace	()V
    //   155: iload_1
    //   156: ireturn
    //   157: astore 5
    //   159: goto +20 -> 179
    //   162: astore 5
    //   164: goto +40 -> 204
    //   167: astore 4
    //   169: aconst_null
    //   170: astore_3
    //   171: goto +55 -> 226
    //   174: astore 5
    //   176: aconst_null
    //   177: astore 4
    //   179: aload 4
    //   181: astore_3
    //   182: aload 5
    //   184: invokevirtual 519	java/lang/Throwable:printStackTrace	()V
    //   187: aload 4
    //   189: ifnull +33 -> 222
    //   192: aload 4
    //   194: invokevirtual 677	java/io/BufferedReader:close	()V
    //   197: iconst_0
    //   198: ireturn
    //   199: astore 5
    //   201: aconst_null
    //   202: astore 4
    //   204: aload 4
    //   206: astore_3
    //   207: aload 5
    //   209: invokevirtual 678	java/io/IOException:printStackTrace	()V
    //   212: aload 4
    //   214: ifnull +8 -> 222
    //   217: aload 4
    //   219: invokevirtual 677	java/io/BufferedReader:close	()V
    //   222: iconst_0
    //   223: ireturn
    //   224: astore 4
    //   226: aload_3
    //   227: ifnull +15 -> 242
    //   230: aload_3
    //   231: invokevirtual 677	java/io/BufferedReader:close	()V
    //   234: goto +8 -> 242
    //   237: astore_3
    //   238: aload_3
    //   239: invokevirtual 678	java/io/IOException:printStackTrace	()V
    //   242: aload 4
    //   244: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   37	112	0	i	int
    //   1	155	1	j	int
    //   3	95	2	k	int
    //   28	88	3	localBufferedReader1	java.io.BufferedReader
    //   150	2	3	localIOException1	IOException
    //   170	61	3	localObject1	Object
    //   237	2	3	localIOException2	IOException
    //   24	120	4	localBufferedReader2	java.io.BufferedReader
    //   167	1	4	localObject2	Object
    //   177	41	4	localObject3	Object
    //   224	19	4	localObject4	Object
    //   34	86	5	str	String
    //   157	1	5	localThrowable1	Throwable
    //   162	1	5	localIOException3	IOException
    //   174	9	5	localThrowable2	Throwable
    //   199	9	5	localIOException4	IOException
    // Exception table:
    //   from	to	target	type
    //   143	148	150	java/io/IOException
    //   192	197	150	java/io/IOException
    //   217	222	150	java/io/IOException
    //   29	36	157	java/lang/Throwable
    //   46	55	157	java/lang/Throwable
    //   63	77	157	java/lang/Throwable
    //   89	97	157	java/lang/Throwable
    //   102	113	157	java/lang/Throwable
    //   116	141	157	java/lang/Throwable
    //   29	36	162	java/io/IOException
    //   46	55	162	java/io/IOException
    //   63	77	162	java/io/IOException
    //   89	97	162	java/io/IOException
    //   102	113	162	java/io/IOException
    //   116	141	162	java/io/IOException
    //   4	26	167	finally
    //   4	26	174	java/lang/Throwable
    //   4	26	199	java/io/IOException
    //   29	36	224	finally
    //   46	55	224	finally
    //   63	77	224	finally
    //   89	97	224	finally
    //   102	113	224	finally
    //   116	141	224	finally
    //   182	187	224	finally
    //   207	212	224	finally
    //   230	234	237	java/io/IOException
  }
  
  private void initViewParams()
  {
    this.mDensity = this.mContext.getResources().getDisplayMetrics().density;
    this.mContentHeight = ((int)(this.mWebView.getContentHeight() * this.mWebView.getScale()));
    this.mStatusBarHeight = getStatusBarHeightFromSystem();
    if (this.mStatusBarHeight <= 0)
    {
      Log.e("catch-screen", "cannot get right status bar height...");
      this.mStatusBarHeight = ((int)(this.mDensity * 25.0F));
    }
    this.mScreenWidth = this.mContext.getResources().getDisplayMetrics().widthPixels;
    this.mScreenHeight = this.mContext.getResources().getDisplayMetrics().heightPixels;
    this.mWebviewWidth = ((X5WebViewAdapter)this.mWebView).getRealWebView().getWidth();
    this.mWebviewHeight = ((X5WebViewAdapter)this.mWebView).getRealWebView().getHeight();
    this.mTitleBarHeight = (this.mScreenHeight - this.mStatusBarHeight - this.mWebviewHeight);
  }
  
  public static boolean isFileManagerInstalled(Context paramContext)
  {
    boolean bool = false;
    if (paramContext != null) {
      paramContext = paramContext.getPackageManager();
    }
    try
    {
      paramContext = paramContext.getPackageInfo("com.tencent.FileManager", 0);
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    paramContext = null;
    if (paramContext != null) {
      bool = true;
    }
    return bool;
    return false;
  }
  
  public static boolean needUpdateFileManager(Context paramContext)
  {
    boolean bool = false;
    if (paramContext != null) {
      paramContext = paramContext.getPackageManager();
    }
    try
    {
      paramContext = paramContext.getPackageInfo("com.tencent.FileManager", 0);
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    paramContext = null;
    if (paramContext == null) {
      return false;
    }
    if (paramContext.versionCode < 4610018) {
      bool = true;
    }
    return bool;
    return false;
  }
  
  public static boolean openImageWithFileManager(Context paramContext, String paramString)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("openImageWithFileManager,filePath=");
    ((StringBuilder)localObject).append(paramString);
    Log.e("catch-screen", ((StringBuilder)localObject).toString());
    localObject = new Intent();
    ((Intent)localObject).setData(Uri.parse("tencentfile://feature/dispatch?feature=19"));
    Bundle localBundle = new Bundle();
    localBundle.putInt("feature", 19);
    localBundle.putString("filepath", paramString);
    ((Intent)localObject).putExtras(localBundle);
    paramContext.startActivity((Intent)localObject);
    return true;
  }
  
  private boolean previewPicture()
  {
    if (TextUtils.isEmpty(this.mSnapshotPath)) {
      return false;
    }
    try
    {
      new g(this.mContext, this.mSnapshotPath).show();
    }
    catch (Exception localException)
    {
      Intent localIntent;
      for (;;) {}
    }
    if (isFileManagerInstalled(this.mContext))
    {
      openImageWithFileManager(this.mContext, this.mSnapshotPath);
    }
    else
    {
      localIntent = getImageFileIntent(this.mSnapshotPath);
      this.mContext.startActivity(localIntent);
    }
    return true;
  }
  
  private void removeAllMessage()
  {
    this.mSnapshotHandler.removeMessages(0);
    this.mSnapshotHandler.removeMessages(1);
    this.mSnapshotHandler.removeMessages(2);
    this.mSnapshotHandler.removeMessages(3);
  }
  
  private void removeViewMask()
  {
    try
    {
      ViewGroup localViewGroup = (ViewGroup)((X5WebViewAdapter)this.mWebView).getRealWebView().getRootView();
      localObject = localViewGroup.findViewById(Integer.MAX_VALUE);
      if (localObject != null)
      {
        localViewGroup.removeView((View)localObject);
        return;
      }
      Log.e("catch-screen", "removeViewMask fail .. mask view is null");
      return;
    }
    catch (Exception localException)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("removeViewMask throws exception:");
      ((StringBuilder)localObject).append(localException.getMessage());
      ((StringBuilder)localObject).append(",");
      ((StringBuilder)localObject).append(localException.getCause());
      Log.e("catch-screen", ((StringBuilder)localObject).toString());
      SmttServiceProxy.getInstance().userBehaviorStatistics("BZBC102");
    }
  }
  
  private int savePictureToSys(Bitmap paramBitmap)
  {
    if (paramBitmap == null) {
      return -1;
    }
    Object localObject1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append(File.separator);
    ((StringBuilder)localObject2).append("Camera");
    localObject1 = ((StringBuilder)localObject2).toString();
    File localFile = new File((String)localObject1);
    if (!localFile.exists()) {
      localFile.mkdir();
    }
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("webpage_screen_shot_");
    ((StringBuilder)localObject2).append(System.currentTimeMillis());
    ((StringBuilder)localObject2).append(".jpg");
    localObject2 = ((StringBuilder)localObject2).toString();
    localFile = new File(localFile, (String)localObject2);
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
      paramBitmap.compress(Bitmap.CompressFormat.JPEG, 50, localFileOutputStream);
      localFileOutputStream.flush();
      localFileOutputStream.close();
      paramBitmap = Uri.fromFile(localFile);
      this.mContext.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", paramBitmap));
      paramBitmap = new StringBuilder();
      paramBitmap.append((String)localObject1);
      paramBitmap.append(File.separator);
      paramBitmap.append((String)localObject2);
      this.mSnapshotPath = paramBitmap.toString();
      return 0;
    }
    catch (IOException paramBitmap)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("savePictureToSys exception:");
      ((StringBuilder)localObject1).append(paramBitmap.getMessage());
      ((StringBuilder)localObject1).append(",");
      ((StringBuilder)localObject1).append(paramBitmap.getCause());
      Log.e("catch-screen", ((StringBuilder)localObject1).toString());
      SmttServiceProxy.getInstance().userBehaviorStatistics("BZBC103");
    }
    return -2;
  }
  
  private boolean saveTmpBitmap(Bitmap paramBitmap, String paramString)
  {
    File localFile = new File("/sdcard/tmppic");
    if (!localFile.exists()) {
      localFile.mkdir();
    }
    paramString = new File(localFile, paramString);
    if (paramString.exists()) {
      paramString.delete();
    }
    try
    {
      paramString = new FileOutputStream(paramString);
      paramBitmap.compress(Bitmap.CompressFormat.JPEG, 50, paramString);
      paramString.flush();
      paramString.close();
      return true;
    }
    catch (IOException paramBitmap)
    {
      paramString = new StringBuilder();
      paramString.append("saveTmpBitmap exception:");
      paramString.append(paramBitmap.getMessage());
      paramString.append(",");
      paramString.append(paramBitmap.getCause());
      Log.e("catch-screen", paramString.toString());
    }
    return false;
  }
  
  private void sendSnapshotFinish()
  {
    this.mViewPicture.setEnabled(false);
    this.mSnapshotHandler.sendEmptyMessage(2);
  }
  
  private void showPreviewButton()
  {
    try
    {
      this.mSnapShotStatusContainer.setVisibility(4);
      View localView = ((X5WebViewAdapter)this.mWebView).getRealWebView().getRootView().findViewById(Integer.MAX_VALUE);
      if (localView != null) {
        localView.setBackgroundColor(0);
      }
      this.mViewPicture.setEnabled(true);
      if ((isFileManagerInstalled(this.mContext)) && (!needUpdateFileManager(this.mContext)))
      {
        this.mViewPicture.setText(SmttResource.getString("x5_pop_menu_snapshot_preview", "string"));
        this.mViewPicture.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            X5SnapshotMenu.this.removeViewMask();
            X5SnapshotMenu.openImageWithFileManager(X5SnapshotMenu.this.mContext, X5SnapshotMenu.this.mSnapshotPath);
            SmttServiceProxy.getInstance().userBehaviorStatistics("BZBC012");
          }
        });
        SmttServiceProxy.getInstance().userBehaviorStatistics("BZBC011");
        return;
      }
      if (needUpdateFileManager(this.mContext)) {
        this.mViewPicture.setText(SmttResource.getString("x5_pop_menu_snapshot_preview_update", "string"));
      } else {
        this.mViewPicture.setText(SmttResource.getString("x5_pop_menu_snapshot_preview_download", "string"));
      }
      this.mViewPicture.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          X5SnapshotMenu.this.mViewPicture.setEnabled(false);
          paramAnonymousView = TFMDownloadManager.getInstance();
          Context localContext = X5SnapshotMenu.this.mContext;
          X5SnapshotMenu localX5SnapshotMenu = X5SnapshotMenu.this;
          paramAnonymousView.startDownload(localContext, localX5SnapshotMenu, localX5SnapshotMenu);
          SmttServiceProxy.getInstance().userBehaviorStatistics("BZBC008");
        }
      });
      SmttServiceProxy.getInstance().userBehaviorStatistics("BZBC007");
      return;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    removeViewMask();
  }
  
  private boolean showSnapshotPreviewDialog()
  {
    try
    {
      final TbsConfirmDialog local8 = new TbsConfirmDialog(this.mContext)
      {
        public LinearLayout getContentView()
        {
          LinearLayout localLinearLayout = new LinearLayout(X5SnapshotMenu.this.mContext);
          localLinearLayout.setOrientation(1);
          localLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
          Object localObject1 = new TextView(X5SnapshotMenu.this.mContext);
          ((TextView)localObject1).setText(SmttResource.getString("x5_qq_snapshot_saved", "string"));
          ((TextView)localObject1).setTextSize(1, 20.0F);
          ((TextView)localObject1).setGravity(17);
          ((TextView)localObject1).setPadding(0, dip2px(50.0F), 0, dip2px(50.0F));
          new LinearLayout.LayoutParams(-1, -2);
          localLinearLayout.addView((View)localObject1);
          localObject1 = new ImageView(X5SnapshotMenu.this.mContext);
          Object localObject2 = new LinearLayout.LayoutParams(-1, 1);
          ((LinearLayout.LayoutParams)localObject2).weight = 0.0F;
          ((ImageView)localObject1).setLayoutParams((ViewGroup.LayoutParams)localObject2);
          ((ImageView)localObject1).setBackgroundColor(Color.rgb(217, 217, 217));
          localLinearLayout.addView((View)localObject1);
          localObject1 = new LinearLayout(X5SnapshotMenu.this.mContext);
          localObject2 = new LinearLayout.LayoutParams(-1, -2);
          ((LinearLayout.LayoutParams)localObject2).weight = 0.0F;
          ((LinearLayout)localObject1).setLayoutParams((ViewGroup.LayoutParams)localObject2);
          ((LinearLayout)localObject1).setOrientation(0);
          ((LinearLayout)localObject1).setContentDescription("x5_tbs_activity_picker_btn_container");
          localObject2 = new Button(X5SnapshotMenu.this.mContext);
          ((Button)localObject2).setOnClickListener(new View.OnClickListener()
          {
            public void onClick(View paramAnonymous2View)
            {
              if (X5SnapshotMenu.8.this.listener != null) {
                X5SnapshotMenu.8.this.listener.onCancel();
              }
            }
          });
          Object localObject3 = new LinearLayout.LayoutParams(-1, dip2px(49.0F));
          ((LinearLayout.LayoutParams)localObject3).weight = 1.0F;
          ((Button)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
          localObject3 = new StateListDrawable();
          ColorDrawable localColorDrawable = new ColorDrawable(Color.argb(41, 0, 0, 0));
          ((StateListDrawable)localObject3).addState(new int[] { 16842919 }, localColorDrawable);
          localColorDrawable = new ColorDrawable(0);
          ((StateListDrawable)localObject3).addState(new int[] { -16842919 }, localColorDrawable);
          ((Button)localObject2).setBackgroundDrawable((Drawable)localObject3);
          ((Button)localObject2).setText(SmttResource.getString("x5_cancel", "string"));
          ((Button)localObject2).setTextColor(Color.rgb(102, 102, 102));
          ((Button)localObject2).setTextSize(1, 18.0F);
          ((LinearLayout)localObject1).addView((View)localObject2);
          localObject2 = new ImageView(X5SnapshotMenu.this.mContext);
          localObject3 = new LinearLayout.LayoutParams(1, -1);
          ((LinearLayout.LayoutParams)localObject3).weight = 0.0F;
          ((ImageView)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
          ((ImageView)localObject2).setBackgroundColor(Color.rgb(217, 217, 217));
          ((LinearLayout)localObject1).addView((View)localObject2);
          localObject2 = new Button(X5SnapshotMenu.this.mContext);
          ((Button)localObject2).setOnClickListener(new View.OnClickListener()
          {
            public void onClick(View paramAnonymous2View)
            {
              if (X5SnapshotMenu.8.this.listener != null) {
                X5SnapshotMenu.8.this.listener.onSure();
              }
            }
          });
          localObject3 = new LinearLayout.LayoutParams(-1, dip2px(49.0F));
          ((LinearLayout.LayoutParams)localObject3).weight = 1.0F;
          ((Button)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
          localObject3 = new StateListDrawable();
          localColorDrawable = new ColorDrawable(Color.argb(41, 0, 0, 0));
          ((StateListDrawable)localObject3).addState(new int[] { 16842919 }, localColorDrawable);
          localColorDrawable = new ColorDrawable(0);
          ((StateListDrawable)localObject3).addState(new int[] { -16842919 }, localColorDrawable);
          ((Button)localObject2).setBackgroundDrawable((Drawable)localObject3);
          ((Button)localObject2).setText(SmttResource.getString("x5_qq_snapshot_check", "string"));
          ((Button)localObject2).setTextColor(Color.rgb(76, 154, 250));
          ((Button)localObject2).setTextSize(1, 18.0F);
          ((LinearLayout)localObject1).addView((View)localObject2);
          localLinearLayout.addView((View)localObject1);
          return localLinearLayout;
        }
      };
      local8.setListener(new TbsConfirmDialog.BtnClickListener()
      {
        public void onCancel()
        {
          local8.dismiss();
        }
        
        public void onSure()
        {
          X5SnapshotMenu.this.previewPicture();
          local8.dismiss();
        }
      });
      local8.show();
      return true;
    }
    catch (Throwable localThrowable)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("showSnapshotPreviewDialog exception:");
      localStringBuilder.append(localThrowable.getMessage());
      localStringBuilder.append(",");
      localStringBuilder.append(localThrowable.getCause());
      Log.e("catch-screen", localStringBuilder.toString());
    }
    return false;
  }
  
  private boolean snapShotVisible(Bitmap paramBitmap, Runnable paramRunnable)
  {
    try
    {
      this.mWebView.snapshotVisibleWithBitmap(paramBitmap, false, false, false, false, 1.0F, 1.0F);
      return true;
    }
    catch (Exception paramBitmap)
    {
      paramRunnable = new StringBuilder();
      paramRunnable.append(" snapShotVisible exception:");
      paramRunnable.append(paramBitmap.getCause());
      paramRunnable.append(",");
      paramRunnable.append(paramBitmap.getMessage());
      Log.e("catch-screen", paramRunnable.toString());
      SmttServiceProxy.getInstance().userBehaviorStatistics("BZBC100");
    }
    return false;
  }
  
  @TargetApi(11)
  private void startAnimationAndSendLoopMessage(int paramInt1, int paramInt2)
  {
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofInt(((X5WebViewAdapter)this.mWebView).getRealWebView(), "scrollY", new int[] { paramInt1, paramInt2 });
    localObjectAnimator.setDuration(500L).start();
    localObjectAnimator.addListener(new Animator.AnimatorListener()
    {
      public void onAnimationCancel(Animator paramAnonymousAnimator) {}
      
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        if (!X5SnapshotMenu.this.mSnapshotFinished) {
          X5SnapshotMenu.this.mWebView.evaluateJavascript("isImgInViewport();", new ValueCallback()
          {
            public void onReceiveValue(String paramAnonymous2String)
            {
              if (paramAnonymous2String.equalsIgnoreCase("true"))
              {
                X5SnapshotMenu.this.mSnapshotHandler.sendEmptyMessageDelayed(1, 1000L);
                return;
              }
              X5SnapshotMenu.this.mSnapshotHandler.sendEmptyMessage(1);
            }
          });
        }
      }
      
      public void onAnimationRepeat(Animator paramAnonymousAnimator) {}
      
      public void onAnimationStart(Animator paramAnonymousAnimator) {}
    });
  }
  
  public void enterSnapshotMode()
  {
    this.mSnapshotFinished = false;
    initViewParams();
    removeViewMask();
    if (createSnapshotViewMask())
    {
      createHandler();
      this.mSnapshotHandler.sendEmptyMessage(0);
      return;
    }
    Log.e("catch-screen", "enterSnapshotMode --  createSnapshotViewMask return false");
    exitSnapshotMode();
  }
  
  public void onDownloadFailed(Bundle paramBundle)
  {
    Log.e("catch-screen", "TF onDownloadFailed");
    this.mViewPicture.setText(SmttResource.getString("x5_pop_menu_snapshot_download_error", "string"));
    this.mViewPicture.setEnabled(true);
    TFMDownloadManager.getInstance().unregisterTFInstallBroadcastReceiver(this.mContext);
    TFMDownloadManager.getInstance().unregisterTFDownloadListener(this);
    TFMDownloadManager.getInstance().unregisterTFInstallListener(this);
    SmttServiceProxy.getInstance().userBehaviorStatistics("BZBC013");
  }
  
  public void onDownloadPause(int paramInt) {}
  
  public void onDownloadProgress(int paramInt)
  {
    TextView localTextView = this.mViewPicture;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(SmttResource.getString("x5_pop_menu_snapshot_downloading", "string"));
    localStringBuilder.append("(");
    localStringBuilder.append(paramInt);
    localStringBuilder.append("%)");
    localTextView.setText(localStringBuilder.toString());
  }
  
  public void onDownloadResume(int paramInt) {}
  
  public void onDownloadStart() {}
  
  public void onDownloadSucess(String paramString, Bundle paramBundle)
  {
    SmttServiceProxy.getInstance().userBehaviorStatistics("BZBC010");
    this.mSnapshotHandler.removeMessages(4);
    this.mSnapshotHandler.sendEmptyMessage(4);
    TFMDownloadManager.getInstance().unregisterTFDownloadListener(this);
  }
  
  public boolean onInstallFinished()
  {
    this.mSnapshotHandler.removeMessages(5);
    this.mSnapshotHandler.sendEmptyMessage(5);
    SmttServiceProxy.getInstance().userBehaviorStatistics("BZBC014");
    TFMDownloadManager.getInstance().unregisterTFInstallBroadcastReceiver(this.mContext);
    TFMDownloadManager.getInstance().unregisterTFInstallListener(this);
    return false;
  }
  
  public boolean onInstalling()
  {
    return false;
  }
  
  public boolean onUninstallFinished()
  {
    return false;
  }
  
  static class SnapShotHandler
    extends Handler
  {
    private WeakReference<Context> mContext;
    
    public SnapShotHandler(Context paramContext)
    {
      this.mContext = new WeakReference(paramContext);
    }
    
    public void handleMessage(Message paramMessage)
    {
      super.handleMessage(paramMessage);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\menu\X5SnapshotMenu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */