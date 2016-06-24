/**
 * 
 */
package org.smart4j.framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ConfigHelper;
import org.smart4j.framework.helper.ControllerHelper;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.CodecUtil;
import org.smart4j.framework.util.JsonUtil;
import org.smart4j.framework.util.ReflectionUtil;
import org.smart4j.framework.util.StreamUtil;
import org.smart4j.framework.util.StringUtil;

/**
 * @author Simon Shen 2016-6-23
 */
public class DispatcherServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3021893609587075837L;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		// 初始化
		HelperLoader.init();
		// 获取servletContext对象
		ServletContext servletContext = servletConfig.getServletContext();
		// 注册处理JSPservlet
		ServletRegistration jspServlet = servletContext
				.getServletRegistration("jsp");

		jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

		// 注册处理静态资源
		ServletRegistration defaultServlet = servletContext
				.getServletRegistration("default");

		defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
	}

	@Override
	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// 获取请求的方和请求的路径
		String requestMethod = req.getMethod().toLowerCase();
		String requestPath = req.getPathInfo();
		Handler handler = ControllerHelper.getHandler(requestMethod,
				requestPath);
		if (handler != null) {
			// 获取controller类和bean实例
			Class<?> controllerClass = handler.getControllerClass();
			Object controllerBean = BeanHelper.getBean(controllerClass);
			// 创建请求参数和对象
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Enumeration<String> paramNames = req.getParameterNames();
			while (paramNames.hasMoreElements()) {
				String paramName = paramNames.nextElement();
				String paramValue = req.getParameter(paramName);
				paramMap.put(paramName, paramValue);
			}
			String body = CodecUtil.decodeURL(StreamUtil.getString(req
					.getInputStream()));
			if (StringUtil.isNotEmpty(body)) {
				String[] params = StringUtil.splitString(body, "&");
				if (ArrayUtil.isNotEmpty(params)) {
					for (String param : params) {
						String array[] = StringUtil.splitString(param, "=");
						if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
							String paramName = array[0];
							String paramValue = array[1];
							paramMap.put(paramName, paramValue);
						}
					}
				}
			}
			Param param = new Param(paramMap);
			// 调用action 方法
			Method actionMethod = handler.getActionMethod();
			Object result = ReflectionUtil.invokeMethod(controllerBean,
					actionMethod, param);
			if (result instanceof View) {
				// 返回jsp页面
				View view = (View) result;
				String path = view.getPath();
				if (StringUtil.isNotEmpty(path)) {
					if (path.startsWith("/")) {
						res.sendRedirect(req.getContextPath() + path);
					} else {
						Map<String, Object> model = view.getModel();
						// 设置model键值对request中
						for (Map.Entry<String, Object> entry : model.entrySet()) {
							req.setAttribute(entry.getKey(), entry.getValue());
						}
						req.getRequestDispatcher(
								ConfigHelper.getAppJspPath() + path).forward(
								req, res);
					}
				}
			} else if (result instanceof Data) {
				// 返回JSON数据
				Data data = (Data) result;
				Object model = data.getModel();
				if (model != null) {
					res.setContentType("application/json");
					res.setCharacterEncoding("UTF-8");
					PrintWriter writer = res.getWriter();
					String json = JsonUtil.toJson(model);
					writer.write(json);
					writer.flush();
					writer.close();
				}
			}
		}

	}
}
