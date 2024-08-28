package codes.domino.dsimonsays.client.mixin;

import codes.domino.dsimonsays.client.cmd.SimonSaysCommand;
import net.minecraft.client.GuiMessage;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
public class ChatComponentMixin {

    @Inject(method = "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V", at = @At("HEAD"), cancellable = true)
    public void addMessage(Component chatComponent, MessageSignature headerSignature, GuiMessageTag tag, CallbackInfo ci) {
        if (SimonSaysCommand.getInstance() == null) {
            return;
        }
        if (!SimonSaysCommand.getInstance().isEnabled()) {
            return;
        }
        String simonsName = SimonSaysCommand.getInstance().getSimonsName();
        if (!chatComponent.getString().contains(simonsName)) {
            return;
        }
        ci.cancel();
        GuiMessage guiMessage = new GuiMessage(Minecraft.getInstance().gui.getGuiTicks(),
                Component.literal(chatComponent.getString().replace(simonsName, "§c§l" + simonsName)), headerSignature, tag);
        this.logChatMessage(guiMessage);
        this.addMessageToDisplayQueue(guiMessage);
        this.addMessageToQueue(guiMessage);

    }

    @Shadow
    private void addMessageToQueue(GuiMessage guiMessage) {
    }

    @Shadow
    private void addMessageToDisplayQueue(GuiMessage guiMessage) {
    }

    @Shadow
    private void logChatMessage(GuiMessage message) {

    }

}
