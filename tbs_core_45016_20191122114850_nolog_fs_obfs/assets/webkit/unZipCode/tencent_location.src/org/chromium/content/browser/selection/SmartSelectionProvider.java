package org.chromium.content.browser.selection;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.LocaleList;
import android.support.annotation.IntDef;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextSelection;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.Locale;
import org.chromium.content_public.browser.SelectionClient.Result;
import org.chromium.content_public.browser.SelectionClient.ResultCallback;
import org.chromium.ui.base.WindowAndroid;

public class SmartSelectionProvider
{
  private Handler jdField_a_of_type_AndroidOsHandler;
  private TextClassifier jdField_a_of_type_AndroidViewTextclassifierTextClassifier;
  private Runnable jdField_a_of_type_JavaLangRunnable;
  private ClassificationTask jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionProvider$ClassificationTask;
  private SelectionClient.ResultCallback jdField_a_of_type_OrgChromiumContent_publicBrowserSelectionClient$ResultCallback;
  private WindowAndroid jdField_a_of_type_OrgChromiumUiBaseWindowAndroid;
  
  public SmartSelectionProvider(SelectionClient.ResultCallback paramResultCallback, WindowAndroid paramWindowAndroid)
  {
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserSelectionClient$ResultCallback = paramResultCallback;
    this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid = paramWindowAndroid;
    this.jdField_a_of_type_AndroidOsHandler = new Handler();
    this.jdField_a_of_type_JavaLangRunnable = new Runnable()
    {
      public void run()
      {
        SmartSelectionProvider.a(SmartSelectionProvider.this).onClassified(new SelectionClient.Result());
      }
    };
  }
  
  @TargetApi(26)
  private void a(int paramInt1, CharSequence paramCharSequence, int paramInt2, int paramInt3, Locale[] paramArrayOfLocale)
  {
    TextClassifier localTextClassifier = getTextClassifier();
    if ((localTextClassifier != null) && (localTextClassifier != TextClassifier.NO_OP))
    {
      ClassificationTask localClassificationTask = this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionProvider$ClassificationTask;
      if (localClassificationTask != null)
      {
        localClassificationTask.cancel(false);
        this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionProvider$ClassificationTask = null;
      }
      this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionProvider$ClassificationTask = new ClassificationTask(localTextClassifier, paramInt1, paramCharSequence, paramInt2, paramInt3, paramArrayOfLocale);
      this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionProvider$ClassificationTask.execute(new Void[0]);
      return;
    }
    this.jdField_a_of_type_AndroidOsHandler.post(this.jdField_a_of_type_JavaLangRunnable);
  }
  
  public void cancelAllRequests()
  {
    ClassificationTask localClassificationTask = this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionProvider$ClassificationTask;
    if (localClassificationTask != null)
    {
      localClassificationTask.cancel(false);
      this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionProvider$ClassificationTask = null;
    }
  }
  
  public TextClassifier getCustomTextClassifier()
  {
    return this.jdField_a_of_type_AndroidViewTextclassifierTextClassifier;
  }
  
  @SuppressLint({"WrongConstant"})
  @TargetApi(26)
  public TextClassifier getTextClassifier()
  {
    Object localObject = this.jdField_a_of_type_AndroidViewTextclassifierTextClassifier;
    if (localObject != null) {
      return (TextClassifier)localObject;
    }
    localObject = (Context)this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid.getContext().get();
    if (localObject == null) {
      return null;
    }
    return ((TextClassificationManager)((Context)localObject).getSystemService("textclassification")).getTextClassifier();
  }
  
  public void sendClassifyRequest(CharSequence paramCharSequence, int paramInt1, int paramInt2, Locale[] paramArrayOfLocale)
  {
    a(0, paramCharSequence, paramInt1, paramInt2, paramArrayOfLocale);
  }
  
  public void sendSuggestAndClassifyRequest(CharSequence paramCharSequence, int paramInt1, int paramInt2, Locale[] paramArrayOfLocale)
  {
    a(1, paramCharSequence, paramInt1, paramInt2, paramArrayOfLocale);
  }
  
