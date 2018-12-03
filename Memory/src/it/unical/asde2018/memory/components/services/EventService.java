package it.unical.asde2018.memory.components.services;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class EventService {

	private Map<String, Map<String, Map<String, BlockingQueue<String>>>> event_map;

	@PostConstruct
	private void init() {
		event_map = new HashMap<>();
	}

	public void addEventSource(String eventSource) {
		if (!event_map.containsKey(eventSource))
			event_map.put(eventSource, new HashMap<>());
	}

	public void addEvent(String object_id, String eventSource, String event) throws InterruptedException {
		if (!event_map.get(eventSource).containsKey(object_id)) {
			event_map.get(eventSource).put(object_id, new HashMap<>());
		}
		for (String key : event_map.get(eventSource).get(object_id).keySet()) {
			event_map.get(eventSource).get(object_id).get(key).put(event);
		}
	}

	public String nextEvent(String user_id, String object_id, String eventSource) throws InterruptedException {
		if (!event_map.get(eventSource).containsKey(object_id)) {
			event_map.get(eventSource).put(object_id, new HashMap<>());
			event_map.get(eventSource).get(object_id).put(user_id, new LinkedBlockingQueue<>());
		}else {
			event_map.get(eventSource).get(object_id).put(user_id, new LinkedBlockingQueue<>());
		}
		return event_map.get(eventSource).get(object_id).get(user_id).take();
	}

}
