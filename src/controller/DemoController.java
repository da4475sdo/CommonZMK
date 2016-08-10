package controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dao.HibernateDao;
import entity.demo.Demo;

@RestController
public class DemoController {
	@Autowired
	private HibernateDao dao;
	
	@RequestMapping(path="/demo",method=RequestMethod.POST)
	public String demoPut(String message){
		Demo demo =new Demo();
		demo.setDemoID("111");
		demo.setName("zmk");
		return "hello World"+dao.save(demo,Demo.class);
	}
}
