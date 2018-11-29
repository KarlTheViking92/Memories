package it.unical.asde2018.memory.components.services;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class EventService {

	private Map<String, Map<String, BlockingQueue<String>>> event_map;

	@PostConstruct
	private void init() {
		event_map = new HashMap<>();
	}

	public void addEventSource(String eventSource) {
		if (!event_map.containsKey(eventSource))
			event_map.put(eventSource, new HashMap<>());
	}

	public void addEvent(String id, String eventSource, String type) throws InterruptedException {
//		System.out.println("ADD EVENT CALL id: " + id + " event : " + eventSource);
		for (BlockingQueue<String> queue : event_map.get(eventSource).values()) {
			queue.put(eventSource);
		}
	}

	public String nextEvent(String id, String eventSource) throws InterruptedException {
//		System.out.println("GET EVENT CALL event service");
//		System.out.println("EVENT SOURCE " + eventSource);
		if (!event_map.get(eventSource).containsKey(id)) {
			System.out.println("create queue " + id);
			event_map.get(eventSource).put(id, new LinkedBlockingQueue<>());
		}
		return event_map.get(eventSource).get(id).take();
	}

}
