package lv.wings;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import lv.wings.enums.Country;
import lv.wings.enums.DeliveryMethod;
import lv.wings.enums.LocaleCode;
import lv.wings.enums.PermissionName;
import lv.wings.model.entity.Color;
import lv.wings.model.entity.DeliveryPrice;
import lv.wings.model.entity.DeliveryType;
import lv.wings.model.entity.Event;
import lv.wings.model.entity.EventCategory;
import lv.wings.model.entity.EventImage;
import lv.wings.model.entity.GlobalParam;
import lv.wings.model.entity.Material;
import lv.wings.model.entity.Product;
import lv.wings.model.entity.ProductCategory;
import lv.wings.model.entity.ProductImage;
import lv.wings.model.entity.ProductMaterial;
import lv.wings.model.security.Permission;
import lv.wings.model.security.Role;
import lv.wings.model.security.User;
import lv.wings.model.translation.ColorTranslation;
import lv.wings.model.translation.DeliveryTypeTranslation;
import lv.wings.model.translation.EventCategoryTranslation;
import lv.wings.model.translation.EventImageTranslation;
import lv.wings.model.translation.EventTranslation;
import lv.wings.model.translation.MaterialTranslation;
import lv.wings.model.translation.ProductCategoryTranslation;
import lv.wings.model.translation.ProductImageTranslation;
import lv.wings.model.translation.ProductTranslation;
import lv.wings.repo.EventRepository;
import lv.wings.repo.GlobalParamsRepository;
import lv.wings.repo.MaterialRepository;
import lv.wings.repo.PermissionRepository;
import lv.wings.repo.ColorRepository;
import lv.wings.repo.CustomerRepository;
import lv.wings.repo.DeliveryTypeRepository;
import lv.wings.repo.EventCategoryRepository;
import lv.wings.repo.EventImageRepository;
import lv.wings.repo.ProductCategoryRepository;
import lv.wings.repo.ProductImageRepository;
import lv.wings.repo.ProductMaterialRepository;
import lv.wings.repo.ProductRepository;
import lv.wings.repo.RoleRepository;
import lv.wings.repo.UserRepository;
import net.datafaker.Faker;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "applicationAuditAware")
@EnableCaching
@Slf4j
public class SparniProjectApplication {

	private final ProductImageRepository productImageRepository;

	SparniProjectApplication(ProductImageRepository productImageRepository) {
		this.productImageRepository = productImageRepository;
	}

	@PostConstruct
	public void logEnv() {
		System.out.println("🚀 Environment variables:");
		System.out.println("DB_HOST = " + System.getenv("DB_HOST"));
		System.out.println("DB_PORT = " + System.getenv("DB_PORT"));
		System.out.println("DB_NAME = " + System.getenv("DB_NAME"));
		System.out.println("DB_USER = " + System.getenv("DB_USER"));
		System.out.println("DB_PASSWORD = " + System.getenv("DB_PASSWORD"));
		System.out.println("JWT_SECRET = " + System.getenv("JWT_SECRET"));
	}

	public static void main(String[] args) {
		try {
			SpringApplication.run(SparniProjectApplication.class, args);
			System.out.println("Server is up and running...");
		} catch (Exception e) {
			System.err.println("❌ Unable to start the server..");
			e.printStackTrace();
		}
	}

	// @Bean
	// @Order(1)
	// @Profile("seed")
	public CommandLineRunner sparniDB(
			ColorRepository colorRepo,
			ProductMaterialRepository productMaterialRepo,
			MaterialRepository materialRepo,
			CustomerRepository customerRepo,
			DeliveryTypeRepository deliveryTypeRepo,
			EventRepository eventRepo,
			EventImageRepository eventImageRepo,
			EventCategoryRepository eventCategoryRepo,
			PasswordEncoder passwordEncoder,
			ProductCategoryRepository productCategoryRepo,
			ProductRepository productRepo,
			ProductImageRepository productImageRepo,
			UserRepository userRepo,
			GlobalParamsRepository globalParamsRepo) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				log.info("CommandLineRunner is initiated..");

				User u1 = User.builder()
						.username("system")
						.email("system@internal.wings")
						.firstName("System")
						.lastName("System")
						.password(passwordEncoder.encode(("some_system_password")))
						.build();
				userRepo.save(u1);

