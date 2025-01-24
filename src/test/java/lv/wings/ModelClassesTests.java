package lv.wings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lv.wings.model.Customer;
import lv.wings.model.DeliveryType;
import lv.wings.model.Event;
import lv.wings.model.EventCategory;
import lv.wings.model.EventPicture;
import lv.wings.model.PaymentType;
import lv.wings.model.Product;
import lv.wings.model.ProductCategory;
import lv.wings.model.ProductPicture;
import lv.wings.model.Purchase;
import lv.wings.model.PurchaseElement;
import net.datafaker.Faker;

@SpringBootTest
public class ModelClassesTests {

    Faker faker = new Faker();


    @Test
    void customerTest() {
        String testName = faker.name().firstName();
        String testSurname = faker.name().lastName();
        String testEmail = faker.expression("#{regexify '[A-Za-z0-9]{6,10}'}") + "@gmail.com";
        String testAdress = faker.address().cityName() + ", " + faker.address().buildingNumber();
        Customer testCustomer = new Customer(testName, testSurname, testEmail, testAdress);

        // Test get methods
        assertEquals(testName, testCustomer.getName());
        assertEquals(testSurname, testCustomer.getSurname());
        assertEquals(testEmail, testCustomer.getEmail());
        assertEquals(testAdress, testCustomer.getAdress());

        // Test set methods
        testName = faker.name().firstName();
        testSurname = faker.name().lastName();
        testEmail = faker.expression("#{regexify '[A-Za-z0-9]{6,10}'}") + "@gmail.com";
        testAdress = faker.address().cityName() + ", " + faker.address().buildingNumber();

        testCustomer.setName(testName);
        testCustomer.setSurname(testSurname);
        testCustomer.setEmail(testEmail);
        testCustomer.setAdress(testAdress);

        assertEquals(testName, testCustomer.getName());
        assertEquals(testSurname, testCustomer.getSurname());
        assertEquals(testEmail, testCustomer.getEmail());
        assertEquals(testAdress, testCustomer.getAdress());
    }


    @Test
    void deliveryTypeTest() {
        String testTitle = faker.expression("#{letterify '?????'}");
        String testDesc = faker.expression("#{letterify '????? ???????'}");
        DeliveryType testDeliveryType = new DeliveryType(testTitle, testDesc);

        // Test get methods
        assertEquals(testTitle, testDeliveryType.getTitle());
        assertEquals(testDesc, testDeliveryType.getDescription());

        // Test set methods
        testTitle = faker.expression("#{letterify '?????'}");
        testDesc = faker.expression("#{letterify '????? ???????'}");

        testDeliveryType.setTitle(testTitle);
        testDeliveryType.setDescription(testDesc);

        assertEquals(testTitle, testDeliveryType.getTitle());
        assertEquals(testDesc, testDeliveryType.getDescription());

        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        // String formattedTimeCreated = testDeliveryType.getCreateDate().format(formatter);
        // String formattedNow = LocalDateTime.now().format(formatter);

        // assertEquals(formattedTimeCreated, formattedNow);
    }

    @Test
    void eventCategoryTest() {
        String testTitle = faker.expression("#{letterify '?????'}");
        EventCategory testEventCategory = new EventCategory(testTitle);

        // Test get methods
        assertEquals(testTitle, testEventCategory.getTitle());

        // Test set methods
        testTitle = faker.expression("#{letterify '?????'}");
        testEventCategory.setTitle(testTitle);

        assertEquals(testTitle, testEventCategory.getTitle());
    }
    
    @Test
    void eventTest() {

        Date testStartDate = new Date();
        Date testEndDate = new Date();
        String testTitle = faker.expression("#{letterify '?????'}");
        String testLocation = faker.expression("#{letterify '????????'}");
        String testDescription = faker.expression("#{letterify '????? ???????'}");
        String testKeyWords = faker.expression("#{letterify '??????'}");

        String testEventCategoryTitle = faker.expression("#{letterify '?????'}");
        EventCategory testEventCategory = new EventCategory(testEventCategoryTitle);

        // Test get methods
        Event testEvent = new Event(testStartDate, testEndDate, testTitle, testLocation, testDescription, testKeyWords, testEventCategory);
        assertEquals(testStartDate, testEvent.getStartDate());
        assertEquals(testEndDate, testEvent.getEndDate());
        assertEquals(testTitle, testEvent.getTitle());
        assertEquals(testLocation, testEvent.getLocation());
        assertEquals(testDescription, testEvent.getDescription());
        assertEquals(testKeyWords, testEvent.getKeyWords());
        assertEquals(testEventCategory.getTitle(), testEvent.getEventCategory().getTitle());

        // Test set methods
        testStartDate = new Date(2026, 4, 17);
        testEndDate = new Date(2025, 4, 18);        // Test finishes with end date being before start date
        testTitle = faker.expression("#{letterify '?????'}");
        testLocation = faker.expression("#{letterify '????????'}");
        testDescription = faker.expression("#{letterify '????? ???????'}");
        testKeyWords = faker.expression("#{letterify '??????'}");

        testEventCategoryTitle = faker.expression("#{letterify '?????'}");
        testEventCategory = new EventCategory(testEventCategoryTitle);

        testEvent.setStartDate(testStartDate);
        testEvent.setEndDate(testEndDate);
        testEvent.setTitle(testTitle);
        testEvent.setLocation(testLocation);
        testEvent.setDescription(testDescription);
        testEvent.setKeyWords(testKeyWords);
        testEvent.setEventCategory(testEventCategory);

        assertEquals(testStartDate, testEvent.getStartDate());
        assertEquals(testEndDate, testEvent.getEndDate());
        assertEquals(testTitle, testEvent.getTitle());
        assertEquals(testLocation, testEvent.getLocation());
        assertEquals(testDescription, testEvent.getDescription());
        assertEquals(testKeyWords, testEvent.getKeyWords());
        assertEquals(testEventCategory.getTitle(), testEvent.getEventCategory().getTitle());

    }

