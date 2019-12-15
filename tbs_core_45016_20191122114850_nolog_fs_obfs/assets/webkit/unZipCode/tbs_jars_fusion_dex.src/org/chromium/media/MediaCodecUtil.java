package org.chromium.media;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.CryptoInfo;
import android.media.MediaCodec.CryptoInfo.Pattern;
import android.media.MediaCodecInfo;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.media.MediaCodecInfo.VideoCapabilities;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.Range;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import org.chromium.base.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;
import org.chromium.tencent.SmttServiceClientProxy;

@JNINamespace("media")
@MainDex
public class MediaCodecUtil
{
  private static final String[] jdField_a_of_type_ArrayOfJavaLangString = { "SAMSUNG-SGH-I337", "Nexus 7", "Nexus 4" };
  
  public static CodecCreationInfo a(String paramString)
  {
    CodecCreationInfo localCodecCreationInfo = new CodecCreationInfo();
    HWEncoderProperties localHWEncoderProperties = a(paramString);
    if (localHWEncoderProperties == null) {
      return localCodecCreationInfo;
    }
    try
    {
      localCodecCreationInfo.jdField_a_of_type_AndroidMediaMediaCodec = MediaCodec.createEncoderByType(paramString);
      localCodecCreationInfo.jdField_a_of_type_Boolean = false;
      localCodecCreationInfo.jdField_a_of_type_OrgChromiumMediaBitrateAdjuster = localHWEncoderProperties.a();
      return localCodecCreationInfo;
    }
    catch (Exception localException)
    {
      Log.e("cr_MediaCodecUtil", "Failed to create MediaCodec: %s", new Object[] { paramString, localException });
    }
    return localCodecCreationInfo;
  }
  
  static CodecCreationInfo a(String paramString, int paramInt)
  {
    return a(paramString, paramInt, null);
  }
  
  static CodecCreationInfo a(String paramString, int paramInt, MediaCrypto paramMediaCrypto)
  {
    CodecCreationInfo localCodecCreationInfo = new CodecCreationInfo();
    if ((!jdField_a_of_type_Boolean) && (localCodecCreationInfo.jdField_a_of_type_AndroidMediaMediaCodec != null)) {
      throw new AssertionError();
    }
    if ((paramInt == 1) && (Build.VERSION.SDK_INT < 18)) {
      return localCodecCreationInfo;
    }
    if (!isDecoderSupportedForDevice(paramString))
    {
      Log.e("cr_MediaCodecUtil", "Decoder for type %s is not supported on this device", new Object[] { paramString });
      return localCodecCreationInfo;
    }
    try
    {
      if (((paramString.startsWith("video")) && (paramInt == 1)) || ((paramString.startsWith("audio")) && (paramMediaCrypto != null) && (paramMediaCrypto.requiresSecureDecoderComponent(paramString))))
      {
        paramMediaCrypto = getDefaultCodecName(paramString, 0, false);
        if (paramMediaCrypto.equals("")) {
          return null;
        }
        if (Build.VERSION.SDK_INT >= 19)
        {
          localObject = MediaCodec.createByCodecName(paramMediaCrypto);
          localCodecCreationInfo.jdField_a_of_type_Boolean = a((MediaCodec)localObject, paramString);
          ((MediaCodec)localObject).release();
        }
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append(paramMediaCrypto);
        ((StringBuilder)localObject).append(".secure");
        localCodecCreationInfo.jdField_a_of_type_AndroidMediaMediaCodec = MediaCodec.createByCodecName(((StringBuilder)localObject).toString());
        return localCodecCreationInfo;
      }
      if (paramInt == 2) {
        localCodecCreationInfo.jdField_a_of_type_AndroidMediaMediaCodec = MediaCodec.createByCodecName(getDefaultCodecName(paramString, 0, true));
      } else if (paramString.equals("audio/raw")) {
        localCodecCreationInfo.jdField_a_of_type_AndroidMediaMediaCodec = MediaCodec.createByCodecName("OMX.google.raw.decoder");
      } else {
        localCodecCreationInfo.jdField_a_of_type_AndroidMediaMediaCodec = MediaCodec.createDecoderByType(paramString);
      }
      localCodecCreationInfo.jdField_a_of_type_Boolean = a(localCodecCreationInfo.jdField_a_of_type_AndroidMediaMediaCodec, paramString);
      return localCodecCreationInfo;
    }
    catch (Exception paramMediaCrypto)
    {
      Log.e("cr_MediaCodecUtil", "Failed to create MediaCodec: %s, codecType: %d", new Object[] { paramString, Integer.valueOf(paramInt), paramMediaCrypto });
      localCodecCreationInfo.jdField_a_of_type_AndroidMediaMediaCodec = null;
    }
    return localCodecCreationInfo;
  }
  
