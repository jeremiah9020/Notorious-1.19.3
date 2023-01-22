package net.jabberjerry.notorious.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;
import org.joml.Matrix4f;

public class MenuScreen extends Screen {
    public MenuScreen() {
        super(Text.translatable("key.notorious.screen.menu"));
    }

    @Override
    protected void init() {
        return;
    }

    private static int withAlpha(int color, int alpha) {
        return color & 16777215 | alpha << 24;
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int COLOR_START = ColorHelper.Argb.getArgb(100, 0, 0, 0);
        int COLOR_END = ColorHelper.Argb.getArgb(250, 0, 0, 0);
        int BUTTON_COLOR = ColorHelper.Argb.getArgb(255, 240, 240, 240);

        fill(matrices,0,0,2*this.width/5,this.height,COLOR_START);
        fillHorizontalGradient(matrices,2*this.width/5,0,this.width,this.height,COLOR_START,COLOR_END);

        int step = this.height/5;
        int y = this.height/10;

        for (int i = 1; i <= 5; i++) {
            fill(matrices,20,step-y,2*this.width/5 - 20,step,BUTTON_COLOR);
            step += this.height/5;
        }
    }

    private void fillHorizontalGradient(MatrixStack matrices, int startX, int startY, int endX, int endY,int colorStart, int colorEnd) {
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        fillGradient(matrices.peek().getPositionMatrix().mapYXZ(), bufferBuilder, endY, startX, startY, endX, getZOffset(),colorStart, colorEnd);
        matrices.peek().getPositionMatrix().mapYXZ();
        tessellator.draw();
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
    }
}
