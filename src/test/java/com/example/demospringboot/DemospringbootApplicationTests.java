package com.example.demospringboot;

import com.example.demospringboot.bean.User;
import com.example.demospringboot.task.AsyncTasks;
import com.example.demospringboot.dao.UserMapper;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.util.concurrent.CompletableFuture;
import org.springframework.cache.CacheManager;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(value = false)
public class DemospringbootApplicationTests {
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private CacheManager cacheManager;

	@Test
	public void testUserMapper() throws Exception {
		// deleteAllUsers
		userMapper.deleteAllUsers();

		// insertUser 插入2条
		User user = new User();
		user.setId(100);
		user.setUsername("Jacky");
		user.setPassword("1000");
		userMapper.insertUser(user);
		user.setId(200);
		user.setUsername("Mike");
		user.setPassword("2000");
		userMapper.insertUser(user);

		// findUserById
		user = userMapper.findUserById(100);
		Assert.assertEquals("Jacky", user.getUsername());

		// updateUserPassword
		user.setPassword("1500");
		userMapper.updateUserPassword(user);
		Assert.assertEquals("1500", user.getPassword());

		// deleteUserById
		userMapper.deleteUserById(100);

		// findAllUsers
		List<User> AllUsers = userMapper.findAllUsers();
		for (User u : AllUsers) {
			System.out.println(u);
		}
		//Assert.assertEquals(1, AllUsers.size());

		System.out.println("CacheManager type : " + cacheManager.getClass());

	}
}


