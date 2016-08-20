package com.cidic.sdx.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cidic.sdx.model.HPModel;
import com.cidic.sdx.service.HpManageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
public class ImportExcelData {

	private static final Map<String, String> map = new HashMap<String, String>() {
		{
			put("A", "14");
			put("B", "15");
			put("C", "16");
			put("D", "17");
			put("E", "18");
			put("F", "19");
			put("G", "20");
			put("H", "21");
			put("J", "22");
			put("K", "23");
			put("L", "24");
			put("M", "25");
			put("圣得西", "1");
			put("商务系列", "2");
			put("婚庆商务", "5");
			put("自在商务", "6");
			put("时尚商务", "7");
			put("自在系列", "3");
			put("轻商务", "8");
			put("轻休闲", "9");
			put("时尚系列", "4");
			put("都市时尚", "10");
			put("休闲时尚", "11");
			put("裤装", "1");
			put("西裤", "5");
			put("仿毛西裤", "8");
			put("毛料西裤", "9");
			put("仿毛套裤", "10");
			put("毛料套裤", "11");
			put("休闲裤", "6");
			put("休闲长裤", "12");
			put("休闲短裤", "13");
			put("牛仔裤", "7");
			put("牛仔长裤", "14");
			put("牛仔短裤", "15");
			put("外套", "2");
			put("套西", "16");
			put("仿毛套西", "27");
			put("毛料套西", "28");
			put("轻单西", "17");
			put("便装", "18");
			put("夹克", "19");
			put("大衣", "20");
			put("马夹", "21");
			put("风衣", "22");
			put("尼克服", "23");
			put("皮衣", "24");
			put("单皮衣", "29");
			put("厚皮衣", "30");
			put("棉服", "25");
			put("薄棉服", "31");
			put("厚棉服", "32");
			put("羽绒服", "26");
			put("薄羽绒服", "33");
			put("厚羽绒服", "34");
			put("羽绒背心", "35");
			put("内搭", "3");
			put("T恤", "36");
			put("长袖T恤", "39");
			put("有领短T恤", "40");
			put("长袖工艺衫", "41");
			put("短袖工艺衫", "42");
			put("圆V领短T恤", "43");
			put("毛衫", "37");
			put("薄毛衫", "44");
			put("厚毛衫", "45");
			put("毛背心", "46");
			put("衬衣", "38");
			put("长正装衬衣", "47");
			put("短正装衬衣", "48");
			put("长休闲衬衣", "49");
			put("短休闲衬衣", "50");
			put("配饰", "4");
			put("皮带", "51");
			put("商务皮带", "60");
			put("休闲皮带", "61");
			put("鞋子", "52");
			put("商务鞋", "");
			put("休闲鞋", "");
			put("箱包", "53");
			put("商务包", "64");
			put("休闲包", "65");
			put("旅行箱", "66");
			put("皮夹", "67");
			put("领带", "54");
			put("领结", "55");
			put("小方巾", "56");
			put("围巾", "57");
			put("袜子", "58");
			put("内裤", "59");
			put("0蓝", "1");
			put("01鸭蓝", "11");
			put("02矿物蓝", "12");
			put("03冰蓝", "13");
			put("04蓝色", "15");
			put("1黑", "2");
			put("11黑色", "16");
			put("2白", "3");
			put("21白色", "17");
			put("22米白", "18");
			put("23米色", "19");
			put("24米杏", "20");
			put("3灰", "4");
			put("31深灰", "21");
			put("32中灰", "22");
			put("33浅灰", "23");
			put("34灰色", "24");
			put("4红", "5");
			put("41酒红", "25");
			put("42粉红", "26");
			put("43紫红", "27");
			put("44西瓜红", "28");
			put("45玫红", "29");
			put("46砖红", "30");
			put("47大红", "31");
			put("48深红", "32");
			put("49珊瑚红", "33");
			put("5紫", "6");
			put("51深紫", "34");
			put("52浅紫", "35");
			put("53紫罗兰", "36");
			put("54紫色", "37");
			put("6绿", "8");
			put("60绿色", "38");
			put("61墨绿", "39");
			put("62军绿", "40");
			put("63松绿", "41");
			put("64浅绿", "42");
			put("65孔雀绿", "43");
			put("66卡其绿", "44");
			put("67薄荷绿", "45");
			put("68森林绿", "46");
			put("69橄榄绿", "48");
			put("7蓝", "49");
			put("70克莱因蓝", "53");
			put("71藏青", "54");
			put("72深蓝", "55");
			put("73宝石蓝", "56");
			put("74天蓝", "57");
			put("75浅蓝", "58");
			put("76灰蓝", "59");
			put("77孔雀蓝", "60");
			put("78牛仔蓝", "61");
			put("79石油蓝", "62");
			put("8黄", "50");
			put("81橙色", "64");
			put("82黄色", "65");
			put("83驼色", "66");
			put("84芥末黄", "67");
			put("85米黄", "68");
			put("9卡其", "51");
			put("90卡其", "69");
			put("91咖啡", "70");
			put("92深卡", "71");
			put("93中卡", "72");
			put("94浅卡", "73");
			put("95米卡", "74");
			put("96棕色", "75");
			put("其它色", "52");
		}
	};

