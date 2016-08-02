package com.cidic.sdx.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cidic.sdx.dao.HpManageDao;
import com.cidic.sdx.model.HPListModel;
import com.cidic.sdx.model.HPModel;
import com.cidic.sdx.service.HpManageService;
import com.cidic.sdx.util.UploadVo;

@Service
@Component
@Qualifier(value = "hpManageServiceImpl")
public class HpManageServiceImpl implements HpManageService {

	@Autowired
	@Qualifier(value = "hpManageDaoImpl")
	private HpManageDao hpManageDaoImpl;

	@Override
	public HPListModel getHpData(int iDisplayStart,int iDisplayLength) {

		return hpManageDaoImpl.getHpData(iDisplayStart, iDisplayLength);
	}

	@Override
	public void insertHpData(HPModel hpModel) {
		hpManageDaoImpl.insertHpData(hpModel);
	}

	@Override
	public void updateHpData(HPModel hpModel) {
		hpManageDaoImpl.updateHpData(hpModel);
	}

	@Override
	public void deleteHpData(String id) {
		hpManageDaoImpl.deleteHpData(id);
	}

	@Override
	public HPModel getHpDataById(int id) {
		// TODO Auto-generated method stub
		return hpManageDaoImpl.getHpDataById(id);
	}

	@Override
	public boolean uploadForm(UploadVo uploadVo) throws Exception {
		/// usr/local/project/sdx_hpgl
		uploadVo.validateFile();
		uploadFile("E:/test", uploadVo.getImgFile(), uploadVo.getImgFile().getOriginalFilename());
		return true;
	}

	private boolean uploadFile(String destinationDir, MultipartFile file, String filename) throws Exception {
		System.out.println("文件大小: " + file.getSize());
		System.out.println("文件类型: " + file.getContentType());
		System.out.println("文件名称: " + file.getName());
		System.out.println("文件名称: " + file.getOriginalFilename());
		System.out.println("========================================");
		try {
			SaveFileFromInputStream(file.getInputStream(), destinationDir, filename);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * @param stream
	 * @param path
	 * @param filename
	 * @throws IOException
	 */
	private void SaveFileFromInputStream(InputStream stream, String path, String filename) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(path + "/" + filename);
		int byteCount = 0;
		byte[] bytes = new byte[1024];
		while ((byteCount = stream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, byteCount);
		}
		outputStream.close();
		stream.close();
	}

	@Override
	public HPModel getHpDataByHpNum(String hp_num) {
		// TODO Auto-generated method stub
		return hpManageDaoImpl.getHpDataByHpNum(hp_num);
	}

}
