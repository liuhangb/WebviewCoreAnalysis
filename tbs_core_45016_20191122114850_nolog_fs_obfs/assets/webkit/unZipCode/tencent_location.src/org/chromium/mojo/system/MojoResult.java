package org.chromium.mojo.system;

public final class MojoResult
{
  public static String a(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return "UNKNOWN";
    case 17: 
      return "SHOULD_WAIT";
    case 16: 
      return "BUSY";
    case 15: 
      return "DATA_LOSS";
    case 14: 
      return "UNAVAILABLE";
    case 13: 
      return "INTERNAL";
    case 12: 
      return "UNIMPLEMENTED";
    case 11: 
      return "OUT_OF_RANGE";
    case 10: 
      return "ABORTED";
    case 9: 
      return "FAILED_PRECONDITION";
    case 8: 
      return "RESOURCE_EXHAUSTED";
    case 7: 
      return "PERMISSION_DENIED";
    case 6: 
      return "ALREADY_EXISTS";
    case 5: 
      return "NOT_FOUND";
    case 4: 
      return "DEADLINE_EXCEEDED";
    case 3: 
      return "INVALID_ARGUMENT";
    case 2: 
      return "UNKNOWN";
    case 1: 
      return "CANCELLED";
    }
    return "OK";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\MojoResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */