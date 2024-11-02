public class JNILogicManager {

    static{
        System.load("C:\\Users\\super\\CLionProjects\\SeaBattleLogicManager\\cmake-build-debug\\libSeaBattleLogicManager.dll");
    }

    public native void setRotated(boolean rotated);

    public native boolean isRotated();
}
