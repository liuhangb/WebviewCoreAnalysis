package org.chromium.media;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import android.provider.Settings.System;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.chromium.base.ContextUtils;
import org.chromium.base.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.SmttServiceClientProxy;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;

@JNINamespace("media")
public class AudioManagerAndroid
{
  private static final Integer[] jdField_a_of_type_ArrayOfJavaLangInteger = { Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4) };
  private static final String[] jdField_a_of_type_ArrayOfJavaLangString = { "GT-I9300", "GT-I9500", "GT-N7105", "Nexus 4", "Nexus 5", "Nexus 7", "SM-N9005", "SM-T310" };
  private static final String[] jdField_b_of_type_ArrayOfJavaLangString = { "Speakerphone", "Wired headset", "Headset earpiece", "Bluetooth headset", "USB audio" };
  private int jdField_a_of_type_Int = -1;
  private final long jdField_a_of_type_Long;
  private BroadcastReceiver jdField_a_of_type_AndroidContentBroadcastReceiver;
  private final ContentResolver jdField_a_of_type_AndroidContentContentResolver;
  private ContentObserver jdField_a_of_type_AndroidDatabaseContentObserver;
  private final UsbManager jdField_a_of_type_AndroidHardwareUsbUsbManager;
  private final AudioManager jdField_a_of_type_AndroidMediaAudioManager;
  private HandlerThread jdField_a_of_type_AndroidOsHandlerThread;
  private final Object jdField_a_of_type_JavaLangObject = new Object();
  private final NonThreadSafe jdField_a_of_type_OrgChromiumMediaAudioManagerAndroid$NonThreadSafe = new NonThreadSafe();
  private boolean jdField_a_of_type_Boolean;
  private boolean[] jdField_a_of_type_ArrayOfBoolean = new boolean[5];
  private int jdField_b_of_type_Int = -1;
  private BroadcastReceiver jdField_b_of_type_AndroidContentBroadcastReceiver;
  private boolean jdField_b_of_type_Boolean;
  private BroadcastReceiver jdField_c_of_type_AndroidContentBroadcastReceiver;
  private boolean jdField_c_of_type_Boolean;
  private BroadcastReceiver jdField_d_of_type_AndroidContentBroadcastReceiver;
  private boolean jdField_d_of_type_Boolean;
  private boolean e;
  
  private AudioManagerAndroid(long paramLong)
  {
    this.jdField_a_of_type_Long = paramLong;
    this.jdField_a_of_type_AndroidMediaAudioManager = ((AudioManager)ContextUtils.getApplicationContext().getSystemService("audio"));
    this.jdField_a_of_type_AndroidContentContentResolver = ContextUtils.getApplicationContext().getContentResolver();
    this.jdField_a_of_type_AndroidHardwareUsbUsbManager = ((UsbManager)ContextUtils.getApplicationContext().getSystemService("usb"));
  }
  
  private static int a(boolean[] paramArrayOfBoolean)
  {
    if (paramArrayOfBoolean[1] != 0) {
      return 1;
    }
    if (paramArrayOfBoolean[4] != 0) {
      return 4;
    }
    if (paramArrayOfBoolean[3] != 0) {
      return 3;
    }
    return 0;
  }
  
  private void a() {}
  
  private void a(int paramInt)
  {
    if (paramInt == 3) {
      j();
    } else {
      k();
    }
    switch (paramInt)
    {
    default: 
      c("Invalid audio device selection");
      break;
    case 4: 
      b(false);
      break;
    case 2: 
      b(false);
      break;
    case 1: 
      b(false);
      break;
    case 0: 
      b(true);
    }
    m();
  }
  
  private void a(boolean paramBoolean)
  {
    if (paramBoolean) {
      try
      {
        if (SmttServiceClientProxy.getInstance().isTbsWebRTCAudioWhiteList(Build.MODEL))
        {
          this.jdField_a_of_type_AndroidMediaAudioManager.setMode(3);
          new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
          {
            public void run()
            {
              AudioManagerAndroid.a(AudioManagerAndroid.this).setMode(0);
            }
          }, 500L);
          return;
        }
        this.jdField_a_of_type_AndroidMediaAudioManager.setMode(0);
        return;
      }
      catch (SecurityException localSecurityException)
      {
        n();
        throw localSecurityException;
      }
    }
  }
  
  private boolean a()
  {
    return ContextUtils.getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.telephony");
  }
  
  private boolean a(UsbDevice paramUsbDevice)
  {
    int i = 0;
    while (i < paramUsbDevice.getInterfaceCount())
    {
      UsbInterface localUsbInterface = paramUsbDevice.getInterface(i);
      if ((localUsbInterface.getInterfaceClass() == 1) && (localUsbInterface.getInterfaceSubclass() == 2)) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  private boolean a(String paramString)
  {
    return ContextUtils.getApplicationContext().checkPermission(paramString, Process.myPid(), Process.myUid()) == 0;
  }
  
  private static int b(boolean[] paramArrayOfBoolean)
  {
    int i = 0;
    int k;
    for (int j = 0; i < 5; j = k)
    {
      k = j;
      if (paramArrayOfBoolean[i] != 0) {
        k = j + 1;
      }
      i += 1;
    }
    return j;
  }
  
  private void b()
  {
    this.jdField_b_of_type_Boolean = a("android.permission.BLUETOOTH");
    if (!this.jdField_b_of_type_Boolean) {
      return;
    }
    this.jdField_a_of_type_ArrayOfBoolean[3] = c();
    f();
    h();
  }
  
  private static void b(String paramString) {}
  
  private void b(boolean paramBoolean)
  {
    if (this.jdField_a_of_type_AndroidMediaAudioManager.isSpeakerphoneOn() == paramBoolean) {
      return;
    }
    this.jdField_a_of_type_AndroidMediaAudioManager.setSpeakerphoneOn(paramBoolean);
  }
  
  @Deprecated
  private boolean b()
  {
    return this.jdField_a_of_type_AndroidMediaAudioManager.isWiredHeadsetOn();
  }
  
  private void c()
  {
    if (this.jdField_b_of_type_Boolean)
    {
      this.jdField_a_of_type_AndroidMediaAudioManager.stopBluetoothSco();
      g();
      i();
    }
  }
  
  private static void c(String paramString)
  {
    Log.e("cr.media", paramString, new Object[0]);
  }
  
  private void c(boolean paramBoolean)
  {
    if (this.jdField_a_of_type_AndroidMediaAudioManager.isMicrophoneMute() == paramBoolean) {
      return;
    }
    this.jdField_a_of_type_AndroidMediaAudioManager.setMicrophoneMute(paramBoolean);
  }
  
  @TargetApi(18)
  private boolean c()
  {
    boolean bool1 = this.jdField_b_of_type_Boolean;
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    Object localObject = null;
    if (Build.VERSION.SDK_INT >= 18) {
      localObject = X5ApiCompatibilityUtils.getAdapter(ContextUtils.getApplicationContext());
    } else {
      try
      {
        BluetoothAdapter localBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        localObject = localBluetoothAdapter;
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
      }
    }
    if (localObject == null) {
      return false;
    }
    int i = ((BluetoothAdapter)localObject).getProfileConnectionState(1);
    bool1 = bool2;
    if (((BluetoothAdapter)localObject).isEnabled())
    {
      bool1 = bool2;
      if (i == 2) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  @CalledByNative
  private void close()
  {
    a();
    if (!this.jdField_c_of_type_Boolean) {
      return;
    }
    p();
    e();
    c();
    r();
    this.jdField_c_of_type_Boolean = false;
  }
  
  @CalledByNative
  private static AudioManagerAndroid createAudioManagerAndroid(long paramLong)
  {
    return new AudioManagerAndroid(paramLong);
  }
  
  private void d()
  {
    IntentFilter localIntentFilter = new IntentFilter("android.intent.action.HEADSET_PLUG");
    this.jdField_a_of_type_AndroidContentBroadcastReceiver = new BroadcastReceiver()
    {
      public void onReceive(Context arg1, Intent paramAnonymousIntent)
      {
        switch (paramAnonymousIntent.getIntExtra("state", 0))
        {
        default: 
          AudioManagerAndroid.a("Invalid state");
          break;
        case 1: 
          synchronized (AudioManagerAndroid.a(AudioManagerAndroid.this))
          {
            AudioManagerAndroid.a(AudioManagerAndroid.this)[1] = 1;
            AudioManagerAndroid.a(AudioManagerAndroid.this)[2] = 0;
            AudioManagerAndroid.a(AudioManagerAndroid.this)[4] = 0;
          }
        case 0: 
          synchronized (AudioManagerAndroid.a(AudioManagerAndroid.this))
          {
            AudioManagerAndroid.a(AudioManagerAndroid.this)[1] = 0;
            if (AudioManagerAndroid.a(AudioManagerAndroid.this))
            {
              AudioManagerAndroid.a(AudioManagerAndroid.this)[4] = 1;
              AudioManagerAndroid.a(AudioManagerAndroid.this)[2] = 0;
            }
            else if (AudioManagerAndroid.b(AudioManagerAndroid.this))
            {
              AudioManagerAndroid.a(AudioManagerAndroid.this)[2] = 1;
              AudioManagerAndroid.a(AudioManagerAndroid.this)[4] = 0;
            }
          }
        }
        if (AudioManagerAndroid.c(AudioManagerAndroid.this)) {
          AudioManagerAndroid.a(AudioManagerAndroid.this);
        }
      }
    };
    X5ApiCompatibilityUtils.x5RegisterReceiver(ContextUtils.getApplicationContext(), this.jdField_a_of_type_AndroidContentBroadcastReceiver, localIntentFilter);
  }
  
  private boolean d()
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool2 = false;
    if (i < 21) {
      return false;
    }
    try
    {
      Object localObject = this.jdField_a_of_type_AndroidHardwareUsbUsbManager.getDeviceList();
      if (localObject == null) {
        return false;
      }
      localObject = ((Map)localObject).values().iterator();
      do
      {
        bool1 = bool2;
        if (!((Iterator)localObject).hasNext()) {
          break;
        }
      } while (!a((UsbDevice)((Iterator)localObject).next()));
      boolean bool1 = true;
      return bool1;
    }
    catch (NullPointerException localNullPointerException) {}
    return false;
  }
  
  private void e()
  {
    X5ApiCompatibilityUtils.x5UnRegisterReceiver(ContextUtils.getApplicationContext(), this.jdField_a_of_type_AndroidContentBroadcastReceiver);
    this.jdField_a_of_type_AndroidContentBroadcastReceiver = null;
  }
  
  private boolean e()
  {
    for (;;)
    {
      synchronized (this.jdField_a_of_type_JavaLangObject)
      {
        if (this.jdField_b_of_type_Int != -1)
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
    }
  }
  
  private void f()
  {
    IntentFilter localIntentFilter = new IntentFilter("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
    this.jdField_b_of_type_AndroidContentBroadcastReceiver = new BroadcastReceiver()
    {
      public void onReceive(Context arg1, Intent paramAnonymousIntent)
      {
        switch (paramAnonymousIntent.getIntExtra("android.bluetooth.profile.extra.STATE", 0))
        {
        default: 
          AudioManagerAndroid.a("Invalid state");
          return;
        case 2: 
          synchronized (AudioManagerAndroid.a(AudioManagerAndroid.this))
          {
            AudioManagerAndroid.a(AudioManagerAndroid.this)[3] = 1;
            return;
          }
        case 0: 
          synchronized (AudioManagerAndroid.a(AudioManagerAndroid.this))
          {
            AudioManagerAndroid.a(AudioManagerAndroid.this)[3] = 0;
            return;
          }
        }
      }
    };
    X5ApiCompatibilityUtils.x5RegisterReceiver(ContextUtils.getApplicationContext(), this.jdField_b_of_type_AndroidContentBroadcastReceiver, localIntentFilter);
  }
  
  private void g()
  {
    X5ApiCompatibilityUtils.x5UnRegisterReceiver(ContextUtils.getApplicationContext(), this.jdField_b_of_type_AndroidContentBroadcastReceiver);
    this.jdField_b_of_type_AndroidContentBroadcastReceiver = null;
  }
  
  @CalledByNative
  private AudioDeviceName[] getAudioInputDeviceNames()
  {
    if (!this.jdField_c_of_type_Boolean) {
      return null;
    }
    boolean bool = a("android.permission.RECORD_AUDIO");
    if (this.jdField_a_of_type_Boolean)
    {
      if (!bool) {
        return null;
      }
      synchronized (this.jdField_a_of_type_JavaLangObject)
      {
        boolean[] arrayOfBoolean = (boolean[])this.jdField_a_of_type_ArrayOfBoolean.clone();
        ??? = new ArrayList();
        AudioDeviceName[] arrayOfAudioDeviceName = new AudioDeviceName[b(arrayOfBoolean)];
        int i = 0;
        int k;
        for (int j = 0; i < 5; j = k)
        {
          k = j;
          if (arrayOfBoolean[i] != 0)
          {
            arrayOfAudioDeviceName[j] = new AudioDeviceName(i, jdField_b_of_type_ArrayOfJavaLangString[i], null);
            ((List)???).add(jdField_b_of_type_ArrayOfJavaLangString[i]);
            k = j + 1;
          }
          i += 1;
        }
        return arrayOfAudioDeviceName;
      }
    }
    return null;
  }
  
  @TargetApi(17)
  @CalledByNative
  private int getAudioLowLatencyOutputFrameSize()
  {
    if (Build.VERSION.SDK_INT < 17) {
      return 256;
    }
    String str = this.jdField_a_of_type_AndroidMediaAudioManager.getProperty("android.media.property.OUTPUT_FRAMES_PER_BUFFER");
    if (str == null) {
      return 256;
    }
    return Integer.parseInt(str);
  }
  
  @CalledByNative
  private static int getMinInputFrameSize(int paramInt1, int paramInt2)
  {
    int i;
    if (paramInt2 == 1)
    {
      i = 16;
    }
    else
    {
      if (paramInt2 != 2) {
        break label30;
      }
      i = 12;
    }
    return AudioRecord.getMinBufferSize(paramInt1, i, 2) / 2 / paramInt2;
    label30:
    return -1;
  }
  
  @CalledByNative
  private static int getMinOutputFrameSize(int paramInt1, int paramInt2)
  {
    int i;
    if (paramInt2 == 1)
    {
      i = 4;
    }
    else
    {
      if (paramInt2 != 2) {
        break label29;
      }
      i = 12;
    }
    return AudioTrack.getMinBufferSize(paramInt1, i, 2) / 2 / paramInt2;
    label29:
    return -1;
  }
  
  @TargetApi(17)
  @CalledByNative
  private int getNativeOutputSampleRate()
  {
    if (Build.VERSION.SDK_INT >= 17)
    {
      String str = this.jdField_a_of_type_AndroidMediaAudioManager.getProperty("android.media.property.OUTPUT_SAMPLE_RATE");
      if (str == null) {
        return 44100;
      }
      return Integer.parseInt(str);
    }
    return 44100;
  }
  
  private void h()
  {
    IntentFilter localIntentFilter = new IntentFilter("android.media.ACTION_SCO_AUDIO_STATE_UPDATED");
    this.jdField_c_of_type_AndroidContentBroadcastReceiver = new BroadcastReceiver()
    {
      public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
      {
        switch (paramAnonymousIntent.getIntExtra("android.media.extra.SCO_AUDIO_STATE", 0))
        {
        default: 
          AudioManagerAndroid.a("Invalid state");
          return;
        case 1: 
          AudioManagerAndroid.a(AudioManagerAndroid.this, 1);
          return;
        case 0: 
          if ((AudioManagerAndroid.a(AudioManagerAndroid.this) != 3) && (AudioManagerAndroid.c(AudioManagerAndroid.this))) {
            AudioManagerAndroid.a(AudioManagerAndroid.this);
          }
          AudioManagerAndroid.a(AudioManagerAndroid.this, 0);
        }
      }
    };
    X5ApiCompatibilityUtils.x5RegisterReceiver(ContextUtils.getApplicationContext(), this.jdField_c_of_type_AndroidContentBroadcastReceiver, localIntentFilter);
  }
  
  private void i()
  {
    X5ApiCompatibilityUtils.x5UnRegisterReceiver(ContextUtils.getApplicationContext(), this.jdField_c_of_type_AndroidContentBroadcastReceiver);
    this.jdField_c_of_type_AndroidContentBroadcastReceiver = null;
  }
  
  @CalledByNative
  private void init()
  {
    a();
    if (this.jdField_c_of_type_Boolean) {
      return;
    }
    this.jdField_a_of_type_Boolean = a("android.permission.MODIFY_AUDIO_SETTINGS");
    this.jdField_a_of_type_ArrayOfBoolean[2] = a();
    this.jdField_a_of_type_ArrayOfBoolean[1] = b();
    this.jdField_a_of_type_ArrayOfBoolean[4] = d();
    this.jdField_a_of_type_ArrayOfBoolean[0] = true;
    try
    {
      b();
      d();
      q();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    this.jdField_c_of_type_Boolean = true;
  }
  
  @CalledByNative
  private boolean isAudioLowLatencySupported()
  {
    return ContextUtils.getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.audio.low_latency");
  }
  
  private void j()
  {
    if (!this.jdField_b_of_type_Boolean) {
      return;
    }
    int i = this.jdField_a_of_type_Int;
    if (i != 1)
    {
      if (i == 2) {
        return;
      }
      if (this.jdField_a_of_type_AndroidMediaAudioManager.isBluetoothScoOn())
      {
        this.jdField_a_of_type_Int = 1;
        return;
      }
      this.jdField_a_of_type_Int = 2;
      this.jdField_a_of_type_AndroidMediaAudioManager.startBluetoothSco();
      return;
    }
  }
  
  private void k()
  {
    if (!this.jdField_b_of_type_Boolean) {
      return;
    }
    int i = this.jdField_a_of_type_Int;
    if ((i != 1) && (i != 2)) {
      return;
    }
    if (!this.jdField_a_of_type_AndroidMediaAudioManager.isBluetoothScoOn())
    {
      c("Unable to stop BT SCO since it is already disabled");
      this.jdField_a_of_type_Int = 0;
      return;
    }
    this.jdField_a_of_type_Int = 3;
    this.jdField_a_of_type_AndroidMediaAudioManager.stopBluetoothSco();
  }
  
  private void l()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      int i = this.jdField_b_of_type_Int;
      boolean[] arrayOfBoolean = (boolean[])this.jdField_a_of_type_ArrayOfBoolean.clone();
      if (i == -1)
      {
        c("Unable to activate device since no device is selected");
        return;
      }
      if ((i != -2) && (arrayOfBoolean[i] != 0))
      {
        a(i);
        return;
      }
      a(a(arrayOfBoolean));
      return;
    }
  }
  
  private void m() {}
  
  private void n()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Android SDK: ");
    localStringBuilder.append(Build.VERSION.SDK_INT);
    localStringBuilder.append(", Release: ");
    localStringBuilder.append(Build.VERSION.RELEASE);
    localStringBuilder.append(", Brand: ");
    localStringBuilder.append(Build.BRAND);
    localStringBuilder.append(", Device: ");
    localStringBuilder.append(Build.DEVICE);
    localStringBuilder.append(", Id: ");
    localStringBuilder.append(Build.ID);
    localStringBuilder.append(", Hardware: ");
    localStringBuilder.append(Build.HARDWARE);
    localStringBuilder.append(", Manufacturer: ");
    localStringBuilder.append(Build.MANUFACTURER);
    localStringBuilder.append(", Model: ");
    localStringBuilder.append(Build.MODEL);
    localStringBuilder.append(", Product: ");
    localStringBuilder.append(Build.PRODUCT);
    b(localStringBuilder.toString());
  }
  
  private native void nativeSetMute(long paramLong, boolean paramBoolean);
  
  private void o()
  {
    if (this.jdField_a_of_type_AndroidOsHandlerThread != null) {
      return;
    }
    this.jdField_a_of_type_AndroidOsHandlerThread = new HandlerThread("SettingsObserver");
    this.jdField_a_of_type_AndroidOsHandlerThread.start();
    this.jdField_a_of_type_AndroidDatabaseContentObserver = new ContentObserver(new Handler(this.jdField_a_of_type_AndroidOsHandlerThread.getLooper()))
    {
      public void onChange(boolean paramAnonymousBoolean)
      {
        super.onChange(paramAnonymousBoolean);
        try
        {
          Object localObject = AudioManagerAndroid.a(AudioManagerAndroid.this);
          paramAnonymousBoolean = false;
          int i = ((AudioManager)localObject).getStreamVolume(0);
          localObject = AudioManagerAndroid.this;
          long l = AudioManagerAndroid.a(AudioManagerAndroid.this);
          if (i == 0) {
            paramAnonymousBoolean = true;
          }
          AudioManagerAndroid.a((AudioManagerAndroid)localObject, l, paramAnonymousBoolean);
          return;
        }
        catch (SecurityException localSecurityException)
        {
          localSecurityException.printStackTrace();
        }
      }
    };
    this.jdField_a_of_type_AndroidContentContentResolver.registerContentObserver(Settings.System.CONTENT_URI, true, this.jdField_a_of_type_AndroidDatabaseContentObserver);
  }
  
  private void p()
  {
    if (this.jdField_a_of_type_AndroidOsHandlerThread == null) {
      return;
    }
    this.jdField_a_of_type_AndroidContentContentResolver.unregisterContentObserver(this.jdField_a_of_type_AndroidDatabaseContentObserver);
    this.jdField_a_of_type_AndroidDatabaseContentObserver = null;
    this.jdField_a_of_type_AndroidOsHandlerThread.quit();
    try
    {
      this.jdField_a_of_type_AndroidOsHandlerThread.join();
    }
    catch (InterruptedException localInterruptedException)
    {
      Log.e("cr.media", "Thread.join() exception: ", new Object[] { localInterruptedException });
    }
    this.jdField_a_of_type_AndroidOsHandlerThread = null;
  }
  
  private void q()
  {
    this.jdField_d_of_type_AndroidContentBroadcastReceiver = new BroadcastReceiver()
    {
      public void onReceive(Context arg1, Intent paramAnonymousIntent)
      {
        ??? = (UsbDevice)paramAnonymousIntent.getParcelableExtra("device");
        if (!AudioManagerAndroid.a(AudioManagerAndroid.this, ???)) {
          return;
        }
        if ("android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(paramAnonymousIntent.getAction())) {
          synchronized (AudioManagerAndroid.a(AudioManagerAndroid.this))
          {
            if (!AudioManagerAndroid.d(AudioManagerAndroid.this))
            {
              AudioManagerAndroid.a(AudioManagerAndroid.this)[4] = 1;
              AudioManagerAndroid.a(AudioManagerAndroid.this)[2] = 0;
            }
          }
        }
        if (("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(paramAnonymousIntent.getAction())) && (!AudioManagerAndroid.a(AudioManagerAndroid.this))) {
          synchronized (AudioManagerAndroid.a(AudioManagerAndroid.this))
          {
            if (!AudioManagerAndroid.d(AudioManagerAndroid.this))
            {
              AudioManagerAndroid.a(AudioManagerAndroid.this)[4] = 0;
              if (AudioManagerAndroid.b(AudioManagerAndroid.this)) {
                AudioManagerAndroid.a(AudioManagerAndroid.this)[2] = 1;
              }
            }
          }
        }
        if (AudioManagerAndroid.c(AudioManagerAndroid.this)) {
          AudioManagerAndroid.a(AudioManagerAndroid.this);
        }
      }
    };
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
    localIntentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
    X5ApiCompatibilityUtils.x5RegisterReceiver(ContextUtils.getApplicationContext(), this.jdField_d_of_type_AndroidContentBroadcastReceiver, localIntentFilter);
  }
  
  private void r()
  {
    X5ApiCompatibilityUtils.x5UnRegisterReceiver(ContextUtils.getApplicationContext(), this.jdField_d_of_type_AndroidContentBroadcastReceiver);
    this.jdField_d_of_type_AndroidContentBroadcastReceiver = null;
  }
  
  @CalledByNative
  private void setCommunicationAudioModeOn(boolean paramBoolean)
  {
    a();
    if (!this.jdField_c_of_type_Boolean) {
      return;
    }
    if (!this.jdField_a_of_type_Boolean) {
      return;
    }
    if (paramBoolean)
    {
      this.jdField_d_of_type_Boolean = this.jdField_a_of_type_AndroidMediaAudioManager.isSpeakerphoneOn();
      this.e = this.jdField_a_of_type_AndroidMediaAudioManager.isMicrophoneMute();
      o();
    }
    else
    {
      p();
      k();
    }
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      this.jdField_b_of_type_Int = -1;
      c(this.e);
      b(this.jdField_d_of_type_Boolean);
      a(paramBoolean);
      return;
    }
  }
  
  @CalledByNative
  private boolean setDevice(String arg1)
  {
    if (!this.jdField_c_of_type_Boolean) {
      return false;
    }
    boolean bool = a("android.permission.RECORD_AUDIO");
    if (this.jdField_a_of_type_Boolean)
    {
      if (!bool) {
        return false;
      }
      int i;
      if (???.isEmpty()) {
        i = -2;
      } else {
        i = Integer.parseInt(???);
      }
      if (i == -2) {
        synchronized (this.jdField_a_of_type_JavaLangObject)
        {
          boolean[] arrayOfBoolean = (boolean[])this.jdField_a_of_type_ArrayOfBoolean.clone();
          this.jdField_b_of_type_Int = -2;
          a(a(arrayOfBoolean));
          return true;
        }
      }
      if (Arrays.asList(jdField_a_of_type_ArrayOfJavaLangInteger).contains(Integer.valueOf(i)))
      {
        if (this.jdField_a_of_type_ArrayOfBoolean[i] == 0) {
          return false;
        }
        synchronized (this.jdField_a_of_type_JavaLangObject)
        {
          this.jdField_b_of_type_Int = i;
          a(i);
          return true;
        }
      }
      return false;
    }
    return false;
  }
  
  @CalledByNative
  private static boolean shouldUseAcousticEchoCanceler()
  {
    if (!Arrays.asList(jdField_a_of_type_ArrayOfJavaLangString).contains(Build.MODEL)) {
      return false;
    }
    return X5ApiCompatibilityUtils.isAcousticEchoCancelerAvailable();
  }
  
  private static class AudioDeviceName
  {
    private final int jdField_a_of_type_Int;
    private final String jdField_a_of_type_JavaLangString;
    
    private AudioDeviceName(int paramInt, String paramString)
    {
      this.jdField_a_of_type_Int = paramInt;
      this.jdField_a_of_type_JavaLangString = paramString;
    }
    
    @CalledByNative("AudioDeviceName")
    private String id()
    {
      return String.valueOf(this.jdField_a_of_type_Int);
    }
    
    @CalledByNative("AudioDeviceName")
    private String name()
    {
      return this.jdField_a_of_type_JavaLangString;
    }
  }
  
  private static class NonThreadSafe
  {
    private final Long a = Long.valueOf(0L);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\AudioManagerAndroid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */