package org.chromium.content.browser;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.chromium.base.ContextUtils;
import org.chromium.base.PackageUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("content")
public class SpeechRecognition
{
  private static ComponentName jdField_a_of_type_AndroidContentComponentName;
  private int jdField_a_of_type_Int;
  private long jdField_a_of_type_Long;
  private final Intent jdField_a_of_type_AndroidContentIntent;
  private final RecognitionListener jdField_a_of_type_AndroidSpeechRecognitionListener;
  private SpeechRecognizer jdField_a_of_type_AndroidSpeechSpeechRecognizer;
  private boolean jdField_a_of_type_Boolean = false;
  
  private SpeechRecognition(long paramLong)
  {
    this.jdField_a_of_type_Long = paramLong;
    this.jdField_a_of_type_AndroidSpeechRecognitionListener = new Listener();
    this.jdField_a_of_type_AndroidContentIntent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
    if (jdField_a_of_type_AndroidContentComponentName != null) {
      this.jdField_a_of_type_AndroidSpeechSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(ContextUtils.getApplicationContext(), jdField_a_of_type_AndroidContentComponentName);
    } else {
      this.jdField_a_of_type_AndroidSpeechSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(ContextUtils.getApplicationContext());
    }
    this.jdField_a_of_type_AndroidSpeechSpeechRecognizer.setRecognitionListener(this.jdField_a_of_type_AndroidSpeechRecognitionListener);
  }
  
  private void a(int paramInt)
  {
    long l = this.jdField_a_of_type_Long;
    if (l == 0L) {
      return;
    }
    int i = this.jdField_a_of_type_Int;
    if (i != 0)
    {
      if (i == 2) {
        nativeOnSoundEnd(l);
      }
      nativeOnAudioEnd(this.jdField_a_of_type_Long);
      this.jdField_a_of_type_Int = 0;
    }
    if (paramInt != 0) {
      nativeOnRecognitionError(this.jdField_a_of_type_Long, paramInt);
    }
    try
    {
      this.jdField_a_of_type_AndroidSpeechSpeechRecognizer.destroy();
      this.jdField_a_of_type_AndroidSpeechSpeechRecognizer = null;
      nativeOnRecognitionEnd(this.jdField_a_of_type_Long);
      this.jdField_a_of_type_Long = 0L;
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      for (;;) {}
    }
  }
  
  @CalledByNative
  private void abortRecognition()
  {
    SpeechRecognizer localSpeechRecognizer = this.jdField_a_of_type_AndroidSpeechSpeechRecognizer;
    if (localSpeechRecognizer == null) {
      return;
    }
    localSpeechRecognizer.cancel();
    a(2);
  }
  
  @CalledByNative
  private static SpeechRecognition createSpeechRecognition(long paramLong)
  {
    return new SpeechRecognition(paramLong);
  }
  
  @SuppressLint({"WrongConstant"})
  public static boolean initialize(Context paramContext)
  {
    if (!SpeechRecognizer.isRecognitionAvailable(paramContext)) {
      return false;
    }
    Iterator localIterator = paramContext.getPackageManager().queryIntentServices(new Intent("android.speech.RecognitionService"), 4).iterator();
    while (localIterator.hasNext())
    {
      ServiceInfo localServiceInfo = ((ResolveInfo)localIterator.next()).serviceInfo;
      if ((localServiceInfo.packageName.equals("com.google.android.googlequicksearchbox")) && (PackageUtils.getPackageVersion(paramContext, localServiceInfo.packageName) >= 300207030))
      {
        jdField_a_of_type_AndroidContentComponentName = new ComponentName(localServiceInfo.packageName, localServiceInfo.name);
        return true;
      }
    }
    return false;
  }
  
  private native void nativeOnAudioEnd(long paramLong);
  
  private native void nativeOnAudioStart(long paramLong);
  
  private native void nativeOnRecognitionEnd(long paramLong);
  
  private native void nativeOnRecognitionError(long paramLong, int paramInt);
  
  private native void nativeOnRecognitionResults(long paramLong, String[] paramArrayOfString, float[] paramArrayOfFloat, boolean paramBoolean);
  
  private native void nativeOnSoundEnd(long paramLong);
  
  private native void nativeOnSoundStart(long paramLong);
  