	@Autowired
	@Qualifier(value = "hpManageServiceImpl")
	private HpManageService hpManageServiceImpl;

	@Test
	public void testInsertHp(){

		try{
			List<HPModel> list = this.readXlsx("d:\\sdx2.xlsx");
			System.out.println("总共有:"+list.size()+"条数据");
			/*
			if (list.size() > 1){
				HPModel HPModel = list.get(0);
				System.out.println(HPModel.getBrand());
				System.out.println(HPModel.getColor());
				System.out.println(HPModel.getSize());
				hpManageServiceImpl.insertHpData(list.get(0));
			}
			*/
			int i = 0;
			for (HPModel hpModel : list){
				hpManageServiceImpl.insertHpData(hpModel);
				++i;
				System.out.println("完成写入第:"+i+"条数据");
			}
			
			
			/*
			list.parallelStream().forEach((hpModel)->{
				hpManageServiceImpl.insertHpData(hpModel);
			});
			*/
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}

	public List<HPModel> readXlsx(String path) throws IOException {

		InputStream is = new FileInputStream(path);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		HPModel hPModel = null;
		List<HPModel> list = new ArrayList<HPModel>();
		// Read the Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			// Read the Row
			for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					hPModel = new HPModel();
					XSSFCell cell1 = xssfRow.getCell(0);
					XSSFCell cell2 = xssfRow.getCell(1);
					XSSFCell cell3 = xssfRow.getCell(2);
					XSSFCell cell4 = xssfRow.getCell(3);
					XSSFCell cell5 = xssfRow.getCell(4);
					XSSFCell cell6 = xssfRow.getCell(5);
					XSSFCell cell7 = xssfRow.getCell(6);
					XSSFCell cell8 = xssfRow.getCell(7);
					XSSFCell cell9 = xssfRow.getCell(8);
					XSSFCell cell10 = xssfRow.getCell(9);
					XSSFCell cell11 = xssfRow.getCell(10);
					XSSFCell cell12 = xssfRow.getCell(11);
					XSSFCell cell13 = xssfRow.getCell(12);
					XSSFCell cell14 = xssfRow.getCell(13);
					XSSFCell cell15 = xssfRow.getCell(14);
					XSSFCell cell16 = xssfRow.getCell(15);
					XSSFCell cell17 = xssfRow.getCell(16);
					XSSFCell cell18 = xssfRow.getCell(17);
					XSSFCell cell19 = xssfRow.getCell(18);
					XSSFCell cell20 = xssfRow.getCell(19);
					XSSFCell cell21 = xssfRow.getCell(30);
					XSSFCell cell22 = xssfRow.getCell(21);
					XSSFCell cell23 = xssfRow.getCell(22);
					XSSFCell cell24 = xssfRow.getCell(23);
					XSSFCell cell25 = xssfRow.getCell(24);
					XSSFCell cell26 = xssfRow.getCell(25);
					XSSFCell cell27 = xssfRow.getCell(26);
					XSSFCell cell28 = xssfRow.getCell(27);
					XSSFCell cell29 = xssfRow.getCell(28);
					XSSFCell cell30 = xssfRow.getCell(29);
					XSSFCell cell31 = xssfRow.getCell(30);
					XSSFCell cell32 = xssfRow.getCell(31);
					XSSFCell cell33 = xssfRow.getCell(32);
					XSSFCell cell34 = xssfRow.getCell(33);
					XSSFCell cell35 = xssfRow.getCell(34);
					XSSFCell cell36 = xssfRow.getCell(35);
					XSSFCell cell37 = xssfRow.getCell(36);
					XSSFCell cell38 = xssfRow.getCell(37);
					//System.out.println(getValue(cell1)+"	"+getValue(cell2)+"	"+getValue(cell3)+"	"+getValue(cell4)+"	"+getValue(cell5)+"	"+getValue(cell6)+"	"+getValue(cell7));
					
					hPModel.setHp_num(getValue(cell1));
					String brand = getValue(cell4);
					String brandArray[] = brand.split("\\/");
					StringBuilder brandBuilder = new StringBuilder();
					int bi = 0;
					for (String b : brandArray){
						brandBuilder.append(map.get(b));
						++bi;
						if (bi != brandArray.length){
							brandBuilder.append(",");
						}
					}
					hPModel.setBrand(brandBuilder.toString());

					hPModel.setColor(getValue(cell6).trim());
					
					String size = getValue(cell2);
					if (size != null && !size.equals("")){
						String sizeValue = map.get(String.valueOf(size.charAt(0)));
						hPModel.setSize(sizeValue);
					}
					else{
						hPModel.setSize("");
					}
					
					
					String category = getValue(cell5);
					String categoryArray[] = category.split("\\/");
					StringBuilder categoryBuilder = new StringBuilder();
					int ci = 0;
					for (String b : categoryArray){
						
						categoryBuilder.append(map.get(b));
						++ci;
						if (ci != categoryArray.length){
							categoryBuilder.append(",");
						}
					}
					hPModel.setCategory(categoryBuilder.toString());
					
					if (getValue(cell7) !=null && !getValue(cell7).equals("")){
						hPModel.setPrice(getValue(cell7));
					}
					else{
						hPModel.setPrice("无");
					}
					
					String timeCategory = getValue(cell3);
					if (timeCategory != null && !timeCategory.equals("")){
						String[] temp = timeCategory.split("\\/");
						StringBuilder timeCategoryValue = new StringBuilder();
						if (temp.length > 0){
							timeCategoryValue.append(temp[0]);
						}
						if (temp.length > 1){
							timeCategoryValue.append("/");
							timeCategoryValue.append(temp[1]);
						}
						hPModel.setTimeCategory(timeCategoryValue.toString());
					}
					
					String state = getValue(cell8);
					hPModel.setState(state);
					
					String hpName = getValue(cell9);
					hPModel.setHpName(hpName);
					
					String createTime = getValue(cell10);
					hPModel.setCreateTime(createTime);
					
					String remark = getValue(cell11);
					hPModel.setRemark(remark);
					
					String unit = getValue(cell12);
					hPModel.setUnit(unit);
					
					String isPanDian = getValue(cell13);
					hPModel.setIsPanDian(isPanDian);
					
					String kuanXing = getValue(cell14);
					hPModel.setKuanXing(kuanXing);
					
					String banXing = getValue(cell15);
					hPModel.setBanXing(banXing);
					
					String proxyPrice = getValue(cell16);
					hPModel.setProxyPrice(proxyPrice);
					
					String fPrice = getValue(cell17);
					hPModel.setfPrice(fPrice);
					
					String sPrice = getValue(cell18);
					hPModel.setsPrice(sPrice);
					
					String tPrice = getValue(cell19);
					hPModel.settPrice(tPrice);
					
					String f1Price = getValue(cell20);
					hPModel.setF1Price(f1Price);
					
					String f2Price = getValue(cell21);
					hPModel.setF2Price(f2Price);
					
					String f3Price = getValue(cell22);
					hPModel.setF3Price(f3Price);
					
					String upDown = getValue(cell23);
					hPModel.setUpDown(upDown);
					
					String huoPan = getValue(cell24);
					hPModel.setHuoPan(huoPan);
					
					String cunhuo_type = getValue(cell25);
					hPModel.setCunhuo_type(cunhuo_type);
					
					String priceSegment = getValue(cell26);
					hPModel.setPriceSegment(priceSegment);
					
					String productionType = getValue(cell27);
					hPModel.setProductionType(productionType);
					
					String releventMetting = getValue(cell28);
					hPModel.setReleventMetting(releventMetting);
					
					String mettingTime = getValue(cell29);
					hPModel.setMettingTime(mettingTime);
					
					String productionArea = getValue(cell30);
					hPModel.setProductionArea(productionArea);
					
					String entryPerson = getValue(cell31);
					hPModel.setEntryPerson(entryPerson);
					
					String entryTime = getValue(cell32);
					hPModel.setEntryTime(entryTime);
					
					String updatePerson = getValue(cell33);
					hPModel.setUpdatePerson(updatePerson);
					
					String updateTime = getValue(cell34);
					hPModel.setUpdateTime(updateTime);
					
					String effectPerson = getValue(cell35);
					hPModel.setEffectPerson(effectPerson);
					
					String effectTime = getValue(cell36);
					hPModel.setEffectTime(effectTime);
					
					String failurePerson = getValue(cell37);
					hPModel.setFailurePerson(failurePerson);
					
					String failureTime = getValue(cell38);
					hPModel.setFailureTime(failureTime);
					list.add(hPModel);
				}
			}
		}
		return list;
	}

	private String getValue(XSSFCell xssfRow) {
		if (xssfRow != null){
			if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
				return String.valueOf(xssfRow.getBooleanCellValue());
			} else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
				return String.valueOf(xssfRow.getNumericCellValue());
			} else {
				return String.valueOf(xssfRow.getStringCellValue());
			}
		}
		return "";
	}
}
