package com.sds.semipj;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FcmController {
	String id = null;

	@RequestMapping(value = "/fcm")
	public void fcmTest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("hi fcm");

		String ip = request.getParameter("ip");
		String speed = request.getParameter("speed");

		System.out.println("ip : " + ip);
		System.out.println("speed : " + speed);

		URL url = new URL("https://fcm.googleapis.com/fcm/send");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);

		conn.setRequestProperty("Authorization",
				"key=key");

		JSONObject json = new JSONObject();
		json.put("to",
				"token");

		JSONObject info = new JSONObject();
		info.put("title", ip);
		info.put("body", speed);

		json.put("notification", info);

		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
		out.write(json.toString());
		out.flush();
		conn.getInputStream();

	}

	@RequestMapping("/fcmweb2")
	public void fcmweb2(Model model, HttpServletRequest request, HttpServletResponse response) {

		System.out.println("fcmweb2 µé¿È");
		String reid = request.getParameter("ip");
		System.out.println(reid);
		if (reid != null) {
			id = reid;
		}

		if (id != null) {

			try {
				PrintWriter out = response.getWriter();
				// String reid = request.getParameter("ip");

			

				JSONArray ja = new JSONArray();
				JSONObject jo = new JSONObject();
				jo.put("id", id);

				ja.add(jo);
				out.print(ja.toJSONString());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@RequestMapping("/fcmweb")
	public String fcmweb2(Model model) {

		System.out.println("fcmweb");

		return "fcmweb";
	}
}
