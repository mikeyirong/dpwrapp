package mike.dpwrapp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class ConfigurationLoader {
	static void registerURLProtocol() {
		URL.setURLStreamHandlerFactory(new URLStreamHandlerFactory() {
			public URLStreamHandler createURLStreamHandler(String arg0) {
				if ("classpath".equals(arg0)) {
					return new URLStreamHandler() {
						protected URLConnection openConnection(URL arg0) throws IOException {
							String file = arg0.getFile();
							System.out.println("file===="+file);
							if (file.isEmpty()) {
								throw new IOException("Malformed URL:" + arg0);
							}
							return new URLConnection(arg0) {
						
								public InputStream getInputStream() throws IOException {
									return Thread.currentThread().getContextClassLoader()
											.getResourceAsStream(file);
								}

								@Override
								public void connect() throws IOException {
									
								}
							};
						}
					};
				}
				return null;
			}
		});
	}

	public static InputStream loadAsInputStream(String expr) {
		try {
			URL url = new URL(expr);
			URLConnection connection = url.openConnection();
			InputStream in = connection.getInputStream();
			return in;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String load(String expr) {
		try {
			InputStream in = loadAsInputStream(expr);
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int len = -1;
			do {
				len = in.read(buffer);
				if (len != -1)
					out.write(buffer, 0, len);
			} while (len != -1);

			return new String(buffer).trim();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static {
		registerURLProtocol();
	}
}
