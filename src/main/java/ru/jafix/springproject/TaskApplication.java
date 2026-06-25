package ru.jafix.springproject;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class TaskApplication {

	static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
	}

}
