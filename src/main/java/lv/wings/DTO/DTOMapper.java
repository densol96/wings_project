package lv.wings.DTO;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

import lv.wings.DTO.object.DTOObject;

public class DTOMapper {
	public static <T extends DTOObject> ArrayList<T> mapMany(Class<T> responseType, Object[] targets) throws Exception{
		ArrayList<T> response = new ArrayList<>();
		

		for(Object target: targets){
			response.add(map(responseType, target));
		}

		return response;
	}

	public static <T extends DTOObject> T map(Class<T> responseType, Object target) throws Exception{
		T response = responseType.getConstructor().newInstance();

		Class<?> targetClass = target.getClass();
		
		//all field of the DTO class
		Field[] resourceFields = responseType.getDeclaredFields();
		//all methods of the base class
		Method[] targetMethods = targetClass.getMethods();

		//loop through all the fields
		for(Field resourceField : resourceFields){

			//get the corresponding getter
			Method targetMethod = getGetMethod(resourceField, targetMethods);
			if(targetMethod == null){
				continue;
			}
			//result type information of field
			Class<?> fieldClass = resourceField.getType();
			Type fieldType = resourceField.getGenericType();
			
			//result type information of contents
			Type methodType = targetMethod.getGenericReturnType();
			//the result data to be put in
			Object executionResult = targetMethod.invoke(target);

			//if the result types are the same - shove the data inside
			if (fieldClass.toString().equals(methodType.toString())) {
				resourceField.set(response, executionResult);
			}else{
				//if the result types are different
				
				//if the result type is generic -> expecting it to be a collection
				if (fieldType instanceof ParameterizedType) {
					//the real data types
					ParameterizedType fieldPType = (ParameterizedType)fieldType;

					Type fieldCollectionType = fieldPType.getRawType();
					Type fieldCollectionContentType = fieldPType.getActualTypeArguments()[0];
					Class<?> newFieldCollectionContentType = (Class<?>) fieldCollectionContentType;

					//creating a collection type with its default non generic constructor (TODO: find a way to create a generic object)
					Class<?> newFieldClass = (Class<?>) fieldCollectionType;
					Object newObj = newFieldClass.getDeclaredConstructor().newInstance();
					resourceField.set(response, newObj);

					//get the toArray method to iterate
					Method toArrayMethod = executionResult.getClass().getMethod("toArray");
					
					if(toArrayMethod != null){
						//get the "add" method to fill up the collection; .getMethod("add") didnt work
						Method addMethod = null;
						for(Method method : newFieldClass.getMethods()){
							if(method.getName().equals("add")){
								addMethod = method;
								break;
							}
							System.out.println("hasm: " + method.getName());
						}
						if(addMethod == null){
							System.out.println("add method not found: ");
							continue;
						}
						
						//get all the content of the result colection
						Object[] allContentsAsArray = (Object[]) toArrayMethod.invoke(executionResult);
						//fill up the new collection with new content
						for (Object elem : allContentsAsArray) {
							addMethod.invoke(newObj, map((Class<T>) newFieldCollectionContentType, elem));
						}
					}

				}else
				if(fieldType instanceof Class<?>){
					//if the resulting entity is a model
					
					//set the propery with the new DTO class
					Class<?> newFieldClass = (Class<?>) fieldType;
					resourceField.set(response, map((Class<T>) newFieldClass, executionResult));
				}
			}
		}

		return response;
	}

	private static Method getGetMethod(Field field, Method[] methods){
		String fieldName = field.getName();
		String getterName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
		System.out.println("Search of: " + getterName);
		for (Method method : methods) {
			System.out.println("Compare to: " + method.getName());
			if(method.getName().equals(getterName)) return method;
		}
		return null;
	}
}