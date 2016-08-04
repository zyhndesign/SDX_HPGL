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
import com.cidic.sdx.model.CategoryModel;
import com.cidic.sdx.model.ResultModel;
import com.cidic.sdx.service.CategoryService;
import com.cidic.sdx.util.WebRequestUtil;

@Controller
@RequestMapping("/category")
public class CategorySettingController {
	private static final Logger logger = LoggerFactory.getLogger(CategorySettingController.class);
	
	@Autowired
	@Qualifier(value = "categoryServiceImpl")
	private CategoryService categoryServiceImpl;
	
	private ResultModel resultModel = null;
	
	@ExceptionHandler(SdxException.class)
	public @ResponseBody ResultModel handleCustomException(SdxException ex) {
		ResultModel resultModel = new ResultModel();
		resultModel.setResultCode(ex.getErrCode());
		resultModel.setMessage(ex.getErrMsg());
		resultModel.setSuccess(false);
		return resultModel;
	}
	
	@RequestMapping(value = "/categoryMgr", method = RequestMethod.GET)
	public String userMgr(Locale locale, Model model) {
		return "categoryMgr";
	}
	
	@RequestMapping(value = "/getData", method = RequestMethod.GET)  
	@ResponseBody
	public ResultModel getDate(HttpServletRequest request,HttpServletResponse response,@RequestParam(required=false) String id){
		
		WebRequestUtil.AccrossAreaRequestSet(request, response);
	    
		try{
			resultModel = new ResultModel();
			if (id == null){
				List<CategoryModel> list = new ArrayList<>();
				CategoryModel categoryModel = new CategoryModel();
				categoryModel.setId(0);
				categoryModel.setName("品类");
				list.add(categoryModel);
				resultModel.setObject(list);
			}
			else{
				
				List<CategoryModel> list = categoryServiceImpl.getCategoryData(id);
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
			Long back_id = categoryServiceImpl.insertCategoryData(id, name);
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
			categoryServiceImpl.updateCategoryData(parentId, id, name);
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
			categoryServiceImpl.deleteCategoryData(parentId, id);
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
