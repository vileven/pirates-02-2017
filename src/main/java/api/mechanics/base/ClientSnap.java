package api.mechanics.base;

/**
 * Created by Vileven on 28.05.17.
 */
public class ClientSnap {
    public void setState(String state) {
        this.state = state;
    }

    private String state;



//    public ClientSnap(String state) {
//        this.state = state;
//    }

    public String getState() {
        return state;
    }
}