  public void setTextClassifier(TextClassifier paramTextClassifier)
  {
    this.jdField_a_of_type_AndroidViewTextclassifierTextClassifier = paramTextClassifier;
  }
  
  @TargetApi(26)
  private class ClassificationTask
    extends AsyncTask<Void, Void, SelectionClient.Result>
  {
    private final int jdField_a_of_type_Int;
    private final TextClassifier jdField_a_of_type_AndroidViewTextclassifierTextClassifier;
    private final CharSequence jdField_a_of_type_JavaLangCharSequence;
    private final Locale[] jdField_a_of_type_ArrayOfJavaUtilLocale;
    private final int b;
    private final int c;
    
    ClassificationTask(TextClassifier paramTextClassifier, int paramInt1, CharSequence paramCharSequence, int paramInt2, int paramInt3, Locale[] paramArrayOfLocale)
    {
      this.jdField_a_of_type_AndroidViewTextclassifierTextClassifier = paramTextClassifier;
      this.jdField_a_of_type_Int = paramInt1;
      this.jdField_a_of_type_JavaLangCharSequence = paramCharSequence;
      this.b = paramInt2;
      this.c = paramInt3;
      this.jdField_a_of_type_ArrayOfJavaUtilLocale = paramArrayOfLocale;
    }
    
    @SuppressLint({"NewApi"})
    private LocaleList a(Locale[] paramArrayOfLocale)
    {
      if ((paramArrayOfLocale != null) && (paramArrayOfLocale.length != 0)) {
        return new LocaleList(paramArrayOfLocale);
      }
      return null;
    }
    
    private SelectionClient.Result a(int paramInt1, int paramInt2, TextClassification paramTextClassification, TextSelection paramTextSelection)
    {
      SelectionClient.Result localResult = new SelectionClient.Result();
      localResult.jdField_a_of_type_Int = (paramInt1 - this.b);
      localResult.b = (paramInt2 - this.c);
      localResult.jdField_a_of_type_JavaLangCharSequence = paramTextClassification.getLabel();
      localResult.jdField_a_of_type_AndroidGraphicsDrawableDrawable = paramTextClassification.getIcon();
      localResult.jdField_a_of_type_AndroidContentIntent = paramTextClassification.getIntent();
      localResult.jdField_a_of_type_AndroidViewView$OnClickListener = paramTextClassification.getOnClickListener();
      localResult.jdField_a_of_type_AndroidViewTextclassifierTextSelection = paramTextSelection;
      localResult.jdField_a_of_type_AndroidViewTextclassifierTextClassification = paramTextClassification;
      return localResult;
    }
    
    protected SelectionClient.Result a(Void... paramVarArgs)
    {
      int i = this.b;
      int j = this.c;
      if (this.jdField_a_of_type_Int == 1)
      {
        paramVarArgs = this.jdField_a_of_type_AndroidViewTextclassifierTextClassifier.suggestSelection(this.jdField_a_of_type_JavaLangCharSequence, i, j, a(this.jdField_a_of_type_ArrayOfJavaUtilLocale));
        i = Math.max(0, paramVarArgs.getSelectionStartIndex());
        j = Math.min(this.jdField_a_of_type_JavaLangCharSequence.length(), paramVarArgs.getSelectionEndIndex());
        if (isCancelled()) {
          return new SelectionClient.Result();
        }
      }
      else
      {
        paramVarArgs = null;
      }
      return a(i, j, this.jdField_a_of_type_AndroidViewTextclassifierTextClassifier.classifyText(this.jdField_a_of_type_JavaLangCharSequence, i, j, a(this.jdField_a_of_type_ArrayOfJavaUtilLocale)), paramVarArgs);
    }
    
    protected void a(SelectionClient.Result paramResult)
    {
      SmartSelectionProvider.a(SmartSelectionProvider.this).onClassified(paramResult);
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  @IntDef({0L, 1L})
  private static @interface RequestType {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\selection\SmartSelectionProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */