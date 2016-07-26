package com.cidic.sdx.controller;

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
import com.cidic.sdx.model.HPModel;
import com.cidic.sdx.model.ResultModel;
import com.cidic.sdx.service.HpIndexService;
import com.cidic.sdx.util.WebRequestUtil;

@Controller
@RequestMapping("/hpIndexManage")
public class HpIndexManagerController {

	private static final Logger logger = LoggerFactory.getLogger(HpIndexManagerController.class);
	
	@Autowired
	@Qualifier(value = "hpIndexServiceImpl")
	private HpIndexService hpIndexServiceImpl;
	
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
	
	@RequestMapping(value = "/getData", method = RequestMethod.GET)  
	@ResponseBody
	public ResultModel getDate(HttpServletRequest request,HttpServletResponse response,@RequestParam(required=false) String brand,
			@RequestParam(required=false) String color,@RequestParam(required=false) String size,@RequestParam(required=false) String category){
		
		WebRequestUtil.AccrossAreaRequestSet(request, response);
	    
		try{
			String[] brandArray = 
			List<HPModel> resultData = hpIndexServiceImpl.getIndexDataByTag(tagList)
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
			resultModel.setObject(resultData);
		}
		
		catch(Exception e){
			throw new SdxException(500, "获取数据失败");
		}
		return resultModel;
	}
}
