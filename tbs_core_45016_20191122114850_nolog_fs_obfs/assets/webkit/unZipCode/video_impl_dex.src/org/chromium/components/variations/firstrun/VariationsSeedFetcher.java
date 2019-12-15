package org.chromium.components.variations.firstrun;

import org.chromium.base.VisibleForTesting;

public class VariationsSeedFetcher
{
  private static final Object jdField_a_of_type_JavaLangObject = new Object();
  
  public static class SeedInfo {}
  
  public static enum VariationsPlatform
  {
    static
    {
      jdField_a_of_type_OrgChromiumComponentsVariationsFirstrunVariationsSeedFetcher$VariationsPlatform = new VariationsPlatform("ANDROID", 0);
      b = new VariationsPlatform("ANDROID_WEBVIEW", 1);
      jdField_a_of_type_ArrayOfOrgChromiumComponentsVariationsFirstrunVariationsSeedFetcher$VariationsPlatform = new VariationsPlatform[] { jdField_a_of_type_OrgChromiumComponentsVariationsFirstrunVariationsSeedFetcher$VariationsPlatform, b };
    }
    
    private VariationsPlatform() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\variations\firstrun\VariationsSeedFetcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */