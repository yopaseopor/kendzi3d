/*
 * This software is provided "AS IS" without a warranty of any kind.
 * You use it on your own risk and responsibility!!!
 *
 * This file is shared under BSD v3 license.
 * See readme.txt and BSD3 file for details.
 *
 */

package kendzi.josm.kendzi3d.action;

import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonModel;

import kendzi.josm.kendzi3d.Kendzi3DPlugin;
import kendzi.josm.kendzi3d.jogl.model.ground.Ground;
import kendzi.josm.kendzi3d.jogl.model.ground.StyledTitleGround;
import kendzi.josm.kendzi3d.ui.Kendzi3dGLEventListener;

import org.openstreetmap.josm.actions.JosmAction;

/**
 * Enable/disable display texture on ground toggle action.
 *
 * @author Tomasz Kędziora (Kendzi)
 *
 */
public class GroundToggleAction extends JosmAction {

    /**
     * Button models.
     */
    private final List<ButtonModel> buttonModels = new ArrayList<ButtonModel>();
    //FIXME: replace with property Action.SELECTED_KEY when migrating to
    // Java 6
    private boolean selected;

    private Kendzi3DPlugin kendzi3DPlugin;

    /**
     * Constructor of debug toggle action.
     */
    public GroundToggleAction(Kendzi3DPlugin kendzi3dPlugin) {
        super(
                tr("Textured Ground"),
                "1306318261_debugger__24",
                tr("Enable/disable display texture on ground"),
//                Shortcut.registerShortcut("menu:view:wireframe", tr("Toggle Wireframe view"),KeyEvent.VK_W, Shortcut.GROUP_MENU),
                null,
                true /* register shortcut */
        );
        this.selected = false;

        this.kendzi3DPlugin = kendzi3dPlugin;

        // Main.pref.getBoolean("draw.wireframe", false);
        notifySelectedState();

        setDebugMode(this.selected);

    }

    /**
     * @param pModel button model
     */
    public void addButtonModel(ButtonModel pModel) {
        if (pModel != null && !this.buttonModels.contains(pModel)) {
            this.buttonModels.add(pModel);
            pModel.setSelected(this.selected);
        }
    }

    /**
     * @param pModel button model
     */
    public void removeButtonModel(ButtonModel pModel) {
        if (pModel != null && this.buttonModels.contains(pModel)) {
            this.buttonModels.remove(pModel);
        }
    }

    /**
     *
     */
    protected void notifySelectedState() {
        for (ButtonModel model : this.buttonModels) {
            if (model.isSelected() != this.selected) {
                model.setSelected(this.selected);
            }
        }
    }

    /**
     *
     */
    protected void toggleSelectedState() {
        this.selected = !this.selected;
//        Main.pref.put("draw.wireframe", this.selected);
        notifySelectedState();

        setDebugMode(this.selected);
    }

    /**
     * @param pEnable enable debug
     */
    private void setDebugMode(boolean pEnable) {

        Kendzi3dGLEventListener canvasListener = this.kendzi3DPlugin.getCanvasListener();
        if (canvasListener != null) {
            if (pEnable) {
                canvasListener.setGround(new StyledTitleGround());
            } else {
                canvasListener.setGround(new Ground());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent pE) {
        toggleSelectedState();
    }

    @Override
    protected void updateEnabledState() {
//        setEnabled(Main.map != null && Main.main.getEditLayer() != null);
    }

    /** Is selected.
     * @return selected
     */
    public boolean isSelected() {
        return this.selected;
    }

    /** If can be in debug mode.
     * @return debug mode
     */
    public boolean canDebug() {
        return true;
    }
}