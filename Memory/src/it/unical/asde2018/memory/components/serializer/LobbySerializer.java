package it.unical.asde2018.memory.components.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import it.unical.asde2018.memory.model.Lobby;
import it.unical.asde2018.memory.model.Player;

@SuppressWarnings("serial")
public class LobbySerializer extends StdSerializer<Lobby> {

	public LobbySerializer() {
		this(null);
	}

	public LobbySerializer(Class<Lobby> t) {
		super(t);
	}

	@Override
	public void serialize(Lobby lobby, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeStringField("name", lobby.getName());
		gen.writeNumberField("playerSize", lobby.getNumberOfPlayers());
		gen.writeArrayFieldStart("userList");
		for (Player p : lobby.getPlayers()) {
			gen.writeStartObject();
			gen.writeStringField("player", p.getUsername());
			gen.writeStringField("creator", lobby.getCreator().getUsername());
			gen.writeEndObject();
		}
		gen.writeEndArray();
		gen.writeEndObject();
	}

}
