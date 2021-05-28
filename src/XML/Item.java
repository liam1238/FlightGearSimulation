package XML;

public class Item
{
    private String name;
    private String node;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }


    @Override
    public String toString() {
        return name + "," + "set" + " " + node;
    }
}
