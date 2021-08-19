package com.tds.business;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.tds.dto.RefData;
import com.tds.dto.Trade;
import com.tds.dto.Valuation;

public class ReadFiles {

	String COMMA = ",";
	private static final Logger LOGGER = Logger.getLogger(ReadFiles.class.getName());

	/**
	 * This method will process given trade input. Here we are processing the files.
	 * For future extensions, we can change this method to read the data from Database or even we can can read from 
	 * a web service . Simi9larly, we just need to populate the RefData , Valuation and Trade DTOs accordingly. 
	 * */
	public List<Trade> processTradeData() {
		List<Trade> tradeList = new ArrayList<Trade>();

		try {

			Reader tradeReader = Files.newBufferedReader(Paths.get("./resources/trade.csv"));
			CSVParser tradeParser = new CSVParser(tradeReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim());
			List<CSVRecord> recordsList = StreamSupport.stream(tradeParser.spliterator(), false)
					.collect(Collectors.toList());
			System.out.println(recordsList.size());

			for (CSVRecord csvRecord : recordsList) {
				Trade tradeDto = new Trade();

				tradeDto.setInventory(csvRecord.get("Inventory"));
				tradeDto.setBook(csvRecord.get("Book"));
				tradeDto.setSystem(csvRecord.get("System"));
				tradeDto.setLegalEntity(csvRecord.get("LegalEntity"));

				tradeDto.setTradeId(Long.parseLong(csvRecord.get("TradeId")));
				tradeDto.setVersion(Integer.parseInt(csvRecord.get("Version")));
				tradeDto.setTradeStatus(csvRecord.get("TradeStatus"));

				tradeDto.setProductType(csvRecord.get("ProductType"));
				tradeDto.setResettingLeg(csvRecord.get("Resetting Leg"));

				tradeDto.setProductSubType(csvRecord.get("ProductSubType"));
				tradeDto.settDSProductType(csvRecord.get("TDSProductType"));
				tradeDto.setSecCodeSubType(csvRecord.get("SecCodeSubType"));

				tradeDto.setSwapType(csvRecord.get("SwapType"));
				tradeDto.setDescription(csvRecord.get("Description"));

				String matDateStr = csvRecord.get("MaturityDate") + "";
				String tradeDateStr = csvRecord.get("TradeDate") + "";
				String startDateStr = csvRecord.get("StartDate") + "";

				String pattern = "yyyyMMdd";
				Date matDate;
				Date tradeDate;
				Date startDate;
				try {
					matDate = new SimpleDateFormat(pattern).parse(matDateStr);
					tradeDate = new SimpleDateFormat(pattern).parse(tradeDateStr);
					startDate = new SimpleDateFormat(pattern).parse(startDateStr);
					tradeDto.setMaturityDate(matDate);
					tradeDto.setTradeDate(tradeDate);
					tradeDto.setStartDate(startDate);
					tradeDto.setMaturityDateStr(matDateStr);
					tradeDto.setTradeDateStr(tradeDateStr);
					tradeDto.setStartDateStr(startDateStr);
				} catch (ParseException ex) {

					LOGGER.log(Level.SEVERE, "Exception occured while processing dates", ex);
				}
				tradeList.add(tradeDto);
				System.out.println(tradeDto.getTradeId());

			}

		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, "Exception occured while processing Trade ", ex);
		}

		return tradeList;

	}

	public List<RefData> processRefData() {
		List<RefData> refDataList = new ArrayList<RefData>();

		try {

			Reader reader = Files.newBufferedReader(Paths.get("./resources/refdata.csv"));
			CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim());
			List<CSVRecord> recordsList = StreamSupport.stream(parser.spliterator(), false)
					.collect(Collectors.toList());
			System.out.println(recordsList.size());

			for (CSVRecord csvRecord : recordsList) {
				RefData refDataDto = new RefData();
				refDataDto.setInventory(csvRecord.get("Inventory"));
				refDataDto.setTopOfHouse(csvRecord.get("topOfHouse"));
				refDataDto.setSegment(csvRecord.get("segment"));
				refDataDto.setViceChair(csvRecord.get("viceChair"));
				refDataDto.setGlobalBusiness(csvRecord.get("globalBusiness"));
				refDataDto.setBu(Integer.parseInt(csvRecord.get("BU")));
				refDataDto.setPolicy(csvRecord.get("Policy"));
				refDataDto.setDesk(csvRecord.get("desk"));
				refDataDto.setPortfolio(csvRecord.get("portfolio"));
				refDataDto.setCline(csvRecord.get("CLINE"));
				refDataList.add(refDataDto);
			
			}

		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, "Exception occured while processing the refdata", ex);
		}

		return refDataList;
	}

	public List<Valuation> processValuation() {
		List<Valuation> valuationList = new ArrayList<Valuation>();

		try {

			Reader reader = Files.newBufferedReader(Paths.get("./resources/valuation.csv"));
			CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim());
			List<CSVRecord> recordsList = StreamSupport.stream(parser.spliterator(), false)
					.collect(Collectors.toList());
			System.out.println(recordsList.size());

			for (CSVRecord csvRecord : recordsList) {
				Valuation valuationDto = new Valuation();
				valuationDto.setTradeId(Long.parseLong(csvRecord.get("TradeId")));
				valuationDto.setUQL_OC_MMB_MS(new BigDecimal((csvRecord.get("UQL_OC_MMB_MS"))));
				valuationDto.setUQL_OC_MMB_MS_PC(new BigDecimal((csvRecord.get("UQL_OC_MMB_MS_PC"))));
				valuationList.add(valuationDto);

			}

		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, "Exception occured while processing the valuation", ex);
		}

		return valuationList;
	}

	public static void main(String[] args) {
		ReadFiles readFiles = new ReadFiles();
		List<Trade> tradeList = readFiles.processTradeData();
		List<RefData> refDataList = readFiles.processRefData();
		List<Valuation> valuationList = readFiles.processValuation();
		WriteReport writeReoprt = new WriteReport();
		try {
			writeReoprt.writeOutput(tradeList, refDataList, valuationList);
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, "Exception occured while writing the file", ex);
		}
	}
}
