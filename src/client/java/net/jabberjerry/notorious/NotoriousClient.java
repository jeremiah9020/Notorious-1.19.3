package net.jabberjerry.notorious;

import net.fabricmc.api.ClientModInitializer;
import net.jabberjerry.notorious.event.InputHandlers;

public class NotoriousClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		InputHandlers.register();
	}
}