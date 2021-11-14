package demo.tool.calendarNotice.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.common.service.CommonService;
import demo.tool.calendarNotice.mapper.CalendarNoticeMapper;
import demo.tool.calendarNotice.mapper.CalendarPreNoticeMapper;
import demo.tool.calendarNotice.mq.producer.TelegramCalendarNoticeMessageAckProducer;
import demo.tool.calendarNotice.pojo.dto.AddCalendarNoticeDTO;
import demo.tool.calendarNotice.pojo.po.CalendarNotice;
import demo.tool.calendarNotice.pojo.po.CalendarNoticeExample;
import demo.tool.calendarNotice.pojo.po.CalendarPreNotice;
import demo.tool.calendarNotice.pojo.po.CalendarPreNoticeExample;
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
	private CalendarPreNoticeMapper preNoticeMapper;
	@Autowired
	private TelegramCalendarNoticeMessageAckProducer telegramMessageAckProducer;

	@Override
	public ModelAndView getManagerView() {
		ModelAndView v  = new ModelAndView("toolJSP/CalendarNoticeSettingManager");
		
		List<TimeUnitType> timeUnitTypeList = Arrays.asList(TimeUnitType.year, TimeUnitType.month, TimeUnitType.week, TimeUnitType.day, TimeUnitType.hour, TimeUnitType.minute);
		v.addObject("timeUnitTypeList", timeUnitTypeList);
		
		List<CalendarNotice> noticeList = findNotices();
		v.addObject("noticeList", noticeList);
		return v;
	}
	
	@Override
	public CommonResult addCalendarNotice(AddCalendarNoticeDTO dto) {
		CommonResult r = validAndSimpleFixDTO(dto);
		if (r.isFail()) {
			return r;
		}

		long newNoticeId = snowFlake.getNextId();
		CalendarNotice po = new CalendarNotice();
		po.setId(newNoticeId);
		po.setIsLunarCalendar(dto.getIsLunarNotice());
		po.setNeedRepeat(dto.getNeedRepeat());
		po.setNoticeContent(dto.getNoticeContent());
		po.setNoticeTime(dto.getNoticeTime());
		po.setRepeatTimeUnit(dto.getRepeatTimeUnit());
		po.setRepeatTimeRange(dto.getRepeatTimeRange());
		po.setValidTime(dto.getValidTime());
		mapper.insertSelective(po);

		if (dto.getPreNoticeRepeatTimeRange() != null && dto.getPreNoticeRepeatTimeUnit() != null) {
			addPreNotice(dto, newNoticeId);
		}

		r.setIsSuccess();
		return r;
	}

	private void addPreNotice(AddCalendarNoticeDTO dto, long newNoticeId) {
		CalendarPreNotice po = new CalendarPreNotice();
		po.setBindNoticeId(newNoticeId);
		po.setId(snowFlake.getNextId());
		po.setValidTime(dto.getNoticeTime());
		po.setRepeatTimeRange(dto.getPreNoticeRepeatTimeRange());
		po.setRepeatTimeUnit(dto.getPreNoticeRepeatTimeUnit());
		po.setRepeatCount(dto.getPreNoticeCount());
		
		TimeUnitType timeUnitType = TimeUnitType.getType(dto.getPreNoticeRepeatTimeUnit());
		po.setNoticeTime(getPreNoticeTime(dto.getNoticeTime(), timeUnitType, dto.getPreNoticeRepeatTimeRange(),
				dto.getPreNoticeCount()));

		preNoticeMapper.insertSelective(po);
	}

	private CommonResult validAndSimpleFixDTO(AddCalendarNoticeDTO dto) {
		CommonResult r = new CommonResult();
		if (dto.getNeedRepeat()) {
			if (dto.getRepeatTimeUnit() == null || dto.getRepeatTimeRange() == null) {
				r.addMessage("If need repeat, please fill time unit and time range");
			} else {
				if (dto.getRepeatTimeRange() <= 0) {
					r.addMessage("Time range must >= 0");
				}
				TimeUnitType timeUnitType = TimeUnitType.getType(dto.getRepeatTimeUnit());
				if (timeUnitType == null || timeUnitType.equals(TimeUnitType.nanoSecond)
						|| timeUnitType.equals(TimeUnitType.milliSecond)) {
					r.addMessage("Invalid time unit");
				}
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
			lunarTargetDay.setLunarYear(dto.getLunarNoticeTime().getYear());
			lunarTargetDay.setLunarMonth(dto.getLunarNoticeTime().getMonthValue());
			lunarTargetDay.setLunarDay(dto.getLunarNoticeTime().getDayOfMonth());
			LocalDateTime targetDayTime = localDateTimeHandler.toLocalDateTime(lunarTargetDay);
			LocalTime noticeTime = LocalTime.of(dto.getLunarNoticeTime().getHour(), dto.getLunarNoticeTime().getMinute(),
					dto.getLunarNoticeTime().getSecond());
			targetDayTime.withHour(noticeTime.getHour()).withMinute(noticeTime.getMinute()).withSecond(noticeTime.getSecond());

			if (dto.getNeedRepeat()) {
				TimeUnitType timeUnitType = TimeUnitType.getType(dto.getRepeatTimeUnit());
				if (timeUnitType == null || timeUnitType.getCode() < TimeUnitType.day.getCode()) {
					r.addMessage("Invalid time unit for lunar calendar notice");

				} else {
					if (targetDayTime.isBefore(LocalDateTime.now())) {
						lunarTargetDay = getNextValidLunarDate(lunarTargetDay, timeUnitType, dto.getRepeatTimeRange());
					}
					dto.setNoticeTime(
							localDateTimeHandler.toLocalDateTime(lunarTargetDay).withHour(noticeTime.getHour())
									.withMinute(noticeTime.getMinute()).withSecond(noticeTime.getSecond()));
				}

			} else {
				if (targetDayTime.isBefore(LocalDateTime.now())) {
					r.addMessage("Can NOT set a notifications in the past");
				} else {
					dto.setNoticeTime(targetDayTime);
				}
			}

			if (dto.getPreNoticeRepeatTimeRange() != null || dto.getPreNoticeRepeatTimeUnit() != null) {
				if (dto.getPreNoticeRepeatTimeRange() == null || dto.getPreNoticeRepeatTimeUnit() == null) {
					r.addMessage("Please set pre notice repeat time range and time unit, if set pre notice");
				} else {
					if (dto.getPreNoticeRepeatTimeUnit() < TimeUnitType.minute.getCode()) {
						r.addMessage("Repettition period must be greater than 1 minute");
					}
					if (dto.getPreNoticeRepeatTimeRange() < 1) {
						r.addMessage("Repettition range must be greater than 1 minute");
					}
					if (dto.getPreNoticeCount() == null || dto.getPreNoticeCount() < 1) {
						r.addMessage("Repettition count must be greater than 1 minute");
					}
				}
			}
		}

		r.setSuccess(StringUtils.isBlank(r.getMessage()));
		return r;
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
		while (nextLocalDateTime.isBefore(now)) {
			nextLunar = getNextLunarDate(lunar, timeUnitType, timeRange);
			nextLocalDateTime = localDateTimeHandler.toLocalDateTime(nextLunar);
		}
		return nextLunar;
	}

	private LocalDateTime getPreNoticeTime(LocalDateTime noticeTime, TimeUnitType timeUnitType, Integer timeRange,
			Integer preNoticeCount) {
		if (TimeUnitType.year.equals(timeUnitType)) {
			return noticeTime.minusYears(timeRange * preNoticeCount);
		} else if (TimeUnitType.month.equals(timeUnitType)) {
			return noticeTime.minusMonths(timeRange * preNoticeCount);
		} else if (TimeUnitType.week.equals(timeUnitType)) {
			return noticeTime.minusWeeks(timeRange * preNoticeCount);
		} else if (TimeUnitType.day.equals(timeUnitType)) {
			return noticeTime.minusDays(timeRange * preNoticeCount);
		} else if (TimeUnitType.hour.equals(timeUnitType)) {
			return noticeTime.minusHours(timeRange * preNoticeCount);
		} else if (TimeUnitType.minute.equals(timeUnitType)) {
			return noticeTime.minusMinutes(timeRange * preNoticeCount);
		}
		return LocalDateTime.now();
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
		} else if (timeUnitType.equals(TimeUnitType.week)) {
			datetime = datetime.plusWeeks(timeRange);
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
			nextLocalDateTime = getNextLocalDateTime(nextLocalDateTime, timeUnitType, timeRange);
		}
		return nextLocalDateTime;
	}

	private List<CalendarNotice> findNotices() {
		LocalDateTime now = LocalDateTime.now();
		CalendarNoticeExample example = new CalendarNoticeExample();
		example.createCriteria().andIsDeleteEqualTo(false).andNoticeTimeLessThanOrEqualTo(now);
		return mapper.selectByExample(example);
	}

	private List<CalendarPreNotice> findPreNotices() {
		LocalDateTime now = LocalDateTime.now();
		CalendarPreNoticeExample example = new CalendarPreNoticeExample();
		example.createCriteria().andIsDeleteEqualTo(false).andNoticeTimeLessThanOrEqualTo(now);
		return preNoticeMapper.selectByExample(example);
	}

	@Override
	public void findAndSendNotice() {
		List<CalendarNotice> commonNoticeList = findNotices();
		List<CalendarPreNotice> preNoticeList = findPreNotices();
		if ((commonNoticeList == null || commonNoticeList.isEmpty())
				&& (preNoticeList == null || preNoticeList.isEmpty())) {
			return;
		}

		TelegramMessageDTO dto = null;
		for (CalendarNotice po : commonNoticeList) {
			dto = new TelegramMessageDTO();
			dto.setId(TelegramStaticChatID.MY_ID);
			dto.setMsg(po.getNoticeContent());
			dto.setBotName(TelegramBotType.CX_CALENDAR_NOTICE_BOT.getName());
			telegramMessageAckProducer.send(dto);

			updateNoticeStatus(po);
		}

		CalendarNotice notice = null;
		for (CalendarPreNotice preNoticePo : preNoticeList) {
			notice = mapper.selectByPrimaryKey(preNoticePo.getBindNoticeId());
			dto = new TelegramMessageDTO();
			dto.setId(TelegramStaticChatID.MY_ID);
			dto.setMsg("PreNotice: " + notice.getNoticeContent() + " at: " + notice.getNoticeTime());
			dto.setBotName(TelegramBotType.CX_CALENDAR_NOTICE_BOT.getName());
			telegramMessageAckProducer.send(dto);

			updatePreNoticeStatus(preNoticePo, notice);
		}
	}

	private void updateNoticeStatus(CalendarNotice po) {
		if (po.getNeedRepeat()) {
			LocalDateTime oldNoticeTime = po.getNoticeTime();
			TimeUnitType timeUnitType = TimeUnitType.getType(po.getRepeatTimeUnit());
			LocalDateTime nextNoticeTime = null;
			if (po.getIsLunarCalendar()) {
				Lunar oldLunarNoticeTime = localDateTimeHandler.toLunar(oldNoticeTime);
				Lunar nextLunarNoticeTime = getNextLunarDate(oldLunarNoticeTime, timeUnitType, po.getRepeatTimeRange());
				nextNoticeTime = localDateTimeHandler.toLocalDateTime(nextLunarNoticeTime);
				nextNoticeTime = nextNoticeTime.withHour(oldNoticeTime.getHour()).withMinute(oldNoticeTime.getMinute())
						.withSecond(oldNoticeTime.getSecond());
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

	private void updatePreNoticeStatus(CalendarPreNotice preNoticePo, CalendarNotice po) {
		TimeUnitType preNoticeTimeUnitType = TimeUnitType.getType(preNoticePo.getRepeatTimeUnit());
		LocalDateTime nextPreNoticeTime = getNextLocalDateTime(preNoticePo.getNoticeTime(), preNoticeTimeUnitType,
				preNoticePo.getRepeatTimeRange());

		// 超出本次提前通知的有效时间
		if (nextPreNoticeTime.isAfter(po.getNoticeTime())) {
			// 如果是重复通知
			if (po.getNeedRepeat()) {
				TimeUnitType noticeTimeUnitType = TimeUnitType.getType(po.getRepeatTimeUnit());
				LocalDateTime nextNoticeTime = getNextLocalDateTime(po.getNoticeTime(), noticeTimeUnitType,
						po.getRepeatTimeRange());
				// 重复通知仍要继续
				if (po.getValidTime() == null || !nextNoticeTime.isAfter(po.getValidTime())) {
					nextPreNoticeTime = getPreNoticeTime(nextNoticeTime, preNoticeTimeUnitType,
							preNoticePo.getRepeatTimeRange(), preNoticePo.getRepeatCount());
					preNoticePo.setValidTime(nextNoticeTime);
					preNoticePo.setNoticeTime(nextPreNoticeTime);
					preNoticeMapper.updateByPrimaryKeySelective(preNoticePo);
					return;
					// 重复通知无需继续
				} else {
					preNoticePo.setIsDelete(true);
					preNoticeMapper.updateByPrimaryKeySelective(preNoticePo);
					return;
				}
				// 非重复通知
			} else {
				preNoticePo.setIsDelete(true);
				preNoticeMapper.updateByPrimaryKeySelective(preNoticePo);
				return;
			}
			// 未超出提前通知有效时间
		} else {
			preNoticePo.setNoticeTime(nextPreNoticeTime);
			preNoticeMapper.updateByPrimaryKeySelective(preNoticePo);
			return;
		}
	}
}
