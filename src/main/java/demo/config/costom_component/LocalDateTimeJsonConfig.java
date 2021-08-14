//package demo.config.costom_component;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
//
//@Configuration
//public class LocalDateTimeJsonConfig {
//
////	@Bean
////    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
////        return builder -> {
////            builder.simpleDateFormat(DateTimeConstant.normalDateTimeFormat);
////            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(DateTimeConstant.normalDateTimeFormat)));
////            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DateTimeConstant.normalDateTimeFormat)));
////            builder.deserializers(new LocalDateDeserializer(DateTimeFormatter.ofPattern(DateTimeConstant.normalDateTimeFormat)));
////            builder.deserializers(new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DateTimeConstant.normalDateTimeFormat)));
////        };
////    }
//
//	@Bean
//	@Primary
//	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
//		String format = "yyyy-MM-dd HH:mm:ss";
//		return builder -> {
//			builder.deserializerByType(LocalDateTime.class,
//					new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(format)));
//			builder.serializerByType(LocalDateTime.class,
//					new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(format)));
//		};
//	}
//
//}
