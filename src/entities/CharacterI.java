package entities;

public interface CharacterI {

    String getName();
    int getCurrentHealth();
    int getFirePower();

    void setCurrentHealth(int currentHealth);
    void setFirePower(int firePower);

    void takesDamageFrom(int damage);
    boolean isAlive();

}
