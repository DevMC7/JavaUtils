package net.devmc.java_utils.config;

import net.devmc.java_utils.logging.LogManager;
import net.devmc.java_utils.logging.Logger;

import java.io.IOException;
import java.nio.file.*;

public class DynamicConfigReloader extends Thread {
	private final Config config;
	private final Logger logger;
	private final boolean log;

	public DynamicConfigReloader(Config config, Logger logger, boolean log) {
		this.config = config;
		this.logger = logger;
		this.log = log;
		setDaemon(true);
	}

	public DynamicConfigReloader(Config config, boolean log) {
		this.config = config;
		this.logger = LogManager.getLogger(getClass());
		this.log = log;
		setDaemon(true);
	}

	public DynamicConfigReloader(Config config, Logger logger) {
		this(config, logger, false);
	}

	@Override
	public void run() {
		try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
			Path path = Path.of(config.getPath());
			path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

			while (true) {
				WatchKey key = watchService.take();
				for (WatchEvent<?> event : key.pollEvents()) {
					if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY &&
							config.getPath().equals(event.context())) {
						config.load();
						if (log) logger.debug("Configuration reloaded.");
					}
				}
				if (!key.reset()) {
					break;
				}
			}
		} catch (IOException | InterruptedException e) {
			logger.error("Failed to reload configuration: " + e.getMessage());
		}
	}
}
