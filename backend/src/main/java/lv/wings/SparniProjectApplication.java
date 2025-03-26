package lv.wings;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import lv.wings.enums.LocaleCode;
import lv.wings.model.entity.Color;
import lv.wings.model.entity.Event;
import lv.wings.model.entity.EventCategory;
import lv.wings.model.entity.EventImage;
import lv.wings.model.entity.Material;
import lv.wings.model.entity.Product;
import lv.wings.model.entity.ProductCategory;
import lv.wings.model.entity.ProductImage;
import lv.wings.model.entity.ProductMaterial;
import lv.wings.model.security.MyAuthority;
import lv.wings.model.security.MyUser;
import lv.wings.model.translation.ColorTranslation;
import lv.wings.model.translation.EventCategoryTranslation;
import lv.wings.model.translation.EventImageTranslation;
import lv.wings.model.translation.EventTranslation;
import lv.wings.model.translation.MaterialTranslation;
import lv.wings.model.translation.ProductCategoryTranslation;
import lv.wings.model.translation.ProductImageTranslation;
import lv.wings.model.translation.ProductTranslation;
import lv.wings.repo.EventRepository;
import lv.wings.repo.MaterialRepository;
import lv.wings.repo.ColorRepository;
import lv.wings.repo.CustomerRepository;
import lv.wings.repo.DeliveryTypeRepository;
import lv.wings.repo.EventCategoryRepository;
import lv.wings.repo.EventImageRepository;
import lv.wings.repo.PaymentTypeRepository;
import lv.wings.repo.ProductCategoryRepository;
import lv.wings.repo.ProductImageRepository;
import lv.wings.repo.ProductMaterialRepository;
import lv.wings.repo.ProductRepository;
import lv.wings.repo.PurchaseElementRepository;
import lv.wings.repo.PurchaseRepository;
import lv.wings.repo.security.IMyAuthorityRepo;
import lv.wings.repo.security.IMyUserRepo;

import net.datafaker.Faker;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableCaching
@Slf4j
public class SparniProjectApplication {

	private final ProductImageRepository productImageRepository;

	SparniProjectApplication(ProductImageRepository productImageRepository) {
		this.productImageRepository = productImageRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(SparniProjectApplication.class, args);
		log.info("Server is running...");
	}

