package demo.tool.calendarNotice.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.common.service.CommonService;
import demo.tool.calendarNotice.mapper.CalendarNoticeMapper;
import demo.tool.calendarNotice.mq.producer.TelegramCalendarNoticeMessageAckProducer;
import demo.tool.calendarNotice.pojo.dto.AddCalendarNoticeDTO;
import demo.tool.calendarNotice.pojo.po.CalendarNotice;
import demo.tool.calendarNotice.pojo.po.CalendarNoticeExample;
import demo.tool.calendarNotice.service.CalendarNoticeService;
import demo.tool.telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.dto.TelegramMessageDTO;
import toolPack.dateTimeHandle.Lunar;

@Service
public class CalendarNoticeServiceImpl extends CommonService implements CalendarNoticeService {

	@Autowired
	private CalendarNoticeMapper mapper;
	@Autowired
	private TelegramCalendarNoticeMessageAckProducer telegramMessageAckProducer;

	@Override
	public CommonResult addCalendarNotice(AddCalendarNoticeDTO dto) {
		CommonResult r = validDTO(dto);
		if (r.isFail()) {
			return r;
		}
		if(dto.getIsLunarNotice()) {
			dto = handleLunarDateTrans(dto);
		}

		CalendarNotice po = new CalendarNotice();
		po.setId(snowFlake.getNextId());
		po.setIsLunarCalendar(dto.getIsLunarNotice());
		po.setNeedRepeat(dto.getNeedRepeat());
		po.setNoticeContent(dto.getNoticeContent());
		po.setNoticeTime(dto.getNoticeTime());
		po.setRepeatTimeUnit(dto.getRepeatTimeUnit());
		po.setRepeatTimeRange(dto.getRepeatTimeRange());
		po.setValidTime(dto.getValidTime());
		mapper.insertSelective(po);

		r.setIsSuccess();
		return r;
	}

	private CommonResult validDTO(AddCalendarNoticeDTO dto) {
		CommonResult r = new CommonResult();
		if (dto.getNeedRepeat()) {
			if (dto.getRepeatTimeUnit() == null || dto.getRepeatTimeRange() == null) {
				r.addMessage("If need repeat, please fill time unit and time range");
			}
			if (dto.getRepeatTimeRange() <= 0) {
				r.addMessage("Time range must >= 0");
			}
			TimeUnitType timeUnitType = TimeUnitType.getType(dto.getRepeatTimeUnit());
			if (timeUnitType == null || timeUnitType.equals(TimeUnitType.nanoSecond)
					|| timeUnitType.equals(TimeUnitType.milliSecond)) {
				r.addMessage("Invalid time unit");
			}
		}

		if (StringUtils.isBlank(dto.getNoticeContent())) {
			r.addMessage("Please fill notice content");
		}

		if (dto.getValidTime() != null) {
			if (dto.getValidTime().isBefore(LocalDateTime.now())) {
				r.addMessage("Can NOT set a notifications valid time in the past");
			}
		}

		if (dto.getIsLunarNotice()) {
			Lunar lunarTargetDay = new Lunar();
			lunarTargetDay.setLunarYear(dto.getNoticeTime().getYear());
			lunarTargetDay.setLunarMonth(dto.getNoticeTime().getMonthValue());
			lunarTargetDay.setLunarDay(dto.getNoticeTime().getDayOfMonth());
			LocalDateTime targetDay = localDateTimeHandler.toLocalDateTime(lunarTargetDay);
			LocalTime noticeTime = LocalTime.of(dto.getNoticeTime().getHour(), dto.getNoticeTime().getMinute(),
					dto.getNoticeTime().getSecond());

			if (dto.getNeedRepeat()) {
				TimeUnitType timeUnitType = TimeUnitType.getType(dto.getRepeatTimeUnit());
				if (timeUnitType == null || timeUnitType.equals(TimeUnitType.nanoSecond)
						|| timeUnitType.equals(TimeUnitType.milliSecond) || timeUnitType.equals(TimeUnitType.second)
						|| timeUnitType.equals(TimeUnitType.minute) || timeUnitType.equals(TimeUnitType.hour)) {
					r.addMessage("Invalid time unit for lunar calendar notice");

				} else {
					LocalDateTime now = LocalDateTime.now();
					Lunar nextLunarDate = null;
					if(targetDay.isBefore(now)) {
						lunarTargetDay = getNextValidLunarDate(nextLunarDate, timeUnitType, dto.getRepeatTimeRange());
					}
					dto.setNoticeTime(
							localDateTimeHandler.toLocalDateTime(lunarTargetDay).withHour(noticeTime.getHour())
									.withMinute(noticeTime.getMinute()).withSecond(noticeTime.getSecond()));
				}

			} else {
				if (targetDay.isBefore(LocalDateTime.now())) {
					r.addMessage("Can NOT set a notifications in the past");
				}
			}
		}

		return r;
	}
	
	private AddCalendarNoticeDTO handleLunarDateTrans(AddCalendarNoticeDTO dto) {
		
		LocalDateTime now = LocalDateTime.now();
		TimeUnitType timeUnitType = TimeUnitType.getType(dto.getRepeatTimeUnit());
		Lunar lunarTargetDay = localDateTimeHandler.toLunar(dto.getNoticeTime());
		LocalTime noticeTime = LocalTime.of(dto.getNoticeTime().getHour(), dto.getNoticeTime().getMinute(),
				dto.getNoticeTime().getSecond());
		
		LocalDateTime targetDay = dto.getNoticeTime();

		if(targetDay.isBefore(now)) {
			lunarTargetDay = getNextValidLunarDate(lunarTargetDay, timeUnitType, dto.getRepeatTimeRange());
		}
		dto.setNoticeTime(
				localDateTimeHandler.toLocalDateTime(lunarTargetDay).withHour(noticeTime.getHour())
						.withMinute(noticeTime.getMinute()).withSecond(noticeTime.getSecond()));
		return dto;
	}

 	private Lunar getNextLunarDate(Lunar lunar, TimeUnitType timeUnitType, Integer timeRange) {

		if (timeUnitType.equals(TimeUnitType.day)) {
			lunar.setLunarDay(lunar.getLunarDay() + timeRange);
			return lunar;
		}

		if (!lunar.isIsleap()) {
			LocalDateTime startDate = localDateTimeHandler.toLocalDateTime(lunar);
			lunar.setIsleap(true);
			LocalDateTime endDate = localDateTimeHandler.toLocalDateTime(lunar);
			if (endDate.isAfter(startDate)) {
				return lunar;
			}
		}

		if (lunar.isIsleap()) {
			lunar.setIsleap(false);
		}

		if (timeUnitType.equals(TimeUnitType.month)) {
			lunar.setLunarMonth(lunar.getLunarMonth() + timeRange);

		} else if (timeUnitType.equals(TimeUnitType.year)) {
			lunar.setLunarYear(lunar.getLunarYear() + timeRange);
		}

		return lunar;
	}
	
	private Lunar getNextValidLunarDate(Lunar lunar, TimeUnitType timeUnitType, Integer timeRange) {
		LocalDateTime now = LocalDateTime.now();
		Lunar nextLunar = getNextLunarDate(lunar, timeUnitType, timeRange);
		LocalDateTime nextLocalDateTime = localDateTimeHandler.toLocalDateTime(nextLunar);
		while(nextLocalDateTime.isBefore(now)) {
			nextLunar = getNextLunarDate(lunar, timeUnitType, timeRange);
			nextLocalDateTime = localDateTimeHandler.toLocalDateTime(nextLunar);
		}
		return nextLunar;
	}

	private LocalDateTime getNextLocalDateTime(LocalDateTime datetime, TimeUnitType timeUnitType, Integer timeRange) {

		if (timeUnitType.equals(TimeUnitType.second)) {
			datetime = datetime.plusSeconds(timeRange);
		} else if (timeUnitType.equals(TimeUnitType.minute)) {
			datetime = datetime.plusMinutes(timeRange);
		} else if (timeUnitType.equals(TimeUnitType.hour)) {
			datetime = datetime.plusHours(timeRange);
		} else if (timeUnitType.equals(TimeUnitType.day)) {
			datetime = datetime.plusDays(timeRange);
		} else if (timeUnitType.equals(TimeUnitType.month)) {
			datetime = datetime.plusMonths(timeRange);
		} else if (timeUnitType.equals(TimeUnitType.year)) {
			datetime = datetime.plusYears(timeRange);
		}

		return datetime;
	}

	private LocalDateTime getNextValidLocalDateTime(LocalDateTime datetime, TimeUnitType timeUnitType,
			Integer timeRange) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime nextLocalDateTime = getNextLocalDateTime(datetime, timeUnitType, timeRange);
		while (nextLocalDateTime.isBefore(now)) {
			nextLocalDateTime = getNextLocalDateTime(datetime, timeUnitType, timeRange);
		}
		return nextLocalDateTime;
	}

	private List<CalendarNotice> findNotices() {
		LocalDateTime now = LocalDateTime.now();
		CalendarNoticeExample example = new CalendarNoticeExample();
		example.createCriteria().andIsDeleteEqualTo(false).andIsLunarCalendarEqualTo(false)
				.andNoticeTimeLessThanOrEqualTo(now);
		return mapper.selectByExample(example);
	}

	@Override
	public void findAndSendNotice() {
		List<CalendarNotice> targetList = findNotices();
		if (targetList == null || targetList.isEmpty()) {
			return;
		}

		TelegramMessageDTO dto = null;
		for (CalendarNotice po : targetList) {
			dto = new TelegramMessageDTO();
			dto.setId(TelegramStaticChatID.MY_ID);
			if (po.getIsLunarCalendar()) {
				po.setNoticeContent(po.getNoticeContent() + ", lunar calendar");
			}
			dto.setMsg(po.getNoticeContent());
			dto.setBotName(TelegramBotType.CX_CALENDAR_NOTICE_BOT.getName());
			telegramMessageAckProducer.send(dto);

			if (po.getNeedRepeat()) {
				LocalDateTime oldNoticeTime = po.getNoticeTime();
				TimeUnitType timeUnitType = TimeUnitType.getType(po.getRepeatTimeUnit());
				LocalDateTime nextNoticeTime = null;
				if (po.getIsLunarCalendar()) {
					Lunar oldLunarNoticeTime = localDateTimeHandler.toLunar(oldNoticeTime);
					Lunar nextLunarNoticeTime = getNextLunarDate(oldLunarNoticeTime, timeUnitType,
							po.getRepeatTimeRange());
					nextNoticeTime = localDateTimeHandler.toLocalDateTime(nextLunarNoticeTime);
				} else {
					nextNoticeTime = getNextValidLocalDateTime(oldNoticeTime, timeUnitType, po.getRepeatTimeRange());
				}
				po.setNoticeTime(nextNoticeTime);
				mapper.updateByPrimaryKeySelective(po);
			} else {
				po.setIsDelete(true);
				mapper.updateByPrimaryKeySelective(po);
			}
		}
	}
}
