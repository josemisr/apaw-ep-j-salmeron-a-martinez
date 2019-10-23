package es.upm.miw.apaw_ep_computers.documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ComponentLeaf extends Component {

    public ComponentLeaf(String type, String name, Double cost, String model){
        super(type, name, cost, model);
    }

    public Boolean isComposite(){
        return false;
    }

    public void addComponent(Component component){
        //nothing to do because this is a leaf
    }

    public void removeComponent(Component component){
        //nothing to do because this is a leaf
    }
}
