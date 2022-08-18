package live.midreamsheep.optb.android.handler;

public abstract class OPTBHandlerAbstract implements OPTBHandlerInterface{
    @Override
    public void execute(byte[] data) {
            executeCode(data);
    }
    protected abstract void executeCode(byte[] data);
}
