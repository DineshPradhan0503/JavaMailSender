package com.bluepal.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bluepal.service.IPurchaseMgmtService;

@Component
public class SpringBootMailTestRunner implements CommandLineRunner {
	@Autowired
	private IPurchaseMgmtService purchaseService;

	@Override
	public void run(String... args) throws Exception {
		try {
			String resultMsg = purchaseService.shopping(new String[] { "shirt", "trouser", "hat" },
					new Double[] { 4000.0, 5000.0, 3000.0 }, new String[] { "dineshpradhan0503@gmail.com",
							"dineshpradhankanha143@gmail.com", "dpradhan2021@gift.edu.in" });
			System.out.println("Result Message: " + resultMsg);
			System.out.println("Mail sent successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
