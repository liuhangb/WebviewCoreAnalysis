package org.chromium.content.app;

import android.os.Bundle;
import java.util.Locale;

public class ChromiumLinkerParams
{
  public final long a;
  public final String a;
  public final boolean a;
  
  public ChromiumLinkerParams(long paramLong, boolean paramBoolean)
  {
    this.jdField_a_of_type_Long = paramLong;
    this.jdField_a_of_type_Boolean = paramBoolean;
    this.jdField_a_of_type_JavaLangString = null;
  }
  
  public ChromiumLinkerParams(long paramLong, boolean paramBoolean, String paramString)
  {
    this.jdField_a_of_type_Long = paramLong;
    this.jdField_a_of_type_Boolean = paramBoolean;
    this.jdField_a_of_type_JavaLangString = paramString;
  }
  
  private ChromiumLinkerParams(Bundle paramBundle)
  {
    this.jdField_a_of_type_Long = paramBundle.getLong("org.chromium.content.common.linker_params.base_load_address", 0L);
    this.jdField_a_of_type_Boolean = paramBundle.getBoolean("org.chromium.content.common.linker_params.wait_for_shared_relro", false);
    this.jdField_a_of_type_JavaLangString = paramBundle.getString("org.chromium.content.common.linker_params.test_runner_class_name");
  }
  
  public static ChromiumLinkerParams a(Bundle paramBundle)
  {
    if ((paramBundle.containsKey("org.chromium.content.common.linker_params.base_load_address")) && (paramBundle.containsKey("org.chromium.content.common.linker_params.wait_for_shared_relro")) && (paramBundle.containsKey("org.chromium.content.common.linker_params.test_runner_class_name"))) {
      return new ChromiumLinkerParams(paramBundle);
    }
    return null;
  }
  
  public void a(Bundle paramBundle)
  {
    paramBundle.putLong("org.chromium.content.common.linker_params.base_load_address", this.jdField_a_of_type_Long);
    paramBundle.putBoolean("org.chromium.content.common.linker_params.wait_for_shared_relro", this.jdField_a_of_type_Boolean);
    paramBundle.putString("org.chromium.content.common.linker_params.test_runner_class_name", this.jdField_a_of_type_JavaLangString);
  }
  
  public String toString()
  {
    return String.format(Locale.US, "LinkerParams(baseLoadAddress:0x%x, waitForSharedRelro:%s, testRunnerClassName:%s", new Object[] { Long.valueOf(this.jdField_a_of_type_Long), Boolean.toString(this.jdField_a_of_type_Boolean), this.jdField_a_of_type_JavaLangString });
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\app\ChromiumLinkerParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */