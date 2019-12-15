package org.chromium.content_public.common;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("content")
public final class MediaMetadata
{
  @NonNull
  private String jdField_a_of_type_JavaLangString;
  @NonNull
  private List<MediaImage> jdField_a_of_type_JavaUtilList = new ArrayList();
  @NonNull
  private String b;
  @NonNull
  private String c;
  
  public MediaMetadata(@NonNull String paramString1, @NonNull String paramString2, @NonNull String paramString3)
  {
    this.jdField_a_of_type_JavaLangString = paramString1;
    this.b = paramString2;
    this.c = paramString3;
  }
  
  @CalledByNative
  private static MediaMetadata create(String paramString1, String paramString2, String paramString3)
  {
    String str = paramString1;
    if (paramString1 == null) {
      str = "";
    }
    paramString1 = paramString2;
    if (paramString2 == null) {
      paramString1 = "";
    }
    paramString2 = paramString3;
    if (paramString3 == null) {
      paramString2 = "";
    }
    return new MediaMetadata(str, paramString1, paramString2);
  }
  
  @CalledByNative
  private void createAndAddMediaImage(String paramString1, String paramString2, int[] paramArrayOfInt)
  {
    if ((!jdField_a_of_type_Boolean) && (paramArrayOfInt.length % 2 != 0)) {
      throw new AssertionError();
    }
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    for (;;)
    {
      int j = i + 1;
      if (j >= paramArrayOfInt.length) {
        break;
      }
      localArrayList.add(new Rect(0, 0, paramArrayOfInt[i], paramArrayOfInt[j]));
      i += 2;
    }
    this.jdField_a_of_type_JavaUtilList.add(new MediaImage(paramString1, paramString2, localArrayList));
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof MediaMetadata)) {
      return false;
    }
    paramObject = (MediaMetadata)paramObject;
    return (TextUtils.equals(this.jdField_a_of_type_JavaLangString, ((MediaMetadata)paramObject).jdField_a_of_type_JavaLangString)) && (TextUtils.equals(this.b, ((MediaMetadata)paramObject).b)) && (TextUtils.equals(this.c, ((MediaMetadata)paramObject).c)) && (this.jdField_a_of_type_JavaUtilList.equals(((MediaMetadata)paramObject).jdField_a_of_type_JavaUtilList));
  }
  
  public int hashCode()
  {
    return ((this.jdField_a_of_type_JavaLangString.hashCode() * 31 + this.b.hashCode()) * 31 + this.c.hashCode()) * 31 + this.jdField_a_of_type_JavaUtilList.hashCode();
  }
  
  public static final class MediaImage
  {
    @NonNull
    private String jdField_a_of_type_JavaLangString;
    @NonNull
    private List<Rect> jdField_a_of_type_JavaUtilList = new ArrayList();
    private String b;
    
    public MediaImage(@NonNull String paramString1, @NonNull String paramString2, @NonNull List<Rect> paramList)
    {
      this.jdField_a_of_type_JavaLangString = paramString1;
      this.b = paramString2;
      this.jdField_a_of_type_JavaUtilList = paramList;
    }
    
    public boolean equals(Object paramObject)
    {
      if (paramObject == this) {
        return true;
      }
      if (!(paramObject instanceof MediaImage)) {
        return false;
      }
      paramObject = (MediaImage)paramObject;
      return (TextUtils.equals(this.jdField_a_of_type_JavaLangString, ((MediaImage)paramObject).jdField_a_of_type_JavaLangString)) && (TextUtils.equals(this.b, ((MediaImage)paramObject).b)) && (this.jdField_a_of_type_JavaUtilList.equals(((MediaImage)paramObject).jdField_a_of_type_JavaUtilList));
    }
    
    public int hashCode()
    {
      return (this.jdField_a_of_type_JavaLangString.hashCode() * 31 + this.b.hashCode()) * 31 + this.jdField_a_of_type_JavaUtilList.hashCode();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\common\MediaMetadata.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */