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
		System.out.println("aggiungo evento " + event + " eventsource " + eventSource
				+ " object_id " + object_id);
		if (!event_map.get(eventSource).containsKey(object_id)) {
			event_map.get(eventSource).put(object_id, new HashMap<>());
		}
		/*
		 * for (BlockingQueue<String> queue :
		 * event_map.get(eventSource).get(object_id).get(user_id).) { queue.put(type); }
		 */
		for (String key : event_map.get(eventSource).get(object_id).keySet()) {
			event_map.get(eventSource).get(object_id).get(key).put(event);
		}
	}

	public String nextEvent(String user_id, String object_id, String eventSource) throws InterruptedException {
//		System.out.println("GET EVENT CALL event service");
//		System.out.println("EVENT SOURCE " + eventSource);
		if (!event_map.get(eventSource).containsKey(object_id)) {
//			System.out.println("create queue " + id);
			event_map.get(eventSource).put(object_id, new HashMap<>());
			event_map.get(eventSource).get(object_id).put(user_id, new LinkedBlockingQueue<>());
		}else {
			System.out.println("non contiene");
			event_map.get(eventSource).get(object_id).put(user_id, new LinkedBlockingQueue<>());
		}
		System.out.println("voglio prelevare dalla coda con key " + user_id + " eventsource " + eventSource
				+ " object_id " + object_id);
		return event_map.get(eventSource).get(object_id).get(user_id).take();
	}

}
