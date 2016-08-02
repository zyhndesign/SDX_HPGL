package com.cidic.sdx.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cidic.sdx.exception.SdxException;
import com.cidic.sdx.model.HPListModel;
import com.cidic.sdx.model.HPModel;
import com.cidic.sdx.model.ListResultModel;
import com.cidic.sdx.model.ResultModel;
import com.cidic.sdx.service.HpIndexService;
import com.cidic.sdx.service.HpManageService;
import com.cidic.sdx.util.RedisVariableUtil;
import com.cidic.sdx.util.WebRequestUtil;

@Controller
@RequestMapping("/hpIndexManage")
public class HpIndexManagerController {

	private static final Logger logger = LoggerFactory.getLogger(HpIndexManagerController.class);
	
	@Autowired
	@Qualifier(value = "hpIndexServiceImpl")
	private HpIndexService hpIndexServiceImpl;
	
	@Autowired
	@Qualifier(value = "hpManageServiceImpl")
	private HpManageService hpManageServiceImpl;
	
	private ResultModel resultModel = null;
	
	@ExceptionHandler(SdxException.class)
	public @ResponseBody ResultModel handleCustomException(SdxException ex) {
		ResultModel resultModel = new ResultModel();
		resultModel.setResultCode(ex.getErrCode());
		resultModel.setMessage(ex.getErrMsg());
		resultModel.setSuccess(false);
		return resultModel;
	}
	
	@RequestMapping(value = "/hpIndex", method = RequestMethod.GET)
	public String userMgr(Locale locale, Model model) {
		return "hpIndex";
	}
	
	@RequestMapping(value = "/getData", method = RequestMethod.GET, produces="application/json")  
	@ResponseBody
	public ListResultModel getDate(HttpServletRequest request,HttpServletResponse response,@RequestParam(required=false) String brand,
			@RequestParam(required=false) String color,@RequestParam(required=false) String size,@RequestParam(required=false) String category,
			@RequestParam int iDisplayLength, @RequestParam int iDisplayStart,@RequestParam String sEcho, @RequestParam(required=false) String hp_num){
		
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		ListResultModel listResultModel = new ListResultModel();
		try{
			if (hp_num == null || hp_num.equals("")){
				List<String> tagList = new ArrayList<>();
				
				if (brand != null && !brand.equals("")){
					String[] brandArray = brand.split("\\,");
					String prefix = RedisVariableUtil.BRAND_TAG_PREFIX + RedisVariableUtil.DIVISION_CHAR;
					Arrays.asList(brandArray).stream().forEach((b)->{
						tagList.add(prefix + b);
					});
				}
				
				if (color != null && !color.equals("")){
					String[] colorArray = color.split("\\,");
					String prefix = RedisVariableUtil.COLOR_TAG_PREFIX + RedisVariableUtil.DIVISION_CHAR;
					Arrays.asList(colorArray).stream().forEach((b)->{
						tagList.add(prefix + b);
					});
				}
				
				if (size != null && !size.equals("")){
					String[] sizeArray = size.split("\\,");
					String prefix = RedisVariableUtil.SIZE_TAG_PREFIX + RedisVariableUtil.DIVISION_CHAR;
					Arrays.asList(sizeArray).stream().forEach((b)->{
						tagList.add(prefix + b);
					});
				}
				
				if (category != null && !category.equals("")){
					String[] categoryArray = category.split("\\,");
					String prefix = RedisVariableUtil.CATEGORY_TAG_PREFIX + RedisVariableUtil.DIVISION_CHAR;
					Arrays.asList(categoryArray).stream().forEach((b)->{
						tagList.add(prefix + b);
					});
				}

				HPListModel resultData = hpIndexServiceImpl.getIndexDataByTag(tagList,iDisplayStart,iDisplayLength);
				
				listResultModel.setAaData(resultData.getList());
				listResultModel.setsEcho(sEcho);
				listResultModel.setiTotalRecords((int)resultData.getCount());
				listResultModel.setiTotalDisplayRecords((int)resultData.getCount());
				listResultModel.setSuccess(true);
			}
			else{
				HPModel hpModel = hpManageServiceImpl.getHpDataByHpNum(hp_num);
				
				List<HPModel> list = new ArrayList<>(1);
				if (hpModel != null){
					list.add(hpModel);
					listResultModel.setiTotalRecords(1);
					listResultModel.setiTotalDisplayRecords(1);
				}
				else{
					listResultModel.setiTotalRecords(0);
					listResultModel.setiTotalDisplayRecords(0);
				}
				
				listResultModel.setAaData(list);
				listResultModel.setsEcho(sEcho);
				
				listResultModel.setSuccess(true);
			}
			
		}
		
		catch(Exception e){
			listResultModel.setSuccess(false);
		}
		return listResultModel;
	}
	
}
