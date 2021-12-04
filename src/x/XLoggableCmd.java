package x;

public abstract class XLoggableCmd implements XExecutable {
    // fields
    protected XApp mApp = null;
    
    // constructor
    protected XLoggableCmd(XApp app) {
        this.mApp = app;
    }

    @Override
    final public boolean execute() {
        if (this.defineCmd()) {
            this.mApp.getLogMgr().addLog(this.createLog());
            return true;
        } else {
            return false;
        }
    }
    
    // abstract methods
    protected abstract boolean defineCmd();
    protected abstract String createLog();
}
