package org.chromium.components.variations.firstrun;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.CalledByNative;

public class VariationsSeedBridge
{
  protected static String a(String paramString)
  {
    return ContextUtils.getAppSharedPreferences().getString(paramString, "");
  }
  
  @CalledByNative
  private static void clearFirstRunPrefs()
  {
    ContextUtils.getAppSharedPreferences().edit().remove("variations_seed_base64").remove("variations_seed_signature").remove("variations_seed_country").remove("variations_seed_date").remove("variations_seed_is_gzip_compressed").apply();
  }
  
  @CalledByNative
  private static String getVariationsFirstRunSeedCountry()
  {
    return a("variations_seed_country");
  }
  
  @CalledByNative
  private static byte[] getVariationsFirstRunSeedData()
  {
    return Base64.decode(a("variations_seed_base64"), 2);
  }
  
  @CalledByNative
  private static String getVariationsFirstRunSeedDate()
  {
    return a("variations_seed_date");
  }
  
  @CalledByNative
  private static boolean getVariationsFirstRunSeedIsGzipCompressed()
  {
    return ContextUtils.getAppSharedPreferences().getBoolean("variations_seed_is_gzip_compressed", false);
  }
  
  @CalledByNative
  private static String getVariationsFirstRunSeedSignature()
  {
    return a("variations_seed_signature");
  }
  
  @CalledByNative
  private static void markVariationsSeedAsStored()
  {
    ContextUtils.getAppSharedPreferences().edit().putBoolean("variations_seed_native_stored", true).apply();
  }
  
  @CalledByNative
  public static void setVariationsFirstRunSeed(byte[] paramArrayOfByte, String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    ContextUtils.getAppSharedPreferences().edit().putString("variations_seed_base64", Base64.encodeToString(paramArrayOfByte, 2)).putString("variations_seed_signature", paramString1).putString("variations_seed_country", paramString2).putString("variations_seed_date", paramString3).putBoolean("variations_seed_is_gzip_compressed", paramBoolean).apply();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\variations\firstrun\VariationsSeedBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */