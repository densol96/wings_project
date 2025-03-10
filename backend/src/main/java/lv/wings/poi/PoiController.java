package lv.wings.poi;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;

import lv.wings.model.entity.Purchase;
import lv.wings.model.entity.PurchaseElement;

import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class PoiController {
	public static <T> byte[] buildSingle(String tabname, T source, String[] fields) throws Exception {
		// ielikt objektu ArrayListā, lai izmantotu funkciju, kas izveidotu to daudziem
		ArrayList<T> sources = new ArrayList<>();
		sources.add(source);
		return buildMultiple(tabname, sources, fields);
	}

	public static <T> byte[] buildMultiple(String filename, List<T> source, String[] fields) throws Exception {
		// Neko nedarīt, ja ir tukšs
		if (source.isEmpty())
			throw new Exception("No source provided");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int cells = 0;

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(filename);

		// rinda nosaukumiem
		Row headerRow = sheet.createRow(0);
		// sagatavotas rindas visiem objektiem
		Row[] inputRows = new Row[source.size()];

		for (int i = 0; i < inputRows.length; i++) {
			inputRows[i] = sheet.createRow(i + 1);
		}

		// izūgt klasi no padota objekta, lai saprastu to tipu
		Class<?> cls = source.get(0).getClass();
		// iegūt tām piemītošas metodes
		Method[] methods = cls.getMethods();

		// ja ir norādīti lauki, kuri ir jāņem - paņemt tos pareizajā secībā un
		// aizvietot tas metodes
		if (fields.length != 0) {
			// sagatavot laukus uz getteru stilu (example -> getExample)
			String[] parsedField = new String[fields.length];

			for (int i = 0; i < fields.length; i++) {
				parsedField[i] = formatToGetterStyle(fields[i]);
			}
			// filtrēt metodes
			Method[] newMethods = new Method[parsedField.length];

			for (Method method : methods) {
				for (int j = 0; j < parsedField.length; j++) {
					if (method.getName().equals(parsedField[j])) {
						newMethods[j] = method;
					}
				}
			}

			methods = newMethods;
		}

		// iziet cauri katrai no tām metodēm, lai nolasītu datus
		for (Method method : methods) {
			String methodName = method.getName();

			// filtrēt visas metodes, kas nav geteri
			if (methodName.equals("getClass") || !methodName.startsWith("get"))
				continue;
			// izveidot virsraksta šūnu
			Cell headCell = headerRow.createCell(cells);
			// atrast pamata lauku, jo getteri tiek veidoti ar lomboku un @PoiMeta ir
			// pievienota laukam
			Field corespondingField = cls.getDeclaredField(formatToFieldStyle(methodName));

			// definēti noklusējuma dati
			String headCellContent = formatToUpperStyle(methodName.substring(3));
			// tukšs - nepārveidot, ar saturu - nomainīt {} uz lauka saturu
			String valueFormat = "";

			if (corespondingField != null) {
				PoiMeta poiMeta;
				poiMeta = corespondingField.getAnnotation(PoiMeta.class);
				// ja @PoiMeta piemīt laukam - nolasīt ta saturu
				if (poiMeta != null) {
					headCellContent = !poiMeta.name().isEmpty() ? poiMeta.name() : headCellContent;
					valueFormat = !poiMeta.valueFormat().isEmpty() ? poiMeta.valueFormat() : valueFormat;
				}
			}

			// nolasīt visus datus no laukiem
			Object[] fieldResults = new Object[source.size()];
			int nonNullIndex = -1;
			for (int i = 0; i < fieldResults.length; i++) {
				fieldResults[i] = method.invoke(source.get(i));
				// izgūt pirmo lauku ar non null saturu, lai definētu ta tipu
				if (nonNullIndex == -1 && fieldResults[i] != null) {
					nonNullIndex = i;
				}
			}
			// ja visi ur null - aiziet un nākošo lauku
			if (nonNullIndex == -1)
				continue;

			// iegt visas metodes un laukus no klases
			Class<?> subCls = fieldResults[nonNullIndex].getClass();
			boolean valueFound = false;
			Field[] subFields = subCls.getDeclaredFields();
			Method[] subMethods = subCls.getMethods();

			// pamēginat iegūt lauku ar @Id anitāciju no modeļa
			if (!valueFound) {
				for (Field subField : subFields) {
					if (subField.isAnnotationPresent(jakarta.persistence.Id.class)) {
						subField.setAccessible(true);
						// Ja ir atrasts - pierakstīt virsrakstam (Id): (Kategorija -> Kategorija (Id))
						headCellContent += " (Id)";
						valueFound = true;

						// Ja ir atrasts - izveidot katrā rindā šūnu un ierakstīt tajā Id
						for (int i = 0; i < inputRows.length; i++) {
							if (fieldResults[i] == null)
								continue;
							Object fieldResult = subField.get(fieldResults[i]);
							Cell valueCell = inputRows[i].createCell(cells);
							valueCell.setCellValue(
									!valueFormat.isEmpty() ? valueFormat.replace("{}", fieldResult.toString())
											: fieldResult.toString());
						}
						break;
					}
				}
			}

			// ja nav atrasts - paskatīties, vai piemīt "size" metode, kas ir vasām
			// kolekcijām
			if (!valueFound) {
				for (Method subMethod : subMethods) {
					if (subMethod.getName().equals("size")) {
						// Ja ir atrasts - pierakstīt virsrakstam (Skaits): (Attēli -> Attēli (Skaits))
						headCellContent += " (Skaits)";
						valueFound = true;

						// Ja ir atrasts - izveidot katrā rindā šūnu un ierakstīt tajā elementu skaitu
						for (int i = 0; i < inputRows.length; i++) {
							if (fieldResults[i] == null)
								continue;
							Object fieldResult = subMethod.invoke(fieldResults[i]);
							Cell valueCell = inputRows[i].createCell(cells);
							valueCell.setCellValue(
									!valueFormat.isEmpty() ? valueFormat.replace("{}", fieldResult.toString())
											: fieldResult.toString());
						}
						break;
					}
				}
			}

			// ierakstīt virsrakstu šūnā
			headCell.setCellValue(headCellContent);

			// ja nekas no ieprieksējā nesanāca - rezultātā droši vien ir parasts datu tips
			// e.g. String, int
			if (!valueFound) {
				// ierakstīt jau izlasītos datus iekšā šūnā
				for (int i = 0; i < inputRows.length; i++) {
					Cell valueCell = inputRows[i].createCell(cells);
					valueCell
							.setCellValue(!valueFormat.isEmpty() ? valueFormat.replace("{}", fieldResults[i].toString())
									: fieldResults[i].toString());
				}
			}
			cells++;
		}

		// automātiski izmainīt visu kolonnu izmēru
		for (int i = 0; i < cells; i++) {
			sheet.autoSizeColumn(i);
			// pievienot papildus 4 simbolu izmēru platumu (1 simbols ir 256), jo
			// automātisks izmērs ir pārāk mazs
			sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1024);
		}

		workbook.write(outputStream);
		workbook.close();

		outputStream.close();
		return outputStream.toByteArray();
	}

	public static byte[] buildInvoice(String filename, Purchase source) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		XWPFDocument document = new XWPFDocument();

		XWPFParagraph paragraph;
		XWPFRun run;

		XWPFTable table;
		List<XWPFTableRow> tableRows;

		paragraph = document.createParagraph();
		run = paragraph.createRun();
		run.setText("Pirkums\n");
		run.setFontSize(32);

		table = document.createTable(1, 3);
		table.setWidth("100%");
		table.setCellMargins(100, 200, 100, 200);
		table.setBottomBorder(XWPFBorderType.NONE, 0, 0, "000000");
		table.setTopBorder(XWPFBorderType.NONE, 0, 0, "000000");
		table.setLeftBorder(XWPFBorderType.NONE, 0, 0, "000000");
		table.setRightBorder(XWPFBorderType.NONE, 0, 0, "000000");
		// table.setInsideVBorder(XWPFBorderType.NONE, 0, 0, "000000");

		tableRows = table.getRows();
		paragraph = tableRows.get(0).getCell(0).getParagraphArray(0);
		run = paragraph.createRun();
		// run.setText(source.getPircejs().getPersonasKods());
		// run.addBreak();
		run.setText(source.getCustomer().getAdress());

		paragraph = tableRows.get(0).getCell(1).getParagraphArray(0);
		paragraph.setAlignment(ParagraphAlignment.RIGHT);
		run = paragraph.createRun();
		run.setText("Pasūtījuma Datums");
		run.addBreak();
		run.setText(source.getDeliveryDate().toString());
		run.addBreak();
		run.setText("Pasūtījuma ID");
		run.addBreak();
		run.setText(source.getPurchaseId() + "");
		run.addBreak();

		paragraph = tableRows.get(0).getCell(2).getParagraphArray(0);
		run = paragraph.createRun();
		run.setText("Veikala nosaukums");
		run.addBreak();
		run.setText("Veikala adrese");
		run.addBreak();
		run.setText("Cita informācija");

		paragraph = document.createParagraph();
		run = paragraph.createRun();
		run.addBreak();
		run.addBreak();

		List<PurchaseElement> purchaseElements = (List<PurchaseElement>) source.getPurchaseElement();

		table = document.createTable(2 + purchaseElements.size(), 3);
		table.setWidth("100%");
		table.setCellMargins(100, 200, 100, 200);
		tableRows = table.getRows();
		paragraph = tableRows.get(0).getCell(0).getParagraphArray(0);
		paragraph.createRun().setText("Prece");
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		paragraph = tableRows.get(0).getCell(1).getParagraphArray(0);
		paragraph.createRun().setText("Skaits");
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		paragraph = tableRows.get(0).getCell(2).getParagraphArray(0);
		paragraph.createRun().setText("Summa");
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		float summ = 0;

		for (int i = 0; i < purchaseElements.size(); i++) {
			int amount = purchaseElements.get(i).getAmount();
			float price = purchaseElements.get(i).getProduct().getPrice();

			tableRows.get(i + 1).getCell(0).getParagraphArray(0).createRun()
					.setText(purchaseElements.get(i).getProduct().getTitle() + "");
			tableRows.get(i + 1).getCell(1).getParagraphArray(0).createRun().setText(amount + "");
			tableRows.get(i + 1).getCell(2).getParagraphArray(0).createRun().setText(price + "");
			summ += amount * price;
		}

		paragraph = tableRows.get(table.getNumberOfRows() - 1).getCell(1).getParagraphArray(0);
		paragraph.createRun().setText("Summa:");
		paragraph.setAlignment(ParagraphAlignment.RIGHT);

		run = tableRows.get(table.getNumberOfRows() - 1).getCell(2).getParagraphArray(0).createRun();
		run.setText(summ + "");
		run.setBold(true);

		/*
		 * XWPFHeader head = document.createHeader(HeaderFooterType.DEFAULT);
		 * head.createParagraph().createRun().setText("header");
		 * 
		 * XWPFFooter foot = document.createFooter(HeaderFooterType.DEFAULT);
		 * foot.createParagraph().createRun().setText("footer");
		 */

		document.write(outputStream);
		document.close();
		outputStream.close();
		return outputStream.toByteArray();
	}

	// kadsLauka_piemērs -> getKadsLauka_piemērs
	private static String formatToGetterStyle(String str) {
		return "get" + Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}

	// getKadsLauka_piemērs -> kadsLauka_piemērs
	private static String formatToFieldStyle(String str) {
		return Character.toLowerCase(str.charAt(3)) + str.substring(4);
	}

	// kadsLauka_piemers -> Kads Lauka Piemers
	private static String formatToUpperStyle(String str) {
		StringBuilder builder = new StringBuilder();
		boolean nextIsUpper = true;

		// iziet cauri katram ciparam, ja sastop _ -> izveidot atstarpi un rakstīt
		// nākošo ciparu ar lielo
		// ja ir lielais burts -> pievienot pievienot atstarpi un rakstīt to burtu
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (ch == '_') {
				builder.append(' ');
				nextIsUpper = true;
			} else if (Character.isUpperCase(ch)) {
				if (!nextIsUpper && builder.length() != 0)
					builder.append(" ");
				builder.append(ch);
				nextIsUpper = false;
			} else {
				builder.append(nextIsUpper ? Character.toUpperCase(ch) : ch);
				nextIsUpper = false;
			}
		}

		return builder.toString();
	}
}
