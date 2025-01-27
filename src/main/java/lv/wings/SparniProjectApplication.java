package lv.wings;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
import lv.wings.model.security.MyAuthority;
import lv.wings.model.security.MyUser;
import lv.wings.repo.ICustomerRepo;
import lv.wings.repo.IDeliveryTypeRepo;
import lv.wings.repo.IEventCategory;
import lv.wings.repo.IEventPictureRepo;
import lv.wings.repo.IEventRepo;
import lv.wings.repo.IPaymentTypeRepo;
import lv.wings.repo.IProductCategoryRepo;
import lv.wings.repo.IProductPictureRepo;
import lv.wings.repo.IProductRepo;
import lv.wings.repo.IPurchaseElementRepo;
import lv.wings.repo.IPurchaseRepo;
import lv.wings.repo.security.IMyAuthorityRepo;
import lv.wings.repo.security.IMyUserRepo;
import net.datafaker.Faker;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SparniProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SparniProjectApplication.class, args);
	}

	@Bean
	public CommandLineRunner sparniDB(ICustomerRepo customerRepo, IPurchaseRepo purchaseRepo,
			IDeliveryTypeRepo deliveryTypeRepo, IPaymentTypeRepo paymentTypeRepo,
			IEventRepo eventRepo, IEventPictureRepo eventPictureRepo, IEventCategory eventCategoryRepo,
			IProductCategoryRepo productCategoryRepo, IPurchaseElementRepo purchaseElementRepo,
			IProductRepo productRepo, IProductPictureRepo productPictureRepo, IMyAuthorityRepo authRepo, IMyUserRepo userRepo) {

		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {


				DeliveryType deliveryType1 = new DeliveryType("Piegades Veids1", "Apraksts1");
				DeliveryType deliveryType2 = new DeliveryType("Piegades Veids2", "Apraksts2");
				deliveryTypeRepo.save(deliveryType1);
				deliveryTypeRepo.save(deliveryType2);

				PaymentType paymentType1 = new PaymentType("Samaksas Veids 1", "Piezimes1");
				PaymentType paymentType2 = new PaymentType("Samaksas Veids 2", "Piezimes2");
				paymentTypeRepo.save(paymentType1);
				paymentTypeRepo.save(paymentType2);


				Faker faker = new Faker();


				EventCategory newCat = new EventCategory("fake data category");
				eventCategoryRepo.save(newCat);
				for(int i = 0; i < 50; i++) {
					String dfName = faker.name().firstName();
					String dfSurname = faker.name().lastName();
					String dfEmail = faker.expression("#{regexify '[A-Za-z0-9]{6,10}'}") + "@gmail.com";
					String dfAdress = faker.address().cityName() + ", " + faker.address().buildingNumber();
					// String dfPersonasKods = faker.expression("#{regexify '[0-9]{6}-[0-9]{5}'}");
					// String dfBankasNosaukums = faker.expression("#{options.option 'Liela banka', 'Maza banka', 'XL banka' 'XS banka'}");
					// String dfBankasSwiftKods = faker.expression("#{regexify '[0-9]{6}'}");
					// String dfBankasKods = "Bankas kods " + faker.expression("#{numerify '###'}");

					//// event fake data
					/// 
					//(Date startDate, Date endDate, String title, String location,
	//	String description, String keyWords, EventCategory eventCategory
					Date fakeDate = Date.from(faker.timeAndDate().past(10000, TimeUnit.DAYS));
					Event event = new Event(fakeDate, fakeDate, faker.text().text(20), 
					faker.address().city(), "orem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi." , "fake keyword", newCat);

					eventRepo.save(event);

					Customer dfCustomer = new Customer(dfName, dfSurname, dfEmail, dfAdress);

					customerRepo.save(dfCustomer);

					String d = faker.date().future(300, TimeUnit.HOURS, "YYYY-MM-dd hh:mm");
        			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        			LocalDateTime dateTime = LocalDateTime.parse(d, formatter);

					Purchase newPurchase;
					if(Integer.parseInt(faker.expression("#{numerify '#'}")) > 5) {
						newPurchase = new Purchase(deliveryType1, paymentType2, dfCustomer, dateTime,
						"Nav detalas");
					} else {
						newPurchase = new Purchase(deliveryType2, paymentType1, dfCustomer, dateTime,
						"Lielas detalas");
					}
					purchaseRepo.save(newPurchase);
				}

				Customer customer1 = new Customer("Markuss", "Blumbergs", "random1@gmail.com", "Talsi, Street 1");
				Customer customer2 = new Customer("Lauris", "Kairo", "random2@gmail.com", "Liepaja, Street 4");
				customerRepo.save(customer1);
				customerRepo.save(customer2);

				Purchase purchase1 = new Purchase(deliveryType1, paymentType1, customer1, LocalDateTime.now(),
						"Nav detalas");
				Purchase purchase2 = new Purchase(deliveryType2, paymentType2, customer1, LocalDateTime.now(),
						"Nav detalas");
				purchaseRepo.save(purchase1);
				purchaseRepo.save(purchase2);

				EventCategory eventCategory1 = new EventCategory("Sporta spēles");
				EventCategory eventCategory2 = new EventCategory("Vasaras svētki");
				EventCategory eventCategory3 = new EventCategory("Līgo");

				eventCategoryRepo.save(eventCategory1);
				eventCategoryRepo.save(eventCategory2);
				eventCategoryRepo.save(eventCategory3);
 
				Event pasakums1 = new Event(Date.from(Instant.now()), Date.from(Instant.now().plusSeconds(86400 * 1)), "Pasakums 1",
						"Liepāja", "Pasākums visiem cilvēkiem!", "vasara atpūta", eventCategory1);
				Event pasakums2 = new Event(Date.from(Instant.now()), Date.from(Instant.now().plusSeconds(86400 * 2)), "Pasakums 2",
						"Ventspils", "Pasākums visiem cilvēkiem!", "vasara atpūta", eventCategory2);
				Event pasakums3 = new Event(Date.from(Instant.now()), Date.from(Instant.now().plusSeconds(86400 * 3)), "Pasakums 3",
						"Rīga", "Pasākums visiem cilvēkiem!", "vasara beigas", eventCategory2);
				eventRepo.save(pasakums1);
				eventRepo.save(pasakums2);
				eventRepo.save(pasakums3);

				EventPicture bilde1 = new EventPicture("bilde1.jpg", "bilde1", "laba bilde", pasakums1);
				EventPicture bilde2 = new EventPicture("bilde2.jpg", "bilde2", "slikta bilde", pasakums2);
				EventPicture bilde3 = new EventPicture("bilde3.jpg", "bilde3", "laba bilde", pasakums1);
				eventPictureRepo.save(bilde1);
				eventPictureRepo.save(bilde2);
				eventPictureRepo.save(bilde3);

				// KATEGORIJAS
				ProductCategory kategorija1 = new ProductCategory("Cepure", "Galvas segas");
				ProductCategory kategorija2 = new ProductCategory("Cimdi", "Adīti cimdi");
				ProductCategory kategorija3 = new ProductCategory("Zeķes", "Adītas zeķes");
				productCategoryRepo.save(kategorija1);
				productCategoryRepo.save(kategorija2);
				productCategoryRepo.save(kategorija3);

				// PRECES
				Product product1 = new Product("Adīti ziemas dūraiņi", "Krustūrīenā izšūti adīti ziemas cimdi", 13.60f, 6,
						kategorija2);
				Product product2 = new Product("Tamborētas zeķes pusaudžiem", "Baltas taborētas zeķes izmērs 35", 12.20f, 4,
						kategorija3);
				productRepo.save(product1);
				productRepo.save(product2);

				// PIRKUMA ELEMENTS
				PurchaseElement purchaseElement1 = new PurchaseElement(purchase1, product1, 2);
				PurchaseElement purchaseElement2 = new PurchaseElement(purchase2, product2, 1);
				PurchaseElement purchaseElement3 = new PurchaseElement(purchase2, product1, 1);
				purchaseElementRepo.save(purchaseElement1);
				purchaseElementRepo.save(purchaseElement2);
				purchaseElementRepo.save(purchaseElement3);

				// BILDES
				// public Preces_bilde(String bilde, String apraksts, Prece product)
				ProductPicture bilde11 = new ProductPicture("bilde1.jpg", "Zeķes1", product1);
				ProductPicture bilde22 = new ProductPicture("bilde2.jpg", "Zeķes2", product1);
				ProductPicture bilde33 = new ProductPicture("bilde3.jpg", "Cimdi1", product2);
				productPictureRepo.save(bilde11);
				productPictureRepo.save(bilde22);
				productPictureRepo.save(bilde33);
				
				//USER & AUTHORITY
				MyAuthority a1 = new MyAuthority("ADMIN");
				authRepo.save(a1);
				
				MyAuthority a2 = new MyAuthority("USER"); //TODO: jāizrunā vai šādu lomu vajag
				authRepo.save(a2);
				
				PasswordEncoder encoder = new BCryptPasswordEncoder();
				

				MyUser u1 = new MyUser("annija.user", encoder.encode("123"),a2);
				userRepo.save(u1);
				
				MyUser u2 = new MyUser("annija.admin", encoder.encode("456"),a1);
				userRepo.save(u2);
				
				MyUser u3 = new MyUser("admin", encoder.encode("123"),a1);
				userRepo.save(u3);

			}
		};

	}

}