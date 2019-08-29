//package demo.config;
//
/*
 * mybatis 用springBoot 打包后 类别名无法使用, 个别方案中提议使用此方法, 但实践无效, 暂搁置
 */
//import org.apache.ibatis.io.DefaultVFS;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.jar.JarEntry;
//import java.util.jar.JarInputStream;
//
//import org.springframework.util.ResourceUtils;
//
//
//public class SpringBootVFS extends DefaultVFS {
//
//	@Override
//	protected URL findJarForResource(URL url) throws MalformedURLException {
//		url = ResourceUtils.extractJarFileURL(url);
//		return (isJar(url)) ? url : null;
//	}
//	
//	@Override
//	protected boolean isJar(URL url) {
//		return url.getPath().toLowerCase().endsWith(ResourceUtils.JAR_FILE_EXTENSION)
//				|| url.getPath().toLowerCase().endsWith(ResourceUtils.URL_PROTOCOL_WAR);
//	}
//
//	/**
//	 * List the names of the entries in the given {@link JarInputStream} that
//	 * begin with the specified {@code path}. Entries will match with or without
//	 * a leading slash.
//	 * 
//	 * @param jar The JAR input stream
//	 * @param path The leading path to match
//	 * @return The names of all the matching entries
//	 * @throws IOException If I/O errors occur
//	 */
//	@Override
//	protected List<String> listResources(JarInputStream jar, String path) throws IOException {
//		// Include the leading and trailing slash when matching names
//		if (!path.startsWith("/"))
//			path = "/" + path;
//		if (!path.endsWith("/"))
//			path = path + "/";
//
//		// Iterate over the entries and collect those that begin with the
//		// requested path
//		List<String> resources = new ArrayList<String>();
//		for (JarEntry entry; (entry = jar.getNextJarEntry()) != null;) {
//			if (!entry.isDirectory()) {
//				// Add leading slash if it's missing
//				String name = entry.getName();
//				if (!name.startsWith("/"))
//					name = "/" + name;
//
//				// Check file name
//				if (name.startsWith(path) || name.startsWith("/WEB-INF/classes" + path)) {
////					log.trace("Found resource: ", name);
//					resources.add(name.substring(1)); // Trim leading slash
//				}
//			}
//		}
//		return resources;
//	}
//}