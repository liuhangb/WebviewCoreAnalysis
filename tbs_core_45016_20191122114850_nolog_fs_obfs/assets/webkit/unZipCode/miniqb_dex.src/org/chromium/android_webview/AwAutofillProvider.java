package org.chromium.android_webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.ViewStructure.HtmlInfo.Builder;
import android.view.autofill.AutofillValue;
import java.util.ArrayList;
import java.util.Iterator;
import org.chromium.base.ThreadUtils;
import org.chromium.components.autofill.AutofillProvider;
import org.chromium.components.autofill.FormData;
import org.chromium.components.autofill.FormFieldData;
import org.chromium.content_public.browser.WebContents;
import org.chromium.ui.base.WindowAndroid;
import org.chromium.ui.display.DisplayAndroid;

@TargetApi(26)
public class AwAutofillProvider
  extends AutofillProvider
{
  private long jdField_a_of_type_Long;
  private ViewGroup jdField_a_of_type_AndroidViewViewGroup;
  private AwAutofillManager jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillManager;
  private AutofillRequest jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest;
  private WebContents jdField_a_of_type_OrgChromiumContent_publicBrowserWebContents;
  
  public AwAutofillProvider(Context paramContext, ViewGroup paramViewGroup)
  {
    if ((!jdField_a_of_type_Boolean) && (Build.VERSION.SDK_INT < 26)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillManager = new AwAutofillManager(paramContext);
    this.jdField_a_of_type_AndroidViewViewGroup = paramViewGroup;
  }
  
  public AwAutofillProvider(ViewGroup paramViewGroup, AwAutofillManager paramAwAutofillManager)
  {
    if ((!jdField_a_of_type_Boolean) && (Build.VERSION.SDK_INT < 26)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillManager = paramAwAutofillManager;
    this.jdField_a_of_type_AndroidViewViewGroup = paramViewGroup;
  }
  
  private Rect a(RectF paramRectF)
  {
    float f = this.jdField_a_of_type_OrgChromiumContent_publicBrowserWebContents.getTopLevelNativeWindow().getDisplay().getDipScale();
    paramRectF = new RectF(paramRectF);
    Matrix localMatrix = new Matrix();
    localMatrix.setScale(f, f);
    int[] arrayOfInt = new int[2];
    this.jdField_a_of_type_AndroidViewViewGroup.getLocationOnScreen(arrayOfInt);
    localMatrix.postTranslate(arrayOfInt[0], arrayOfInt[1]);
    localMatrix.mapRect(paramRectF);
    return new Rect((int)paramRectF.left, (int)paramRectF.top, (int)paramRectF.right, (int)paramRectF.bottom);
  }
  
  private void a()
  {
    if (this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest == null) {
      return;
    }
    int i = 0;
    while (i < this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest.getFieldCount())
    {
      a(i);
      i += 1;
    }
  }
  
  private void a(int paramInt)
  {
    AutofillValue localAutofillValue = this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest.getFieldNewValue(paramInt);
    if (localAutofillValue == null) {
      return;
    }
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillManager.notifyVirtualValueChanged(this.jdField_a_of_type_AndroidViewViewGroup, this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest.getVirtualId((short)paramInt), localAutofillValue);
  }
  
  public void autofill(SparseArray<AutofillValue> paramSparseArray)
  {
    if (this.jdField_a_of_type_Long != 0L)
    {
      AutofillRequest localAutofillRequest = this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest;
      if ((localAutofillRequest != null) && (localAutofillRequest.autofill(paramSparseArray))) {
        a(this.jdField_a_of_type_Long, AutofillRequest.a(this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest));
      }
    }
  }
  
  public void onContainerViewChanged(ViewGroup paramViewGroup)
  {
    this.jdField_a_of_type_AndroidViewViewGroup = paramViewGroup;
  }
  
  protected void onDidFillAutofillFormData()
  {
    a();
  }
  
  public void onFocusChanged(boolean paramBoolean, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    Object localObject1 = this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest;
    if (localObject1 == null) {
      return;
    }
    Object localObject2 = ((AutofillRequest)localObject1).getFocusField();
    if (paramBoolean)
    {
      localObject1 = a(new RectF(paramFloat1, paramFloat2, paramFloat3 + paramFloat1, paramFloat4 + paramFloat2));
      if ((localObject2 != null) && (((FocusField)localObject2).fieldIndex == paramInt) && (((Rect)localObject1).equals(((FocusField)localObject2).absBound))) {
        return;
      }
      if (localObject2 != null) {
        this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillManager.notifyVirtualViewExited(this.jdField_a_of_type_AndroidViewViewGroup, this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest.getVirtualId(((FocusField)localObject2).fieldIndex));
      }
      localObject2 = this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillManager;
      ViewGroup localViewGroup = this.jdField_a_of_type_AndroidViewViewGroup;
      AutofillRequest localAutofillRequest = this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest;
      short s = (short)paramInt;
      ((AwAutofillManager)localObject2).notifyVirtualViewEntered(localViewGroup, localAutofillRequest.getVirtualId(s), (Rect)localObject1);
      a(paramInt);
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest.setFocusField(new FocusField(s, (Rect)localObject1));
      return;
    }
    if (localObject2 == null) {
      return;
    }
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillManager.notifyVirtualViewExited(this.jdField_a_of_type_AndroidViewViewGroup, this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest.getVirtualId(((FocusField)localObject2).fieldIndex));
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest.setFocusField(null);
  }
  
  public void onFormSubmitted(int paramInt)
  {
    a();
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillManager.commit(paramInt);
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest = null;
  }
  
  public void onProvideAutoFillVirtualStructure(ViewStructure paramViewStructure, int paramInt)
  {
    AutofillRequest localAutofillRequest = this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest;
    if (localAutofillRequest == null) {
      return;
    }
    localAutofillRequest.fillViewStructure(paramViewStructure);
  }
  
  public void onTextFieldDidChange(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    Object localObject = this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest;
    if (localObject == null) {
      return;
    }
    short s = (short)paramInt;
    localObject = ((AutofillRequest)localObject).getFocusField();
    if ((localObject != null) && (s == ((FocusField)localObject).fieldIndex))
    {
      int i = this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest.getVirtualId(s);
      Rect localRect = a(new RectF(paramFloat1, paramFloat2, paramFloat3 + paramFloat1, paramFloat4 + paramFloat2));
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillManager.notifyVirtualViewExited(this.jdField_a_of_type_AndroidViewViewGroup, i);
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillManager.notifyVirtualViewEntered(this.jdField_a_of_type_AndroidViewViewGroup, i, localRect);
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest.setFocusField(new FocusField(((FocusField)localObject).fieldIndex, localRect));
    }
    else
    {
      onFocusChanged(true, paramInt, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    }
    a(paramInt);
  }
  
  public void onTextFieldDidScroll(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (Build.VERSION.SDK_INT > 27) {
      return;
    }
    Object localObject = this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest;
    if (localObject == null) {
      return;
    }
    short s = (short)paramInt;
    localObject = ((AutofillRequest)localObject).getFocusField();
    if (localObject != null)
    {
      if (s != ((FocusField)localObject).fieldIndex) {
        return;
      }
      paramInt = this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest.getVirtualId(s);
      Rect localRect = a(new RectF(paramFloat1, paramFloat2, paramFloat3 + paramFloat1, paramFloat4 + paramFloat2));
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillManager.notifyVirtualViewEntered(this.jdField_a_of_type_AndroidViewViewGroup, paramInt, localRect);
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest.setFocusField(new FocusField(((FocusField)localObject).fieldIndex, localRect));
      return;
    }
  }
  
  public void queryAutofillSuggestion()
  {
    if (shouldQueryAutofillSuggestion())
    {
      FocusField localFocusField = this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest.getFocusField();
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillManager.requestAutofill(this.jdField_a_of_type_AndroidViewViewGroup, this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest.getVirtualId(localFocusField.fieldIndex), localFocusField.absBound);
    }
  }
  
  protected void reset() {}
  
  protected void setNativeAutofillProvider(long paramLong)
  {
    long l = this.jdField_a_of_type_Long;
    if (paramLong == l) {
      return;
    }
    if (l != 0L) {}
    try
    {
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest = null;
      this.jdField_a_of_type_Long = paramLong;
      if (paramLong == 0L) {
        this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillManager.destroy();
      }
      return;
    }
    catch (IllegalStateException localIllegalStateException) {}
  }
  
  public void setWebContents(WebContents paramWebContents)
  {
    WebContents localWebContents = this.jdField_a_of_type_OrgChromiumContent_publicBrowserWebContents;
    if (paramWebContents == localWebContents) {
      return;
    }
    if (localWebContents != null) {
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest = null;
    }
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserWebContents = paramWebContents;
  }
  
  public boolean shouldQueryAutofillSuggestion()
  {
    AutofillRequest localAutofillRequest = this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest;
    return (localAutofillRequest != null) && (localAutofillRequest.getFocusField() != null) && (!this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillManager.isAutofillInputUIShowing());
  }
  
  public void startAutofillSession(FormData paramFormData, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillManager.cancel();
    Rect localRect = a(new RectF(paramFloat1, paramFloat2, paramFloat3 + paramFloat1, paramFloat4 + paramFloat2));
    short s = (short)paramInt;
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest = new AutofillRequest(paramFormData, new FocusField(s, localRect));
    paramInt = this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$AutofillRequest.getVirtualId(s);
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillManager.notifyVirtualViewEntered(this.jdField_a_of_type_AndroidViewViewGroup, paramInt, localRect);
  }
  
  private static class AutofillRequest
  {
    private static int jdField_a_of_type_Int = 1;
    private AwAutofillProvider.FocusField jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$FocusField;
    private FormData jdField_a_of_type_OrgChromiumComponentsAutofillFormData;
    public final int sessionId = a();
    
    public AutofillRequest(FormData paramFormData, AwAutofillProvider.FocusField paramFocusField)
    {
      this.jdField_a_of_type_OrgChromiumComponentsAutofillFormData = paramFormData;
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$FocusField = paramFocusField;
    }
    
    private static int a()
    {
      
      if (jdField_a_of_type_Int == 65535) {
        jdField_a_of_type_Int = 1;
      }
      int i = jdField_a_of_type_Int;
      jdField_a_of_type_Int = i + 1;
      return i;
    }
    
    private static int a(int paramInt)
    {
      return (paramInt & 0xFFFF0000) >> 16;
    }
    
    private static int a(int paramInt, short paramShort)
    {
      return paramInt << 16 | paramShort;
    }
    
    private static int a(String[] paramArrayOfString, String paramString)
    {
      if ((paramArrayOfString != null) && (paramString != null))
      {
        int i = 0;
        while (i < paramArrayOfString.length)
        {
          if (paramString.equals(paramArrayOfString[i])) {
            return i;
          }
          i += 1;
        }
      }
      return -1;
    }
    
    private static short a(int paramInt)
    {
      return (short)(paramInt & 0xFFFF);
    }
    
    public boolean autofill(SparseArray<AutofillValue> paramSparseArray)
    {
      int i = 0;
      while (i < paramSparseArray.size())
      {
        int j = paramSparseArray.keyAt(i);
        if (a(j) != this.sessionId) {
          return false;
        }
        AutofillValue localAutofillValue = (AutofillValue)paramSparseArray.get(j);
        if (localAutofillValue != null)
        {
          j = a(j);
          if (j < 0) {
            break label202;
          }
          if (j >= this.jdField_a_of_type_OrgChromiumComponentsAutofillFormData.jdField_a_of_type_JavaUtilArrayList.size()) {
            return false;
          }
          FormFieldData localFormFieldData = (FormFieldData)this.jdField_a_of_type_OrgChromiumComponentsAutofillFormData.jdField_a_of_type_JavaUtilArrayList.get(j);
          if (localFormFieldData == null) {
            return false;
          }
          switch (localFormFieldData.a())
          {
          default: 
            break;
          case 2: 
            j = localAutofillValue.getListValue();
            if ((j >= 0) || (j < localFormFieldData.jdField_a_of_type_ArrayOfJavaLangString.length)) {
              localFormFieldData.updateValue(localFormFieldData.jdField_a_of_type_ArrayOfJavaLangString[j]);
            }
            break;
          case 1: 
            localFormFieldData.a(localAutofillValue.getToggleValue());
            break;
          case 0: 
            localFormFieldData.updateValue((String)localAutofillValue.getTextValue());
          }
        }
        i += 1;
        continue;
        label202:
        return false;
      }
      return true;
    }
    
    public void fillViewStructure(ViewStructure paramViewStructure)
    {
      paramViewStructure.setWebDomain(this.jdField_a_of_type_OrgChromiumComponentsAutofillFormData.jdField_b_of_type_JavaLangString);
      paramViewStructure.setHtmlInfo(paramViewStructure.newHtmlInfoBuilder("form").addAttribute("name", this.jdField_a_of_type_OrgChromiumComponentsAutofillFormData.jdField_a_of_type_JavaLangString).build());
      int i = paramViewStructure.addChildCount(this.jdField_a_of_type_OrgChromiumComponentsAutofillFormData.jdField_a_of_type_JavaUtilArrayList.size());
      Iterator localIterator = this.jdField_a_of_type_OrgChromiumComponentsAutofillFormData.jdField_a_of_type_JavaUtilArrayList.iterator();
      short s2;
      for (short s1 = 0; localIterator.hasNext(); s1 = s2)
      {
        FormFieldData localFormFieldData = (FormFieldData)localIterator.next();
        ViewStructure localViewStructure = paramViewStructure.newChild(i);
        int j = this.sessionId;
        s2 = (short)(s1 + 1);
        j = a(j, s1);
        localViewStructure.setAutofillId(paramViewStructure.getAutofillId(), j);
        if ((localFormFieldData.c != null) && (!localFormFieldData.c.isEmpty())) {
          localViewStructure.setAutofillHints(localFormFieldData.c.split(" +"));
        }
        localViewStructure.setHint(localFormFieldData.d);
        localViewStructure.setHtmlInfo(localViewStructure.newHtmlInfoBuilder("input").addAttribute("name", localFormFieldData.jdField_b_of_type_JavaLangString).addAttribute("type", localFormFieldData.e).addAttribute("label", localFormFieldData.jdField_a_of_type_JavaLangString).addAttribute("id", localFormFieldData.f).build());
        switch (localFormFieldData.a())
        {
        default: 
          break;
        case 2: 
          localViewStructure.setAutofillType(3);
          localViewStructure.setAutofillOptions(localFormFieldData.jdField_b_of_type_ArrayOfJavaLangString);
          j = a(localFormFieldData.jdField_a_of_type_ArrayOfJavaLangString, localFormFieldData.getValue());
          if (j != -1) {
            localViewStructure.setAutofillValue(AutofillValue.forList(j));
          }
          break;
        case 1: 
          localViewStructure.setAutofillType(2);
          localViewStructure.setAutofillValue(AutofillValue.forToggle(localFormFieldData.isChecked()));
          break;
        case 0: 
          localViewStructure.setAutofillType(1);
          localViewStructure.setAutofillValue(AutofillValue.forText(localFormFieldData.getValue()));
        }
        i += 1;
      }
    }
    
    public int getFieldCount()
    {
      return this.jdField_a_of_type_OrgChromiumComponentsAutofillFormData.jdField_a_of_type_JavaUtilArrayList.size();
    }
    
    public AutofillValue getFieldNewValue(int paramInt)
    {
      FormFieldData localFormFieldData = (FormFieldData)this.jdField_a_of_type_OrgChromiumComponentsAutofillFormData.jdField_a_of_type_JavaUtilArrayList.get(paramInt);
      if (localFormFieldData == null) {
        return null;
      }
      switch (localFormFieldData.a())
      {
      default: 
        return null;
      case 2: 
        paramInt = a(localFormFieldData.jdField_a_of_type_ArrayOfJavaLangString, localFormFieldData.getValue());
        if (paramInt == -1) {
          return null;
        }
        return AutofillValue.forList(paramInt);
      case 1: 
        return AutofillValue.forToggle(localFormFieldData.isChecked());
      }
      return AutofillValue.forText(localFormFieldData.getValue());
    }
    
    public AwAutofillProvider.FocusField getFocusField()
    {
      return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$FocusField;
    }
    
    public int getVirtualId(short paramShort)
    {
      return a(this.sessionId, paramShort);
    }
    
    public void setFocusField(AwAutofillProvider.FocusField paramFocusField)
    {
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillProvider$FocusField = paramFocusField;
    }
  }
  
  private static class FocusField
  {
    public final Rect absBound;
    public final short fieldIndex;
    
    public FocusField(short paramShort, Rect paramRect)
    {
      this.fieldIndex = paramShort;
      this.absBound = paramRect;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwAutofillProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */