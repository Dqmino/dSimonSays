package codes.domino.dsimonsays.client.cmd;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class SimonSaysCommand {
    private static SimonSaysCommand instance;
    private String simonsName = "simon";
    private boolean enabled = true;

    public static SimonSaysCommand getInstance() {
        return instance;
    }

    public String getSimonsName() {
        return simonsName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public SimonSaysCommand(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        instance = this;
        LiteralCommandNode<FabricClientCommandSource> mainCommand = dispatcher.register(ClientCommandManager
                .literal("dss")
                .executes(context -> {
//                    String tempSimonsName = context.getArgument("simonsName", String.class);
//                    if (tempSimonsName != null) {
//                        return 0;
//                    }
                    this.enabled = !this.enabled;
                    Minecraft.getInstance().player.displayClientMessage(Component.literal(
                            (enabled ? "§aEnabled dSimonSays."
                                    + "\nSimon's name: " + this.simonsName : "§cDisabled dSimonSays.")), false);

                    return 0;
                }));

        ArgumentCommandNode<FabricClientCommandSource, String> airArgument = ClientCommandManager
                .argument("simonsName", StringReader::readString)
                .executes((context -> {
                    String tempSimonsName = context.getArgument("simonsName", String.class);
                    if (tempSimonsName == null) {
                        Minecraft.getInstance().player.displayClientMessage(Component.literal("§cInvalid usage. /dss <simonsName>"), false);
                        return 0;
                    }
                    this.simonsName = tempSimonsName;
                    Minecraft.getInstance().player.displayClientMessage(Component.literal("§aSet simon's name to: %s"
                            .formatted(this.simonsName)), false);
                    return 0;
                })).build();

        mainCommand.addChild(airArgument);
    }
}