    @Test
    void eventPictureTest() {
        String testReferenceToPicture = faker.expression("#{letterify '?????.jpg'}");
        String testTitle = faker.expression("#{letterify '?????'}");
        String testDescription = faker.expression("#{letterify '????? ???????'}");
        Event testEvent = new Event();

        EventPicture testEventPicture = new EventPicture(testReferenceToPicture, testTitle, testDescription, testEvent);
        assertEquals(testReferenceToPicture, testEventPicture.getReferenceToPicture());
        assertEquals(testTitle, testEventPicture.getTitle());
        assertEquals(testDescription, testEventPicture.getDescription());
        assertEquals(testEvent, testEventPicture.getEvent());

        testReferenceToPicture = faker.expression("#{letterify '?????.jpg'}");
        testTitle = faker.expression("#{letterify '?????'}");
        testDescription = faker.expression("#{letterify '????? ???????'}");
        testEvent = new Event();
        testEvent.setLocation("Talsi");

        testEventPicture.setReferenceToPicture(testReferenceToPicture);
        testEventPicture.setTitle(testTitle);
        testEventPicture.setDescription(testDescription);
        testEventPicture.setEvent(testEvent);

        assertEquals(testReferenceToPicture, testEventPicture.getReferenceToPicture());
        assertEquals(testTitle, testEventPicture.getTitle());
        assertEquals(testDescription, testEventPicture.getDescription());
        assertEquals(testEvent.getLocation(), testEventPicture.getEvent().getLocation());
    }

    @Test
    void paymentTypeTest() {
        
        String testTitle = faker.expression("#{letterify '?????'}");
        String testDesc = faker.expression("#{letterify '????? ???????'}");
        PaymentType testPaymentType = new PaymentType(testTitle, testDesc);

        // Test get methods
        assertEquals(testTitle, testPaymentType.getTitle());
        assertEquals(testDesc, testPaymentType.getDescription());

        // Test set methods
        testTitle = faker.expression("#{letterify '?????'}");
        testDesc = faker.expression("#{letterify '????? ???????'}");

        testPaymentType.setTitle(testTitle);
        testPaymentType.setDescription(testDesc);

        assertEquals(testTitle, testPaymentType.getTitle());
        assertEquals(testDesc, testPaymentType.getDescription());
    }

    @Test
    void productCategoryTest() {
        String testTitle = faker.expression("#{letterify '?????'}");
        String testDesc = faker.expression("#{letterify '????? ???????'}");
        ProductCategory testProductCategory = new ProductCategory(testTitle, testDesc);

        // Test get methods
        assertEquals(testTitle, testProductCategory.getTitle());
        assertEquals(testDesc, testProductCategory.getDescription());

        // Test set methods
        testTitle = faker.expression("#{letterify '?????'}");
        testDesc = faker.expression("#{letterify '????? ???????'}");

        testProductCategory.setTitle(testTitle);
        testProductCategory.setDescription(testDesc);

        assertEquals(testTitle, testProductCategory.getTitle());
        assertEquals(testDesc, testProductCategory.getDescription());
    }

    @Test
    void productTest() {
        String testTitle = faker.expression("#{letterify '?????'}");
        String testDesc = faker.expression("#{letterify '????? ???????'}");
        float testPrice = 1.22f;
        int testAmount = 5;

        String testProductCategoryTitle = faker.expression("#{letterify '?????'}");
        String testProductCategoryDesc = faker.expression("#{letterify '????? ???????'}");
        ProductCategory testProductCategory = new ProductCategory(testProductCategoryTitle, testProductCategoryDesc);

        Product testProduct = new Product(testTitle, testDesc, testPrice, testAmount, testProductCategory);

        //get
        assertEquals(testTitle, testProduct.getTitle());
        assertEquals(testDesc, testProduct.getDescription());
        assertEquals(testPrice, testProduct.getPrice());
        assertEquals(testAmount, testProduct.getAmount());
        assertEquals(testProductCategory, testProduct.getProductCategory());

        //set
        testTitle = faker.expression("#{letterify '?????'}");
        testDesc = faker.expression("#{letterify '????? ???????'}");
        float newTestPrice = 4.22f;
        int newTestAmount = 7;
        testProductCategory = new ProductCategory();

        testProduct.setTitle(testTitle);
        testProduct.setDescription(testDesc);
        testProduct.setPrice(newTestPrice);
        testProduct.setAmount(newTestAmount);
        testProduct.setProductCategory(testProductCategory);

        assertEquals(testTitle, testProduct.getTitle());
        assertEquals(testDesc, testProduct.getDescription());
        assertNotEquals(testPrice, testProduct.getPrice());
        assertNotEquals(testAmount, testProduct.getAmount());
        assertEquals(testProductCategory, testProduct.getProductCategory());
    }

