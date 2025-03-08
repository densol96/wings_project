package lv.wings;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;
import lv.wings.model.Customer;
import lv.wings.model.DeliveryType;
import lv.wings.model.Event;
import lv.wings.model.EventCategory;
import lv.wings.model.EventPicture;
import lv.wings.model.PaymentType;
import lv.wings.model.ProductCategory;
import lv.wings.model.Purchase;
import lv.wings.model.security.MyAuthority;
import lv.wings.model.security.MyUser;
import lv.wings.repo.EventRepository;
import lv.wings.repo.ICustomerRepo;
import lv.wings.repo.IDeliveryTypeRepo;
import lv.wings.repo.IEventCategory;
import lv.wings.repo.IEventPictureRepo;
import lv.wings.repo.IPaymentTypeRepo;
import lv.wings.repo.IProductCategoryRepo;
import lv.wings.repo.IProductPictureRepo;
import lv.wings.repo.IProductRepo;
import lv.wings.repo.IPurchaseElementRepo;
import lv.wings.repo.IPurchaseRepo;
import lv.wings.repo.security.IMyAuthorityRepo;
import lv.wings.repo.security.IMyUserRepo;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableCaching
@Slf4j
public class SparniProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SparniProjectApplication.class, args);
		log.info("Server is running...");
	}

	// @Bean
	public CommandLineRunner sparniDB(
			ICustomerRepo customerRepo,
			IPurchaseRepo purchaseRepo,
			IDeliveryTypeRepo deliveryTypeRepo,
			IPaymentTypeRepo paymentTypeRepo,
			EventRepository eventRepo,
			IEventPictureRepo eventPictureRepo,
			IEventCategory eventCategoryRepo,
			IProductCategoryRepo productCategoryRepo,
			IPurchaseElementRepo purchaseElementRepo,
			IProductRepo productRepo,
			IProductPictureRepo productPictureRepo, IMyAuthorityRepo authRepo,
			IMyUserRepo userRepo) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				log.info("CommandLineRunner is initiated...");
				// DeliveryType deliveryType1 = new DeliveryType("Piegades Veids1",
				// "Apraksts1");
				// DeliveryType deliveryType2 = new DeliveryType("Piegades Veids2",
				// "Apraksts2");
				// deliveryTypeRepo.save(deliveryType1);
				// deliveryTypeRepo.save(deliveryType2);

				// PaymentType paymentType1 = new PaymentType("Samaksas Veids 1", "Piezimes1");
				// PaymentType paymentType2 = new PaymentType("Samaksas Veids 2", "Piezimes2");
				// paymentTypeRepo.save(paymentType1);
				// paymentTypeRepo.save(paymentType2);

				// Faker faker = new Faker();
				// for (int i = 0; i < 50; i++) {
				// String dfName = faker.name().firstName();
				// String dfSurname = faker.name().lastName();
				// String dfEmail = faker.expression("#{regexify '[A-Za-z0-9]{6,10}'}") +
				// "@gmail.com";
				// String dfAdress = faker.address().cityName() + ", " +
				// faker.address().buildingNumber();

				// Customer dfCustomer = new Customer(dfName, dfSurname, dfEmail, dfAdress);

				// customerRepo.save(dfCustomer);

				// String d = faker.date().future(300, TimeUnit.HOURS, "YYYY-MM-dd hh:mm");
				// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd
				// HH:mm");
				// LocalDateTime dateTime = LocalDateTime.parse(d, formatter);

				// Purchase newPurchase;
				// if (Integer.parseInt(faker.expression("#{numerify '#'}")) > 5) {
				// newPurchase = new Purchase(deliveryType1, paymentType2, dfCustomer, dateTime,
				// "Nav detalas");
				// } else {
				// newPurchase = new Purchase(deliveryType2, paymentType1, dfCustomer, dateTime,
				// "Lielas detalas");
				// }
				// purchaseRepo.save(newPurchase);
				// }

				// Customer customer1 = new Customer("Markuss", "Blumbergs",
				// "random1@gmail.com", "Talsi, Street 1");
				// Customer customer2 = new Customer("Lauris", "Kairo", "random2@gmail.com",
				// "Liepaja, Street 4");
				// customerRepo.save(customer1);
				// customerRepo.save(customer2);

				// Purchase purchase1 = new Purchase(deliveryType1, paymentType1, customer1,
				// LocalDateTime.now(),
				// "Nav detalas");
				// Purchase purchase2 = new Purchase(deliveryType2, paymentType2, customer1,
				// LocalDateTime.now(),
				// "Nav detalas");
				// purchaseRepo.save(purchase1);
				// purchaseRepo.save(purchase2);

				EventCategory eventCategory1 = new EventCategory("Sporta spēles");
				EventCategory eventCategory2 = new EventCategory("Vasaras svētki");
				EventCategory eventCategory3 = new EventCategory("Līgo");

				eventCategoryRepo.save(eventCategory1);
				eventCategoryRepo.save(eventCategory2);
				eventCategoryRepo.save(eventCategory3);

				MyAuthority a2 = new MyAuthority("USER");
				authRepo.save(a2);
				PasswordEncoder encoder = new BCryptPasswordEncoder();
				MyUser u1 = new MyUser("annija.user", encoder.encode("123"), a2);
				userRepo.save(u1);

				for (int i = 1; i <= 10; i++) {
					Event e = Event.builder()
							.startDate(LocalDate.now())
							.endDate(LocalDate.now().plusDays(1)) // 24hrs
							.title("Pasakums " + i)
							.description("Pasākums visiem cilvēkiem!")
							.location("Liepāja")
							.category(eventCategory1)
							.keyWords("example")
							.build();
					e.setCreatedBy(u1);
					eventRepo.save(e);
					if (i <= 3) {
						EventPicture bilde = new EventPicture("bilde" + i + ".jpg", "bilde1", "lababilde", e);
						bilde.setCreatedBy(u1);
						eventPictureRepo.save(bilde);
					}
				}

				// KATEGORIJAS
				ProductCategory kategorija1 = new ProductCategory("Adītas lelles", "Adītas lelles");
				ProductCategory kategorija2 = new ProductCategory("Aksesuāri", "Aksesuāri");
				ProductCategory kategorija3 = new ProductCategory("Cepure", "Galvas segas");
				ProductCategory kategorija4 = new ProductCategory("Cimdi", "Adīti cimdi");
				ProductCategory kategorija5 = new ProductCategory("Džemperi", "Adīti džemperi");
				ProductCategory kategorija6 = new ProductCategory("Šalles", "Kakla sega");
				ProductCategory kategorija7 = new ProductCategory("Zeķes", "Adītas zeķes");
				productCategoryRepo.save(kategorija1);
				productCategoryRepo.save(kategorija2);
				productCategoryRepo.save(kategorija3);
				productCategoryRepo.save(kategorija4);
				productCategoryRepo.save(kategorija5);
				productCategoryRepo.save(kategorija6);
				productCategoryRepo.save(kategorija7);

				// PRECES
				// Product product1 = new Product("Adīti ziemas dūraiņi A", "cimdi1", 13.99f, 6,
				// kategorija4);
				// Product product2 = new Product("Adīti ziemas dūraiņi B", "cimdi2", 9.99f, 6,
				// kategorija4);
				// Product product3 = new Product("Adīti ziemas dūraiņi C", "cimdi3", 14.99f, 6,
				// kategorija4);
				// Product product4 = new Product("Zeķes pusaudžiem A", "zekes1", 8.99f, 4,
				// kategorija7);
				// Product product5 = new Product("Zeķes pusaudžiem B", "zekes2", 8.88f, 4,
				// kategorija7);
				// Product product6 = new Product("Zeķes pusaudžiem C", "zekes3", 12.99f, 4,
				// kategorija7);
				// Product product7 = new Product("Džemperis A", "dzemperi1", 25.99f, 4,
				// kategorija5);
				// Product product8 = new Product("Džemperis B", "dzemperi2", 31.99f, 4,
				// kategorija5);
				// Product product9 = new Product("Džemperis C", "dzemperi3", 19.99f, 4,
				// kategorija5);
				// Product product10 = new Product("Cepure A", "cepures1", 8.99f, 4,
				// kategorija3);
				// Product product11 = new Product("Cepure B", "cepures2", 7.99f, 4,
				// kategorija3);
				// Product product12 = new Product("Cepure C", "cepures3", 12.99f, 4,
				// kategorija3);
				// Product product13 = new Product("Šalle A", "salle1", 8.99f, 4,
				// kategorija6);
				// Product product14 = new Product("Šalle B", "salle2", 7.99f, 4,
				// kategorija6);
				// Product product15 = new Product("Šalle C", "salle3", 12.99f, 4,
				// kategorija6);
				// Product product16 = new Product("Lelle A", "manta1", 8.99f, 4,
				// kategorija1);
				// Product product17 = new Product("Lelle B", "manta2", 7.99f, 4,
				// kategorija1);
				// Product product18 = new Product("Lelle C", "manta3", 12.99f, 4,
				// kategorija1);
				// Product product19 = new Product("Aksesuārs A", "aksesuars1", 8.99f, 4,
				// kategorija2);
				// Product product20 = new Product("Aksesuārs B", "aksesuars2", 7.99f, 4,
				// kategorija2);
				// Product product21 = new Product("Aksesuārs C", "aksesuars3", 12.99f, 4,
				// kategorija2);

				// productRepo.save(product1);
				// productRepo.save(product2);
				// productRepo.save(product3);
				// productRepo.save(product4);
				// productRepo.save(product5);
				// productRepo.save(product6);
				// productRepo.save(product7);
				// productRepo.save(product8);
				// productRepo.save(product9);
				// productRepo.save(product10);
				// productRepo.save(product11);
				// productRepo.save(product12);
				// productRepo.save(product13);
				// productRepo.save(product14);
				// productRepo.save(product15);
				// productRepo.save(product16);
				// productRepo.save(product17);
				// productRepo.save(product18);
				// productRepo.save(product19);
				// productRepo.save(product20);
				// productRepo.save(product21);

				// PIRKUMA ELEMENTS
				// PurchaseElement purchaseElement1 = new PurchaseElement(purchase1, product1,
				// 2);
				// PurchaseElement purchaseElement2 = new PurchaseElement(purchase2, product2,
				// 1);
				// PurchaseElement purchaseElement3 = new PurchaseElement(purchase2, product1,
				// 1);
				// purchaseElementRepo.save(purchaseElement1);
				// purchaseElementRepo.save(purchaseElement2);
				// purchaseElementRepo.save(purchaseElement3);

				// BILDES
				// public Preces_bilde(String bilde, String apraksts, Prece product)
				// ProductPicture bilde11 = new ProductPicture("cimdi1.jpg", "Cimdi1",
				// product1);
				// ProductPicture bilde22 = new ProductPicture("cimdi2.jpg", "Cimdi2",
				// product2);
				// ProductPicture bilde33 = new ProductPicture("cimdi3.jpg", "Cimdi3",
				// product3);

				// ProductPicture bilde44 = new ProductPicture("zekes1.jpg", "Zeķes1",
				// product4);
				// ProductPicture bilde55 = new ProductPicture("zekes2.jpg", "Zeķes2",
				// product5);
				// ProductPicture bilde66 = new ProductPicture("zekes3.jpg", "Zeķes3",
				// product6);

				// ProductPicture bilde77 = new ProductPicture("dzemperi1.jpg", "Džemperis1",
				// product7);
				// ProductPicture bilde88 = new ProductPicture("dzemperi2.jpg", "Džemperis2",
				// product8);
				// ProductPicture bilde99 = new ProductPicture("dzemperi3.jpg", "Džemperis3",
				// product9);

				// ProductPicture bilde100 = new ProductPicture("cepures1.jpg", "Cepure1",
				// product10);
				// ProductPicture bilde111 = new ProductPicture("cepures2.jpg", "Cepure2",
				// product11);
				// ProductPicture bilde122 = new ProductPicture("cepures3.jpg", "Cepure3",
				// product12);

				// ProductPicture bilde133 = new ProductPicture("salle1.jpg", "Šalle1",
				// product13);
				// ProductPicture bilde144 = new ProductPicture("salle2.jpg", "Šalle2",
				// product14);
				// ProductPicture bilde155 = new ProductPicture("salle3.jpg", "Šalle3",
				// product15);

				// ProductPicture bilde166 = new ProductPicture("manta1.jpg", "Manta1",
				// product16);
				// ProductPicture bilde177 = new ProductPicture("manta2.jpg", "Manta2",
				// product17);
				// ProductPicture bilde188 = new ProductPicture("manta3.jpg", "Manta3",
				// product18);

				// ProductPicture bilde190 = new ProductPicture("aksesuars1.jpg", "Aksesuars 1",
				// product19);
				// ProductPicture bilde200 = new ProductPicture("aksesuars2.jpg", "Aksesuars 2",
				// product20);
				// ProductPicture bilde210 = new ProductPicture("aksesuars3.jpg", "Aksesuars 3",
				// product21);
				// productPictureRepo.save(bilde11);
				// productPictureRepo.save(bilde22);
				// productPictureRepo.save(bilde33);

				// USER & AUTHORITY
				// productPictureRepo.save(bilde44);
				// productPictureRepo.save(bilde55);
				// productPictureRepo.save(bilde66);
				// productPictureRepo.save(bilde77);
				// productPictureRepo.save(bilde88);
				// productPictureRepo.save(bilde99);
				// productPictureRepo.save(bilde100);
				// productPictureRepo.save(bilde111);
				// productPictureRepo.save(bilde122);
				// productPictureRepo.save(bilde133);
				// productPictureRepo.save(bilde144);
				// productPictureRepo.save(bilde155);
				// productPictureRepo.save(bilde166);
				// productPictureRepo.save(bilde177);
				// productPictureRepo.save(bilde188);

				// productPictureRepo.save(bilde190);
				// productPictureRepo.save(bilde200);
				// productPictureRepo.save(bilde210);

				// USER & AUTHORITY
				// MyAuthority a1 = new MyAuthority("ADMIN");
				// authRepo.save(a1);

				// MyAuthority a2 = new MyAuthority("USER"); //
				// authRepo.save(a2);

				// PasswordEncoder encoder = new BCryptPasswordEncoder();

				// MyUser u1 = new MyUser("annija.user", encoder.encode("123"), a2);
				// userRepo.save(u1);

				// MyUser u2 = new MyUser("annija.admin", encoder.encode("456"), a1);
				// userRepo.save(u2);

				// MyUser u3 = new MyUser("admin", encoder.encode("123"), a1);
				// userRepo.save(u3);

				//// create upload picture directories
				/// (Wrong but fast version)
				// Path path1 = Paths.get("uploads/images/events");
				// Path path2 = Paths.get("uploads/images/products");

				// if (!Files.exists(path1)) {
				// Files.createDirectories(path1);
				// }

				// if (!Files.exists(path2)) {
				// Files.createDirectories(path2);
				// }
				log.info("Data has been successfully seeded.");
			}
		};
	}
}
