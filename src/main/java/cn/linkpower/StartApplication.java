package cn.linkpower;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartApplication {
	private static Logger log = LoggerFactory.getLogger(StartApplication.class);
	
	public static void main(String[] args) {
		try {
			SpringApplication.run(StartApplication.class, args);
		} catch (Exception e) {
			log.info("----cn.linkpower.StartApplication----"+String.valueOf(e));
		}
	}
}
