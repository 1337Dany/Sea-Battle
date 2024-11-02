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
}