	// @Bean
	// @Profile("seed")
	// @Transactional
	public CommandLineRunner sparniDB(
			ColorRepository colorRepo,
			ProductMaterialRepository productMaterialRepo,
			MaterialRepository materialRepo,
			CustomerRepository customerRepo,
			PurchaseRepository purchaseRepo,
			DeliveryTypeRepository deliveryTypeRepo,
			PaymentTypeRepository paymentTypeRepo,
			EventRepository eventRepo,
			EventImageRepository eventImageRepo,
			EventCategoryRepository eventCategoryRepo,
			ProductCategoryRepository productCategoryRepo,
			PurchaseElementRepository purchaseElementRepo,
			ProductRepository productRepo,
			ProductImageRepository productImageRepo,
			IMyAuthorityRepo authRepo,
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

				Faker faker = new Faker();
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

				MyAuthority a2 = new MyAuthority("USER");
				authRepo.save(a2);
				PasswordEncoder encoder = new BCryptPasswordEncoder();
				MyUser u1 = new MyUser("annija.user", encoder.encode("123"), a2);
				userRepo.save(u1);

				EventCategory eventCategory1 = new EventCategory();
				EventCategoryTranslation eventCategory1Lv = EventCategoryTranslation.builder()
						.title("Veikala blogs")
						.locale(LocaleCode.LV)
						.category(eventCategory1)
						.build();
				EventCategoryTranslation eventCategory1En = EventCategoryTranslation.builder()
						.title("Shop blog")
						.locale(LocaleCode.EN)
						.category(eventCategory1)
						.build();
				eventCategory1.setTranslations(List.of(eventCategory1Lv, eventCategory1En));
				eventCategory1.setCreatedBy(u1);
				eventCategoryRepo.save(eventCategory1);

				for (int i = 1; i <= 10; i++) {

					EventTranslation lv = EventTranslation.builder()
							.title("lv_title_" + i)
							.description("lv_description_" + i)
							.locale(LocaleCode.LV)
							.build();

					EventTranslation en = EventTranslation.builder()
							.title("en_title_" + i)
							.description("en_description_" + i)
							.locale(LocaleCode.EN)
							.build();
					if (i <= 5) {
						lv.setLocation("Rīga");
						en.setLocation("Riga");
					}
					Event e = Event.builder()
							.startDate(LocalDate.now())
							.endDate(LocalDate.now().plusDays(1)) // 24hrs
							.category(eventCategory1)
							.build();

					lv.setEntity(e);
					en.setEntity(e);
					e.setTranslations(List.of(lv, en));
					e.setCreatedBy(u1);
					eventRepo.save(e);
					if (i <= 3) {
						EventImage bilde = EventImage.builder().src("http://localhost:8080/images/bilde1.jpg").event(e).build();
						EventImageTranslation altEn = EventImageTranslation.builder().alt("Good Image").image(bilde).locale(LocaleCode.EN).build();
						EventImageTranslation altLv = EventImageTranslation.builder().alt("Laba bilde").image(bilde).locale(LocaleCode.LV).build();
						bilde.setTranslations(List.of(altEn, altLv));
						bilde.setCreatedBy(u1);
						eventImageRepo.save(bilde);
						if (i <= 2) {
							EventImage bilde2 = EventImage.builder().src("http://localhost:8080/images/bilde1.jpg").event(e).build();
							EventImageTranslation altEn2 =
									EventImageTranslation.builder().alt("Some other good Image").image(bilde2).locale(LocaleCode.EN).build();
							EventImageTranslation altLv2 =
									EventImageTranslation.builder().alt("Kada cita laba bilde").image(bilde2).locale(LocaleCode.LV).build();
							bilde2.setTranslations(List.of(altEn2, altLv2));
							bilde2.setCreatedBy(u1);
							eventImageRepo.save(bilde2);
						}
					}
				}

				// =============== EVERYTHING PRODUCT RELATED =========================
				List<Color> colors = new ArrayList<>();

				String[][] colorNames = {
						{"Red", "Sarkans"},
						{"Blue", "Zils"},
						{"Green", "Zaļš"},
						{"Yellow", "Dzeltens"},
						{"Black", "Melns"},
						{"White", "Balts"},
						{"Orange", "Oranžs"},
						{"Purple", "Violets"},
						{"Pink", "Rozā"},
						{"Gray", "Pelēks"}
				};

				for (String[] pair : colorNames) {
					Color color = new Color();

					ColorTranslation en = ColorTranslation.builder()
							.color(color)
							.locale(LocaleCode.EN)
							.name(pair[0])
							.build();

					ColorTranslation lv = ColorTranslation.builder()
							.color(color)
							.locale(LocaleCode.LV)
							.name(pair[1])
							.build();

					color.setTranslations(List.of(en, lv));
					color.setCreatedBy(u1);

					colors.add(color);
				}

				colorRepo.saveAll(colors);

				// Materials
				String[][] materialNames = {
						{"Wool", "Vilna"},
						{"Cotton", "Kokvilna"},
						{"Linen", "Lins"},
						{"Silk", "Zīds"}
				};
				List<Material> materials = new ArrayList<>();

				for (String[] mTranslations : materialNames) {
					Material m = new Material();
					MaterialTranslation m_en = MaterialTranslation.builder().material(m).locale(LocaleCode.EN).name(mTranslations[0]).build();
					MaterialTranslation m_lv = MaterialTranslation.builder().material(m).locale(LocaleCode.LV).name(mTranslations[1]).build();
					m.setCreatedBy(u1);
					m.setTranslations(Arrays.asList(m_en, m_lv));
					materials.add(m);
				}
				materialRepo.saveAll(materials);


				// KATEGORIJAS
				ProductCategory categoryOne = new ProductCategory();
				categoryOne.setCreatedBy(u1);
				ProductCategoryTranslation categoryOneLv = ProductCategoryTranslation
						.builder()
						.title("Cepures")
						.description(
								"Piedāvājam dažādu stilu adītas cepures. Visām cepurēm ir standarta izmērs pieaugušajiem, tās staipās. Bumbuli iespējams akurāti nogriezt.")
						.category(categoryOne)
						.locale(LocaleCode.LV)
						.build();
				ProductCategoryTranslation categoryOneEn = ProductCategoryTranslation
						.builder()
						.title("Hats")
						.description(
								"We offer a variety of knitted hats in different styles. All hats come in a standard adult size and are stretchable. The pom-pom can be carefully removed if desired.")
						.category(categoryOne)
						.locale(LocaleCode.EN)
						.build();

				categoryOne.setTranslations(List.of(categoryOneLv, categoryOneEn));
				productCategoryRepo.save(categoryOne);

				ProductCategory categoryTwo = new ProductCategory();
				categoryTwo.setCreatedBy(u1);
				ProductCategoryTranslation categoryTwoLv = ProductCategoryTranslation.builder().title("Cimdi").description(
						"""
								Ar rokām adīti latviešu rakstainie dūraiņi, kas glabā katra meistara sirds siltumu. Katrs cimdu pāris ir unikāls roku darbs, tāpēc iespējamas nelielas krāsu, dizaina un izmēru atšķirības no norādītā.
								""")
						.category(categoryTwo)
						.locale(LocaleCode.LV)
						.build();
				ProductCategoryTranslation categoryTwoEn = ProductCategoryTranslation.builder().title("Gloves").description(
						"""
								Hand-knitted Latvian patterned mittens that carry the warmth of each artisan's heart. Each pair of mittens is a unique handmade creation, so slight variations in color, design, and size from the specified ones may occur.
								""")
						.category(categoryTwo)
						.locale(LocaleCode.EN)
						.build();

				categoryTwo.setTranslations(List.of(categoryTwoLv, categoryTwoEn));
				productCategoryRepo.save(categoryTwo);

				ProductCategory categoryThree = new ProductCategory();
				categoryThree.setCreatedBy(u1);
				ProductCategoryTranslation categoryThreeLv = ProductCategoryTranslation.builder()
						.title("Zeķes")
						.description(
								"""
										Ar rokām adītas vilnas zeķes dažādos izmēros. Lai izvēlētos izmēru, nospiediet uz vēlamā produkta. Zeķēm, kuras pieejamas tikai vienā izmērā,
										izmērs norādīts nosaukumā. Ja neatrodat vēlamo skaitu vai izmēru, sazinieties ar mums shop@sparni.lv un mēs centīsimies palīdzēt.
										""")
						.category(categoryThree)
						.locale(LocaleCode.LV)
						.build();
				ProductCategoryTranslation categoryThreeEn = ProductCategoryTranslation.builder()
						.title("Socks")
						.description(
								"""
										Hand-knitted wool socks in various sizes. To choose a size, click on the desired product. For socks available in only one size, the size is
										indicated in the title. If you cannot find the desired quantity or size, contact us at shop@sparni.lv, and we will do our best to assist you.
										""")
						.category(categoryThree)
						.locale(LocaleCode.EN)
						.build();

				categoryThree.setTranslations(List.of(categoryThreeLv, categoryThreeEn));
				productCategoryRepo.save(categoryThree);


				// PRECES
				// CATEGORY - HATS - 4
				Product hat1 = Product.builder().price(10.5).amount(3).category(categoryOne).build();
				hat1.setCreatedBy(u1);
				ProductTranslation hat1Lv = ProductTranslation.builder()
						.title("Adītas cepures")
						.description("Stilīgas adītas cepures, izgatavotas ar mīlestību.")
						.locale(LocaleCode.LV)
						.product(hat1)
						.build();
				ProductTranslation hat1En = ProductTranslation.builder()
						.title("Knitted Hats")
						.description("Stylish knitted hats crafted with love.")
						.locale(LocaleCode.EN)
						.product(hat1)
						.build();
				hat1.setTranslations(List.of(hat1Lv, hat1En));
				hat1.setColors(colors.subList(0, 3));
				productRepo.save(hat1);

				ProductMaterial pm1 = ProductMaterial.builder().product(hat1).material(materials.get(0)).percentage(50).build();
				pm1.setCreatedBy(u1);
				ProductMaterial pm2 = ProductMaterial.builder().product(hat1).material(materials.get(1)).percentage(50).build();
				pm2.setCreatedBy(u1);
				productMaterialRepo.saveAll(Arrays.asList(pm1, pm2));

				Product hat2 = Product.builder().price(12.0).amount(5).category(categoryOne).build();
				hat2.setCreatedBy(u1);
				ProductTranslation hat2Lv = ProductTranslation.builder()
						.title("Modernās cepures")
						.description("Modernās cepures, kas piešķir individuālu stilu.")
						.locale(LocaleCode.LV)
						.product(hat2)
						.build();
				ProductTranslation hat2En = ProductTranslation.builder()
						.title("Modern Hats")
						.description("Modern hats that offer a unique style.")
						.locale(LocaleCode.EN)
						.product(hat2)
						.build();
				hat2.setTranslations(List.of(hat2Lv, hat2En));
				hat2.setColors(colors.subList(3, 5));
				productRepo.save(hat2);

				ProductMaterial pm3 = ProductMaterial.builder().product(hat2).material(materials.get(2)).percentage(70).build();
				pm3.setCreatedBy(u1);
				ProductMaterial pm4 = ProductMaterial.builder().product(hat2).material(materials.get(3)).percentage(30).build();
				pm4.setCreatedBy(u1);
				productMaterialRepo.saveAll(Arrays.asList(pm3, pm4));

				Product hat3 = Product.builder().price(11.0).amount(0).category(categoryOne).build();
				hat3.setCreatedBy(u1);
				ProductTranslation hat3Lv = ProductTranslation.builder()
						.title("Klasiskas cepures")
						.description("Klasiskas cepures, kas papildina jebkuru tērpu.")
						.locale(LocaleCode.LV)
						.product(hat3)
						.build();
				ProductTranslation hat3En = ProductTranslation.builder()
						.title("Classic Hats")
						.description("Classic hats that complement any outfit.")
						.locale(LocaleCode.EN)
						.product(hat3)
						.build();

				hat3.setTranslations(List.of(hat3Lv, hat3En));
				hat3.getColors().add(colors.get(6));
				productRepo.save(hat3);

				ProductMaterial pm5 = ProductMaterial.builder().product(hat2).material(materials.get(0)).percentage(100).build();
				pm5.setCreatedBy(u1);
				productMaterialRepo.save(pm5);


				Product hat4 = Product.builder().price(13.0).amount(4).category(categoryOne).build();
				hat4.setCreatedBy(u1);
				ProductTranslation hat4Lv = ProductTranslation.builder()
						.title("Eleganti cepures")
						.description("Eleganti cepures, ideāli piemērotas gan ikdienai, gan svētkiem.")
						.locale(LocaleCode.LV)
						.product(hat4)
						.build();
				ProductTranslation hat4En = ProductTranslation.builder()
						.title("Elegant Hats")
						.description("Elegant hats perfect for everyday wear and special occasions.")
						.locale(LocaleCode.EN)
						.product(hat4)
						.build();


				hat4.setTranslations(List.of(hat4Lv, hat4En));
				productRepo.save(hat4);


				// (Category Two) – 7 PRODUCTS
				Product glove1 = Product.builder().price(8.5).amount(6).category(categoryTwo).build();
				glove1.setCreatedBy(u1);
				ProductTranslation glove1Lv = ProductTranslation.builder()
						.title("Adītas cimdi")
						.description("Roku darbs, kas sniedz siltumu aukstās dienās.")
						.locale(LocaleCode.LV)
						.product(glove1)
						.build();
				ProductTranslation glove1En = ProductTranslation.builder()
						.title("Knitted Gloves")
						.description("Handcrafted gloves providing warmth on cold days.")
						.locale(LocaleCode.EN)
						.product(glove1)
						.build();

				glove1.setTranslations(List.of(glove1Lv, glove1En));
				productRepo.save(glove1);

				Product glove2 = Product.builder().price(9.0).amount(4).category(categoryTwo).build();
				glove2.setCreatedBy(u1);
				ProductTranslation glove2Lv = ProductTranslation.builder()
						.title("Stilīgi cimdi")
						.description("Stilīgi cimdi, kas papildina jebkuru tērpu.")
						.locale(LocaleCode.LV)
						.product(glove2)
						.build();
				ProductTranslation glove2En = ProductTranslation.builder()
						.title("Stylish Gloves")
						.description("Stylish gloves that complement any outfit.")
						.locale(LocaleCode.EN)
						.product(glove2)
						.build();
				glove2.setTranslations(List.of(glove2Lv, glove2En));
				productRepo.save(glove2);

				Product glove3 = Product.builder().price(8.0).amount(5).category(categoryTwo).build();
				glove3.setCreatedBy(u1);
				ProductTranslation glove3Lv = ProductTranslation.builder()
						.title("Klasiski cimdi")
						.description("Klasiski cimdi, izgatavoti ar rūpību.")
						.locale(LocaleCode.LV)
						.product(glove3)
						.build();
				ProductTranslation glove3En = ProductTranslation.builder()
						.title("Classic Gloves")
						.description("Classic gloves crafted with care.")
						.locale(LocaleCode.EN)
						.product(glove3)
						.build();
				glove3.setTranslations(List.of(glove3Lv, glove3En));
				productRepo.save(glove3);

				Product glove4 = Product.builder().price(10.0).amount(3).category(categoryTwo).build();
				glove4.setCreatedBy(u1);
				ProductTranslation glove4Lv = ProductTranslation.builder()
						.title("Mūsdienīgi cimdi")
						.description("Mūsdienīgi cimdi ar inovatīviem dizainiem.")
						.locale(LocaleCode.LV)
						.product(glove4)
						.build();
				ProductTranslation glove4En = ProductTranslation.builder()
						.title("Modern Gloves")
						.description("Modern gloves with innovative designs.")
						.locale(LocaleCode.EN)
						.product(glove4)
						.build();
				glove4.setTranslations(List.of(glove4Lv, glove4En));
				productRepo.save(glove4);



				Product glove5 = Product.builder().price(7.5).amount(7).category(categoryTwo).build();
				glove5.setCreatedBy(u1);
				ProductTranslation glove5Lv = ProductTranslation.builder()
						.title("Radoši cimdi")
						.description("Radoši cimdi, kas sniedz unikālu stilu.")
						.locale(LocaleCode.LV)
						.product(glove5)
						.build();
				ProductTranslation glove5En = ProductTranslation.builder()
						.title("Creative Gloves")
						.description("Creative gloves that offer a unique style.")
						.locale(LocaleCode.EN)
						.product(glove5)
						.build();
				glove5.setTranslations(List.of(glove5Lv, glove5En));
				productRepo.save(glove5);

				Product glove6 = Product.builder().price(8.75).amount(5).category(categoryTwo).build();
				glove6.setCreatedBy(u1);
				ProductTranslation glove6Lv = ProductTranslation.builder()
						.title("Praktiski cimdi")
						.description("Praktiski cimdi ikdienas lietošanai.")
						.locale(LocaleCode.LV)
						.product(glove6)
						.build();
				ProductTranslation glove6En = ProductTranslation.builder()
						.title("Practical Gloves")
						.description("Practical gloves for everyday use.")
						.locale(LocaleCode.EN)
						.product(glove6)
						.build();
				glove6.setTranslations(List.of(glove6Lv, glove6En));
				productRepo.save(glove6);

				Product glove7 = Product.builder().price(9.5).amount(2).category(categoryTwo).build();
				glove7.setCreatedBy(u1);
				ProductTranslation glove7Lv = ProductTranslation.builder()
						.title("Eleganti cimdi")
						.description("Eleganti cimdi, kas piešķir tērpam izsmalcinātību.")
						.locale(LocaleCode.LV)
						.product(glove7)
						.build();
				ProductTranslation glove7En = ProductTranslation.builder()
						.title("Elegant Gloves")
						.description("Elegant gloves that add sophistication to your outfit.")
						.locale(LocaleCode.EN)
						.product(glove7)
						.build();
				glove7.setTranslations(List.of(glove7Lv, glove7En));
				productRepo.save(glove7);

				// BILDES
				ProductImage bilde1 = ProductImage.builder().product(glove7).src("http://localhost:8080/images/products/cimdi1.jpg").build();
				bilde1.setCreatedBy(u1);
				ProductImageTranslation bilde1_lv = ProductImageTranslation.builder().alt("Labi cimdi 1").picture(bilde1).locale(LocaleCode.LV).build();
				ProductImageTranslation bilde1_en = ProductImageTranslation.builder().alt("Good gloves 1").picture(bilde1).locale(LocaleCode.EN).build();
				bilde1.setTranslations(List.of(bilde1_lv, bilde1_en));
				productImageRepo.save(bilde1);

				ProductImage bilde2 = ProductImage.builder().product(glove7).src("http://localhost:8080/images/products/cimdi2.jpg").build();
				bilde2.setCreatedBy(u1);
				ProductImageTranslation bilde2_lv = ProductImageTranslation.builder().alt("Labi cimdi 2").picture(bilde2).locale(LocaleCode.LV).build();
				ProductImageTranslation bilde2_en = ProductImageTranslation.builder().alt("Good gloves 2").picture(bilde2).locale(LocaleCode.EN).build();
				bilde2.setTranslations(List.of(bilde2_lv, bilde2_en));
				productImageRepo.save(bilde2);

				ProductImage bilde3 = ProductImage.builder().product(glove7).src("http://localhost:8080/images/products/cimdi3.jpg").build();
				bilde3.setCreatedBy(u1);
				ProductImageTranslation bilde3_lv = ProductImageTranslation.builder().alt("Labi cimdi 3").picture(bilde3).locale(LocaleCode.LV).build();
				ProductImageTranslation bilde3_en = ProductImageTranslation.builder().alt("Good gloves 3").picture(bilde3).locale(LocaleCode.EN).build();
				bilde3.setTranslations(List.of(bilde3_lv, bilde3_en));
				productImageRepo.save(bilde3);

				ProductImage bilde4 = ProductImage.builder().product(glove6).src("http://localhost:8080/images/products/cimdi1.jpg").build();
				bilde4.setCreatedBy(u1);
				ProductImageTranslation bilde4_lv = ProductImageTranslation.builder().alt("Labi cimdi 3").picture(bilde4).locale(LocaleCode.LV).build();
				ProductImageTranslation bilde4_en = ProductImageTranslation.builder().alt("Good gloves 3").picture(bilde4).locale(LocaleCode.EN).build();
				bilde4.setTranslations(List.of(bilde4_lv, bilde4_en));
				productImageRepo.save(bilde4);

				ProductImage bilde5 = ProductImage.builder().product(glove6).src("http://localhost:8080/images/products/cimdi2.jpg").build();
				bilde5.setCreatedBy(u1);
				ProductImageTranslation bilde5_lv = ProductImageTranslation.builder().alt("Labi cimdi 3").picture(bilde5).locale(LocaleCode.LV).build();
				ProductImageTranslation bilde5_en = ProductImageTranslation.builder().alt("Good gloves 3").picture(bilde5).locale(LocaleCode.EN).build();
				bilde5.setTranslations(List.of(bilde5_lv, bilde5_en));
				productImageRepo.save(bilde5);

				ProductImage bilde6 = ProductImage.builder().product(glove5).src("http://localhost:8080/images/products/cimdi3.jpg").build();
				bilde6.setCreatedBy(u1);
				ProductImageTranslation bilde6_lv = ProductImageTranslation.builder().alt("Labi cimdi 3").picture(bilde6).locale(LocaleCode.LV).build();
				ProductImageTranslation bilde6_en = ProductImageTranslation.builder().alt("Good gloves 3").picture(bilde6).locale(LocaleCode.EN).build();
				bilde6.setTranslations(List.of(bilde6_lv, bilde6_en));
				productImageRepo.save(bilde6);


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

				// USER & AUTHORITY
				// productImageRepo.save(bilde44);
				// productImageRepo.save(bilde55);
				// productImageRepo.save(bilde66);
				// productImageRepo.save(bilde77);
				// productImageRepo.save(bilde88);
				// productImageRepo.save(bilde99);
				// productImageRepo.save(bilde100);
				// productImageRepo.save(bilde111);
				// productImageRepo.save(bilde122);
				// productImageRepo.save(bilde133);
				// productImageRepo.save(bilde144);
				// productImageRepo.save(bilde155);
				// productImageRepo.save(bilde166);
				// productImageRepo.save(bilde177);
				// productImageRepo.save(bilde188);

				// productImageRepo.save(bilde190);
				// productImageRepo.save(bilde200);
				// productImageRepo.save(bilde210);

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

				//// create upload Image directories
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