    @Test
    void productPictureTest() {
        String testPicture = faker.expression("#{letterify '?????.jpg'}");
        String testDescription = faker.expression("#{letterify '????? ???????'}");   
        Product testProduct = new Product();
        ProductPicture testProductPicture = new ProductPicture(testPicture, testDescription, testProduct);

        //get
        assertEquals(testPicture, testProductPicture.getReferenceToPicture());
        assertEquals(testDescription, testProductPicture.getDescription());
        assertEquals(testProduct, testProductPicture.getProduct());


        //set
        testPicture = faker.expression("#{letterify '?????.jpg'}");
        testDescription = faker.expression("#{letterify '????? ???????'}");
        Product newTestProduct = new Product();
        newTestProduct.setAmount(7); 

        testProductPicture.setReferenceToPicture(testPicture);;
        testProductPicture.setDescription(testDescription);
        testProductPicture.setProduct(newTestProduct);
        
        
        assertEquals(testPicture, testProductPicture.getReferenceToPicture());
        assertEquals(testDescription, testProductPicture.getDescription());
        assertNotEquals(testProduct, testProductPicture.getProduct());

    }

    @Test
    void purchaseTest() {
        DeliveryType testDeliveryType = new DeliveryType();
        PaymentType testPaymentType = new PaymentType();
        Customer testCustomer = new Customer();
        LocalDateTime testDeliveryDate = LocalDateTime.of(2026, 4, 5, 10, 5, 33);
        String testDeliveryDetails = faker.expression("#{letterify '????? ??????? ??? ?????? ? ????? ?????'}");

        Purchase testPurchase = new Purchase(testDeliveryType, testPaymentType, testCustomer, testDeliveryDate, testDeliveryDetails);

        //get
        assertEquals(testDeliveryType, testPurchase.getDeliveryType());
        assertEquals(testPaymentType, testPurchase.getPaymentType());
        assertEquals(testCustomer, testPurchase.getCustomer());
        assertEquals(testDeliveryDate, testPurchase.getDeliveryDate());
        assertEquals(testDeliveryDetails, testPurchase.getDeliveryDetails());

        //set
        DeliveryType newTestDeliveryType = new DeliveryType();
        newTestDeliveryType.setDescription("new description");
        PaymentType newPaymentType = new PaymentType();
        newPaymentType.setDescription("new description");
        Customer newCustomer = new Customer();
        newCustomer.setName("Markuss");
        testDeliveryDate = LocalDateTime.of(2027, 4, 5, 10, 5, 33);
        testDeliveryDetails = faker.expression("#{letterify '????? ??????? ??? ?????? ? ????? ?????'}");

        testPurchase.setDeliveryType(newTestDeliveryType);
        testPurchase.setPaymentType(newPaymentType);
        testPurchase.setCustomer(newCustomer);
        testPurchase.setDeliveryDate(testDeliveryDate);
        testPurchase.setDeliveryDetails(testDeliveryDetails);

        assertNotEquals(testDeliveryType, testPurchase.getDeliveryType());
        assertNotEquals(testPaymentType, testPurchase.getPaymentType());
        assertNotEquals(testCustomer, testPurchase.getCustomer());
        assertEquals(testDeliveryDate, testPurchase.getDeliveryDate());
        assertEquals(testDeliveryDetails, testPurchase.getDeliveryDetails());

    }

    @Test
    void purchaseElementTest() {
        Purchase testPurchase = new Purchase();
        Product testProduct = new Product();
        int testAmount = 5;

        PurchaseElement testPurchaseElement = new PurchaseElement(testPurchase, testProduct, testAmount);
        
        //get
        assertEquals(testPurchase, testPurchaseElement.getPurchase());
        assertEquals(testProduct, testPurchaseElement.getProduct());
        assertEquals(testAmount, testPurchaseElement.getAmount());

        //set
        Purchase newTestPurchase = new Purchase();
        newTestPurchase.setDeliveryDetails("big details");
        Product newTestProduct = new Product();
        newTestProduct.setAmount(8);
        testAmount = 8;

        testPurchaseElement.setPurchase(newTestPurchase);
        testPurchaseElement.setProduct(newTestProduct);
        testPurchaseElement.setAmount(testAmount);

        assertNotEquals(testPurchase, testPurchaseElement.getPurchase());
        assertNotEquals(testProduct, testPurchaseElement.getProduct());
        assertEquals(testAmount, testPurchaseElement.getAmount());

    }
}
