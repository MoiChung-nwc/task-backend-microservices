package com.chung.tasksubmission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TasksubmissionApplication {

	public static void main(String[] args) {
		SpringApplication.run(TasksubmissionApplication.class, args);
	}

}
