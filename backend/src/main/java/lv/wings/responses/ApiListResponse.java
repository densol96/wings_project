package lv.wings.responses;

import java.util.List;

public class ApiListResponse<T> {
   private String message;
   private List<T> result;

   public ApiListResponse(String message, List<T> result) {
      this.message = message;
      this.result = result;
   }
}