  public static CodecCreationInfo a(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    CodecCreationInfo localCodecCreationInfo = new CodecCreationInfo();
    if ((!jdField_a_of_type_Boolean) && (localCodecCreationInfo.jdField_a_of_type_AndroidMediaMediaCodec != null)) {
      throw new AssertionError();
    }
    if ((paramBoolean1) && (Build.VERSION.SDK_INT < 18)) {
      return localCodecCreationInfo;
    }
    if (!isDecoderSupportedForDevice(paramString))
    {
      Log.e("cr_MediaCodecUtil", "Decoder for type %s is not supported on this device", new Object[] { paramString });
      return localCodecCreationInfo;
    }
    try
    {
      if ((paramString.startsWith("video")) && (paramBoolean1))
      {
        str = getDefaultCodecName(paramString, 0, paramBoolean2);
        if (str.equals("")) {
          return null;
        }
        if (Build.VERSION.SDK_INT >= 19)
        {
          localObject = MediaCodec.createByCodecName(str);
          localCodecCreationInfo.jdField_a_of_type_Boolean = a((MediaCodec)localObject, paramString);
          ((MediaCodec)localObject).release();
        }
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append(str);
        ((StringBuilder)localObject).append(".secure");
        localCodecCreationInfo.jdField_a_of_type_AndroidMediaMediaCodec = MediaCodec.createByCodecName(((StringBuilder)localObject).toString());
        return localCodecCreationInfo;
      }
      if (paramBoolean2) {
        localCodecCreationInfo.jdField_a_of_type_AndroidMediaMediaCodec = MediaCodec.createByCodecName(getDefaultCodecName(paramString, 0, paramBoolean2));
      } else {
        localCodecCreationInfo.jdField_a_of_type_AndroidMediaMediaCodec = MediaCodec.createDecoderByType(paramString);
      }
      localCodecCreationInfo.jdField_a_of_type_Boolean = a(localCodecCreationInfo.jdField_a_of_type_AndroidMediaMediaCodec, paramString);
      return localCodecCreationInfo;
    }
    catch (Exception localException)
    {
      String str;
      if (paramBoolean2) {
        str = "yes";
      } else {
        str = "no";
      }
      Log.e("cr_MediaCodecUtil", "Failed to create MediaCodec: %s, isSecure: %s, requireSoftwareCodec: %s", new Object[] { paramString, Boolean.valueOf(paramBoolean1), str, localException });
      localCodecCreationInfo.jdField_a_of_type_AndroidMediaMediaCodec = null;
    }
    return localCodecCreationInfo;
  }
  
  private static HWEncoderProperties a(String paramString)
  {
    Iterator localIterator = new MediaCodecListHelper().iterator();
    for (;;)
    {
      boolean bool = localIterator.hasNext();
      HWEncoderProperties[] arrayOfHWEncoderProperties = null;
      if (!bool) {
        break;
      }
      Object localObject2 = (MediaCodecInfo)localIterator.next();
      if ((((MediaCodecInfo)localObject2).isEncoder()) && (!a(((MediaCodecInfo)localObject2).getName())))
      {
        String[] arrayOfString = ((MediaCodecInfo)localObject2).getSupportedTypes();
        int k = arrayOfString.length;
        int j = 0;
        int i = 0;
        Object localObject1;
        for (;;)
        {
          localObject1 = arrayOfHWEncoderProperties;
          if (i >= k) {
            break;
          }
          if (arrayOfString[i].equalsIgnoreCase(paramString))
          {
            localObject1 = ((MediaCodecInfo)localObject2).getName();
            break;
          }
          i += 1;
        }
        if (localObject1 != null)
        {
          arrayOfHWEncoderProperties = HWEncoderProperties.values();
          k = arrayOfHWEncoderProperties.length;
          i = j;
          while (i < k)
          {
            localObject2 = arrayOfHWEncoderProperties[i];
            if ((paramString.equalsIgnoreCase(((HWEncoderProperties)localObject2).a())) && (((String)localObject1).startsWith(((HWEncoderProperties)localObject2).b())) && (Build.VERSION.SDK_INT >= ((HWEncoderProperties)localObject2).a())) {
              return (HWEncoderProperties)localObject2;
            }
            i += 1;
          }
        }
      }
    }
    return null;
  }
  
  public static void a(MediaCodec.CryptoInfo paramCryptoInfo, int paramInt1, int paramInt2)
  {
    if (Build.VERSION.SDK_INT >= 24) {
      paramCryptoInfo.setPattern(new MediaCodec.CryptoInfo.Pattern(paramInt1, paramInt2));
    }
  }
  
  @TargetApi(21)
  private static void a(CodecProfileLevelList paramCodecProfileLevelList, MediaCodecInfo.CodecCapabilities paramCodecCapabilities)
  {
    int[][] arrayOfInt = new int[11][];
    arrayOfInt[0] = { 200, 10 };
    arrayOfInt[1] = { 800, 11 };
    arrayOfInt[2] = { 1800, 20 };
    arrayOfInt[3] = { 3600, 21 };
    arrayOfInt[4] = { 7200, 30 };
    arrayOfInt[5] = { 12000, 31 };
    arrayOfInt[6] = { 18000, 40 };
    arrayOfInt[7] = { 30000, 41 };
    arrayOfInt[8] = { 60000, 50 };
    arrayOfInt[9] = { 120000, 51 };
    arrayOfInt[10] = { 180000, 52 };
    paramCodecCapabilities = paramCodecCapabilities.getVideoCapabilities();
    int j = arrayOfInt.length;
    int i = 0;
    while (i < j)
    {
      int[] arrayOfInt1 = arrayOfInt[i];
      int k = arrayOfInt1[0];
      int m = arrayOfInt1[1];
      if (paramCodecCapabilities.getBitrateRange().contains(Integer.valueOf(k))) {
        paramCodecProfileLevelList.a(7, 12, m);
      }
      i += 1;
    }
  }
  
  @TargetApi(19)
  private static boolean a(MediaCodec paramMediaCodec, String paramString)
  {
    if (Build.VERSION.SDK_INT >= 19)
    {
      if (paramMediaCodec == null) {
        return false;
      }
      try
      {
        paramMediaCodec = paramMediaCodec.getCodecInfo();
        if (paramMediaCodec.isEncoder()) {
          return false;
        }
        if (b(paramString)) {
          return false;
        }
        paramMediaCodec = paramMediaCodec.getCapabilitiesForType(paramString);
        if (paramMediaCodec != null)
        {
          boolean bool = paramMediaCodec.isFeatureSupported("adaptive-playback");
          if (bool) {
            return true;
          }
        }
        return false;
      }
      catch (IllegalArgumentException paramMediaCodec)
      {
        Log.e("cr_MediaCodecUtil", "Cannot retrieve codec information", new Object[] { paramMediaCodec });
        return false;
      }
    }
    return false;
  }
  
  public static boolean a(String paramString)
  {
    if (paramString.startsWith("OMX.google.")) {
      return true;
    }
    return !paramString.startsWith("OMX.");
  }
  
  private static boolean b(String paramString)
  {
    boolean bool2 = paramString.equals("video/avc");
    boolean bool1 = false;
    if ((!bool2) && (!paramString.equals("video/avc1"))) {
      return false;
    }
    if (!Build.VERSION.RELEASE.equals("4.4.2")) {
      return false;
    }
    if (!Build.MANUFACTURER.toLowerCase(Locale.getDefault()).equals("samsung")) {
      return false;
    }
    if ((Build.MODEL.startsWith("GT-I9300")) || (Build.MODEL.startsWith("SCH-I535"))) {
      bool1 = true;
    }
    return bool1;
  }
  
