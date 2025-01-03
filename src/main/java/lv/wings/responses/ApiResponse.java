package lv.wings.responses;



public class ApiResponse <T> {

    private String message;
    private T result;

   public ApiResponse(String message, T result){
        this.message = message;
        this.result = result;
   }

   public String getMessage(){
      return this.message;
   }

   public T getResult(){
      return this.result;
   }
}
