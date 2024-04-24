package demo.config.customComponent;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import autoTest.report.service.ATJsonReportService;

@Component
public class AutomationTestComponent {

	@Bean
	public ATJsonReportService getATJsonReportService() {
		return new ATJsonReportService();
	}
	
}