				GlobalParam gbp = GlobalParam.builder().title("omniva_api_link")
						.value("https://www.omniva.lv/locations.json").build();
				gbp.setCreatedBy(u1);
				globalParamsRepo.save(gbp);

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
						EventImage bilde = EventImage.builder().src("http://localhost:8080/images/bilde1.jpg").event(e)
								.build();
						EventImageTranslation altEn = EventImageTranslation.builder().alt("Good Image").image(bilde)
								.locale(LocaleCode.EN).build();
						EventImageTranslation altLv = EventImageTranslation.builder().alt("Laba bilde").image(bilde)
								.locale(LocaleCode.LV).build();
						bilde.setTranslations(List.of(altEn, altLv));
						bilde.setCreatedBy(u1);
						eventImageRepo.save(bilde);
						if (i <= 2) {
							EventImage bilde2 = EventImage.builder().src("http://localhost:8080/images/bilde1.jpg")
									.event(e).build();
							EventImageTranslation altEn2 = EventImageTranslation.builder().alt("Some other good Image")
									.image(bilde2).locale(LocaleCode.EN).build();
							EventImageTranslation altLv2 = EventImageTranslation.builder().alt("Kada cita laba bilde")
									.image(bilde2).locale(LocaleCode.LV).build();
							bilde2.setTranslations(List.of(altEn2, altLv2));
							bilde2.setCreatedBy(u1);
							eventImageRepo.save(bilde2);
						}
					}
				}

				// =============== EVERYTHING PRODUCT RELATED =========================
				List<Color> colors = new ArrayList<>();

				String[][] colorNames = {
						{ "Red", "Sarkans" },
						{ "Blue", "Zils" },
						{ "Green", "Zaļš" },
						{ "Yellow", "Dzeltens" },
						{ "Black", "Melns" },
						{ "White", "Balts" },
						{ "Orange", "Oranžs" },
						{ "Purple", "Violets" },
						{ "Pink", "Rozā" },
						{ "Gray", "Pelēks" }
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
						{ "Wool", "Vilna" },
						{ "Cotton", "Kokvilna" },
						{ "Linen", "Lins" },
						{ "Silk", "Zīds" }
				};
				List<Material> materials = new ArrayList<>();

				for (String[] mTranslations : materialNames) {
					Material m = new Material();
					MaterialTranslation m_en = MaterialTranslation.builder().material(m).locale(LocaleCode.EN)
							.name(mTranslations[0]).build();
					MaterialTranslation m_lv = MaterialTranslation.builder().material(m).locale(LocaleCode.LV)
							.name(mTranslations[1]).build();
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
				ProductCategoryTranslation categoryTwoLv = ProductCategoryTranslation.builder().title("Cimdi")
						.description(
								"""
										Ar rokām adīti latviešu rakstainie dūraiņi, kas glabā katra meistara sirds siltumu. Katrs cimdu pāris ir unikāls roku darbs, tāpēc iespējamas nelielas krāsu, dizaina un izmēru atšķirības no norādītā.
										""")
						.category(categoryTwo)
						.locale(LocaleCode.LV)
						.build();
				ProductCategoryTranslation categoryTwoEn = ProductCategoryTranslation.builder().title("Gloves")
						.description(
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
				Product hat1 = Product.builder().price(BigDecimal.valueOf(10.5)).amount(3).category(categoryOne)
						.build();
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

				ProductMaterial pm1 = ProductMaterial.builder().product(hat1).material(materials.get(0)).percentage(50)
						.build();
				pm1.setCreatedBy(u1);
				ProductMaterial pm2 = ProductMaterial.builder().product(hat1).material(materials.get(1)).percentage(50)
						.build();
				pm2.setCreatedBy(u1);
				productMaterialRepo.saveAll(Arrays.asList(pm1, pm2));

				Product hat2 = Product.builder().price(BigDecimal.valueOf(12.0)).amount(5).category(categoryOne)
						.build();
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

				ProductMaterial pm3 = ProductMaterial.builder().product(hat2).material(materials.get(2)).percentage(70)
						.build();
				pm3.setCreatedBy(u1);
				ProductMaterial pm4 = ProductMaterial.builder().product(hat2).material(materials.get(3)).percentage(30)
						.build();
				pm4.setCreatedBy(u1);
				productMaterialRepo.saveAll(Arrays.asList(pm3, pm4));

				Product hat3 = Product.builder().price(BigDecimal.valueOf(11.0)).amount(0).category(categoryOne)
						.build();
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

				ProductMaterial pm5 = ProductMaterial.builder().product(hat2).material(materials.get(0)).percentage(100)
						.build();
				pm5.setCreatedBy(u1);
				productMaterialRepo.save(pm5);

				Product hat4 = Product.builder().price(BigDecimal.valueOf(13.0)).amount(4).category(categoryOne)
						.build();
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
				Product glove1 = Product.builder().price(BigDecimal.valueOf(8.5)).amount(6).category(categoryTwo)
						.build();
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

				Product glove2 = Product.builder().price(BigDecimal.valueOf(9.0)).amount(4).category(categoryTwo)
						.build();
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

				Product glove3 = Product.builder().price(BigDecimal.valueOf(8.0)).amount(5).category(categoryTwo)
						.build();
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

				Product glove4 = Product.builder().price(BigDecimal.valueOf(10.0)).amount(3).category(categoryTwo)
						.build();
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

				Product glove5 = Product.builder().price(BigDecimal.valueOf(7.5)).amount(7).category(categoryTwo)
						.build();
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

				Product glove6 = Product.builder().price(BigDecimal.valueOf(8.75)).amount(5).category(categoryTwo)
						.build();
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

				Product glove7 = Product.builder().price(BigDecimal.valueOf(9.5)).amount(2).category(categoryTwo)
						.build();
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
				ProductImage bilde1 = ProductImage.builder().product(glove7)
						.src("http://localhost:8080/images/products/cimdi1.jpg").build();
				bilde1.setCreatedBy(u1);
				ProductImageTranslation bilde1_lv = ProductImageTranslation.builder().alt("Labi cimdi 1")
						.picture(bilde1).locale(LocaleCode.LV).build();
				ProductImageTranslation bilde1_en = ProductImageTranslation.builder().alt("Good gloves 1")
						.picture(bilde1).locale(LocaleCode.EN).build();
				bilde1.setTranslations(List.of(bilde1_lv, bilde1_en));
				productImageRepo.save(bilde1);

				ProductImage bilde2 = ProductImage.builder().product(glove7)
						.src("http://localhost:8080/images/products/cimdi2.jpg").build();
				bilde2.setCreatedBy(u1);
				ProductImageTranslation bilde2_lv = ProductImageTranslation.builder().alt("Labi cimdi 2")
						.picture(bilde2).locale(LocaleCode.LV).build();
				ProductImageTranslation bilde2_en = ProductImageTranslation.builder().alt("Good gloves 2")
						.picture(bilde2).locale(LocaleCode.EN).build();
				bilde2.setTranslations(List.of(bilde2_lv, bilde2_en));
				productImageRepo.save(bilde2);

				ProductImage bilde3 = ProductImage.builder().product(glove7)
						.src("http://localhost:8080/images/products/cimdi3.jpg").build();
				bilde3.setCreatedBy(u1);
				ProductImageTranslation bilde3_lv = ProductImageTranslation.builder().alt("Labi cimdi 3")
						.picture(bilde3).locale(LocaleCode.LV).build();
				ProductImageTranslation bilde3_en = ProductImageTranslation.builder().alt("Good gloves 3")
						.picture(bilde3).locale(LocaleCode.EN).build();
				bilde3.setTranslations(List.of(bilde3_lv, bilde3_en));
				productImageRepo.save(bilde3);

				ProductImage bilde4 = ProductImage.builder().product(glove6)
						.src("http://localhost:8080/images/products/cimdi1.jpg").build();
				bilde4.setCreatedBy(u1);
				ProductImageTranslation bilde4_lv = ProductImageTranslation.builder().alt("Labi cimdi 3")
						.picture(bilde4).locale(LocaleCode.LV).build();
				ProductImageTranslation bilde4_en = ProductImageTranslation.builder().alt("Good gloves 3")
						.picture(bilde4).locale(LocaleCode.EN).build();
				bilde4.setTranslations(List.of(bilde4_lv, bilde4_en));
				productImageRepo.save(bilde4);

				ProductImage bilde5 = ProductImage.builder().product(glove6)
						.src("http://localhost:8080/images/products/cimdi2.jpg").build();
				bilde5.setCreatedBy(u1);
				ProductImageTranslation bilde5_lv = ProductImageTranslation.builder().alt("Labi cimdi 3")
						.picture(bilde5).locale(LocaleCode.LV).build();
				ProductImageTranslation bilde5_en = ProductImageTranslation.builder().alt("Good gloves 3")
						.picture(bilde5).locale(LocaleCode.EN).build();
				bilde5.setTranslations(List.of(bilde5_lv, bilde5_en));
				productImageRepo.save(bilde5);

				ProductImage bilde6 = ProductImage.builder().product(glove5)
						.src("http://localhost:8080/images/products/cimdi3.jpg").build();
				bilde6.setCreatedBy(u1);
				ProductImageTranslation bilde6_lv = ProductImageTranslation.builder().alt("Labi cimdi 3")
						.picture(bilde6).locale(LocaleCode.LV).build();
				ProductImageTranslation bilde6_en = ProductImageTranslation.builder().alt("Good gloves 3")
						.picture(bilde6).locale(LocaleCode.EN).build();
				bilde6.setTranslations(List.of(bilde6_lv, bilde6_en));
				productImageRepo.save(bilde6);

				log.info("Data has been successfully seeded.");
			}
		};
	}

	// @Order(2)
	// @Bean
	public CommandLineRunner createDeliveryTypesAndPrices(DeliveryTypeRepository deliveryTypeRepo,
			UserRepository userRepo) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				System.out.println("=== createDeliveryTypesAndPrices ===");
				User u1 = userRepo.findSystemUserNative();

				List<DeliveryType> deliveryTypes = new ArrayList<>();

				// ---------- 1. PICKUP ----------
				DeliveryType pickup = new DeliveryType(DeliveryMethod.PICKUP);
				pickup.setCreatedBy(u1);

				DeliveryTypeTranslation pickup_lv = DeliveryTypeTranslation.builder()
						.title("Saņemšana veikalā")
						.description("Bezmaksas pasūtījuma saņemšana mūsu veikalā Ventspilī")
						.locale(LocaleCode.LV)
						.deliveryType(pickup)
						.build();

				DeliveryTypeTranslation pickup_en = DeliveryTypeTranslation.builder()
						.title("Store pickup")
						.description("Free pickup from our store in Ventspils")
						.locale(LocaleCode.EN)
						.deliveryType(pickup)
						.build();

				pickup.setTranslations(List.of(pickup_lv, pickup_en));
				pickup.setPrices(List.of(
						DeliveryPrice.builder()
								.country(Country.LV)
								.price(BigDecimal.ZERO)
								.deliveryType(pickup)
								.build()));

				deliveryTypes.add(pickup);

				// ---------- 2. OMNIVA ----------
				DeliveryType omniva = new DeliveryType(DeliveryMethod.PARCEL_MACHINE);
				omniva.setCreatedBy(u1);

				DeliveryTypeTranslation omniva_lv = DeliveryTypeTranslation.builder()
						.title("Omniva pakomāts")
						.description("Piegāde uz Omniva pakomātu. 5 EUR Latvijā, 10 EUR uz Lietuvu un Igauniju.")
						.locale(LocaleCode.LV)
						.deliveryType(omniva)
						.build();

				DeliveryTypeTranslation omniva_en = DeliveryTypeTranslation.builder()
						.title("Omniva parcel locker")
						.description("Delivery to Omniva parcel locker. €5 in Latvia, €10 to LT and EE.")
						.locale(LocaleCode.EN)
						.deliveryType(omniva)
						.build();

				omniva.setTranslations(List.of(omniva_lv, omniva_en));
				omniva.setPrices(List.of(
						DeliveryPrice.builder().country(Country.LV).price(new BigDecimal("5.00")).deliveryType(omniva)
								.build(),
						DeliveryPrice.builder().country(Country.LT).price(new BigDecimal("10.00")).deliveryType(omniva)
								.build(),
						DeliveryPrice.builder().country(Country.EE).price(new BigDecimal("10.00")).deliveryType(omniva)
								.build()));

				deliveryTypes.add(omniva);

				// ---------- 3. COURIER ----------
				DeliveryType courier = new DeliveryType(DeliveryMethod.COURIER);
				courier.setCreatedBy(u1);

				DeliveryTypeTranslation courier_lv = DeliveryTypeTranslation.builder()
						.title("Kurjers")
						.description("Kurjera piegāde līdz durvīm. 10 EUR Latvijā, 20 EUR uz Lietuvu un Igauniju.")
						.locale(LocaleCode.LV)
						.deliveryType(courier)
						.build();

				DeliveryTypeTranslation courier_en = DeliveryTypeTranslation.builder()
						.title("Courier")
						.description("Courier delivery to your door. €10 in Latvia, €20 to Lithuania and Estonia")
						.locale(LocaleCode.EN)
						.deliveryType(courier)
						.build();

				courier.setTranslations(List.of(courier_lv, courier_en));
				courier.setPrices(List.of(
						DeliveryPrice.builder().country(Country.LV).price(new BigDecimal("10.00")).deliveryType(courier)
								.build(),
						DeliveryPrice.builder().country(Country.LT).price(new BigDecimal("20.00")).deliveryType(courier)
								.build(),
						DeliveryPrice.builder().country(Country.EE).price(new BigDecimal("20.00")).deliveryType(courier)
								.build()));

				deliveryTypes.add(courier);

				// ---------- Save all ----------
				deliveryTypeRepo.saveAll(deliveryTypes);

				System.out.println("=== createDeliveryTypesAndPrices SEEDED ===");
			}
		};
	}

	// @Bean
	// @Order(3)
	public CommandLineRunner setUpPermissionsAndRoles(UserRepository userRepo, RoleRepository roleRepository,
			PermissionRepository permissionRepo,
			PasswordEncoder passwordEncoder) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws RuntimeException {
				System.out.println("=== setUpPermissionsAndRoles ===");
				Permission toManageProducts = new Permission(PermissionName.MANAGE_PRODUCTS);
				Permission toManageOrders = new Permission(PermissionName.MANAGE_ORDERS);
				Permission toManageNews = new Permission(PermissionName.MANAGE_NEWS);
				Permission toManageSecurity = new Permission(PermissionName.MANAGE_SECURITY);

				permissionRepo.saveAll(List.of(toManageProducts, toManageOrders, toManageNews, toManageSecurity));

				Role boss = new Role("Galvenais administrators");
				Role productManager = new Role("Produktu pārvaldnieks");
				Role orderManager = new Role("Pasūtījumu pārvaldnieks");
				Role newsManager = new Role("Ziņu redaktors");
				Role securityManager = new Role("Drošības pārvaldnieks");

				boss.setPermissions(List.of(toManageProducts, toManageOrders, toManageNews, toManageSecurity));
				productManager.setPermissions(List.of(toManageProducts));
				orderManager.setPermissions(List.of(toManageOrders));
				newsManager.setPermissions(List.of(toManageNews));
				securityManager.setPermissions(List.of(toManageSecurity));
				roleRepository.saveAll(List.of(boss, productManager, orderManager, newsManager, securityManager));

				User admin = User.builder()
						.email("products@mail.com")
						.username("admin")
						.firstName("Deniss")
						.lastName("Adminov")
						.password(passwordEncoder.encode("password"))
						.build();
				admin.setRoles(List.of(boss));

				User productManagerUser = User.builder()
						.email("admin@mail.com")
						.username("products")
						.firstName("Deniss")
						.lastName("Prouctov")
						.password(passwordEncoder.encode("password"))
						.build();
				productManagerUser.setRoles(List.of(productManager));

				User orderManagerUser = User.builder()
						.email("orders@mail.com")
						.username("orders")
						.firstName("Deniss")
						.lastName("Orderov")
						.password(passwordEncoder.encode("password"))
						.build();
				orderManagerUser.setRoles(List.of(orderManager));

				User newsManagerUser = User.builder()
						.email("news@mail.com")
						.username("news")
						.firstName("Deniss")
						.lastName("Newsow")
						.password(passwordEncoder.encode("password"))
						.build();
				newsManagerUser.setRoles(List.of(newsManager));

				User securityManagerUser = User.builder()
						.email("security@mail.com")
						.username("security")
						.firstName("Deniss")
						.lastName("Securov")
						.password(passwordEncoder.encode("password"))
						.build();
				securityManagerUser.setRoles(List.of(securityManager));

				userRepo.saveAll(
						List.of(admin, productManagerUser, orderManagerUser, newsManagerUser, securityManagerUser));
				System.out.println("=== SEEDED ===");
			}
		};
	}

}
