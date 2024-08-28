package codes.domino.dsimonsays.client.mixin;

import codes.domino.dsimonsays.client.cmd.SimonSaysCommand;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ChatComponent.class)
public class ChatComponentMixin {

    @ModifyVariable(method = "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V"
            , at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public Component injected(Component chatComponent) {
        if (SimonSaysCommand.getInstance() == null || !SimonSaysCommand.getInstance().isEnabled()) {
            return chatComponent;
        }
        String simonsName = SimonSaysCommand.getInstance().getSimonsName();
        if (!chatComponent.getString().contains(simonsName)) {
            return chatComponent;
        }
        return Component.literal(chatComponent.getString().replace(simonsName, "§c§l" + simonsName));
    }
}
