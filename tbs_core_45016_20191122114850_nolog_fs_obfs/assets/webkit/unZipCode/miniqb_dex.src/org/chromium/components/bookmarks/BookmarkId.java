package org.chromium.components.bookmarks;

import org.chromium.base.annotations.CalledByNative;

public class BookmarkId
{
  private final int jdField_a_of_type_Int;
  private final long jdField_a_of_type_Long;
  
  public BookmarkId(long paramLong, int paramInt)
  {
    this.jdField_a_of_type_Long = paramLong;
    this.jdField_a_of_type_Int = paramInt;
  }
  
  private String a()
  {
    if (this.jdField_a_of_type_Int != 1) {
      return "";
    }
    return String.valueOf('p');
  }
  
  @CalledByNative
  private static BookmarkId createBookmarkId(long paramLong, int paramInt)
  {
    return new BookmarkId(paramLong, paramInt);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof BookmarkId;
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    paramObject = (BookmarkId)paramObject;
    bool1 = bool2;
    if (((BookmarkId)paramObject).jdField_a_of_type_Long == this.jdField_a_of_type_Long)
    {
      bool1 = bool2;
      if (((BookmarkId)paramObject).jdField_a_of_type_Int == this.jdField_a_of_type_Int) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  @CalledByNative
  public long getId()
  {
    return this.jdField_a_of_type_Long;
  }
  
  @CalledByNative
  public int getType()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public int hashCode()
  {
    return toString().hashCode();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(a());
    localStringBuilder.append(this.jdField_a_of_type_Long);
    return localStringBuilder.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\bookmarks\BookmarkId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */