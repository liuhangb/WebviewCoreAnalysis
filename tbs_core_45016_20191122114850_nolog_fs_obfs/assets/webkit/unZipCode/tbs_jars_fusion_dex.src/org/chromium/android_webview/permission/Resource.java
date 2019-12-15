package org.chromium.android_webview.permission;

import android.support.annotation.IntDef;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({1L, 2L, 4L, 8L, 16L})
public @interface Resource
{
  public static final int AUDIO_CAPTURE = 4;
  public static final int GEOLOCATION = 1;
  public static final int MIDI_SYSEX = 16;
  public static final int PROTECTED_MEDIA_ID = 8;
  public static final int VIDEO_CAPTURE = 2;
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\permission\Resource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */