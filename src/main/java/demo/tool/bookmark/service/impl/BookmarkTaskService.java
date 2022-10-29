package demo.tool.bookmark.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.tool.bookmark.service.BookmarkService;

@Component
public class BookmarkTaskService {

	@Autowired
	private BookmarkService service;

	@Scheduled(cron = "30 29 03 * * *")
	public void reBalanceWeight() {
		service.reBalanceWeight();
	}
}