  @CalledByNative
  private static boolean canDecode(String paramString, boolean paramBoolean)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  @CalledByNative
  private static String getDefaultCodecName(String paramString, int paramInt, boolean paramBoolean)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge Z and I\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:322)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  @CalledByNative
  private static int[] getEncoderColorFormatsForMime(String paramString)
  {
    Iterator localIterator = new MediaCodecListHelper().iterator();
    while (localIterator.hasNext())
    {
      MediaCodecInfo localMediaCodecInfo = (MediaCodecInfo)localIterator.next();
      if (localMediaCodecInfo.isEncoder())
      {
        String[] arrayOfString = localMediaCodecInfo.getSupportedTypes();
        int j = arrayOfString.length;
        int i = 0;
        while (i < j)
        {
          String str = arrayOfString[i];
          if (str.equalsIgnoreCase(paramString)) {
            return localMediaCodecInfo.getCapabilitiesForType(str).colorFormats;
          }
          i += 1;
        }
      }
    }
    return null;
  }
  
  @CalledByNative
  private static Object[] getSupportedCodecProfileLevels()
  {
    CodecProfileLevelList localCodecProfileLevelList = new CodecProfileLevelList();
    Iterator localIterator = new MediaCodecListHelper().iterator();
    while (localIterator.hasNext())
    {
      MediaCodecInfo localMediaCodecInfo = (MediaCodecInfo)localIterator.next();
      String[] arrayOfString = localMediaCodecInfo.getSupportedTypes();
      int k = arrayOfString.length;
      int i = 0;
      while (i < k)
      {
        String str = arrayOfString[i];
        if (isDecoderSupportedForDevice(str))
        {
          Object localObject = localMediaCodecInfo.getCapabilitiesForType(str);
          if ((str.endsWith("vp9")) && (21 <= Build.VERSION.SDK_INT) && (Build.VERSION.SDK_INT <= 23))
          {
            a(localCodecProfileLevelList, (MediaCodecInfo.CodecCapabilities)localObject);
          }
          else
          {
            localObject = ((MediaCodecInfo.CodecCapabilities)localObject).profileLevels;
            int m = localObject.length;
            int j = 0;
            while (j < m)
            {
              localCodecProfileLevelList.a(str, localObject[j]);
              j += 1;
            }
          }
        }
        i += 1;
      }
    }
    return localCodecProfileLevelList.a();
  }
  
  @CalledByNative
  static boolean isDecoderSupportedForDevice(String paramString)
  {
    if (paramString.equals("video/x-vnd.on2.vp8"))
    {
      if (Build.VERSION.SDK_INT < 18) {
        return false;
      }
      if (Build.MANUFACTURER.toLowerCase(Locale.getDefault()).equals("samsung"))
      {
        if ((Build.VERSION.SDK_INT < 21) && ((Build.MODEL.startsWith("GT-I9505")) || (Build.MODEL.startsWith("GT-I9500")))) {
          return false;
        }
        if (!Build.MODEL.startsWith("GT-I9190"))
        {
          if (Build.MODEL.startsWith("GT-I9195")) {
            return false;
          }
          if (Build.VERSION.SDK_INT <= 19)
          {
            if (Build.MODEL.startsWith("GT-")) {
              return false;
            }
            if (Build.MODEL.startsWith("SCH-")) {
              return false;
            }
            if (Build.MODEL.startsWith("SM-T")) {
              return false;
            }
            if (Build.MODEL.startsWith("SM-G")) {
              return false;
            }
          }
        }
        else
        {
          return false;
        }
      }
      if (Build.HARDWARE.startsWith("mt")) {
        return false;
      }
      if ((Build.VERSION.SDK_INT <= 19) && (Build.MODEL.startsWith("Lenovo A6000"))) {
        return false;
      }
    }
    else if (paramString.equals("video/x-vnd.on2.vp9"))
    {
      if (Build.VERSION.SDK_INT < 19) {
        return false;
      }
      if ((Build.VERSION.SDK_INT < 21) && (Build.HARDWARE.startsWith("mt"))) {
        return false;
      }
      if (Build.MODEL.equals("Nexus Player")) {
        return false;
      }
    }
    else if ((paramString.equals("audio/opus")) && (Build.VERSION.SDK_INT < 21))
    {
      return false;
    }
    return true;
  }
  
  @CalledByNative
  static boolean isEncoderSupportedByDevice(String paramString)
  {
    if (Build.VERSION.SDK_INT < 19) {
      return false;
    }
    if ((paramString.equals("video/avc")) && (Arrays.asList(jdField_a_of_type_ArrayOfJavaLangString).contains(Build.MODEL))) {
      return false;
    }
    return a(paramString) != null;
  }
  
  @CalledByNative
  static boolean isMSEBlackList(String paramString)
  {
    return SmttServiceClientProxy.getInstance().isMSEBlackList(paramString);
  }
  
  @CalledByNative
  static boolean isMediaCodecBlackList()
  {
    return SmttServiceClientProxy.getInstance().isMediaCodecBlackList(Build.MODEL);
  }
  
  @CalledByNative
  static boolean isSetOutputSurfaceSupported()
  {
    return (Build.VERSION.SDK_INT >= 23) && (!Build.HARDWARE.equalsIgnoreCase("hi6210sft")) && (!Build.HARDWARE.equalsIgnoreCase("hi6250"));
  }
  
  @CalledByNative
  public static boolean platformSupportsCbcsEncryption(int paramInt)
  {
    return paramInt >= 25;
  }
  
  public static class CodecCreationInfo
  {
    public MediaCodec a;
    public BitrateAdjuster a = BitrateAdjuster.a;
    public boolean a;
  }
  
  private static enum HWEncoderProperties
  {
    private final int jdField_a_of_type_Int;
    private final String jdField_a_of_type_JavaLangString;
    private final BitrateAdjuster jdField_a_of_type_OrgChromiumMediaBitrateAdjuster;
    private final String b;
    
    static
    {
      jdField_a_of_type_OrgChromiumMediaMediaCodecUtil$HWEncoderProperties = new HWEncoderProperties("QcomVp8", 0, "video/x-vnd.on2.vp8", "OMX.qcom.", 19, BitrateAdjuster.jdField_a_of_type_OrgChromiumMediaBitrateAdjuster);
      jdField_b_of_type_OrgChromiumMediaMediaCodecUtil$HWEncoderProperties = new HWEncoderProperties("QcomH264", 1, "video/avc", "OMX.qcom.", 19, BitrateAdjuster.jdField_a_of_type_OrgChromiumMediaBitrateAdjuster);
      c = new HWEncoderProperties("ExynosVp8", 2, "video/x-vnd.on2.vp8", "OMX.Exynos.", 23, BitrateAdjuster.jdField_a_of_type_OrgChromiumMediaBitrateAdjuster);
      d = new HWEncoderProperties("ExynosH264", 3, "video/avc", "OMX.Exynos.", 21, BitrateAdjuster.b);
    }
    
    private HWEncoderProperties(String paramString1, String paramString2, int paramInt, BitrateAdjuster paramBitrateAdjuster)
    {
      this.jdField_a_of_type_JavaLangString = paramString1;
      this.jdField_b_of_type_JavaLangString = paramString2;
      this.jdField_a_of_type_Int = paramInt;
      this.jdField_a_of_type_OrgChromiumMediaBitrateAdjuster = paramBitrateAdjuster;
    }
    
    public int a()
    {
      return this.jdField_a_of_type_Int;
    }
    
    public String a()
    {
      return this.jdField_a_of_type_JavaLangString;
    }
    
    public BitrateAdjuster a()
    {
      return this.jdField_a_of_type_OrgChromiumMediaBitrateAdjuster;
    }
    
    public String b()
    {
      return this.jdField_b_of_type_JavaLangString;
    }
  }
  
  private static class MediaCodecListHelper
    implements Iterable<MediaCodecInfo>
  {
    private MediaCodecInfo[] a;
    
    @TargetApi(21)
    public MediaCodecListHelper()
    {
      if (a()) {}
      try
      {
        this.a = new MediaCodecList(1).getCodecInfos();
        return;
      }
      catch (RuntimeException localRuntimeException) {}
    }
    
    private int a()
    {
      if (b()) {
        return this.a.length;
      }
      try
      {
        int i = MediaCodecList.getCodecCount();
        return i;
      }
      catch (RuntimeException localRuntimeException)
      {
        for (;;) {}
      }
      return 0;
    }
    
    private MediaCodecInfo a(int paramInt)
    {
      if (b()) {
        return this.a[paramInt];
      }
      return MediaCodecList.getCodecInfoAt(paramInt);
    }
    
    private static boolean a()
    {
      return Build.VERSION.SDK_INT >= 21;
    }
    
    private boolean b()
    {
      return (a()) && (this.a != null);
    }
    
    public Iterator<MediaCodecInfo> iterator()
    {
      return new CodecInfoIterator(null);
    }
    
    private class CodecInfoIterator
      implements Iterator<MediaCodecInfo>
    {
      private int jdField_a_of_type_Int = 0;
      
      private CodecInfoIterator() {}
      
      public MediaCodecInfo a()
      {
        if (this.jdField_a_of_type_Int != MediaCodecUtil.MediaCodecListHelper.a(MediaCodecUtil.MediaCodecListHelper.this))
        {
          MediaCodecUtil.MediaCodecListHelper localMediaCodecListHelper = MediaCodecUtil.MediaCodecListHelper.this;
          int i = this.jdField_a_of_type_Int;
          this.jdField_a_of_type_Int = (i + 1);
          return MediaCodecUtil.MediaCodecListHelper.a(localMediaCodecListHelper, i);
        }
        throw new NoSuchElementException();
      }
      
      public boolean hasNext()
      {
        return this.jdField_a_of_type_Int < MediaCodecUtil.MediaCodecListHelper.a(MediaCodecUtil.MediaCodecListHelper.this);
      }
      
      public void remove()
      {
        throw new UnsupportedOperationException();
      }
    }
  }
  
  public static final class MimeTypes {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\MediaCodecUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */