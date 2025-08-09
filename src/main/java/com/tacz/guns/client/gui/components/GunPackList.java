package com.tacz.guns.client.gui.components;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tacz.guns.client.gui.GunSmithTableScreen;
import com.tacz.guns.client.resource.ClientAssetsManager;
import com.tacz.guns.client.resource.pojo.PackInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.*;

public class GunPackList extends ContainerObjectSelectionList<GunPackList.Entry> {
    private final GunSmithTableScreen parent;
    private final List<Checkbox> gunPackList = new ArrayList<>();
    private final Set<String> selectedNamespaces = new HashSet<>();
    private final Checkbox byHandCheckbox;
    private final EditBox byName;

    public GunPackList(Minecraft pMinecraft, int pWidth, int pHeight, int pY0, int pY1, int pItemHeight,
                       Map<ResourceLocation, List<ResourceLocation>> recipes, GunSmithTableScreen parent) {
        super(pMinecraft, pWidth, pHeight, pY0, pY1, pItemHeight);
        this.setRenderBackground(false);
        this.setRenderTopAndBottom(false);
        this.parent = parent;
        Set<String> namespaces = new HashSet<>();
        for (List<ResourceLocation> entry : recipes.values()) {
            entry.forEach((resourceLocation) -> namespaces.add(resourceLocation.getNamespace()));
        }

        this.byName = new EditBox(pMinecraft.font, 3, 0, 94, 10, Component.empty());
        this.byName.setHint(Component.translatable("gui.tacz.gun_smith_table.filter.search"));
        this.byName.setResponder((pText) -> {
            parent.init();
            parent.setIndexPage(0);
        });
        this.addEntry(new GunPackList.Entry(byName));

        this.byHandCheckbox = new Checkbox(0, 0, 10, 10, Component.translatable("gui.tacz.gun_smith_table.filter.handgun"), false) {
            @Override
            public void onPress() {
                super.onPress();
                parent.init();
                parent.setIndexPage(0);
            }
        };
        this.addEntry(new GunPackList.Entry(byHandCheckbox));

        Checkbox checkbox1 = new Checkbox(0, 0, 10, 10, Component.translatable("gui.tacz.gun_smith_table.filter.all"), true) {
            @Override
            public void onPress() {
                super.onPress();
                gunPackList.forEach((checkbox) -> checkbox.selected = this.selected);
                updateSelectedNamespaces();
            }
        };
        this.addEntry(new GunPackList.Entry(checkbox1));

        for (String namespace : namespaces) {
            PackInfo packInfo = ClientAssetsManager.INSTANCE.getPackInfo(namespace);
            Component name = packInfo == null ? Component.literal(namespace) : Component.translatable(packInfo.getName());

            Checkbox checkbox = new Checkbox(0, 0, 10, 10, name, namespace, true) {
                @Override
                public void onPress() {
                    super.onPress();
                    checkbox1.selected = gunPackList.stream().allMatch(Checkbox::selected);
                    updateSelectedNamespaces();
                }
            };
            gunPackList.add(checkbox);
            selectedNamespaces.add(namespace);
            this.addEntry(new GunPackList.Entry(checkbox));
        }
    }

    public String getSearchText() {
        return byName.getValue();
    }

    public boolean isByHandSelected() {
        return byHandCheckbox.selected;
    }

    public Set<String> namespaceList() {
        return selectedNamespaces;
    }

    public void updateSelectedNamespaces() {
        selectedNamespaces.clear();
        gunPackList.forEach((checkbox) -> {
            if (checkbox.selected) {
                selectedNamespaces.add(checkbox.getId());
            }
        });
        parent.init();
        parent.setIndexPage(0);
    }

    protected int getScrollbarPosition() {
        return this.x1 - 2;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        pGuiGraphics.fill(this.x0, this.y0, this.x1, this.y1, 0x80000000);
        int i = this.getScrollbarPosition();
        int j = i + 6;

        this.enableScissor(pGuiGraphics);
        this.renderList(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        pGuiGraphics.disableScissor();

        int i2 = this.getMaxScroll();
        if (i2 > 0) {
            int j2 = (int)((float)((this.y1 - this.y0) * (this.y1 - this.y0)) / (float)this.getMaxPosition());
            j2 = Mth.clamp(j2, 32, this.y1 - this.y0 - 8);
            int k1 = (int)this.getScrollAmount() * (this.y1 - this.y0 - j2) / i2 + this.y0;
            if (k1 < this.y0) {
                k1 = this.y0;
            }
            pGuiGraphics.fill(i, k1, j, k1 + j2, -8355712);
            pGuiGraphics.fill(i, k1, j - 1, k1 + j2 - 1, -4144960);
        }
        this.renderDecorations(pGuiGraphics, pMouseX, pMouseY);

        RenderSystem.disableBlend();
    }

    public int getRowLeft() {
        return this.x0 + 4;
    }

    public int getRowWidth() {
        return this.width;
    }

    public static class Entry extends ContainerObjectSelectionList.Entry<GunPackList.Entry> {
        private final AbstractWidget widget;

        public Entry(AbstractWidget widget) {
            this.widget = widget;
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
             return ImmutableList.of(widget);
        }

        @Override
        public void render(GuiGraphics pGuiGraphics, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pHovering, float pPartialTick) {
            this.widget.setX(pLeft);
            this.widget.setY(pTop);
            this.widget.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return ImmutableList.of(widget);
        }
    }

    public static class Checkbox extends AbstractButton {
        private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/checkbox.png");
        protected boolean selected;
        protected final boolean showLabel;
        private String id;

        public Checkbox(int pX, int pY, int pWidth, int pHeight, Component pMessage, String id, boolean pSelected) {
            this(pX, pY, pWidth, pHeight, pMessage, pSelected, true);
            this.id = id;
        }

        public Checkbox(int pX, int pY, int pWidth, int pHeight, Component pMessage, boolean pSelected) {
            this(pX, pY, pWidth, pHeight, pMessage, pSelected, true);
        }

        public Checkbox(int pX, int pY, int pWidth, int pHeight, Component pMessage, boolean pSelected, boolean pShowLabel) {
            super(pX, pY, pWidth, pHeight, pMessage);
            this.selected = pSelected;
            this.showLabel = pShowLabel;
        }

        public String getId() {
            return id;
        }

        public void onPress() {
            this.selected = !this.selected;
        }

        public boolean selected() {
            return this.selected;
        }

        public void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
            pNarrationElementOutput.add(NarratedElementType.TITLE, this.createNarrationMessage());
            if (this.active) {
                if (this.isFocused()) {
                    pNarrationElementOutput.add(NarratedElementType.USAGE, Component.translatable("narration.checkbox.usage.focused"));
                } else {
                    pNarrationElementOutput.add(NarratedElementType.USAGE, Component.translatable("narration.checkbox.usage.hovered"));
                }
            }

        }

        public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
            Minecraft minecraft = Minecraft.getInstance();
            RenderSystem.enableDepthTest();
            Font font = minecraft.font;
            pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.enableBlend();
            pGuiGraphics.blit(TEXTURE, this.getX(), this.getY(), this.isFocused() ? 10.0F : 0.0F, this.selected ? 10.0F : 0.0F, 10, 10, 32, 32);
            pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            if (this.showLabel) {
                pGuiGraphics.drawString(font, this.getMessage(), this.getX() + 24, this.getY() + (this.height - 8) / 2, 14737632 | Mth.ceil(this.alpha * 255.0F) << 24);
            }

        }
    }
}