  @CalledByNative
  private void startRecognition(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.jdField_a_of_type_AndroidSpeechSpeechRecognizer == null) {
      return;
    }
    this.jdField_a_of_type_Boolean = paramBoolean1;
    this.jdField_a_of_type_AndroidContentIntent.putExtra("android.speech.extra.DICTATION_MODE", paramBoolean1);
    this.jdField_a_of_type_AndroidContentIntent.putExtra("android.speech.extra.LANGUAGE", paramString);
    this.jdField_a_of_type_AndroidContentIntent.putExtra("android.speech.extra.PARTIAL_RESULTS", paramBoolean2);
    this.jdField_a_of_type_AndroidSpeechSpeechRecognizer.startListening(this.jdField_a_of_type_AndroidContentIntent);
  }
  
  @CalledByNative
  private void stopRecognition()
  {
    SpeechRecognizer localSpeechRecognizer = this.jdField_a_of_type_AndroidSpeechSpeechRecognizer;
    if (localSpeechRecognizer == null) {
      return;
    }
    this.jdField_a_of_type_Boolean = false;
    localSpeechRecognizer.stopListening();
  }
  
  class Listener
    implements RecognitionListener
  {
    Listener() {}
    
    private void a(Bundle paramBundle, boolean paramBoolean)
    {
      if ((SpeechRecognition.a(SpeechRecognition.this)) && (paramBoolean)) {
        paramBoolean = false;
      }
      Object localObject = paramBundle.getStringArrayList("results_recognition");
      localObject = (String[])((ArrayList)localObject).toArray(new String[((ArrayList)localObject).size()]);
      paramBundle = paramBundle.getFloatArray("confidence_scores");
      SpeechRecognition localSpeechRecognition = SpeechRecognition.this;
      SpeechRecognition.a(localSpeechRecognition, SpeechRecognition.a(localSpeechRecognition), (String[])localObject, paramBundle, paramBoolean);
    }
    
    public void onBeginningOfSpeech()
    {
      SpeechRecognition.a(SpeechRecognition.this, 2);
      SpeechRecognition localSpeechRecognition = SpeechRecognition.this;
      SpeechRecognition.a(localSpeechRecognition, SpeechRecognition.a(localSpeechRecognition));
    }
    
    public void onBufferReceived(byte[] paramArrayOfByte) {}
    
    public void onEndOfSpeech()
    {
      if (!SpeechRecognition.a(SpeechRecognition.this))
      {
        SpeechRecognition localSpeechRecognition = SpeechRecognition.this;
        SpeechRecognition.b(localSpeechRecognition, SpeechRecognition.a(localSpeechRecognition));
        localSpeechRecognition = SpeechRecognition.this;
        SpeechRecognition.c(localSpeechRecognition, SpeechRecognition.a(localSpeechRecognition));
        SpeechRecognition.a(SpeechRecognition.this, 0);
      }
    }
    
    public void onError(int paramInt)
    {
      switch (paramInt)
      {
      default: 
        if (!jdField_a_of_type_Boolean) {
          break label96;
        }
        return;
      case 8: 
      case 9: 
        paramInt = 5;
        break;
      case 7: 
        paramInt = 9;
        break;
      case 6: 
        paramInt = 1;
        break;
      case 5: 
        paramInt = 2;
        break;
      case 3: 
        paramInt = 3;
        break;
      case 1: 
      case 2: 
      case 4: 
        paramInt = 4;
      }
      SpeechRecognition.a(SpeechRecognition.this, paramInt);
      return;
      label96:
      throw new AssertionError();
    }
    
    public void onEvent(int paramInt, Bundle paramBundle) {}
    
    public void onPartialResults(Bundle paramBundle)
    {
      a(paramBundle, true);
    }
    
    public void onReadyForSpeech(Bundle paramBundle)
    {
      SpeechRecognition.a(SpeechRecognition.this, 1);
      paramBundle = SpeechRecognition.this;
      SpeechRecognition.d(paramBundle, SpeechRecognition.a(paramBundle));
    }
    
    public void onResults(Bundle paramBundle)
    {
      a(paramBundle, false);
      SpeechRecognition.a(SpeechRecognition.this, 0);
    }
    
    public void onRmsChanged(float paramFloat) {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\SpeechRecognition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */