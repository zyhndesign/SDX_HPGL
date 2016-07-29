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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cidic.sdx.exception.SdxException;
import com.cidic.sdx.model.HPModel;
import com.cidic.sdx.model.ResultModel;
import com.cidic.sdx.service.HpManageService;
import com.cidic.sdx.util.UploadVo;
import com.cidic.sdx.util.WebRequestUtil;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

@Controller
@RequestMapping("/hpManage")
public class HpManageController {

	private static final Logger logger = LoggerFactory.getLogger(BrandSettingController.class);

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

	@RequestMapping(value = "/productMgr", method = RequestMethod.GET)
	public String userMgr(Locale locale, Model model) {
		return "productMgr";
	}

	@RequestMapping(value = "/productCOU", method = RequestMethod.GET)
	public ModelAndView getProductCOU(HttpServletRequest request) {

		ModelAndView view = new ModelAndView();
		view.setViewName("/productCOU");
		return view;
	}

	@RequestMapping(value = "/productCOU/{id}", method = RequestMethod.GET)
	public ModelAndView updateProductCOU(HttpServletRequest request, @PathVariable int id) {

		HPModel hpModel = null;
		if (id > 0) {
			hpModel = hpManageServiceImpl.getHpDataById(id);
		}
		ModelAndView view = new ModelAndView();
		view.setViewName("/productCOU");
		view.addObject("hp", hpModel);
		return view;
	}

	@RequestMapping(value = "/getData", method = RequestMethod.GET)
	@ResponseBody
	public ResultModel getDate(HttpServletRequest request, HttpServletResponse response, @RequestParam int pageNum,
			@RequestParam int limit) {

		WebRequestUtil.AccrossAreaRequestSet(request, response);

		try {
			List<HPModel> resultData = hpManageServiceImpl.getHpData(pageNum, limit);
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
			resultModel.setObject(resultData);
		}

		catch (Exception e) {
			throw new SdxException(500, "鑾峰彇鏁版嵁澶辫触");
		}
		return resultModel;
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel insert(HttpServletRequest request, HttpServletResponse response, @RequestParam String hp_num,
			@RequestParam String brand, @RequestParam String category, @RequestParam String size,
			@RequestParam String color, @RequestParam float price, @RequestParam String imageUrl1,
			@RequestParam String imageUrl2, @RequestParam String imageUrl3) {

		WebRequestUtil.AccrossAreaRequestSet(request, response);

		try {
			HPModel hpModel = new HPModel();
			hpModel.setHp_num(hp_num);
			hpModel.setBrand(brand);
			hpModel.setCategory(category);
			hpModel.setSize(size);
			hpModel.setColor(color);
			hpModel.setPrice(price);
			hpModel.setImageUrl1(imageUrl1);
			hpModel.setImageUrl2(imageUrl2);
			hpModel.setImageUrl3(imageUrl3);
			hpManageServiceImpl.insertHpData(hpModel);
			resultModel = new ResultModel();
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new SdxException(500, "鍐欏叆鏁版嵁澶辫触");
		}
		return resultModel;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel update(HttpServletRequest request, HttpServletResponse response, @RequestParam String id,
			@RequestParam String hp_num, @RequestParam float price, @RequestParam String imageUrl1,
			@RequestParam String imageUrl2, @RequestParam String imageUrl3) {

		WebRequestUtil.AccrossAreaRequestSet(request, response);

		try {
			HPModel hpModel = new HPModel();
			hpModel.setId(Integer.parseInt(id));
			hpModel.setHp_num(hp_num);
			hpModel.setPrice(price);
			hpModel.setImageUrl1(imageUrl1);
			hpModel.setImageUrl2(imageUrl2);
			hpModel.setImageUrl3(imageUrl3);
			hpManageServiceImpl.updateHpData(hpModel);
			resultModel = new ResultModel();
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
		}

		catch (Exception e) {
			throw new SdxException(500, "鏇存柊鏁版嵁澶辫触");
		}
		return resultModel;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel delete(HttpServletRequest request, HttpServletResponse response, @RequestParam String id) {

		WebRequestUtil.AccrossAreaRequestSet(request, response);

		try {
			hpManageServiceImpl.deleteHpData(id);
			resultModel = new ResultModel();
			resultModel.setResultCode(200);
			resultModel.setSuccess(true);
		}

		catch (Exception e) {
			throw new SdxException(500, "鍒犻櫎鏁版嵁澶辫触");
		}
		return resultModel;
	}

	@RequestMapping(value = "/getUploadKey", method = RequestMethod.GET)
	@ResponseBody
	public ResultModel getUploadKey(HttpServletRequest request) {

		final String ACCESS_KEY = "Q-DeiayZfPqA0WDSOGSf-ekk345VrzuZa_6oBrX_";
		final String SECRET_KEY = "fIiGiRr3pFmHOmBDR2Md1hTCqpMMBcE_gvZYMzwD";
		final String bucketname = "sdx-hpgl";
		try {
			StringMap strMap = new StringMap().putNotNull("returnBody",
					"{\"key\": $(key), \"hash\": $(etag), \"w\": $(imageInfo.width), \"h\": $(imageInfo.height)}");
			Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

			String token = auth.uploadToken(bucketname, null, 3600, strMap);

			resultModel = new ResultModel();
			resultModel.setResultCode(200);
			resultModel.setUptoken(token);
		} catch (Exception e) {
			throw new SdxException(500, "鑾峰彇Token鍑洪敊");
		}

		return resultModel;
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public Object firstUpload(HttpServletRequest request, UploadVo demo) {
		logger.info("firstUpload info:" + demo.toString());
		boolean flag = false;
		// errorMessage：上传失败，则是错误信息；上传成功，则提示成功以及显示文件上传后的地址
		String errorMessage = "";
		try {
			flag = hpManageServiceImpl.uploadForm(demo);
			errorMessage += "文件地址："
					+ demo.getImgFile().getOriginalFilename();
		} catch (SdxException serviceE) {
			logger.error("firstUpload failed!", serviceE);
			errorMessage = serviceE.getMessage();
		} catch (Exception e) {
			logger.error("firstUpload failed!", e);
			errorMessage = "新增文件失败!";
		}
		if (flag) {
			// 上传成功，返回到前台，调用uploadSucced()这个方法
			return "<script>window.parent.uploadSucced('" + errorMessage + "');</script>";
		}
		// 上传失败，返回到前台，调用uploadFailed()这个方法
		return "<script>window.parent.uploadFailed('" + errorMessage + "');</script>";
	}
}
