package domain;

public abstract class EspecialElement extends Entity implements Interactable{
    protected boolean active;
    public boolean isActive() {
        return active;
    }
}
