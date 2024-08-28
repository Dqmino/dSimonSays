package codes.domino.dsimonsays.client;

import codes.domino.dsimonsays.client.cmd.SimonSaysCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class DSimonSaysClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            new SimonSaysCommand(dispatcher);
        });
    }
}
