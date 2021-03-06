package demo.tool.fakeFTP.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import demo.tool.fakeFTP.pojo.param.controllerParam.DownloadTargetFileParam;
import demo.tool.fakeFTP.pojo.param.controllerParam.GetFilePathDetailParam;
import demo.tool.fakeFTP.pojo.result.FakeFTPFilePathDetailResult;
import demo.tool.pojo.result.UploadResult;

public interface FakeFTPServcie {

	FakeFTPFilePathDetailResult GetFilePathDetail(GetFilePathDetailParam param);

	void downloadTargetFile(HttpServletResponse response, DownloadTargetFileParam param) throws IOException;

	UploadResult fakeFTPuploadFile(MultipartHttpServletRequest request, HttpServletResponse response);

}
