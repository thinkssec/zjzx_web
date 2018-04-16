/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import com.ckfinder.connector.ConnectorServlet;

/**
 * CKFinderConnectorServlet
 * @author ThinkGem
 * @version 2014-06-25
 */
public class CKFinderConnectorServlet extends ConnectorServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		prepareGetResponse(request, response, false);
		super.doGet(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		prepareGetResponse(request, response, true);
		super.doPost(request, response);
	}
	
	private void prepareGetResponse(final HttpServletRequest request,
			final HttpServletResponse response, final boolean post) throws ServletException {
		Principal principal = (Principal) UserUtils.getPrincipal();
		if (principal == null){
			return;
		}
		String relationId=principal.getRelationId();

		String command = request.getParameter("command");
		String type = request.getParameter("type");
		// 初始化时，如果startupPath文件夹不存在，则自动创建startupPath文件夹
		if ("Init".equals(command)){
			request.setAttribute("path",type);
			//System.out.println("-------"+type);
			String startupPath = request.getParameter("startupPath");// 当前文件夹可指定为模块名
			String path="";
			if (startupPath!=null){
				String[] ss = startupPath.split(":");
				if (ss.length==2){
					String realPath = Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL
							+ principal + "/" +relationId+"/"+ ss[0] + ss[1];
					//path=realPath;
					FileUtils.createDirectory(FileUtils.path(realPath));
				}
			}
			System.out.println("---------------------"+type);
			if("software".equals(type)){
				path=Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL
						+ principal +"/"+relationId+ "/software";
				FileUtils.createDirectory(FileUtils.path(path+"/bin"));
				FileUtils.createDirectory(FileUtils.path(path+"/lib"));
				FileUtils.createDirectory(FileUtils.path(path+"/com"));
			}else if("data".equals(type)){
				path=Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL
						+ principal +"/" +relationId+ "/data";
				FileUtils.createDirectory(FileUtils.path(path+"/单项"));
				FileUtils.createDirectory(FileUtils.path(path+"/单位"));
				FileUtils.createDirectory(FileUtils.path(path+"/分项"));
				FileUtils.createDirectory(FileUtils.path(path+"/设备"));
				FileUtils.createDirectory(FileUtils.path(path+"/主材"));
			}
		}
		// 快捷上传，自动创建当前文件夹，并上传到该路径
		else if ("QuickUpload".equals(command) && type!=null){
			String currentFolder = request.getParameter("currentFolder");// 当前文件夹可指定为模块名
			String realPath = Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL
					+ principal + "/" +relationId+ "/"+ type + (currentFolder != null ? currentFolder : "");
			FileUtils.createDirectory(FileUtils.path(realPath));
		}
//		System.out.println("------------------------");
//		for (Object key : request.getParameterMap().keySet()){
//			System.out.println(key + ": " + request.getParameter(key.toString()));
//		}
	}
	
}
