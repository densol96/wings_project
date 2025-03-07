package lv.wings.responses;

import java.util.ArrayList;

public class ApiArrayListResponse <T> {
   private String message;
   private ArrayList<T> result;

   public ApiArrayListResponse(String message, ArrayList<T> result){
        this.message = message;
        this.result = result;
   }

   public String getMessage(){
      return this.message;
   }

   public ArrayList<T> getResult(){
      return this.result;
   }

}
