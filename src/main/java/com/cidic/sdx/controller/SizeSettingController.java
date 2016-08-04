package com.cidic.sdx.controller;

import java.util.ArrayList;
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
import com.cidic.sdx.model.BrandModel;
import com.cidic.sdx.model.ResultModel;
import com.cidic.sdx.model.SizeModel;
import com.cidic.sdx.service.SizeService;
import com.cidic.sdx.util.WebRequestUtil;

@Controller
@RequestMapping("/size")
public class SizeSettingController {
	
	private static final Logger logger = LoggerFactory.getLogger(SizeSettingController.class);
	
	@Autowired
	@Qualifier(value = "sizeServiceImpl")
	private SizeService sizeServiceImpl;
	
	private ResultModel resultModel = null;
	
	@ExceptionHandler(SdxException.class)
	public @ResponseBody ResultModel handleCustomException(SdxException ex) {
		ResultModel resultModel = new ResultModel();
		resultModel.setResultCode(ex.getErrCode());
		resultModel.setMessage(ex.getErrMsg());
		resultModel.setSuccess(false);
		return resultModel;
	}
	
	@RequestMapping(value = "/sizeMgr", method = RequestMethod.GET)
	public String userMgr(Locale locale, Model model) {
		return "sizeMgr";
	}
	
	@RequestMapping(value = "/getData", method = RequestMethod.GET)  
	@ResponseBody
	public ResultModel getDate(HttpServletRequest request,HttpServletResponse response,@RequestParam(required=false) String id){
		
		WebRequestUtil.AccrossAreaRequestSet(request, response);
	    
		try{
			resultModel = new ResultModel();
			if (id == null){
				List<SizeModel> list = new ArrayList<>();
				SizeModel sizeModel = new SizeModel();
				sizeModel.setId(0);
				sizeModel.setName("尺码");
				list.add(sizeModel);
				resultModel.setObject(list);
			}
			else{
				
				List<SizeModel> list = sizeServiceImpl.getSizeData(id);
				resultModel.setObject(list);
			}
			
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
		}
		
		catch(Exception e){
			throw new SdxException(500, "获取数据失败");
		}
		return resultModel;
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)  
	@ResponseBody
	public ResultModel insert(HttpServletRequest request,HttpServletResponse response,@RequestParam String id,@RequestParam String name){
		
		WebRequestUtil.AccrossAreaRequestSet(request, response);
	    
		try{
			Long back_id = sizeServiceImpl.insertSizeData(id, name);
			resultModel = new ResultModel();
			resultModel.setResultCode(200);
			resultModel.setObject(back_id);
			resultModel.setSuccess(true);
		}
		
		catch(Exception e){
			throw new SdxException(500, "写入数据失败");
		}
		return resultModel;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)  
	@ResponseBody
	public ResultModel update(HttpServletRequest request,HttpServletResponse response,@RequestParam String parentId,@RequestParam String id,@RequestParam String name){
		
		WebRequestUtil.AccrossAreaRequestSet(request, response);
	    
		try{
			sizeServiceImpl.updateSizeData(parentId, id, name);
			resultModel = new ResultModel();
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
		}
		
		catch(Exception e){
			throw new SdxException(500, "更新数据失败");
		}
		return resultModel;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)  
	@ResponseBody
	public ResultModel delete(HttpServletRequest request,HttpServletResponse response,@RequestParam String id,@RequestParam String parentId){
		
		WebRequestUtil.AccrossAreaRequestSet(request, response);
	    
		try{
			sizeServiceImpl.deleteSizeData(parentId, id);
			resultModel = new ResultModel();
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
		}
		
		catch(Exception e){
			throw new SdxException(500, "删除数据失败");
		}
		return resultModel;
	}
}
