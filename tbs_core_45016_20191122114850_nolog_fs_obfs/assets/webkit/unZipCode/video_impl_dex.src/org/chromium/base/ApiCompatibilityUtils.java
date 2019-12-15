package org.chromium.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager.TaskDescription;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.PowerManager;
import android.os.Process;
import android.os.StatFs;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.os.UserManager;
import android.provider.Settings.Global;
import android.provider.Settings.Secure;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.inputmethod.InputMethodSubtype;
import android.view.textclassifier.TextClassifier;
import android.widget.TextView;
import java.io.File;
import java.io.UnsupportedEncodingException;

@TargetApi(21)
public class ApiCompatibilityUtils
{
  public static int checkPermission(Context paramContext, String paramString, int paramInt1, int paramInt2)
  {
    try
    {
      paramInt1 = paramContext.checkPermission(paramString, paramInt1, paramInt2);
      return paramInt1;
    }
    catch (RuntimeException paramContext)
    {
      for (;;) {}
    }
    return -1;
  }
  
  public static int compareBoolean(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean1 == paramBoolean2) {
      return 0;
    }
    if (paramBoolean1) {
      return 1;
    }
    return -1;
  }
  
  public static int compareLong(long paramLong1, long paramLong2)
  {
    if (paramLong1 < paramLong2) {
      return -1;
    }
    if (paramLong1 == paramLong2) {
      return 0;
    }
    return 1;
  }
  
  @TargetApi(26)
  public static void disableSmartSelectionTextClassifier(TextView paramTextView)
  {
    if (Build.VERSION.SDK_INT < 26) {
      return;
    }
    paramTextView.setTextClassifier(TextClassifier.NO_OP);
  }
  
  public static void finishAfterTransition(Activity paramActivity)
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      paramActivity.finishAfterTransition();
      return;
    }
    paramActivity.finish();
  }
  
  public static void finishAndRemoveTask(Activity paramActivity)
  {
    if (Build.VERSION.SDK_INT > 21)
    {
      paramActivity.finishAndRemoveTask();
      return;
    }
    if (Build.VERSION.SDK_INT == 21)
    {
      new FinishAndRemoveTaskWithRetry(paramActivity).run();
      return;
    }
    paramActivity.finish();
  }
  
  public static int getActivityNewDocumentFlag()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return 524288;
    }
    return 524288;
  }
  
  public static long getAvailableBlocks(StatFs paramStatFs)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return paramStatFs.getAvailableBlocksLong();
    }
    return paramStatFs.getAvailableBlocks();
  }
  
  public static long getBlockCount(StatFs paramStatFs)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return paramStatFs.getBlockCountLong();
    }
    return paramStatFs.getBlockCount();
  }
  
  public static long getBlockSize(StatFs paramStatFs)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return paramStatFs.getBlockSizeLong();
    }
    return paramStatFs.getBlockSize();
  }
  
  public static byte[] getBytesUtf8(String paramString)
  {
    try
    {
      paramString = paramString.getBytes("UTF-8");
      return paramString;
    }
    catch (UnsupportedEncodingException paramString)
    {
      throw new IllegalStateException("UTF-8 encoding not available.", paramString);
    }
  }
  
  public static int getColor(Resources paramResources, int paramInt)
    throws Resources.NotFoundException
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return paramResources.getColor(paramInt, null);
    }
    return paramResources.getColor(paramInt);
  }
  
  public static ColorFilter getColorFilter(Drawable paramDrawable)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return paramDrawable.getColorFilter();
    }
    return null;
  }
  
  public static ColorStateList getColorStateList(Resources paramResources, int paramInt)
    throws Resources.NotFoundException
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return paramResources.getColorStateList(paramInt, null);
    }
    return paramResources.getColorStateList(paramInt);
  }
  
  public static String getCreatorPackage(PendingIntent paramPendingIntent)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return paramPendingIntent.getCreatorPackage();
    }
    return paramPendingIntent.getTargetPackage();
  }
  
  public static Drawable getDrawable(Resources paramResources, int paramInt)
    throws Resources.NotFoundException
  {
    StrictMode.ThreadPolicy localThreadPolicy = StrictMode.allowThreadDiskReads();
    try
    {
      if (Build.VERSION.SDK_INT >= 21)
      {
        paramResources = paramResources.getDrawable(paramInt, null);
        return paramResources;
      }
      paramResources = paramResources.getDrawable(paramInt);
      return paramResources;
    }
    finally
    {
      StrictMode.setThreadPolicy(localThreadPolicy);
    }
  }
  
  public static Drawable getDrawableForDensity(Resources paramResources, int paramInt1, int paramInt2)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return paramResources.getDrawableForDensity(paramInt1, paramInt2, null);
    }
    return paramResources.getDrawableForDensity(paramInt1, paramInt2);
  }
  
  public static int getLayoutDirection(Configuration paramConfiguration)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return paramConfiguration.getLayoutDirection();
    }
    return 0;
  }
  
  public static String getLocale(InputMethodSubtype paramInputMethodSubtype)
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return paramInputMethodSubtype.getLanguageTag();
    }
    return paramInputMethodSubtype.getLocale();
  }
  
  public static int getMarginEnd(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return paramMarginLayoutParams.getMarginEnd();
    }
    return paramMarginLayoutParams.rightMargin;
  }
  
  public static int getMarginStart(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return paramMarginLayoutParams.getMarginStart();
    }
    return paramMarginLayoutParams.leftMargin;
  }
  
  public static int getPaddingEnd(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return paramView.getPaddingEnd();
    }
    return paramView.getPaddingRight();
  }
  
  public static int getPaddingStart(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return paramView.getPaddingStart();
    }
    return paramView.getPaddingLeft();
  }
  
  public static Uri getUriForDownloadedFile(File paramFile)
  {
    if (Build.VERSION.SDK_INT > 23) {
      return FileUtils.getUriForFile(paramFile);
    }
    return Uri.fromFile(paramFile);
  }
  
  public static Uri getUriForImageCaptureFile(File paramFile)
  {
    if (Build.VERSION.SDK_INT >= 18) {
      return ContentUriUtils.getContentUriFromFile(paramFile);
    }
    return Uri.fromFile(paramFile);
  }
  
  public static Drawable getUserBadgedDrawableForDensity(Context paramContext, Drawable paramDrawable, Rect paramRect, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return paramContext.getPackageManager().getUserBadgedDrawableForDensity(paramDrawable, Process.myUserHandle(), paramRect, paramInt);
    }
    return paramDrawable;
  }
  
  public static Drawable getUserBadgedIcon(Context paramContext, int paramInt)
  {
    Drawable localDrawable2 = getDrawable(paramContext.getResources(), paramInt);
    Drawable localDrawable1 = localDrawable2;
    if (Build.VERSION.SDK_INT >= 21) {
      localDrawable1 = paramContext.getPackageManager().getUserBadgedIcon(localDrawable2, Process.myUserHandle());
    }
    return localDrawable1;
  }
  
  public static boolean isDemoUser(Context paramContext)
  {
    if (Build.VERSION.SDK_INT < 25) {
      return false;
    }
    return ((UserManager)paramContext.getSystemService("user")).isDemoUser();
  }
  
  @TargetApi(17)
  public static boolean isDeviceProvisioned(Context paramContext)
  {
    if (Build.VERSION.SDK_INT < 17) {
      return true;
    }
    if (paramContext == null) {
      return true;
    }
    if (paramContext.getContentResolver() == null) {
      return true;
    }
    return Settings.Global.getInt(paramContext.getContentResolver(), "device_provisioned", 0) != 0;
  }
  
  public static boolean isElevationSupported()
  {
    return Build.VERSION.SDK_INT >= 21;
  }
  
  public static boolean isInMultiWindowMode(Activity paramActivity)
  {
    if (Build.VERSION.SDK_INT < 24) {
      return false;
    }
    return paramActivity.isInMultiWindowMode();
  }
  
  public static boolean isInteractive(Context paramContext)
  {
    paramContext = (PowerManager)paramContext.getSystemService("power");
    if (Build.VERSION.SDK_INT >= 20) {
      return paramContext.isInteractive();
    }
    return paramContext.isScreenOn();
  }
  
  public static boolean isLayoutRtl(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return paramView.getLayoutDirection() == 1;
    }
    return false;
  }
  
  public static boolean isPrintingSupported()
  {
    return Build.VERSION.SDK_INT >= 19;
  }
  
  public static boolean objectEquals(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 == null) {
      return paramObject2 == null;
    }
    return paramObject1.equals(paramObject2);
  }
  
  public static void setCompoundDrawablesRelative(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
  {
    if (Build.VERSION.SDK_INT == 17)
    {
      boolean bool = isLayoutRtl(paramTextView);
      Drawable localDrawable;
      if (bool) {
        localDrawable = paramDrawable3;
      } else {
        localDrawable = paramDrawable1;
      }
      if (!bool) {
        paramDrawable1 = paramDrawable3;
      }
      paramTextView.setCompoundDrawables(localDrawable, paramDrawable2, paramDrawable1, paramDrawable4);
      return;
    }
    if (Build.VERSION.SDK_INT > 17)
    {
      paramTextView.setCompoundDrawablesRelative(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
      return;
    }
    paramTextView.setCompoundDrawables(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
  }
  
  public static void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (Build.VERSION.SDK_INT == 17)
    {
      boolean bool = isLayoutRtl(paramTextView);
      int i;
      if (bool) {
        i = paramInt3;
      } else {
        i = paramInt1;
      }
      if (!bool) {
        paramInt1 = paramInt3;
      }
      paramTextView.setCompoundDrawablesWithIntrinsicBounds(i, paramInt2, paramInt1, paramInt4);
      return;
    }
    if (Build.VERSION.SDK_INT > 17)
    {
      paramTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(paramInt1, paramInt2, paramInt3, paramInt4);
      return;
    }
    paramTextView.setCompoundDrawablesWithIntrinsicBounds(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public static void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
  {
    if (Build.VERSION.SDK_INT == 17)
    {
      boolean bool = isLayoutRtl(paramTextView);
      Drawable localDrawable;
      if (bool) {
        localDrawable = paramDrawable3;
      } else {
        localDrawable = paramDrawable1;
      }
      if (!bool) {
        paramDrawable1 = paramDrawable3;
      }
      paramTextView.setCompoundDrawablesWithIntrinsicBounds(localDrawable, paramDrawable2, paramDrawable1, paramDrawable4);
      return;
    }
    if (Build.VERSION.SDK_INT > 17)
    {
      paramTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
      return;
    }
    paramTextView.setCompoundDrawablesWithIntrinsicBounds(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
  }
  
  @TargetApi(21)
  public static boolean setElevation(View paramView, float paramFloat)
  {
    if (!isElevationSupported()) {
      return false;
    }
    paramView.setElevation(paramFloat);
    return true;
  }
  
  public static void setLabelFor(View paramView, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      paramView.setLabelFor(paramInt);
    }
  }
  
  public static void setLayoutDirection(View paramView, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      paramView.setLayoutDirection(paramInt);
    }
  }
  
  public static void setMarginEnd(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 17)
    {
      paramMarginLayoutParams.setMarginEnd(paramInt);
      return;
    }
    paramMarginLayoutParams.rightMargin = paramInt;
  }
  
  public static void setMarginStart(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 17)
    {
      paramMarginLayoutParams.setMarginStart(paramInt);
      return;
    }
    paramMarginLayoutParams.leftMargin = paramInt;
  }
  
  public static void setPaddingRelative(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (Build.VERSION.SDK_INT >= 17)
    {
      paramView.setPaddingRelative(paramInt1, paramInt2, paramInt3, paramInt4);
      return;
    }
    paramView.setPadding(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public static void setStatusBarColor(Window paramWindow, int paramInt)
  {
    if (Build.VERSION.SDK_INT < 21) {
      return;
    }
    if ((Build.VERSION.SDK_INT < 26) && (paramInt == -16777216) && (paramWindow.getNavigationBarColor() == -16777216)) {
      paramWindow.clearFlags(Integer.MIN_VALUE);
    } else {
      paramWindow.addFlags(Integer.MIN_VALUE);
    }
    paramWindow.setStatusBarColor(paramInt);
  }
  
  public static void setTaskDescription(Activity paramActivity, String paramString, Bitmap paramBitmap, int paramInt)
  {
    if ((!a) && (Color.alpha(paramInt) != 255)) {
      throw new AssertionError();
    }
    if (Build.VERSION.SDK_INT >= 21) {
      paramActivity.setTaskDescription(new ActivityManager.TaskDescription(paramString, paramBitmap, paramInt));
    }
  }
  
  public static void setTextAlignment(View paramView, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      paramView.setTextAlignment(paramInt);
    }
  }
  
  public static void setTextAppearance(TextView paramTextView, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 23)
    {
      paramTextView.setTextAppearance(paramInt);
      return;
    }
    paramTextView.setTextAppearance(paramTextView.getContext(), paramInt);
  }
  
  public static void setTextDirection(View paramView, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      paramView.setTextDirection(paramInt);
    }
  }
  
  public static void setWindowIndeterminateProgress(Window paramWindow)
  {
    if (Build.VERSION.SDK_INT < 21) {
      paramWindow.setFeatureInt(5, -2);
    }
  }
  
  public static boolean shouldSkipFirstUseHints(ContentResolver paramContentResolver)
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool = false;
    if (i >= 21)
    {
      if (Settings.Secure.getInt(paramContentResolver, "skip_first_use_hints", 0) != 0) {
        bool = true;
      }
      return bool;
    }
    return false;
  }
  
  public static String toHtml(Spanned paramSpanned, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return Html.toHtml(paramSpanned, paramInt);
    }
    return Html.toHtml(paramSpanned);
  }
  
  private static class FinishAndRemoveTaskWithRetry
    implements Runnable
  {
    private int jdField_a_of_type_Int;
    private final Activity jdField_a_of_type_AndroidAppActivity;
    
    FinishAndRemoveTaskWithRetry(Activity paramActivity)
    {
      this.jdField_a_of_type_AndroidAppActivity = paramActivity;
    }
    
    public void run()
    {
      this.jdField_a_of_type_AndroidAppActivity.finishAndRemoveTask();
      this.jdField_a_of_type_Int += 1;
      if (!this.jdField_a_of_type_AndroidAppActivity.isFinishing())
      {
        if (this.jdField_a_of_type_Int < 3L)
        {
          ThreadUtils.postOnUiThreadDelayed(this, 500L);
          return;
        }
        this.jdField_a_of_type_AndroidAppActivity.finish();
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\ApiCompatibilityUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */