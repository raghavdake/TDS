package com.tds.business;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.tds.dto.RefData;
import com.tds.dto.Report;
import com.tds.dto.Trade;
import com.tds.dto.Valuation;

public class WriteReport {
	
	String COMMA = ",";
	private static final Logger LOGGER = Logger.getLogger(WriteReport.class.getName());
	
	public String checkBreakStatus(BigDecimal difference) {
		String breakStatus = "100000+";
		Double zero = 0d;
		Double level1 = 99d;
		Double level2 = 100d;
		Double level3 = 999d;
		Double level4 = 1000d;
		Double level5 = 9999d;
		Double level6 = 10000d;
		Double level7 = 99999d;

		if (difference.doubleValue() >= zero && difference.doubleValue() <= level1) {
			return breakStatus = "0-99";
		} else if (difference.doubleValue() >= level2 && difference.doubleValue() <= level3) {
			return breakStatus = "100-999";
		} else if (difference.doubleValue() >= level4 && difference.doubleValue() <= level5) {
			return breakStatus = "1000-9999";
		} else if (difference.doubleValue() >= level6 && difference.doubleValue() <= level7) {
			return breakStatus = "10000-99999";
		}
		return breakStatus;
	}
	
	public void writeOutput(List<Trade> tradeList, List<RefData> refDataList, List<Valuation> valuationList)
			throws IOException {
		List<Report> reportOutput = new ArrayList<>(valuationList.size());
		for (int i = 0; i < tradeList.size(); i++) {
			for (int j = 0; j < valuationList.size(); j++) {
				for (int k = 0; k < refDataList.size(); k++) {
					Trade tradeOutput = tradeList.get(i);
					Valuation valuationOutpt = valuationList.get(j);
					RefData refDataOutput = refDataList.get(k);

					if (tradeOutput.getInventory().equals(refDataOutput.getInventory())) {
						if (tradeOutput.getTradeId() == valuationOutpt.getTradeId()) {
							Report report = new Report();
							report.setTopOfHouse(refDataOutput.getTopOfHouse());
							report.setSegment(refDataOutput.getSegment());
							report.setViceChair(refDataOutput.getViceChair());
							report.setGlobalBusiness(refDataOutput.getGlobalBusiness());

							report.setPolicy(refDataOutput.getPolicy());
							report.setDesk(refDataOutput.getDesk());
							report.setPortfolio(refDataOutput.getPortfolio());
							report.setBu(refDataOutput.getBu());

							report.setCline(refDataOutput.getCline());
							report.setInventory(refDataOutput.getInventory());

							report.setBook(tradeOutput.getBook());
							report.setSystem(tradeOutput.getSystem());

							report.setLegalEntity(tradeOutput.getLegalEntity());
							report.setTradeId(tradeOutput.getTradeId());
							report.setVersion(tradeOutput.getVersion());
							report.setSystem(tradeOutput.getSystem());

							report.setTradeStatus(tradeOutput.getTradeStatus());
							report.setProductType(tradeOutput.getProductType());
							report.setResettingLeg(tradeOutput.getResettingLeg());
							report.setProductSubType(tradeOutput.getProductSubType());

							report.settDSProductType(tradeOutput.gettDSProductType());
							report.setSecCodeSubType(tradeOutput.getSecCodeSubType());
							report.setSwapType(tradeOutput.getSwapType());
							report.setDescription(tradeOutput.getDescription());

							report.setUQL_OC_MMB_MS(valuationOutpt.getUQL_OC_MMB_MS());
							report.setUQL_OC_MMB_MS_PC(valuationOutpt.getUQL_OC_MMB_MS_PC());
							BigDecimal difference = (valuationOutpt.getUQL_OC_MMB_MS()
									.subtract(valuationOutpt.getUQL_OC_MMB_MS_PC()));
						  
							report.setBreakStatus(checkBreakStatus(difference.abs()));
							report.setMS_PC(difference);
							// Today Date
							Calendar cal = Calendar.getInstance();
							cal.add(Calendar.DATE, 1);
							Date date = cal.getTime();
							SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
							String currentDateStr = format1.format(date);
							// Term
							SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
						 
							String matDateStr = sdf2.format(tradeOutput.getMaturityDate());
							long monthsBetween = ChronoUnit.MONTHS.between(
									YearMonth.from(LocalDate.parse(currentDateStr)),
									YearMonth.from(LocalDate.parse(matDateStr)));
							 

							report.setTradeDate(tradeOutput.getTradeDateStr());
							report.setStartDate(tradeOutput.getStartDateStr());
							report.setMaturityDate(tradeOutput.getMaturityDateStr());

							if (monthsBetween < 0 || null == (tradeOutput.getMaturityDate())) {
								report.setTerm("");
							} else if (monthsBetween <= 1 && monthsBetween > 0) {
								report.setTerm("0m-1m");
							} else if (monthsBetween > 1 && monthsBetween <= 6) {
								report.setTerm("1m-6m");
							} else if (monthsBetween > 6 && monthsBetween <= 12) {
								report.setTerm("6m-1yr");
							} else if (monthsBetween > 12 && monthsBetween <= 120) {
								report.setTerm("1yr-10yr");
							} else if (monthsBetween > 120 && monthsBetween <= 360) {
								report.setTerm("10yr-30yr");
							} else if (monthsBetween > 360 && monthsBetween <= 600) {
								report.setTerm("30yr-50yr");
							} else {
								report.setTerm("50yr+");
							}

							reportOutput.add(report);
							// System.out.println("OUTPUT------>"+report.getMS_PC());
						}
					}
				}
			}
		}

		generateReport(reportOutput);
	}

	@SuppressWarnings("deprecation")
	public void generateReport(List<Report> reportList) throws IOException {
		FileWriter out = null;
		try {
			out = new FileWriter("./resources/TradeDetails.csv");

		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, "Exception occured while writing file", ex);
		}

		CSVPrinter printer = CSVFormat.DEFAULT.withHeader("TopOfHouse", "Segment", "ViceChair", "GlobalBusiness",
				"Policy", "Desk", "Portfolio", "BU", "CLINE", "Inventory", "Book", "System", "LegalEntity", "TradeId",
				"Version", "TradeStatus", "ProductType", "Resetting Leg", "ProductSubType", "TDSProductType",
				"SecCodeSubType", "SwapType", "Description", "TradeDate", "StartDate", "MaturityDate", "UQL_OC_MMB_MS",
				"UQL_OC_MMB_MS_PC", "MS-PC", "BreakStatus", "Term").print(out);

		for (Report report : reportList) {
			printer.printRecord(report.getTopOfHouse(), report.getSegment(), report.getViceChair(),
					report.getGlobalBusiness(), report.getPolicy(), report.getDesk(), report.getPortfolio(),
					report.getBu(), report.getCline(), report.getInventory(), report.getBook(), report.getSystem(),
					report.getLegalEntity(), report.getTradeId(), report.getVersion(), report.getTradeStatus(),
					report.getProductType(), report.getResettingLeg(), report.getProductSubType(),
					report.gettDSProductType(), report.getSecCodeSubType(), report.getSwapType(),
					report.getDescription(), report.getTradeDate(), report.getStartDate(), report.getMaturityDate(),
					report.getUQL_OC_MMB_MS(), report.getUQL_OC_MMB_MS_PC(), report.getMS_PC(), report.getBreakStatus(),
					report.getTerm());

		}

		out.flush();
		out.close();
		printer.close();

	}

}
