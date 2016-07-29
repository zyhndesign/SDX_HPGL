package com.cidic.sdx.util;

import org.springframework.web.multipart.MultipartFile;

import com.cidic.sdx.exception.SdxException;

public class UploadVo {
	/**
     * 文件
     */
    private MultipartFile imgFile;
 
    public MultipartFile getImgFile() {
        return imgFile;
    }
 
    public void setImgFile(MultipartFile imgFile) {
        this.imgFile = imgFile;
    }
 
    @Override 
    public String toString() {
        return "UploadVo [imgFile=" + imgFile + "]";
    }

    public boolean validateFile() throws SdxException{
        if(!this.getImgFile().getContentType().equals("xls") || !this.getImgFile().getContentType().equals("xlsx ")){
        	throw new SdxException(500, "文件类型只能是xls、xlsx！");
        }
        if(this.getImgFile().getSize() > 1000 * 10000){
            throw new SdxException(500,"文件最大不能超过10M！");
        }
        return true;
    }
}
