package demo.config.costom_component;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import at.report.service.ATJsonReportService;

@Component
public class AutomationTestComponent {

	@Bean
	public ATJsonReportService getATJsonReportService() {
		return new ATJsonReportService();
	}
	
}
