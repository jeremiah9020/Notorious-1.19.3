package net.jabberjerry.notorious.screen;

import com.google.common.util.concurrent.Runnables;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.CreditsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerWarningScreen;
import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screen.option.LanguageOptionsScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.*;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.Iterator;
public class TitleScreen extends Screen {
    public TitleScreen() {
        super(Text.translatable("narrator.screen.title"));
    }

    public static final Text COPYRIGHT = Text.literal("Copyright Mojang AB. Do not distribute!");
    private static final Identifier ACCESSIBILITY_ICON_TEXTURE = new Identifier("textures/gui/accessibility.png");
    private static final Identifier MINECRAFT_TITLE_TEXTURE = new Identifier("textures/gui/title/minecraft.png");
    private static final Identifier EDITION_TITLE_TEXTURE = new Identifier("textures/gui/title/edition.png");

    protected void init() {
        int i = this.textRenderer.getWidth(COPYRIGHT);
        int j = this.width - i - 2;
        boolean k = true;
        int l = this.height / 4 + 48;
        this.initWidgetsNormal(l, 24);

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.options"), (button) -> {
            this.client.setScreen(new OptionsScreen(this, this.client.options));
        }).dimensions(this.width / 2 - 100, l + 72 + 12, 98, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.quit"), (button) -> {
            this.client.scheduleStop();
        }).dimensions(this.width / 2 + 2, l + 72 + 12, 98, 20).build());
    }

    private void initWidgetsNormal(int y, int spacingY) {
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.singleplayer"), (button) -> {
            this.client.setScreen(new SelectWorldScreen(this));
        }).dimensions(this.width / 2 - 100, y, 200, 20).build());
        Text text = Text.literal("Multiplayer disabled");
        Tooltip tooltip = Tooltip.of(text);
        ((ButtonWidget)this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.multiplayer"), (button) -> {
            Screen screen = this.client.options.skipMultiplayerWarning ? new MultiplayerScreen(this) : new MultiplayerWarningScreen(this);
            this.client.setScreen((Screen)screen);
        }).dimensions(this.width / 2 - 100, y + spacingY * 1, 200, 20).tooltip(tooltip).build())).active = true;
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        boolean i = true;
        int j = this.width / 2 - 137;
        boolean k = true;
        float g = 1.0F;
        int l = MathHelper.ceil(g * 255.0F) << 24;

        this.renderBackground(matrices);

        if ((l & -67108864) != 0) {
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderTexture(0, MINECRAFT_TITLE_TEXTURE);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, g);

            this.drawWithOutline(j, 30, (x, y) -> {
                this.drawTexture(matrices, x, y, 0, 0, 155, 44);
                this.drawTexture(matrices, x + 155, y, 0, 45, 155, 44);
            });


            RenderSystem.setShaderTexture(0, EDITION_TITLE_TEXTURE);
            drawTexture(matrices, j + 88, 67, 0.0F, 0.0F, 98, 14, 128, 16);

            Iterator var12 = this.children().iterator();
            while(var12.hasNext()) {
                Element element = (Element)var12.next();
                if (element instanceof ClickableWidget) {
                    ((ClickableWidget)element).setAlpha(1F);
                }
            }

            super.render(matrices, mouseX, mouseY, delta);
        }
    }

}


