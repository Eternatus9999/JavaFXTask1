package dto;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Item {
    private StringProperty name = new SimpleStringProperty();
    private BooleanProperty state = new SimpleBooleanProperty();
    private int id;

    public Item(String name, boolean state, int id){
        setName(name);
        setState(state);
        this.id =id;
    }
    public final BooleanProperty stateProperty(){
        return this.state;
    }

    public StringProperty nameProperty() {
        return this.name;
    }
    public final String getName() {
        return this.nameProperty().get();
    }

    public final void setName(String name) {
        this.nameProperty().set(name);
    }
    public final void setState(boolean state) {
        this.stateProperty().set(state);
    }
    public final boolean isState() {
        return this.stateProperty().get();
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id =id;
    }
    @Override
    public String toString(){
        return getName();
    }
}
