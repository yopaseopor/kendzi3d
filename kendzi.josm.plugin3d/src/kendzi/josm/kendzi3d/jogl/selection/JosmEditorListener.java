package kendzi.josm.kendzi3d.jogl.selection;

import kendzi.josm.kendzi3d.jogl.selection.editor.ArrowEditorJosm;
import kendzi.josm.kendzi3d.jogl.selection.event.ArrowEditorChangeEvent;
import kendzi.josm.kendzi3d.jogl.selection.event.EditorChangeEvent;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.command.ChangeCommand;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.data.osm.Way;

public class JosmEditorListener implements kendzi.josm.kendzi3d.jogl.selection.ObjectSelectionListener.EditorChangeListener {

    @Override
    public void onEditorChange(EditorChangeEvent event) {

        if (event instanceof ArrowEditorChangeEvent) {
            ArrowEditorChangeEvent aece = (ArrowEditorChangeEvent) event;

            if ( aece.getArrowEditor() instanceof ArrowEditorJosm) {

                ArrowEditorJosm ae = (ArrowEditorJosm) aece.getArrowEditor();

                OsmPrimitive primitive = Main.main.getCurrentDataSet().getPrimitiveById(ae.getPrimitiveId(), ae.getPrimitiveType());

                double newValue = aece.getHeight();

                if (primitive instanceof Way) {
                    Way newWay = new Way((Way) primitive);

                    // XXX FIXME format!
                    newWay.put(ae.getFildName(), "" + newValue);

                    ae.setValue(newValue);

                    Main.main.undoRedo.add(new ChangeCommand(primitive, newWay));

                } else {
                    throw new RuntimeException("TODO");
                }
            } else {
                throw new RuntimeException("TODO");
            }
        } else {
            throw new RuntimeException("TODO");
        }
    }
}