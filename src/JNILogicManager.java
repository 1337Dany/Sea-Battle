public class JNILogicManager {

    static{
        System.load("C:\\Users\\super\\CLionProjects\\SeaBattleLogicManager\\cmake-build-debug\\libSeaBattleLogicManager.dll");
    }

    public native void setRotated(boolean rotated);

    public native boolean isRotated();

    public native void setGameStart(boolean bool);
    public native boolean isGameStarted();
    public native void setMyTurn(boolean turn);
    public native boolean isMyTurn();

    /*      PlaceShips class logic implementation    */

    public native int getNavy(int index);
    public native void setNavy(int index, int val);
    public native int countShips();

    /*      GameField class logic implementation    */

    public native int borderSize();
    public native int cellSize();
    public native int getShipToLand();
    public native void setShipToLand(int val);
    public native void setPlacable(boolean state);
    public native boolean isPlacable();
    public native void addShip(int index, int x, int y);
    public native int getShipX(int index);
    public native int getShipY(int index);
    public native void setShipSize(int size);
    public native boolean positionValid();
    public native void addAll();
    public native int getShipLocationX(int index);
    public native int getShipLocationY(int index);
    public native int getShipLocationsSize();
    public native int getShipSize();
    public native boolean contains(int x, int y);
    public native void kill(int x, int y);
    public native boolean isDead(int index);
    public native boolean isEveryoneDead();
}
