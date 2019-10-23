package es.upm.miw.apaw_ep_computers.documents;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class ComponentComposite extends Component {

    public ComponentComposite(String type, String name, Double cost, String model){
        super(type, name, cost, model);
    }

    private List<Component> components = new ArrayList<>();

    public Boolean isComposite(){
        return true;
    }

    public void addComponent(Component component){
        if(component != null)
            components.add(component);
    }

    public void removeComponent(Component component){
        if(component != null)
            components.remove(component);
    }
}
