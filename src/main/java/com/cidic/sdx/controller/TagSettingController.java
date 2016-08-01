package com.cidic.sdx.controller;

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
import com.cidic.sdx.model.ResultModel;
import com.cidic.sdx.service.TagService;
import com.cidic.sdx.util.WebRequestUtil;

@Controller
@RequestMapping("/tag")
public class TagSettingController {

	private static final Logger logger = LoggerFactory.getLogger(TagSettingController.class);
	
	@Autowired
	@Qualifier(value = "tagServiceImpl")
	private TagService tagServiceImpl;
	
	private ResultModel resultModel = null;
	
	@ExceptionHandler(SdxException.class)
	public @ResponseBody ResultModel handleCustomException(SdxException ex) {
		ResultModel resultModel = new ResultModel();
		resultModel.setResultCode(ex.getErrCode());
		resultModel.setMessage(ex.getErrMsg());
		resultModel.setSuccess(false);
		return resultModel;
	}
	
	@RequestMapping(value = "/tagMgr", method = RequestMethod.GET)
	public String userMgr(Locale locale, Model model) {
		return "tagMgr";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)  
	@ResponseBody
	public ResultModel updateTag(HttpServletRequest request,HttpServletResponse response,@RequestParam String hp_id,@RequestParam String pre_id,
			@RequestParam String new_id,@RequestParam String type){
		WebRequestUtil.AccrossAreaRequestSet(request, response);
		
		try{
			if (type.equals("brand")){
				tagServiceImpl.updateBrandTag(hp_id, pre_id, new_id);
			}
			else if (type.equals("category")){
				tagServiceImpl.updateCategoryTag(hp_id, pre_id, new_id);
			}
			else if (type.equals("color")){
				tagServiceImpl.updateColorTag(hp_id, pre_id, new_id);
			}
			else if (type.equals("size")){
				tagServiceImpl.updateSizeTag(hp_id, pre_id, new_id);
			}
			
			resultModel = new ResultModel();
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
		}
		
		catch(Exception e){
			throw new SdxException(500, "写入数据失败");
		}
		return resultModel;
		
	}
}
