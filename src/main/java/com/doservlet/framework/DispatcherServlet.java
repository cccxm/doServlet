package com.doservlet.framework;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.doservlet.framework.bean.Data;
import com.doservlet.framework.bean.DownloadFile;
import com.doservlet.framework.bean.Handler;
import com.doservlet.framework.bean.Param;
import com.doservlet.framework.bean.View;
import com.doservlet.framework.helper.BeanHelper;
import com.doservlet.framework.helper.ConfigHelper;
import com.doservlet.framework.helper.ControllerHelper;
import com.doservlet.framework.helper.RequestHelper;
import com.doservlet.framework.helper.ServletHelper;
import com.doservlet.framework.helper.UploadHelper;
import com.doservlet.framework.util.JsonUtil;
import com.doservlet.framework.util.ReflectionUtils;
import com.doservlet.framework.util.Regex;

@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7942269710631231359L;
	private static final Logger l = LoggerFactory
			.getLogger(DispatcherServlet.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		l.info("框架初始化");
		HelperLoader.init();
		ServletContext servletContext = config.getServletContext();
		l.info("注册JSP");
		// 注册JSP的servlet
		ServletRegistration jspServlet = servletContext
				.getServletRegistration("jsp");
		jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
		l.info("JSP注册成功");
		l.info("注册静态资源");
		// 注册静态资源默认的servlet
		ServletRegistration defaultServlet = servletContext
				.getServletRegistration("default");
		defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
		l.info("静态资源注册成功");
		l.info("加载文件上传帮助类");
		UploadHelper.init(servletContext);
		l.info("文件上传帮助类加载完成");
		l.info("框架初始化完成");
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletHelper.init(request, response);
		try {

			// 获取请求方法与请求路径
			String requestMethod = request.getMethod().toLowerCase();
			String requestPath = request.getPathInfo();

			if (requestPath.equals("/favicon.ico")) {
				// TDOO
				return;
			}

			l.info("REQUEST IP:" + request.getRemoteAddr() + ";HOST:"
					+ request.getRemoteHost() + ";PORT:"
					+ request.getRemotePort() + ";URI:"
					+ request.getRequestURI() + ";METHOD:" + requestMethod
					+ ";PATH:" + requestPath);// TODO
			// 获取Action处理器
			Handler handler = ControllerHelper.getHandler(requestMethod,
					requestPath);
			if (handler != null) {
				// 映射获取Control类以及其bean实例
				Class<?> controllerClass = handler.getControllerClass();
				Object controllerBean = BeanHelper.getBean(controllerClass);

				Param param;
				if (UploadHelper.isMultipart(request)) {
					param = UploadHelper.createParam(request);
				} else {
					param = RequestHelper.createParam(request);
				}

				// 调用Action方法
				Method actionMethod = handler.getActionMethod();
				Object result = ReflectionUtils.invokeMethod(controllerBean,
						actionMethod, param);

				if (result instanceof View) {
					// 返回JSP页面
					handleViewResult((View) result, request, response);
				} else if (result instanceof Data) {
					// 返回json数据
					handleDataResult((Data) result, request, response);
				} else if (result instanceof DownloadFile) {
					handleFileResult((DownloadFile) result, request, response);
				}
			}
		} finally {
			ServletHelper.destory();
		}
	}

	private void handleFileResult(DownloadFile result,
			HttpServletRequest request, HttpServletResponse response) {
		if (result.getApplication() != null) {
			response.setHeader("content-type",
					"application/" + result.getApplication());
		} else if (result.getAttachment() != null) {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ result.getAttachment());
		} else {
			l.error("not header");
			return;
		}
		response.setContentLength((int) result.getFile().length());
		response.setContentLengthLong(result.getFile().length());
		FileInputStream fis = null;
		ServletOutputStream outputStream = null;
		try {
			fis = new FileInputStream(result.getFile());
			byte[] buff = new byte[1024 * 64];
			int length = -1;
			outputStream = response.getOutputStream();
			while ((length = fis.read(buff)) != -1) {
				outputStream.write(buff, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
			l.error(e.getMessage());
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
					fis = null;
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Exception e) {
					outputStream = null;
				}
			}
		}
	}

	private void handleViewResult(View view, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String path = view.getPath();
		if (Regex.isNotBlank(path)) {
			if (path.startsWith("/")) {
				response.sendRedirect(request.getContextPath() + path);
			} else {
				Map<String, Object> model = view.getModel();
				for (Map.Entry<String, Object> entry : model.entrySet()) {
					request.setAttribute(entry.getKey(), entry.getValue());
				}
				request.getRequestDispatcher(
						ConfigHelper.getAppJspPath() + path).forward(request,
						response);
			}
		}
	}

	private void handleDataResult(Data data, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Object model = data.getModel();
		if (model != null) {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(JsonUtil.toJson(model));
			writer.flush();
			writer.close();
		}
	}
}
