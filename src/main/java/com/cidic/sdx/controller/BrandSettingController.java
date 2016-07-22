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
import com.cidic.sdx.model.BrandModel;
import com.cidic.sdx.model.ResultModel;
import com.cidic.sdx.service.BrandService;

@Controller
@RequestMapping("/brand")
public class BrandSettingController {

	private static final Logger logger = LoggerFactory.getLogger(BrandSettingController.class);
	
	@Autowired
	@Qualifier(value = "brandServiceImpl")
	private BrandService brandServiceImpl;
	
	private ResultModel resultModel = null;
	
	@ExceptionHandler(SdxException.class)
	public @ResponseBody ResultModel handleCustomException(SdxException ex) {
		ResultModel resultModel = new ResultModel();
		resultModel.setResultCode(ex.getErrCode());
		resultModel.setMessage(ex.getErrMsg());
		return resultModel;
	}
	
	@RequestMapping(value = "/brandMgr", method = RequestMethod.GET)
	public String userMgr(Locale locale, Model model) {
		return "boardMgr";
	}
	
	@RequestMapping(value = "/getDate", method = RequestMethod.POST)  
	@ResponseBody
	public ResultModel getDate(HttpServletRequest request,HttpServletResponse response,@RequestParam String id){
		
		response.setContentType("text/html;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin","*");
	    if("IE".equals(request.getParameter("type"))){
	    	response.addHeader("XDomainRequestAllowed","1");
	    }
	    
		try{
			resultModel = new ResultModel();
			if (id == null){
				BrandModel brandModel = new BrandModel();
				brandModel.setId(0);
				brandModel.setBroadName("圣德西");
				resultModel.setObject(brandModel);
			}
			else{
				List<BrandModel> list = brandServiceImpl.getBoardDate(id);
				resultModel.setObject(list);
			}
			
			resultModel.setResultCode(200);
			
		}
		
		catch(Exception e){
			throw new SdxException(500, "获取数据出错");
		}
		return resultModel;
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)  
	@ResponseBody
	public ResultModel insert(HttpServletRequest request,HttpServletResponse response,@RequestParam String id,@RequestParam String name){
		
		response.setContentType("text/html;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin","*");
	    if("IE".equals(request.getParameter("type"))){
	    	response.addHeader("XDomainRequestAllowed","1");
	    }
	    
		try{
			brandServiceImpl.insertBoardData(id, name);
			resultModel = new ResultModel();
			resultModel.setResultCode(200);
		}
		
		catch(Exception e){
			throw new SdxException(500, "获取数据出错");
		}
		return resultModel;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)  
	@ResponseBody
	public ResultModel update(HttpServletRequest request,HttpServletResponse response,@RequestParam String parentId,@RequestParam String id,@RequestParam String name){
		
		response.setContentType("text/html;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin","*");
	    if("IE".equals(request.getParameter("type"))){
	    	response.addHeader("XDomainRequestAllowed","1");
	    }
	    
		try{
			brandServiceImpl.updateBoardData(parentId, id, name);
			resultModel = new ResultModel();
			resultModel.setResultCode(200);
		}
		
		catch(Exception e){
			throw new SdxException(500, "获取数据出错");
		}
		return resultModel;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)  
	@ResponseBody
	public ResultModel delete(HttpServletRequest request,HttpServletResponse response,@RequestParam String id,@RequestParam String parentId){
		
		response.setContentType("text/html;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin","*");
	    if("IE".equals(request.getParameter("type"))){
	    	response.addHeader("XDomainRequestAllowed","1");
	    }
	    
		try{
			brandServiceImpl.deleteBoardData(parentId, id);
			resultModel = new ResultModel();
			resultModel.setResultCode(200);
		}
		
		catch(Exception e){
			throw new SdxException(500, "获取数据出错");
		}
		return resultModel;
	}
}
