package com.excilys.computerdatabase.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.computerdatabase.om.Computer;
import com.excilys.computerdatabase.om.Page;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.util.Order;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private ComputerService computerService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				getServletContext());
	}

	public void showErrorMsg(HttpServletRequest request) {
		String errorMsg = "An error has occured while treating your request. Please, try again.";
		request.setAttribute("errorMsg", errorMsg);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		boolean delete;
		Page<Computer> wrapper;
		Boolean error = false, asc = true;
		Order order = null;
		String search = null;
		int searchDomain = 0, limit = 10, page = 1;
		Cookie[] cookies = request.getCookies();
		Map<String, String> cookieMap = new HashMap<String, String>();
		if (cookies != null)
			for (Cookie c : cookies) {
				cookieMap.put(c.getName(), c.getValue());
			}

		if (request.getParameter("delete") != null)
			delete = Boolean.parseBoolean(request.getParameter("delete"));
		else
			delete = false;
		if (delete == true) {
			long computerId = Long
					.parseLong(request.getParameter("computerId"));
			if (!computerService.delete(computerId)) {
				error = true;
				showErrorMsg(request);
			}
		}

		if (request.getParameter("search") != null) {
			search = request.getParameter("search");
			Cookie cookie = new Cookie("search", search);
			cookie.setMaxAge(10 * 60);
			response.addCookie(cookie);
		} else if (cookieMap.containsKey("search")) {
			search = cookieMap.get("search");
		}

		if (request.getParameter("searchDomain") != null
				&& request.getParameter("searchDomain") != "") {
			searchDomain = Integer.parseInt(request
					.getParameter("searchDomain"));
			Cookie cookie = new Cookie("searchDomain",
					Integer.toString(searchDomain));
			response.addCookie(cookie);
		} else if (cookieMap.containsKey("searchDomain")) {
			searchDomain = Integer.parseInt(cookieMap.get("searchDomain"));
		}

		if (request.getParameter("limitation") != null
				&& request.getParameter("limitation") != "") {
			limit = Integer.parseInt(request.getParameter("limitation"));
			Cookie cookie = new Cookie("limit", Integer.toString(limit));
			response.addCookie(cookie);
		} else if (cookieMap.containsKey("limit")) {
			limit = Integer.parseInt(cookieMap.get("limit"));
		}

		if (cookieMap.containsKey("page")) {
			page = Integer.parseInt(cookieMap.get("page"));
		}

		if (request.getParameter("asc") != null
				&& request.getParameter("asc") != "") {
			asc = Boolean.parseBoolean(request.getParameter("asc"));
			Cookie cookie = new Cookie("asc", Boolean.toString(asc));
			response.addCookie(cookie);
		} else if (cookieMap.containsKey("asc")) {
			asc = Boolean.parseBoolean(cookieMap.get("asc"));
		}

		Page.Builder<Computer> cb = new Page.Builder<Computer>();
		wrapper = cb.name(search).searchDomain(searchDomain).limit(limit)
				.page(page).asc(asc).build();

		if (request.getParameter("page") != null
				&& request.getParameter("page") != "") {
			wrapper.setNewPage(Integer.parseInt(request.getParameter("page")));
		}

		if (request.getParameter("order") != null
				&& request.getParameter("order") != "") {
			order = Order.getOrder(request.getParameter("order"));
			Cookie cookie = new Cookie("order", order.toString());
			response.addCookie(cookie);
		} else if (cookieMap.containsKey("order")) {
			order = Order.getOrder(cookieMap.get("order"));
		} else {
			order = Order.NAME;
		}

		try {
			wrapper.setOrder(order);
		} catch (Exception e) {
			System.out.println("Choosen order is impossible");
		}

		if (!computerService.search(wrapper)) {
			error = true;
			showErrorMsg(request);
		}
		if (wrapper.getPage() > 0) {
			page = wrapper.getPage();
			Cookie cookie = new Cookie("page", Integer.toString(page));
			response.addCookie(cookie);
		}
		request.setAttribute("error", error);
		request.setAttribute("wrapper", wrapper);
		request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request,
				response);
	}

}
